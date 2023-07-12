package com.phuclq.student.service.impl;

//import com.aspose.words.Document;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.phuclq.student.common.Constants;
import com.phuclq.student.exception.BusinessHandleException;
import com.phuclq.student.exception.ExceptionUtils;
import com.phuclq.student.repository.*;
import com.phuclq.student.service.AttachmentService;
import com.phuclq.student.service.EmailSenderService;
import com.phuclq.student.service.UserHistoryService;
import com.phuclq.student.types.CommentType;
import com.phuclq.student.types.FileType;
import com.phuclq.student.types.HistoryCoinType;
import com.phuclq.student.types.OrderFileType;
import com.phuclq.student.types.RateType;
import com.phuclq.student.utils.Base64ToMultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.phuclq.student.domain.*;
import com.phuclq.student.dto.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phuclq.student.exception.BusinessException;
import com.phuclq.student.exception.NotFoundException;
import com.phuclq.student.service.FileService;
import com.phuclq.student.service.UserService;
import com.phuclq.student.utils.ActivityConstants;
import com.phuclq.student.utils.DateTimeUtils;
import org.springframework.web.multipart.MultipartFile;

import static com.phuclq.student.utils.ActivityConstants.CARD;
import static com.phuclq.student.utils.ActivityConstants.LIKE;

@Service
@Transactional
public class FileServiceImpl implements FileService {

  private static List<RequestFileDTO> dtos;
  private final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
  @Autowired
  private FileRepository fileRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private FilePriceRepository filePriceRepository;
  @Autowired
  private AttachmentRepository attachmentRepository;
  @Autowired
  private UserCoinRepository userCoinRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private UserHistoryRepository userHistoryRepository;
  @Autowired
  private UserHistoryFileRepository userHistoryFileRepository;
  @PersistenceContext
  private EntityManager entityManager;
  @Autowired
  private IndustryRepository industryRepository;
  @Autowired
  private AttachmentService attachmentService;
  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  RateRepository rateRepository;
  @Autowired
  private UserHistoryService userHistoryService;

  @Autowired
  UserHistoryCoinRepository userHistoryCoinRepository;

  @Value("${coin.vip}")
  private int coinVip;

  @Value("${coin.percent.file}")
  private int percentFile;


  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private EmailSenderService emailSenderService;


  @Override
  public Page<File> findFilesByCategory(Integer categoryId, Pageable pageable) {
    Page<File> filePage = fileRepository.findFilesByCategory(categoryId, pageable);
    return filePage;
  }

  @Override
  public FileDTO getFile(Integer id) {
    Optional<File> optionalFile = fileRepository.findById(id);
    File file = new File();
    if (optionalFile.isPresent()) {
      file = optionalFile.get();
    }
    byte[] array = null;
    try {
      array = FileUtils.readFileToByteArray(
          new java.io.File(file.getFileCut())); // Doc file theo đường dẫn và tên
    } catch (Exception e) {
      log.error("Err file > " + e.getMessage(), e);
    }
    User user = new User();
    Optional<User> userOptional = userService.findUserById(file.getAuthorId());
    if (optionalFile.isPresent()) {
      user = userOptional.get();
    }

    Optional<Category> category = categoryRepository.findById(file.getCategoryId());
    Optional<Industry> industry = industryRepository.findById(file.getIndustryId());
    FilePrice filePrice = filePriceRepository.findByFileId(file.getId());
    FileDTO fileDTO = new FileDTO(file);
    fileDTO.setAuthorName(user.getUserName());
    fileDTO.setFileId(file.getId());
    fileDTO.setCountView(file.getView() + 1);
    fileDTO.setCountDownload(file.getDowloading());
    fileDTO.setFileTitle(file.getTitle());
    fileDTO.setCategoryId(category.get().getId());
    fileDTO.setCategoryName(category.get().getCategory());
    fileDTO.setIndustryName(industry.get().getValue());
    fileDTO.setFileBase64(array);
    fileDTO.setFilePrice(filePrice.getPrice());
    int countView = file.getView() + 1;
    optionalFile.get().setView(countView);
    fileRepository.save(optionalFile.get());
    return fileDTO;
  }

  @Override
  public Page<File> searchFiles(Integer category, Integer specialization, Integer school,
      String title, Boolean isVip, Float price, Pageable pageable) {
    return fileRepository.searchFiles(category, specialization, school, title, isVip, price,
        pageable);
  }

  @Override
  public File uploadFile(FileUploadRequest dto)
      throws IOException, com.itextpdf.text.DocumentException {
    Integer login = userService.getUserLogin().getId();

    if (dto.getIsVip()) {
      boolean status = registryFileVip(login);
      if (!status) {
        throw new BusinessHandleException("SS006");
      }
    }
    List<RequestFileDTO> files = dto.getFiles();

    if(Objects.nonNull(dto.getFiles())) {
      String zipB64 = zipB64(
          files.stream().filter(x -> !x.getType().equals(FileType.FILE_AVATAR.getName())).collect(
              Collectors.toList()));
      RequestFileDTO requestFileDTOZip = new RequestFileDTO();
      requestFileDTOZip.setType(FileType.FILE_ZIP.getName());
      requestFileDTOZip.setType(FileType.FILE_ZIP.getName());
      requestFileDTOZip.setName(com.phuclq.student.utils.StringUtils.getSearchableString(
          generateFileName(dto.getTitle() + FileType.FILE_ZIP.getName())).replace(" ", ""));
      requestFileDTOZip.setExtension(".zip");
      requestFileDTOZip.setContent(zipB64);
      files.add(requestFileDTOZip);

      RequestFileDTO requestFileDTO = files.stream()
          .filter(x -> x.getType().equals(FileType.FILE_UPLOAD.getName())).findFirst()
          .get();
      if (requestFileDTO.getExtension().equalsIgnoreCase(".PDF")) {
        RequestFileDTO cutFileShow = cutFileShow(dto.getStartPageNumber(), dto.getEndPageNumber(),
            requestFileDTO);
        files.add(cutFileShow);
      }
//      List<String> docs = Doc, Docx.
      if (requestFileDTO.getExtension().equalsIgnoreCase(".PDF")) {
        RequestFileDTO cutFileShow = cutFileShow(dto.getStartPageNumber(), dto.getEndPageNumber(),
            requestFileDTO);
        files.add(cutFileShow);
      }
    }
    if (Objects.isNull(dto.getId())) {
      File file = new File(login);
      BeanUtils.copyProperties(dto, file);

      //Phuc fake data
      file.setApproverId(12);
      File saveFile = fileRepository.save(file);
      Double price = dto.getFilePrice() != null ? dto.getFilePrice() : 0;
      FilePrice filePrice = new FilePrice(saveFile.getId(), price, login);
      filePriceRepository.save(filePrice);
      attachmentService.createListAttachmentsFromBase64S3(files, saveFile.getId(), login);
      userHistoryService.activateFileHistory(login, file.getId(), ActivityConstants.UPLOAD);
      return saveFile;
    } else {
      File byId = fileRepository.findById(dto.getId()).get();
      File file = updateFile(byId, dto, login);
      fileRepository.save(file);
      if (Objects.nonNull(dto.getFiles())) {
        attachmentService.createListAttachmentsFromBase64S3(files, file.getId(), login);
      }
      return file;
    }

  }

  public File updateFile(File file, FileUploadRequest dto, Integer loginId) {
    if (Objects.nonNull(dto.getTitle())) {
      file.setTitle(dto.getTitle());
    }
    if (Objects.nonNull(dto.getCategoryId())) {
      file.setCategoryId(dto.getCategoryId());
    }
    if (Objects.nonNull(dto.getIndustryId())) {
      file.setIndustryId(dto.getIndustryId());
    }
    if (Objects.nonNull(dto.getSpecializationId())) {
      file.setSpecializationId(dto.getSpecializationId());
    }
    if (Objects.nonNull(dto.getLanguageId())) {
      file.setLanguageId(dto.getLanguageId());
    }
    if (Objects.nonNull(dto.getSchoolId())) {
      file.setSchoolId(dto.getSchoolId());
    }
    if (Objects.nonNull(dto.getDescription())) {
      file.setDescription(dto.getDescription());
    }
    if (Objects.nonNull(dto.getFilePrice())) {
      FilePrice filePrice = filePriceRepository.findByFileId(dto.getId());
      filePrice.setPrice(dto.getFilePrice());
      filePrice.setLastUpdatedBy(loginId.toString());
      filePrice.setLastUpdatedDate(LocalDateTime.now());
      filePriceRepository.save(filePrice);
    }
    if (Objects.nonNull(dto.getLanguageId())) {
      file.setLanguageId(dto.getLanguageId());
    }
    return file;
  }

  @Override
  public boolean registryFileVip(Integer userId) {
    UserCoin userCoin = userCoinRepository.findByUserId(userId);
    if (userCoin != null) {
      double totalCoin = userCoin.getTotalCoin() != null ? userCoin.getTotalCoin() : 0;
      if (totalCoin > coinVip) {
        totalCoin -= coinVip;
        userCoin.setTotalCoin(totalCoin);
        userCoinRepository.save(userCoin);
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public AttachmentDTO downloadDocument(DownloadFileDTO downloadFileDTO) {
    User user = userService.getUserLogin();
    boolean passwordDefine = passwordEncoder.matches(downloadFileDTO.getPassword(),
        user.getPassword());

    userHistoryService.activateFileHistory(user.getId(), downloadFileDTO.getId(),
        ActivityConstants.DOWNLOAD);

    if (passwordDefine) {

      File file = fileRepository.findById(downloadFileDTO.getId())
          .orElseThrow(() -> new BusinessException(ExceptionUtils.REQUEST_NOT_EXIST));

      file.setDowloading(Objects.isNull(file.getDowloading()) ? 1 : file.getDowloading() + 1);
      FilePrice filePrice = filePriceRepository.findByFileId(file.getId());
      UserCoin userCoinDownload = userCoinRepository.findByUserId(user.getId());
      if (userCoinDownload != null) {
        Double fileCost = filePrice.getPrice() != null ? filePrice.getPrice() : 0;
        Double userTotalCoin =
            userCoinDownload.getTotalCoin() != null ? userCoinDownload.getTotalCoin() : 0;
        if (userTotalCoin >= fileCost) {
          userCoinDownload.setTotalCoin(userTotalCoin - fileCost);
          UserCoin userUpload = userCoinRepository.findByUserId(file.getAuthorId());

          Double costMoney = (fileCost * percentFile) / 100;
          Double totalCoinUpload =
              Objects.isNull(userUpload.getTotalCoin()) ? 0 : userUpload.getTotalCoin();
          userUpload.setTotalCoin(totalCoinUpload + costMoney);

          userCoinRepository.save(userCoinDownload);
          userCoinRepository.save(userUpload);

          historyCoin(user, file, fileCost, costMoney);

          sendMailDownload(user.getEmail());

          return attachmentService.getAttachmentByRequestIdFromS3AndTypes(file.getId(),
              Collections.singletonList(FileType.FILE_ZIP.getName())).get(0);
        } else {
          throw new BusinessHandleException("SS009");
        }
      } else {
        throw new BusinessHandleException("SS009");
      }
    } else {
      throw new BusinessHandleException("SS008");
    }
  }

  private void historyCoin(User user, File file, Double fileCost, Double costMoney) {
    // history coin
    UserHistoryCoin historyCoinDownload = new UserHistoryCoin(user.getId(), fileCost,
        HistoryCoinType.DOWNLOAD_FILE.getCode(), HistoryCoinType.DOWNLOAD_FILE.getName(),
        user.getId(), HistoryCoinType.DOWNLOAD_FILE.getType());
    userHistoryCoinRepository.save(historyCoinDownload);

    UserHistoryCoin historyCoinUpload = new UserHistoryCoin(file.getAuthorId(), costMoney,
        HistoryCoinType.DOWNLOADED_FILE.getCode(), HistoryCoinType.DOWNLOADED_FILE.getName(),
        user.getId(), HistoryCoinType.DOWNLOADED_FILE.getType());
    userHistoryCoinRepository.save(historyCoinUpload);
  }

  void sendMailDownload(String email) {
    String sub = "Tài liệu đã được tải";
    String message = "Chúc mừng bạn đã download tài liệu";
    emailSenderService.sendEmailUser(email, sub, message);
  }

  private String generateFileName(String fileName) {
    String time = Instant.now().toString();
    time = time.replace(":", "");
    time = time.replace(".", "");
    time = time.replace("T", "");
    time = time.replace("Z", "");


    return time+fileName;
  }

  @Override
  public void approveFile(Integer id) {
    File file = fileRepository.findById(id).orElseThrow(NotFoundException::new);
    file.setApproverId(userService.getUserLogin().getId());
    file.setApprovedDate(DateTimeUtils.timeNow());
    fileRepository.save(file);
  }

  @Override
  public List<CategoryHomeDTO> getCategoriesHome() {
    List<CategoryHomeDTO> categoryHomeDTOList = new ArrayList<CategoryHomeDTO>();
    List<Category> categoryList = categoryRepository.findAll();
    categoryList.forEach(category -> {
      CategoryHomeDTO categoryHomeDTO = new CategoryHomeDTO();
      categoryHomeDTO.setId(category.getId());
      categoryHomeDTO.setNameCategory(category.getCategory());
      categoryHomeDTO.setCountCategory(fileRepository.countCategoriesHome(category.getId()));
      categoryHomeDTOList.add(categoryHomeDTO);
    });

    return categoryHomeDTOList;
  }

  @Override
  public CategoryHomeFileResult filesPage(FileHomePageRequest request, Pageable pageable) {
    CategoryHomeFileResult categoryHomeFileResult = new CategoryHomeFileResult();
    Page<CategoryFilePageDTO> listCategory =
        Objects.nonNull(request.getCategoryIds()) && request.getCategoryIds().size() > 0
            ? categoryRepository.findAllByIdInFile(request.getCategoryIds(), pageable)
            : categoryRepository.findAllByIdInFile(pageable);
    List<FileHomeDoFilterDTO> listFile = new ArrayList<FileHomeDoFilterDTO>();
    User userLogin = userService.getUserLogin();
    List<UserHistoryDTO> fileHistoryHome = new ArrayList<>();
    if (Objects.nonNull(userLogin.getId())) {
      fileHistoryHome = userHistoryFileRepository.findFileHistoryHome(userLogin.getId());

    }

    List<UserHistoryDTO> finalFileHistoryHome = fileHistoryHome;
    listCategory.stream().parallel().forEach(category -> {
      FileHomeDoFilterDTO file = new FileHomeDoFilterDTO();
      file.setCategory(category.getName());
      file.setId(category.getId());
      List<FileResult> fileByCategory = searchFileInCategory(request, category.getId());
      fileByCategory.parallelStream().forEach(x -> {

        setLikeAndCard(finalFileHistoryHome, x);
      });
      file.setListFile(fileByCategory);
      listFile.add(file);
    });
    categoryHomeFileResult.setFileHomeDoFilterDTOS(listFile);
    PaginationModel paginationModel = new PaginationModel(
        listCategory.getPageable().getPageNumber(), listCategory.getPageable().getPageSize(),
        (int) listCategory.getTotalElements());
    categoryHomeFileResult.setPaginationModel(paginationModel);
    return categoryHomeFileResult;
  }

  public List<FileResult> searchFileInCategory(FileHomePageRequest request, Integer categoryIds) {
    List<Object> objList = null;

    StringBuilder sqlStatement = new StringBuilder();
    List<Object> listParam = new ArrayList<Object>();
    sqlStatement.append(
        "from file f    inner join category c on f.category_id = c.id inner join file_price fp on f.id = fp.file_id "
            + " inner join industry i on f.industry_id = i.id join attachment a on f.id = a.request_id and a.file_type = "
            + "'" + FileType.FILE_AVATAR.getName() + "'"
            + " inner join user u on f.author_id = u.id left join attachment ab on u.id = ab.request_id and ab.file_type = "
            + "'" + FileType.USER_AVATAR.getName() + "'"
            + "where f.approver_id is not null and f.is_deleted =0  ");
    sqlStatement.append(" and f.category_id = ? ");
    listParam.add(categoryIds);
    if (request.getSearch() != null && !request.getSearch().isEmpty()) {
      sqlStatement.append(" and (LOWER(f.title) like LOWER(?) ");
      sqlStatement.append(" or LOWER(i.value) like LOWER(?) ");
      sqlStatement.append(" or LOWER(u.user_name) like LOWER(?)) ");
      listParam.add("%" + request.getSearch() + "%");
      listParam.add("%" + request.getSearch() + "%");
      listParam.add("%" + request.getSearch() + "%");
    }
    if (Objects.nonNull(request.getPriceStart())) {
      sqlStatement.append(" and fp.price >= ? ");
      listParam.add(request.getPriceStart());
    }

    if (Objects.nonNull(request.getPriceEnd())) {
      sqlStatement.append(" and fp.price <= ? ");
      listParam.add(request.getPriceEnd());
    }
    if (Objects.nonNull(request.getIsVip())) {

      sqlStatement.append(" and f.is_vip = ? ");
      listParam.add(request.getIsVip());
    }

    if (Objects.nonNull(request.getOrderType())) {
      if (request.getOrderType().equals(OrderFileType.DOWNLOADS.getCode())) {
        sqlStatement.append(" order by f.dowloading ").append(request.getOrder());
      }
      if (request.getOrderType().equals(OrderFileType.FAVORITES.getCode())) {
        sqlStatement.append(" order by f.total_like ").append(request.getOrder());
      }
      if (request.getOrderType().equals(OrderFileType.PRICE.getCode())) {
        sqlStatement.append(" order by  fp.price ").append(request.getOrder());
      }
      if (request.getOrderType().equals(OrderFileType.VIEW.getCode())) {
        sqlStatement.append(" order by f.view ").append(request.getOrder());
      }
    } else {
      sqlStatement.append(" order by f.created_date desc ");
    }

    sqlStatement.append(" LIMIT ? OFFSET ?");
    listParam.add(request.getSizeFile());
    listParam.add(request.getSizeFile() * request.getPage());
    Query query = entityManager.createNativeQuery(Constants.SQL_FILE + sqlStatement);
    for (int i = 0; i < listParam.size(); i++) {
      query.setParameter(i + 1, listParam.get(i));
    }
    objList = query.getResultList();
    List<FileResult> list = new ArrayList<>();
    for (Object obj : objList) {
      FileResult result = new FileResult((Object[]) obj);
      list.add(result);
    }

    return list;

  }

  @Override
  public FileResultDto searchFileCategory(FileHomePageRequest request, Integer categoryId,
      Pageable pageable) {
    List<Object> objList = null;
    Integer loginId = userService.getUserLogin().getId();

    StringBuilder sqlStatement = new StringBuilder();
    List<Object> listParam = new ArrayList<Object>();
    sqlStatement.append(
        "from file f    inner join category c on f.category_id = c.id inner join file_price fp on f.id = fp.file_id "
            + "inner join industry i on f.industry_id = i.id join attachment a on f.id = a.request_id and a.file_type ="
            + "'" + FileType.FILE_AVATAR.getName() + "'"
            + " inner join user u on f.author_id = u.id left join attachment ab on u.id = ab.request_id and ab.file_type = "
            + "'" + FileType.USER_AVATAR.getName() + "'"
            + "where f.approver_id is not null and f.is_deleted =0  ");
    if(Objects.nonNull(categoryId)) {
      sqlStatement.append(" and f.category_id = ? ");
      listParam.add(categoryId);
    }
    if(Objects.nonNull(request.getIsApprove())&& request.getIsApprove()) {
      sqlStatement.append(" and f.approver_id is not null ");
      listParam.add(categoryId);
    }
    if(Objects.nonNull(request.getIsApprove())&& !request.getIsApprove()) {
      sqlStatement.append(" and f.approver_id is  null ");
      listParam.add(categoryId);
    }
    if (request.getSearch() != null && !request.getSearch().isEmpty()) {
      sqlStatement.append(" and (LOWER(f.title) like LOWER(?) ");
      sqlStatement.append(" or LOWER(i.value) like LOWER(?) ");
      sqlStatement.append(" or LOWER(u.user_name) like LOWER(?)) ");
      listParam.add("%" + request.getSearch() + "%");
      listParam.add("%" + request.getSearch() + "%");
      listParam.add("%" + request.getSearch() + "%");
    }
    if (Objects.nonNull(request.getPriceStart())) {
      sqlStatement.append(" and fp.price >= ? ");
      listParam.add(request.getPriceStart());
    }

    if (Objects.nonNull(request.getPriceEnd())) {
      sqlStatement.append(" and fp.price <= ? ");
      listParam.add(request.getPriceEnd());
    }
    if (Objects.nonNull(request.getIsVip())) {

      sqlStatement.append(" and f.is_vip = ? ");
      listParam.add(request.getIsVip());
    }
    if (Objects.nonNull(request.getFileId())) {
      sqlStatement.append(" and f.id = ? ");
      listParam.add(request.getFileId());
      if (Objects.nonNull(request.getIsBase64()) && request.getIsBase64()) {
        sqlStatement.append(" and f.author_id = ? ");
        listParam.add(loginId);
      }
    }

    if (Objects.nonNull(request.getOrderType())) {
      if (request.getOrderType().equals(OrderFileType.DOWNLOADS.getCode())) {
        sqlStatement.append(" order by f.dowloading " + request.getOrder());
      }
      if (request.getOrderType().equals(OrderFileType.FAVORITES.getCode())) {
        sqlStatement.append(" order by f.total_like " + request.getOrder());
      }
      if (request.getOrderType().equals(OrderFileType.PRICE.getCode())) {
        sqlStatement.append(" order by  fp.price " + request.getOrder());
      }
      if (request.getOrderType().equals(OrderFileType.VIEW.getCode())) {
        sqlStatement.append(" order by f.view " + request.getOrder());
      }
    } else {
      sqlStatement.append(" order by f.created_date desc ");
    }

    Query queryCount = entityManager.createNativeQuery(" select count(f.id) " + sqlStatement);
    for (int i = 0; i < listParam.size(); i++) {
      queryCount.setParameter(i + 1, listParam.get(i));
    }
    Integer count = ((Number) queryCount.getSingleResult()).intValue();

    sqlStatement.append(" LIMIT ? OFFSET ?");
    listParam.add(request.getSize());
    listParam.add(request.getSize() * request.getPage());
    Query query = entityManager.createNativeQuery(Constants.SQL_FILE + sqlStatement);
    for (int i = 0; i < listParam.size(); i++) {
      query.setParameter(i + 1, listParam.get(i));
    }
    objList = query.getResultList();
    List<FileResult> list = new ArrayList<>();
    for (Object obj : objList) {
      FileResult result = new FileResult((Object[]) obj);
      list.add(result);
    }

    Page<FileResult> file = new PageImpl<FileResult>(list, pageable, count);
    FileResultDto fileResultDto = new FileResultDto();
    List<UserHistoryDTO> fileHistoryHome = new ArrayList<>();
    if (Objects.nonNull(loginId)) {
      fileHistoryHome = userHistoryFileRepository.findFileHistoryHome(loginId);

    }
    List<UserHistoryDTO> finalFileHistoryHome = fileHistoryHome;
    file.stream().parallel().forEach(x -> {

      setLikeAndCard(finalFileHistoryHome, x);
      if (Objects.nonNull(request.getFileId()) && Objects.nonNull(request.getIsBase64())
          && (request.getIsBase64())) {
        List<Attachment> attachmentOptional = attachmentRepository.findAllByRequestIdAndFileTypeIn(
            x.getId(), Arrays.asList(FileType.FILE_AVATAR.getName(), FileType.FILE_UPLOAD.getName(),
                FileType.FILE_DEMO.getName()));
        x.setAttachmentOptional(attachmentOptional);
      }

      if (Objects.nonNull(request.getFileId()) && Objects.nonNull(request.getIsBase64())
          && (!request.getIsBase64())) {
        try {
          AttachmentDTO attachmentByRequestIdFromS3 = attachmentService.getAttachmentByRequestIdFromS3(
              x.getId(), FileType.FILE_CUT.getName());
          x.setAttachmentDTO(attachmentByRequestIdFromS3);

          // get comment
          List<Comment> listComment = commentRepository.findAllByRequestIdAndType(
              x.getId(),
              CommentType.COMMENT_FILE.getName());
          listComment.forEach(y->{
            y.setIsDelete(Objects.nonNull(loginId)&&y.getCreatedBy().equals(
                Objects.requireNonNull(loginId).toString()));
          });
          x.setComments(listComment);

          // get rate

          x.setTotalRate(Arrays.stream(ArrayUtils.toPrimitive(rateRepository.findAllByRequestIdAndType(x.getId(),
                  RateType.RATE_FILE.getName()).stream().map(Rate::getRate)
              .toArray(Double[]::new))).average().orElse(Double.NaN));
        } catch (IOException e) {
        }
        File byId = fileRepository.findById(x.getId()).get();
        byId.setView(Objects.isNull(byId.getView()) ? 1 : byId.getView() + 1);
        fileRepository.save(byId);
      }
    });
    fileResultDto.setList(file.getContent());
    PaginationModel paginationModel = new PaginationModel(file.getPageable().getPageNumber(),
        file.getPageable().getPageSize(), (int) file.getTotalElements());
    fileResultDto.setPaginationModel(paginationModel);
    return fileResultDto;
  }

  private void setLikeAndCard(List<UserHistoryDTO> finalFileHistoryHome, FileResult x) {
    if (finalFileHistoryHome.size() > 0) {
      List<UserHistoryDTO> collect = finalFileHistoryHome.stream()
          .filter(f -> f.getFileId().equals(x.getId())).collect(Collectors.toList());
      if (collect.size() > 0) {
        x.setIsLike(collect.stream().anyMatch(f -> f.getActivityId().equals(LIKE)));
        x.setIsCard(collect.stream().anyMatch(f -> f.getActivityId().equals(CARD)));
      }
    }
  }


  @Override
  public Page<FileApprove> getFileUnApprove(Pageable pageable) {
    Page<File> page = fileRepository.findByApproverId(null, pageable);
    List<FileApprove> list = new ArrayList<>();
    page.getContent().forEach(file -> {
      FileApprove fileApprove = new FileApprove(file);
      Optional<User> userOptional = userRepository.findById(file.getAuthorId());
      fileApprove.setAuthor(userOptional.get().getUserName());
      list.add(fileApprove);
    });
    Page<FileApprove> result = new PageImpl<FileApprove>(list, pageable, page.getTotalElements());
    return result;
  }

  @Override
  public File getFileDownload(String fileHashcode) {
    File file = fileRepository.findByFileHashcode(fileHashcode);
    return file;
  }

  public List<File> findTop8FileOrderByIdDesc() {
    return fileRepository.findTop8FileOrderByIdDesc();
  }

  @Override
  public Page<FileResult> searchfileDownloaded(Integer userId, Pageable pageable) {

    List<UserHistory> list = userHistoryRepository.findDownloadUserHistory(userId, pageable);
    List<UserHistoryFile> uFList = new ArrayList<UserHistoryFile>();
    for (UserHistory userHistory : list) {

      List<UserHistoryFile> historyFileList = userHistoryFileRepository.findByUserHisotyId(
          userHistory.getId());
      if (!historyFileList.isEmpty()) {
        UserHistoryFile historyFile = historyFileList.get(0);
        uFList.add(historyFile);
      }
    }

    List<File> fList = new ArrayList<File>();
    for (UserHistoryFile userHistoryFile : uFList) {
      Optional<File> optional = fileRepository.findById(userHistoryFile.getFileId());
      if (optional.isPresent()) {
        File file = optional.get();
        fList.add(file);
      }
    }

    List<FileResult> lst = new ArrayList<>();
    for (File obj : fList) {
      FileResult result = new FileResult();
      result.setTitle(obj.getTitle());
      result.setId(obj.getId());
      result.setFileHashCode(obj.getFileHashcode());
      lst.add(result);
    }

    Page<FileResult> pageTotal = new PageImpl<FileResult>(lst, pageable, lst.size());
    return pageTotal;
  }

  @Scheduled(fixedRate = 2 * 60 * 1000)
  @Transactional
  public void jobCompareFile(){
    List<Attachment> allByCodeFileNotNull = attachmentRepository.findAllByCodeFileNotNull();
    log.info("total file {}",allByCodeFileNotNull.size());
    allByCodeFileNotNull.forEach(x -> {
          Optional<Attachment> first = allByCodeFileNotNull.stream()
              .filter(i -> i.getCodeFile().equals(x.getCodeFile()) && !x.getId().equals(i.getId())).findFirst();
          if (first.isPresent()) {
            log.info("file is duplicate is {}",first);
            File file = fileRepository.findById(x.getRequestId()).orElseThrow(() -> new BusinessException(
                ExceptionUtils.ATTACHMENT_NOT_EXIST));
            assert file != null;
            file.setIsDuplicate(true);
            file.setFileDuplicate(first.get().getRequestId());

          }
        });
  }
public RequestFileDTO cutFileShow(Integer startPageNumber,Integer endPageNumber, RequestFileDTO dto)
    throws IOException, com.itextpdf.text.DocumentException {

  PdfReader reader = new PdfReader(Base64.decodeBase64(dto.getContent().split(Constants.DOT_COMMA_2)[1]));
  ByteArrayOutputStream baos = new ByteArrayOutputStream();
  PdfStamper stamper = new PdfStamper(reader, baos);
  int n = reader.getNumberOfPages();
  int endPage = endPageNumber> n ? n: endPageNumber;
  String viewPage = startPageNumber+"-"+endPage;
  reader.selectPages(viewPage);
  stamper.close();
  String base64cutFile = com.itextpdf.text.pdf.codec.Base64.encodeBytes(baos.toByteArray());
  RequestFileDTO requestFileDTO = new RequestFileDTO();
  requestFileDTO.setContent(dto.getContent().split(Constants.DOT_COMMA_2)[0]+Constants.DOT_COMMA_2+base64cutFile.replace("\n",""));
  requestFileDTO.setType(FileType.FILE_CUT.getName());
  requestFileDTO.setName(FileType.FILE_CUT.getName()+dto.getName());
  requestFileDTO.setExtension(dto.getExtension());

  return requestFileDTO;
}


  private static String zipB64(List<RequestFileDTO> dto) throws IOException {
    List<java.io.File> files = convertBase64toFile(dto);
    return  filesZip(files);
  }
  static List<java.io.File> convertBase64toFile(List<RequestFileDTO> dtos){
    List<java.io.File> files = new ArrayList<>();
    dtos.forEach(x->{
      try {
        java.io.File file = convertMultiPartToFile(uploadFile(x.getContent(),x.getName()));
        files.add(file);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    return files;

  }
  private static java.io.File convertMultiPartToFile(MultipartFile file) throws IOException {
    java.io.File convFile = new java.io.File(file.getOriginalFilename());
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();
    return convFile;
  }
  public static MultipartFile uploadFile(String base64, String fileName) {
    final String[] base64Array = base64.split(",");
    String dataUir, data;
    if (base64Array.length > 1) {
      dataUir = base64Array[0];
      data = base64Array[1];
    } else {
      //Build according to the specific file you represent
      dataUir = "data:image/jpg;base64";
      data = base64Array[0];
    }

    return new Base64ToMultipartFile(dataUir+","+data, dataUir,fileName);
  }


  static String filesZip(List<java.io.File> files) throws IOException {

    FileOutputStream  fos = new FileOutputStream("myFile.zip");
    ByteArrayOutputStream bo = new ByteArrayOutputStream();
    bo.writeTo(fos);


    ZipOutputStream zipOut= new ZipOutputStream(bo);

    for(java.io.File xlsFile:files){
      if(!xlsFile.isFile())continue;
      ZipEntry zipEntry = new ZipEntry(xlsFile.getName());
      zipOut.putNextEntry(zipEntry);
      zipOut.write(IOUtils.toByteArray(new FileInputStream(xlsFile)));
      zipOut.closeEntry();
    }
    zipOut.setComment("myFile.zip");
    zipOut.close();
    return "data:application/x-zip-compressed;base64,"+new String(Base64.encodeBase64(bo.toByteArray()));
  }



}

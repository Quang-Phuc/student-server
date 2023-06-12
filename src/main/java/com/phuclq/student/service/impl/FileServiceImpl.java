package com.phuclq.student.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.phuclq.student.service.AttachmentService;
import com.phuclq.student.service.UserHistoryService;
import com.phuclq.student.types.FileType;
import com.phuclq.student.types.OrderFileType;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.phuclq.student.domain.*;
import com.phuclq.student.dto.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Storage;
import com.phuclq.student.constant.ErrorCode;
import com.phuclq.student.exception.BusinessException;
import com.phuclq.student.exception.NotFoundException;
import com.phuclq.student.repository.CategoryRepository;
import com.phuclq.student.repository.FilePriceRepository;
import com.phuclq.student.repository.FileRepository;
import com.phuclq.student.repository.IndustryRepository;
import com.phuclq.student.repository.UserCoinRepository;
import com.phuclq.student.repository.UserHistoryFileRepository;
import com.phuclq.student.repository.UserHistoryRepository;
import com.phuclq.student.repository.UserRepository;
import com.phuclq.student.service.FileService;
import com.phuclq.student.service.UserService;
import com.phuclq.student.utils.ActivityConstants;
import com.phuclq.student.utils.DateTimeUtils;

import static com.phuclq.student.utils.ActivityConstants.CARD;
import static com.phuclq.student.utils.ActivityConstants.LIKE;

@Service
@Transactional
public class FileServiceImpl implements FileService {

  private final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

  @Autowired
  private FileRepository fileRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private FilePriceRepository filePriceRepository;

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
  private UserHistoryService userHistoryService;


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
//    fileDTO.setUrlImage(file.getImage());
//    fileDTO.setFileLink(file.getFileCut());
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
  public File uploadFile(FileUploadRequest fileUploadRequest) throws IOException {

    Integer login = userService.getUserLogin().getId();
    File file = new File(login);
    BeanUtils.copyProperties(fileUploadRequest, file);

    boolean error = false;
    if (file.getIsVip()) {
      boolean status = registryFileVip(file);
      if (!status) {
        error = true;
        throw new BusinessException(ErrorCode.ERROR_NOT_ENOUGH_COIN,
            ErrorCode.ERROR_NOT_ENOUGH_COIN_MESSAGE);
      }
    }

    if (!error) {
      File saveFile = fileRepository.save(file);
      Double price = fileUploadRequest.getFilePrice() != null ? fileUploadRequest.getFilePrice() : 0;
      FilePrice filePrice = new FilePrice(saveFile.getId(), price);
      filePriceRepository.save(filePrice);
      attachmentService.createListAttachmentsFromBase64S3(fileUploadRequest.getFiles(),saveFile.getId(),login);
      userHistoryService.activateFileHistory(login, file.getId(), ActivityConstants.UPLOAD);
      return saveFile;
    } else {
      return null;
    }

  }

  @Override
  public File updateFile(FileUploadRequest fileUploadRequest, Storage storage, String bucketName)
      throws IOException {
    Optional<File> optionalCurentFile = fileRepository.findById(fileUploadRequest.getId());
    File newfile = optionalCurentFile.get();
    BeanUtils.copyProperties(fileUploadRequest, newfile);
    File curentFile = optionalCurentFile.get();
    BeanUtils.copyProperties(fileUploadRequest, newfile);
    boolean isFileUpdate = false;
    try (InputStream is = fileUploadRequest.getFile().getInputStream()) {
      String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(is);
//      if (!curentFile.getFileHashcode().equals(md5)) {
//        newfile = upload(fileUploadRequest, newfile, true);
//        isFileUpdate = true;
//      }
    }

    boolean error = false;
    if (!curentFile.getIsVip()) {
      if (newfile.getIsVip()) {
        boolean status = registryFileVip(newfile);
        if (!status) {
          error = true;
          throw new BusinessException(ErrorCode.ERROR_NOT_ENOUGH_COIN,
              ErrorCode.ERROR_NOT_ENOUGH_COIN_MESSAGE);
        }
      }
    }
    newfile.setDowloading(0);
    if (!error) {
      File saveFile = fileRepository.save(newfile);

      // set file price
      Double price =
          fileUploadRequest.getFilePrice() != null ? fileUploadRequest.getFilePrice() : 0;
      FilePrice filePrice = filePriceRepository.findByFileId(saveFile.getId());
      filePrice.setPrice(price);
      filePriceRepository.save(filePrice);

      // save to history
      UserHistory userHistory = new UserHistory(saveFile.getAuthorId(),
          ActivityConstants.UPDATE_FILE);
      UserHistory saveUserHistory = userHistoryRepository.save(userHistory);
      Instant instant = Instant.now();
      Timestamp timestamp = Timestamp.from(instant);
      UserHistoryFile userHistoryFile = new UserHistoryFile(saveUserHistory.getId(),
          saveFile.getId(), timestamp);
      userHistoryFile.setIsFileUpdate(isFileUpdate);
      userHistoryFileRepository.save(userHistoryFile);
      return saveFile;
    } else {
      return null;
    }

  }

  private File upload(FileUploadRequest fileUploadRequest, File newfile, boolean isUpdate)
      throws IOException {
    // upload file
    MultipartFile fileUpload = fileUploadRequest.getFile();



//    try (InputStream is = fileUpload.getInputStream()) {
//      String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(is);
//      newfile.setFileHashcode(md5);
//      if (!isUpdate) {
//        File fileCompare = fileRepository.findByFileHashcode(md5);
//        if (fileCompare != null) {
//          throw new BusinessException(ErrorCode.ERROR_FILE_EXISTS,
//              ErrorCode.ERROR_FILE_EXISTS_MESSAGE);
//        }
//      }
//    }
//    byte[] bytes = fileUpload.getBytes();
//    Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
//    Path path = Paths.get(root.toString(), "src", "main", "resources", "upload",
//        fileUpload.getOriginalFilename());
//    Files.write(path, bytes);
//    newfile.setFile(path.toString());
//
//    // upload file image
//    MultipartFile fileImage = fileUploadRequest.getFileImage();
//    bytes = fileImage.getBytes();
//    path = Paths.get(root.toString(), "src", "main", "resources", "upload",
//        fileImage.getOriginalFilename());
//    Files.write(path, bytes);
//    newfile.setImage(path.toString());
//
//    // upload file attachment
//    if (fileUploadRequest.getAttachment() != null) {
//      MultipartFile fileAttachment = fileUploadRequest.getAttachment();
//      bytes = fileAttachment.getBytes();
//      path = Paths.get(root.toString(), "src", "main", "resources", "upload",
//          fileAttachment.getOriginalFilename());
//      Files.write(path, bytes);
//      newfile.setAttachment(path.toString());
//    }

    return newfile;
  }

  @Override
  public boolean registryFileVip(File file) {
    UserCoin userCoin = userCoinRepository.findByUserId(file.getAuthorId());
    if (userCoin != null) {
      Double totalCoin = userCoin.getTotalCoin() != null ? userCoin.getTotalCoin() : 0;
      if (totalCoin > 20) {
        totalCoin -= 20;
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
  public File downloadDocument(Integer fileId, User user) {
    Optional<File> fileOptional = fileRepository.findById(fileId);

    if (!fileOptional.isPresent()) {
      return null;
    } else {
      File file = fileOptional.get();
      FilePrice filePrice = filePriceRepository.findByFileId(file.getId());
      UserCoin userCoin = userCoinRepository.findByUserId(user.getId());
      if (userCoin != null) {
        Double fileCost = filePrice.getPrice() != null ? filePrice.getPrice() : 0;
        Double userTotalCoin = userCoin.getTotalCoin() != null ? userCoin.getTotalCoin() : 0;
        if (userTotalCoin >= fileCost) {
          userCoin.setTotalCoin(userTotalCoin - fileCost);
          userCoinRepository.save(userCoin);
          return file;
        } else {
          return null;
        }
      } else {
        return null;
      }
    }
  }

  private String generateFileName(String fileName) {
    fileName = fileName.replace(".", ";;");
    String time = Instant.now().toString();
    time = time.replace(":", "-");
    time = time.replace(".", "-");
    time = time.replace("T", "");
    time = time.replace("Z", "");
    String[] fileNameArr = fileName.split(";;");
    String newFileName = "";
    if (fileNameArr.length >= 2) {
      newFileName = fileNameArr[0] + "-" + time + "." + fileNameArr[1];
    }

    return newFileName;
  }

  @Override
  public void approverFile(Integer approverId, Integer id, MultipartFile fileCut, Storage storage,
      String bucketName) throws IOException {
    File file = fileRepository.findById(id).get();
    User userApprove = userRepository.findById(approverId).get();
    if (file == null || userApprove == null) {
      throw new NotFoundException("Do not find file or user by id");
    }

    String fileUploadName = fileCut.getOriginalFilename();
    Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
    byte[] bytes = fileCut.getBytes();
    Path path = Paths.get(root.toString(), "src", "main", "resources", "upload", fileUploadName);
    Files.write(path, bytes);
    String fileCutLink = path.toString();

    file.setApproverId(approverId);
    file.setApprovedDate(DateTimeUtils.timeNow());
    file.setFileCut(fileCutLink);
    File fileSave = fileRepository.save(file);
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
    Page<Category> listCategory =
        Objects.nonNull(request.getCategoryIds()) && request.getCategoryIds().size()>0 ? categoryRepository.findAllByIdIn(
            request.getCategoryIds(),pageable) : categoryRepository.findAll(pageable);
    List<FileHomeDoFilterDTO> listFile = new ArrayList<FileHomeDoFilterDTO>();
    User userLogin = userService.getUserLogin();
    List<UserHistoryDTO> fileHistoryHome = new ArrayList<>();
    if(Objects.nonNull(userLogin.getId())){
       fileHistoryHome = userHistoryFileRepository.findFileHistoryHome(userLogin.getId());

    }

    List<UserHistoryDTO> finalFileHistoryHome = fileHistoryHome;
    listCategory.stream().parallel().forEach(category -> {
      FileHomeDoFilterDTO file = new FileHomeDoFilterDTO();
      file.setCategory(category.getCategory());
      file.setId(category.getId());
      List<FileResult> fileByCategory = searchFileInCategory(request,category.getId());
      fileByCategory.parallelStream().forEach(x->{

        if(finalFileHistoryHome.size()>0 ){
          List<UserHistoryDTO> collect = finalFileHistoryHome.stream().filter(f -> f.getFileId().equals(x.getId())).collect(Collectors.toList());
          if(collect.size()>0){
            x.setIsLike(collect.stream().anyMatch(f -> f.getActivityId().equals(LIKE)));
            x.setIsCard(collect.stream().anyMatch(f -> f.getActivityId().equals(CARD)));
          }
        }
      });
      file.setListFile(fileByCategory);
      listFile.add(file);
    });
    categoryHomeFileResult.setFileHomeDoFilterDTOS(listFile);
    PaginationModel paginationModel = new PaginationModel(listCategory.getPageable().getPageNumber(), listCategory.getPageable().getPageSize(), (int) listCategory.getTotalElements());
    categoryHomeFileResult.setPaginationModel(paginationModel);
    return categoryHomeFileResult;
  }

  public List<FileResult> searchFileInCategory(FileHomePageRequest request,Integer categoryIds) {
    List<Object> objList = null;

    StringBuilder sqlStatement = new StringBuilder();
    List<Object> listParam = new ArrayList<Object>();
    sqlStatement.append(
        "from file f    inner join category c on f.category_id = c.id inner join file_price fp on f.id = fp.file_id "
            + " inner join industry i on f.industry_id = i.id join attachment a on f.id = a.request_id and a.file_type = "+"'"+FileType.FILE_AVATAR.getName()+"'"
            + " inner join user u on f.author_id = u.id " + "where f.approver_id is not null ");
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

    if(Objects.nonNull(request.getOrderType())){
      if(request.getOrderType().equals(OrderFileType.DOWNLOADS.getCode())){
        sqlStatement.append(" order by f.dowloading "+request.getOrder());
      }
      if(request.getOrderType().equals(OrderFileType.FAVORITES.getCode())){
        sqlStatement.append(" order by f.total_like "+request.getOrder());
      }
      if(request.getOrderType().equals(OrderFileType.PRICE.getCode())){
        sqlStatement.append(" order by  fp.price "+request.getOrder());
      }
      if(request.getOrderType().equals(OrderFileType.VIEW.getCode())){
        sqlStatement.append(" order by f.view "+request.getOrder());
      }
    }else {
      sqlStatement.append(" order by f.created_date desc ");
    }

    sqlStatement.append(" LIMIT ? OFFSET ?");
    listParam.add(request.getSizeFile());
    listParam.add(request.getSizeFile() * request.getPage());
    Query query = entityManager.createNativeQuery(
        " select f.id as id, f.title as title, f.view as view, f.dowloading as download, fp.price as price "
            + "    		, a.url as image, date_format(f.created_date, '%d/%m/%Y') as createDate,f.total_comment as totalComment,c.category as category,f.total_like as  totalLike , f.is_like as isLike,f.is_Card as isCard, f.is_vip as isVip,c.id as categoryId  "
            + sqlStatement);
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

    StringBuilder sqlStatement = new StringBuilder();
    List<Object> listParam = new ArrayList<Object>();
    sqlStatement.append(
        "from file f    inner join category c on f.category_id = c.id inner join file_price fp on f.id = fp.file_id "
            + "inner join industry i on f.industry_id = i.id join attachment a on f.id = a.request_id and a.file_type ="+"'"+FileType.FILE_AVATAR.getName()+"'"
            + " inner join user u on f.author_id = u.id " + "where f.approver_id is not null ");
    sqlStatement.append(" and f.category_id = ? ");
    listParam.add(categoryId);
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

    if(Objects.nonNull(request.getOrderType())){
      if(request.getOrderType().equals(OrderFileType.DOWNLOADS.getCode())){
        sqlStatement.append(" order by f.dowloading "+request.getOrder());
      }
      if(request.getOrderType().equals(OrderFileType.FAVORITES.getCode())){
        sqlStatement.append(" order by f.total_like "+request.getOrder());
      }
      if(request.getOrderType().equals(OrderFileType.PRICE.getCode())){
        sqlStatement.append(" order by  fp.price "+request.getOrder());
      }
      if(request.getOrderType().equals(OrderFileType.VIEW.getCode())){
        sqlStatement.append(" order by f.view "+request.getOrder());
      }
    }else {
      sqlStatement.append(" order by f.created_date desc ");
    }



    Query queryCount = entityManager.createNativeQuery(
        " select count(f.id) " + sqlStatement);
    for (int i = 0; i < listParam.size(); i++) {
      queryCount.setParameter(i + 1, listParam.get(i));
    }
    Integer count = ((Number) queryCount.getSingleResult()).intValue();

    sqlStatement.append(" LIMIT ? OFFSET ?");
    listParam.add(request.getSize());
    listParam.add(request.getSize() * request.getPage());
    Query query = entityManager.createNativeQuery(
        " select f.id as id, f.title as title, f.view as view, f.dowloading as download, fp.price as price "
            + "    		, a.url as image, date_format(f.created_date, '%d/%m/%Y') as createDate,f.total_comment as totalComment,c.category as category,f.total_like as  totalLike , f.is_like as isLike,f.is_Card as isCard, f.is_vip as isVip,c.id as categoryId  "
            + sqlStatement);
    for (int i = 0; i < listParam.size(); i++) {
      query.setParameter(i + 1, listParam.get(i));
    }
    objList = query.getResultList();
    List<FileResult> list = new ArrayList<>();
    for (Object obj : objList) {
      FileResult result = new FileResult((Object[]) obj);
      list.add(result);
    }

    Page<FileResult> listCategory = new PageImpl<FileResult>(list, pageable, count);
    FileResultDto fileResultDto = new FileResultDto();
    User userLogin = userService.getUserLogin();
    List<UserHistoryDTO> fileHistoryHome = new ArrayList<>();
    if(Objects.nonNull(userLogin.getId())){
      fileHistoryHome = userHistoryFileRepository.findFileHistoryHome(userLogin.getId());

    }
    List<UserHistoryDTO> finalFileHistoryHome = fileHistoryHome;
    listCategory.stream().parallel().forEach(x->{

        if(finalFileHistoryHome.size()>0 ){
          List<UserHistoryDTO> collect = finalFileHistoryHome.stream().filter(f -> f.getFileId().equals(x.getId())).collect(Collectors.toList());
          if(collect.size()>0){
            x.setIsLike(collect.stream().anyMatch(f -> f.getActivityId().equals(LIKE)));
            x.setIsCard(collect.stream().anyMatch(f -> f.getActivityId().equals(CARD)));
          }
        }
      });
    fileResultDto.setList(listCategory.getContent());
    PaginationModel paginationModel = new PaginationModel(listCategory.getPageable().getPageNumber(), listCategory.getPageable().getPageSize(), (int) listCategory.getTotalElements());
    fileResultDto.setPaginationModel(paginationModel);
    return fileResultDto;
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


}

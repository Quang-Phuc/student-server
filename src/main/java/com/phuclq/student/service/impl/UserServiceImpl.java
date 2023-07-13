package com.phuclq.student.service.impl;

import com.phuclq.student.dao.UsersDao;
import com.phuclq.student.domain.File;
import com.phuclq.student.domain.User;
import com.phuclq.student.dto.*;
import com.phuclq.student.exception.BusinessHandleException;
import com.phuclq.student.repository.UserRepository;
import com.phuclq.student.service.AttachmentService;
import com.phuclq.student.service.CaptchaService;
import com.phuclq.student.service.ConfirmationTokenService;
import com.phuclq.student.service.EmailSenderService;
import com.phuclq.student.service.UserService;
import com.phuclq.student.types.FileType;
import com.phuclq.student.utils.DateTimeUtils;
import com.phuclq.student.utils.StringUtils;
import java.io.IOException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ConfirmationTokenService confirmationTokenService;

  @Autowired
  private EmailSenderService emailSenderService;

  @Autowired
  private CaptchaService captchaService;

  @Autowired
  private AttachmentService attachmentService;
  @Autowired
  UsersDao usersDao;

  @Override
  public User registryUser(UserAccountDTO accountDTO) {

    User existingUser = findUserByEmail(accountDTO.getEmail());
    if (existingUser != null) {
      throw new BusinessHandleException("SS002");
    }
    User user = new User();
    BeanUtils.copyProperties(accountDTO, user);
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String hashedPassword = passwordEncoder.encode(accountDTO.getPassword());
    user.setPassword(hashedPassword);
    user.setIsEnable(false);
    user.setIsDeleted(false);
    user.setRoleId(2);
    user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
    userRepository.save(user);
    User saveUser = userRepository.findByEmailIgnoreCaseAndIsDeleted(user.getEmail(), false);
    confirmationTokenService.sendEmailRegister(user);
    return saveUser;
  }

  @Override
  public User findUserByEmail(String email) {
    return userRepository.findByEmailIgnoreCaseAndIsDeleted(email, false);
  }

  @Override
  public Page<UserDTO> getUser(Pageable pageable) {
    Page<UserResult> page = userRepository.findUserResult(pageable);
    List<UserDTO> list = new ArrayList<UserDTO>();
    page.getContent().forEach(user -> {
      UserDTO userDTO = new UserDTO();
      userDTO.setId(user.getId());
      userDTO.setUserName(user.getUser_name());
      //userDTO.setPassword(user.getPassword());
      userDTO.setEmail(user.getEmail());
      userDTO.setPhone(user.getPhone());
      userDTO.setRoleId(user.getRole_id());
      userDTO.setTotalCoin(user.getTotal_coin());
      list.add(userDTO);
    });
    return new PageImpl<UserDTO>(list, pageable, page.getTotalElements());
  }

  @Override
  public UserResultDto getUser2(FileHomePageRequest request,Pageable pageable) {
    Page<UserAdminResult> fileResultDto = usersDao.listUserAdmin(request, pageable);
    UserResultDto userResultDto = new UserResultDto();
    userResultDto.setList(fileResultDto.getContent());
    PaginationModel paginationModel = new PaginationModel(
        fileResultDto.getPageable().getPageNumber(), fileResultDto.getPageable().getPageSize(),
        (int) fileResultDto.getTotalElements());
    userResultDto.setPaginationModel(paginationModel);
    return userResultDto;
  }

  @Override
  public Optional<User> findUserById(Integer id) {
    return userRepository.findById(id);
  }

  @Override
  public User deleteUser(Integer Id) {
    User userDelete = userRepository.findById(Id).get();
    userDelete.setIsDeleted(true);
    User userSave = userRepository.save(userDelete);
    return userSave;
  }

  @Override
  public User getUserLogin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = new User();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String email = authentication.getName();
      user = userRepository.findUserByEmail(email);

    }
    return user;
  }

  @Override
  public Page<File> getListFileByUser(Integer userId, Pageable pageable) {
    return userRepository.findFilesByUser(userId, pageable);
  }

  @Override
  public Page<User> findUserByUserNameAndEmailAndPhone(UsersSearchRequest usersSearchRequest,
      Pageable pageable) {
    Date dateFrom = null;
    Date dateTo = null;
    String dateFromStr = usersSearchRequest.getStartDate();
    String dateToStr = usersSearchRequest.getEndDate();
    Page<User> userPage = null;

    boolean hasDateFrom = StringUtils.isStringNotNullAndHasValue(dateFromStr);
    boolean hasDateTo = StringUtils.isStringNotNullAndHasValue(dateToStr);

    if (hasDateFrom && hasDateTo) {
      dateFrom = DateTimeUtils.convertStringDateYYmmdd(dateFromStr);
      dateTo = DateTimeUtils.convertStringDateYYmmdd(dateToStr);
      userPage = userRepository.findUserByUserNameAndEmailAndTime

          (usersSearchRequest.getUserName(), usersSearchRequest.getEmail(),
              usersSearchRequest.getPhone(), dateFrom, dateTo, pageable);
    } else {
      userPage = userRepository.findUserByUserNameAndEmail(usersSearchRequest.getUserName(),
          usersSearchRequest.getEmail(), usersSearchRequest.getPhone(), pageable);
    }

    return userPage;
  }

  @Override
  public void forgotPassword(String email) {
    String pass = RandomStringUtils.randomAlphanumeric(8);
    User existingUser = userRepository.findByEmailIgnoreCaseAndIsDeleted(email, false);
    if (existingUser == null) {
      return;
    }
    existingUser.setPassword(passwordEncoder.encode(pass));
    userRepository.save(existingUser);
    String message = "Please check your email password account: " + pass;
    String sub ="Thông tin passowrd ";
    SimpleMailMessage mailMessage = emailSenderService.sendEmailUser(existingUser.getEmail(),sub, message);
    emailSenderService.sendEmail(mailMessage);
  }

  @Override
  public boolean changePassword(String password, String passwordNew, String passwordConfirm) {
    User user = getUserLogin();
    boolean passwordDefine = passwordEncoder.matches(password, user.getPassword());
    if (passwordDefine && passwordNew.equals(passwordConfirm)) {
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String hashedPassword = passwordEncoder.encode(passwordNew);
      user.setPassword(hashedPassword);
      userRepository.save(user);
      return true;
    }
    throw new BusinessHandleException("SS008");

  }

  @Override
  public User createAdmin(UserAccountDTO accountDTO) {
    User user = new User();
    BeanUtils.copyProperties(accountDTO, user);
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String hashedPassword = passwordEncoder.encode(accountDTO.getPassword());
    user.setPassword(hashedPassword);
    user.setIsEnable(false);
    user.setIsDeleted(false);
    user.setRoleId(1);
    userRepository.save(user);
    User saveUser = userRepository.findByEmailIgnoreCaseAndIsDeleted(user.getEmail(), false);
    return saveUser;
  }

  @Override
  public void updateAdmin(UserAccountDTO accountDTO) {
    User user = userRepository.findUserByEmail(accountDTO.getEmail());
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String hashedPassword = passwordEncoder.encode(accountDTO.getPassword());
    user.setPassword(hashedPassword);
    if (StringUtils.isStringNotNullAndHasValue(accountDTO.getUserName())) {
      user.setUserName(accountDTO.getUserName());
    }
    if (StringUtils.isStringNotNullAndHasValue(accountDTO.getPhone())) {
      user.setPhone(accountDTO.getPhone());
    }
    userRepository.save(user);

  }

  @Override
  public UserDTO getUserResultLogin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserResult userResult = null;
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String email = authentication.getName();
      userResult = userRepository.findUserResultByEmail(email,FileType.USER_AVATAR.getName());

    }
    UserDTO user = new UserDTO();
    assert userResult != null;
    user.setId(userResult.getId());
    user.setUserName(userResult.getUser_name());
    user.setEmail(userResult.getEmail());
    user.setPhone(userResult.getPhone());
    user.setRoleId(userResult.getRole_id());
    user.setTotalCoin(userResult.getTotal_coin());
    user.setBirthDay(userResult.getBirthDay());
    user.setFullName(userResult.getFullName());
    user.setGender(userResult.getGender());
    user.setIntroduction(userResult.getIntroduction());
    user.setIndustryId(userResult.getIndustryId());
    user.setAddress(userResult.getAddress());
    user.setFullName(userResult.getFullName());
    user.setImage(userResult.getImage());
    user.setRole(authentication.getAuthorities());
    return user;
  }

  @Override
  public List<UserInfoDTO> getUserInfos() {
    List<UserInfoResult> userInfoResults = userRepository.findUserInfoResult();
    List<UserInfoDTO> userInfoDTOs = new ArrayList<UserInfoDTO>();
    userInfoResults.forEach(userInfoResult -> {
      UserInfoDTO userDto = new UserInfoDTO();
      BeanUtils.copyProperties(userInfoResult, userDto);
      userInfoDTOs.add(userDto);
    });
    return userInfoDTOs;
  }

  @Override
  public List<UserDTO> getUsersByRole(Integer roleId) {
    List<User> userList = userRepository.findUserByRoleIdAndIsDeleted(roleId, false);
    List<UserDTO> userDtoList = userList.stream().map(user -> new UserDTO(user))
        .collect(Collectors.toList());
    return userDtoList;
  }

  @Override
  public List<UserInfoResult> findTop10OrderByIdDesc() {
    return userRepository.findTop10OrderByIdDesc(FileType.USER_AVATAR.getName());

  }

  @Override
  public UserDTO changeRole(AdminRoleDTO adminRoleDTO) {
    User user = userRepository.getOne(adminRoleDTO.getUserId());
    if (user == null) {
      return null;
    }
    user.setRoleId(adminRoleDTO.getRoleId());
    user = userRepository.save(user);
    return new UserDTO(user);
  }

  @Override
  public User save(UserSaveDTO accountDTO) throws IOException {
    User userLogin = userRepository.findById(getUserLogin().getId()).get();
    setUser(accountDTO, userLogin);
    return userRepository.save(userLogin);
  }

  @Override
  public User saveAdmin(UserSaveDTO accountDTO) throws IOException {
    User userLogin = userRepository.findById(accountDTO.getId()).get();
    setUser(accountDTO, userLogin);
    return userRepository.save(userLogin);
  }

  private void setUser(UserSaveDTO accountDTO, User userLogin) throws IOException {
    if (Objects.nonNull(accountDTO.getBirthDay())) {
      userLogin.setBirthDay(accountDTO.getBirthDay());
    }
    if (Objects.nonNull(accountDTO.getFullName())) {
      userLogin.setFullName(accountDTO.getFullName().trim());
    }
    if (Objects.nonNull(accountDTO.getGender())) {
      userLogin.setGender(accountDTO.getGender());
    }
    if (Objects.nonNull(accountDTO.getAddress())) {
      userLogin.setAddress(accountDTO.getAddress().trim());
    }
    if (Objects.nonNull(accountDTO.getIndustryId())) {
      userLogin.setIndustryId(accountDTO.getIndustryId());
    }
    if (Objects.nonNull(accountDTO.getIntroduction())) {
      userLogin.setIntroduction(accountDTO.getIntroduction().trim());
    }
    if (Objects.nonNull(accountDTO.getFiles())) {
      Long listAttachmentsFromBase64S3 = attachmentService.createListAttachmentsFromBase64S3(
          accountDTO.getFiles(), userLogin.getId(), userLogin.getId());
    }
  }
}

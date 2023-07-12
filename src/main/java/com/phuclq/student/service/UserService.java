package com.phuclq.student.service;

import com.phuclq.student.domain.File;
import com.phuclq.student.domain.User;
import com.phuclq.student.dto.AdminRoleDTO;
import com.phuclq.student.dto.FileHomePageRequest;
import com.phuclq.student.dto.UserAccountDTO;
import com.phuclq.student.dto.UserDTO;
import com.phuclq.student.dto.UserInfoDTO;
import com.phuclq.student.dto.UserInfoResult;
import com.phuclq.student.dto.UserResultDto;
import com.phuclq.student.dto.UserSaveDTO;
import com.phuclq.student.dto.UsersSearchRequest;
import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registryUser(UserAccountDTO accountDTO);
    User save(UserSaveDTO accountDTO) throws IOException;
    User saveAdmin(UserSaveDTO accountDTO) throws IOException;

    User findUserByEmail(String email);
    
    Page<UserDTO> getUser(Pageable pageable);
    UserResultDto getUser2(FileHomePageRequest request,Pageable pageable);

    Optional<User> findUserById(Integer Id);

    User deleteUser(Integer Id);

    User getUserLogin();

    Page<File> getListFileByUser(Integer userId, Pageable pageable);

    Page<User> findUserByUserNameAndEmailAndPhone(UsersSearchRequest usersSearchRequest, Pageable pageable);

    void forgotPassword(String email);
    
    boolean changePassword(String password, String passwordNew,String passwordConfirm);
    
    User createAdmin(UserAccountDTO accountDTO);
    
    void updateAdmin(UserAccountDTO accountDTO);
    
    UserDTO getUserResultLogin();
    
    List<UserInfoDTO> getUserInfos();
    
    List<UserDTO> getUsersByRole(Integer roleId);
    
    UserDTO changeRole(AdminRoleDTO adminRoleDTO);

    List<UserInfoResult> findTop10OrderByIdDesc();
    
}

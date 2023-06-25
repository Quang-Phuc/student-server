package com.phuclq.student.controller.admin;

import com.phuclq.student.component.RestEntityResponse;
import com.phuclq.student.domain.User;
import com.phuclq.student.dto.AdminRoleDTO;
import com.phuclq.student.dto.UserAccountDTO;
import com.phuclq.student.dto.UserDTO;
import com.phuclq.student.service.UserService;
import io.swagger.models.auth.In;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminUsersController {
    @Autowired
    private UserService userService;

    @Autowired
    private RestEntityResponse restEntityRes;

    @DeleteMapping("/user/register/{Id}")
    public ResponseEntity<?> registryUser(@PathVariable Integer Id) {
        User userDelete = userService.deleteUser(Id);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(userDelete).getResponse();

    }
    
    @PostMapping("/admin/add-admin")
    public ResponseEntity<?> createAdmin(@RequestBody @Valid UserAccountDTO accountDTO) throws Exception {
    	User existingUser = userService.findUserByEmail(accountDTO.getEmail());
        if (existingUser != null) {
            throw new Exception("This email already exists!");
        }
        User createAdm = userService.createAdmin(accountDTO);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(createAdm).getResponse();

    }
    
    @PutMapping("/admin/update-admin")
    public ResponseEntity<?> updateAdmin(@RequestBody @Valid UserAccountDTO accountDTO) throws Exception {
        userService.updateAdmin(accountDTO);
        return restEntityRes.setHttpStatus(HttpStatus.OK).getResponse();

    }
    
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINSYSTEM')")
    @GetMapping("/admin/get-user/{roleId}")
    public ResponseEntity<?> updateAdmin(@PathVariable("roleId") Integer roleId) {
        List<UserDTO> data = userService.getUsersByRole(roleId);
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(data).getResponse();

    }
    
    @PreAuthorize("hasRole('ADMINSYSTEM')")
    @DeleteMapping("/admin/delete/{Id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable("Id") Integer id) {
        User user = userService.deleteUser(id);
        return restEntityRes.setHttpStatus(HttpStatus.OK).getResponse();
    }
    
    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMINSYSTEM')")
    @DeleteMapping("/user/delete/{Id}")
    public ResponseEntity<?> deleteUser(@PathVariable("Id") Integer id) {
    	User user = userService.deleteUser(id);
        String message = "Xóa " + user.getUserName() + " thành công";
        return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(message).getResponse();
    }
    
    @PreAuthorize("hasRole('ADMINSYSTEM')")
    @PutMapping("/admin/change-role")
    public ResponseEntity<?> changeRole(@RequestBody AdminRoleDTO adminRoleDTO) {
    	if (adminRoleDTO == null) {
    		String message = "Có lỗi xảy ra vui lòng thử lại";
    		return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(message).getResponse();
    	}
        UserDTO user = userService.changeRole(adminRoleDTO);
        if (user == null) {
        	String message = "Có lỗi xảy ra vui lòng thử lại";
        	return restEntityRes.setHttpStatus(HttpStatus.OK).setDataResponse(message).getResponse();
        } else {
        	String message = "Cập nhật thành công";
        	return restEntityRes.setHttpStatus(HttpStatus.CREATED).setDataResponse(message).getResponse();
        }
    }
    
}

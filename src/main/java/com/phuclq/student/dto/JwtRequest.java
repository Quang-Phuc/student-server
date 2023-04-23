package com.phuclq.student.dto;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String password;

    private Boolean isDelete;

    public JwtRequest(String email, String password, Boolean isDelete) {
        this.email = email;
        this.password = password;
        this.isDelete = isDelete;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}

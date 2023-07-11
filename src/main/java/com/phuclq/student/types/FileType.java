package com.phuclq.student.types;


public enum FileType {
    FILE_AVATAR (1, "FILE_AVATAR"),
    FILE_ZIP (1, "FILE_ZIP"),

    FILE_CUT (1, "FILE_CUT"),
    FILE_DEMO(2, "FILE_DEMO"),
    FILE_UPLOAD(2, "FILE_UPLOAD"),
    USER_AVATAR(3, "USER_AVATAR");


    private final Integer code;
    private final String name;

    FileType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }
}

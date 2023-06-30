package com.phuclq.student.types;


public enum HistoryCoinType {
  DOWNLOAD_FILE(1, "Tải tài liệu", "-"), DOWNLOADED_FILE(2, "Tài liệu được tải", "+"), PAY_COIN(3,
      "Nạp tiền vào tài khoản", "+");

  private final Integer code;
  private final String name;
  private final String type;

  HistoryCoinType(Integer code, String name, String type) {
    this.code = code;
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public Integer getCode() {
    return code;
  }

  public String getType() {
    return type;
  }
}

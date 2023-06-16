package com.phuclq.student.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "file_price", schema = "smdb", catalog = "")
public class FilePrice extends Auditable<String>{
    private Integer id;
    private Integer fileId;
    private Double price;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "file_id")
    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    @Basic
    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilePrice filePrice = (FilePrice) o;

        if (id != filePrice.id) return false;
        if (fileId != null ? !fileId.equals(filePrice.fileId) : filePrice.fileId != null) return false;
        if (price != null ? !price.equals(filePrice.price) : filePrice.price != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (fileId != null ? fileId.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    public FilePrice() {
    }

    public FilePrice(Integer fileId, Double price,Integer userId) {
        this.fileId = fileId;
        this.price = price;
        this.setCreatedBy(userId.toString());
        this.setCreatedDate(LocalDateTime.now());
    }
}

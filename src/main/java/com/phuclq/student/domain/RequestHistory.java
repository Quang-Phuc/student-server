package com.phuclq.student.domain;


import com.phuclq.student.momo.shared.constants.RequestType;
import com.phuclq.student.types.RequestHistoryStatus;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "REQUEST_HISTORY")
@Getter
@Setter
@Builder
@Table(name = "REQUEST_HISTORY")
public class RequestHistory  {
    @Id
    @SequenceGenerator(name = "REQUEST_HISTORY_SEQUENCE", sequenceName = "REQUEST_HISTORY_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REQUEST_HISTORY_SEQUENCE")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column
    private Date createdTime;
    @Column
    private String url;
    @Column
    private String requestContent;
    @Column
    private String responseContent;
    @Column
    private String responseCode;
    @Column
    private String status;
    @Column
    private String type;
    private long duration;
    @Column
    private Integer requestId;


}

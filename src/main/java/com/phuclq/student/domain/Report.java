package com.phuclq.student.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "REPORT")
@Getter
@Setter
@Builder
@Table(name = "REPORT")
public class Report extends Auditable<String>{

    @Id
    @SequenceGenerator(name = "REPORT_SEQUENCE", sequenceName = "REPORT_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPORT_SEQUENCE")
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "ID_FILE", nullable = false)
    private Integer requestId;

    @Column(name = "TYPE", nullable = false)
    private String type;

    @Column(name = "CONTENT", nullable = false)
    private String content;



}

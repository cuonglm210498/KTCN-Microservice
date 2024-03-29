package com.vss.userservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreatedDate
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate createdDate;

    @CreatedBy
    @Column
    private String createdBy;

    @LastModifiedDate
    @Column(insertable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate modifiedDate;

    @LastModifiedBy
    @Column(insertable = false)
    private String modifiedBy;
}

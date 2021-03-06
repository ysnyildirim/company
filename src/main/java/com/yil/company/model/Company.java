package com.yil.company.model;

import com.yil.company.base.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMPANY")
public class Company extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "USER_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_USER_ID",
            allocationSize = 1)
    @GeneratedValue(generator = "USER_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "TITLE", nullable = false, length = 1000)
    private String title;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FOUNDATION_DATE")
    private Date foundationDate;
    @Column(name = "CONTACT_ID")
    private Long contactId;
}

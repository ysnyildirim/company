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
@Table(schema = "CMP",
        name = "COMPANY")
public class Company extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "USER_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_USER_ID")
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
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "CREATED_USER_ID")
    private Long createdUserId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFY_DATE")
    private Date lastModifyDate;
    @Column(name = "LAST_MODIFY_USER_ID")
    private Long lastModifyUserId;
}

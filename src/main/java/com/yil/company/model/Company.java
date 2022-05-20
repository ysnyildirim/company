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
@Table(name = "Company")
public class Company extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "User_Sequence_Generator",
            sequenceName = "Seq_User",
            allocationSize = 1)
    @GeneratedValue(generator = "User_Sequence_Generator")
    @Column(name = "Id")
    private Long id;
    @Column(name = "Title", nullable = false, length = 1000)
    private String title;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FoundationDate")
    private Date foundationDate;
    @Column(name = "ContactId")
    private Long contactId;
}

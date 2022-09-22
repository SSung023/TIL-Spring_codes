package jpa.jpaBookPrac.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
@Entity
@Table // (name = "MEMBER")을 따로 설정하지 않는다면, 엔티티의 이름으로 자동 설정된다.
public class Member {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME", nullable = false, length = 10)
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Grade grade;

//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;
    private LocalDateTime createdDate;

//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;
    private LocalDateTime lastModifiedDate;

    @Lob
    private String description;
}

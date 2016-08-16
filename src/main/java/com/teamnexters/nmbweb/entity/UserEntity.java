package com.teamnexters.nmbweb.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


/**
 * Created by limjuhyun on 8/2/16.
 */
@Entity
@Data
@Table(name="user")
public class UserEntity {
    @Id
    @Column
    @NotNull(message = "아이디는 필수 입력 항목입니다.")
    private String userid;

    @Column
    @Size(min=10,max=100,message = "최소 10자 이상 적어주세요.")
    private String passwd;

    @Column
    @Size(min=2,max=45)
    private String name;

    @Column
    private int type;

    @Column
    @Size(min=9,max=45,message = "폰 번호는 필수 요소입니다.")
    private String phone;

    @Column
    private String token;

    @Column
    private String rid;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="userid")
    private List<FriendEntity> friend;

    public void setPasswd(String passwd) {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        this.passwd =encoder.encodePassword(passwd, null);
    }

}

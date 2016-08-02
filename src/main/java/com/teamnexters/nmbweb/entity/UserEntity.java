package com.teamnexters.nmbweb.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * Created by limjuhyun on 8/2/16.
 */
@Entity
@Data
@Table(name="user")
public class UserEntity {
    @Id
    @Column
    private String userid;

    @Column
    private String passwd;

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private String token;

    @Column
    private String rid;
}

package com.teamnexters.nmbweb.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by limjuhyun on 8/2/16.
 */
@Entity
@Data
@Table(name="box")
public class BoxEntity {
    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int boxno;

    @Column
    private String userid;

    @Column
    private Date date;

    @Column
    private String label;

    @Column
    private String shuserid;

    @Column
    private String content;

    @Column
    private int status;
}

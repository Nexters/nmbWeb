package com.teamnexters.nmbweb.entity;


import lombok.Data;

import javax.persistence.*;

/**
 * Created by Jeon on 2016-08-10.
 */
@Entity
@Data
@Table(name="friend")
public class FriendEntity {


    @Column
    private String userid;

    @Column
    private String friendid;

    @Column
    private int receivedmsgCnt;

    @Column
    private int sendmsgCnt;
}

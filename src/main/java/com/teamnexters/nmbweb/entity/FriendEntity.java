package com.teamnexters.nmbweb.entity;


import com.teamnexters.nmbweb.entity.id.FriendIdClass;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by Jeon on 2016-08-10.
 */
@Entity
@Data
@Table(name="friend")
@IdClass(FriendIdClass.class)
public class FriendEntity {

    @Id
    @Column
    private String userid;

    @Id
    @Column
    private String friendid;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userid", insertable=false, updatable=false)
    private UserEntity user;

}

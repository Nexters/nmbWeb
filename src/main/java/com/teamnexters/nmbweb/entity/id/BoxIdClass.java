package com.teamnexters.nmbweb.entity.id;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by limjuhyun on 7/31/16.
 */
@Data
public class BoxIdClass implements Serializable {
    private static final long serialVersionUID = 1L;

    private int boxno;
    private String userid;

}

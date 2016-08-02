package com.teamnexters.nmbweb.controller;

import com.teamnexters.nmbweb.entity.UserEntity;
import com.teamnexters.nmbweb.repo.BoxRepo;
import com.teamnexters.nmbweb.repo.UserRepo;
import com.teamnexters.nmbweb.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by limjuhyun on 8/2/16.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepo userRepo;

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Map<String, Object> getUserData(@PathVariable String id) {


        Map<String, Object> mapResData = new HashMap<String, Object>();
        UserEntity userEntity = userRepo.findByUserid(id);

        if(userEntity==null)
            return JsonUtil.putFailJsonContainer("0001", "존재하지 않는 사용자입니다");

        mapResData.put("user", userEntity);

        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.POST)
    public Map<String, Object> saveUserData(@PathVariable String id) {


        Map<String, Object> mapResData = new HashMap<String, Object>();
        UserEntity userEntity = userRepo.findByUserid(id);

        if(userEntity==null)
            return JsonUtil.putFailJsonContainer("0001", "존재하지 않는 사용자입니다");

        mapResData.put("user", userEntity);

        userRepo.saveAndFlush((UserEntity)mapResData.get("user"));

        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

}

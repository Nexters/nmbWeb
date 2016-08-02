package com.teamnexters.nmbweb.controller;

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

    @RequestMapping(name="/{id}", method = RequestMethod.POST)
    public Map<String, Object> test(@PathVariable String id) {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        mapResData.put("user", userRepo.findByUserid(id));
        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

}

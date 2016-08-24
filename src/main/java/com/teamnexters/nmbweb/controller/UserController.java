package com.teamnexters.nmbweb.controller;

import com.teamnexters.nmbweb.entity.UserEntity;
import com.teamnexters.nmbweb.repo.BoxRepo;
import com.teamnexters.nmbweb.repo.UserRepo;
import com.teamnexters.nmbweb.util.JsonUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by limjuhyun on 8/2/16.
 */
@RestController
@RequestMapping(value="/user", produces="application/json; charset=UTF-8")
public class UserController {

    @Autowired
    UserRepo userRepo;

    @ApiOperation(value="유저 조회", notes="조회 시, 즐겨찾기 멤버도 가지고 온다")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Map<String, Object> getUserData(@PathVariable String id) {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        UserEntity userEntity = userRepo.findByUserid(id);
        //사용자가 존재하지 않을 경우
        if(userEntity==null)
            return JsonUtil.putFailJsonContainer("0001", "존재하지 않는 사용자입니다");
        mapResData.put("user", userEntity);
        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

    @Secured({"ROLE_ANONYMOUS","IS_AUTHENTICATED_ANONYMOUSLY"})
    @ApiOperation(value="유저 가입", notes = "유저가 존재하면 'user' 키 값에 사용자 정보를 넘겨줌")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public Map<String, Object> saveUserData(@Valid @ModelAttribute UserEntity userEntity) {
        UserEntity user = userRepo.findByUserid(userEntity.getUserid());
        Map<String, Object> mapResData = new HashMap<String, Object>();

        if(user!=null) {
            user.setPasswd(null);
            mapResData.put("user", user);
        }  else {
            mapResData.put("saved", userRepo.saveAndFlush(userEntity));

        }
        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

}

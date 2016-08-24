package com.teamnexters.nmbweb.controller;

import com.teamnexters.nmbweb.entity.FriendEntity;
import com.teamnexters.nmbweb.repo.FriendRepo;
import com.teamnexters.nmbweb.util.CommUtil;
import com.teamnexters.nmbweb.util.JsonUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeon on 2016-08-10.
 */
@RestController
@RequestMapping(value="/friend", produces="application/json; charset=UTF-8")
public class FriendController {

    @Autowired
    FriendRepo friendRepo;

    @ApiOperation(value="프렌드 추가")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public Map<String, Object> addFriend(@Valid @ModelAttribute FriendEntity friendEntity) {
        Map<String, Object> mapResData = new HashMap<String, Object>();

        String strUsrId = CommUtil.getUserid();
        if(strUsrId==null || "".equals(strUsrId))
            return JsonUtil.putFailJsonContainer("9999", "로그인 해주세요.");

        friendEntity.setUserid(strUsrId);
        mapResData.put("saved", friendRepo.saveAndFlush(friendEntity));
        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

    @ApiOperation(value="프렌드 삭제")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteFriend(@PathVariable String id) {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        FriendEntity friendEntity = new FriendEntity();
        String strUsrId = CommUtil.getUserid();

        if(strUsrId==null || "".equals(strUsrId))
            return JsonUtil.putFailJsonContainer("9999", "로그인 해주세요.");

        friendEntity.setUserid(strUsrId);
        friendEntity.setFriendid(id);
        friendRepo.delete(friendEntity);
        mapResData.put("deleted", friendEntity);
        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

}

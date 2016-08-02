package com.teamnexters.nmbweb.controller;

import com.teamnexters.nmbweb.entity.BoxEntity;

import com.teamnexters.nmbweb.repo.BoxRepo;
import com.teamnexters.nmbweb.util.CommUtil;
import com.teamnexters.nmbweb.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limjuhyun on 8/2/16.
 */
@RestController
@RequestMapping("/box")
public class BoxController {

    @Autowired
    BoxRepo boxRepo;

    @RequestMapping(value="/{id}", method = RequestMethod.POST)
    public Map<String, Object> write(@PathVariable int id
                                    ,@RequestParam  Map<String, Object> mapReqData) {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        BoxEntity boxEntity = new BoxEntity();

        boxEntity.setBoxno(id);
        boxEntity.setUserid((String)mapReqData.get("userid"));
        boxEntity.setDate((Date)mapReqData.get("date"));
        boxEntity.setLabel((String)mapReqData.get("label"));
        boxEntity.setShuserid((String)mapReqData.get("shuserid"));
        boxEntity.setContent((String)mapReqData.get("content"));
        boxEntity.setStatus((int)mapReqData.get("status"));

        mapResData.put("saved", boxRepo.saveAndFlush(boxEntity));

        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Map<String, Object> findBoxByBothId(@PathVariable int id) {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        mapResData.put("box", boxRepo.findByBoxno(id));
        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

    @RequestMapping(value="/shared", method = RequestMethod.GET)
    public Map<String, Object> getSharedBoxList() {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        mapResData.put("boxes", boxRepo.findByUseridAndShuseridIsNotNullOrderByDateDesc(CommUtil.getUserid()));
        return JsonUtil.putSuccessJsonContainer(mapResData);
    }
}

package com.teamnexters.nmbweb.controller;

import com.teamnexters.nmbweb.entity.BoxEntity;

import com.teamnexters.nmbweb.repo.BoxRepo;
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

    BoxEntity boxEntity;

    Map<String, Object> mapResData;


    @RequestMapping(name="/{id}", method = RequestMethod.POST)
    public Map<String, Object> write(@PathVariable Map<String, Object> mapResData) {


         mapResData= new HashMap<String, Object>();

        boxEntity.setBoxno((int)mapResData.get("boxno"));
        boxEntity.setUserid((String)mapResData.get("userid"));
        boxEntity.setDate((Date)mapResData.get("date"));
        boxEntity.setLabel((String)mapResData.get("label"));
        boxEntity.setShuserid((String)mapResData.get("shuserid"));
        boxEntity.setContent((String)mapResData.get("content"));
        boxEntity.setStatus((int)mapResData.get("status"));


        boxRepo.saveAndFlush(boxEntity);

        return JsonUtil.putSuccessJsonContainer(mapResData);
    }



     @RequestMapping(name="/{id}", method = RequestMethod.GET)
    public Map<String, Object> findBoxByBothId(@PathVariable String id,@PathVariable String shUserid) {


        Map<String, Object> mapResData = new HashMap<String, Object>();
        List<BoxEntity> boxEntityList= boxRepo.findByUseridAndShuserid(id,shUserid);

        mapResData.put("box", boxEntityList);

        return JsonUtil.putSuccessJsonContainer(mapResData);

    }






    /*///box/dasdsad/edit?data=dfdfdf
    @RequestMapping(name="/{id}/edit", method = RequestMethod.POST)
    public Map<String, Object> edit(@PathVariable("id") String idd
                                    , @RequestParam("data") String data) {
        Map<String, Object> mapResData = new HashMap<String, Object>();

        return null;
    }*/





}

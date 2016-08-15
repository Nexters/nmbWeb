package com.teamnexters.nmbweb.controller;

import com.teamnexters.nmbweb.entity.BoxEntity;

import com.teamnexters.nmbweb.repo.BoxRepo;
import com.teamnexters.nmbweb.util.CommUtil;
import com.teamnexters.nmbweb.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Box;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @RequestMapping(value="/", method = RequestMethod.POST)
    public Map<String, Object> write(@Valid @ModelAttribute BoxEntity paramBoxEntity) {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        BoxEntity boxEntity = new BoxEntity();
        String usrid = CommUtil.getUserid();

        if(usrid==null || "".equals(usrid))
            return JsonUtil.putFailJsonContainer("9999", "로그인 해주세요.");

        boxEntity.setUserid(CommUtil.getUserid());
        boxEntity.setDate(new Date());
        boxEntity.setLabel(paramBoxEntity.getLabel());
        boxEntity.setTitle(paramBoxEntity.getTitle());
        boxEntity.setShuserid(paramBoxEntity.getShuserid());
        boxEntity.setContent(paramBoxEntity.getContent());
        boxEntity.setStatus(paramBoxEntity.getStatus());

        mapResData.put("saved", boxRepo.saveAndFlush(boxEntity));

        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Map<String, Object> findBoxByBothId(@PathVariable int id) {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        BoxEntity entity = boxRepo.findByBoxno(id);
        entity.setReadYN(1);
        boxRepo.saveAndFlush(entity);
        mapResData.put("box", entity);
        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

    @RequestMapping(value="/{boxno}/update", method = RequestMethod.POST)
    public Map<String, Object> update(@Valid @ModelAttribute BoxEntity paramBoxEntity) {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        BoxEntity boxEntity = new BoxEntity();
        String usrid = CommUtil.getUserid();

        if(usrid==null || "".equals(usrid))
            return JsonUtil.putFailJsonContainer("9999", "로그인 해주세요.");

        boxEntity.setBoxno(paramBoxEntity.getBoxno());
        boxEntity.setUserid(CommUtil.getUserid());
        boxEntity.setDate(new Date());
        boxEntity.setLabel(paramBoxEntity.getLabel());
        boxEntity.setTitle(paramBoxEntity.getTitle());
        boxEntity.setShuserid(paramBoxEntity.getShuserid());
        boxEntity.setContent(paramBoxEntity.getContent());
        boxEntity.setStatus(paramBoxEntity.getStatus());

        mapResData.put("updated", boxRepo.saveAndFlush(boxEntity));

        return JsonUtil.putSuccessJsonContainer(mapResData);
    }


    //받은메시지랑 보낸메시지
    @RequestMapping(value="/sent_list/{page}", method = RequestMethod.GET)
    public Map<String, Object> getSharedBoxList(@PathVariable int page) {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        PageRequest pageRequest = new PageRequest(page-1, 10, new Sort(Sort.Direction.DESC, "date"));
        mapResData.put("list", boxRepo.findByUserid(CommUtil.getUserid(), pageRequest));
        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

    @RequestMapping(value="/shared_list/{page}", method = RequestMethod.GET)
    public Map<String, Object> getSentBoxList(@PathVariable int page){
        Map<String, Object> mapResData = new HashMap<String, Object>();
        PageRequest pageRequest = new PageRequest(page-1, 10, new Sort(Sort.Direction.DESC, "date"));
        mapResData.put("list", boxRepo.findByUserid(CommUtil.getUserid(), pageRequest));
        return JsonUtil.putSuccessJsonContainer(mapResData);
    }
}

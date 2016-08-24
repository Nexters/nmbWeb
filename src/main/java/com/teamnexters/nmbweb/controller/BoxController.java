package com.teamnexters.nmbweb.controller;

import com.teamnexters.nmbweb.entity.BoxEntity;

import com.teamnexters.nmbweb.repo.BoxRepo;
import com.teamnexters.nmbweb.util.CommUtil;
import com.teamnexters.nmbweb.util.JsonUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Box;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limjuhyun on 8/2/16.
 */
@RestController
@RequestMapping(value="/box", produces="application/json;charset=UTF-8")
public class BoxController {

    /**
     * 그냥 상자    잠금박스       공유박스       해결된 박스

     userid jhk     jhk             jhk            jhk

     shuserid x    bongjae2      bongjae2       bongjae2
     status 0      status 1      status 2       status 3
     */

    @Autowired
    BoxRepo boxRepo;

    @ApiOperation(value="안읽은 메시지 카운트")
    @RequestMapping(value="/count", method=RequestMethod.GET)
    public Map<String, Object> getCount() {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        String strUsrId = CommUtil.getUserid();

        if(strUsrId==null || "".equals(strUsrId))
            return JsonUtil.putFailJsonContainer("9999", "로그인 해주세요.");

        mapResData.put("count", boxRepo.countByShuseridAndReadYN(strUsrId, 0));

        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

    @ApiOperation(value="박스 추가/수정",notes = "boxno는 '수정' 할 때에만 넣어주세요.")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public Map<String, Object> updateAndSave(@Valid @ModelAttribute BoxEntity paramBoxEntity) {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        String strUsrId = CommUtil.getUserid();

        if(strUsrId==null || "".equals(strUsrId))
            return JsonUtil.putFailJsonContainer("9999", "로그인 해주세요.");

        paramBoxEntity.setUserid(strUsrId);
        paramBoxEntity.setDate(new Date());

        mapResData.put("saved", boxRepo.saveAndFlush(paramBoxEntity));

        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

    @ApiOperation(value="박스 삭제")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteBox(@PathVariable int id) {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        String strUsrId = CommUtil.getUserid();
        BoxEntity entity = boxRepo.findByBoxno(id);

        if(strUsrId==null || "".equals(strUsrId))
            return JsonUtil.putFailJsonContainer("9999", "로그인 해주세요.");

        if(!entity.getUserid().equals(strUsrId))
            return JsonUtil.putFailJsonContainer("9998", "본인의 글만 지울 수 있습니다.");

        if(entity.getStatus()>=2)
            return JsonUtil.putFailJsonContainer("9997", "공유된 박스는 삭제할 수 없습니다.");


        boxRepo.delete(entity);

        mapResData.put("deleted", entity);

        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

    @ApiIgnore
    @RequestMapping(value="/{id}/share", method = RequestMethod.DELETE)
    public Map<String, Object> removeShareUser(@PathVariable int id) {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        String strUsrId = CommUtil.getUserid();
        BoxEntity entity = boxRepo.findByBoxno(id);
        entity.setShuserid(null);

        if(strUsrId==null || "".equals(strUsrId))
            return JsonUtil.putFailJsonContainer("9999", "로그인 해주세요.");

        if(!entity.getShuserid().equals(strUsrId))
            return JsonUtil.putFailJsonContainer("9998", "공유된 글만 지울 수 있습니다");


        mapResData.put("updated", boxRepo.saveAndFlush(entity));

        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

    @ApiOperation(value="상태 변경")
    @RequestMapping(value="/{id}/status", method = RequestMethod.POST)
    public Map<String, Object> updateBoxStatus(@PathVariable int id, @RequestParam("status") int status) {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        String strUsrId = CommUtil.getUserid();
        BoxEntity entity = boxRepo.findByBoxno(id);
        entity.setStatus(status);

        if(strUsrId==null || "".equals(strUsrId))
            return JsonUtil.putFailJsonContainer("9999", "로그인 해주세요.");

        if(!entity.getUserid().equals(strUsrId) ||  !entity.getShuserid().equals(strUsrId))
            return JsonUtil.putFailJsonContainer("9998", "수정 권한이 없습니다.");

        mapResData.put("updated", boxRepo.saveAndFlush(entity));

        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

    @ApiOperation(value="박스 공유 사용자 추가", notes="shuserid에 공유할 상대방 아이디를 집어넣어야함")
    @RequestMapping(value="/{id}/share", method = RequestMethod.POST)
    public Map<String, Object> addShareUser(@PathVariable int id, @RequestParam("shuserid") String strShuserId) {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        String strUsrId = CommUtil.getUserid();

        if(strUsrId==null || "".equals(strUsrId))
            return JsonUtil.putFailJsonContainer("9999", "로그인 해주세요.");

        BoxEntity entity = boxRepo.findByBoxno(id);


        if(!entity.getUserid().equals(strUsrId))
            return JsonUtil.putFailJsonContainer("9998", "수정 권한이 없습니다.");

        entity.setShuserid(strShuserId);
        entity.setStatus(2);


        mapResData.put("updated", boxRepo.saveAndFlush(entity));

        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

    @ApiOperation(value="박스 내용 조회", notes = "공유된 상대방이 조회 시, 이 API를 통해 ReadYN이 읽은 메시지로 변함")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Map<String, Object> findBoxByBoxno(@PathVariable int id) {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        BoxEntity entity = boxRepo.findByBoxno(id);
        String strUsrId = CommUtil.getUserid();
        mapResData.put("box", entity);

        if(entity.getShuserid() != null && entity.getShuserid().equals(strUsrId)) {
            if(entity.getStatus()<=1)
                return JsonUtil.putFailJsonContainer("9998", "공유된 상자가 아닙니다.");

            entity.setReadYN(1);
            boxRepo.saveAndFlush(entity);
            return JsonUtil.putSuccessJsonContainer(mapResData);

        }  else if(entity.getUserid().equals(strUsrId)) {
            return JsonUtil.putSuccessJsonContainer(mapResData);
        }

        return JsonUtil.putFailJsonContainer("9997", "권한이 없습니다.");

    }

    //받은메시지랑 보낸메시지
    @ApiOperation(value="보낸 메시지 조회")
    @RequestMapping(value="/sent_list/{page}", method = RequestMethod.GET)
    public Map<String, Object> getSharedBoxList(@PathVariable int page) {
        Map<String, Object> mapResData = new HashMap<String, Object>();
        PageRequest pageRequest = new PageRequest(page-1, 10, new Sort(Sort.Direction.DESC, "date"));
        mapResData.put("list", boxRepo.findByUserid(CommUtil.getUserid(), pageRequest));
        return JsonUtil.putSuccessJsonContainer(mapResData);
    }

    @ApiOperation(value="공유 받은 메시지 조회")
    @RequestMapping(value="/shared_list/{page}", method = RequestMethod.GET)
    public Map<String, Object> getSentBoxList(@PathVariable int page){
        Map<String, Object> mapResData = new HashMap<String, Object>();
        PageRequest pageRequest = new PageRequest(page-1, 10, new Sort(Sort.Direction.DESC, "date"));
        mapResData.put("list", boxRepo.findByShuseridAndStatusGreaterThanEqual(CommUtil.getUserid(), 1, pageRequest));
        return JsonUtil.putSuccessJsonContainer(mapResData);
    }
}

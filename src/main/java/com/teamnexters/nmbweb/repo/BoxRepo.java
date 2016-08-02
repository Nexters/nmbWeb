package com.teamnexters.nmbweb.repo;

import com.teamnexters.nmbweb.entity.BoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;

import java.util.List;

/**
 * Created by limjuhyun on 8/2/16.
 */
public interface BoxRepo extends JpaRepository<BoxEntity, String> {
    List<BoxEntity> findByUseridAndShuserid(String userid, String shUserid);

    @PostAuthorize("returnObject.type == authentication.name")
    List<BoxEntity> findByUseridAndShuseridIsNotNullOrderByDateDesc(String userId);

    @PostAuthorize ("returnObject.type == authentication.name")
    BoxEntity findByBoxno(int boxno);
}


package com.teamnexters.nmbweb.repo;

import com.teamnexters.nmbweb.entity.BoxEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;

import java.util.List;

/**
 * Created by limjuhyun on 8/2/16.
 */
public interface BoxRepo extends JpaRepository<BoxEntity, String> {
    List<BoxEntity> findByUseridAndShuserid(String userid, String shUserid);

    Page<BoxEntity> findByUseridAndShuseridIsNotNull(String userId, Pageable pageable);

    Page<BoxEntity> findByUserid(String userid, Pageable pageable);

    Page<BoxEntity> findByShuserid(String shuserid, Pageable pageable);

    BoxEntity findByBoxno(int boxno);
}


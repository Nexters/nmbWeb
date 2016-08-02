package com.teamnexters.nmbweb.repo;

import com.teamnexters.nmbweb.entity.BoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Jeon on 2016-08-02.
 */
public interface BoxRepo2 extends JpaRepository<BoxEntity, String> {
    BoxEntity findByUserid(String userid);
}

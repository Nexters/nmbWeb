package com.teamnexters.nmbweb.repo;

import com.teamnexters.nmbweb.entity.BoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by limjuhyun on 8/2/16.
 */
public interface BoxRepo extends JpaRepository<BoxEntity, String> {
}

package com.teamnexters.nmbweb.repo;

import com.teamnexters.nmbweb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by limjuhyun on 8/2/16.
 */
public interface UserRepo extends JpaRepository<UserEntity, String> {
    UserEntity findByUserid(String id);
}

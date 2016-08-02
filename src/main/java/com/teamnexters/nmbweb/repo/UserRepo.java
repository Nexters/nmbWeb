package com.teamnexters.nmbweb.repo;

import com.teamnexters.nmbweb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;

/**
 * Created by limjuhyun on 8/2/16.
 */
public interface UserRepo extends JpaRepository<UserEntity, String> {
    @PostAuthorize("returnObject.type == authentication.name")
    UserEntity findByUserid(String id);
}

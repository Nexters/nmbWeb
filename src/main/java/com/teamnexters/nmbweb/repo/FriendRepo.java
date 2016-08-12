package com.teamnexters.nmbweb.repo;

import com.teamnexters.nmbweb.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
/**
 * Created by Jeon on 2016-08-10.
 */
public interface FriendRepo extends JpaRepository<FriendEntity, String>  {
}

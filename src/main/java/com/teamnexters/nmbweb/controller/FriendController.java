package com.teamnexters.nmbweb.controller;

import com.teamnexters.nmbweb.repo.FriendRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jeon on 2016-08-10.
 */
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    FriendRepo friendRepo;

}

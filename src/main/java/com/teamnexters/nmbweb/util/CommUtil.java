package com.teamnexters.nmbweb.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by limjuhyun on 8/3/16.
 */
public class CommUtil {

    /**
     * 현재 로그인 중인 사용자의 정보를 가져옴
     * @return 로그인 중인 사용자의 이름(아이디)
     */
    public static String getUserid() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}

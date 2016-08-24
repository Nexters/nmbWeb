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
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String strUsrId = auth.getName();
            if(!"anonymousUser".equals(strUsrId)) {
                return auth.getName();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}

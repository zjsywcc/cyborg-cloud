package com.ese.cloud.client.util;

import java.util.UUID;

/**
 * Created by wangchengcheng on 2017/11/24.
 */
public class TokenUtil {


    public static String generateToken(int uid) {
        String uuid = UUID.randomUUID ().toString ().replace ("-", "");
        String token = uid + "_" + uuid;
        return token;
    }
}

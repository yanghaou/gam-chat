package com.chat.util;

import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QiNiuUtil {

    public static String getQiNiuPicToken(){
        String ak = "XLZWuYaQcENxicyUXEOZf-fePYnDCxTrLg62XOgb";
        String sk = "_dWbzHxlbw0MjuU2lEvaZTrqooG8iYS-PtKpmCF2";
        String bucket = "kinker";
        Auth auth = Auth.create(ak, sk);
        return auth.uploadToken(bucket);
    }

    public static void main(String[] args) {
        System.out.println(getQiNiuPicToken());
    }
}

package com.chat.service;

import com.chat.util.QiNiuUtil;
import com.chat.vo.file.FileToken;
import org.springframework.stereotype.Service;

@Service
public class FileUploadService {
    public FileToken getQiNiuUploadToken() {
        return new FileToken(QiNiuUtil.getQiNiuPicToken());
    }
}

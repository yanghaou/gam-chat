package com.chat.chatparent;

import com.chat.repository.user.UserBaseRepository;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatParentApplicationTests {
	@Autowired
	UserBaseRepository userRepository;

	@Test
	void contextLoads() {

		System.out.println();
	}


	@Test
	void testMail(){
		System.out.println();
	}
	@Autowired
	StringEncryptor encryptor;

	@Test
	void testJasypt(){

		//加密工具
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		//加密配置
		EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
		config.setAlgorithm("PBEWithMD5AndDES");//加密方式，默认PBEWithMD5AndDES，可改PBEWithMD5AndTripleDES
		config.setPassword("");//加密所需的salt(盐)
		config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
		//应用配置
		encryptor.setConfig(config);

		System.out.println();
	}
}

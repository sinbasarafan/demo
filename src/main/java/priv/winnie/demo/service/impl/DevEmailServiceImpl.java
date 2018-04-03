package priv.winnie.demo.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import priv.winnie.demo.service.EmailService;

@Service
@Profile("dev")
@Slf4j
public class DevEmailServiceImpl implements EmailService {

	@Override
	public void send() {
		log.info("开发环境不执行邮件的发送");
	}

}

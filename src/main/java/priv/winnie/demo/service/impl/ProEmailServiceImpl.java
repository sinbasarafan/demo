package priv.winnie.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import priv.winnie.demo.service.EmailService;

@Service
@Profile("pro")
@Slf4j
public class ProEmailServiceImpl implements EmailService {
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Override
	public void send() {
		SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("13502461435@139.com");
        message.setTo("zheng.wn@foxmail.com");
        message.setSubject("测试");
        message.setText("测试");
        mailSender.send(message);
		log.info("生产环境执行邮件的发送");
	}

}

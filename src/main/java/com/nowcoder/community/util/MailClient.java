package com.nowcoder.community.util;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Component
public class MailClient {
	private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String from;

	/**
	 * 发送邮件
	 * @param to 收件人
	 * @param subject 主题
	 * @param context 内容
	 */
	public void sendMail(String to, String subject, String context){

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			//设置文件内容并让他支持html
			helper.setText(context,true);
			mailSender.send(helper.getMimeMessage());
		} catch (MessagingException e) {
			logger.error("发送邮件失败："+e.getMessage());
		}

	}

}

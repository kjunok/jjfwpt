package com.jjkj.support.mq.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.jjkj.support.email.Email;
import com.jjkj.util.EmailUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 发送邮件队列
 *
 * @author WangJun
 * @version 2016年6月14日 上午11:00:53
 */
public class SendEmailListener implements MessageListener {
	private final Logger logger = LogManager.getLogger();

	@Override
    public void onMessage(Message message) {
		try {
			Email email = (Email) ((ObjectMessage) message).getObject();
			logger.info("将发送邮件至：" + email.getSendTo());
			EmailUtil.sendEmail(email);
		} catch (JMSException e) {
			logger.error(e);
		}
	}
}

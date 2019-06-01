package com.afan.user.controller;

import com.afan.user.UserApplication;
import com.afan.user.pojo.SocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: MessageController
 * @Description: TODO
 * @Author：afan
 * @Date : 2019/4/27 13:07
 */
@RestController
@CrossOrigin
public class MessageController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/send")
	public void send(SocketMessage message) throws Exception {
		System.out.println(message.getMessage());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		message.setDate(df.format(new Date()));
		String sessionId= UserApplication.sessionMap.get(message.getToUser());
		System.out.println("sessionId:"+sessionId);
		messagingTemplate.convertAndSendToUser(sessionId,"/" +
				"topic/send",message,createHeaders
				(sessionId));
	}
	private MessageHeaders createHeaders(String sessionId) {
		SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		headerAccessor.setSessionId(sessionId);
		headerAccessor.setLeaveMutable(true);
		return headerAccessor.getMessageHeaders();
	}
}

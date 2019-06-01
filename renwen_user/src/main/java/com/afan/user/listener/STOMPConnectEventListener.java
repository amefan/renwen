package com.afan.user.listener;

import com.afan.user.UserApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: STOMPConnectEventListener
 * @Description: TODO
 * @Author：afan
 * @Date : 2019/4/10 16:31
 */
@Component
public class STOMPConnectEventListener implements ApplicationListener<SessionConnectEvent> {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;


	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
		String userId = sha.getNativeHeader("login").get(0);
		String sessionId = sha.getSessionId();
		UserApplication.sessionMap.put(userId,sessionId);
		System.out.println("STOMPConnectEventListener........"+userId+"-"+sessionId);
	}
}
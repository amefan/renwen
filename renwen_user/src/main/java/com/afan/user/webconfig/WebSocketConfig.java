package com.afan.user.webconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @ClassName: WebSocketConfig
 * @Description: TODO
 * @Author：afan
 * @Date : 2019/4/27 12:37
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
        /*
         * 用户可以订阅来自"/topic"和"/user"的消息，
         * 在Controller中，可通过@SendTo注解指明发送目标，这样服务器就可以将消息发送到订阅相关消息的客户端
         *
         * 在本Demo中，使用topic来达到聊天室效果（单聊+多聊）
         *
         * 客户端只可以订阅这两个前缀的主题
         */
		config.enableSimpleBroker("/topic");

        /*
         * 客户端发送过来的消息，需要以"/app"为前缀，再经过Broker转发给响应的Controller
         */
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {

        /*
         * 路径"/websocket"被注册为STOMP端点，对外暴露，客户端通过该路径接入WebSocket服务
         */
		registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS();
	}

}
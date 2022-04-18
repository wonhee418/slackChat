package com.example.clonecoding.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//stomp를 사용하기 위해 선언
//WebSocketMessageBrokerConfigurer를 상속받아 configurerMessageBroker를 구현
//Websocket api를 바로 사용하지 않고 STOMP를 통해서 설정한다.
//configureMessageBroker : 메시지 브로커에 관련된 설정을 한다
//registerStompEndpoints : SockJs Fallback을 이용해 노출할 STOMP endpoint를 설정한다.
//메시지 발행하는 prefix는 /pub로 시작하도록 설정하고 메시지를 구독하는 요청의 prefix는 /sub 시작하도록 설정했다.
//stomp websocket의 연결 endpoint는 /ws-stomp 으로 설정했다.

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //메시지 구독요청은 prefix/sub
        config.enableSimpleBroker("/sub");
        //메시지 발행요청은 prefix/pub
        config.setApplicationDestinationPrefixes("/pub");
        log.info(String.valueOf(config));
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //프론트와 합칠때 엔드포인트 의심해볼것.!//
        //SockJS Client가 웹소켓 핸드셰이크 커넥션을 생성할 경로이다.
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*")
                .withSockJS();
    }
}

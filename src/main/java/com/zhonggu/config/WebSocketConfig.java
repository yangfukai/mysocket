package com.zhonggu.config;

import com.zhonggu.socket.ChatWebSocketHandler;
import com.zhonggu.socket.WebSocketHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author yangkai on 2018/1/24.
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //允许连接的域,只能以http或https开头
//        String[] allowsOrigins = {"http://www.xxx.com"};.setAllowedOrigins(allowsOrigins)
        //WebIM WebSocket通道
        registry.addHandler(chatWebSocketHandler(),"websocket/socketServer.do").addInterceptors(myInterceptor());
        registry.addHandler(chatWebSocketHandler(), "/sockjs/socketServer.do").addInterceptors(myInterceptor()).withSockJS();
    }
    @Bean
    public ChatWebSocketHandler chatWebSocketHandler() {
        return new ChatWebSocketHandler();
    }
    @Bean
    public WebSocketHandshakeInterceptor myInterceptor(){
        return new WebSocketHandshakeInterceptor();
    }
}

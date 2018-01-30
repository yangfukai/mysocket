package com.zhonggu.socket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author yangkai on 2018/1/24.
 */
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor
{
    /**
     *在调用handler前处理方法。常用在注册用户信息，
     * 绑定WebSocketSession，在handler里根据用户信息获取WebSocketSession发送消息
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes)
            throws Exception {
        System.out.println("Before Handshake");
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                //使用userName区分WebSocketHandler，以便定向发送消息
                String userName = (String) session.getAttribute("SESSION_USERNAME");
                if (userName==null) {
                    userName="default-system";
                }
                attributes.put("WEBSOCKET_USERNAME",userName);
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println("After Handshake");
        super.afterHandshake(request, response, wsHandler, exception);
    }
}

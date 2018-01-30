package com.zhonggu.socket;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author yangkai on 2018/1/24.
 */
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = Logger.getLogger(ChatWebSocketHandler.class);

    private final static Set<WebSocketSession> sessions = new CopyOnWriteArraySet<WebSocketSession>();
    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        chatTextMessageHandler(message.getPayload());
        super.handleTextMessage(session, message);
        TextMessage returnMessage = new TextMessage(message.getPayload());
        sendMessageToUsers(returnMessage);
//        session.sendMessage(returnMessage);
    }
    //连接建立后处理
    @SuppressWarnings("unchecked")
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("connect to the websocket chat success......"+sessions.size());
        sessions.add(session);
        //处理离线消息
        //这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
        //TextMessage returnMessage = new TextMessage("你将收到的离线");
        //session.sendMessage(returnMessage);
    }
    //抛出异常时处理
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        logger.debug("websocket chat connection closed......");
        sessions.remove(session);
    }
    //连接关闭后处理
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.info("websocket chat connection closed......");
        sessions.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession session : sessions) {
            if (session.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {
                try {
                    if (session.isOpen()) {
                        session.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : sessions) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

package com.zhonggu.controller;

import com.zhonggu.controller.reqmodel.User;
import com.zhonggu.socket.ChatWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import javax.servlet.http.HttpSession;

/**
 * @author yangkai on 2018/1/24.
 */
@Controller
@RequestMapping("/socket_login")
public class WebSocketController extends BaseController {

    @Bean//这个注解会从Spring容器拿出Bean
    public ChatWebSocketHandler infoHandler() {
        return new ChatWebSocketHandler();
    }

    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    public String login(User user, HttpSession session){
        LOG.info("uset:"+user.getUsername()+" "+ user.getPassword());
        session.setAttribute("SESSION_USERNAME",user.getUsername());
        return "socket";
    }
    @RequestMapping("/websocket/send.do")
    @ResponseBody
    public String send(@RequestParam("username") String username) {
//        infoHandler().sendMessageToUser(username, new TextMessage("你好，测试！！！！"));
        infoHandler().sendMessageToUsers(new TextMessage("群发的无罪！"));
        return null;
    }
}

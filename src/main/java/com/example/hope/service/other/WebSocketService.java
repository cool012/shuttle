package com.example.hope.service.other;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ServerEndpoint(value = "/ws")
@Service
public class WebSocketService {
    /**
     * 存放所有在线的客户端
     */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        clients.put(session.getId(), session);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        clients.remove(session.getId());
        log.info("{}已退出到服务端", session.getId());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("服务端收到客户端[{}]的消息:{}", session.getId(), message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("{}连接发生错误", session.getId());
        error.printStackTrace();
    }

    /**
     * 发送信息
     *
     * @param msg 内容
     */
    public void sendMessage(String msg) {
        clients.values().forEach(session -> session.getAsyncRemote().sendText(msg));
    }
}

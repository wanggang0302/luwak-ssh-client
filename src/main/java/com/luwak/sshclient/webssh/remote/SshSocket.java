package com.luwak.sshclient.webssh.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

/**
 * @author ChenXc
 * @version V1.0
 * @Date 2018/4/22 下午8:57
 * @Description TODO(一句话描述类作用)
 */
@ServerEndpoint("/websocket")
@Component
public class SshSocket {

    private Logger logger = LoggerFactory.getLogger(SshSocket.class);

    @OnOpen
    public void onOpen() {

    }

    @OnMessage
    public void onMessage(WebSocketSession socketSession) throws Exception {

    }

    @OnClose
    public void onClose() {

    }

    @OnError
    public void onError() {

    }


}

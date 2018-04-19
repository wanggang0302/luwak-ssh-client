package com.luwak.sshclient.controller;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

/**
 * @author ChenXc
 * @version V1.0
 * @Date 2018/4/19 下午8:43
 * @Description TODO(一句话描述类作用)
 */
@ServerEndpoint(value = "/sshSocket/{host}/{port}/{username}/{password}")
public class SocketController {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("connect success");
    }

    /**
     * 创建到远程服务器的ssh的channel
     *
     * @param username 登录用户名
     * @param password 登录密码
     * @param host     远程主机
     * @param port     远程端口
     * @param col      终端的列数
     * @param row      终端的行数
     * @param wp       窗口的宽度，px单位
     * @param hp       窗口的高度，px单位
     * @return 创建好的channelShell
     * @throws JSchException
     */

    public ChannelShell createChannel(String host, int port, String username, String password,
                                      int col, int row, int wp, int hp) {

        JSch jsch = new JSch();
        ChannelShell channel = null;
        try {
            Session session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "password");
            session.setServerAliveInterval(40000);
            session.setTimeout(120000);
            session.setDaemonThread(true);
            session.connect(30000);
            channel = (ChannelShell) session.openChannel("shell");
            channel.setEnv("LANG", "zh_CN.UTF8");
            channel.setAgentForwarding(false);
            channel.setPtyType("xterm");
            channel.connect(10000);
            channel.setPtySize(col, row, wp, hp);

        } catch (JSchException e) {
            e.printStackTrace();
        }
        return channel;
    }
}

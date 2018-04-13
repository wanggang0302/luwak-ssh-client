package com.luwak.sshclient.controller;

import cn.hutool.core.io.IoUtil;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ChenXc
 * @version V1.0
 * @Date 2018/4/13  17:14
 * @Description TODO(一句话描述类作用)
 */
public class demo {

    public static void main(String[] args) throws IOException, JSchException {

        String host = "140.143.153.135";
        int port = 22;
        String user = "root";
        String password = "cxc19941217";
        String command = "ls /app";
        String res = exeCommand(host,port,user,password,command);

        System.out.println(res);

    }

    public static String exeCommand(String host, int port, String user, String password, String command) throws JSchException, IOException {

        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        session.setConfig("StrictHostKeyChecking", "no");
        //    java.util.Properties config = new java.util.Properties();
        //   config.put("StrictHostKeyChecking", "no");

        session.setPassword(password);
        session.connect();

        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        InputStream in = channelExec.getInputStream();
        channelExec.setCommand(command);
        channelExec.setErrStream(System.err);
        channelExec.connect();
        String out = IoUtil.read(in, "UTF-8");
        channelExec.disconnect();
        session.disconnect();

        return out;
    }
}

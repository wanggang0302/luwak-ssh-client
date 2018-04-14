package com.luwak.sshclient.controller;

import cn.hutool.core.io.IoUtil;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.luwak.sshclient.utils.Command;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ChenXc
 * @version V1.0
 * @Date 2018/4/14 下午6:44
 * @Description TODO(一句话描述类作用)
 */
@Controller
@RequestMapping("/ssh")
public class SshController {

    @PostMapping("/connect")
    public String connect(@RequestBody String hostname, String username, String password, Model model) {

        int port = 22;

        JSch jsch = new JSch();
        try {
            Session session = jsch.getSession(username, hostname, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            InputStream in = channelExec.getInputStream();
            channelExec.setCommand(Command.COMMAND_HSOTNAME);
            channelExec.setErrStream(System.err);
            channelExec.connect();
            String name = IoUtil.read(in, "UTF-8");

            model.addAttribute("name", name);

        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "index";
    }
}

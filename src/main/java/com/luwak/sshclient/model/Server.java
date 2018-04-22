package com.luwak.sshclient.model;

import java.io.Serializable;

/**
 * @author ChenXc
 * @version V1.0
 * @Date 2018/4/20 下午4:19
 * @Description TODO(一句话描述类作用)
 */
public class Server implements Serializable {

    private static final long serialVersionUID = 1L;


    private String hostname;

    private String username;

    private String password;

    public Server(String hostname, String username, String password) {
        super();
        this.hostname = hostname;
        this.username = username;
        this.password = password;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.luwak.sshclient.webssh.remote;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.luwak.sshclient.model.Server;
import org.springframework.web.socket.WebSocketSession;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author ChenXc
 * @version V1.0
 * @Date 2018/4/20 下午4:18
 * @Description TODO(一句话描述类作用)
 */
public class SshClient {

    // 服务器信息
    private Server server;

    // 与客户端连接的socket回话
    private WebSocketSession socket;

    // 连接信息
    private Connection conn = null;

    // term的session信息
    private Session session = null;

    // 向服务器外面写数据的线程
    private SshWriteThread writeThread = null;

    //写命令到服务器
    private BufferedWriter out = null;

    public SshClient(Server server, WebSocketSession socket) {
        super();
        this.server = server;
        this.socket = socket;
    }

    /**
     * 连接到目标服务器
     *
     * @return
     */
    public boolean connect() {

        try {

            String hostname = this.server.getHostname();
            String username = this.server.getUsername();
            String password = this.server.getPassword();

            // 建立连接
            conn = new Connection(hostname, 22);

            // 连接
            conn.connect();

            // 授权
            boolean isAuthenticate = conn.authenticateWithPassword(username, password);

            if (isAuthenticate) {

                // 打开连接
                session = conn.openSession();

                // 打开bash
                //session.requestPTY("bash");
                session.requestPTY("xterm", 90, 30, 0, 0, null);

                // 启动shell
                session.startShell();

                // 向客户端写数据
                startWriter();

                // 输出流
                out = new BufferedWriter(new OutputStreamWriter(session.getStdin(), "utf-8"));

                // 开启term
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向服务器端写数据
     */
    private void startWriter() {
        // 启动多线程，来获取我们运行的结果
        // 第一个参数 输入流
        // 第二个参数 输出流，这个直接输出的是控制台
        writeThread = new SshWriteThread(session.getStdout(), socket);
        new Thread(writeThread).start();

    }

    /**
     * 写数据到服务器，让机器执行命令
     *
     * @param cmd
     * @return
     */
    public boolean write(String cmd) {
        try {
            this.out.write(cmd);
            this.out.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 关闭连接
     */
    public void disconnect() {
        try {
            //将与服务器端的连接关闭掉，并设置为空
            conn.close();
            session.close();
            session = null;
            conn = null;
            writeThread.stopThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

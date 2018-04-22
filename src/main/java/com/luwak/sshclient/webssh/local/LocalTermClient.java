package com.luwak.sshclient.webssh.local;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ChenXc
 * @version V1.0
 * @Date 2018/4/22 下午9:21
 * @Description TODO(本地term客户端)
 */
public class LocalTermClient {

    // 与客户端连接的socket回话
    private WebSocketSession session;

    /**
     * 获取本地
     * @param session
     */
    public LocalTermClient(WebSocketSession session) {
        super();
        this.session = session;
    }

    /**
     * 执行命令
     * @param commandStr
     */
    public void exeCmd(String commandStr) {
        try {
            //执行命令
            Process p = Runtime.getRuntime().exec(commandStr);

            //获取输出流
            InputStream in = p.getInputStream();

            //写数据到客户端
            writeToWeb(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写数据到web控制界面
     * @param in
     */
    private void writeToWeb(InputStream in) {

        try {
            //定义一个缓存
            //一个UDP 的用户数据报的数据字段长度为8192字节
            byte [] buff = new byte[8192];

            int len =0;
            StringBuffer sb = new StringBuffer();
            while((len = in.read(buff)) > 0) {
                //设定从0 开始
                sb.setLength(0);

                //读取数组里面的数据，进行补码
                for (int i = 0; i < len; i++){
                    //进行补码操作
                    char c = (char) (buff[i] & 0xff);
                    sb.append(c);
                }
                //写数据到服务器端
                session.sendMessage(new TextMessage(new String(sb.toString().getBytes("ISO-8859-1"),"UTF-8")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

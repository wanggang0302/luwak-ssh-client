package com.luwak.sshclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableWebSocket
public class SshClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SshClientApplication.class, args);
	}
}

package com;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/namooChatBroadcasting")
public class ChatSocket {
	public static Set<Session> setClients = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnMessage	// 클라이언트로부터 메시지가 도착했을 때
	public void onMessage(String message, Session session) throws Exception {
		System.out.println("from client : " + message);
		synchronized(setClients) {	// 동기화 처리
			for(Session client : setClients) {
				if(!client.equals(session)) {
					client.getBasicRemote().sendText(message);
				}
			}
		}
	}
	
	@OnOpen		// 새로운 클라이언트가 서버로 접속했을 때
	public void onOpen(Session session) {
		setClients.add(session);
		System.out.println("새로운 클라이언트! 현재 참여 : " + setClients.size() + "명입니다.");
	}

	@OnClose	// 클라이언트의 접속이 끊어졌을 때
	public void onClose(Session session) {
		setClients.remove(session);
		System.out.println("클라이언트 나감! 현재 참여 : " + setClients.size() + "명입니다.");
	}
}
package util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import dao.NamooServiceTalkDao;

@ServerEndpoint("/chat")
public class BroadSocket { // 중복x 순서 x
   public static Set<Session> setClients = Collections.synchronizedSet(new HashSet<Session>());
   
   @OnMessage // 클라이언트로부터 메세지가 도착했을 때
   public void onMessage(String message, Session session)throws Exception {
      System.out.println("from client : " + message);
      
      // message형식 : "chat///5(몇번방)///1(누가)///0(누구에게)///누구세요(라고 말했다)"
      if(message!=null && message.startsWith("chat")) {
    	  int serviceTalkroomIdx = Integer.parseInt(message.split("///")[1]);
    	  int from = Integer.parseInt(message.split("///")[2]);
    	  int to = Integer.parseInt(message.split("///")[3]);
    	  String msg = message.split("///")[4];
    	  
    	  NamooServiceTalkDao dao = new NamooServiceTalkDao();
    	  dao.serviceTalkSendMessage(serviceTalkroomIdx, from, msg);
      }
      
      synchronized(setClients) { //(동기화처리)
         for(Session client : setClients) {
            if(!client.equals(session)) {
               client.getBasicRemote().sendText(message);
            }
         }
      }
   }
   
   @OnOpen //새로운 클라이언트가 서버로 접속했을 때
   public void onOpen(Session session) {
      setClients.add(session);
      System.out.println("새로운 클라이언트! / 현재참여 : " + setClients.size() + "명입니다");
   }
   
   @OnClose //클라이언트 접속이 끊어졌을 때
   public void onClose(Session session) {
      setClients.remove(session);
      System.out.println("클라이언트 나감! / 현재참여 : " + setClients.size() + "명입니다");

   }

}
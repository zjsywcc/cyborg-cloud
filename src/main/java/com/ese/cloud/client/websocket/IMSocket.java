//package org.rencong.supersystem.websocket;
//
//import org.springframework.stereotype.Component;
//
//import javax.websocket.OnClose;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.concurrent.CopyOnWriteArraySet;
//
///**
// * 即时通信socket接口
// * Created by rencong on 16/9/27.
// */
//@ServerEndpoint("/im_socket")
//@Component
//public class IMSocket {
//
//    private static int onlineCount = 0;
//
//    //链接对象
//    private static CopyOnWriteArraySet<IMSocket> webSocketSet = new CopyOnWriteArraySet<>();
//
//    private Session session;
//
//    @OnOpen
//    public void onOpen (Session session){
//        this.session = session;
//        webSocketSet.add(this);
//    }
//
//    @OnClose
//    public void onClose (){
//        webSocketSet.remove(this);
//    }
//
//    @OnMessage
//    public void onMessage (String message, Session session) throws IOException {
//        System.out.println("来自客户端的消息:" + message);
//        // 群发消息
//        /*for ( MyWebSocket item : webSocketSet ){
//            item.sendMessage(message);
//        }*/
//        session.getBasicRemote().sendText(message);
//
//    }
//
//    public void sendMessage (String message) throws IOException {
//        this.session.getBasicRemote().sendText(message);
//    }
//
//
//}

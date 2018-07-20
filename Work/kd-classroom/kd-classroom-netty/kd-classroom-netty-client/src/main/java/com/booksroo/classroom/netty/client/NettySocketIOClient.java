package com.booksroo.classroom.netty.client;

import com.alibaba.fastjson.JSON;

import com.booksroo.classroom.netty.client.handler.SocketHandler;
import com.booksroo.classroom.netty.common.bean.SocketData;
import com.booksroo.classroom.netty.common.request.SocketRequest;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.util.List;


/**
 * @author liujianjian
 * @date 2018/6/20 14:05
 */
public class NettySocketIOClient {

    private SocketRequest socketRequest;

    protected Socket socket;

    private SocketHandler socketHandler;

    public NettySocketIOClient(SocketRequest request, SocketHandler socketHandler) throws Exception {
//        IO.Options opts = new IO.Options();
//        opts.transports = new String[]{WebSocket.NAME};
        socket = IO.socket(request.getUrl(), null);
        this.socketHandler = socketHandler;
        this.socketRequest = request;
        init();
        connect();
    }

    private void init() {
        socket.on(Socket.EVENT_CONNECT, new OnConnectListener())
                .on(Socket.EVENT_DISCONNECT, new OnDisconnectListener())
                .on(Socket.EVENT_CONNECT_TIMEOUT, new OnConnectTimeoutListener())
                .on(Socket.EVENT_RECONNECT, new OnReconnectListener())
                .on(Socket.EVENT_CONNECT_ERROR, new OnConnectErrorListener());
        onEvent();
    }

    public void connect() {
        socket.connect();
    }

    protected void onEvent() {
    }

    protected void sendData(String data) {
    }

    public void sendData(SocketData data) {
    }

    public void sendData(List<SocketData> data) {
    }

    public void disconnect() {
        socket.disconnect();
    }

    public SocketRequest getSocketRequest() {
        return socketRequest;
    }

    public void setSocketRequest(SocketRequest socketRequest) {
        this.socketRequest = socketRequest;
    }

    //消息接收和处理
    public class EventListener implements Emitter.Listener {
        @Override
        public void call(Object... args) {
            String data = "";
            if (args.length > 0) {
                data = (String) args[0];
            }
            socketHandler.handleData(data);
        }
    }

    //建立连接时处理
    class OnConnectListener implements Emitter.Listener {
        @Override
        public void call(Object... args) {
            String data = "";
            if (args.length > 0) {
                data = JSON.toJSONString(args);
            }
            socketHandler.handleConnect(data);
        }
    }

    //断开连接时处理
    class OnDisconnectListener implements Emitter.Listener {
        @Override
        public void call(Object... args) {
            String data = "";
            if (args.length > 0) {
                data = JSON.toJSONString(args);
            }
            socketHandler.handleDisconnect(data);
        }
    }

    class OnConnectTimeoutListener implements Emitter.Listener {
        @Override
        public void call(Object... args) {
            String data = "";
            if (args.length > 0) {
                data = JSON.toJSONString(args);
            }
            socketHandler.handleConnectTimeout(data);
        }
    }

    class OnReconnectListener implements Emitter.Listener {
        @Override
        public void call(Object... args) {
            String data = "";
            if (args.length > 0) {
                data = JSON.toJSONString(args);
            }
            socketHandler.handleReConnect(data);

            //客户端重连的话给服务端发个重连消息
            SocketRequest req = getSocketRequest();
            SocketData sd = new SocketData(req.getClientId(), "server", "", req.getUserType() + "_reconnect");
            sendData(sd);
        }
    }

    class OnConnectErrorListener implements Emitter.Listener {
        @Override
        public void call(Object... args) {
            String data = "";
            if (args.length > 0) {
                data = JSON.toJSONString(args);
            }
            socketHandler.handleConnectError(data);
        }
    }

    public SocketHandler getSocketHandler() {
        return socketHandler;
    }

    public void setSocketHandler(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}

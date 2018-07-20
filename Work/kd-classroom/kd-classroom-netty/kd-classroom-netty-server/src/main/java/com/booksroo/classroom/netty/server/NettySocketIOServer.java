package com.booksroo.classroom.netty.server;

import com.alibaba.fastjson.JSON;
import com.booksroo.classroom.netty.common.bean.ClientInfo;
import com.booksroo.classroom.netty.server.listener.StudentDataListener;
import com.booksroo.classroom.netty.server.listener.TeacherDataListener;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.booksroo.classroom.netty.common.constant.EventKeyConstant.EVENT_DATA_STUDENT;
import static com.booksroo.classroom.netty.common.constant.EventKeyConstant.EVENT_DATA_TEACHER;
import static com.booksroo.classroom.netty.common.constant.PropertyValConstant.*;

import org.apache.log4j.Logger;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author liujianjian
 * @date 2018/6/15 18:05
 */
@Service("nettySocketIOServer")
public class NettySocketIOServer implements InitializingBean {

    protected final Logger log = Logger.getLogger(this.getClass());

    private SocketIOServer server;

    @Autowired
    private BizSocketService bizSocketService;
    @Autowired
    private TeacherDataListener<String> teacherDataListener;
    @Autowired
    private StudentDataListener<String> studentDataListener;

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    public void stop() {
        if (server == null) return;
        server.stop();
        log.info("socketServer stopped ...");
    }

    public NettySocketIOServer createServer(String host, Integer port) {
        try {
            Configuration config = new Configuration();
            //服务器主机ip，这里配置本机
            config.setHostname(StringUtils.isBlank(host) ? SERVER_HOST : host);
            //端口，任意
            config.setPort(port == null ? SERVER_PORT : port);
            config.setMaxFramePayloadLength(1024 * 1024);
            config.setMaxHttpContentLength(1024 * 1024);
//        config.setPingTimeout(30000);
//        config.setPingInterval(15000);
            server = new SocketIOServer(config);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("createServer error: " + e.getMessage());
        }
        return this;
    }

    public void start() {
        try {
//        server.addNamespace(NAMESPACE_BIZ_TEACHER);
//        server.addNamespace(NAMESPACE_BIZ_STUDENT);
            if (server == null) throw new Exception("server is null");
            initListener();
            server.start();
            log.info("socketServer start, port:" + server.getConfiguration().getPort());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("socketServer start error: " + e.getMessage());
        }
    }

    public void initListener() {
        onConnect();
        onDisconnect();
        //监听教师消息
        server.addEventListener(EVENT_DATA_TEACHER, String.class, teacherDataListener);
    }

    public void onConnect() {
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {

                ClientInfo info = bizSocketService.bindClientInfo(client);
                log.info(info.getUserType() + " 客户端已连接, clientInfo:" + JSON.toJSONString(info));

                if (info.isTeacherClient()) {
                    //添加命名空间
                    String name = info.getNamespace1();
                    SocketIONamespace namespace = server.getNamespace(name);
                    if (namespace == null) {
                        namespace = server.addNamespace(name);
                        //该命令空间下监听学生端消息
                        namespace.addEventListener(EVENT_DATA_STUDENT, String.class, studentDataListener);
                    }
                }
                if (info.isStudentClient()) {
                    //如果是学生端连接的话，先发一遍教师之前已发送过的数据
                    bizSocketService.sendStudentCacheData(info.getNamespace0(), info);
                }

//                StringBuffer sb = new StringBuffer();
//                for (SocketIONamespace n : server.getAllNamespaces()) {
//                    sb.append(n.getName()).append(",");
//                }
//
//                log.info("current namespaces: " + sb.toString());
            }
        });
    }

    public void onDisconnect() {
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                ClientInfo info = bizSocketService.bindClientInfo(client);
                log.info(info.getUserType() + " 客户端已断开连接, clientInfo:" + JSON.toJSONString(info));

                BizSocketService.CLIENT_MAP.remove(info.getClientId());
            }
        });
    }

    public SocketIOServer getServer() {
        return server;
    }

    public void setServer(SocketIOServer server) {
        this.server = server;
    }
}

package com.booksroo.classroom.netty.server.listener;

import com.booksroo.classroom.netty.common.bean.ClientInfo;
import com.booksroo.classroom.netty.common.bean.SocketData;
import com.booksroo.classroom.netty.common.util.BizSocketUtil;
import com.booksroo.classroom.netty.server.BizSocketService;
import com.booksroo.classroom.netty.server.NettySocketIOServer;
import com.booksroo.classroom.netty.server.task.BizSocketTask;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.booksroo.classroom.netty.common.constant.EventKeyConstant.EVENT_DATA_STUDENT;

/**
 * @author liujianjian
 * @date 2018/6/23 18:03
 */
@Component("teacherDataListener")
public class TeacherDataListener<T> implements DataListener<T> {

    protected final Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private BizSocketService bizSocketService;
    @Autowired
    private BizSocketTask bizSocketTask;

    @Override
    public void onData(SocketIOClient client, T data, AckRequest ackSender) throws Exception {
//        bizSocketTask.execute(new Runnable() {
//        @Override
//        public void run () {
        try {
            if (data == null) return;
            String dataStr = String.valueOf(data);
            if (StringUtils.isBlank(dataStr)) return;

            ClientInfo info = bizSocketService.bindClientInfo(client);
            log.info("sync received data from teacher, clientId: " + info.getClientId() + ", namespace0: " + info.getNamespace0() + ", namespace1: " + info.getNamespace1() + ", data: " + dataStr);

            SocketIONamespace namespace = bizSocketService.getServer().getNamespace(info.getNamespace1());

            log.info("sync send teacher data to student, namespace:" + (namespace != null ? namespace.getName() : "null"));
            bizSocketService.sendStudentData(namespace, dataStr);

            // 把老师上课时的指令数据存到缓存中，如果有断线重连的，需要重新把数据推送到学生端
            bizSocketService.setTeacherOnClassData2Cache(info.getTeacherClassId(), info.getSubjectId(), dataStr);
        } catch (Exception e) {
            log.error("接收处理教师端消息时异常: " + e.getMessage(), e);
        }
//        }
//        });
    }


}

package com.booksroo.classroom.netty.server.listener;

import com.alibaba.fastjson.JSONObject;
import com.booksroo.classroom.netty.common.bean.ClientInfo;
import com.booksroo.classroom.netty.common.bean.SocketData;
import com.booksroo.classroom.netty.common.constant.InstructionEnum;
import com.booksroo.classroom.netty.common.util.BizSocketUtil;
import com.booksroo.classroom.netty.server.BizSocketService;
import com.booksroo.classroom.netty.server.NettySocketIOServer;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liujianjian
 * @date 2018/6/23 18:03
 */
@Component("studentDataListener")
public class StudentDataListener<T> implements DataListener<T> {

    protected final Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private BizSocketService bizSocketService;

    @Override
    public void onData(SocketIOClient client, T data, AckRequest ackSender) throws Exception {
        try {
            if (data == null) return;

            String dataStr = String.valueOf(data);
            if (StringUtils.isBlank(dataStr)) return;

            ClientInfo info = bizSocketService.bindClientInfo(client);
            if (dataStr.startsWith("{")) {
                SocketData sd = BizSocketUtil.toSocketData(dataStr);
                if (sd == null) return;

                if (InstructionEnum.isStudentReconnect(sd.getInstruction())) {
                    //如果学生断线重连的话，重新发送下教师之前的数据
                    bizSocketService.sendStudentCacheData(info.getNamespace0(), info);
                }
            }
            log.info("received data from student, clientId: " + info.getClientId() + ", namespace0: " + info.getNamespace0() + ", namespace1: " + info.getNamespace1() + ", data: " + dataStr);

        } catch (Exception e) {
            log.error("接收处理学生端消息时异常: " + e.getMessage(), e);
        }
    }
}

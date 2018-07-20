package com.booksroo.classroom.netty.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.booksroo.classroom.common.third.service.JedisService;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.netty.common.bean.ClientInfo;
import com.booksroo.classroom.netty.common.bean.SocketData;
import com.booksroo.classroom.netty.common.constant.InstructionEnum;
import com.booksroo.classroom.netty.common.util.BizSocketUtil;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import static com.booksroo.classroom.common.constant.CacheKeyConstant.TEACHER_ON_CLASS_DATA_CACHE_KEY_PRE;
import static com.booksroo.classroom.common.constant.CacheKeyConstant.TEACHER_START_CLASS_DATA_CACHE_KEY;
import static com.booksroo.classroom.netty.common.constant.EventKeyConstant.EVENT_DATA_STUDENT;

@Service("bizSocketService")
public class BizSocketService {
    protected final Logger log = Logger.getLogger(this.getClass());

    public static final ConcurrentHashMap<String, ClientInfo> CLIENT_MAP = new ConcurrentHashMap<>();

    @Autowired
    private NettySocketIOServer nettySocketIOServer;

    @Autowired
    private JedisService jedisService;

    //给学生发数据
    public void sendStudentData(SocketIONamespace namespace, String data) {
        if (namespace == null) return;
        namespace.getBroadcastOperations().sendEvent(EVENT_DATA_STUDENT, data);
    }

    //给学生发教师缓存里的历史数据
    public void sendStudentCacheData(String namespace, ClientInfo client) {

        try {
            if (StringUtils.isBlank(namespace)) return;
            SocketIONamespace sn = getServer().getNamespace(namespace);
            if (sn == null) return;

            List<SocketData> list = getTeacherOnClassCacheData(client.getTeacherClassId(), client.getSubjectId());
            log.info("begin send teacher on class all cache data to student, namespace:" + namespace + ", list.size:" + (list != null ? list.size() : "list is null"));

            if (CollectionUtils.isEmpty(list)) return;
            sendStudentData(sn, JSON.toJSONString(list));

            log.info("end send teacher on class all cache data to student, namespace:" + namespace);

        } catch (Exception e) {
            log.error("namespace:" + namespace + ", tcId:" + client.getTeacherClassId() + ", subjectId:" + client.getSubjectId() + "给学生发送教师缓存中数据出错: " + e.getMessage(), e);
        }
    }

    //把老师当堂课发送的数据存到缓存中
    public void setTeacherOnClassData2Cache(long teacherClassId, long subjectId, String data) {
        try {
            if (StringUtils.isBlank(data) || (!data.startsWith("{") && !data.startsWith("["))) return;

            List<SocketData> list = getTeacherOnClassCacheData(teacherClassId, subjectId);
            if (list == null) list = new ArrayList<>();

            SocketData sd = BizSocketUtil.toSocketData(data);
            if (sd != null) {
                list.add(sd);
                // 如果是开始上课的指令，把数据放到缓存里，定时任务5秒一次推送到学生端，如果是退出上课指令，清空缓存数据；
                if (InstructionEnum.isTeacherStartClass(sd.getInstruction())) {
                    setTeacherInClassCache(teacherClassId, subjectId);
                }
                if (InstructionEnum.isTeacherEndClass(sd.getInstruction())) {
                    delTeacherInClassCache(teacherClassId, subjectId);
                }

            } else {
                JSONArray arr = JSON.parseArray(data);
                for (int i = 0; i < arr.size(); i++) {
                    SocketData temp = BizSocketUtil.toSocketData(arr.getString(i));
                    if (temp == null) continue;
                    list.add(temp);
                }
            }
            jedisService.set(TEACHER_ON_CLASS_DATA_CACHE_KEY_PRE + teacherClassId + "_" + subjectId, JSON.toJSONString(list), 3600L * 2);

        } catch (Exception e) {
            log.error("teacherClassId: " + teacherClassId + ", subjectId: " + subjectId + ", 设置缓存失败: " + e.getMessage());
        }
    }

    public List<SocketData> getTeacherOnClassCacheData(long teacherClassId, long subjectId) {
        try {
            String val = jedisService.get(TEACHER_ON_CLASS_DATA_CACHE_KEY_PRE + teacherClassId + "_" + subjectId);
            if (StringUtils.isBlank(val)) return null;

            if (!val.startsWith("[")) return null;

            JSONArray arr = JSON.parseArray(val);
            if (arr.size() <= 0) return null;

            List<SocketData> list = new ArrayList<>();
            for (int i = 0; i < arr.size(); i++) {
                SocketData sd = JSONObject.parseObject(arr.getString(i), SocketData.class);
                list.add(sd);
            }
            return list;
        } catch (Exception e) {
            log.error(String.format("从缓存中获取教师上课发送的数据失败, teacherClassId:%s, subjectId:%s, msg:%s", teacherClassId, subjectId, e.getMessage()), e);
        }
        return null;
    }

    //老师是否正在上课
    public Set<String> getTeacherInClassList() {
        String val = jedisService.get(TEACHER_START_CLASS_DATA_CACHE_KEY);
        return BizUtil.jsonStrToSet(val);
    }

    public void setTeacherInClassCache(long teacherClassId, long subjectId) {
        Set<String> list = getTeacherInClassList();
        if (list == null) list = new HashSet<>();

        list.add(teacherClassId + "_" + subjectId);
        jedisService.set(TEACHER_START_CLASS_DATA_CACHE_KEY, JSON.toJSONString(list), 3600L * 2);
    }

    public void delTeacherInClassCache(long teacherClassId, long subjectId) {
        Set<String> list = getTeacherInClassList();
        if (CollectionUtils.isEmpty(list)) return;

        list.removeIf(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return (teacherClassId + "_" + subjectId).equals(s);
            }
        });

        jedisService.set(TEACHER_START_CLASS_DATA_CACHE_KEY, JSON.toJSONString(list), 3600L * 2);
    }

    public ClientInfo bindClientInfo(SocketIOClient client) {

        String sa = client.getRemoteAddress().toString();
        String clientIp = sa.substring(1, sa.indexOf(":"));//获取设备ip

        String clientId = getClientId(client);

        ClientInfo info = CLIENT_MAP.get(clientId);
        if (info != null) return info;
        String namespace0 = client.getHandshakeData().getSingleUrlParam("namespace0");
        String namespace1 = client.getHandshakeData().getSingleUrlParam("namespace1");

        String userType = null;
        Long teacherId = null, studentId = null, tcId = null, subjectId = null;
        if (StringUtils.isNotBlank(clientId)) {
            String[] arr = clientId.split("_");
            userType = arr[4];
            if ("teacher".equalsIgnoreCase(userType)) {
                teacherId = Long.parseLong(arr[1]);
            }
            if ("student".equalsIgnoreCase(userType)) {
                studentId = Long.parseLong(arr[0]);
            }
            tcId = Long.parseLong(arr[2]);
            subjectId = Long.parseLong(arr[3]);
        }

        info = ClientInfo.newInstance(userType, teacherId, studentId, tcId, subjectId);
        info.setClientIp(clientIp);
        info.setClientId(clientId);
        if (StringUtils.isNotBlank(namespace1))
            info.setNamespace1(namespace1.startsWith("/") ? namespace1 : "/" + namespace1);
        if (StringUtils.isNotBlank(namespace0))
            info.setNamespace0(namespace0.startsWith("/") ? namespace0 : "/" + namespace0);

        CLIENT_MAP.put(clientId, info);
        return info;
    }

    public String getClientId(SocketIOClient client) {
        String clientId = client.getHandshakeData().getSingleUrlParam("clientId");
        if (StringUtils.isBlank(clientId)) return "";

        return clientId.replaceAll("/", "");
    }

    public SocketIOServer getServer() {
        return nettySocketIOServer.getServer();
    }

}

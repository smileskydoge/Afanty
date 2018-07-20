package com.booksroo.classroom.web.third;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("appTokenService")
public class AppTokenService {

    public static void main(String[] args) throws Exception {
        AppTokenService as = new AppTokenService();
        System.out.println(JSON.toJSONString(as.getSTSResp("teacher_10000")));
    }

    // 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
    public static final String REGION_CN_HANGZHOU = "cn-hangzhou";
    public static final String STS_API_VERSION = "2015-04-01";

    private static Map<String, String> configMap = null;

    static {
       /* {
            "AccessKeyID" : "",
                "AccessKeySecret" : "",
                "RoleArn" : "",
                "TokenExpireTime" : "900",
                "PolicyFile": "policy/all_policy.txt"
        }*/

        String policy = "{\"Statement\": [{\"Action\": [\"oss:*\"],\"Effect\": \"Allow\",\"Resource\": [\"acs:oss:*:*:*\"]}],\"Version\": \"1\"}";
        configMap = new HashMap<>();
        configMap.put("accessKeyID", "LTAILSI3hyq60t6j");
        configMap.put("accessKeySecret", "sDqOgNBrgYjwykdTzSBvKy2zYynFAb");
        configMap.put("roleArn", "acs:ram::1903407798655412:role/aliyunosstokengeneratorrole");
        configMap.put("policy", policy);
    }

    private AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret, String roleArn,
                                         String roleSessionName, String policy, ProtocolType protocolType, long durationSeconds) throws Exception {
        // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
        IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        // 创建一个 AssumeRoleRequest 并设置请求参数
        final AssumeRoleRequest request = new AssumeRoleRequest();
        request.setVersion(STS_API_VERSION);
        request.setMethod(MethodType.POST);
        request.setProtocol(protocolType);

        request.setRoleArn(roleArn);
        request.setRoleSessionName(roleSessionName);
        request.setPolicy(policy);
        request.setDurationSeconds(durationSeconds);

        // 发起请求，并得到response
        return client.getAcsResponse(request);
    }

    public AssumeRoleResponse.Credentials getSTSResp(String roleSessionName) throws Exception {

        // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
        // 具体规则请参考API文档中的格式要求

        // 此处必须为 HTTPS
        ProtocolType protocolType = ProtocolType.HTTPS;

        final AssumeRoleResponse stsResponse = assumeRole(configMap.get("accessKeyID"), configMap.get("accessKeySecret"), configMap.get("roleArn"), roleSessionName,
                configMap.get("policy"), protocolType, 3600L);

//        Map<String, String> respMap = new LinkedHashMap<String, String>();
//        respMap.put("StatusCode", "200");
//        respMap.put("AccessKeyId", stsResponse.getCredentials().getAccessKeyId());
//        respMap.put("AccessKeySecret", stsResponse.getCredentials().getAccessKeySecret());
//        respMap.put("SecurityToken", stsResponse.getCredentials().getSecurityToken());
//        respMap.put("Expiration", stsResponse.getCredentials().getExpiration());
        return stsResponse.getCredentials();
    }
}

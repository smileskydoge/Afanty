package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.booksroo.classroom.common.constant.BizConstant;
import com.booksroo.classroom.common.domain.Subject;
import com.booksroo.classroom.common.enums.SubjectEnum;
import com.booksroo.classroom.common.util.BizUtil;
import com.booksroo.classroom.common.util.EncoderUtil;
import com.booksroo.classroom.common.vo.FileResourceVo;
import com.booksroo.classroom.common.vo.StatisticsVo;
import com.booksroo.classroom.common.vo.SubjectVo;
import com.booksroo.classroom.common.vo.TeacherClassVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.Predicate;

/**
 * @author liujianjian
 * @date 2018/6/6 15:37
 */
public class Testt {

    public static void main(String[] args) throws Exception {

        Integer n = null;

    }
}

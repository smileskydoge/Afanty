import com.aliyun.openservices.ons.api.*;

import java.util.Date;
import java.util.Properties;

/**
 * @author liujianjian
 * @date 2018/6/19 13:46
 */
public class ProducerTest {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ProducerId, "PID_classroom_teacher_test");
        properties.put(PropertyKeyConst.AccessKey,"LTAIgAfb0btgnA1C");
        properties.put(PropertyKeyConst.SecretKey, "OfVFgXpCVDrRjwA48mCUXcbmVgi4dj");
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
        properties.put(PropertyKeyConst.ONSAddr,
                "http://onsaddr-internet.aliyun.com/rocketmq/nsaddr4client-internet");
        Producer producer = ONSFactory.createProducer(properties);
        producer.start();

        //循环发送消息
        for (int i = 0; i < 1; i++){
            Message msg = new Message("classroom_topic_app_client_teacher_test", "TagA", "Hello MQ".getBytes());
            msg.setKey("ORDERID_" + i);
            try {
                SendResult sendResult = producer.send(msg);
                // 同步发送消息，只要不抛异常就是成功
                if (sendResult != null) {
                    System.out.println(new Date() + " Send mq message success. Topic is:" + msg.getTopic() + " msgId is: " + sendResult.getMessageId());
                }
            }
            catch (Exception e) {
                // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
                System.out.println(new Date() + " Send mq message failed. Topic is:" + msg.getTopic());
                e.printStackTrace();
            }
        }
        // 注意：如果不销毁也没有问题
        producer.shutdown();
    }
}
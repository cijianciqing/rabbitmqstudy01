package exchanges.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import util.RabbitMqUtils;


public class TopicProducer {
    private static final String EXCHANGE_NAME = "cftopic";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        for(int i=0;i<5;i++){
            String message1 = "child1-message"+i;
            String message2 = "child2-message"+i;
            channel.basicPublish(EXCHANGE_NAME,"topic.child1",null, message1.getBytes("UTF-8"));
            channel.basicPublish(EXCHANGE_NAME,"topic.child2",null, message2.getBytes("UTF-8"));
            System.out.println("发送消息完成:" + message1);
            System.out.println("发送消息完成:" + message2);
        }


    }

}
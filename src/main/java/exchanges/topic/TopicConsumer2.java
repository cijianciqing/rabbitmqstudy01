package exchanges.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import util.RabbitMqUtils;


public class TopicConsumer2 {


    private static final String EXCHANGE_NAME = "cftopic";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String queue = "q2";
        channel.queueDeclare(queue,false,false,false,null);
        //在exchange上绑定queue
        channel.queueBind(queue,EXCHANGE_NAME,"topic.child1");

        //消息消费的时候如何处理消息
        DeliverCallback deliverCallback=(consumerTag,delivery)->{
            String message= new String(delivery.getBody());
            System.out.println("TopicConsumer2 接收到消息:"+message);
        };

        CancelCallback cancelCallback=(consumerTag)->{
            System.out.println(consumerTag+"TopicConsumer2 消费者取消消费接口回调逻辑");};

        //采用手动应答
        boolean autoAck=false;
        channel.basicConsume(queue,autoAck,deliverCallback,cancelCallback);
    }
}
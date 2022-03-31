package exchanges.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import util.RabbitMqUtils;


public class DirectConsumer1 {


    private static final String EXCHANGE_NAME = "cfdirect";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        /*
        * 随机生成临时队列
        * */
        String queue = channel.queueDeclare().getQueue();
        //在exchange上绑定一个queue
        channel.queueBind(queue,EXCHANGE_NAME,"rk2");

        //消息消费的时候如何处理消息
        DeliverCallback deliverCallback=(consumerTag,delivery)->{
            String message= new String(delivery.getBody());
            System.out.println("Direct2 接收到消息:"+message);
        };

        CancelCallback cancelCallback=(consumerTag)->{
            System.out.println(consumerTag+"Direct2 消费者取消消费接口回调逻辑");};

        //采用手动应答
        boolean autoAck=false;
        channel.basicConsume(queue,autoAck,deliverCallback,cancelCallback);
    }
}
package exchanges.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import util.RabbitMqUtils;


public class FanOutConsumer2 {


    private static final String EXCHANGE_NAME = "cjfanout";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        /*
        * 随机生成临时队列
        * */
        String queue = channel.queueDeclare().getQueue();
        //在exchange上绑定一个queue
        channel.queueBind(queue,EXCHANGE_NAME,"rk2");

        //消息消费的时候如何处理消息
        DeliverCallback deliverCallback=(consumerTag,delivery)->{
            String message= new String(delivery.getBody());
            System.out.println("FanOunt2接收到消息:"+message);
        };
            //采用手动应答
        boolean autoAck=false;
        CancelCallback cancelCallback=(consumerTag)->{
            System.out.println(consumerTag+"FanOunt2消费者取消消费接口回调逻辑");};


        channel.basicConsume(queue,true,deliverCallback,cancelCallback);
    }
}
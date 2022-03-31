package exchanges.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import util.RabbitMqUtils;


public class FanOutProducer {
    private static final String EXCHANGE_NAME = "cjfanout";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        for(int i=0;i<5;i++){
            String message = "message"+i;
            channel.basicPublish(EXCHANGE_NAME,"rk1",null, message.getBytes("UTF-8"));
            System.out.println("发送消息完成:" + message);
        }
    }

}
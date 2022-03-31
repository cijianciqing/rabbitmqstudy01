package exchanges.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import util.RabbitMqUtils;


public class DirectProducer {
    private static final String EXCHANGE_NAME = "cfdirect";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        for(int i=0;i<5;i++){
            String message1 = "rk1-message"+i;
            String message2 = "rk2-message"+i;
            channel.basicPublish(EXCHANGE_NAME,"rk1",null, message1.getBytes("UTF-8"));
            channel.basicPublish(EXCHANGE_NAME,"rk2",null, message2.getBytes("UTF-8"));
            System.out.println("发送消息完成:" + message1);
            System.out.println("发送消息完成:" + message2);
        }


    }

}
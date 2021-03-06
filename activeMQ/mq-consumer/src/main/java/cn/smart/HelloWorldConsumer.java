package cn.smart;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class HelloWorldConsumer {

    /**
     * 消费消息
     */
    public void receiveHelloWorldActiveMQ(){
        //定义链接工厂
        ConnectionFactory connectionFactory = null;

        //定义链接对象
        Connection connection = null;

        //定义绘画
        Session session = null;

        //目的地
        Destination destination = null;

        //消息的消费者
        MessageConsumer consumer = null;

        //定义消息
        Message message = null;

        try {
            /**
             * username:访问ActiveMQ服务的用户名。用户密码。默认admin。用户名可以通过jetty-ream.properties文件修改
             * password:访问ActiveMQ服务的用户名。用户密码。默认admin。用户名可以通过jetty-ream.properties文件修改
             * brokerURL:访问ActiveMQ服务的路径地址:协议名://主机地址:端口号
             */
            connectionFactory = new ActiveMQConnectionFactory("admin","admin","tcp://192.168.66.186:61616");
            //创建链接对象
            connection = connectionFactory.createConnection();

            //启动链接
            connection.start();

            /**
             * transacted:是否使用事务:Boolean
             *      true表示使用事务,Session.SESSION_TRANSACTED
             *      false不使用事务,是指此变量acknowledgeMode参数必须设置
             * acknowledgeMode:
             *      Session.AUTO_ACKNOWLEDGE:表示自动确认机制
             *      Session.CLIENT_ACKNOWLEDGE:客户端确认机制
             *      Session.DUPS_OK_ACKNOWLEDGE:有副本的客户端确认消息机制
             */
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

            //创建目的地:目的地名称即队列的名称。消息的消费者需要通过消息名称访问对应队列
            destination = session.createQueue("helloworld-destination");

            //创建消息消费者
            consumer = session.createConsumer(destination);

            //创建消息对象
            message = consumer.receive();

            //处理消息
            String text = ((TextMessage) message).getText();

            System.out.println("从ActiveMQ服务中获取的文本消息"+text);


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (consumer != null){
                try {
                    consumer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

            if (session != null){
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}

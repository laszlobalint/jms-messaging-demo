package hu.balint.laszlo.jmsmessagingdemo.listener;

import hu.balint.laszlo.jmsmessagingdemo.configuration.JmsConfig;
import hu.balint.laszlo.jmsmessagingdemo.model.IntroMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class IntroListener {

    private final JmsTemplate jmsTemplate;

/*    @JmsListener(destination = JmsConfig.TEST_QUEUE)
    public void listenMessages(@Payload IntroMessage introMessage, @Headers MessageHeaders headers, Message message) {
        System.out.println(introMessage);
    }*/

    @JmsListener(destination = JmsConfig.SEND_AND_RECEIVE_QUEUE)
    public void listenForSentMessage(@Payload String introMessage, @Headers MessageHeaders headers, Message message) {
        System.out.printf("Indicator message: %s%n", introMessage);

        IntroMessage reply = IntroMessage
                .builder()
                .id(UUID.randomUUID())
                .message("We are here! No worries!")
                .build();

        try {
            jmsTemplate.convertAndSend(message.getJMSReplyTo(), reply);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

package hu.balint.laszlo.jmsmessagingdemo.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.balint.laszlo.jmsmessagingdemo.configuration.JmsConfig;
import hu.balint.laszlo.jmsmessagingdemo.model.IntroMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class IntroSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

/*    @Scheduled(fixedRate = 3000)
    public void sendMessage() {
        System.out.println("Sending a message...");

        IntroMessage message = IntroMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Intro message")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.TEST_QUEUE, message);
    }*/

    @Scheduled(fixedRate = 5000)
    public void sendAndReceiveMessage() {
        IntroMessage message = IntroMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello from the other side.")
                .build();

        Message receivedMessage = jmsTemplate.sendAndReceive(JmsConfig.SEND_AND_RECEIVE_QUEUE, session -> {
            Message introMessage;

            try {
                introMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                introMessage.setStringProperty("_type", "hu.balint.laszlo.jmsmessagingdemo.model.IntroMessage");

                return introMessage;

            } catch (JMSException | JsonProcessingException e) {
                throw new JMSException("Failed to create message!");
            }
        });

        try {
            if (receivedMessage != null)
                System.out.printf("Received message: %s%n", receivedMessage.getBody(IntroMessage.class));

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

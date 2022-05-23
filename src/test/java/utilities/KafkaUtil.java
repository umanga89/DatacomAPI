package utilities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.Level;

public class KafkaUtil extends BaseUtil{
    public static List<String> getKafkaMessages(String topicName, int numberOfRecordsToFetch) {
        try {
            List<String> kafkaMessages = new ArrayList<>();
            String kafkaTopicName = topicName;

            Properties props = new Properties();
            props.put("bootstrap.servers", BaseUtil.props.getProperty("kafka.consumer.host.port"));
            props.put("group.id", "kafka-consumer-test"+kafkaTopicName);
            props.put("max.partition.fetch.bytes", "1048576000");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(Collections.singletonList(kafkaTopicName));

            consumer.poll(10000);

            consumer.assignment().forEach(System.out::println);

            // get the last offsets for each partition
            consumer.endOffsets(consumer.assignment()).forEach((topicPartition, offset) -> {
                System.out.println("offset: "+offset);

                // seek to the last offset of each partition
                consumer.seek(topicPartition, (offset==0) ? offset:offset - numberOfRecordsToFetch);

                // poll to get the last record in each partition
                consumer.poll(10000).forEach(record -> {
                   kafkaMessages.add(record.value());
                });
            });
            consumer.close();
            return kafkaMessages;
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage());
            throw e;
    }
    }

    public static String verifyKakfaMessageForCustomer(List<String> kafkaMsgs, String customerUUID){
        try {
            String messageArray = null;
            for (String message : kafkaMsgs) {
                if (message.contains(customerUUID)) {
                    messageArray =  message;
                    break;
                }
            }
            return messageArray;
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage());
            throw e;
        }
    }

    public static String verifyKakfaMessageForOrder(List<String> kafkaMsgs, String isin){
        try {
            String messageArray = null;
            for (String message : kafkaMsgs) {
                if (message.contains(isin)) {
                    messageArray =  message;
                    break;
                }
            }
            return messageArray;
        }catch (Exception e){
            BaseUtil.logger.log(Level.ERROR,"ERROR Occurred =>\n"+e.getMessage());
            throw e;
        }
    }
}

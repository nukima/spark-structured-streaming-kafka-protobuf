package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import model.Datatracking.DataTracking;

public class Producer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.17.80.26:9092");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "manhnk9-producer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ProtobufSerializer.class.getName());
        props.put("schema.registry.url", "http://172.17.80.26:8081");
        props.put("auto.register.schemas", "true");


        //create a producer
        KafkaProducer<String, DataTracking> producer = new KafkaProducer<>(props);

        // read data from file
        String filePath = "data/messages.csv";
        String line = "";
        String csvSplitBy = ",";
        try {
            java.util.Scanner scanner = new java.util.Scanner(new java.io.File(filePath));
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                // Split line into different fields
                String[] fields = line.split(csvSplitBy);
                // Create a new message
                DataTracking message = DataTracking.newBuilder()
                        .setVersion(fields[0])
                        .setName(fields[1])
                        .setTimestamp(Long.parseLong(fields[2]))
                        .setPhoneId(fields[3])
                        .setLon(Long.parseLong(fields[4]))
                        .setLat(Long.parseLong(fields[5]))
                        .build();
                String key = fields[2];
                producer.send(new ProducerRecord<>("data_tracking_manhnk9", key, message));
            }
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        //close producer
        producer.flush();
        producer.close();
    }
}

package kafka;

import com.google.protobuf.Message;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;

public class ProtobufSerializer<T extends Message> extends KafkaProtobufSerializer<T> {
    @Override
    public byte[] serialize(String topic, T record) {
        return record.toByteArray();
    }
}
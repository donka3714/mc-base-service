package org.acme.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import java.util.Properties;

@ApplicationScoped
public class KafkaConfig {

    @ConfigProperty(name = "app.kafka.bootstrap-servers", defaultValue = "localhost:9092")
    private String kafkaBrokers;

    @ConfigProperty(name = "app.producer.acks", defaultValue = "1")
    private String acks;

    @ConfigProperty(name = "app.consumer.groupId", defaultValue = "kafka-client-quarkus-consumer")
    private String consumerGroupId;

    @ConfigProperty(name = "app.consumer.clientId", defaultValue = "kafka-client-quarkus-consumer-client")
    private String consumerClientId;

    @ConfigProperty(name = "app.consumer.maxPoolRecords", defaultValue = "1000")
    private String maxPoolRecords;

    @ConfigProperty(name = "app.consumer.offsetReset", defaultValue = "earliest")
    private String offsetReset;

    @ConfigProperty(name = "app.consumer.autoCommit", defaultValue = "false")
    String autoCommit;

    @Produces
    @RequestScoped
    public Producer<String, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.putIfAbsent(ProducerConfig.ACKS_CONFIG, acks);
        return new KafkaProducer<>(props);
    }

    @Produces
    @RequestScoped
    public Consumer<String, String> createConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokers);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPoolRecords);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset);
        return new KafkaConsumer<String, String>(props);
    }
}
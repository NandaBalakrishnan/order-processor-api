package com.comp.orderprocessorapi.config;

import com.comp.orderprocessorapi.model.Order;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;
	
	@Bean
	public Map<String, Object> consumerConfig(){
		Map<String, Object> props = new HashMap<>();
	    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
	      bootstrapServers);
	    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
	    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class);
	    return props;
	}
	
	@Bean
	public ConsumerFactory<String, Order> consumerFacory(){
		return new DefaultKafkaConsumerFactory<>(
				consumerConfig(),
				new StringDeserializer(),
				new JsonDeserializer<>(Order.class));
	}	
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Order> orderKafkaListenerContainerFactory() {
	    ConcurrentKafkaListenerContainerFactory<String, Order> factory =
	      new ConcurrentKafkaListenerContainerFactory<>();
	    factory.setConsumerFactory(consumerFacory());
	    return factory;	
	  }
	
}

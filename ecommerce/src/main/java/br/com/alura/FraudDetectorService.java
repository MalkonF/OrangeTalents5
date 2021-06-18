package br.com.alura;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class FraudDetectorService {

    public static void main(String[] args) {
        //cria um consumidor
        var consumer = new KafkaConsumer<String, String>(properties());
        //tópico que vai escutar. Geralmente só escuta um tópico pq cada serviço vai ter um objetivo em específico
        consumer.subscribe(Collections.singletonList("ECOMMERCE_NEW_ORDER"));
        while (true) { //pergunta se tem mensagem no topico pelo tempo de 1000 segundos. Em records vai ta os registros que foram devolvidos
            var records = consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()) {
                System.out.println("Encontrei " + records.count() + " registros");
                for (var record : records) { //p cada um dos registros imprime chave, valor da mensagem, partição da msg e offset
                    System.out.println("------------------------------------------");
                    System.out.println("Processing new order, checking for fraud");
                    System.out.println(record.key());
                    System.out.println(record.value());
                    System.out.println(record.partition());
                    System.out.println(record.offset());
                    try {
                        Thread.sleep(5000);//simula processamento de fraude
                    } catch (InterruptedException e) {
                        // ignoring
                        e.printStackTrace();
                    }
                    System.out.println("Order processed");
                }
            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        //onde ele se conectar para escutar
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // vai transformar de bytes para string. Quando ele produziu a mensagem na outra classe ele serializou foi de string para bytes
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //cria um grupo para o service FraudDetectorService
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService.class.getSimpleName());
        return properties;
    }
}

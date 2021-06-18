package br.com.alura;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //produzir uma msg
        var producer = new KafkaProducer<String, String>(properties());
        //mensagem que vai mandar para o tópico: id_pedido, id_user, valor_compra
        var value = "132123,67523,7894589745";
        // registra o topico, a mensagem vai ser enviada pra ele
        var record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER", value, value);
        //envia uma mensagem registrada no kafka
        producer.send(record, (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }//offset é o numero de mensagens que vc enviou, cada vez que rodar a app vai enviar uma msg
            System.out.println("sucesso enviando " + data.topic() + ":::partition " + data.partition() + "/ offset " +
                    data.offset() + "/ timestamp " + data.timestamp());
        }).get();//o get torna o send assincrono
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        //StringSerializer serializa string para bytes
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}

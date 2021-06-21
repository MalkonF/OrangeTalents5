package br.com.alura;

import org.apache.kafka.clients.consumer.ConsumerRecord;
//a criação dessa interface é para receber o método parse onde este recebe um ConsumerRecord como arg e nao retorna nada
public interface ConsumerFunction {
    void consume(ConsumerRecord<String, String> record);
}

package br.com.alura;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class GsonDeserializer<T> implements Deserializer<T> {

    public static final String TYPE_CONFIG = "br.com.alura.ecommerce.type_config";
    //precisa de uma instancia do Gson
    private final Gson gson = new GsonBuilder().create();
    private Class<T> type;

    @Override//aqui recebe as configurações do KafkaSerice, aquelas que estão no Properties
    public void configure(Map<String, ?> configs, boolean isKey) {
        String typeName = String.valueOf(configs.get(TYPE_CONFIG));
        try {
            this.type = (Class<T>) Class.forName(typeName);//transforma o tipo para uma classe
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Type for deserialization does not exist in the classpath." , e);
        }
    }

    @Override//implementa a função  do Deserializer para deserializar os bytes
    public T deserialize(String s, byte[] bytes) {
        return gson.fromJson(new String(bytes), type);//trasnforma em string
    }
}

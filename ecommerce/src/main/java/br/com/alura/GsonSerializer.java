package br.com.alura;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Serializer;

public class GsonSerializer<T> implements Serializer<T> {

    private final Gson gson = new GsonBuilder().create();

    @Override
    public byte[] serialize(String s, T object) {
        return gson.toJson(object).getBytes();
    }//serializa para json o object que é passado como arg
}
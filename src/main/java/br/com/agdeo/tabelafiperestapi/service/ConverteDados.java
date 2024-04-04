package br.com.agdeo.tabelafiperestapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.List;

public class ConverteDados implements IConsumoApi {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public <T> List<T> obterDadosList(String json, Class<T> classe) {
        try {
            TypeFactory typeFactory = mapper.getTypeFactory();
            return mapper.readValue(json, typeFactory.constructCollectionType(List.class,classe));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

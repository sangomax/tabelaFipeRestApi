package br.com.agdeo.tabelafiperestapi.service;

import java.util.List;

public interface IConsumoApi {

    public <T> T obterDados(String json, Class<T> classe);

    public <T> List<T> obterDadosList(String json, Class<T> classe);

}

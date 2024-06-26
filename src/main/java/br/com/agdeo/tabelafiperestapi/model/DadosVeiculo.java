package br.com.agdeo.tabelafiperestapi.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosVeiculo(@JsonAlias("Valor") String valor,
                           @JsonAlias("Marca")String marca,
                           @JsonAlias("Modelo")String modelo,
                           @JsonAlias("AnoModelo")Integer anoModelo,
                           @JsonAlias("Combustivel")String combustivel,
                           @JsonAlias("CodigoFipe")String codigoFipe,
                           @JsonAlias("MesReferencia")String mesReferencia) {

    @Override
    public String toString() {
        return "Veiculo [" +
                "valor='" + valor + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + anoModelo +
                ", combustivel='" + combustivel + '\'' +
                ']';
    }
}

package br.com.agdeo.tabelafiperestapi.principal;

import br.com.agdeo.tabelafiperestapi.model.DadosBusca;
import br.com.agdeo.tabelafiperestapi.model.DadosModelo;
import br.com.agdeo.tabelafiperestapi.model.DadosVeiculo;
import br.com.agdeo.tabelafiperestapi.service.ConsumoApi;
import br.com.agdeo.tabelafiperestapi.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private static final String PREFIXO_ENDERECO = "https://parallelum.com.br/fipe/api/v1/";

    private List<String> tipoBuscaList = Arrays.asList("carros","motos","caminhoes");

    private Scanner leitura = new Scanner(System.in);
    private ConverteDados converte = new ConverteDados();

    public void buscarCarro() {
        String endereco =  PREFIXO_ENDERECO;
        tipoBuscaList.forEach(System.out::println);
        System.out.println("Digite uma das opções acima:");
        var tipoBuscaEscolhido = leitura.nextLine();
        endereco = endereco.concat(tipoBuscaEscolhido.toLowerCase() + "/marcas/");
        var json = ConsumoApi.obterDados(endereco);
        List<DadosBusca> marcas = converte.obterDadosList(json, DadosBusca.class);
        marcas.forEach(System.out::println);

        System.out.println("Digite o codigo da marca: ");
        var marcaEscolhido = leitura.nextLine();
        endereco = endereco.concat(marcaEscolhido.toLowerCase() + "/modelos/");
        json = ConsumoApi.obterDados(endereco);
        DadosModelo modelos = converte.obterDados(json, DadosModelo.class);
        modelos.modelos().forEach(System.out::println);

        System.out.println("Digite um trecho do nome do carro: ");
        String trechoDoCarro = leitura.nextLine();

        modelos.modelos().stream()
                .filter(v -> v.nome().toLowerCase().contains(trechoDoCarro.toLowerCase()))
                .forEach(System.out::println);

        System.out.println("Digite o codigo do modelo: ");
        var modeloEscolhido = leitura.nextLine();
        endereco = endereco.concat(modeloEscolhido.toLowerCase() + "/anos/");
        json = ConsumoApi.obterDados(endereco);
        List<DadosBusca> anos = converte.obterDadosList(json, DadosBusca.class);

        List<DadosVeiculo> veiculos = new ArrayList<>();
        for (int i = 0; i < anos.size(); i++) {
            json = ConsumoApi.obterDados(endereco + anos.get(i).codigo());
            DadosVeiculo dadosVeiculo = converte.obterDados(json, DadosVeiculo.class);
            veiculos.add(dadosVeiculo);
        }

        veiculos.forEach(System.out::println);






    }
}

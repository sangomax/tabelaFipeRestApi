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

    private List<String> tipoBuscaList = Arrays.asList("Carros", "Motos", "Caminhões");

    private Scanner leitura = new Scanner(System.in);
    private ConverteDados converte = new ConverteDados();

    public void buscarCarro() {
        String endereco = new String();
        System.out.println("*** Opções ***");
        tipoBuscaList.forEach(System.out::println);
        System.out.println("Digite uma das opções acima:");
        var tipoBuscaEscolhido = leitura.nextLine();
        endereco = retornaEnderecoTipoBusca(tipoBuscaEscolhido);


        var json = ConsumoApi.obterDados(endereco);
        List<DadosBusca> marcas = converte.obterDadosList(json, DadosBusca.class);
        marcas.forEach(System.out::println);

        boolean flag = true;

        do {
            System.out.println("Digite o codigo da marca: ");
            var marcaEscolhido = leitura.nextLine();
            if(validaOpcao(marcaEscolhido, marcas, 'c')) {
                endereco = endereco.concat(marcaEscolhido.toLowerCase() + "/modelos/");
                flag = false;
            } else {
                System.out.println("Opção não valida!\n");
            }
        } while (flag);

        json = ConsumoApi.obterDados(endereco);
        DadosModelo modelos = converte.obterDados(json, DadosModelo.class);
        modelos.modelos().forEach(System.out::println);

        flag = true;
        List<DadosBusca> listaVeiculos = new ArrayList<>();
        do {
            System.out.println("Digite um trecho do nome do carro: ");
            String trechoDoCarro = leitura.nextLine();
            if (validaOpcao(trechoDoCarro, modelos.modelos(), 'n')) {
                listaVeiculos = modelos.modelos().stream()
                        .filter(v -> v.nome().toLowerCase().contains(trechoDoCarro.toLowerCase()))
                        .collect(Collectors.toList());
                listaVeiculos.forEach(System.out::println);
                flag = false;
            } else {
                System.out.println("Trecho não encontrado!\n");
            }
        } while (flag);

        flag = true;

        do {
            System.out.println("Digite o codigo do modelo: ");
            var modeloEscolhido = leitura.nextLine();
            if(validaOpcao(modeloEscolhido, listaVeiculos, 'c')) {
                endereco = endereco.concat(modeloEscolhido.toLowerCase() + "/anos/");
                flag = false;
            } else {
                System.out.println("Opção não valida!\n");
            }
        } while (flag);

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

    public String retornaEnderecoTipoBusca(String tipoBuscaUsuario) {

        if (tipoBuscaUsuario.toLowerCase().contains("car")) {
            return PREFIXO_ENDERECO + "carros/marcas/";
        } else if (tipoBuscaUsuario.toLowerCase().contains("mo")) {
            return PREFIXO_ENDERECO + "motos/marcas/";
        } else {
            return PREFIXO_ENDERECO + "caminhoes/marcas/";
        }
    }

    public Boolean validaOpcao(String opcao, List<DadosBusca> retornoLista, char compare) {

        switch (compare) {
            case 'c' : return retornoLista.stream().filter(m -> m.codigo().contains(opcao)).count() > 0;
            case 'n' : return retornoLista.stream().filter(m -> m.nome().toLowerCase().contains(opcao.toLowerCase())).count() > 0;
            default: return false;
        }


    }
}

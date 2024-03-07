package com.apiTabelaFipe.tabelaFipe.principal;

import com.apiTabelaFipe.tabelaFipe.model.Dados;
import com.apiTabelaFipe.tabelaFipe.model.Modelos;
import com.apiTabelaFipe.tabelaFipe.model.Veiculo;
import com.apiTabelaFipe.tabelaFipe.service.consumoAPI;
import com.apiTabelaFipe.tabelaFipe.service.converteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private consumoAPI consumo = new consumoAPI();
    private converteDados conversor  = new converteDados();
    private final String URL_BASE= "https://parallelum.com.br/fipe/api/v1";
    public void exibeMenu(){
        var menu = """
                *** Opções ***
                Carro
                Moto 
                Caminhão
                
                Dugite uma das opções para consulta:
                
                """;
        System.out.println(menu);
        var opcao = leitura.nextLine();
        String endereco;
        if (opcao.toLowerCase().contains("carr")){
            endereco = URL_BASE + "/carros/marcas";

        } else if (opcao.toLowerCase().contains("mot")) {
            endereco = URL_BASE + "/motos/marcas";
        }else {
            endereco = URL_BASE + "/caminhoes/marcas";
        }
        var json = consumo.obterDados(endereco);
        System.out.println(json);

        var marcas= conversor.obterlista(json, Dados.class);
        marcas.stream()
                .sorted (Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);
        System.out.println("Informe o código da marca para consulta: ");
        var codigoMarca = leitura.nextLine();
        endereco = endereco  + "/" +  codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);
        var modeloLista  = conversor.obterDados(json, Modelos.class);
        System.out.println(modeloLista);
        System.out.println("\nDigite o nome do veiculo ou uma parte para ser buscado! ");
        var nomeVeiculo = leitura.nextLine();
        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());
        System.out.println("\nModelos filtrados. ");
        modelosFiltrados.forEach(System.out::println);
        System.out.println("Digite o código do modelo para buscar valores de avaliação. ");
        var codigoModelos = leitura.nextLine();
        endereco = endereco + "/" + codigoModelos + "/anos";
        json = consumo.obterDados(endereco);
        List<Dados> anos = conversor.obterlista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0 ; i  < anos.size(); i++) {
            var enderecoAnos =  endereco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }
        System.out.println("\nTodos os veiculos filtrados por ano: ");
        veiculos.forEach(System.out::println);
    }

}

package com.apiTabelaFipe.tabelaFipe.principal;

import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
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
    }
}

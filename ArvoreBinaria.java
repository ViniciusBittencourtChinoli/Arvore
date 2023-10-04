/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArvoreBinaria;

import java.util.Random;
import java.util.Scanner;
import java.text.DecimalFormat;

/**
 *
 * @author itzvi
 */



public class ArvoreBinaria {
    private No raiz;

    private class No {
        int valor;
        No esquerda, direita;

        public No(int valor) {
            this.valor = valor;
            esquerda = direita = null;
        }
    }

    public ArvoreBinaria() {
        raiz = null;
    }

    public void inserir(int valor) {
        raiz = inserirRec(raiz, valor);
    }

    private No inserirRec(No no, int valor) {
        if (no == null) {
            return new No(valor);
        }

        if (valor < no.valor) {
            no.esquerda = inserirRec(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = inserirRec(no.direita, valor);
        }

        return no;
    }

    public boolean buscar(int valor) {
        return buscarRec(raiz, valor);
    }

    private boolean buscarRec(No no, int valor) {
        if (no == null) {
            return false;
        }

        if (valor == no.valor) {
            return true;
        } else if (valor < no.valor) {
            return buscarRec(no.esquerda, valor);
        } else {
            return buscarRec(no.direita, valor);
        }
    }

    public void remover(int valor) {
        raiz = removerRec(raiz, valor);
    }

// Este método remove um valor da árvore de forma recursiva.
private No removerRec(No no, int valor) {
    if (no == null) {
        return no; // Se o nó for nulo, não há nada para fazer.
    }

    if (valor < no.valor) {
        no.esquerda = removerRec(no.esquerda, valor); // Vá para a esquerda se o valor for menor.
    } else if (valor > no.valor) {
        no.direita = removerRec(no.direita, valor); // Vá para a direita se o valor for maior.
    } else {
        if (no.esquerda == null) {
            return no.direita;
        } else if (no.direita == null) {
            return no.esquerda;
        }

        no.valor = minValor(no.direita); // Encontre o menor valor na subárvore direita.
        no.direita = removerRec(no.direita, no.valor); // Remova o menor valor da subárvore direita.
    }

    return no; // Retorne o nó atualizado.
}

// Este método encontra o menor valor em uma subárvore.
private int minValor(No no) {
    int minValor = no.valor;
    while (no.esquerda != null) {
        minValor = no.esquerda.valor;
        no = no.esquerda;
    }
    return minValor;
}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArvoreBinaria arvore = new ArvoreBinaria();
        long startTime = 0;
        long endTime = 0;

        int opcao;

        do {
            System.out.println("Menu:");
            System.out.println("1. Inserir elementos aleatórios");
            System.out.println("2. Buscar");
            System.out.println("3. Remover");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> {
                    System.out.print("Digite a quantidade de elementos aleatórios a serem gerados: ");
                    int quantidadeNumeros = scanner.nextInt();
                    Random random = new Random();

                    startTime = System.nanoTime();
                    for (int i = 0; i < quantidadeNumeros; i++) {
                        int numeroAleatorio = random.nextInt(100); // Gera números aleatórios de 0 a 99
                        arvore.inserir(numeroAleatorio);
                    }
                    endTime = System.nanoTime();
                    System.out.println("Árvore criada com elementos aleatórios.");
                }
                case 2 -> {
                    System.out.print("Digite o número a ser buscado: ");
                    int numeroBusca = scanner.nextInt();
                    startTime = System.nanoTime();
                    boolean encontrado = arvore.buscar(numeroBusca);
                    endTime = System.nanoTime();
                    if (encontrado) {
                        System.out.println("O número " + numeroBusca + " foi encontrado na árvore.");
                    } else {
                        System.out.println("O número " + numeroBusca + " não foi encontrado na árvore.");
                    }
                }
                case 3 -> {
                    System.out.print("Digite o número a ser removido: ");
                    int numeroRemocao = scanner.nextInt();
                    startTime = System.nanoTime();
                    arvore.remover(numeroRemocao);
                    endTime = System.nanoTime();
                    System.out.println("Número removido.");
                }
                case 4 -> System.out.println("Saindo do programa.");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }

            if (opcao >= 1 && opcao <= 3) {
                double executionTimeInSeconds = (endTime - startTime) / 1e9; // Converter nanossegundos para segundos
                DecimalFormat df = new DecimalFormat("#.######"); // Formatar para 6 casas decimais
                System.out.println("Tempo de execução em segundos: " + df.format(executionTimeInSeconds));
            }

        } while (opcao != 4);

        scanner.close();
    }
}
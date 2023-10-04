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

public class ArvoreAVL {
    private No raiz;

    private class No {
        int valor;
        No esquerda, direita;
        int altura;

        public No(int valor) {
            this.valor = valor;
            esquerda = direita = null;
            altura = 1;
        }
    }

    public ArvoreAVL() {
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
        } else {
            return no; // Não permitir valores duplicados
        }

        // Atualizar a altura do nó atual
        no.altura = 1 + Math.max(getAltura(no.esquerda), getAltura(no.direita));

        // Verificar o fator de equilíbrio
        int balance = getBalance(no);

        // Casos de desequilíbrio

        // Rotação à direita (simples)
        if (balance > 1 && valor < no.esquerda.valor) {
            return rotacaoDireita(no);
        }

        // Rotação à esquerda (simples)
        if (balance < -1 && valor > no.direita.valor) {
            return rotacaoEsquerda(no);
        }

        // Rotação à esquerda-direita (dupla)
        if (balance > 1 && valor > no.esquerda.valor) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        // Rotação à direita-esquerda (dupla)
        if (balance < -1 && valor < no.direita.valor) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    private int getAltura(No no) {
        if (no == null) {
            return 0;
        }
        return no.altura;
    }

    private int getBalance(No no) {
        if (no == null) {
            return 0;
        }
        return getAltura(no.esquerda) - getAltura(no.direita);
    }

    private No rotacaoDireita(No y) {
        No x = y.esquerda;
        No T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        y.altura = 1 + Math.max(getAltura(y.esquerda), getAltura(y.direita));
        x.altura = 1 + Math.max(getAltura(x.esquerda), getAltura(x.direita));

        return x;
    }

    private No rotacaoEsquerda(No x) {
        No y = x.direita;
        No T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        x.altura = 1 + Math.max(getAltura(x.esquerda), getAltura(x.direita));
        y.altura = 1 + Math.max(getAltura(y.esquerda), getAltura(y.direita));

        return y;
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

    private No removerRec(No no, int valor) {
        if (no == null) {
            return no;
        }

        if (valor < no.valor) {
            no.esquerda = removerRec(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = removerRec(no.direita, valor);
        } else {
            if (no.esquerda == null) {
                return no.direita;
            } else if (no.direita == null) {
                return no.esquerda;
            }

            no.valor = minValor(no.direita);
            no.direita = removerRec(no.direita, no.valor);
        }

        // Atualizar a altura do nó atual
        no.altura = 1 + Math.max(getAltura(no.esquerda), getAltura(no.direita));

        // Verificar o fator de equilíbrio
        int balance = getBalance(no);

        // Casos de desequilíbrio

        // Rotação à direita (simples)
        if (balance > 1 && getBalance(no.esquerda) >= 0) {
            return rotacaoDireita(no);
        }

        // Rotação à esquerda (simples)
        if (balance < -1 && getBalance(no.direita) <= 0) {
            return rotacaoEsquerda(no);
        }

        // Rotação à esquerda-direita (dupla)
        if (balance > 1 && getBalance(no.esquerda) < 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        // Rotação à direita-esquerda (dupla)
        if (balance < -1 && getBalance(no.direita) > 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

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
        ArvoreAVL arvore = new ArvoreAVL();
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
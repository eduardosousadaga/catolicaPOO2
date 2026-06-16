/*
Integrantes (Grupo 6):
Danilo Espíndola Folgierini Gomes – danilo.folgierini@a.ucb.br
Eduardo Sousa Daga – eduardo.daga@a.ucb.br
Fernando de Paula Hatabe – fernando.hatabe@a.ucb.br
Gabriel Sales do Nascimento – gabriel.snascimento@a.ucb.br
*/

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();
        int opcao = -1;

        System.out.println("\nBem-vindo ao Sistema de Biblioteca do Grupo 6");

        while (opcao != 0) {
            // ... (Menu idêntico ao original) ...
            System.out.println("\n--- MENU ---");
            System.out.println("1 - Cadastrar livro");
            System.out.println("2 - Cadastrar usuário");
            System.out.println("3 - Emprestar livro");
            System.out.println("4 - Devolver livro");
            System.out.println("5 - Listar livros do acervo");
            System.out.println("6 - Listar empréstimos ativos");
            System.out.println("0 - Sair");
            System.out.print("\nEscolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("ID do Livro: ");
                    int idLivro = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();
                    System.out.print("Autor: ");
                    String autor = sc.nextLine();
                    biblioteca.cadastrarLivro(new Livro(idLivro, titulo, autor));
                    break;

                case 2:
                    System.out.print("ID do Usuário: ");
                    int idUser = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Tipo (1 - Aluno | 2 - Professor): ");
                    int tipo = sc.nextInt();

                    try {
                        // Utilização do Padrão Factory
                        Usuario novoUsuario = UsuarioFactory.criarUsuario(idUser, nome, tipo);
                        biblioteca.cadastrarUsuario(novoUsuario, tipo);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                // ... (Casos 3, 4, 5, 6 e 0 permanecem idênticos, a biblioteca agora gerencia via SQLite internamente) ...
                case 3:
                    System.out.print("ID do Livro: ");
                    int empLivro = sc.nextInt();
                    System.out.print("ID do Usuário: ");
                    int empUser = sc.nextInt();
                    biblioteca.processarEmprestimo(empLivro, empUser);
                    break;
                case 4:
                    System.out.print("ID do Livro para devolução: ");
                    int devLivro = sc.nextInt();
                    biblioteca.processarDevolucao(devLivro);
                    break;
                case 5:
                    biblioteca.exibirLivrosDisponiveis();
                    break;
                case 6:
                    biblioteca.exibirEmprestimos();
                    break;
                case 0:
                    System.out.println("\nEncerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
        sc.close();
    }
}
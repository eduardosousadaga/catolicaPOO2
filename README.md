# Sistema de Biblioteca
Este é o trabalho final da disciplina de Programação Orientada a Objetos (POO) do curso de Análise e Desenvolvimento de Sistemas da Universidade Católica de Brasília.

O projeto consiste em um sistema de gerenciamento de biblioteca operado via terminal. Nesta etapa, o sistema foi evoluído para utilizar persistência de dados com banco de dados SQLite e incorporar Design Patterns.

## Funcionalidades
* Cadastro de usuários com regras distintas (Aluno com limite de 3 livros, Professor com limite de 5 livros).
* Cadastro de livros e controle de disponibilidade.
* Controle de empréstimos, com validação de limite por usuário e disponibilidade do livro.
* Controle de devoluções.
* Relatórios em tela de livros disponíveis e empréstimos ativos.

## Arquitetura e Padrões de Projeto
O sistema foi estruturado para garantir escalabilidade e organização do código, aplicando os seguintes padrões:

* Singleton (DatabaseConnection): Garante que a aplicação inteira utilize uma única conexão com o banco de dados SQLite, evitando problemas de concorrência e uso desnecessário de memória.
* Factory (UsuarioFactory): Centraliza a criação de objetos do tipo Usuário. Recebe o comando da interface de texto e retorna o objeto correto (Aluno ou Professor) configurado com suas respectivas regras, evitando estruturas condicionais extensas na classe principal.
* Repository Pattern (Interfaces): As interfaces LivroRepository, UsuarioRepository e EmprestimoRepository isolam a lógica de acesso a dados (consultas SQL) das regras de negócio do sistema, facilitando a manutenção e a eventual troca de banco de dados no futuro.

## Como Executar?
1. Tenha o Java Development Kit (JDK) instalado no seu ambiente.
2. Clone o repositório ou faça o download dos arquivos.
3. Certifique-se de adicionar a biblioteca do driver JDBC do SQLite (sqlite-jdbc.jar) ao classpath do projeto.
4. Execute a classe Main.java.
5. As tabelas necessárias serão criadas automaticamente na primeira execução no arquivo biblioteca.db.

## Integrantes (Grupo 6)
* Danilo Espíndola Folgierini Gomes
* Eduardo Sousa Daga
* Fernando de Paula Hatabe
* Gabriel Sales do Nascimento

import java.util.List;

public class Biblioteca {
    private LivroRepository livroRepo;
    private UsuarioRepository usuarioRepo;
    private EmprestimoRepository emprestimoRepo;

    public Biblioteca() {
        // Injeção de dependência simplificada
        this.livroRepo = new LivroRepositorySQLite();
        this.usuarioRepo = new UsuarioRepositorySQLite();
        this.emprestimoRepo = new EmprestimoRepositorySQLite();
    }

    public void cadastrarLivro(Livro livro) {
        livroRepo.salvar(livro);
        System.out.println("-> Livro adicionado ao acervo com sucesso!");
    }

    public void cadastrarUsuario(Usuario usuario, int tipo) {
        usuarioRepo.salvar(usuario, tipo);
        System.out.println("-> Usuário registrado no sistema!");
    }

    public void processarEmprestimo(int idLivro, int idUsuario) {
        Livro livro = livroRepo.buscarPorId(idLivro);
        Usuario user = usuarioRepo.buscarPorId(idUsuario);

        if (livro == null) {
            System.out.println("Erro: Livro não encontrado.");
            return;
        }
        if (user == null) {
            System.out.println("Erro: Usuário não encontrado.");
            return;
        }
        if (!livro.isDisponivel()) {
            System.out.println("Aviso: O livro '" + livro.getTitulo() + "' já está emprestado.");
            return;
        }
        if (!user.podeEmprestar()) {
            System.out.println("Aviso: " + user.getNome() + " já atingiu o limite de " + user.limiteLivros + " livros!");
            return;
        }

        // Regras de negócio validadas. Aplicando e persistindo:
        livro.setDisponivel(false);
        livroRepo.atualizarDisponibilidade(idLivro, false);

        user.registrarEmprestimo();
        usuarioRepo.atualizarQtdEmprestada(idUsuario, user.getQtdEmprestada());

        emprestimoRepo.registrar(idLivro, idUsuario);
        System.out.println("-> Empréstimo de '" + livro.getTitulo() + "' para " + user.getNome() + " realizado!");
    }

    public void processarDevolucao(int idLivro) {
        if (!emprestimoRepo.existeEmprestimo(idLivro)) {
            System.out.println("Erro: Nenhum empréstimo ativo encontrado com o ID deste livro.");
            return;
        }

        // Para devolver, precisamos descobrir qual usuário tinha o livro.
        // O repositório ou o banco relacional gerencia isso perfeitamente.
        // Neste modelo simplificado, buscamos o usuário diretamente do banco (poderíamos otimizar com uma query única)
        EmprestimoRepositorySQLite emprestimoSQLite = (EmprestimoRepositorySQLite) emprestimoRepo;

        // Em uma implementação de vida real, buscaríamos o objeto Emprestimo completo.
        // Aqui atualizamos diretamente as tabelas correlatas:
        String sqlBuscaUser = "SELECT idUsuario FROM emprestimos WHERE idLivro = " + idLivro;
        try (java.sql.Statement stmt = DatabaseConnection.getInstancia().createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(sqlBuscaUser)) {

            if(rs.next()) {
                int idUsuario = rs.getInt("idUsuario");
                Usuario user = usuarioRepo.buscarPorId(idUsuario);

                user.registrarDevolucao();
                usuarioRepo.atualizarQtdEmprestada(idUsuario, user.getQtdEmprestada());

                livroRepo.atualizarDisponibilidade(idLivro, true);
                emprestimoRepo.remover(idLivro);

                System.out.println("-> Livro devolvido com sucesso à biblioteca!");
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao processar devolução: " + e.getMessage());
        }
    }

    public void exibirLivrosDisponiveis() {
        System.out.println("\n--- LIVROS DISPONÍVEIS ---");
        List<Livro> livros = livroRepo.listarTodos();
        for (Livro livro : livros) {
            if (livro.isDisponivel()) {
                System.out.println(livro);
            }
        }
    }

    public void exibirEmprestimos() {
        System.out.println("\n--- EMPRÉSTIMOS ATIVOS ---");
        List<String> emprestimos = emprestimoRepo.listarEmprestimosAtivos();
        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum livro emprestado no momento.");
        } else {
            for (String emp : emprestimos) {
                System.out.println(emp);
            }
        }
    }
}
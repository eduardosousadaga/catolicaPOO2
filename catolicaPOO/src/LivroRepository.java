import java.util.List;

public interface LivroRepository {
    void salvar(Livro livro);
    Livro buscarPorId(int id);
    void atualizarDisponibilidade(int id, boolean disponivel);
    List<Livro> listarTodos();
}
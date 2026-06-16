import java.util.List;

public interface EmprestimoRepository {
    void registrar(int idLivro, int idUsuario);
    void remover(int idLivro);
    boolean existeEmprestimo(int idLivro);
    List<String> listarEmprestimosAtivos();
}
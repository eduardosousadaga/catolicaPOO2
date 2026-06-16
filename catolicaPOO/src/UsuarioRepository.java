public interface UsuarioRepository {
    void salvar(Usuario usuario, int tipo);
    Usuario buscarPorId(int id);
    void atualizarQtdEmprestada(int id, int novaQtd);
}
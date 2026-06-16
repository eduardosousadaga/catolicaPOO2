public class UsuarioFactory {
    public static Usuario criarUsuario(int id, String nome, int tipo) {
        if (tipo == 1) {
            return new Aluno(id, nome);
        } else if (tipo == 2) {
            return new Professor(id, nome);
        }
        throw new IllegalArgumentException("Tipo de usuário inválido! (Use 1 para Aluno, 2 para Professor)");
    }
}
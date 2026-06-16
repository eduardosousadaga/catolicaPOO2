public class Aluno extends Usuario {
    public Aluno(int id, String nome) {
        super(id, nome);
        this.limiteLivros = 3;
    }
}
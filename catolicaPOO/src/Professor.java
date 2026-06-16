public class Professor extends Usuario {
    public Professor(int id, String nome) {
        super(id, nome);
        this.limiteLivros = 5;
    }
}
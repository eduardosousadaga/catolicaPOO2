public class Emprestimo {
    private Livro livro;
    private Usuario usuario;

    public Emprestimo(Livro livro, Usuario usuario) {
        this.livro = livro;
        this.usuario = usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public String toString() {
        return "ID: " + livro.getId() + " | " + livro.getTitulo() + " - " + livro.getAutor() + " → Emprestado para " + usuario.getNome();
    }
}
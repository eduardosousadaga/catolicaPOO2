public class Usuario {
    private int id;
    private String nome;
    protected int limiteLivros;
    private int qtdEmprestada;

    public Usuario(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.qtdEmprestada = 0;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public boolean podeEmprestar() {
        return this.qtdEmprestada < this.limiteLivros;
    }

    public void registrarEmprestimo() {
        this.qtdEmprestada++;
    }

    public void registrarDevolucao() {
        if (this.qtdEmprestada > 0) {
            this.qtdEmprestada--;
        }
    }
    
    public int getQtdEmprestada() {
        return this.qtdEmprestada;
    }

    public void setQtdEmprestada(int qtd) {
        this.qtdEmprestada = qtd;
    }
}
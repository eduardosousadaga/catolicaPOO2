import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static Connection instancia = null;
    private static final String URL = "jdbc:sqlite:biblioteca.db";

    private DatabaseConnection() {
        // Construtor privado para impedir instâncias externas
    }

    public static Connection getInstancia() {
        if (instancia == null) {
            try {
                instancia = DriverManager.getConnection(URL);
                criarTabelas();
            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            }
        }
        return instancia;
    }

    private static void criarTabelas() {
        String sqlLivros = "CREATE TABLE IF NOT EXISTS livros (id INTEGER PRIMARY KEY, titulo TEXT, autor TEXT, disponivel INTEGER DEFAULT 1)";
        String sqlUsuarios = "CREATE TABLE IF NOT EXISTS usuarios (id INTEGER PRIMARY KEY, nome TEXT, tipo INTEGER, limiteLivros INTEGER, qtdEmprestada INTEGER DEFAULT 0)";
        String sqlEmprestimos = "CREATE TABLE IF NOT EXISTS emprestimos (idLivro INTEGER, idUsuario INTEGER, PRIMARY KEY(idLivro, idUsuario), FOREIGN KEY(idLivro) REFERENCES livros(id), FOREIGN KEY(idUsuario) REFERENCES usuarios(id))";

        try (Statement stmt = instancia.createStatement()) {
            stmt.execute(sqlLivros);
            stmt.execute(sqlUsuarios);
            stmt.execute(sqlEmprestimos);
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoRepositorySQLite implements EmprestimoRepository {
    private Connection conexao;

    public EmprestimoRepositorySQLite() {
        this.conexao = DatabaseConnection.getInstancia();
    }

    @Override
    public void registrar(int idLivro, int idUsuario) {
        String sql = "INSERT INTO emprestimos(idLivro, idUsuario) VALUES(?, ?)";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, idLivro);
            pstmt.setInt(2, idUsuario);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao registrar empréstimo no banco: " + e.getMessage());
        }
    }

    @Override
    public void remover(int idLivro) {
        String sql = "DELETE FROM emprestimos WHERE idLivro = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, idLivro);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao remover empréstimo: " + e.getMessage());
        }
    }

    @Override
    public boolean existeEmprestimo(int idLivro) {
        String sql = "SELECT 1 FROM emprestimos WHERE idLivro = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, idLivro);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Erro ao checar empréstimo: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<String> listarEmprestimosAtivos() {
        List<String> ativos = new ArrayList<>();
        String sql = "SELECT e.idLivro, l.titulo, l.autor, u.nome FROM emprestimos e " +
                "JOIN livros l ON e.idLivro = l.id " +
                "JOIN usuarios u ON e.idUsuario = u.id";
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ativos.add("ID Livro: " + rs.getInt("idLivro") + " | " + rs.getString("titulo") + " - " + rs.getString("autor") + " → Emprestado para " + rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar empréstimos: " + e.getMessage());
        }
        return ativos;
    }
}
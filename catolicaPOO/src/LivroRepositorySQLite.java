import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroRepositorySQLite implements LivroRepository {
    private Connection conexao;

    public LivroRepositorySQLite() {
        this.conexao = DatabaseConnection.getInstancia();
    }

    @Override
    public void salvar(Livro livro) {
        String sql = "INSERT INTO livros(id, titulo, autor, disponivel) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, livro.getId());
            pstmt.setString(2, livro.getTitulo());
            pstmt.setString(3, livro.getAutor());
            pstmt.setInt(4, livro.isDisponivel() ? 1 : 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar livro: " + e.getMessage());
        }
    }

    @Override
    public Livro buscarPorId(int id) {
        String sql = "SELECT * FROM livros WHERE id = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Livro livro = new Livro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"));
                livro.setDisponivel(rs.getInt("disponivel") == 1);
                return livro;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void atualizarDisponibilidade(int id, boolean disponivel) {
        String sql = "UPDATE livros SET disponivel = ? WHERE id = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, disponivel ? 1 : 0);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar livro: " + e.getMessage());
        }
    }

    @Override
    public List<Livro> listarTodos() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros";
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Livro livro = new Livro(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"));
                livro.setDisponivel(rs.getInt("disponivel") == 1);
                livros.add(livro);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
        }
        return livros;
    }
}
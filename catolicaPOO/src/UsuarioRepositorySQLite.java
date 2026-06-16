import java.sql.*;

public class UsuarioRepositorySQLite implements UsuarioRepository {
    private Connection conexao;

    public UsuarioRepositorySQLite() {
        this.conexao = DatabaseConnection.getInstancia();
    }

    @Override
    public void salvar(Usuario usuario, int tipo) {
        String sql = "INSERT INTO usuarios(id, nome, tipo, limiteLivros, qtdEmprestada) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, usuario.getId());
            pstmt.setString(2, usuario.getNome());
            pstmt.setInt(3, tipo);
            pstmt.setInt(4, usuario.limiteLivros);
            pstmt.setInt(5, usuario.getQtdEmprestada());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    @Override
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int tipo = rs.getInt("tipo");
                Usuario user = UsuarioFactory.criarUsuario(rs.getInt("id"), rs.getString("nome"), tipo);
                user.setQtdEmprestada(rs.getInt("qtdEmprestada"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void atualizarQtdEmprestada(int id, int novaQtd) {
        String sql = "UPDATE usuarios SET qtdEmprestada = ? WHERE id = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, novaQtd);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar quantidade emprestada: " + e.getMessage());
        }
    }
}
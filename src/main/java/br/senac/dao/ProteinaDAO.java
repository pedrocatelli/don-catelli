package br.senac.dao;

import br.senac.dto.ProteinaDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@ApplicationScoped
public class ProteinaDAO {

    public void save(Connection conn, ProteinaDTO proteina) throws SQLException {
        String sql = "INSERT INTO public.proteina (id, nome, descricao) VALUES (?, ?, ?,)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            stmt.setInt(index++, proteina.getId());
            stmt.setString(index++, proteina.getNome());
            stmt.setString(index, proteina.getDescricao());
            stmt.executeUpdate();
        }
    }

    public void update(Connection conn,  ProteinaDTO proteina) throws SQLException {
        String sql = "UPDATE public.proteina SET nome = ?, descricao = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, proteina.getNome());
            stmt.setString(2, proteina.getDescricao());
            stmt.executeUpdate();
        }
    }
    public void delete(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM public.proteina WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
        }
    }
}


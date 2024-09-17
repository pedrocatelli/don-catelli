package br.senac.dao;

import br.senac.dto.EnderecoDTO;
import br.senac.dto.PagamentoDTO;
import br.senac.dto.PessoaDTO;
import br.senac.dto.ProteinaDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProteinaDAO {

    public void save(Connection conn, ProteinaDTO proteina) throws SQLException {
        String sql = "INSERT INTO public.proteina (id, nome, descricao, img64) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            stmt.setInt(index++, proteina.getId());
            stmt.setString(index++, proteina.getNome());
            stmt.setString(index++, proteina.getDescricao());
            stmt.setString(index, proteina.getImg64());
            stmt.executeUpdate();
        }
    }

    public void update(Connection conn,  ProteinaDTO proteina) throws SQLException {
        String sql = "UPDATE public.proteina SET nome = ?, descricao = ?, img64 = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, proteina.getNome());
            stmt.setString(2, proteina.getDescricao());
            stmt.setString(3, proteina.getImg64());
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

    public ProteinaDTO findById(Connection conn, int id) throws SQLException {
        String sql = "SELECT id, nome, descricao, img64 FROM public.proteina WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ProteinaDTO proteina = new ProteinaDTO();
                    proteina.setId(rs.getInt("id"));
                    proteina.setNome(rs.getString("nome"));
                    proteina.setDescricao(rs.getString("descricao"));
                    proteina.setImg64(rs.getString("img64"));

                    return proteina;
                }
            }
        }
        return null;
    }

    public List<ProteinaDTO> findAll(Connection conn) throws SQLException {
        String sql = "SELECT id, nome, descricao, img64 FROM public.proteina";
        List<ProteinaDTO> proteinas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ProteinaDTO proteina = new ProteinaDTO();
                proteina.setId(rs.getInt("id"));
                proteina.setNome(rs.getString("nome"));
                proteina.setDescricao(rs.getString("descricao"));
                proteina.setImg64(rs.getString("img64"));

                proteinas.add(proteina);
            }
        }
        return proteinas;
    }


    public int getNextId(Connection conn) throws SQLException {
        String sql = "SELECT nextval('public.sq_proteina')";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

}
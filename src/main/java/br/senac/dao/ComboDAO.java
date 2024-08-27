package br.senac.dao;
import br.senac.dto.ComboDTO;
import br.senac.dto.EnderecoDTO;
import br.senac.dto.PagamentoDTO;
import br.senac.dto.PessoaDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ComboDAO {

    public void save(Connection conn, ComboDTO combo) throws SQLException {
        String sql = "INSERT INTO public.combo(id, nome, descricao, preco) values (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            int index = 1;
            stmt.setInt(index++, combo.getId());
            stmt.setString(index++, combo.getNome());
            stmt.setString(index++, combo.getDescricao());
            stmt.setDouble(index, combo.getPreco());
            stmt.executeUpdate();

        }
    }


    public void update(Connection conn, ComboDTO combo) throws SQLDataException {
        String sql = "UPDATE public.combo SET nome = ?, descricao = ?, preco = ? WHERE id =- ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            int index = 1; stmt.setInt(index++, combo.getId());
            stmt.setString(index++, combo.getNome());
            stmt.setString(index++, combo.getDescricao());
            stmt.setDouble(index, combo.getPreco());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void delete (Connection conn, int id) throws
            SQLDataException{
        String sql = "DELETE FROM public.combo WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ComboDTO findById(Connection conn, int id) throws SQLException {
        String sql = "SELECT id, nome, descricao, preco FROM public.combo WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ComboDTO combo = new ComboDTO();
                    combo.setId(rs.getInt("id"));
                    combo.setNome(rs.getString("nome"));
                    combo.setDescricao(rs.getString("descricao"));
                    combo.setPreco(rs.getDouble("preco"));

                    return combo;
                }
            }
        }
        return null;
    }

    public List<ComboDTO> findAll(Connection conn) throws SQLException {
        String sql = "SELECT id, id_pessoa, id_endereco, tipo, data_venda FROM public.combo";
        List<ComboDTO> combos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ComboDTO combo = new ComboDTO();
                combo.setId(rs.getInt("id"));
                combo.setNome(rs.getString("nome"));
                combo.setDescricao(rs.getString("descricao"));
                combo.setPreco(rs.getDouble("preco"));

                combos.add(combo);
            }
        }
        return combos;
    }

    public int getNextId(Connection conn) throws SQLException {
        String sql = "SELECT nextval('public.sq_combo')";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

}

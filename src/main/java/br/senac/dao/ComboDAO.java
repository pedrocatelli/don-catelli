package br.senac.dao;
import br.senac.dto.ComboDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;
import java.sql.SQLException;

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

}

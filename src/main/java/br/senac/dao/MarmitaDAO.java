package br.senac.dao;

import br.senac.dto.MarmitaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class MarmitaDAO {
    public void save(Connection conn, MarmitaDTO marmita) throws SQLException {
        String sql = "INSERT INTO public.marmita(id, nome, acompanhamentos, proteina, combo) values (?, ?, ?, ?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            stmt.setInt(index++, marmita.getId());
            stmt.setString(index++, marmita.getNome());
            stmt.setString(index++, marmita.getAcompanhamentos());
            stmt.setString(index++, marmita.getProteina());
            stmt.setString(index, marmita.getCombo());

            stmt.executeUpdate();


        }
    }

        public void update(Connection conn, MarmitaDTO marmita) throws SQLDataException {
            String sql = "UPDATE public.combo SET nome = ?, acompanhamantos = ?, proteina = ?, combo = ?, WHERE id =- ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                int index = 1; stmt.setInt(index++, marmita.getId());
                stmt.setString(index++, marmita.getNome());
                stmt.setString(index++, marmita.getAcompanhamentos());
                stmt.setString(index++, marmita.getProtenia());
                stmt.setString(index, marmita.getCombo());
                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }

        public void delete (Connection conn, int id) throws SQLDataException{
            String sql = "DELETE FROM public.marmita WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }


    }

package br.senac.dao;

import br.senac.dto.*;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@ApplicationScoped
public class MarmitaDAO {
    public void save(Connection conn, MarmitaDTO marmita) throws SQLException {
        String sql = "INSERT INTO public.marmita(id,  id_combo, nome, acompanhamentos, id_proteina) values (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            stmt.setInt(index++, marmita.getId());
            stmt.setInt(index, marmita.getCombo() != null ? marmita.getCombo().getId() : null);
            stmt.setString(index++, marmita.getNome());
            stmt.setString(index++, marmita.getAcompanhamentos());
            stmt.setInt(index++, marmita.getProteina() != null ? marmita.getProteina().getId() : null);
            stmt.executeUpdate();
        }
    }

        public void update(Connection conn, MarmitaDTO marmita) throws SQLException {
            String sql = "UPDATE public.combo SET id_combo = ?, nome = ?, acompanhamantos = ?, id_proteina = ? WHERE id =- ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                int index = 1;
                stmt.setInt(index++, marmita.getId());
                stmt.setInt(index, marmita.getCombo() != null ? marmita.getCombo().getId() : null);
                stmt.setString(index++, marmita.getNome());
                stmt.setString(index++, marmita.getAcompanhamentos());
                stmt.setInt(index++, marmita.getProteina() != null ? marmita.getProteina().getId() : null);
                stmt.executeUpdate();
            }
        }

        public void delete(Connection conn, int id) throws SQLException{
            String sql = "DELETE FROM public.marmita WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        }

    public MarmitaDTO findById(Connection conn, int id) throws SQLException {
        String sql = "SELECT marmita.id, marmita.id_combo, marmita.nome, marmita.acompanhamentos, marmita.id_proteina, proteina.img64 FROM marmita JOIN proteina ON marmita.id_proteina = proteina.id WHERE marmita.id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    MarmitaDTO marmita = new MarmitaDTO();
                    marmita.setId(rs.getInt("id"));
                    if (rs.getInt("id_combo") != 0) {
                        ComboDTO combo = new ComboDTO();
                        combo.setId(rs.getInt("id_combo"));
                        marmita.setCombo(combo);
                    }
                    marmita.setNome(rs.getString("nome"));
                    marmita.setAcompanhamentos(rs.getString("acompanhamentos"));
                    if (rs.getInt("id_proteina") != 0) {
                        ProteinaDTO proteina = new ProteinaDTO();
                        proteina.setId(rs.getInt("id_proteina"));
                        proteina.setImg64(rs.getString("img64"));
                        marmita.setProteina(proteina);
                    }
                    return marmita;
                }
            }
        }
        return null;
    }

    public List<MarmitaDTO> findAll(Connection conn) throws SQLException {
        String sql = "SELECT marmita.id, marmita.id_combo, marmita.nome, marmita.acompanhamentos, marmita.id_proteina, proteina.img64 FROM marmita JOIN proteina ON marmita.id_proteina = proteina.id";
        List<MarmitaDTO> marmitas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MarmitaDTO marmita = new MarmitaDTO();
                marmita.setId(rs.getInt("id"));
                if (rs.getInt("id_combo") != 0) {
                    ComboDTO combo = new ComboDTO();
                    combo.setId(rs.getInt("id_combo"));
                    marmita.setCombo(combo);
                }
                marmita.setNome(rs.getString("nome"));
                marmita.setAcompanhamentos(rs.getString("acompanhamentos"));
                if (rs.getInt("id_proteina") != 0) {
                    ProteinaDTO proteina = new ProteinaDTO();
                    proteina.setId(rs.getInt("id_proteina"));
                    proteina.setImg64(rs.getString("img64"));
                    marmita.setProteina(proteina);
                }
                marmitas.add(marmita);
            }
        }
        return marmitas;
    }

    public List<MarmitaDTO> maisPedidas(Connection conn) throws SQLException {
        String sql = "select m.id, m.id_combo,  m.nome, m.acompanhamentos, m.id_proteina, SUM(p.id_marmita) AS total_pedidos from marmita m join pedido p ON m.id = p.id_marmita GROUP by m.id, m.nome ORDER by total_pedidos desc LIMIT 6";
        List<MarmitaDTO> topMarmitas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MarmitaDTO marmita = new MarmitaDTO();
                marmita.setId(rs.getInt("m.id"));
                if (rs.getInt("m.id_combo") != 0) {
                    ComboDTO combo = new ComboDTO();
                    combo.setId(rs.getInt("id_combo"));
                    marmita.setCombo(combo);
                }
                marmita.setNome(rs.getString("nome"));
                marmita.setAcompanhamentos(rs.getString("acompanhamentos"));
                if (rs.getInt("id_proteina") != 0) {
                    ProteinaDTO proteina = new ProteinaDTO();
                    proteina.setId(rs.getInt("id_proteina"));
                    proteina.setImg64(rs.getString("img64"));
                    marmita.setProteina(proteina);
                }
                topMarmitas.add(marmita);
            }
        }
        return topMarmitas;
    }

    public List<MarmitaDTO> findMarmitasDoCombo(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM marmita JOIN combo ON marmita.id_combo = combo.id where combo.id = ?";
        List<MarmitaDTO> marmitas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    MarmitaDTO marmita = new MarmitaDTO();
                    marmita.setId(rs.getInt("id"));
                    if (rs.getInt("id_combo") != 0) {
                        ComboDTO combo = new ComboDTO();
                        combo.setId(rs.getInt("id_combo"));
                        marmita.setCombo(combo);
                    }
                    marmita.setNome(rs.getString("nome"));
                    marmita.setAcompanhamentos(rs.getString("acompanhamentos"));
                    if (rs.getInt("id_proteina") != 0) {
                        ProteinaDTO proteina = new ProteinaDTO();
                        proteina.setId(rs.getInt("id_proteina"));
                        proteina.setImg64(rs.getString("img64"));
                        marmita.setProteina(proteina);
                    }
                }
            }
        }
        return marmitas;
    }

    public int getNextId(Connection conn) throws SQLException {
        String sql = "SELECT nextval('public.sq_marmita')";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

}

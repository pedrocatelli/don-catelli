package br.senac.dao;

import br.senac.dto.UfDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UfDAO {

    public List<UfDTO> findAll(Connection conn) throws SQLException {
        String sql = "SELECT id, sg_uf, nm_uf FROM public.uf";
        List<UfDTO> ufs = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UfDTO uf = new UfDTO();
                uf.setId(rs.getInt("id"));
                uf.setSgUf(rs.getString("sg_uf"));
                uf.setNmUf(rs.getString("nm_uf"));
                ufs.add(uf);
            }
        }
        return ufs;
    }

    public UfDTO findById(Connection conn, int id) throws SQLException {
        String sql = "SELECT id, sg_uf, nm_uf FROM public.uf WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UfDTO uf = new UfDTO();
                    uf.setId(rs.getInt("id"));
                    uf.setSgUf(rs.getString("sg_uf"));
                    uf.setNmUf(rs.getString("nm_uf"));
                    return uf;
                }
            }
        }
        return null;
    }
}

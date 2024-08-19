package br.senac.dao;

import br.senac.dto.PessoaDTO;
import br.senac.dto.UfDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PessoaDAO {

    public void save(Connection conn, PessoaDTO pessoa) throws SQLException {
        String sql = "INSERT INTO public.pessoa (id, nome, email, dt_nascimento, id_uf_naturalidade) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pessoa.getId());
            stmt.setString(2, pessoa.getNome());
            stmt.setString(3, pessoa.getEmail());
            stmt.setDate(4, new java.sql.Date(pessoa.getNascimento().getTime()));
            stmt.setInt(5, pessoa.getUf().getId());
            stmt.executeUpdate();
        }
    }

    public void update(Connection conn, PessoaDTO pessoa) throws SQLException {
        String sql = "UPDATE public.pessoa SET nome = ?, email = ?, dt_nascimento = ?, id_uf_naturalidade = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getEmail());
            stmt.setDate(3, new java.sql.Date(pessoa.getNascimento().getTime()));
            stmt.setInt(4, pessoa.getUf().getId());
            stmt.setInt(5, pessoa.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM public.pessoa WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public PessoaDTO findById(Connection conn, int id) throws SQLException {
        String sql = "SELECT id, nome, email, dt_nascimento, id_uf_naturalidade FROM public.pessoa WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PessoaDTO pessoa = new PessoaDTO();
                    pessoa.setId(rs.getInt("id"));
                    pessoa.setNome(rs.getString("nome"));
                    pessoa.setEmail(rs.getString("email"));
                    pessoa.setNascimento(rs.getDate("dt_nascimento"));

                    UfDTO uf = new UfDTO();
                    uf.setId(rs.getInt("id_uf_naturalidade"));
                    pessoa.setUf(uf);

                    return pessoa;
                }
            }
        }
        return null;
    }

    public List<PessoaDTO> findAll(Connection conn) throws SQLException {
        String sql = "SELECT id, nome, email, dt_nascimento, id_uf_naturalidade FROM public.pessoa";
        List<PessoaDTO> pessoas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PessoaDTO pessoa = new PessoaDTO();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setNascimento(rs.getDate("dt_nascimento"));

                UfDTO uf = new UfDTO();
                uf.setId(rs.getInt("id_uf_naturalidade"));
                pessoa.setUf(uf);

                pessoas.add(pessoa);
            }
        }
        return pessoas;
    }

    public int getNextId(Connection conn) throws SQLException {
        String sql = "SELECT nextval('public.sq_pessoa')";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }
}

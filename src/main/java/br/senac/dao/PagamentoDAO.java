package br.senac.dao;

import br.senac.dto.PessoaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {

    public void save(Connection conn, PessoaDTO pessoa) throws SQLException {
        String sql = "INSERT INTO public.pessoa (id, nome, cpf, email, telefone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            stmt.setInt(index++, pessoa.getId());
            stmt.setString(index++, pessoa.getNome());
            stmt.setInt(index++, pessoa.getCpf());
            stmt.setString(index++, pessoa.getEmail());
            stmt.setInt(index, pessoa.getTelefone());
            stmt.executeUpdate();
        }
    }

    public void update(Connection conn, PessoaDTO pessoa) throws SQLException {
        String sql = "UPDATE public.pessoa SET nome = ?, email = ?, cpf = ?, telefone = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            stmt.setString(index++, pessoa.getNome());
            stmt.setString(index++, pessoa.getEmail());
            stmt.setInt(index++, pessoa.getCpf());
            stmt.setInt(index++, pessoa.getTelefone());
            stmt.setInt(index, pessoa.getId());
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
        String sql = "SELECT id, nome, cpf, email, telefone FROM public.pessoa WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PessoaDTO pessoa = new PessoaDTO();
                    pessoa.setId(rs.getInt("id"));
                    pessoa.setNome(rs.getString("nome"));
                    pessoa.setCpf(rs.getInt("cpf"));
                    pessoa.setEmail(rs.getString("email"));
                    pessoa.setTelefone(rs.getInt("telefone"));

                    return pessoa;
                }
            }
        }
        return null;
    }

    public List<PessoaDTO> findAll(Connection conn) throws SQLException {
        String sql = "SELECT id, nome, cpf, email, telefone FROM public.pessoa";
        List<PessoaDTO> pessoas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PessoaDTO pessoa = new PessoaDTO();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setCpf(rs.getInt("cpf"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setTelefone(rs.getInt("telefone"));

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

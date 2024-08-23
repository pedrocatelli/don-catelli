package br.senac.dao;

import br.senac.dto.EnderecoDTO;
import br.senac.dto.PessoaDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EnderecoDAO {

    public void save(Connection conn, EnderecoDTO endereco) throws SQLException {
        String sql = "INSERT INTO public.endereco (id, cep, endereco, complemento, bairro, cidade, uf, id_pessoa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            stmt.setInt(index++, endereco.getId());
            stmt.setInt(index++, endereco.getCep());
            stmt.setString(index++, endereco.getEndereco());
            stmt.setString(index++, endereco.getComplemento());
            stmt.setString(index++, endereco.getBairro());
            stmt.setString(index++, endereco.getCidade());
            stmt.setString(index++, endereco.getUF());
            stmt.setInt(index, endereco.getPessoa() != null ? endereco.getPessoa().getId() : null);
            stmt.executeUpdate();
        }
    }

    public void delete(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM public.endereco WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public EnderecoDTO findById(Connection conn, int id) throws SQLException {
        String sql = "SELECT id, cep, endereco, complemento, bairro, cidade, uf, id_pessoa FROM public.endereco WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    EnderecoDTO endereco = new EnderecoDTO();

                    endereco.setId(rs.getInt("id"));
                    endereco.setCep(rs.getInt("cep"));
                    endereco.setBairro(rs.getString("endereco"));
                    endereco.setBairro(rs.getString("complemento"));
                    endereco.setBairro(rs.getString("bairro"));
                    endereco.setCidade(rs.getString("cidade"));
                    endereco.setCidade(rs.getString("uf"));
                    if (rs.getInt("id_pessoa") != 0) {
                        PessoaDTO pessoa = new PessoaDTO();
                        pessoa.setId(rs.getInt("id_pessoa"));
                        endereco.setPessoa(pessoa);
                    }
                    return endereco;
                }
            }
        }
        return null;
    }

    public List<EnderecoDTO> findAll(Connection conn) throws SQLException {
        String sql = "SELECT id, cep, endereco, complemento, bairro, cidade, uf, id_pessoa FROM public.endereco";
        List<EnderecoDTO> enderecos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                EnderecoDTO endereco = new EnderecoDTO();
                endereco.setId(rs.getInt("id"));
                endereco.setCep(rs.getInt("cep"));
                endereco.setBairro(rs.getString("endereco"));
                endereco.setBairro(rs.getString("complemento"));
                endereco.setBairro(rs.getString("bairro"));
                endereco.setCidade(rs.getString("cidade"));
                endereco.setCidade(rs.getString("uf"));
                if (rs.getInt("id_pessoa") != 0) {
                    PessoaDTO pessoa = new PessoaDTO();
                    pessoa.setId(rs.getInt("id_pessoa"));
                    endereco.setPessoa(pessoa);
                }
                enderecos.add(endereco);
            }
        }
        return enderecos;
    }

    public List<EnderecoDTO> findByPessoaId(Connection conn, int pessoaId) throws SQLException {
        String sql = "SELECT id, cep, endereco, complemento, bairro, cidade, uf, id_pessoa FROM public.endereco WHERE id_pessoa = ?";
        List<EnderecoDTO> enderecos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pessoaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EnderecoDTO endereco = new EnderecoDTO();
                    endereco.setId(rs.getInt("id"));
                    endereco.setCep(rs.getInt("cep"));
                    endereco.setBairro(rs.getString("endereco"));
                    endereco.setBairro(rs.getString("complemento"));
                    endereco.setBairro(rs.getString("bairro"));
                    endereco.setCidade(rs.getString("cidade"));
                    endereco.setCidade(rs.getString("uf"));
                    if (rs.getInt("id_pessoa") != 0) {
                        PessoaDTO pessoa = new PessoaDTO();
                        pessoa.setId(rs.getInt("id_pessoa"));
                        endereco.setPessoa(pessoa);
                    }
                    enderecos.add(endereco);
                }
            }
        }
        return enderecos;
    }

    public int getNextId(Connection conn) throws SQLException {
        String sql = "SELECT nextval('public.sq_endereco')";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }
}

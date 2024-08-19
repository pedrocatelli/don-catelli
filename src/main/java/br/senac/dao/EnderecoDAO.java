package br.senac.dao;

import br.senac.dto.EnderecoDTO;
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
public class EnderecoDAO {

    public void save(Connection conn, EnderecoDTO endereco) throws SQLException {
        String sql = "INSERT INTO public.endereco (id, id_uf, cep, bairro, cidade, endereco, id_pessoa) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, endereco.getId());
            stmt.setInt(2, endereco.getUf().getId());
            stmt.setString(3, endereco.getCep());
            stmt.setString(4, endereco.getBairro());
            stmt.setString(5, endereco.getCidade());
            stmt.setString(6, endereco.getEndereco());
            stmt.setInt(7, endereco.getPessoa() != null ? endereco.getPessoa().getId() : null);
            stmt.executeUpdate();
        }
    }

    public void update(Connection conn, EnderecoDTO endereco) throws SQLException {
        String sql = "UPDATE public.endereco SET id_uf = ?, cep = ?, bairro = ?, cidade = ?, endereco = ?, id_pessoa = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, endereco.getUf().getId());
            stmt.setString(2, endereco.getCep());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCidade());
            stmt.setString(5, endereco.getEndereco());
            stmt.setInt(6, endereco.getPessoa() != null ? endereco.getPessoa().getId() : null);
            stmt.setInt(7, endereco.getId());
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
        String sql = "SELECT id, id_uf, cep, bairro, cidade, endereco, id_pessoa FROM public.endereco WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    EnderecoDTO endereco = new EnderecoDTO();
                    endereco.setId(rs.getInt("id"));
                    UfDTO uf = new UfDTO();
                    uf.setId(rs.getInt("id_uf"));
                    endereco.setUf(uf);
                    endereco.setCep(rs.getString("cep"));
                    endereco.setBairro(rs.getString("bairro"));
                    endereco.setCidade(rs.getString("cidade"));
                    endereco.setEndereco(rs.getString("endereco"));
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
        String sql = "SELECT id, id_uf, cep, bairro, cidade, endereco, id_pessoa FROM public.endereco";
        List<EnderecoDTO> enderecos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                EnderecoDTO endereco = new EnderecoDTO();
                endereco.setId(rs.getInt("id"));
                UfDTO uf = new UfDTO();
                uf.setId(rs.getInt("id_uf"));
                endereco.setUf(uf);
                endereco.setCep(rs.getString("cep"));
                endereco.setBairro(rs.getString("bairro"));
                endereco.setCidade(rs.getString("cidade"));
                endereco.setEndereco(rs.getString("endereco"));
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
        String sql = "SELECT id, id_uf, cep, bairro, cidade, endereco, id_pessoa FROM public.endereco WHERE id_pessoa = ?";
        List<EnderecoDTO> enderecos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pessoaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EnderecoDTO endereco = new EnderecoDTO();
                    endereco.setId(rs.getInt("id"));
                    UfDTO uf = new UfDTO();
                    uf.setId(rs.getInt("id_uf"));
                    endereco.setUf(uf);
                    endereco.setCep(rs.getString("cep"));
                    endereco.setBairro(rs.getString("bairro"));
                    endereco.setCidade(rs.getString("cidade"));
                    endereco.setEndereco(rs.getString("endereco"));
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

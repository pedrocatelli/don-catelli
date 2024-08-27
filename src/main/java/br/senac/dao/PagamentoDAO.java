package br.senac.dao;

import br.senac.dto.EnderecoDTO;
import br.senac.dto.PagamentoDTO;
import br.senac.dto.PessoaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PagamentoDAO {

    public void save(Connection conn, PagamentoDTO pagamento) throws SQLException {
        String sql = "INSERT INTO public.pagamento (id, id_pessoa, id_endereco, tipo, data_venda) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            stmt.setInt(index++, pagamento.getId());
            stmt.setInt(index++, pagamento.getPessoa() != null ? pagamento.getPessoa().getId() : null);
            stmt.setInt(index++, pagamento.getEndereco() != null ? pagamento.getEndereco().getId() : null);
            stmt.setString(index++, pagamento.getTipo());
            stmt.setDate(index, pagamento.getDataPagamento());
            stmt.executeUpdate();
        }
    }

    public void update(Connection conn, PagamentoDTO pagamento) throws SQLException {
        String sql = "UPDATE public.pagamento SET id_pessoa = ?, id_endereco = ?, tipo = ?, data_venda = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            stmt.setInt(index++, pagamento.getId());
            stmt.setInt(index++, pagamento.getPessoa() != null ? pagamento.getPessoa().getId() : null);
            stmt.setInt(index++, pagamento.getEndereco() != null ? pagamento.getEndereco().getId() : null);
            stmt.setString(index++, pagamento.getTipo());
            stmt.setDate(index, pagamento.getDataPagamento());
            stmt.executeUpdate();
        }
    }

    public void delete(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM public.pagamento WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public PagamentoDTO findById(Connection conn, int id) throws SQLException {
        String sql = "SELECT id, id_pessoa, id_endereco, tipo, data_venda FROM public.pagamento WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PagamentoDTO pagamento = new PagamentoDTO();
                    pagamento.setId(rs.getInt("id"));
                    if (rs.getInt("id_pessoa") != 0) {
                        PessoaDTO pessoa = new PessoaDTO();
                        pessoa.setId(rs.getInt("id"));
                        pagamento.setPessoa(pessoa);
                    }
                    if (rs.getInt("id_endereco") != 0) {
                        EnderecoDTO endereco = new EnderecoDTO();
                        endereco.setId(rs.getInt("id"));
                        pagamento.setEndereco(endereco);
                    }
                    pagamento.setTipo(rs.getString("tipo"));
                    pagamento.setDataPagamento(rs.getDate("data_venda"));

                    return pagamento;
                }
            }
        }
        return null;
    }

    public List<PagamentoDTO> findAll(Connection conn) throws SQLException {
        String sql = "SELECT id, id_pessoa, id_endereco, tipo, data_venda FROM public.pagamento";
        List<PagamentoDTO> pagamentos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PagamentoDTO pagamento = new PagamentoDTO();
                pagamento.setId(rs.getInt("id"));
                if (rs.getInt("id_pessoa") != 0) {
                    PessoaDTO pessoa = new PessoaDTO();
                    pessoa.setId(rs.getInt("id"));
                    pagamento.setPessoa(pessoa);
                }
                if (rs.getInt("id_endereco") != 0) {
                    EnderecoDTO endereco = new EnderecoDTO();
                    endereco.setId(rs.getInt("id"));
                    pagamento.setEndereco(endereco);
                }
                pagamento.setTipo(rs.getString("tipo"));
                pagamento.setDataPagamento(rs.getDate("data_venda"));

                pagamentos.add(pagamento);
            }
        }
        return pagamentos;
    }


    public int getNextId(Connection conn) throws SQLException {
        String sql = "SELECT nextval('public.sq_pagamento')";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }
}

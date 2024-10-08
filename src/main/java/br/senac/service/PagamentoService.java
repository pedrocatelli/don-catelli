package br.senac.service;

import br.senac.dao.PagamentoDAO;
import br.senac.dao.PessoaDAO;
import br.senac.dto.PagamentoDTO;
import br.senac.dto.PedidoDTO;
import br.senac.dto.PessoaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class PagamentoService {

    @Inject
    DataSource dataSource;

    @Inject
    PagamentoDAO pagamentoDAO;

    @Inject
    PessoaDAO pessoaDAO;

    @Transactional
    public void createPagamento(PagamentoDTO pagamento) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            int nextId = pagamentoDAO.getNextId(conn);
            pagamento.setId(nextId);
            pagamentoDAO.save(conn, pagamento);
        }
    }

    @Transactional
    public void updatePagamento(PagamentoDTO pagamento) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            pagamentoDAO.update(conn, pagamento);
        }
    }

    @Transactional
    public void deletePagamento(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            pagamentoDAO.delete(conn, id);
        }
    }
    public PagamentoDTO getPagamentoById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return pagamentoDAO.findById(conn, id);
        }
    }

    public List<PagamentoDTO> getAllPagamentos() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return pagamentoDAO.findAll(conn);
        }
    }
}

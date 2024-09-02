package br.senac.service;

import br.senac.dao.PagamentoDAO;
import br.senac.dto.PagamentoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PagmentoService {

    private PagamentoDAO pagamentoDAO;

    @Inject
    private DataSource dataSource;

    @Transactional
    private void createPagamento(PagamentoDTO pagamento) throws SQLException {
        try(Connection conn = dataSource.getConnection()){
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
            PagamentoDTO pagamento = pagamentoDAO.findById(conn, id);
            return pagamento;
        }
    }
    public List<PagamentoDTO> getAllPagamento() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            List<PagamentoDTO> pagamento = pagamentoDAO.findAll(conn);
            /*for (PagamentoDTO pagmento : pagamento){
                List<P>
            }*/

            return pagamento;
        }
    }


}

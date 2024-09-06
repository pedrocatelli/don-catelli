package br.senac.service;

import br.senac.dao.PedidoDAO;
import br.senac.dto.PedidoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PedidoService {

    @Inject
    private PedidoDAO pedidoDAO;

    @Inject
    private DataSource dataSource;

    @Transactional
    public void createPedido(PedidoDTO pedido) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            int nextId = pedidoDAO.getNextId(conn);
            pedido.setId(nextId);
            pedidoDAO.save(conn, pedido);
        }
    }

    @Transactional
    public void updatePedido(PedidoDTO pedido) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            pedidoDAO.update(conn, pedido);
        }
    }

    @Transactional
    public void deletePedido( int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            pedidoDAO.delete(conn, id);
        }
    }
    public PedidoDTO getPedidoById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return pedidoDAO.findById(conn, id);
        }
    }

    public List<PedidoDTO> getAllPedidos() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return pedidoDAO.findAll(conn);
        }
    }
}
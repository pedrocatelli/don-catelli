package br.senac.dao;

import br.senac.dto.*;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PedidoDAO {
    public void save(Connection conn, PedidoDTO pedido) throws SQLException {
        String sql = "INSERT INTO public.pedido (id, id_marmita, id_pagamento) VALUES (?, ?, ?,)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            stmt.setInt(index++, pedido.getId());
            stmt.setInt(index++, pedido.getMarmita() != null ? pedido.getMarmita().getId() : null);
            stmt.setObject(index, pedido.getPagamento() != null ? pedido.getPagamento().getId() : null);
            stmt.executeUpdate();
        }
    }

    public void update(Connection conn,  PedidoDTO pedido) throws SQLException {
        String sql = "UPDATE public.pedido SET id_marmita = ?, id_pagamento = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            stmt.setInt(index++, pedido.getMarmita() != null ? pedido.getMarmita().getId() : null);
            stmt.setObject(index++, pedido.getPagamento() != null ? pedido.getPagamento().getId() : null);
            stmt.setInt(index, pedido.getId());
            stmt.executeUpdate();
        }
    }
    public void delete(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM public.pedido WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    public PedidoDTO findById(Connection conn, int id) throws SQLException {
        String sql = "SELECT id, id_marmita, id_pagamento FROM public.pedido WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PedidoDTO pedido = new PedidoDTO();
                    pedido.setId(rs.getInt("id"));
                    if (rs.getInt("id_marmita") != 0) {
                        MarmitaDTO marmita = new MarmitaDTO();
                        marmita.setId(rs.getInt("id_marmita"));
                        pedido.setMarmita(marmita);
                    }
                    if (rs.getInt("id_pagamento") != 0) {
                        PagamentoDTO pagamento = new PagamentoDTO();
                        pagamento.setId(rs.getInt("id_pagamento"));
                        pedido.setPagamento(pagamento);
                    }
                    return pedido;
                }
            }
        }
        return null;
    }

    public List<PedidoDTO> findAll(Connection conn) throws SQLException {
        String sql = "SELECT id, id_marmita, id_pagamento FROM public.pedido";
        List<PedidoDTO> pedidos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PedidoDTO pedido = new PedidoDTO();
                pedido.setId(rs.getInt("id"));
                if (rs.getInt("id_marmita") != 0) {
                    MarmitaDTO marmita = new MarmitaDTO();
                    marmita.setId(rs.getInt("id_marmita"));
                    pedido.setMarmita(marmita);
                }
                if (rs.getInt("id_pagamento") != 0) {
                    PagamentoDTO pagamento = new PagamentoDTO();
                    pagamento.setId(rs.getInt("id_pagamento"));
                    pedido.setPagamento(pagamento);
                }
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    public int getNextId(Connection conn) throws SQLException {
        String sql = "SELECT nextval('public.sq_pedido')";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

}

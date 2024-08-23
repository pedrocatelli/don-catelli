package br.senac.dao;

import br.senac.dto.EnderecoDTO;
import br.senac.dto.PedidoDTO;
import br.senac.dto.PessoaDTO;
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
        String sql = "INSERT INTO public.pedido (id, marmita, pagamento) VALUES (?, ?, ?,)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            stmt.setInt(index++, pedido.getId());
            stmt.setObject(index++, pedido.getMarmita());
            stmt.setObject(index, pedido.getPagamento());
            stmt.executeUpdate();
        }
    }

    public void update(Connection conn,  PedidoDTO pedido) throws SQLException {
        String sql = "UPDATE public.pedido SET marmita = ?, pagamento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            stmt.setObject(index++, pedido.getMarmita());
            stmt.setObject(index, pedido.getPagamento());
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
        String sql = "SELECT id, marmita, pagamento, id_pedido FROM public.pedido WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PedidoDTO pedido = new PedidoDTO();
                    pedido.setId(rs.getInt("id"));
                    pedido.setMarmita(rs.getObject("marmita"));
                    pedido.setPagamento(rs.getObject("pagamento"));
                    return pedido;
                }
            }
        }
        return null;
    }

    public List<PedidoDTO> findAll(Connection conn) throws SQLException {
        String sql = "SELECT id, marmita, pagamento, id_pedido FROM public.endereco";
        List<PedidoDTO> pedidos = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PedidoDTO pedido = new PedidoDTO();
                pedido.setId(rs.getInt("id"));
                pedido.setMarmita(rs.getObject("marmita"));
                pedido.setPagamento(rs.getObject("pagamento"));
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }
}

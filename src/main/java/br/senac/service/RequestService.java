package br.senac.service;

import br.senac.dao.*;
import br.senac.dto.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class RequestService {

    @Inject
    EnderecoDAO enderecoDAO;

    @Inject
    DataSource dataSource;

    @Inject
    PagamentoDAO pagamentoDAO;

    @Inject
    PessoaDAO pessoaDAO;

    @Inject
    PedidoDAO pedidoDAO;


    @Transactional
    public void createCompra(PessoaDTO pessoa, EnderecoDTO endereco, PagamentoDTO pagamento, PedidoDTO pedido, List<MarmitaDTO> marmitas) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            int nextId = pessoaDAO.getNextId(conn);
            pessoa.setId(nextId);
            pessoaDAO.save(conn, pessoa);
            salvarEndereco(pessoa, endereco, conn);

            int nextId2 = pagamentoDAO.getNextId(conn);
            pagamento.setId(nextId2);
            pagamentoDAO.save(conn, pagamento);

            for(MarmitaDTO marmita : marmitas){
                int nextId3 = pedidoDAO.getNextId(conn);
                pedido.setId(nextId3);
                pedidoDAO.save(conn, pedido);
            }
        }
    }

    private void salvarEndereco(PessoaDTO pessoa, EnderecoDTO endereco, Connection conn) throws SQLException {
        int nextEnderecoId = enderecoDAO.getNextId(conn);
        endereco.setId(nextEnderecoId);
        endereco.setPessoa(pessoa);
        enderecoDAO.save(conn, endereco);
    }
}

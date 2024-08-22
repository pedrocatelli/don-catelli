package br.senac.service;

import br.senac.dao.PessoaDAO;
import br.senac.dao.EnderecoDAO;
import br.senac.dto.PessoaDTO;
import br.senac.dto.EnderecoDTO;
import br.senac.exceptions.PessoaNaoEncontradaException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

@ApplicationScoped
public class PessoaService {

    @Inject
    private PessoaDAO pessoaDAO;

    @Inject
    private EnderecoDAO enderecoDAO;

    @Inject
    private DataSource dataSource;

    @Transactional
    public void createPessoa(PessoaDTO pessoa) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            int nextId = pessoaDAO.getNextId(conn);
            pessoa.setId(nextId);
            pessoaDAO.save(conn, pessoa);

            for (EnderecoDTO endereco : pessoa.getEnderecos()) {
                salvarEndereco(pessoa, endereco, conn);
            }
        }
    }

    @Transactional
    public void createEndereco(int id, EnderecoDTO endereco) throws SQLException{
        try (Connection conn = dataSource.getConnection()){
            PessoaDTO pessoa = pessoaDAO.findById(conn, id);
            salvarEndereco(pessoa, endereco, conn);
        }
    }

    private void salvarEndereco(PessoaDTO pessoa, EnderecoDTO endereco, Connection conn) throws SQLException {
        int nextEnderecoId = enderecoDAO.getNextId(conn);
        endereco.setId(nextEnderecoId);
        endereco.setPessoa(pessoa);
        enderecoDAO.save(conn, endereco);
    }

    public List<EnderecoDTO> buscarEnderecos(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {

            if(pessoaDAO.findById(conn, id) == null){
                throw new PessoaNaoEncontradaException();
            }

            List<EnderecoDTO> enderecos = enderecoDAO.findByPessoaId(conn, id);

            return enderecos;
        }
    }

    @Transactional
    public void updatePessoa(PessoaDTO pessoa) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            pessoaDAO.update(conn, pessoa);

            for (EnderecoDTO endereco : pessoa.getEnderecos()) {
                endereco.setPessoa(pessoa);
                enderecoDAO.update(conn, endereco);
            }
        }
    }

    @Transactional
    public void deletePessoa(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            List<EnderecoDTO> enderecos = enderecoDAO.findByPessoaId(conn, id);
            for (EnderecoDTO endereco : enderecos) {
                enderecoDAO.delete(conn, endereco.getId());
            }
            pessoaDAO.delete(conn, id);
        }
    }

    public PessoaDTO getPessoaById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            PessoaDTO pessoa = pessoaDAO.findById(conn, id);
            if (pessoa != null) {
                List<EnderecoDTO> enderecos = enderecoDAO.findByPessoaId(conn, id);
                pessoa.setEnderecos(new ArrayList<>(enderecos));
            }
            return pessoa;
        }
    }

    public List<PessoaDTO> getAllPessoas() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            List<PessoaDTO> pessoas = pessoaDAO.findAll(conn);
            for (PessoaDTO pessoa : pessoas) {

                // Recuperar os endereços da pessoa e a UF de cada endereço
                List<EnderecoDTO> enderecos = enderecoDAO.findByPessoaId(conn, pessoa.getId());
                pessoa.setEnderecos(new ArrayList<>(enderecos));
            }
            return pessoas;
        }
    }
}

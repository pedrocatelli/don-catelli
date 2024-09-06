package br.senac.service;

import br.senac.dao.ComboDAO;
import br.senac.dao.EnderecoDAO;
import br.senac.dto.ComboDTO;
import br.senac.dto.EnderecoDTO;
import br.senac.dto.PessoaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ComboService {

    @Inject
    private ComboDAO comboDAO;

    @Inject
    private EnderecoDAO enderecoDAO;

    @Inject
    private DataSource dataSource;

    @Transactional
    public void createCombo(ComboDTO combo) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            int nextId = comboDAO.getNextId(conn);
            combo.setId(nextId);
            comboDAO.save(conn, combo);

        }
    }

    @Transactional
    public void updateCombo(ComboDTO combo) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            comboDAO.update(conn, combo);
        }
    }

    @Transactional
    public void deleteCombo(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            comboDAO.delete(conn, id);
        }
    }

    public ComboDTO getComboById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return comboDAO.findById(conn, id);
        }
    }
    public List<ComboDTO> getAllCombos() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return comboDAO.findAll(conn);
        }
    }

}

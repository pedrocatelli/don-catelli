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
    private void createCombo(ComboDAO combo) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            int nextId = combo.getNextId(conn);
            combo.setId(nextId);
            comboDAO.save(conn, combo);

        }
    }

    @Transactional
    public void updateComobo(ComboDTO combo) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            comboDAO.update(conn, combo);

        }
    }

    @Transactional
    public void deleteCombo(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            List<ComboDTO> combos = comboDAO.findByComboId(conn, id);
            for (ComboDTO combo : combos) {
                comboDAO.delete(conn, combo.getId());
            }
            comboDAO.delete(conn, id);
        }
    }

    public ComboDTO getComboById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ComboDTO combo = comboDAO.findById(conn, id);
            if (combo != null) {
                List<ComboDTO> combos = comboDAO.findByComboId(conn, id);
                combo.setCombo(new ArrayList<>(combos));
            }
            return combo;
        }
    }
    public List<ComboDTO> getAllCombos() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            List<ComboDTO> combo = comboDAO.findAll(conn);

            return combo;
        }
    }

}

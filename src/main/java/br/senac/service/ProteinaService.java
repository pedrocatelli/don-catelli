package br.senac.service;

import br.senac.dao.ProteinaDAO;
import br.senac.dto.ProteinaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

@ApplicationScoped
public class ProteinaService {


    @Inject
    private ProteinaDAO proteinaDAO;

    @Inject
    private DataSource dataSource;

    @Transactional
    public void createProteina(ProteinaDTO proteina) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            int nextId = proteinaDAO.getNextId(conn);
            proteina.setId(nextId);
            proteinaDAO.save(conn, proteina);

        }
    }

    @Transactional
    public void updateProteina(ProteinaDTO proteina) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            proteinaDAO.update(conn, proteina);
        }
    }

    @Transactional
    public void deleteProteina(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            proteinaDAO.delete(conn, id);
        }
    }

    public ProteinaDTO getProteinaById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ProteinaDTO proteina = proteinaDAO.findById(conn, id);
            return proteina;
        }
    }

    public List<ProteinaDTO> getAllProteina() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            List<ProteinaDTO> proteina = proteinaDAO.findAll(conn);
            return proteina;
        }
    }
}
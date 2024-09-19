package br.senac.service;

import br.senac.dao.ComboDAO;
import br.senac.dao.MarmitaDAO;
import br.senac.dao.ProteinaDAO;
import br.senac.dto.ComboDTO;
import br.senac.dto.MarmitaDTO;
import br.senac.dto.ProteinaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class MarmitaService {
    @Inject
    private MarmitaDAO marmitaDAO;

    @Inject
    private ProteinaDAO proteinaDAO;

    @Inject
    private ComboDAO comboDAO;

    @Inject
    private DataSource dataSource;

    @Transactional
    public void createMarmita(MarmitaDTO marmita) throws SQLException{
        try(Connection conn = dataSource.getConnection()){
            int nextId = marmitaDAO.getNextId(conn);
            marmita.setId(nextId);
            marmitaDAO.save(conn, marmita);

        }
    }
    @Transactional
    public void updateMarmita(MarmitaDTO marmita) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            marmitaDAO.update(conn, marmita);

        }
    }

    @Transactional
    public void deleteMarmita(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
           marmitaDAO.delete(conn, id);
        }
    }

    public MarmitaDTO getMarmitaById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            MarmitaDTO marmita = marmitaDAO.findById(conn, id);
            return marmita;
        }
    }
    public List<MarmitaDTO> getAllMarmitas() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            List<MarmitaDTO> marmita = marmitaDAO.findAll(conn);

            return marmita;
        }
    }
    public ComboDTO getComboByMarmitaId(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            MarmitaDTO marmita = marmitaDAO.findById(conn, id);
            ComboDTO combo = comboDAO.findById(conn, marmita.getCombo().getId());
            return combo;
        }
    }
    public ProteinaDTO getProteinaByMarmitaId(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            MarmitaDTO marmita = marmitaDAO.findById(conn, id);
            ProteinaDTO proteina = proteinaDAO.findById(conn, marmita.getProteina().getId());
            return proteina;
        }
    }

    public List<MarmitaDTO> getTop6Marmitas() throws SQLException{
        try (Connection conn = dataSource.getConnection()){
            return marmitaDAO.maisPedidas(conn);
        }
    }

    public List<MarmitaDTO> getMarmitasDeUmCombo(int id) throws SQLException{
        try (Connection conn = dataSource.getConnection()){
            return marmitaDAO.findMarmitasDoCombo(conn, id);
        }
    }

}
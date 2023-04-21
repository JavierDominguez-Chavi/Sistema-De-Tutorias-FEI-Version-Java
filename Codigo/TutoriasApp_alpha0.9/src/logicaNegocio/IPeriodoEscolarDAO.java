package logicaNegocio;

import dominio.PeriodoEscolar;
import java.util.ArrayList;
import java.sql.SQLException;
import java.time.LocalDate;

public interface IPeriodoEscolarDAO {
    public boolean registrarPeriodoEscolar(PeriodoEscolar periodoEscolar) 
        throws SQLException;
    public PeriodoEscolar obtenerPeriodoEscolarPorId(int idPeriodoEscolar) 
        throws SQLException;
    public ArrayList <PeriodoEscolar> obtenerTodosLosPeriodosEscolaresRegistrados() 
        throws SQLException;
    public boolean validarPeriodoEscolarNuevo(PeriodoEscolar periodo) 
        throws SQLException;
    public PeriodoEscolar obtenerPeriodoEscolarActualPorFechaDelSistema(LocalDate fechaDelSistema)
        throws SQLException;
    public ArrayList<PeriodoEscolar> obtenerPeriodosEscolaresSinFechasDeTutoriaPorProgramaEducativo
        (int idProgramaEducativo) throws SQLException;
    public ArrayList<PeriodoEscolar> obtenerPeriodosEscolaresConFechasDeTutoriaPorProgramaEducativo
        (int idProgramaEducativo) throws SQLException;
    public boolean verificarExistenciaDePeriodosEscolares() throws SQLException;
}

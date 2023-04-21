package logicaNegocio;

import logicaNegocio.EstudianteDAO;
import dominio.Academico;
import dominio.Estudiante;
import dominio.ProgramaEducativo;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;


public class EstudianteDAOTest {
    
    public EstudianteDAOTest() {
    }
    
    //@Test
    public void testRegistrarEstudiante() {
        try {
            System.out.println("registrarEstudianteExito");
            Estudiante estudiante = new Estudiante();
            estudiante.setMatricula("S30015745");
            estudiante.setNombre("Montserrat");
            estudiante.setApellidoPaterno("Olivares");
            estudiante.setApellidoMaterno("Sorenti");
            estudiante.setCorreoElectronicoPersonal("tengounpanytuno@gmail.com");
            estudiante.setCorreoElectronicoInstitucional("zs30015745@estudiantes.uv.mx");
            EstudianteDAO instancia = new EstudianteDAO();
            boolean restultadoEsperado = true;
            boolean resultadoObtenido = instancia.registrarEstudiante(estudiante);
            assertEquals(restultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testRegistrarEstudianteFallo_IdRepetida() {
        System.out.println("registrarEstudianteFallo_IdRepedita");
        Estudiante estudiante = new Estudiante();
        estudiante.setMatricula("S30015745");
        estudiante.setNombre(" ");
        estudiante.setApellidoPaterno(" ");
        estudiante.setApellidoMaterno(" ");
        estudiante.setCorreoElectronicoInstitucional(" ");
        estudiante.setCorreoElectronicoPersonal(" ");
        EstudianteDAO instancia = new EstudianteDAO();
        Exception excepcion = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            instancia.registrarEstudiante(estudiante);
        });
        String mensajeEsperado = "Duplicate entry 'S30015745' for key 'estudiantes.PRIMARY'";
        String mensajeObtenido = excepcion.getMessage();
        assertEquals(mensajeEsperado,mensajeObtenido);
    }
    
    @Test
    public void testRegistrarEstudianteFallo_CampoVacio() {
        System.out.println("registrarEstudianteFallo_CampoVacio");
        Estudiante estudiante = new Estudiante();
        estudiante.setMatricula("S30015745");
        estudiante.setApellidoPaterno(" ");
        estudiante.setApellidoMaterno(" ");
        estudiante.setCorreoElectronicoInstitucional(" ");
        estudiante.setCorreoElectronicoPersonal(" ");
        EstudianteDAO instancia = new EstudianteDAO();
        Exception excepcion = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            instancia.registrarEstudiante(estudiante);
        });
        String mensajeEsperado = "Column 'nombre' cannot be null";
        String mensajeObtenido = excepcion.getMessage();
        assertEquals(mensajeEsperado,mensajeObtenido);
    }
    

    @Test
    public void testObtenerEstudiantesPorIdDelTutor() {
        try {
            int idTutor = 1;
            EstudianteDAO instancia = new EstudianteDAO();
            
            ArrayList<Estudiante> resultadoEsperado = new ArrayList<>();
            Estudiante expectedEstudiante = new Estudiante();
            Academico tutor = new Academico();
            tutor.setIdAcademico(1);
            expectedEstudiante.setMatricula("S19014018");
            expectedEstudiante.setNombre("Yasser");
            expectedEstudiante.setApellidoPaterno("Gapi");
            expectedEstudiante.setApellidoMaterno("Perez");
            resultadoEsperado.add(expectedEstudiante);
            
            ArrayList<Estudiante> resultadoObtenido = instancia.obtenerEstudiantesPorIdDelTutor(tutor);
            assertEquals("testObtenerEstudiantesPorIdDelTutor", resultadoEsperado.get(0), resultadoObtenido.get(1));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerEstudiantesPorIdDelTutorFallo() {
        try {
            EstudianteDAO instancia = new EstudianteDAO();
            Academico tutor = new Academico();
            tutor.setIdAcademico(99);
            ArrayList<Estudiante> resultadoObtenido = instancia.obtenerEstudiantesPorIdDelTutor(tutor);
            assertTrue(resultadoObtenido.isEmpty());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerEstudiantesConTutorPorProgramaEducativo() {
        try {
            System.out.println("obtenerEstudiantesConTutorPorProgramaEducativoExito");
            Estudiante estudianteRedesConTutor1 = new Estudiante();
            estudianteRedesConTutor1.setMatricula("S20015512");
            estudianteRedesConTutor1.setNombre("Fátima Selena");
            estudianteRedesConTutor1.setApellidoPaterno("Cota");
            estudianteRedesConTutor1.setApellidoMaterno("Castillo");
            Estudiante estudianteRedesConTutor2 = new Estudiante();
            estudianteRedesConTutor2.setMatricula("S20015513");
            estudianteRedesConTutor2.setNombre("Josué Rodrigo");
            estudianteRedesConTutor2.setApellidoPaterno("Solís");
            estudianteRedesConTutor2.setApellidoMaterno("Colmenas");
            ArrayList<Estudiante> resultadoEsperado = new ArrayList<>();
            resultadoEsperado.add(estudianteRedesConTutor1);
            resultadoEsperado.add(estudianteRedesConTutor2);
            int idProgramaEducativo = 2;
            EstudianteDAO instancia = new EstudianteDAO();
            ArrayList<Estudiante> resultadoObtenido = instancia.obtenerEstudiantesConTutorPorProgramaEducativo(idProgramaEducativo);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    @Test
    public void testObtenerEstudiantesConTutorPorProgramaEducativoFallo() {
        try {
            System.out.println("obtenerEstudiantesQueTienenTutorPorProgramaEducativoFallo");
            int idProgramaEducativo = 99;
            EstudianteDAO instancia = new EstudianteDAO();
            ArrayList<Estudiante> resultadoObtenido =
                instancia.obtenerEstudiantesConTutorPorProgramaEducativo(idProgramaEducativo);
            assertTrue(resultadoObtenido.isEmpty());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerEstudiantesSinTutorPorProgramaEducativo(){
        try {
            System.out.println("obtenerEstudiantesSinTutorPorProgramaEducativoExito");
            Estudiante estudianteRedesSinTutor1 = new Estudiante();
            estudianteRedesSinTutor1.setMatricula("S20015510");
            estudianteRedesSinTutor1.setNombre("Paulina");
            estudianteRedesSinTutor1.setApellidoPaterno("Loranca");
            estudianteRedesSinTutor1.setApellidoMaterno("Jimenez");
            Estudiante estudianteRedesSinTutor2 = new Estudiante();
            estudianteRedesSinTutor2.setMatricula("S20015511");
            estudianteRedesSinTutor2.setNombre("Jorge Ernesto");
            estudianteRedesSinTutor2.setApellidoPaterno("Suárez");
            estudianteRedesSinTutor2.setApellidoMaterno("Bosques");
            ArrayList<Estudiante> resultadoEsperado = new ArrayList<>();
            resultadoEsperado.add(estudianteRedesSinTutor1);
            resultadoEsperado.add(estudianteRedesSinTutor2);
            int idProgramaEducativo = 2;
            EstudianteDAO instancia = new EstudianteDAO();
            ArrayList<Estudiante> resultadoObtenido = instancia.obtenerEstudiantesSinTutorPorProgramaEducativo(idProgramaEducativo);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerEstudiantesSinTutorPorProgramaEducativoFallo() {
        try {
            System.out.println("obtenerEstudiantesSinTutorPorProgramaEducativoFallo");
            int idProgramaEducativo = 99;
            EstudianteDAO instancia = new EstudianteDAO();
            ArrayList<Estudiante> resultadoObtenido = 
                instancia.obtenerEstudiantesSinTutorPorProgramaEducativo(idProgramaEducativo);
            assertTrue(resultadoObtenido.isEmpty());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    //@Test
    public void testAsignarTutorAcademico() {
        try {
            System.out.println("asignarTutorAcademicoExito");
            EstudianteDAO instancia = new EstudianteDAO();
            String matricula = "S20000000";
            int idAcademico = 3;
            boolean resultadoEsperado = true;
            boolean resultadoObtenido = instancia.asignarTutorAcademico(matricula,idAcademico);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testAsignarTutorAcademicoFallo_TutorNoExiste() {
        System.out.println("asignarTutorAcademicoFallo1_TutorNoExiste");
        EstudianteDAO instancia = new EstudianteDAO();
        String matricula = "S20000000";
        int idAcademico = 0;
        Exception exception = assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            instancia.asignarTutorAcademico(matricula,idAcademico);
        });
        String mensajeEsperado = "Cannot add or update a child row: a foreign key constraint fails";
        String mensajeObtenido = exception.getMessage();
        assertEquals(mensajeEsperado,mensajeObtenido);
    }
    
    @Test
    public void testAsignarTutorAcademicoFallo_EstudianteNoExiste() {
        try {
            System.out.println("asignarTutorAcademicoFallo2_EstudianteNoExiste");
            EstudianteDAO instancia = new EstudianteDAO();
            String matricula = "S50000000";
            int idAcademico = 2;
            boolean resultadoEsperado = false;
            boolean resultadoObtenido = instancia.asignarTutorAcademico(matricula,idAcademico);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerEstudiantePorMatriculaExitoso() throws Exception {
        System.out.println("obtenerEstudiantePorMatriculaExitoso");
        String matricula = "S19023584";
        EstudianteDAO instancia = new EstudianteDAO();
        Estudiante resultadoEsperado = new Estudiante();
        resultadoEsperado.setMatricula("S19023584");
        resultadoEsperado.setNombre("Cristopher");
        resultadoEsperado.setApellidoMaterno("Salamanca");
        resultadoEsperado.setApellidoPaterno("Rodriguez");
        resultadoEsperado.setCorreoElectronicoInstitucional("zs19023584@estudiantes.uv.mx");
        resultadoEsperado.setCorreoElectronicoPersonal("salasala@hotmail.com");
        Estudiante result = instancia.obtenerEstudiantePorMatricula(matricula);
        assertEquals(resultadoEsperado, result);
    }
    
    @Test
    public void testObtenerEstudiantePorMatriculaFallido() throws Exception {
        System.out.println("obtenerEstudiantePorMatriculaFallido");
        String matricula = "S20000003";
        EstudianteDAO instancia = new EstudianteDAO();
        Estudiante resultadoEsperado = new Estudiante();
        resultadoEsperado.setMatricula("S19023584");
        resultadoEsperado.setNombre("Cristopher");
        resultadoEsperado.setApellidoMaterno("Salamanca");
        resultadoEsperado.setApellidoPaterno("Rodriguez");
        resultadoEsperado.setCorreoElectronicoInstitucional("zs19023584@estudiantes.uv.mx");
        resultadoEsperado.setCorreoElectronicoPersonal("salasala@hotmail.com");
        Estudiante result = instancia.obtenerEstudiantePorMatricula(matricula);
        assertNotEquals(resultadoEsperado, result);
    }
    
    @Test
    public void testObtenerEstudiantesPorIdProgramaEducativoExitoso() throws Exception {
        System.out.println("obtenerEstudiantesPorIdProgramaEducativoExitoso");
        int idProgramaEducativo = 0;
        EstudianteDAO instancia = new EstudianteDAO();
        ArrayList<Estudiante> result = instancia.obtenerEstudiantesPorIdProgramaEducativo(idProgramaEducativo);
        assertEquals(28, result.size());
    }
    
    @Test
    public void testObtenerEstudiantesPorIdProgramaEducativoFallido() throws Exception {
        System.out.println("obtenerEstudiantesPorIdProgramaEducativoFallido");
        int idProgramaEducativo = 0;
        EstudianteDAO instancia = new EstudianteDAO();
        ArrayList<Estudiante> result = instancia.obtenerEstudiantesPorIdProgramaEducativo(idProgramaEducativo);
        assertNotEquals(200, result.size());
    }

    @Test
    public void testObtenerEstudiantesPorIdDelTutorEIdProgramaEducativoExitoso() throws Exception {
        System.out.println("obtenerEstudiantesPorIdDelTutorEIdProgramaEducativoExitoso");
        Academico tutor = new Academico();
        tutor.setIdAcademico(1);
        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        programaEducativo.setIdProgramaEducativo(0);
        EstudianteDAO instancia = new EstudianteDAO();
        ArrayList<Estudiante> result = instancia.obtenerEstudiantesPorIdDelTutorEIdProgramaEducativo(tutor, programaEducativo);
        assertEquals(5, result.size());
    }
    
    @Test
    public void testObtenerEstudiantesPorIdDelTutorEIdProgramaEducativoFallido() throws Exception {
        System.out.println("obtenerEstudiantesPorIdDelTutorEIdProgramaEducativoFallido");
        Academico tutor = new Academico();
        tutor.setIdAcademico(1);
        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        programaEducativo.setIdProgramaEducativo(0);
        EstudianteDAO instancia = new EstudianteDAO();
        ArrayList<Estudiante> result = instancia.obtenerEstudiantesPorIdDelTutorEIdProgramaEducativo(tutor, programaEducativo);
        assertEquals(5, result.size());
    }
    
}

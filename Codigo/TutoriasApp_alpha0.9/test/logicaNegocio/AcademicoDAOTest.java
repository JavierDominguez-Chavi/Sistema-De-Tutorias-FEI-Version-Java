package logicaNegocio;

import logicaNegocio.AcademicoDAO;
import dominio.Academico;
import dominio.constantes.Roles;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class AcademicoDAOTest {
    
    public AcademicoDAOTest() {
    }

    //@Test
    public void testRegistrarAcademico() {
        try {
            System.out.println("registrarAcademicoExito");
            Academico academico = new Academico();
            academico.setNombre("academicoTest");
            academico.setApellidoPaterno("academicoTest");
            academico.setApellidoMaterno("academicoTest");
            academico.setCorreoElectronicoInstitucional("academicoTest");
            academico.setCorreoElectronicoPersonal("academicoTest");
            AcademicoDAO instancia = new AcademicoDAO();
            boolean resultadoEsperado = true;
            boolean resultadoObtenido = instancia.registrarAcademico(academico);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerAcademicoPorId() {
        try {
            System.out.println("obtenerAcademicoPorIdExito");
            int idAcademico = 1;
            AcademicoDAO instancia = new AcademicoDAO();
            Academico resultadoEsperado = new Academico();
            resultadoEsperado.setIdAcademico(idAcademico);
            resultadoEsperado.setNombre("Virginia");
            resultadoEsperado.setApellidoPaterno("Lagunes");
            resultadoEsperado.setApellidoMaterno("Barradas");
            Academico resultadoObtenido = instancia.obtenerAcademicoPorId(idAcademico);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerAcademicoPorIdFallo() {
        try {
            System.out.println("obtenerAcademicoPorIdFallo");
            int idAcademico = 0;
            AcademicoDAO instancia = new AcademicoDAO();
            Academico resultadoObtenido = instancia.obtenerAcademicoPorId(idAcademico);
            String resultadoEsperado = null;
            assertEquals(resultadoEsperado, resultadoObtenido.getNombre());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerAcademicosPorIdProgramaEducativo() {
        try {
            System.out.println("obtenerAcademicosPorIdProgramaEducativoExito");
            int idProgramaEducativo = 2;
            AcademicoDAO instancia = new AcademicoDAO();
            ArrayList<Academico> resultadoEsperado = new ArrayList<>();
            Academico academico1 = new Academico();
            academico1.setIdAcademico(10);
            academico1.setNombre("María de los Ángeles");
            academico1.setApellidoPaterno("Arenas");
            academico1.setApellidoMaterno("Valdes");
            Academico academico2 = new Academico();
            academico2.setIdAcademico(9);
            academico2.setNombre("Sergio Luis");
            academico2.setApellidoPaterno("Castillo");
            academico2.setApellidoMaterno("Valerio");
            resultadoEsperado.add(academico1);
            resultadoEsperado.add(academico2);
            ArrayList<Academico> resultadoObtenido = 
                instancia.obtenerAcademicosPorProgramaEducativo(idProgramaEducativo);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerAcademicosPorIdProgramaEducativoFallo() {
        try {
            System.out.println("obtenerAcademicosPorIdProgramaEducativoFallo");
            int idProgramaEducativo = 99;
            AcademicoDAO instancia = new AcademicoDAO();
            ArrayList<Academico> resultadoObtenido = 
                instancia.obtenerAcademicosPorProgramaEducativo(idProgramaEducativo);
            assertTrue(resultadoObtenido.isEmpty());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerNombreCompletoDeTutoresPorProgramaEducativo() {
        try {
            System.out.println("obtenerTutoresConNumeroDeTutoradosPorProgramaEducativoExito");
            Academico tutorObtenido = new Academico();
            tutorObtenido.setIdAcademico(9);
            tutorObtenido.setNombre("Sergio Luis");
            tutorObtenido.setApellidoMaterno("Valerio");
            tutorObtenido.setApellidoPaterno("Castillo");
            ArrayList<Academico> resultadoEsperado = new ArrayList<>();
            resultadoEsperado.add(tutorObtenido);
            AcademicoDAO instancia = new AcademicoDAO();
            int idProgramaEducativo = 2;
            int idRol = Roles.TUTOR_ACADEMICO.getIdRol();
            ArrayList<Academico> resultadoObtenido = 
                instancia.obtenerNombreCompletoDeTutoresPorIdProgramaEducativo(
                    idProgramaEducativo,idRol
            );
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerNombreCompletoDeTutoresPorProgramaEducativoFallo() {
        try {
            System.out.println("obtenerTutoresConNumeroDeTutoradosPorProgramaEducativoFallo");
            int idProgramaEducativo = 22;
            int idRol = Roles.TUTOR_ACADEMICO.getIdRol();
            AcademicoDAO instancia = new AcademicoDAO();
            ArrayList<Academico> resultado =
                instancia.obtenerNombreCompletoDeTutoresPorIdProgramaEducativo(
                    idProgramaEducativo,idRol
                );
            assertTrue(resultado.isEmpty());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerNombreCompletoDeProfesores()  {
        try {
            System.out.println("obtenerNombreCompletoDeProfesoresExito");
            Academico profesorObtenido1 = new Academico();
            profesorObtenido1.setIdAcademico(10);
            profesorObtenido1.setNombre("María de los Ángeles");
            profesorObtenido1.setApellidoPaterno("Arenas");
            profesorObtenido1.setApellidoMaterno("Valdes");
            Academico profesorObtenido2 = new Academico();
            profesorObtenido2.setIdAcademico(1);
            profesorObtenido2.setNombre("Virginia");
            profesorObtenido2.setApellidoPaterno("Lagunes");
            profesorObtenido2.setApellidoMaterno("Barradas");
            ArrayList<Academico> resultadoEsperado = new ArrayList<>();
            resultadoEsperado.add(profesorObtenido1);
            resultadoEsperado.add(profesorObtenido2);
            int idRolProfesor = Roles.PROFESOR.getIdRol();
            AcademicoDAO instancia = new AcademicoDAO();
            ArrayList<Academico> resultadoObtenido = 
                instancia.obtenerNombreCompletoDeProfesores(idRolProfesor);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerNombreCompletoDeProfesoresFallo()  {
        try {
            System.out.println("obtenerNombreCompletoDeProfesoresFallo");
            int idRolProfesor = -1;
            AcademicoDAO instancia = new AcademicoDAO();
            ArrayList<Academico> result = instancia.obtenerNombreCompletoDeProfesores(idRolProfesor);
            assertTrue(result.isEmpty());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerNumeroDeTutoradosDeTutorPorIdAcademico() {
        try {
            System.out.println("obtenerNumeroDeTutoradosDeTutorPorIdAcademicoExito");
            int idAcademico = 2;
            AcademicoDAO instancia =  new AcademicoDAO();
            int resultadoEsperado = 5;
            int resultadoObtenido = instancia.obtenerNumeroDeTutoradosDeTutorPorIdAcademico(idAcademico);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerNumeroDeTutoradosDeTutorPorIdAcademicoFallo()  {
        try {
            System.out.println("obtenerNumeroDeTutoradosDeTutorPorIdAcademicoFallo");
            int idAcademico = 99;
            AcademicoDAO instancia = new AcademicoDAO();
            int resultadoEsperado = 0;
            int resultadoObtenido = instancia.obtenerNumeroDeTutoradosDeTutorPorIdAcademico(idAcademico);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    //@Test
    public void testModificarAcademico()  {
        try {
            System.out.println("modificarAcademicoExito");
            int idAcademico = 4;
            AcademicoDAO instancia = new AcademicoDAO();
            Academico academicoModificado = instancia.obtenerAcademicoPorId(idAcademico);
            academicoModificado.setCorreoElectronicoPersonal("TEST");
            boolean resultadoEsperado = true;
            boolean resultado = instancia.modificarAcademico(academicoModificado);
            assertEquals(resultadoEsperado, resultado);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    //@Test
    public void testModificarAcademicoFallo()  {
        try {
            System.out.println("modificarAcademicoFallo");
            int idAcademico = 2;
            AcademicoDAO instancia = new AcademicoDAO();
            Academico academicoModificado = instancia.obtenerAcademicoPorId(idAcademico);
            academicoModificado.setIdAcademico(99);
            academicoModificado.setCorreoElectronicoPersonal("ASDF");
            boolean resultadoEsperado = false;
            boolean result = instancia.modificarAcademico(academicoModificado);
            assertEquals(resultadoEsperado, result);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testBuscarNombreDeAcademicoExistente(){
        try {
            System.out.println("buscarNombreDeAcademicoExistenteExito");
            Academico academico = new Academico();
            academico.setIdAcademico(9);
            academico.setNombre("Sergio Luis");
            academico.setApellidoPaterno("Castillo");
            academico.setApellidoMaterno("Valerio");
            AcademicoDAO instancia = new AcademicoDAO();
            boolean resultadoEsperado = true;
            boolean resultadoObtenido = instancia.buscarNombreDeAcademicoExistente(academico);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testBuscarNombreDeAcademicoExistenteFallo(){
        try {
            System.out.println("buscarNombreDeAcademicoExistenteFallo");
            Academico academico = new Academico();
            academico.setIdAcademico(9);
            academico.setNombre("Sergio Luis");
            academico.setApellidoPaterno("Casdstillo");
            academico.setApellidoMaterno("Valerio");
            AcademicoDAO instancia = new AcademicoDAO();
            boolean resultadoEsperado = false;
            boolean result = instancia.buscarNombreDeAcademicoExistente(academico);
            assertEquals(resultadoEsperado, result);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testBuscarCorreoElectronicoPersonalDeAcademicoExistente() {
        try {
            System.out.println("buscarCorreoElectronicoPersonalDeAcademicoExistenteExito");
            Academico academico = new Academico();
            academico.setCorreoElectronicoPersonal("chperez@gmail.com");
            AcademicoDAO instancia = new AcademicoDAO();
            boolean resultadoEsperado = true;
            boolean resultadoObtenido = instancia.buscarCorreoElectronicoPersonalDeAcademicoExistente(academico);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testBuscarCorreoElectronicoPersonalDeAcademicoExistenteFallo() {
        try {
            System.out.println("buscarCorreoElectronicoPersonalDeAcademicoExistenteFallo");
            Academico academico = new Academico();
            academico.setCorreoElectronicoPersonal("correopro@gmail.com");
            AcademicoDAO instancia = new AcademicoDAO();
            boolean resultadoEsperado = false;
            boolean resultadoObtenido = instancia.buscarCorreoElectronicoPersonalDeAcademicoExistente(academico);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testBuscarCorreoElectronicoInstitucionalDeAcademicoExistente(){
        try {
            System.out.println("buscarCorreoElectronicoPersonalDeAcademicoExistenteExito");
            Academico academico = new Academico();
            academico.setCorreoElectronicoInstitucional("chperez@uv.mx");
            AcademicoDAO instancia = new AcademicoDAO();
            boolean resultadoEsperado = true;
            boolean resultadoObtenido = instancia.buscarCorreoElectronicoInstitucionalDeAcademicoExistente(academico);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    @Test
    public void testBuscarCorreoElectronicoInstitucionalDeAcademicoExistenteFallo(){
        try {
            System.out.println("buscarCorreoElectronicoPersonalDeAcademicoExistenteFallo");
            Academico academico = new Academico();
            academico.setCorreoElectronicoInstitucional("asdfassdf@uv.mx");
            AcademicoDAO instancia = new AcademicoDAO();
            boolean resultadoEsperado = false;
            boolean resultadoObtenido = instancia.buscarCorreoElectronicoInstitucionalDeAcademicoExistente(academico);
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerMaximoIdAcademicoInsertado(){
        try {
            System.out.println("obtenerUltimoIdAcademicoInsertadoExito");
            AcademicoDAO instancia = new AcademicoDAO();
            int resultadoEsperado = 10;
            int resultadoObtenido = instancia.obtenerMaximoIdAcademicoInsertado();
            assertEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerMaximoIdAcademicoInsertadoFallo(){
        try {
            System.out.println("obtenerUltimoIdAcademicoInsertadoFallo");
            AcademicoDAO instancia = new AcademicoDAO();
            int resultadoEsperado = 9;
            int resultadoObtenido = instancia.obtenerMaximoIdAcademicoInsertado();
            assertNotEquals(resultadoEsperado, resultadoObtenido);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}

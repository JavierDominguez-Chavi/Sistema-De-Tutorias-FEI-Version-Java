package logicaNegocio;

import logicaNegocio.AcademicoRolDAO;
import dominio.Academico;
import dominio.AcademicoRol;
import dominio.AcademicoUsuario;
import dominio.ProgramaEducativo;
import dominio.Rol;
import dominio.constantes.Roles;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;

public class AcademicoRolDAOTest {
    
    public AcademicoRolDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testObtenerAcademicoRolPorIdUsuarioExito(){
        System.out.println("obtenerAcademicoRolPorIdUsuarioExito");
        AcademicoRol academicoRolBuscar = new AcademicoRol();
        AcademicoRolDAO academicoRolDao = new AcademicoRolDAO();
        AcademicoRol academicoRolEsperado = new AcademicoRol();
        AcademicoUsuario academicoUsuario = new AcademicoUsuario();
        academicoUsuario.setIdUsuario(5);
        academicoRolBuscar.setAcademicoUsuario(academicoUsuario);
        Rol rol = new Rol();
        int ROL_TUTOR_ACADEMICO = Roles.TUTOR_ACADEMICO.getIdRol();
        rol.setIdRol(ROL_TUTOR_ACADEMICO);
        Academico academico = new Academico();
        academico.setIdAcademico(2);
        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        programaEducativo.setIdProgramaEducativo(0);
        academicoRolEsperado.setRol(rol);
        academicoRolEsperado.setAcademico(academico);
        academicoRolEsperado.setProgramaEducativo(programaEducativo);
        academicoRolEsperado.setAcademicoUsuario(academicoUsuario);
        try{
            AcademicoRol academicoRolObtenido = academicoRolDao.obtenerAcademicoRolPorIdUsuario(academicoRolBuscar);
            assertEquals(academicoRolEsperado, academicoRolObtenido);
        }  catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerAcademicoRolPorIdUsuarioFallo_IdUsuarioInexistente(){
        System.out.println("obtenerAcademicoRolPorIdUsuarioFallo_IdUsuarioInexistente");
        AcademicoRol academicoRolBuscar = new AcademicoRol();
        AcademicoRolDAO academicoRolDao = new AcademicoRolDAO();
        AcademicoRol academicoRolEsperado = new AcademicoRol();
        AcademicoUsuario academicoUsuario = new AcademicoUsuario();
        academicoUsuario.setIdUsuario(144);
        academicoRolBuscar.setAcademicoUsuario(academicoUsuario);
        Rol rol = new Rol();
        Academico academico = new Academico();
        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        academicoRolEsperado.setRol(rol);
        academicoRolEsperado.setAcademico(academico);
        academicoRolEsperado.setProgramaEducativo(programaEducativo);
        academicoRolEsperado.setAcademicoUsuario(academicoUsuario);
        try{
            AcademicoRol academicoRolObtenido = academicoRolDao.obtenerAcademicoRolPorIdUsuario(academicoRolBuscar);
            assertEquals(academicoRolEsperado, academicoRolObtenido);
        }  catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testBuscarAcademicoRolExistenteExito(){
        System.out.println("buscarAcademicoRolExistenteExito");
        AcademicoRol academicoRolBuscar =  new AcademicoRol();
        AcademicoRolDAO academicoRolDao = new AcademicoRolDAO();
        Academico academico = new Academico();
        academico.setIdAcademico(3);
        academicoRolBuscar.setAcademico(academico);
        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        programaEducativo.setIdProgramaEducativo(0);
        academicoRolBuscar.setProgramaEducativo(programaEducativo);
        Rol rol = new Rol();
        int ROL_TUTOR_ACADEMICO = Roles.TUTOR_ACADEMICO.getIdRol();
        rol.setIdRol(ROL_TUTOR_ACADEMICO);
        academicoRolBuscar.setRol(rol);
        boolean validacionEsperada = true;
        try{
            boolean validacionObtenida = academicoRolDao.buscarAcademicoRolExistente(academicoRolBuscar);
            assertEquals(validacionEsperada, validacionObtenida);
        }  catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testBuscarAcademicoRolExistenteFallo_IdAcademicoInexistente(){
        System.out.println("buscarAcademicoRolExistenteFallo_IdAcademicoInexistente");
        AcademicoRol academicoRolBuscar =  new AcademicoRol();
        AcademicoRolDAO academicoRolDao = new AcademicoRolDAO();
        Academico academico = new Academico();
        academico.setIdAcademico(99);
        academicoRolBuscar.setAcademico(academico);
        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        programaEducativo.setIdProgramaEducativo(0);
        academicoRolBuscar.setProgramaEducativo(programaEducativo);
        Rol rol = new Rol();
        int ROL_TUTOR_ACADEMICO = Roles.TUTOR_ACADEMICO.getIdRol();
        rol.setIdRol(ROL_TUTOR_ACADEMICO);
        academicoRolBuscar.setRol(rol);
        boolean validacionEsperada = false;
        try{
            boolean validacionObtenida = academicoRolDao.buscarAcademicoRolExistente(academicoRolBuscar);
            assertEquals(validacionEsperada, validacionObtenida);
        }  catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testBuscarAcademicoRolExistenteFallo_IdProgramaEducativoInexistente(){
        System.out.println("buscarAcademicoRolExistenteFallo_IdProgramaEducativoInexistente");
        AcademicoRol academicoRolBuscar =  new AcademicoRol();
        AcademicoRolDAO academicoRolDao = new AcademicoRolDAO();
        Academico academico = new Academico();
        academico.setIdAcademico(3);
        academicoRolBuscar.setAcademico(academico);
        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        programaEducativo.setIdProgramaEducativo(144);
        academicoRolBuscar.setProgramaEducativo(programaEducativo);
        Rol rol = new Rol();
        int ROL_TUTOR_ACADEMICO = Roles.TUTOR_ACADEMICO.getIdRol();
        rol.setIdRol(ROL_TUTOR_ACADEMICO);
        academicoRolBuscar.setRol(rol);
        boolean validacionEsperada = false;
        try{
            boolean validacionObtenida = academicoRolDao.buscarAcademicoRolExistente(academicoRolBuscar);
            assertEquals(validacionEsperada, validacionObtenida);
        }  catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testBuscarAcademicoRolExistenteFallo_IdRolInexistente(){
        System.out.println("buscarAcademicoRolExistenteFallo_IdRolInexistente");
        AcademicoRol academicoRolBuscar =  new AcademicoRol();
        AcademicoRolDAO academicoRolDao = new AcademicoRolDAO();
        Academico academico = new Academico();
        academico.setIdAcademico(3);
        academicoRolBuscar.setAcademico(academico);
        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        programaEducativo.setIdProgramaEducativo(0);
        academicoRolBuscar.setProgramaEducativo(programaEducativo);
        Rol rol = new Rol();
        rol.setIdRol(144);
        academicoRolBuscar.setRol(rol);
        boolean validacionEsperada = false;
        try{
            boolean validacionObtenida = academicoRolDao.buscarAcademicoRolExistente(academicoRolBuscar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    //@Test
    public void testActualizarAcademicoRolExistenteExito(){
        System.out.println("actualizarAcademicoRolExistenteExito");
        AcademicoRol academicoRolActualizar = new AcademicoRol();
        AcademicoRolDAO academicoRolDao = new AcademicoRolDAO();
        Academico academico = new Academico();
        academico.setIdAcademico(3);
        academicoRolActualizar.setAcademico(academico);
        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        programaEducativo.setIdProgramaEducativo(0);
        academicoRolActualizar.setProgramaEducativo(programaEducativo);
        AcademicoUsuario academicoUsuario = new AcademicoUsuario();
        academicoUsuario.setIdUsuario(2);
        academicoRolActualizar.setAcademicoUsuario(academicoUsuario);
        Rol rol = new Rol();
        int ROL_TUTOR_ACADEMICO = Roles.TUTOR_ACADEMICO.getIdRol();
        rol.setIdRol(ROL_TUTOR_ACADEMICO);
        academicoRolActualizar.setRol(rol);
        boolean validacionEsperada = true;
        try{
            boolean validacionObtenida = academicoRolDao.actualizarAcademicoRolExistente(academicoRolActualizar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testActualizarAcademicoRolExistenteFallo_IdAcademicoInexistente(){
        System.out.println("actualizarAcademicoRolExistenteFallo_IdAcademicoInexistente");
        AcademicoRol academicoRolActualizar = new AcademicoRol();
        AcademicoRolDAO academicoRolDao = new AcademicoRolDAO();
        Academico academico = new Academico();
        academico.setIdAcademico(144);
        academicoRolActualizar.setAcademico(academico);
        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        programaEducativo.setIdProgramaEducativo(0);
        academicoRolActualizar.setProgramaEducativo(programaEducativo);
        AcademicoUsuario academicoUsuario = new AcademicoUsuario();
        academicoUsuario.setIdUsuario(2);
        academicoRolActualizar.setAcademicoUsuario(academicoUsuario);
        Rol rol = new Rol();
        int ROL_TUTOR_ACADEMICO = Roles.TUTOR_ACADEMICO.getIdRol();
        rol.setIdRol(ROL_TUTOR_ACADEMICO);;
        academicoRolActualizar.setRol(rol);
        boolean validacionEsperada = false;
        try{
            boolean validacionObtenida = academicoRolDao.actualizarAcademicoRolExistente(academicoRolActualizar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testActualizarAcademicoRolExistenteFallo_IdProgramaEducativoInexistente(){
        System.out.println("actualizarAcademicoRolExistenteFallo_IdProgramaEducativoInexistente");
        AcademicoRol academicoRolActualizar = new AcademicoRol();
        AcademicoRolDAO academicoRolDao = new AcademicoRolDAO();
        Academico academico = new Academico();
        academico.setIdAcademico(3);
        academicoRolActualizar.setAcademico(academico);
        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        programaEducativo.setIdProgramaEducativo(144);
        academicoRolActualizar.setProgramaEducativo(programaEducativo);
        AcademicoUsuario academicoUsuario = new AcademicoUsuario();
        academicoUsuario.setIdUsuario(2);
        academicoRolActualizar.setAcademicoUsuario(academicoUsuario);
        Rol rol = new Rol();
        int ROL_TUTOR_ACADEMICO = Roles.TUTOR_ACADEMICO.getIdRol();
        rol.setIdRol(ROL_TUTOR_ACADEMICO);
        academicoRolActualizar.setRol(rol);
        boolean validacionEsperada = false;
        try{
            boolean validacionObtenida = academicoRolDao.actualizarAcademicoRolExistente(academicoRolActualizar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testActualizarAcademicoRolExistenteFallo_IdUsuarioInexistente(){
        System.out.println("actualizarAcademicoRolExistenteFallo_IdUsuarioInexistente");
        AcademicoRol academicoRolActualizar = new AcademicoRol();
        AcademicoRolDAO academicoRolDao = new AcademicoRolDAO();
        Academico academico = new Academico();
        academico.setIdAcademico(3);
        academicoRolActualizar.setAcademico(academico);
        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        programaEducativo.setIdProgramaEducativo(0);
        academicoRolActualizar.setProgramaEducativo(programaEducativo);
        AcademicoUsuario academicoUsuario = new AcademicoUsuario();
        academicoUsuario.setIdUsuario(144);
        academicoRolActualizar.setAcademicoUsuario(academicoUsuario);
        Rol rol = new Rol();
        int ROL_TUTOR_ACADEMICO = Roles.TUTOR_ACADEMICO.getIdRol();
        rol.setIdRol(ROL_TUTOR_ACADEMICO);
        academicoRolActualizar.setRol(rol);
        boolean validacionEsperada = false;
        try{
            boolean validacionObtenida = 
            academicoRolDao.actualizarAcademicoRolExistente(academicoRolActualizar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    public void testObtenerIdUsuarioDeAcademicoRolExistenteExito(){
        System.out.println("obtenerIdUsuarioDeAcademicoRolExistenteExito");
        AcademicoRol academicoRolBuscar = new AcademicoRol();
        AcademicoRolDAO academicoRolDao = new AcademicoRolDAO();
        AcademicoRol academicoRolEsperado = new AcademicoRol();
        Academico academico = new Academico();
        academico.setIdAcademico(3);
        academicoRolBuscar.setAcademico(academico);
        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        programaEducativo.setIdProgramaEducativo(0);
        academicoRolBuscar.setProgramaEducativo(programaEducativo);
        Rol rol = new Rol();
        int ROL_TUTOR_ACADEMICO = Roles.TUTOR_ACADEMICO.getIdRol();
        rol.setIdRol(ROL_TUTOR_ACADEMICO);
        academicoRolBuscar.setRol(rol);
        academicoRolEsperado.setAcademico(academico);
        academicoRolEsperado.setProgramaEducativo(programaEducativo);
        academicoRolEsperado.setRol(rol);
        AcademicoUsuario academicoUsuario = new AcademicoUsuario();
        academicoUsuario.setIdUsuario(2);
        academicoRolEsperado.setAcademicoUsuario(academicoUsuario);
        try{
            AcademicoRol academicoRolObtenido = 
            academicoRolDao.obtenerIdUsuarioDeAcademicoRolExistente(academicoRolBuscar);
            assertEquals(academicoRolEsperado, academicoRolObtenido);
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerIdUsuarioDeAcademicoRolExistenteFallo_IdAcademicoInexistente(){
        System.out.println("obtenerIdUsuarioDeAcademicoRolExistenteFallo_IdAcademicoInexistente");
        AcademicoRol academicoRolBuscar = new AcademicoRol();
        AcademicoRolDAO academicoRolDao = new AcademicoRolDAO();
        AcademicoRol academicoRolEsperado = new AcademicoRol();
        Academico academico = new Academico();
        academico.setIdAcademico(144);
        academicoRolBuscar.setAcademico(academico);
        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        programaEducativo.setIdProgramaEducativo(0);
        academicoRolBuscar.setProgramaEducativo(programaEducativo);
        Rol rol = new Rol();
        int ROL_TUTOR_ACADEMICO = Roles.TUTOR_ACADEMICO.getIdRol();
        rol.setIdRol(ROL_TUTOR_ACADEMICO);
        academicoRolBuscar.setRol(rol);
        academicoRolEsperado.setAcademico(academico);
        academicoRolEsperado.setProgramaEducativo(programaEducativo);
        academicoRolEsperado.setRol(rol);
        AcademicoUsuario academicoUsuario = new AcademicoUsuario();
        academicoUsuario.setIdUsuario(0);
        academicoRolEsperado.setAcademicoUsuario(academicoUsuario);
        try{
            AcademicoRol academicoRolObtenido = 
            academicoRolDao.obtenerIdUsuarioDeAcademicoRolExistente(academicoRolBuscar);
            assertEquals(academicoRolEsperado, academicoRolObtenido);
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerIdUsuarioDeAcademicoRolExistenteFallo_IdProgramaEducativoInexistente(){
        System.out.println("obtenerIdUsuarioDeAcademicoRolExistenteFallo_IdProgramaEducativoInexistente");
        AcademicoRol academicoRolBuscar = new AcademicoRol();
        AcademicoRolDAO academicoRolDao = new AcademicoRolDAO();
        AcademicoRol academicoRolEsperado = new AcademicoRol();
        Academico academico = new Academico();
        academico.setIdAcademico(3);
        academicoRolBuscar.setAcademico(academico);
        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        programaEducativo.setIdProgramaEducativo(144);
        academicoRolBuscar.setProgramaEducativo(programaEducativo);
        Rol rol = new Rol();
        int ROL_TUTOR_ACADEMICO = Roles.TUTOR_ACADEMICO.getIdRol();
        rol.setIdRol(ROL_TUTOR_ACADEMICO);
        academicoRolBuscar.setRol(rol);
        academicoRolEsperado.setAcademico(academico);
        academicoRolEsperado.setProgramaEducativo(programaEducativo);
        academicoRolEsperado.setRol(rol);
        AcademicoUsuario academicoUsuario = new AcademicoUsuario();
        academicoUsuario.setIdUsuario(0);
        academicoRolEsperado.setAcademicoUsuario(academicoUsuario);
        try{
            AcademicoRol academicoRolObtenido = 
            academicoRolDao.obtenerIdUsuarioDeAcademicoRolExistente(academicoRolBuscar);
            assertEquals(academicoRolEsperado, academicoRolObtenido);
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    @Test
    public void testObtenerIdUsuarioDeAcademicoRolExistenteFallo_IdRolInexistente(){
        System.out.println("obtenerIdUsuarioDeAcademicoRolExistenteFallo_IdRolInexistente");
        AcademicoRol academicoRolBuscar = new AcademicoRol();
        AcademicoRolDAO academicoRolDao = new AcademicoRolDAO();
        AcademicoRol academicoRolEsperado = new AcademicoRol();
        Academico academico = new Academico();
        academico.setIdAcademico(3);
        academicoRolBuscar.setAcademico(academico);
        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        programaEducativo.setIdProgramaEducativo(0);
        academicoRolBuscar.setProgramaEducativo(programaEducativo);
        Rol rol = new Rol();
        rol.setIdRol(114);
        academicoRolBuscar.setRol(rol);
        academicoRolEsperado.setAcademico(academico);
        academicoRolEsperado.setProgramaEducativo(programaEducativo);
        academicoRolEsperado.setRol(rol);
        AcademicoUsuario academicoUsuario = new AcademicoUsuario();
        academicoUsuario.setIdUsuario(0);
        academicoRolEsperado.setAcademicoUsuario(academicoUsuario);
        try{
            AcademicoRol academicoRolObtenido = 
            academicoRolDao.obtenerIdUsuarioDeAcademicoRolExistente(academicoRolBuscar);
            assertEquals(academicoRolEsperado, academicoRolObtenido);
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
   
    //@Test
    public void testRegistrarAcademicoRol(){
        System.out.println("registrarAcademicoRolExito");
        AcademicoRol academicoRolRegistrar = new AcademicoRol();
        AcademicoRolDAO academicoRolDao = new AcademicoRolDAO();
        boolean validacionEsperada = true;
        Academico academico = new Academico();
        academico.setIdAcademico(1);
        academicoRolRegistrar.setAcademico(academico);
        Rol rol = new Rol();
        int ROL_PROFESOR = Roles.PROFESOR.getIdRol();
        rol.setIdRol(ROL_PROFESOR);
        academicoRolRegistrar.setRol(rol);
        ProgramaEducativo programaEducativo = new ProgramaEducativo();
        programaEducativo.setIdProgramaEducativo(0);
        academicoRolRegistrar.setProgramaEducativo(programaEducativo);
        AcademicoUsuario academicoUsuario = new AcademicoUsuario();
        academicoUsuario.setIdUsuario(4);
        academicoRolRegistrar.setAcademicoUsuario(academicoUsuario);
        try{
            boolean validacionObtenida = academicoRolDao.registrarAcademicoRol(academicoRolRegistrar);
            assertEquals(validacionEsperada, validacionObtenida);
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
}

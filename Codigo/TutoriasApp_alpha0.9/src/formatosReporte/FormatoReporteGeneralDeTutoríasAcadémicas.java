package formatosReporte;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import java.io.FileNotFoundException;
import java.io.IOException;
import DIG.DIGProblematicaTabla;
import DIG.DIGTutoriaAcademicaComentarioGeneralTabla;
import dominio.constantes.Roles;
import dominio.globales.InformacionSesion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FormatoReporteGeneralDeTutoríasAcadémicas {
    
private ObservableList<DIGProblematicaTabla> problematicas;
private ObservableList<DIGTutoriaAcademicaComentarioGeneralTabla> comentarios;
private String programaEducativo;
private String numeroSesion;
private String fecha;
private String periodoEscolar;
private String asistentes;
private String enRiesgo;
private String destinoDelArchivo;
    
    public FormatoReporteGeneralDeTutoríasAcadémicas(){
        this.problematicas = FXCollections.observableArrayList();
        this.comentarios = FXCollections.observableArrayList();
    }
    
    public void setProblematicas(ObservableList<DIGProblematicaTabla> problematicas) {
        this.problematicas.addAll(problematicas);
    }

    public void setComentarios(ObservableList<DIGTutoriaAcademicaComentarioGeneralTabla> comentarios) {
        this.comentarios.addAll(comentarios);
    }

    public void setProgramaEducativo(String programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public void setNumeroSesion(String numeroSesion) {
        this.numeroSesion = numeroSesion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setPeriodoEscolar(String periodoEscolar) {
        this.periodoEscolar = periodoEscolar;
    }

    public void setAsistentes(String asistentes) {
        this.asistentes = asistentes;
    }

    public void setEnRiesgo(String enRiesgo) {
        this.enRiesgo = enRiesgo;
    }

    public void setDestinoDelArchivo(String destinoDelArchivo) {
        this.destinoDelArchivo = destinoDelArchivo;
    }
    
    public void generarPDF() throws FileNotFoundException, IOException{
        PdfWriter escritorPDF = new PdfWriter(destinoDelArchivo);
        PdfDocument documentoPDF = new PdfDocument(escritorPDF);
        Document reporteGeneral = new Document(documentoPDF);
        reporteGeneral = agregarTitulo(reporteGeneral);
        reporteGeneral = agregarTablaMetadatos(reporteGeneral);
        reporteGeneral = agregarTablaTotalAsistenciasYRiesgos(reporteGeneral);
        reporteGeneral = agregarTablaProblematicas(reporteGeneral);
        reporteGeneral = agregarTablaComentarios(reporteGeneral);
        reporteGeneral = agregarPieDeDocumento(reporteGeneral);
        reporteGeneral.close();
    }
    
    private Document agregarTitulo(Document reporteGeneral){
        reporteGeneral.add(
            new Paragraph(
                "Reporte General de Tutorías Académicas\n"
                + "Facultad de Estadística e Informática\n\n"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );
        return reporteGeneral;
    }
    
    private Document agregarTablaComentarios(Document reporteGeneral) throws IOException{
        reporteGeneral.add(
            new Paragraph(
                "\nComentarios generales"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );
        final float TAMANIO_COLUMNA_TUTOR = 200f;
        final float TAMANIO_COLUMNA_COMENTARIO = 320f;
        final float TAMANIO_COLUMNAS[]={
            TAMANIO_COLUMNA_TUTOR,
            TAMANIO_COLUMNA_COMENTARIO
        };
        Table tablaComentarios = new Table(TAMANIO_COLUMNAS);
        Cell cabeceraTutor = new Cell().add(
            new Paragraph(
                "Tutor"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );
        Cell cabeceraComentario = new Cell().add(
            new Paragraph(
                "Comentarios"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );
        tablaComentarios.addHeaderCell(cabeceraTutor);
        tablaComentarios.addHeaderCell(cabeceraComentario);
        
        comentarios.forEach(comentario -> {
            Cell celdaTutor = new Cell().add(new Paragraph(comentario.getNombreTutorAcademico()));
            Cell celdaComentario = new Cell().add(new Paragraph(comentario.getComentarioGeneral()));
            tablaComentarios.addCell(celdaTutor);
            tablaComentarios.addCell(celdaComentario);
        });
        reporteGeneral.add(tablaComentarios);
        return reporteGeneral;
    }
    
    private Document agregarTablaTotalAsistenciasYRiesgos (Document reporteDeTutoriaAcademica) {
        final float TAMANIO_COLUMNA_ASISTENTES = 200f;
        final float TAMANIO_COLUMNA_NUM_ASISTENTES = 70f;
        final float TAMANIO_COLUMNA_RIESGO = 200f;
        final float TAMANIO_COLUMNA_NUM_RIESGO = 70f;
        final float TAMANIOS_COLUMNAS[]={
            TAMANIO_COLUMNA_ASISTENTES,
            TAMANIO_COLUMNA_NUM_ASISTENTES,
            TAMANIO_COLUMNA_RIESGO,
            TAMANIO_COLUMNA_NUM_RIESGO
        };
        
        Cell celdaAsistentes = new Cell();
        celdaAsistentes.add(
            new Paragraph(
                "Num. de estudiantes asistentes: "
            ).setBold().setTextAlignment(TextAlignment.RIGHT)
        );
        Cell numAsistentes = new Cell();
        numAsistentes.add(
            new Paragraph(
                asistentes
            ).setTextAlignment(TextAlignment.CENTER)
        );
        Cell celdaEnRiesgo = new Cell();
        celdaEnRiesgo.add(
            new Paragraph(
                "Num. de estudiantes en riesgo: ").setBold().setTextAlignment(TextAlignment.RIGHT)
        );
        Cell celdaNumEnRiesgo = new Cell();
        celdaNumEnRiesgo.add(
            new Paragraph(
                enRiesgo
            ).setTextAlignment(TextAlignment.CENTER)
        );
        Table tablaTotalAsistenciasYRiesgos = new Table(TAMANIOS_COLUMNAS);
        tablaTotalAsistenciasYRiesgos.addCell(celdaAsistentes);
        tablaTotalAsistenciasYRiesgos.addCell(numAsistentes);
        tablaTotalAsistenciasYRiesgos.addCell(celdaEnRiesgo);
        tablaTotalAsistenciasYRiesgos.addCell(celdaNumEnRiesgo);
        reporteDeTutoriaAcademica.add(tablaTotalAsistenciasYRiesgos);
        return reporteDeTutoriaAcademica;
    }     
    
    private Document agregarPieDeDocumento(Document reporteGeneral) {
        final String NOMBRE_AUTORIDAD = 
            InformacionSesion.getInformacionSesion().getAcademicoRol().getAcademico().getNombreCompleto();
        final int ID_ROL_AUTORIDAD = 
            InformacionSesion.getInformacionSesion().getAcademicoRol().getRol().getIdRol();
        final String ROL_AUTORIDAD =  ID_ROL_AUTORIDAD == 3 ? 
                Roles.COORDINADOR_DE_TUTORIAS_ACADEMICAS.getNombreRol() :
                Roles.JEFE_DE_CARRERA.getNombreRol();
        reporteGeneral.add(
            new Paragraph(
                "\n\n\n\n\n\n________________________________________\n"
                + NOMBRE_AUTORIDAD+"\n"
                + ROL_AUTORIDAD
            ).setBold().setTextAlignment(TextAlignment.CENTER));
        return reporteGeneral;
    }

    private Document agregarTablaProblematicas(Document reporteGeneral){
        reporteGeneral.add(
            new Paragraph(
                "\nProblemáticas Académicas"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );
        final float TAMANIO_COLUMNA_EXPERIENCIA = 50f;
        final float TAMANIO_COLUMNA_PROFESOR = 120f;
        final float TAMANIO_COLUMNA_TITULO = 100f;
        final float TAMANIO_COLUMNA_DESCRIPCION=   220f; 
        final float TAMANIO_COLUMNAS[]={
            TAMANIO_COLUMNA_EXPERIENCIA,
            TAMANIO_COLUMNA_PROFESOR,
            TAMANIO_COLUMNA_TITULO,
            TAMANIO_COLUMNA_DESCRIPCION
        };
        Table tablaProblematicas = new Table(TAMANIO_COLUMNAS);
        Cell cabeceraExperiencia = new Cell().add(
            new Paragraph(
                "EE"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );
        Cell cabeceraProfesor = new Cell().add(
            new Paragraph(
                "Profesor"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );
        Cell cabeceraTitulo = new Cell().add(
            new Paragraph(
                "Título"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );
        Cell cabeceraDescripcion = new Cell().add(
            new Paragraph(
                "Descripción"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );
        tablaProblematicas.addHeaderCell(cabeceraExperiencia);
        tablaProblematicas.addHeaderCell(cabeceraProfesor);
        tablaProblematicas.addHeaderCell(cabeceraTitulo);
        tablaProblematicas.addHeaderCell(cabeceraDescripcion);
        
        problematicas.forEach(problematica -> {
            Cell experiencia = new Cell().add(new Paragraph(problematica.getExperienciaEducativa()));
            Cell profesor = new Cell().add(new Paragraph(problematica.getNombreProfesor()));
            Cell titulo = new Cell().add(new Paragraph(problematica.getTitulo()));
            Cell descripcion = new Cell().add(new Paragraph(problematica.getDescripcion()));
            tablaProblematicas.addCell(experiencia);
            tablaProblematicas.addCell(profesor);
            tablaProblematicas.addCell(titulo);
            tablaProblematicas.addCell(descripcion);
        });
        
        reporteGeneral.add(tablaProblematicas);
        return reporteGeneral;
    }
    
    private Document agregarTablaMetadatos(Document documento) {
        final float TAMANIO_COLUMNA_PROGRAMA_PERIODO1 = 130f;
        final float TAMANIO_COLUMNA_PROGRAMA_PERIODO2 = 250f;
        final float TAMANIO_COLUMNA_NUMSESION_FECHA1 = 105f;
        final float TAMANIO_COLUMNA_NUMSESION_FECHA2 = 100f;
        final float TAMANIOS_COLUMNAS[]={
            TAMANIO_COLUMNA_PROGRAMA_PERIODO1,
            TAMANIO_COLUMNA_PROGRAMA_PERIODO2,
            TAMANIO_COLUMNA_NUMSESION_FECHA1,
            TAMANIO_COLUMNA_NUMSESION_FECHA2
        };
        
        Cell celdaProgramaPeriodoAsistentes1 = new Cell();
        celdaProgramaPeriodoAsistentes1.add(
            new Paragraph(
                """
                Programa educativo:
                PeriodoEscolar:
                """).setBold().setTextAlignment(TextAlignment.RIGHT)
        );
        Cell ProgramaPeriodoAsistentes2 = new Cell();
        ProgramaPeriodoAsistentes2.add(
            new Paragraph(
                this.programaEducativo+"\n"+
                this.periodoEscolar
            )
        );
        Cell celdaNumeroSesionFechaEnRiesgo1 = new Cell();
        celdaNumeroSesionFechaEnRiesgo1.add(
            new Paragraph(
                """
                No. de sesi\u00f3n: 
                Fecha: 
                """).setBold().setTextAlignment(TextAlignment.RIGHT)
        );
        Cell celdaNumeroSesionFechaEnRiesgo2 = new Cell();
        celdaNumeroSesionFechaEnRiesgo2.add(
            new Paragraph(
                this.numeroSesion+"\n"+
                this.fecha)
        );
        Table tablaMetadatos = new Table(TAMANIOS_COLUMNAS);
        tablaMetadatos.addCell(celdaProgramaPeriodoAsistentes1);
        tablaMetadatos.addCell(ProgramaPeriodoAsistentes2);
        tablaMetadatos.addCell(celdaNumeroSesionFechaEnRiesgo1);
        tablaMetadatos.addCell(celdaNumeroSesionFechaEnRiesgo2);
        documento.add(tablaMetadatos);
        return documento;
    }    
}

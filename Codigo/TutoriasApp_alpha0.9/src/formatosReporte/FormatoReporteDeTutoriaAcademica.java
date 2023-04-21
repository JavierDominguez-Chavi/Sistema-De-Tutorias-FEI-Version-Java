package formatosReporte;

import DIG.DIGEstudiantesAsistentesOEnRiesgoTabla;
import DIG.DIGProblematicaTabla;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FormatoReporteDeTutoriaAcademica {
private ObservableList<DIGEstudiantesAsistentesOEnRiesgoTabla> listaDeEstudiantesAsistentesOEnRiesgo;
private ObservableList<DIGProblematicaTabla> listaDeProblematicasAcademicas;
private String nombreTutorAcademico;
private String comentarioGeneral;
private String destinoDelArchivo;
private String programaEducativo;
private String NumeroDeTutoria;
private String fechaDeTutoria;
private String periodoEscolar;

    public FormatoReporteDeTutoriaAcademica() {
        this.listaDeEstudiantesAsistentesOEnRiesgo=FXCollections.observableArrayList();
        this.listaDeProblematicasAcademicas=FXCollections.observableArrayList();
        this.comentarioGeneral="Sin comentario";
        this.destinoDelArchivo="C:\\";
    }

    public void setNombreTutorAcademico(String nombreTutorAcademico) {
        this.nombreTutorAcademico = nombreTutorAcademico;
    }
    
    public void setListaDeEstudiantesAsistentesOEnRiesgo
    (ObservableList<DIGEstudiantesAsistentesOEnRiesgoTabla> listaDeEstudiantesAsistentesOEnRiesgo) {
        this.listaDeEstudiantesAsistentesOEnRiesgo = listaDeEstudiantesAsistentesOEnRiesgo;
    }

    public void setListaDeProblematicasAcademicas
    (ObservableList<DIGProblematicaTabla> listaDeProblematicasAcademicas) {
        this.listaDeProblematicasAcademicas = listaDeProblematicasAcademicas;
    }

    public void setComentarioGeneral(String comentarioGeneral) {
        this.comentarioGeneral = comentarioGeneral;
    }

    public void setDestinoDelArchivo(String destinoDelArchivo) {
        this.destinoDelArchivo = destinoDelArchivo;
    }

    public void setProgramaEducativo(String programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public void setNumeroDeTutoria(String NumeroDeTutoria) {
        this.NumeroDeTutoria = NumeroDeTutoria;
    }

    public void setFechaDeTutoria(String fechaDeTutoria) {
        this.fechaDeTutoria = fechaDeTutoria;
    }

    public void setPeriodoEscolar(String periodoEscolar) {
        this.periodoEscolar = periodoEscolar;
    }
    
    public void generarPDF() throws FileNotFoundException, IOException{
        PdfWriter escritorPDF = new PdfWriter(destinoDelArchivo);
        PdfDocument documentoPDF = new PdfDocument(escritorPDF);
        Document reporteDeTutoriaAcademica = new Document(documentoPDF);
        reporteDeTutoriaAcademica = agregarTitulo(reporteDeTutoriaAcademica);
        reporteDeTutoriaAcademica = agregarTablaMetadatos(reporteDeTutoriaAcademica);
        int numeroAsistencias = calcularAsistencias();
        int numeroAlumnosEnRiesgo = calcularNumeroAlumnosEnRiesgo();
        reporteDeTutoriaAcademica = agregarTablaTotalAsistenciasYRiesgos(
            reporteDeTutoriaAcademica,numeroAsistencias,numeroAlumnosEnRiesgo
        );
        reporteDeTutoriaAcademica = agregarTablaListadoDeEstudiantes(reporteDeTutoriaAcademica);
        reporteDeTutoriaAcademica = agregarTablaProblematicas(reporteDeTutoriaAcademica);
        reporteDeTutoriaAcademica = agregarTablaComentarioGeneral(reporteDeTutoriaAcademica);
        reporteDeTutoriaAcademica.close();
    }

    private int calcularAsistencias(){
        int numeroAsistencias = 0;
        for (DIGEstudiantesAsistentesOEnRiesgoTabla estudiante : listaDeEstudiantesAsistentesOEnRiesgo){
            boolean elEstudianteAsistio = estudiante.getAsistenciaEstudiante().equals("Sí");
            if (elEstudianteAsistio){
                numeroAsistencias++;
            }
        }
        return numeroAsistencias;
    }
    
    private int calcularNumeroAlumnosEnRiesgo(){
        int numeroAlumnosEnRiesgo = 0;
        for (DIGEstudiantesAsistentesOEnRiesgoTabla estudiante : listaDeEstudiantesAsistentesOEnRiesgo){
            boolean elEstudianteAsistio = estudiante.getEstudianteEnRiesgo().equals("Sí");
            if (elEstudianteAsistio){
                numeroAlumnosEnRiesgo++;
            }
        }
        return numeroAlumnosEnRiesgo;
    }
    
    private Document agregarTitulo(Document reporteGeneral){
        reporteGeneral.add(
            new Paragraph(
                "Reporte de Tutoría Académica\n"
                + "Facultad de Estadística e Informática\n\n"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );
        return reporteGeneral;
    }
    
    private Document agregarTablaProblematicas(Document reporteDeTutoriaAcademica){
        reporteDeTutoriaAcademica.add(
            new Paragraph(
                "\nProblemáticas Académicas"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );
        final float TAMANIO_COLUMNA_EXPERIENCIA = 125f;
        final float TAMANIO_COLUMNA_PROFESOR = 150f;
        final float TAMANIO_COLUMNA_DESCRIPCION= 250f; 
        final float TAMANIO_COLUMNAS[]={
            TAMANIO_COLUMNA_EXPERIENCIA,
            TAMANIO_COLUMNA_PROFESOR,
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
        Cell cabeceraDescripcion = new Cell().add(
            new Paragraph(
                "Descripción"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );

        tablaProblematicas.addHeaderCell(cabeceraExperiencia);
        tablaProblematicas.addHeaderCell(cabeceraProfesor);;
        tablaProblematicas.addHeaderCell(cabeceraDescripcion);
        listaDeProblematicasAcademicas.forEach(problematica -> {
            Cell experiencia = new Cell().add(new Paragraph(problematica.getExperienciaEducativa()));
            Cell profesor = new Cell().add(new Paragraph(problematica.getNombreProfesor()));
            Cell descripcion = new Cell().add(new Paragraph(problematica.getDescripcion()));
            tablaProblematicas.addCell(experiencia);
            tablaProblematicas.addCell(profesor);
            tablaProblematicas.addCell(descripcion);
        });
        
        reporteDeTutoriaAcademica.add(tablaProblematicas);
        return reporteDeTutoriaAcademica;
    }
    
    
    
    private Document agregarTablaMetadatos(Document reporteDeTutoriaAcademica){
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
                Tutor académico:
                Programa educativo:
                PeriodoEscolar:
                """).setBold().setTextAlignment(TextAlignment.RIGHT)
        );
        Cell ProgramaPeriodoAsistentes2 = new Cell();
        ProgramaPeriodoAsistentes2.add(
            new Paragraph(
                this.nombreTutorAcademico+"\n"+
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
                this.NumeroDeTutoria+"\n"+
                this.fechaDeTutoria+"\n"
            )
        );
        Table tablaMetadatos = new Table(TAMANIOS_COLUMNAS);
        tablaMetadatos.addCell(celdaProgramaPeriodoAsistentes1);
        tablaMetadatos.addCell(ProgramaPeriodoAsistentes2);
        tablaMetadatos.addCell(celdaNumeroSesionFechaEnRiesgo1);
        tablaMetadatos.addCell(celdaNumeroSesionFechaEnRiesgo2);
        reporteDeTutoriaAcademica.add(tablaMetadatos);
        return reporteDeTutoriaAcademica;
    }

    private Document agregarTablaTotalAsistenciasYRiesgos
    (Document reporteDeTutoriaAcademica, int numeroAsistencias, int numeroAlumnosEnRiesgo){
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
        Cell celdaNumeroAsistentes = new Cell();
        celdaNumeroAsistentes.add(
            new Paragraph(
                Integer.toString(numeroAsistencias)+" / "+this.listaDeEstudiantesAsistentesOEnRiesgo.size()
            ).setTextAlignment(TextAlignment.CENTER)
        );
        Cell celdaEnRiesgo = new Cell();
        celdaEnRiesgo.add(
            new Paragraph(
                "Num. de estudiantes en riesgo: ").setBold().setTextAlignment(TextAlignment.RIGHT)
        );
        Cell celdaNumeroEnRiesgo = new Cell();
        celdaNumeroEnRiesgo.add(
            new Paragraph(
                Integer.toString(numeroAlumnosEnRiesgo)+" / "+this.listaDeEstudiantesAsistentesOEnRiesgo.size()
            ).setTextAlignment(TextAlignment.CENTER)
        );
        Table tablaTotalAsistenciasYRiesgos = new Table(TAMANIOS_COLUMNAS);
        tablaTotalAsistenciasYRiesgos.addCell(celdaAsistentes);
        tablaTotalAsistenciasYRiesgos.addCell(celdaNumeroAsistentes);
        tablaTotalAsistenciasYRiesgos.addCell(celdaEnRiesgo);
        tablaTotalAsistenciasYRiesgos.addCell(celdaNumeroEnRiesgo);
        reporteDeTutoriaAcademica.add(tablaTotalAsistenciasYRiesgos);
        return reporteDeTutoriaAcademica;
    }        
    
    private Document agregarTablaListadoDeEstudiantes(Document reporteDeTutoriaAcademica){
        reporteDeTutoriaAcademica.add(
            new Paragraph(
                "\nListado de estudiantes"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );
        final float TAMANIO_COLUMNA_MATRICULA = 100f;
        final float TAMANIO_COLUMNA_NOMBRE_ESTUDIANTE = 320f;
        final float TAMANIO_COLUMNA_ASISTENCIA = 50f;
        final float TAMANIO_COLUMNA_EN_RIESGO = 50f;
         
        final float TAMANIO_COLUMNAS[]={
            TAMANIO_COLUMNA_MATRICULA,
            TAMANIO_COLUMNA_NOMBRE_ESTUDIANTE,
            TAMANIO_COLUMNA_ASISTENCIA,
            TAMANIO_COLUMNA_EN_RIESGO,
        };
        Table tablaListadoDeEstudiantes = new Table(TAMANIO_COLUMNAS);
        Cell cabeceraMatricula = new Cell().add(
            new Paragraph(
                "Matrícula"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );
        Cell cabeceraNombreDelEstudiante = new Cell().add(
            new Paragraph(
                "Nombre del estudiante"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );
        Cell cabeceraAsisitio = new Cell().add(
            new Paragraph(
                "Asistió"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );
        Cell cabeceraEnRiesgo = new Cell().add(
            new Paragraph(
                "En riesgo"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );
        tablaListadoDeEstudiantes.addHeaderCell(cabeceraMatricula);
        tablaListadoDeEstudiantes.addHeaderCell(cabeceraNombreDelEstudiante);
        tablaListadoDeEstudiantes.addHeaderCell(cabeceraAsisitio);
        tablaListadoDeEstudiantes.addHeaderCell(cabeceraEnRiesgo);
        
        listaDeEstudiantesAsistentesOEnRiesgo.forEach(estudiante -> {
            Cell celdaMatricula = new Cell().add(new Paragraph(estudiante.getEstudiante().getMatricula()));
            Cell celdaNombreDelEstudiante = new Cell().add(new Paragraph(estudiante.getNombreCompleto()));
            Cell celdaAsistio = new Cell().add(new Paragraph(estudiante.getAsistenciaEstudiante()));
            Cell celdaEnRiesgo = new Cell().add(new Paragraph(estudiante.getEstudianteEnRiesgo()));
            tablaListadoDeEstudiantes.addCell(celdaMatricula);
            tablaListadoDeEstudiantes.addCell(celdaNombreDelEstudiante);
            tablaListadoDeEstudiantes.addCell(celdaAsistio);
            tablaListadoDeEstudiantes.addCell(celdaEnRiesgo);
        });
        reporteDeTutoriaAcademica.add(tablaListadoDeEstudiantes);
        return reporteDeTutoriaAcademica;
    }
    
    private Document agregarTablaComentarioGeneral(Document reporteDeTutoriaAcademica){
        reporteDeTutoriaAcademica.add(
            new Paragraph(
                "\nComentario general"
            ).setBold().setTextAlignment(TextAlignment.CENTER)
        );
        final float TAMANIO_COLUMNA_COMENTARIO = 490f;
        final float TAMANIO_COLUMNA[]={
            TAMANIO_COLUMNA_COMENTARIO
        };
        Table tablaComentarioGeneral = new Table(TAMANIO_COLUMNA);
        
        Paragraph paragraphComentarioGeneral = new Paragraph(this.comentarioGeneral);
        tablaComentarioGeneral.addCell(new Cell().add(paragraphComentarioGeneral));
        reporteDeTutoriaAcademica.add(tablaComentarioGeneral);
        return reporteDeTutoriaAcademica;
    }
}

package dominio;

public class TutoriaAcademica {
    private int idTutoriaAcademica;
    private String comentarioGeneral;
    private FechaTutoria fechaTutoria; 
    private Academico tutor;
    private boolean reporteEntregado;
    
    public TutoriaAcademica(){
        this.fechaTutoria = new FechaTutoria();
        this.tutor = new Academico();
    }
    
    public Academico getTutor() {
        return tutor;
    }

    public void setTutor(Academico tutor) {
        this.tutor = tutor;
    }

    public int getIdTutoriaAcademica() {
        return idTutoriaAcademica;
    }

    public String getComentarioGeneral() {
        return comentarioGeneral;
    }

    public FechaTutoria getFechaTutoria() {
        return fechaTutoria;
    }

    public void setIdTutoriaAcademica(int idTutoriaAcademica) {
        this.idTutoriaAcademica = idTutoriaAcademica;
    }

    public void setComentarioGeneral(String comentarioGeneral) {
        this.comentarioGeneral = comentarioGeneral;
    }

    public void setFechaTutoria(FechaTutoria fechaTutoria) {
        this.fechaTutoria = fechaTutoria;
    }

    public boolean isReporteEntregado() {
        return reporteEntregado;
    }

    public void setReporteEntregado(boolean reporteEntregado) {
        this.reporteEntregado = reporteEntregado;
    }
    
    @Override
    public String toString() {
        return "TutoriaAcademica{" + "idTutoriaAcademica=" + idTutoriaAcademica + ", comentarioGeneral=" 
               + comentarioGeneral + ", fechaTutoria=" + fechaTutoria.toString() + ", tutor=" 
               + tutor.toString() + '}';
    }

    @Override
    public boolean equals(Object objeto) {
        boolean esIgual=false;
        if (objeto == this) {
            esIgual=true;
        }
        if (objeto!= null && objeto instanceof TutoriaAcademica) {
            TutoriaAcademica tutoriaAcademica = (TutoriaAcademica) objeto;
            esIgual=this.getIdTutoriaAcademica()==tutoriaAcademica.getIdTutoriaAcademica();
                if(this.getComentarioGeneral()!=null){
                    esIgual= this.getComentarioGeneral().equals(tutoriaAcademica.getComentarioGeneral());
                }
        }
        return esIgual;
    }
    
}

package DIG;

import dominio.Academico;
import dominio.Estudiante;
import java.util.HashSet;

public class DIGAcademicoAsignacionTutorTabla{
        private Academico academico;
        private String nombreCompleto;
        private int numeroTutorados;
        private HashSet<String> nuevosTutoradosAsignados;
        
        public DIGAcademicoAsignacionTutorTabla(int numeroTutorados){
            this.nombreCompleto = "SIN_NOMBRE";
            this.nuevosTutoradosAsignados = new HashSet();
            this.numeroTutorados =numeroTutorados;
        }
        public void setAcademico(Academico academico){
            this.academico = academico;
            setNombreCompleto();
        }
        private void setNombreCompleto(){
            this.nombreCompleto = 
                this.academico.getNombre()+" "+
                this.academico.getApellidoPaterno()+" "+
                this.academico.getApellidoMaterno();
        }

        public void setNumeroTutorados(int numeroTutorados){
            this.numeroTutorados = numeroTutorados;
        }
        public int getNumeroTutorados(){
            return this.numeroTutorados;
        }
        public void restarTutorado(Estudiante tutorado){
            this.nuevosTutoradosAsignados.remove(tutorado.getMatricula());
            --this.numeroTutorados;
        }
        public void sumarTutorado(Estudiante tutorado){
            this.nuevosTutoradosAsignados.add(tutorado.getMatricula());
            ++this.numeroTutorados;
        }
        public Academico getAcademico(){
            return this.academico;
        }
        
        public String getNombreCompleto(){
            return this.nombreCompleto;
        }

        public HashSet<String> getNuevosTutoradosAsignados() {
            return nuevosTutoradosAsignados;
        }
        
        public boolean puedeAceptarMasTutorados(){
            return this.numeroTutorados<30;
        }

        public boolean cambiaronSusAsignaciones(){
            return this.nuevosTutoradosAsignados.size()>0;
        }

        public void limpiarHashSetNuevosTutoradosAsignados(){
            this.nuevosTutoradosAsignados.clear();
        }
        
        public String toString(){
            return this.getNombreCompleto()+" | tutorados: "+this.getNumeroTutorados();
        }
}

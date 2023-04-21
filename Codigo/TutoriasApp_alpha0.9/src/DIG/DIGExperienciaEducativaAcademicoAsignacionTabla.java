package DIG;

import dominio.Asignatura;
import java.util.HashSet;
import javafx.scene.control.CheckBox;

public class DIGExperienciaEducativaAcademicoAsignacionTabla {
     private Asignatura experienciaEducativa;
        private CheckBox checkBoxAsignacion;
        private HashSet<Integer> asignaciones;

        public DIGExperienciaEducativaAcademicoAsignacionTabla() {
            this.asignaciones = new HashSet<>();
            this.checkBoxAsignacion = new CheckBox();
            this.checkBoxAsignacion.setOnAction(e -> modificarAsignacion());
        }

        private void modificarAsignacion(){
            boolean seleccionado = this.checkBoxAsignacion.isSelected();
            if (seleccionado){
                this.asignaciones.add(this.getNRC());
                
            }else{
                this.asignaciones.remove(this.getNRC());
            }
        }

        public void setExperienciaEducativa(Asignatura experienciaEducativa) {
            this.experienciaEducativa = experienciaEducativa;
        }
        
        public String getNombre(){
            return this.experienciaEducativa.getExperienciaEducativa().getNombre();
        }
        
        public int getNRC(){
            return this.experienciaEducativa.getNRC();
        }

        public CheckBox getCheckBoxAsignacion() {
            return checkBoxAsignacion;
        }

        public void setAsignaciones(HashSet<Integer> asignaciones) {
            this.asignaciones = asignaciones;
        }
}

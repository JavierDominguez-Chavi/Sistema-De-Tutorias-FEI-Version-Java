package DIG;

import dominio.Estudiante;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class DIGHorarioTabla {
    private Spinner spinnerHoras;
    private Spinner spinnerMinutos;
    private Estudiante estudiante;
    private String hora;
    private String minutos;
    private int idHorario;
   
    public DIGHorarioTabla() {
        this.estudiante = new Estudiante();
        this.spinnerHoras = new Spinner();
        this.spinnerMinutos = new Spinner();
        SpinnerValueFactory<Integer> valueFactoryHoras =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 24);
        SpinnerValueFactory <Integer> valueFactoryMinutos = 
        new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59);
        valueFactoryHoras.setValue(00);
        valueFactoryMinutos.setValue(00); 
        spinnerHoras.setValueFactory(valueFactoryHoras);
        spinnerMinutos.setValueFactory(valueFactoryMinutos);
    }
    
    public Spinner getSpinnerHoras() {
        return this.spinnerHoras;
    }

    public Spinner getSpinnerMinutos(){
        return this.spinnerMinutos;
    }

    public void setEstudiante(Estudiante estudiante){
        this.estudiante = estudiante;
    }

    public String getMatricula(){
        return this.estudiante.getMatricula();
    }

    public String getNombreCompleto(){
        return this.estudiante.getApellidoPaterno() + " " + this.estudiante.getApellidoMaterno()
               + " " + this.estudiante.getNombre();
    }
    
    public Estudiante getEstudiante(){
        return this.estudiante;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMinutos() {
        return minutos;
    }

    public void setMinutos(String minutos) {
        this.minutos = minutos;
    }
    
    public void setValorInicialSpinners(int valorInicialSpinnerHoras, int valorInicialSpinnerMinutos){
        SpinnerValueFactory<Integer> valueFactoryHoras =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 24);
        SpinnerValueFactory <Integer> valueFactoryMinutos = 
        new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59);
        valueFactoryHoras.setValue(valorInicialSpinnerHoras);
        valueFactoryMinutos.setValue(valorInicialSpinnerMinutos); 
        spinnerHoras.setValueFactory(valueFactoryHoras);
        spinnerMinutos.setValueFactory(valueFactoryMinutos);
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }
    
    
}

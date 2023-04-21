package logicaNegocio.helpers;

import java.time.LocalDate;
import java.util.Date;

public class DateHelper {
    public Date aDate(LocalDate fecha){
        return java.sql.Date.valueOf(fecha.toString());
    }
    public LocalDate aLocalDate(Date fecha){
        return java.sql.Date.valueOf(fecha.toString()).toLocalDate();
    }
}

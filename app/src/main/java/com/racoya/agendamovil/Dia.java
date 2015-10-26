
package com.racoya.agendamovil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author roviol
 */
public class Dia {
    String dia;
    
    String orden;
    
    HashMap materias = new HashMap();

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public HashMap getMaterias() {
        return materias;
    }

    public void setMaterias(HashMap materias) {
        this.materias = materias;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    
}

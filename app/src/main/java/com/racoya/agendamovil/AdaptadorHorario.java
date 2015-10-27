package com.racoya.agendamovil;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by roland on 10/26/15.
 */
public class AdaptadorHorario extends BaseAdapter {
    private final Activity actividad;
    private final AgendaDAO agendaDAO;
    private int dia=1;

    public AdaptadorHorario(Activity actividad, AgendaDAO agendaDAO) {
        super();
        this.actividad = actividad;
        this.agendaDAO = agendaDAO;
    }

    public int getDia() {
        return dia;
    }

    public String getTitulo(){

        Dia diaObj = (Dia) agendaDAO.getDias().get(String.valueOf(dia));
        if (diaObj != null){
            return diaObj.getDia();
        }else{
            return "";
        }
    }

    public void setDia(int diap){
        this.dia=diap;
    }

    public View getView(int position, View convertView,
                        ViewGroup parent) {
        LayoutInflater inflater = actividad.getLayoutInflater();
        View view = inflater.inflate(R.layout.listhorario, null, true);
        TextView textView =(TextView)view.findViewById(R.id.titulo);
        TextView turnoView =(TextView)view.findViewById(R.id.turno);

        try{

            Turno turno = (Turno) agendaDAO.getTurnos().get(position);
            turnoView.setText(turno.getDesde() + "\n" + turno.getHasta());
            Dia diaObj = (Dia) agendaDAO.getDias().get(String.valueOf(dia));
            Materia materia = (Materia) diaObj.getMaterias().get(turno.getId());
            textView.setText( materia.getDescripcion()  );
        }catch (Exception e){
            Log.e(AdaptadorHorario.class.toString(),"Sin datos "+position+","+dia);
        }



        return view;
    }

    public int getCount() {
        return agendaDAO.getTurnos().size();
    }

    public Object getItem(int arg0) {
        return agendaDAO.getTurnos().get(arg0);
    }

    public long getItemId(int position) {
        return position;
    }
}
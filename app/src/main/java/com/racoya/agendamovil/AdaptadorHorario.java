package com.racoya.agendamovil;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by roland on 10/26/15.
 */
public class AdaptadorHorario extends BaseAdapter {
    private Activity actividad;
    private AgendaDAO agendaDAO;
    private int diaposicion=0;
    private ArrayList listaDias;
    private int maxDia;


    public AdaptadorHorario(Activity actividad, AgendaDAO agendaDAO) {
        super();
        this.actividad = actividad;
        this.agendaDAO = agendaDAO;
        this.listaDias = agendaDAO.getListaDias();
        this.maxDia = this.listaDias.size()-1;
    }

    public void nextDia(){
        if(diaposicion<maxDia){
            diaposicion++;
        }
    }

    public void prevDia(){
        if(diaposicion>0){
            diaposicion--;
        }
    }

    public String getTitulo(){

        Dia diaObj = (Dia) agendaDAO.getDias().get(listaDias.get(diaposicion));
        if (diaObj != null){
            return diaObj.getDia();
        }else{
            return "";
        }
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
            Dia diaObj = (Dia) agendaDAO.getDias().get(listaDias.get(diaposicion));
            Materia materia = (Materia) diaObj.getMaterias().get(turno.getId());
            textView.setText( materia.getDescripcion()  );
        }catch (Exception e){
            Log.e(AdaptadorHorario.class.toString(),"Sin datos "+position+","+diaposicion);
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
package com.racoya.agendamovil;

//import android.os.NetworkOnMainThreadException;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by roland on 10/24/15.
 */
public class AgendaDAO {

    private String agendaStr;
    private ArrayList turnos = new ArrayList();
    private HashMap dias = new HashMap();
    private JSONObject horarioObject;
    private String titulo;
    private String urlHorario;
/*
    public static void main(String[] s) {

        AgendaDAO agendaDao = new AgendaDAO("http://agenda.racoya.com/horario/1/");
        for (Object turno : agendaDao.turnos) {

            Turno turnoObj = (Turno) turno;
            System.out.print(turnoObj.desde);
            System.out.print(" - ");
            System.out.print(turnoObj.hasta);
            for (int i = 0; i <= 6; i++) {
                String orden = String.valueOf(i);
                Dia diaAct = (Dia) agendaDao.dias.get(orden);
                if (diaAct != null) {
                    Materia m = (Materia) diaAct.materias.get(turnoObj.id);
                    if (m != null) {
                        System.out.print("\t"+orden + " " + m.descripcion);
                    } else {
                        System.out.print("\t");
                    }
                }
                System.out.print(" - ");
            }
            System.out.println("");

        }
    }
*/
    public AgendaDAO(String urlHorario) {
        this.urlHorario = urlHorario;
        Log.i(AgendaDAO.class.toString(), "Descargando horario");
        getHorario();
        Log.i(AgendaDAO.class.toString(), "Analizando horario");
        analizaHorario();
        Log.i(AgendaDAO.class.toString(), "Listo");
    }

    public void getHorario() {
        agendaStr = getJSON(urlHorario);
    }

    public void parseTurnos() throws JSONException {
        turnos = new ArrayList();
        JSONArray turnosArreglo = horarioObject.getJSONArray("turnos");
        for (int i = 0; i < turnosArreglo.length(); i++) {
            JSONObject turnoObj = turnosArreglo.getJSONObject(i);
            Turno turno = new Turno();
            turno.id = String.valueOf(turnoObj.getInt("id"));
            turno.desde = turnoObj.getString("desde");
            turno.hasta = turnoObj.getString("hasta");
            turnos.add(turno);
        }
    }

    public void parseDias() throws JSONException {
        dias = new HashMap();
        JSONArray diasArreglo = horarioObject.getJSONArray("dias");
        for (int i = 0; i < diasArreglo.length(); i++) {
            JSONObject diaObj = diasArreglo.getJSONObject(i);
            Dia dia = new Dia();
            dia.dia = diaObj.getString("nombre");
            dia.orden = diaObj.getString("dia");
            HashMap materias = new HashMap();
            JSONArray materiasArreglo = diaObj.getJSONArray("materias");
            for (int j = 0; j < materiasArreglo.length(); j++) {
                JSONObject matturno = materiasArreglo.getJSONObject(j);
                Materia materia = new Materia();
                materia.descripcion = matturno.getString("nombre");
                materia.turnoid = matturno.getString("turno");
                materias.put(String.valueOf(materia.turnoid), materia);
            }
            dia.setMaterias(materias);
            dias.put(dia.orden, dia);
        }
    }

    public void analizaHorario() {
        try {
            horarioObject = new JSONObject(agendaStr);
            titulo = horarioObject.getString("titulo");
            parseTurnos();
            parseDias();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Success");
        }
    }

    public String getJSON(String address) {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(address);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e(AgendaDAO.class.toString(), "Failedet JSON object");
            }
        } catch (ClientProtocolException e) {
            Log.e(AgendaDAO.class.toString(), "Fallo http");
        } catch (IOException e) {
            Log.e(AgendaDAO.class.toString(), "Fallo IO");
        } catch (Exception e) {
            Log.e(AgendaDAO.class.toString(), "Fallo Network");
        }
        return builder.toString();
    }

    public ArrayList getTurnos() {
        return turnos;
    }

    public HashMap getDias() {
        return dias;
    }

    public String getTitulo() {
        return titulo;
    }

}

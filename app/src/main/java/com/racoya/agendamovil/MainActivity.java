package com.racoya.agendamovil;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends ActionBarActivity {
    // Remove the below line after defining your own ad unit ID.
    private static final String TOAST_TEXT = "Test ads are being shown. "
            + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";
    private TextView txttitulo;
    private ListView listView;
    private AgendaDAO agendaDao;
    private ImageButton btsiguiente;
    private ImageButton btanterior;
    private AdaptadorHorario adaptaHorario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btanterior=(ImageButton) findViewById(R.id.btback);
        btsiguiente=(ImageButton) findViewById(R.id.btforward);
        // Load an ad into the AdMob banner view.
        txttitulo = (TextView) findViewById(R.id.txttitulo);
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();


        this.listView = (ListView) findViewById(R.id.listView);
        try {
            buscarHorario();
            adaptaHorario = new AdaptadorHorario(this, agendaDao);
            this.listView.setAdapter(adaptaHorario);
            txttitulo.setText(adaptaHorario.getTitulo());
            adaptaHorario.notifyDataSetChanged();
        }catch (Exception e){
            Log.e(MainActivity.class.toString(),e.toString());
        }
        btanterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adaptaHorario.prevDia();
                txttitulo.setText(adaptaHorario.getTitulo());
                adaptaHorario.notifyDataSetChanged();
            }
        });
        btsiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adaptaHorario.nextDia();
                txttitulo.setText(adaptaHorario.getTitulo());
                adaptaHorario.notifyDataSetChanged();
            }
        });
    }

    private void buscarHorario(){

        agendaDao = new AgendaDAO("http://agenda.racoya.com/horario/1/");
        setTitle(agendaDao.getTitulo());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            buscarHorario();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

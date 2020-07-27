package proyecciones.paquetes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuInicial extends AppCompatActivity implements View.OnClickListener {
    Button proyecciones,gastos,reporteActual,reportes,volver;
    TextView nombre,ced;
    String CedulaPermanente="",nombrePermanente="",apellidoPermanente="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);
        Bundle bundle = getIntent().getExtras();
        CedulaPermanente=bundle.getString("cedula");
        nombrePermanente=bundle.getString("nombre");
        apellidoPermanente=bundle.getString("apellido");
        proyecciones = (Button) findViewById(R.id.btnProyecciones);
        gastos=(Button)findViewById(R.id.bntGastosD);
        reporteActual=(Button)findViewById(R.id.btnReporteActual);
        reportes=(Button)findViewById(R.id.btnReportes);
        volver=(Button)findViewById(R.id.btnVolver);
        nombre= (TextView) findViewById(R.id.textView5);
        nombre.setText("BienVenido: "+nombrePermanente+" "+apellidoPermanente+"");

        proyecciones.setOnClickListener(this);
        gastos.setOnClickListener(this);
        reporteActual.setOnClickListener(this);
        reportes.setOnClickListener(this);
        volver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();


        switch (id){
            case R.id.btnProyecciones:
                Intent i = new Intent(this, proyecciones.class);
                i.putExtra("cedula", CedulaPermanente);
                startActivity(i);
                break;
            case R.id.bntGastosD:
                Intent i1 = new Intent(this, Gastos.class);
                i1.putExtra("cedula", CedulaPermanente);
                startActivity(i1);
                break;
            case R.id.btnReporteActual:
                break;
            case R.id.btnReportes:
                break;
            case R.id.btnVolver:
                Intent i2 = new Intent(this, MainActivity.class);
                startActivity(i2);
                break;

        }

    }
}

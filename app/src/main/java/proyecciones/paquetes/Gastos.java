package proyecciones.paquetes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Gastos extends AppCompatActivity implements View.OnClickListener {
    Spinner opciones1, opciones2;
    String CedulaPermanente="",nombrePermanente,apellidoPermanente;
    Button guardar;
    String id_tabla,id_gastos;
    String consultaFecha="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);
        opciones1=(Spinner)findViewById(R.id.spn1);
        opciones2=(Spinner)findViewById(R.id.spn2);
        guardar = (Button) findViewById(R.id.bntGuardarGasto);
        guardar.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        CedulaPermanente=bundle.getString("cedula");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.opcionesGastos,android.R.layout.simple_spinner_item);
        opciones1.setAdapter(adapter);
        opciones1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String op = parent.getItemAtPosition(position).toString();
                switch (op){
                    case "Alimentacion":
                        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(parent.getContext(),R.array.alimentacion,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter1);
                        break;
                    case "Vivienda":
                        id_tabla="ID_VIVIENDA";
                        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(parent.getContext(),R.array.vivienda,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter2);
                        break;
                    case "Educacion":
                        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(parent.getContext(),R.array.educacion,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter3);
                        break;
                    case "Medicos":
                        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(parent.getContext(),R.array.medicos,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter4);
                        break;
                    case "Deudas":
                        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(parent.getContext(),R.array.deudas,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter5);
                        break;
                    case "Personal":
                        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(parent.getContext(),R.array.personal,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter6);
                        break;
                    case "Trasnporte":
                        ArrayAdapter<CharSequence> adapter7 = ArrayAdapter.createFromResource(parent.getContext(),R.array.transporte,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter7);
                        break;
                    case "Gastos Varios":
                        ArrayAdapter<CharSequence> adapter8 = ArrayAdapter.createFromResource(parent.getContext(),R.array.gastosVarios,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter8);
                        break;
                    case "Entretenimiento":
                        ArrayAdapter<CharSequence> adapter9 = ArrayAdapter.createFromResource(parent.getContext(),R.array.entretenimiento,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter9);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    //De Joha Rosero para todos:  05:20 PM
    //select ID_GASTOS , FECHA
    //from ingresos
    //WHERE CED= 1727349076
    //GROUP by ID_INGRESO
    //De Joha Rosero para todos:  05:28 PM
    //select luz
    //from vivienda
   // WHERE fecha = '2020-07-17' AND ID_GASTOS=1
    //De Joha Rosero para todos:  05:52 PM
    //select luz
    //from vivienda
   // WHERE fecha = '2020-07-17' AND ID_GASTOS=1
    // UPDATE `vivienda` SET `AGUA` = '50' WHERE `vivienda`.`ID_VIVIENDA` = 1;
   // INSERT INTO `vivienda` (`ID_VIVIENDA`, `ID_GASTOS`, `FECHA`, `LUZ`, `AGUA`, `TELEFONO`, `PLAN_CELULAR`, `INTERNET`, `TV_CABLE`, `MANTENIMIENTOS_HOGAR`, `TOT_VIVIENDA`) VALUES (NULL, '1', '2020-07-17', '30', NULL, NULL, NULL, NULL, NULL, NULL, NULL);


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIngresoGuardar:
                Conexion conn = new Conexion();
                Connection conexionMySQL = conn.conectar();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(c.getTime());
                try {
                    Statement st  = conexionMySQL.createStatement();
                    ResultSet rs = st.executeQuery("select ID_GASTOS , FECHA from ingresos WHERE CED="+CedulaPermanente+" GROUP by ID_INGRESO");
                    while(rs.next()){
                        consultaFecha=rs.getString(2);
                        String[] parts1 = consultaFecha.split("-");
                        String[] parts2 = formattedDate.split("-");
                        if(parts1[0].equals(parts2[0])){
                            if(parts1[1].equals(parts2[1])){
                                id_gastos=rs.getString(1);
                            }
                        }else{
                        }
                    }
                    conexionMySQL.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btnVolverGastos:

                break;
        }
    }
}

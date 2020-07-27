package proyecciones.paquetes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class proyecciones extends AppCompatActivity implements View.OnClickListener {
    TextView sueldo,otrosIngresos,vivienda,alimentacion,medicina,educacion,personal;
    TextView transporte,gVarios,deudas,entretenimiento, disponible,ahorro;
    Button guardar,volver;
    String CedulaPermanente="",nombrePermanente,apellidoPermanente;
    int total=0;
    int totalSalida=0;
    int totalEntrada=0;
    String sueldoAntes="";
    String sueldoDespues="";
    String cedula="1727349076";
    String antessueldo="",antesotrosIngresos="",antesvivienda="",antesalimentacion="",antesmedicina="",anteseducacion="",antespersonal="",antestransporte="",antesgVarios="",antesdeudas="",antesentretenimiento="",antesahorro="";
    String despuessueldo="",despuesotrosIngresos="",despuesvivienda="",despuesalimentacion="",despuesmedicina="",despueseducacion="",despuespersonal="",despuestransporte="",despuesgVarios="",despuesdeudas="",despuesentretenimiento="",despuesahorro="";

    Calendar c = Calendar.getInstance();
    String consultaFecha="",consultaAhorro="",consultaSueldo="",consultaOtrosIngresos="",consultaVivienda="",consultaAlimentacion="",consultaEducacion="",consultaTransporte="",consultaMedicos="",consultaEntretenimiento="",consultapersonal="",consultaDeudas="",consultaGastosVarios="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyecciones);
        Bundle bundle = getIntent().getExtras();
        CedulaPermanente=bundle.getString("cedula");
        ahorro=(TextView)findViewById(R.id.txtAhorro);
        disponible =(TextView)findViewById(R.id.txtDisponible);
        sueldo=(TextView)findViewById(R.id.txtSueldo);
        otrosIngresos=(TextView)findViewById(R.id.txtOtros);
        vivienda=(TextView)findViewById(R.id.txtIngresoVivienda);
        alimentacion=(TextView)findViewById(R.id.txtIngresoAlimentacion);
        medicina=(TextView)findViewById(R.id.txtIngresoMedicina);
        educacion=(TextView)findViewById(R.id.txtIngresoEducacion);
        personal=(TextView)findViewById(R.id.txtIngresoPersonal);
        transporte=(TextView)findViewById(R.id.txtIngresoTransporte);
        gVarios=(TextView)findViewById(R.id.txtIngresoGastosVarios);
        deudas=(TextView)findViewById(R.id.txtIngresoDeudas);
        entretenimiento=(TextView)findViewById(R.id.txtIngresoEntretenimiento);
        guardar=(Button) findViewById(R.id.btnIngresoGuardar);
        volver=(Button) findViewById(R.id.btnIngresoVolver);
        guardar.setOnClickListener(this);
        volver.setOnClickListener(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());

        Conexion conn = new Conexion();
        Connection conexionMySQL = conn.conectar();
        try {
            Statement st  = conexionMySQL.createStatement();

            ResultSet rs = st.executeQuery("SELECT ingresos.FECHA,ingresos.AHORRO_MES, ingresos.SUELDOS,ingresos.OTROS_INGRE, gastos_proy.VIVIENDA,gastos_proy.ALIMENTACION ,gastos_proy.EDUCACION,gastos_proy.TRANSPORTE , gastos_proy.MEDICOS,gastos_proy.ENTRETENIMIENTO, gastos_proy.PERSONAL,gastos_proy.DEUDAS ,gastos_proy.GASTOS_VARIOS FROM ingresos INNER JOIN gastos_proy on gastos_proy.ID_INGRESO=ingresos.ID_INGRESO WHERE ingresos.CED="+CedulaPermanente+" GROUP by ingresos.ID_INGRESO");
            while(rs.next()){
                consultaFecha=rs.getString(1);
                consultaAhorro=rs.getString(2);
                consultaSueldo=rs.getString(3);
                consultaOtrosIngresos=rs.getString(4);
                consultaVivienda=rs.getString(5);
                consultaAlimentacion=rs.getString(6);
                consultaEducacion=rs.getString(7);
                consultaTransporte=rs.getString(8);
                consultaMedicos=rs.getString(9);
                consultaEntretenimiento=rs.getString(10);
                consultapersonal=rs.getString(11);
                consultaDeudas=rs.getString(12);
                consultaGastosVarios=rs.getString(13);
                String[] parts1 = consultaFecha.split("-");
                String[] parts2 = formattedDate.split("-");
                if(parts1[0].equals(parts2[0])){
                    if(parts1[1].equals(parts2[1])){
                        sueldo.setEnabled(false);
                        otrosIngresos.setEnabled(false);
                        vivienda.setEnabled(false);
                        alimentacion.setEnabled(false);
                        medicina.setEnabled(false);
                        educacion.setEnabled(false);
                        personal.setEnabled(false);
                        transporte.setEnabled(false);
                        gVarios.setEnabled(false);
                        deudas.setEnabled(false);
                        entretenimiento.setEnabled(false);
                        guardar.setEnabled(false);
                        sueldo.setText(consultaSueldo+"");
                        otrosIngresos.setText(consultaOtrosIngresos+"");
                        vivienda.setText(consultaVivienda+"");
                        alimentacion.setText(consultaAlimentacion+"");
                        medicina.setText(consultaMedicos+"");
                        educacion.setText(consultaEducacion+"");
                        personal.setText(consultapersonal+"");
                        transporte.setText(consultaTransporte+"");
                        gVarios.setText(consultaGastosVarios+"");
                        deudas.setText(consultaDeudas+"");
                        entretenimiento.setText(consultaEntretenimiento+"");
                    }
                }else{
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conexionMySQL.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sueldo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                sueldoDespues = sequence.subSequence(0, start + count).toString();
                if(sueldoDespues.equals("")){
                    if (otrosIngresos.getText().toString().equals("")) {
                        disponible.setText(0+"");
                    }else{
                        totalEntrada = Integer.parseInt(otrosIngresos.getText().toString());
                        total=totalEntrada-totalSalida;
                        disponible.setText(total+"");
                    }
                } else{
                    if(otrosIngresos.getText().toString().equals("")){
                        totalEntrada = Integer.parseInt(sueldoDespues);
                        total=totalEntrada-totalSalida;
                        disponible.setText(total + "");
                    }else {
                        totalEntrada = Integer.parseInt(sueldoDespues) + Integer.parseInt(otrosIngresos.getText().toString());
                        total=totalEntrada-totalSalida;
                        disponible.setText(total + "");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        otrosIngresos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {

                sueldoDespues = sequence.subSequence(0, start + count).toString();
                if(sueldoDespues.equals("")){
                    if (sueldo.getText().toString().equals("")) {
                        disponible.setText(0+"");
                    }else{
                        totalEntrada = Integer.parseInt(sueldo.getText().toString());
                        total=totalEntrada-totalSalida;
                        disponible.setText(total+"");
                    }
                } else{
                    if(sueldo.getText().toString().equals("")){
                        totalEntrada = Integer.parseInt(sueldoDespues);
                        total=totalEntrada-totalSalida;
                        disponible.setText(total + "");
                    }else {
                        totalEntrada = Integer.parseInt(sueldoDespues) + Integer.parseInt(sueldo.getText().toString());
                        total=totalEntrada-totalSalida;
                        disponible.setText(total + "");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        vivienda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despuesvivienda = antesvivienda;
            }
            @Override
            public void afterTextChanged(Editable s) {
                int ant=0;
                int desp=0;
                antesvivienda=s.toString();
                if(antesvivienda.equals("")){
                    ant=0;
                    if(despuesvivienda.equals("")){
                        desp=0;
                    }else{
                        desp=Integer.parseInt(despuesvivienda);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Integer.parseInt(antesvivienda);
                    if(despuesvivienda.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Integer.parseInt(despuesvivienda);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }

                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
        alimentacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despuesalimentacion = antesalimentacion;
            }
            @Override
            public void afterTextChanged(Editable s) {
                int ant=0;
                int desp=0;
                antesalimentacion=s.toString();
                if(antesalimentacion.equals("")){
                    ant=0;
                    if(despuesalimentacion.equals("")){
                        desp=0;
                    }else{
                        desp=Integer.parseInt(despuesalimentacion);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Integer.parseInt(antesalimentacion);
                    if(despuesalimentacion.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Integer.parseInt(despuesalimentacion);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }

                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
        medicina.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despuesmedicina = antesmedicina;
            }
            @Override
            public void afterTextChanged(Editable s) {
                int ant=0;
                int desp=0;
                antesmedicina=s.toString();
                if(antesmedicina.equals("")){
                    ant=0;
                    if(despuesmedicina.equals("")){
                        desp=0;
                    }else{
                        desp=Integer.parseInt(despuesmedicina);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Integer.parseInt(antesmedicina);
                    if(despuesmedicina.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Integer.parseInt(despuesmedicina);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }

                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
        educacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despueseducacion = anteseducacion;
            }
            @Override
            public void afterTextChanged(Editable s) {
                int ant=0;
                int desp=0;
                anteseducacion=s.toString();
                if(anteseducacion.equals("")){
                    ant=0;
                    if(despueseducacion.equals("")){
                        desp=0;
                    }else{
                        desp=Integer.parseInt(despueseducacion);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Integer.parseInt(anteseducacion);
                    if(despueseducacion.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Integer.parseInt(despueseducacion);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }
                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
        personal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despuespersonal = antespersonal;
            }
            @Override
            public void afterTextChanged(Editable s) {
                int ant=0;
                int desp=0;
                antespersonal=s.toString();
                if(antespersonal.equals("")){
                    ant=0;
                    if(despuespersonal.equals("")){
                        desp=0;
                    }else{
                        desp=Integer.parseInt(despuespersonal);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Integer.parseInt(antespersonal);
                    if(despuespersonal.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Integer.parseInt(despuespersonal);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }
                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
        transporte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despuestransporte = antestransporte;
            }
            @Override
            public void afterTextChanged(Editable s) {
                int ant=0;
                int desp=0;
                antestransporte=s.toString();
                if(antestransporte.equals("")){
                    ant=0;
                    if(despuestransporte.equals("")){
                        desp=0;
                    }else{
                        desp=Integer.parseInt(despuestransporte);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Integer.parseInt(antestransporte);
                    if(despuestransporte.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Integer.parseInt(despuestransporte);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }
                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
        gVarios.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despuesgVarios = antesgVarios;
            }
            @Override
            public void afterTextChanged(Editable s) {
                int ant=0;
                int desp=0;
                antesgVarios=s.toString();
                if(antesgVarios.equals("")){
                    ant=0;
                    if(despuesgVarios.equals("")){
                        desp=0;
                    }else{
                        desp=Integer.parseInt(despuesgVarios);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Integer.parseInt(antesgVarios);
                    if(despuesgVarios.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Integer.parseInt(despuesgVarios);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }
                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
        deudas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despuesdeudas = antesdeudas;
            }
            @Override
            public void afterTextChanged(Editable s) {
                int ant=0;
                int desp=0;
                antesdeudas=s.toString();
                if(antesdeudas.equals("")){
                    ant=0;
                    if(despuesdeudas.equals("")){
                        desp=0;
                    }else{
                        desp=Integer.parseInt(despuesdeudas);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Integer.parseInt(antesdeudas);
                    if(despuesdeudas.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Integer.parseInt(despuesdeudas);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }
                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
        entretenimiento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despuesentretenimiento = antesentretenimiento;
            }
            @Override
            public void afterTextChanged(Editable s) {
                int ant=0;
                int desp=0;
                antesentretenimiento=s.toString();
                if(antesentretenimiento.equals("")){
                    ant=0;
                    if(despuesentretenimiento.equals("")){
                        desp=0;
                    }else{
                        desp=Integer.parseInt(despuesentretenimiento);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Integer.parseInt(antesentretenimiento);
                    if(despuesentretenimiento.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Integer.parseInt(despuesentretenimiento);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }

                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnIngresoGuardar:
                String idIngreso="";
                int ahorroMes=0;
                SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate1 = df1.format(c.getTime());
                Conexion conn = new Conexion();
                Connection conexionMySQL = conn.conectar();
                if(totalEntrada<totalSalida){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(proyecciones.this);
                    alerta.setMessage("El valor proyectado excede los ingresos.");
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("ERROR");
                    titulo.show();
                }else {
                    try {
                        ahorroMes=totalEntrada-totalSalida;
                        String query = "INSERT INTO ingresos (ID_INGRESO,AHORRO_MES,SUELDOS,OTROS_INGRE,FECHA,CED,ID_GASTOS) VALUES ('','"+ahorroMes+"','"+sueldo.getText().toString()+"','"+otrosIngresos.getText().toString()+"','"+formattedDate1+"','"+CedulaPermanente+"',NULL)";
                        Statement st1 = conexionMySQL.createStatement();
                        st1.executeUpdate(query);
                        conexionMySQL.close();

                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedDate = df.format(c.getTime());
                        conn = new Conexion();
                        conexionMySQL = conn.conectar();
                        try {
                            Statement st  = conexionMySQL.createStatement();
                            ResultSet rs = st.executeQuery("SELECT ID_INGRESO,FECHA FROM ingresos WHERE CED = "+CedulaPermanente);
                            while(rs.next()){
                                consultaFecha=rs.getString(2);
                                ahorro.setText(consultaFecha+"");
                                String[] parts1 = consultaFecha.split("-");
                                String[] parts2 = formattedDate.split("-");
                                if(parts1[0].equals(parts2[0])){
                                    if(parts1[1].equals(parts2[1])){
                                        idIngreso=rs.getString(1);
                                    }
                                }else{
                                }
                            }
                            conexionMySQL.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        conn = new Conexion();
                        conexionMySQL = conn.conectar();
                        String query1 = "INSERT INTO gastos_proy (ID_GASTOS,ID_INGRESO,FECHA,ID_VIVIENDA,ID_ALIMENTACION,ID_EDUCACION,ID_TRANS,ID_PERSONAL,ID_MEDICOS,ID_ENTRETENIMIETO,ID_DEUDAS,ID_GASTOSV,VIVIENDA,ALIMENTACION,EDUCACION,TRANSPORTE,MEDICOS,ENTRETENIMIENTO,PERSONAL,DEUDAS,GASTOS_VARIOS,AHORRO,TOTAL_GASTOSPRO) VALUES (NULL, '"+idIngreso+"', '"+formattedDate1+"', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL, '"+vivienda.getText().toString()+"', '"+alimentacion.getText().toString()+"', '"+educacion.getText().toString()+"', '"+transporte.getText().toString()+"', '"+medicina.getText().toString()+"', '"+entretenimiento.getText().toString()+"', '"+personal.getText().toString()+"', '"+deudas.getText().toString()+"', '"+gVarios.getText().toString()+"', '"+ahorro.getText().toString()+"', '"+totalSalida+"')";
                        Statement st2 = conexionMySQL.createStatement();
                        st2.executeUpdate(query1);
                        conexionMySQL.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        conn = new Conexion();
                        conexionMySQL = conn.conectar();
                        String query1 = "UPDATE ingresos SET ingresos.ID_GASTOS=(SELECT gastos_proy.ID_GASTOS FROM gastos_proy WHERE gastos_proy.ID_INGRESO="+idIngreso+") WHERE ingresos.ID_INGRESO="+idIngreso;
                        Statement st2 = conexionMySQL.createStatement();
                        st2.executeUpdate(query1);
                        conexionMySQL.close();
                    }catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btnIngresoVolver:

                break;
        }
    }
}

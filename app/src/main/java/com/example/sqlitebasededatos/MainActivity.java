package com.example.sqlitebasededatos;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText etDocumento;
    private EditText etUsuario;
    private EditText etNombres;
    private EditText etApellidos;
    private EditText etContra;

    private ListView listaUsuarios;
    int documento;
    String usuario;
    String nombres;
    String apellidos;
    String contra;

    private GestionBD gestionBD;
    SQLiteDatabase baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializador();
    }

    private void inicializador(){
        etDocumento = findViewById(R.id.ET_documento);
        etUsuario = findViewById(R.id.ET_Usuario);
        etNombres = findViewById(R.id.ET_Nombres);
        etApellidos = findViewById(R.id.ET_apellidos);
        etContra = findViewById(R.id.ET_CONTRA);
        listaUsuarios = findViewById(R.id.LV_lista);
        this.listarUsuario();
    }

    public void  setDatos (){
        // hacer validaciones con Regex
        if(validarNombre(etNombres.getText().toString())){
            this.nombres =etNombres.getText().toString();
            this.apellidos =etApellidos.getText().toString();
            if(etDocumento.getText().toString().equals("")){
               this.documento =0;
            }  else {
            this.documento =Integer.parseInt(etDocumento.getText().toString());}
            this.contra = etContra.getText().toString();
            this.usuario = etUsuario.getText().toString();
        }else {

         Toast.makeText(this, "El nombre ingresado Contiene caracteres especiales o numericos", Toast.LENGTH_LONG).show();
         etNombres.setText("");

        }
    }
    public void accionRegistrar(View v){
        setDatos();
        UsuarioDao usuarioDao = new UsuarioDao(this, v);
        Usuario usuario1 = new Usuario();

        usuario1.setNombres(this.nombres);
        usuario1.setApellidos(this.apellidos);
        usuario1.setUsuario(this.usuario);
        usuario1.setContra(this.contra);
        usuario1.setDocumento(this.documento);
        usuarioDao.insertUser(usuario1);
        this.listarUsuario();
    }

    private void listarUsuario(){
        UsuarioDao usuarioDao = new UsuarioDao(this, findViewById(R.id.LV_lista));
        ArrayList<Usuario> userList = usuarioDao.getUserList();
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,userList);
        listaUsuarios.setAdapter(adapter);

    }

    public boolean validarNombre(String Nombre){
        String regex= "^[a-zA-Z]+$";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher= pattern.matcher(Nombre);

        return matcher.matches();
    }

    public void actualizar (View v) {
        UsuarioDao usuarioDao = new UsuarioDao(this, v);
        setDatos();
        Usuario usuario1 = new Usuario();
        usuario1.setNombres(this.nombres);
        usuario1.setApellidos(this.apellidos);
        usuario1.setUsuario(this.usuario);
        usuario1.setContra(this.contra);
        usuario1.setDocumento(this.documento);
        usuarioDao.actualizarUsuario (usuario1);
        this.listarUsuario();

    }

    public void Buscar (View v){
        if (!etDocumento.getText().toString().equals("") || !etDocumento.getText().toString().equals("0")){
            this.documento =Integer.parseInt(etDocumento.getText().toString());
            UsuarioDao usuarioDao = new UsuarioDao(this, findViewById(R.id.LV_lista));
            ArrayList<Usuario> userList = usuarioDao.buscarUsuario(this.documento);
            ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,userList);
            listaUsuarios.setAdapter(adapter);
        }else {
            Toast.makeText(this, "Ingresa un número de documento válido", Toast.LENGTH_SHORT).show();
        }



    }
    public void listarBoton (View v){
        listarUsuario();
    }

    public void eliminar(View v){

         UsuarioDao usuarioDao = new UsuarioDao(this, v);
         if(etDocumento.getText().toString().equals("")){
               this.documento = 0;
            }  else {
            this.documento =Integer.parseInt(etDocumento.getText().toString());}
         if(this.documento > 0){
             usuarioDao.eliminarUsuario(this.documento);
              this.listarUsuario();
         }else {
             Toast.makeText(this, "Ingrese un numero de documento correcto", Toast.LENGTH_LONG).show();
         }
    }

}
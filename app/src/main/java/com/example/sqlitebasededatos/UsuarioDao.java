package com.example.sqlitebasededatos;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UsuarioDao {
    private GestionBD gestionBD;
    Context context;
   View view;
   Usuario usuario;

    public UsuarioDao(Context context, View view) {
        this.context = context;
        this.view = view;

        gestionBD= new GestionBD(this.context);

    }

    //Insertar datos en la tabla usuarios

    public void insertUser(Usuario usuario) {
        try {
            if (usuario.getDocumento() == 0) {
            Snackbar.make(this.view, "El número de documento no puede ser 0", Snackbar.LENGTH_LONG).show();
            return; // Salir del método sin realizar la inserción
        }

            SQLiteDatabase db = gestionBD.getWritableDatabase();

            if (db != null) {
                ContentValues values = new ContentValues();
                values.put("USU_DOCUMENTO", usuario.getDocumento());
                values.put("USU_USUARIO", usuario.getUsuario());
                values.put("USU_NOMBRES", usuario.getNombres());
                values.put("USUS_APELLIDOS", usuario.getApellidos());
                values.put("USU_CONTRA", usuario.getContra());

                long response = db.insert("usuarios", null, values);
                Snackbar.make(this.view, "Se ha registrado el usuario: " +response,Snackbar.LENGTH_LONG).show();
                db.close();
            } else {
                Snackbar.make(this.view, "No se ha registrado el usuario " , Snackbar.LENGTH_LONG).show();

            }

        }catch(SQLException sqlException){
            Log.i("DB", ""+sqlException);
        }
    }

    public ArrayList<Usuario> getUserList(){
        SQLiteDatabase db = gestionBD.getReadableDatabase();
        String query ="SELECT * FROM usuarios ";
        ArrayList<Usuario> userList= new ArrayList<>();
        Cursor cursor= db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
                usuario = new Usuario();
                usuario.setDocumento(cursor.getInt(0));
                usuario.setUsuario(cursor.getString(1));
                usuario.setNombres(cursor.getString(2));
                usuario.setApellidos(cursor.getString(3));
                usuario.setContra(cursor.getString(4));

                userList.add(usuario);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    public ArrayList<Usuario> buscarUsuario(int documento){

         SQLiteDatabase db = gestionBD.getReadableDatabase();
        String query ="SELECT * FROM usuarios WHERE USU_DOCUMENTO = ? ";
        ArrayList<Usuario> userList= new ArrayList<>();
        Cursor cursor= db.rawQuery(query,new String[]{String.valueOf(documento)});
        if (cursor != null && cursor.moveToFirst()) {
        do {
            usuario = new Usuario();
                usuario.setDocumento(cursor.getInt(0));
                usuario.setUsuario(cursor.getString(1));
                usuario.setNombres(cursor.getString(2));
                usuario.setApellidos(cursor.getString(3));
                usuario.setContra(cursor.getString(4));

            userList.add(usuario);
        } while (cursor.moveToNext());

        cursor.close();
        }
        db.close();
        return userList;

    }

    public int actualizarUsuario (Usuario usuario) {
        SQLiteDatabase db = gestionBD.getWritableDatabase();
        ContentValues values = new ContentValues();
        if(usuario.getDocumento()<=0){
            Snackbar.make(this.view, "no se actualiza, por favor ingresar no documento" , Snackbar.LENGTH_LONG).show();
            return -1;

        }
        values.put("USU_USUARIO", usuario.getUsuario());
        values.put("USU_NOMBRES", usuario.getNombres());
        values.put("USUS_APELLIDOS", usuario.getApellidos());
        values.put("USU_CONTRA", usuario.getContra());

        return db.update("usuarios", values, "USU_DOCUMENTO = ?", new String[]{String.valueOf(usuario.getDocumento())});
    }

    public void eliminarUsuario (int documento) {
    SQLiteDatabase db = gestionBD.getWritableDatabase();
    db.delete("usuarios", "USU_DOCUMENTO = ?", new String[]{String.valueOf(documento)});
    db.close();
    }

}

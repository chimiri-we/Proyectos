package com.example.proyectos.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDLocal extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 2;                                                                                                                              ;
    private static final String DATABASE_NAME = "mi_empleo.db";
    private static final String ID_LOCAL = "id_local";

    private static final String TABLE_USUARIO = "Usuario";
    private static final String TABLE_DATOS_USUARIO = "DatosUsuario";
    private static final String TABLE_IMG_USUARIO = "ImgUsuario";

    public BDLocal(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String dbUsuario="create table Usuario("+
                "id_local INTEGER PRIMARY KEY,"+
                "id TEXT,"+
                "token TEXT,"+
                "nombre TEXT,"+
                "apellido_mat TEXT,"+
                "apellido_pat TEXT,"+
                "uri_imagen TEXT,"+
                "correo TEXT,"+
                "password TEXT,"+
                "telefono TEXT)";
        db.execSQL(dbUsuario);

        String dbImgUsuario="create table ImgUsuario("+
                "id INTEGER PRIMARY KEY,"+
                "id_usuario INTEGER,"+
                "tipo_imagen TEXT,"+

                "uri_imagen TEXT)";
        db.execSQL(dbImgUsuario);


        String dbDatosUsuario="create table DatosUsuario("+
                "id TEXT,"+
                "fechadeNacimiento TEXT,"+
                "ciudad TEXT,"+
                "colonia TEXT,"+
                "estado TEXT,"+
                "calle TEXT)";
        db.execSQL(dbDatosUsuario);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATOS_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMG_USUARIO);

        onCreate(db);
    }

}

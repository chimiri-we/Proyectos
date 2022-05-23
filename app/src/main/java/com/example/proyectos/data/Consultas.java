package com.example.proyectos.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyectos.model.Usuario;


public class Consultas extends BDLocal{

    Context context;
    private BDLocal bd;
    private static  Consultas INSTANCE;
    private static final String TABLE_USUARIO = "Usuario";
    private static final String TABLE_IMG_USUARIO = "ImgUsuario";
    private static final String ID_LOCAL = "id_local";
    private static final String TABLE_DATOS_USUARIO = "DatosUsuario";

    public Consultas(Context context) {
        super(context);
        this.context = context;
    }
    public static Consultas get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Consultas(context);
        }
        return INSTANCE;
    }

    public long crearCuenta(Usuario usuario) {
        bd = new BDLocal(context);
        long u = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("id", usuario.getId());
            values.put("token", usuario.getToken());
            values.put("nombre", usuario.getNombre());
            values.put("apellido_pat", usuario.getApellido_pat());
            values.put("apellido_mat", usuario.getApellido_mat());
            values.put("telefono", usuario.getTelefono());
            values.put("password", usuario.getPassword());
            values.put("correo", usuario.getCorreo());
            SQLiteDatabase db = bd.getWritableDatabase();
            u = db.insert(TABLE_USUARIO, null, values);
        }catch (Exception ex){
            ex.toString();
        }
        return u;

    }

    public  String guardarFotoUsuario(String id, String path){
        bd = new BDLocal(context);
        String img = null;
        try {
        ContentValues values = new ContentValues();

        //  values.put("tipo_imagen", usuario.getTipo_img());
        values.put("uri_imagen", path);
        SQLiteDatabase db = bd.getWritableDatabase();
       // db.insert(TABLE_USUARIO, null, values);
         img = String.valueOf(db.update(TABLE_USUARIO, values, ID_LOCAL+ " = ?", new String[]{id}));

        }catch (Exception ex){
            ex.toString();
        }
        return img;
    }

    public long guardarDireccion(Usuario usuario){
        bd = new BDLocal(context);
      //  Usuario usu = null;
        long id  =0;
        try {
            ContentValues values = new ContentValues();
            //values.put("id", usuario.getId());
            values.put("id", usuario.getId());
            values.put("calle", usuario.getCalle());
            values.put("ciudad", usuario.getCiudad());
            values.put("colonia", usuario.getColonia());
            values.put("estado", usuario.getEstado());

            SQLiteDatabase db = bd.getWritableDatabase();
            id = db.insert(TABLE_DATOS_USUARIO, null, values);
        }catch (Exception ex){
            ex.toString();
        }
        return id;

    }

    public Usuario verdatosUsuario(String id) {
        bd = new BDLocal(context);
        SQLiteDatabase db = bd.getWritableDatabase();
        Usuario usuarios = null;
        Cursor cursor;
try{
        cursor = db.rawQuery("select * from Usuario where id =" +id, null);
        if (cursor.moveToFirst()) {

            usuarios = new Usuario();
            usuarios.setId_local(cursor.getInt(0));
            //usuarios.setId_R(cursor.getInt(1));
            usuarios.setNombre(cursor.getString(3));
            usuarios.setApellido_pat(cursor.getString(4));
            usuarios.setApellido_mat(cursor.getString(5));
            usuarios.setUrlImagen(cursor.getString(6));
            usuarios.setCorreo(cursor.getString(7));
            usuarios.setPassword(cursor.getString(8));

            usuarios.setTelefono(cursor.getString(9));
        }
    cursor.close();

        } catch (Exception ex){
            ex.toString();
        }


        return usuarios;
    }

    public void cerrarSecion(){
        bd = new BDLocal(context);
        SQLiteDatabase db = bd.getReadableDatabase();
        db.execSQL("delete from " + TABLE_USUARIO);
    }

}

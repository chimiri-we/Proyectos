package com.example.proyectos.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.proyectos.model.Usuario;

public class SessionUsuario {

    private static final String PREFERENCIA_NOMBRE = "MI_TRABAJO_PREFS";
    private static final String PREF_ID = "PREF_USUARIO_ID";
    private static final String PREF_ID_LOCAL = "PREF_USUARIO_ID_LOCAL";
    private static final String PREF_NOMBRE = "PREF_USUARIO_NOMBRE";
    private static final String PREF_APELLIDO_PAT = "PREF_USUARIO_APELLIDO_PAT";
    private static final String PREF_APELLIDO_MAT = "PREF_USUARIO_APELLIDO_MAT";
    private static final String PREF_TELEFONO = "PREF_USUARIO_TELEFONO";
    private static final String PREF_CORREO = "PREF_USUARIO_CORREO";
    private static final String PREF_GENERO = "PREF_USUARIO_GENERO";
    private static final String PREF_TOKEN = "PREF_USUARIO_TOKEN";

    private boolean mIsLoggedIn = false;
    private final SharedPreferences mPrefs;
    private static SessionUsuario INSTANCE;

    public static SessionUsuario get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SessionUsuario(context);
        }
        return INSTANCE;
    }
    private SessionUsuario(Context context) {
    mPrefs = context.getApplicationContext()
            .getSharedPreferences(PREFERENCIA_NOMBRE, Context.MODE_PRIVATE);

    mIsLoggedIn = !TextUtils.isEmpty(mPrefs.getString(PREF_ID, null));
}

    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public void saveUSUARIO(Usuario usuario) {
        if (usuario != null) {
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(PREF_ID, usuario.getId());
          //  editor.putString(PREF_ID_LOCAL, String.valueOf(usuario.getId_local()));
            editor.putString(PREF_NOMBRE, usuario.getNombre());
            editor.putString(PREF_CORREO, usuario.getCorreo());
            //  editor.putString(PREF_GENERO, usuario.getGenero());
            editor.putString(PREF_TELEFONO, usuario.getTelefono());
            editor.putString(PREF_APELLIDO_PAT, usuario.getApellido_pat());
            editor.putString(PREF_APELLIDO_MAT, usuario.getApellido_mat());
              editor.putString(PREF_TOKEN, usuario.getToken());
            editor.apply();

            mIsLoggedIn = true;
        }
    }

    public String getToken() {
        return mPrefs.getString(PREF_TOKEN, null);
    }

    public void logOut() {
        mIsLoggedIn = false;
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_ID, null);
        editor.putString(PREF_NOMBRE, null);
        editor.putString(PREF_CORREO, null);
        editor.putString(PREF_GENERO, null);
        editor.putString(PREF_TELEFONO, null);
        editor.putString(PREF_APELLIDO_PAT, null);
        editor.putString(PREF_TOKEN, null);
        editor.apply();
    }

    public String getId() {
        return mPrefs.getString(PREF_ID, null);
    }



}

package com.example.proyectos.activitis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.proyectos.MainActivity;
import com.example.proyectos.R;
import com.example.proyectos.data.SessionUsuario;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    //int id=0;
    String ID=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ID = SessionUsuario.get(this).getId();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //  SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                //  SharedPreferences.Editor editor=preferences.edit();
                //  int bandera=Integer.parseInt(preferences.getString("bandera","0"));

                if (ID!=null){
                    Intent intent=new Intent(SplashActivity.this, ActividadPrincipal.class);
                    intent.putExtra("ID_USUARIO", ID);
                    startActivity(intent);
                }else{
                    // editor.putString("bandera","1");
                    //  editor.commit();

                    Intent intent=new Intent(SplashActivity.this, ContentRegistro.class);
                    startActivity(intent);


                }

                finish();
            }
        },3000);

    }
}
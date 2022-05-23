package com.example.proyectos.activitis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.proyectos.R;
import com.example.proyectos.ui.fragment.FinalFragment;
import com.example.proyectos.ui.fragment.LoginFragment;
import com.example.proyectos.ui.fragment.RegistroDireccionFragment;
import com.example.proyectos.ui.fragment.RegistroFragment;
import com.example.proyectos.ui.fragment.RegistroImagenFragment;

public class ContentRegistro extends AppCompatActivity {

    int id = 0;
    String cod = null;
    private Fragment fragmentR, fragmentLocation, fragmentImg,  fragmentFinal, fragmentL;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras != null){
                id = extras.getInt("id");
             cod = extras.getString("cod");
                if (id != 0) {
                    fragmentImg = new RegistroImagenFragment();
                    fragmentImg = RegistroImagenFragment.newInstance(String.valueOf(id));
                    getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragment, fragmentImg).commit();
                }
                if (cod != null){
                    fragmentLocation = new RegistroDireccionFragment();
                    fragmentLocation = RegistroDireccionFragment.newInstance(cod);
                    getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragment, fragmentLocation).commit();
                }


            }else {
            fragmentL = new LoginFragment();
            fragmentR = new RegistroFragment();
            fragmentFinal = new FinalFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragment, fragmentL).commit();

        } //else {
          //  id = (int) savedInstanceState.getSerializable("id");

        }




    }
    public void onClick (View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (view.getId())
        {
            case R.id.login_reg_btn: transaction.replace(R.id.contenedorFragment, fragmentR);
                transaction.addToBackStack(null);
                break;

            case R.id.btn_guardar: transaction.replace(R.id.contenedorFragment, fragmentLocation);
                transaction.addToBackStack(null);

                break;
         /*   case R.id.btnGuardarDireccion: transaction.replace(R.id.contenedorFragment, fragmentFinal);
                transaction.addToBackStack(null);
                break;*/
        }
        transaction.commit();

    }


    /*public void crearCuenta() {
       // long id = new RegistroFragment().obtenerDatos();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.contenedorFragment, fragmentImg);
        transaction.addToBackStack(null);

    }*/


}

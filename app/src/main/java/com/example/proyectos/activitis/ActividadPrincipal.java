package com.example.proyectos.activitis;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.proyectos.R;
import com.example.proyectos.data.Consultas;
import com.example.proyectos.data.SessionUsuario;
import com.example.proyectos.model.Usuario;
import com.example.proyectos.ui.fragment.FragmentoCuenta;
import com.example.proyectos.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;


public class ActividadPrincipal extends AppCompatActivity {

    private DrawerLayout drawerLayout;
Usuario usuario;
String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SessionUsuario.get(this).isLoggedIn()) {
            startActivity(new Intent(this, ContentRegistro.class));
            finish();
            return;
        }

        ID = SessionUsuario.get(this).getId();
        setContentView(R.layout.actividad_principal);
        agregarToolbar();
        usuario = Consultas.get(this).verdatosUsuario(ID);
        if (usuario != null ){
          //  String ID = usuario.getId();
            String NOMBRE = usuario.getNombre().toString();
            String APELLIDO_PAT = usuario.getApellido_pat().toString();
            String APELLIDO_MAT = usuario.getApellido_mat().toString();
            String TELEFONO = usuario.getTelefono().toString();
            String CORREO = usuario.getCorreo().toString();
            //String IMAGEN = usuario.getUrlImagen().toString();


            Toast.makeText(this,  ID, Toast.LENGTH_SHORT).show();
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));
            View header = navigationView.getHeaderView(0);
            TextView txtNombreHeader = header.findViewById(R.id.txtNombre);
         //   txtNombreHeader.setText(usuario.getNombre());
        }
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_drawer);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.item_inicio:
                fragmentoGenerico = new HomeFragment();
                break;
            case R.id.item_cuenta:
                fragmentoGenerico = new FragmentoCuenta();
                break;

            case R.id.item_configuracion:
                startActivity(new Intent(this, ActividadConfiguracion.class));
                break;
        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void cerrarSesion() {

        SessionUsuario.get(this).logOut();
        Consultas.get(this).cerrarSecion();
        startActivity(new Intent(this, ActividadPrincipal.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_logout_btn:
                cerrarSesion();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

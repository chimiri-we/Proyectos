package com.example.proyectos.activitis;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyectos.R;
import com.example.proyectos.model.Usuario;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
public class DetallePerfilActivity extends AppCompatActivity {
    View  vista;
    ImageView image;
    //Bitmap bitmap;
     Usuario usuario;
 /*   public static void createInstance(Activity activity, Girl title) {
        Intent intent = getLaunchIntent(activity, title);
        activity.startActivity(intent);
    }*/


   /* public static Intent getLaunchIntent(Context context, Girl girl) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_NAME, girl.getName());
        intent.putExtra(EXTRA_DRAWABLE, girl.getIdDrawable());
        return intent;
    }*/
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.actividad_detalle_perfil);
      // usuario =  Consultas.get(DetailActivity.this).verdatosUsuario();

       setToolbar();// Añadir action bar
       if (getSupportActionBar() != null) // Habilitar up button
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent i = getIntent();
        String name = i.getStringExtra("nombre");
       // int idDrawable = i.getIntExtra(EXTRA_DRAWABLE, -1);

       //String idDrawable = (String.valueOf(R.drawable.pp);

       CollapsingToolbarLayout collapser =
               (CollapsingToolbarLayout) findViewById(R.id.collapser);
       collapser.setTitle(name); // Cambiar título
       // Usuario usuario = new Usuario();
       //  String img  = Consultas.get(this).buscarDatos();
       /*if(usuario.getUrlImagen() != null){

           Bitmap bitmap= BitmapFactory.decodeFile(usuario.getUrlImagen());
           loadImageParallax(bitmap);
           Toast.makeText(this, "tipo de img es "+usuario.getUrlImagen(), Toast.LENGTH_LONG).show();


       }*/
       // Cargar Imagen

       // Setear escucha al FAB
     /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       fab.setOnClickListener(
               new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       showSnackBar("Opción de Chatear");
                   }
               }
       );
*/

   }


    private void setToolbar() {
        // Añadir la Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Se carga una imagen aleatoria para el detalle
     * @param bitmap
     */
    private void loadImageParallax(Bitmap bitmap) {
        ImageView image = (ImageView) findViewById(R.id.image_paralax);
        // Usando Glide para la carga asíncrona

        Glide.with(this)
                .asBitmap()
                .load(bitmap)
                .error(R.drawable.ic_help)
                .centerCrop()
                .apply(RequestOptions.centerCropTransform())
                .into(image);
    }

    /**
     * Proyecta una {@link Snackbar} con el string usado
     *
     * @param msg Mensaje
     */
    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                showSnackBar("Se abren los ajustes");
                return true;
            case R.id.action_add:
                showSnackBar("Añadir a contactos");
                return true;
            case R.id.action_favorite:
                showSnackBar("Añadir a favoritos");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
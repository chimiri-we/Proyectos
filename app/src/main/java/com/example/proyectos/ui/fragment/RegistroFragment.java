package com.example.proyectos.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.proyectos.R;
import com.example.proyectos.activitis.ActividadPrincipal;
import com.example.proyectos.activitis.ContentRegistro;
import com.example.proyectos.api.ConeccionApi;
import com.example.proyectos.api.mapping.ApiError;
import com.example.proyectos.api.mapping.RegistroBody;
import com.example.proyectos.data.Consultas;
import com.example.proyectos.data.SessionUsuario;
import com.example.proyectos.model.Usuario;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText nombreUsuario;
    private EditText correoUsuario;
    private EditText telefonoUsuario;
    private ProgressBar progressBar;
    private EditText apellidosPat;
    private EditText apellidosMat;
    Button btnRegistrar;
    View formRegis;
    Usuario usuario;
    private EditText passwordUsuario;
   long id;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroFragment newInstance(String param1, String param2) {
        RegistroFragment fragment = new RegistroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private Retrofit mRestAdapter;
    private ConeccionApi coneccionApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_registro, container, false);

        mRestAdapter = new Retrofit.Builder()
                .baseUrl(ConeccionApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        coneccionApi = mRestAdapter.create(ConeccionApi.class);

        nombreUsuario = v.findViewById(R.id.reg_name_et);
        correoUsuario = v.findViewById(R.id.reg_email_et);
        apellidosPat = v.findViewById(R.id.reg_apellido_pat);
        apellidosMat = v.findViewById(R.id.reg_apellido_mat);
        telefonoUsuario = v.findViewById(R.id.telefonoRegistro);
        passwordUsuario = v.findViewById(R.id.reg_password_et);
        progressBar = v.findViewById(R.id.loading_reg);
        formRegis = v.findViewById(R.id.form_registro);
        btnRegistrar = v.findViewById(R.id.btn_registrar);
        btnRegistrar.setOnClickListener(view -> {
           // long id = obtenerDatos();
            obtenerDatos();

           // Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_LONG).show();

        });
        return v;
    }

    public void obtenerDatos() {

        Usuario usuario = null;
        String NOMBRE = nombreUsuario.getText().toString();
        String APELLIDO_PAT = apellidosPat.getText().toString();
        String APELLIDO_MAT = apellidosMat.getText().toString();
        String TELEFONO = telefonoUsuario.getText().toString();
        String PASSWORD = passwordUsuario.getText().toString();
        String CORREO = correoUsuario.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(PASSWORD)) {
            passwordUsuario.setError(getString(R.string.error_field_required));
            focusView = passwordUsuario;
            cancel = true;
        } else if (!isPasswordValid(PASSWORD)) {
            passwordUsuario.setError(getString(R.string.error_invalid_password));
            focusView = passwordUsuario;
            cancel = true;
        }

        // Verificar si el ID tiene contenido.
        if (TextUtils.isEmpty(CORREO)) {
            correoUsuario.setError(getString(R.string.error_field_required));
            focusView = correoUsuario;
            cancel = true;
        } /*else if (!isUserIdValid(CORREO)) {
            correoUsuario.setError(getString(R.string.error_invalid_user_id));
            focusView = correoUsuario;
            cancel = true;
        }*/
        if (TextUtils.isEmpty(TELEFONO)) {
            telefonoUsuario.setError(getString(R.string.error_field_required));
            focusView = telefonoUsuario;
            cancel = true;
        } /*else if (!isPasswordValid(TELEFONO)) {
            telefonoUsuario.setError(getString(R.string.error_invalid_password));
            focusView = telefonoUsuario;
            cancel = true;
        }*/

        // Verificar si el ID tiene contenido.
        if (TextUtils.isEmpty(NOMBRE)) {
            nombreUsuario.setError(getString(R.string.error_field_required));
            focusView = nombreUsuario;
            cancel = true;
        } /*else if (!isUserIdValid(NOMBRE)) {
            nombreUsuario.setError(getString(R.string.error_invalid_user_id));
            focusView = nombreUsuario;
            cancel = true;
        }
*/
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else {
            // Mostrar el indicador de carga y luego iniciar la petición asíncrona.
            showProgress(true);

           // usuario = new Usuario(NOMBRE, APELLIDO_MAT, APELLIDO_PAT, CORREO, TELEFONO, PASSWORD);

            Call<Usuario> registroCall = coneccionApi.registro(new RegistroBody(NOMBRE, APELLIDO_MAT, APELLIDO_PAT, CORREO, TELEFONO, PASSWORD));
            registroCall.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    // Mostrar progreso
                    showProgress(false);

                    // Procesar errores
                    if (!response.isSuccessful()) {
                        String error = "Ha ocurrido un error. Contacte al administrador";
                        if (response.errorBody()
                                .contentType()
                                .subtype()
                                .equals("json")) {
                            ApiError apiError = ApiError.fromResponseBody(response.errorBody());

                            error = apiError.getMessage();
                            Log.d("RegistroFragment", apiError.getDeveloperMessage());
                        } else {
                            try {
                                // Reportar causas de error no relacionado con la API
                                Log.d("RegistroFragment", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        showError(error);
                        return;
                    }


                    // Guardar afiliado en preferencias
                  //  SessionUsuario.get(getContext()).saveUSUARIO(response.body());
                    id = Consultas.get(getContext()).crearCuenta(response.body());
                    if(id != 0){
                        Usuario usu = new Usuario((int) id, NOMBRE, APELLIDO_MAT, APELLIDO_PAT, CORREO, TELEFONO, PASSWORD);
                        SessionUsuario.get(getContext()).saveUSUARIO(usu);
                        Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_LONG).show();

                        //
                        //showLocationScreen();
                    }
                    else {
                        String error = "usuario no fue creado";
                        //showProgress(false);
                        showError(error);
                    }

                    showLocationScreen(id);

                }
        // Ir a la citas médicas


                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    showProgress(false);
                    showError(t.getMessage());
                }
            });

        }
            //return id;
    }

    /*private void crearCuenta(Usuario usuario) {

       // Usuario usuario = new Usuario(NOMBRE, APELLIDO_MAT, APELLIDO_PAT, CORREO, TELEFONO, PASSWORD);

        id = Consultas.get(getContext()).crearCuenta(usuario);
        if(id != 0){
        //    showProgress(false);

            Usuario usu = new Usuario((int) id, usuario.getNombre(), usuario.getApellido_mat() , usuario.getApellido_pat(),  usuario.getTelefono(),  usuario.getCorreo(),  usuario.getPassword());

            SessionUsuario.get(getContext()).saveUSUARIO(usu);

            Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_LONG).show();


            //showLocationScreen();
        }
    }
*/
    private void limppiarCampos() {

      nombreUsuario.setText("");
        apellidosPat.setText("");
       apellidosMat.setText("");
       telefonoUsuario.setText("");
       passwordUsuario.setText("");
        correoUsuario.setText("");
    }

    private void showLocationScreen(long id) {
        limppiarCampos();
        Intent in = new Intent(getContext(), ContentRegistro.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", (int) id);
        //RegistroImagenFragment fragmentImg = RegistroImagenFragment.newInstance(String.valueOf(id));

        in.putExtras(bundle);
        getContext().startActivity(in);
    }


    private boolean isUserIdValid(String userId) {
        return userId.length() == 10;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }


    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);

        int visibility = show ? View.GONE : View.VISIBLE;
        // mLogoView.setVisibility(visibility);
        formRegis.setVisibility(visibility);
    }

    private void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

   /* private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }*/

}
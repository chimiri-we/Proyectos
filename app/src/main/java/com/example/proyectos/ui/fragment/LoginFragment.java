package com.example.proyectos.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectos.R;
import com.example.proyectos.activitis.ActividadPrincipal;
import com.example.proyectos.api.ConeccionApi;
import com.example.proyectos.api.mapping.ApiError;
import com.example.proyectos.api.mapping.LoginBody;
import com.example.proyectos.data.Consultas;
import com.example.proyectos.data.SessionUsuario;
import com.example.proyectos.model.Usuario;
import com.example.proyectos.ui.perfil.SeccionPerfilFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
    private ConeccionApi mSaludMockApi;

    // UI references.
    private ImageView mLogoView;
    private EditText mUserIdView;
    private EditText mPasswordView;
  //  private TextInputLayout mFloatLabelUserId;
   // private TextInputLayout mFloatLabelPassword;
    private View mProgressView;
    private View mLoginFormView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mRestAdapter = new Retrofit.Builder()
                .baseUrl(ConeccionApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mSaludMockApi = mRestAdapter.create(ConeccionApi.class);

        mLogoView = view.findViewById(R.id.login_logo);
        mUserIdView = view.findViewById(R.id.email_et);
        mPasswordView = view.findViewById(R.id.password);
      //  mFloatLabelUserId = view.findViewById(R.id.float_label_user_id);
      //  mFloatLabelPassword = view.findViewById(R.id.float_label_password);
        Button mSignInButton = view.findViewById(R.id.login_btn);
        mLoginFormView = view.findViewById(R.id.login_form);
        mProgressView = view.findViewById(R.id.loading);

        // Setup
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    if (!isOnline()) {
                        showLoginError(getString(R.string.error_network));
                        return false;
                    }
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOnline()) {
                    showLoginError(getString(R.string.error_network));
                    return;
                }
                attemptLogin();

            }
        });

        return view;
    }



    private void attemptLogin() {

        // Reset errors.
        mUserIdView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String correo = mUserIdView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Verificar si el ID tiene contenido.
        if (TextUtils.isEmpty(correo)) {
            mUserIdView.setError(getString(R.string.error_field_required));
            focusView = mUserIdView;
            cancel = true;
        } else if (!isUserIdValid(correo)) {
            mUserIdView.setError(getString(R.string.error_invalid_user));
            focusView = mUserIdView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Mostrar el indicador de carga y luego iniciar la petición asíncrona.
            showProgress(true);
            Toast.makeText(getContext(), correo + password, Toast.LENGTH_LONG).show();
           // Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            Call<Usuario> loginCall = mSaludMockApi.login(new LoginBody(correo, password));
            loginCall.enqueue(new Callback<Usuario>() {
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
                            Log.d("LoginActivity", apiError.getDeveloperMessage());
                        } else {
                            try {
                                // Reportar causas de error no relacionado con la API
                                Log.d("LoginActivity", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        showLoginError(error);
                        return;
                    }

                    // Guardar afiliado en preferencias
                    long id = Consultas.get(getContext()).crearCuenta(response.body());
                    SessionUsuario.get(getContext()).saveUSUARIO(response.body());
                    Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_LONG).show();

                    // Ir a la citas médicas
                    showAppointmentsScreen(id);
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    showProgress(false);
                    showLoginError(t.getMessage());
                }
            });
        }
    }

    private void showAppointmentsScreen(long id) {
        startActivity(new Intent(getContext(), ActividadPrincipal.class));
        getActivity().finish();
    }

    private boolean isUserIdValid(String userId) {
        return userId.length() > 10;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void showProgress(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        int visibility = show ? View.GONE : View.VISIBLE;
        mLogoView.setVisibility(visibility);
        mLoginFormView.setVisibility(visibility);
    }



    private void showLoginError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

}
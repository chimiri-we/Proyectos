package com.example.proyectos.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectos.R;
import com.example.proyectos.adapter.AdapterListaUsuarios;
import com.example.proyectos.api.ConeccionApi;
import com.example.proyectos.api.mapping.ApiError;
import com.example.proyectos.api.mapping.ListUsuario;
import com.example.proyectos.data.SessionUsuario;

import com.example.proyectos.databinding.FragmentHomeBinding;
import com.example.proyectos.model.User;
import com.example.proyectos.model.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    List<User> serverAppointments;
    private AdapterListaUsuarios adapter;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private RecyclerView rv;
    private Retrofit retrofit;
    private ConeccionApi coneccionApi;
    public static final String TAG = HomeFragment.class.getSimpleName();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(ConeccionApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        coneccionApi = retrofit.create(ConeccionApi.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rv = binding.listAppointments;
        loadAppointments();




        return root;
    }

    private void loadAppointments() {
        String token = SessionUsuario.get(getContext()).getToken();
        Call call = coneccionApi.getListUsuarios(token);
        call.enqueue(new Callback<ListUsuario>() {
            @Override
            public void onResponse(Call<ListUsuario> call,
                                   Response<ListUsuario> response) {
                if (!response.isSuccessful()) {
                    // Procesar error de API
                    String error = "Ha ocurrido un error. Contacte al administrador";
                    if (response.errorBody()
                            .contentType()
                            .subtype()
                            .equals("json")) {
                        ApiError apiError = ApiError.fromResponseBody(response.errorBody());

                        error = apiError.getMessage();
                        Log.d(TAG, apiError.getDeveloperMessage());
                    } else {
                        try {
                            // Reportar causas de error no relacionado con la API
                            Log.d(TAG, response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                 //   showLoadingIndicator(false);
                    showErrorMessage(error);
                    return;
                }

             //   = response.body().getUser();

                if (serverAppointments.size() > 0) {
                    // Mostrar lista de citas médicas
                    showAppointments(serverAppointments);
//                    Toast.makeText(getContext(), (CharSequence) serverAppointments, Toast.LENGTH_LONG).show();
                } else {
                    // Mostrar empty state
                  //  showNoAppointments();
                }

            }

            @Override
            public void onFailure(Call<ListUsuario> call, Throwable t) {
                //showLoadingIndicator(false);
                Log.d(TAG, "Petición rechazada:" + t.getMessage());
                showErrorMessage("Error de comunicación");
            }
        });
    }

    private void showAppointments(List<User> serverAppointments) {
       // adapter.swapItems(serverAppointments);
        adapter = new AdapterListaUsuarios(serverAppointments, getContext());
        rv.setAdapter(adapter);

       // rv.setVisibility(View.VISIBLE);
       // mEmptyStateContainer.setVisibility(View.GONE);
    }

    private void showErrorMessage(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAppointments();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
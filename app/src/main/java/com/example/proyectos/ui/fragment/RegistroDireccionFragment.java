package com.example.proyectos.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyectos.R;
import com.example.proyectos.activitis.ActividadPrincipal;
import com.example.proyectos.service.GetAddressIntentService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistroDireccionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroDireccionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView currentAddTv, verUbicacion, editarDir;
    private Location currentLocation;
    private LocationCallback locationCallback;
   private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
    private LocationAddressResultReceiver addressResultReceiver;
    private View cardView;
    private ProgressBar loadingProgressBar;
    CircleImageView imgPerfil;
Button btnGuardarDir;
    public RegistroDireccionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment RegistroDireccionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroDireccionFragment newInstance(String param1) {
        RegistroDireccionFragment fragment = new RegistroDireccionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
      //  args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_registro_direccion, container, false);
        addressResultReceiver = new LocationAddressResultReceiver(new Handler());
        loadingProgressBar = v.findViewById(R.id.loading_registro_img);
        editarDir = v.findViewById(R.id.ubi);
        currentAddTv = v.findViewById(R.id.textView8);
        verUbicacion = v.findViewById(R.id.txt_ubicacion);
        cardView = v.findViewById(R.id.card_direccion);

        btnGuardarDir = v.findViewById(R.id.btnGuardarDireccion);
       btnGuardarDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ActividadPrincipal.class));
                getActivity().finish();
            }
        });
        imgPerfil = v.findViewById(R.id.iv_avatar);
        verFoto(mParam1);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                getAddress();
            }
        };

        verUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardView.setVisibility(view.VISIBLE);

                //  verCardView();
               // verImagen();
                startLocationUpdates();
                btnGuardarDir.setVisibility(view.VISIBLE);
            }
        });
        editarDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarDireccion();
            }
        });
        return v;


    }

    private void editarDireccion() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        //      TextInputLayout inputLayout;
        View viewDir = inflater.inflate(R.layout.dialogo_direccion, null, false);
     /*   TextInputEditText edtDireccionColonia = viewDir.findViewById(R.id.edt_direcci_colonia);
        TextInputEditText edtDireccionCiudad = viewDir.findViewById(R.id.edt_direcci_ciudad);
        TextInputEditText edtDireccionCalle = viewDir.findViewById(R.id.edt_direcci_calle);

        if (usuarios != null){
            edtDireccionCiudad.setText(usuarios.getCiudad());
            edtDireccionCalle.setText(usuarios.getCalle());
            edtDireccionColonia.setText(usuarios.getColonia());
        }*/
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(viewDir);
        builder.create();

       /* builder.setPositiveButton("GUARDAR DATOS", (dialog, which) -> {
            // actualizarDatos();
           String newciudad = Objects.requireNonNull(edtDireccionCiudad.getText()).toString().trim();
            String newcalle = Objects.requireNonNull(edtDireccionCalle.getText()).toString().trim();
            String newcolonia = Objects.requireNonNull(edtDireccionColonia.getText()).toString().trim();

            if (TextUtils.isEmpty(newciudad)) {
                Toast.makeText(getContext(), "Algo salió mal. Verifique sus valores de entrada", Toast.LENGTH_LONG).show();
            } else {
                bdLocal = new BaseDatos(requireContext().getApplicationContext());
                assert usuarios != null;
                bdLocal.actualizarDireccion(new
                        Usuario(usuarios.getId(), newciudad, newcalle, newcolonia));


            }
        });*/
        builder.setNegativeButton("CANCELAR", (dialog, which) -> Toast.makeText(getContext(), "Tarea Cancelada",Toast.LENGTH_LONG).show());

        builder.show();
    }

    private void verFoto(String mParam1) {
        Glide.with(this).
                load(mParam1).
                apply(RequestOptions.centerCropTransform()).
                into(imgPerfil);
    }

    @SuppressWarnings("MissingPermission")
    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new
                            String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
        else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }
    @SuppressWarnings("MissingPermission")
    private void getAddress() {
        if (!Geocoder.isPresent()) {
            Toast.makeText(getContext(), "Can't find current address, ",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getContext(), GetAddressIntentService.class);
        intent.putExtra("add_receiver", addressResultReceiver);
        intent.putExtra("add_location", currentLocation);
        getContext().startService(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull
            int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            }
            else {
                Toast.makeText(getContext(), "Location permission not granted, " + "restart the app if you want the feature", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                Log.d("Address", "Location null retrying");
                getAddress();
            }
            if (resultCode == 1) {
                Toast.makeText(getContext(), "Address not found, ", Toast.LENGTH_SHORT).show();
            }
            String currentAdd = resultData.getString("address_result");

            String registro = String.valueOf(resultData.getInt("registro"));

            showResults(registro);
        }
    }
    private void showResults(String string) {

        currentAddTv.setText(string);
    }
    @Override
    public void onResume() {
        super.onResume();
        startLocationUpdates();
    }
    @Override
    public void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}
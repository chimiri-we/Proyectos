package com.example.proyectos.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.proyectos.data.Consultas;
import com.example.proyectos.data.SessionUsuario;
import com.example.proyectos.model.Usuario;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetAddressIntentService extends IntentService {
    private static final String IDENTIFIER = "GetAddressIntentService";
    private ResultReceiver addressResultReceiver;
    public GetAddressIntentService() {
        super(IDENTIFIER);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String msg;
long registro = 0;
addressResultReceiver = Objects.requireNonNull(intent).getParcelableExtra("add_receiver");
        if (addressResultReceiver == null) {
            Log.e("GetAddressIntentService", "No receiver, not processing the request further");
            return;
        }
        Location location = intent.getParcelableExtra("add_location");
        if (location == null) {
            msg = "No location, can't go further without location";
            sendResultsToReceiver(0, msg, (int) registro);
            return;
        }
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        }
        catch (Exception ioException) {
            Log.e("", "Error in getting address for the location");
        }
        if (addresses == null || addresses.size() == 0) {
            msg = "No address found for the location";
            sendResultsToReceiver(1, msg, (int) registro);
        }
        else {
            Address address = addresses.get(0);
            String addressDetails = address.getFeatureName() + "\n" + address.getThoroughfare() + "\n" +
                    "Locality: " + address.getLocality() + "\n" + "County: " + address.getSubAdminArea() + "\n" +
                    "State: " + address.getAdminArea() + "\n" + "Country: " + address.getCountryName() + "\n" +
                    "Postal Code: " + address.getPostalCode() + "\n";

String ID =            SessionUsuario.get(getApplicationContext()).getId();
    Usuario   direccion = new Usuario(ID, address.getThoroughfare(), address.getLocality(), address.getSubAdminArea(), address.getAdminArea());
           long id = Consultas.get(getApplicationContext()).guardarDireccion(direccion);

            sendResultsToReceiver(2, addressDetails, Math.toIntExact(id));

        }
    }
    private void sendResultsToReceiver(int resultCode, String message, int registro) {
        Bundle bundle = new Bundle();
        bundle.putString("address_result", message);
        bundle.putInt("registro", registro);
        addressResultReceiver.send(resultCode, bundle);
    }
/*



    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.miempleo.service.action.FOO";
    private static final String ACTION_BAZ = "com.miempleo.service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.miempleo.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.miempleo.service.extra.PARAM2";

    public GetAddressIntentService() {
        super("GetAddressIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     *
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, GetAddressIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     *
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, GetAddressIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     *
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     *
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }*/
}
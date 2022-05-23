package com.example.proyectos.ui.fragment;



import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyectos.R;
import com.example.proyectos.activitis.ContentRegistro;
import com.example.proyectos.data.Consultas;
import com.example.proyectos.model.Usuario;
import com.example.proyectos.util.DateTimeUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistroImagenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroImagenFragment extends Fragment {


    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
   // private String path;//almacena la ruta de la imagen
    File fileImagen;
    Bitmap bitmap;
    ImageView imgFoto, imgEdit, imgEditdirec, imgEditContra;
    // CircleImageView imgperfil;
    ProgressDialog progreso;
    private final int MIS_PERMISOS = 100;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;
    public static final String TAG = RegistroImagenFragment.class.getSimpleName();
    private Button mAddPhotoButton, saveBtn;
    private CircleImageView mPhotoImage;

    private String mCurrentPhotoPath;
    private ImageButton mRemoveButton;
    private Uri mPhotoURI;
    TextView ruta;
    Usuario u;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ID = "id";
    String tipoIMG = "perfil";
    String id;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistroImagenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *  * @return A new instance of fragment RegistroImagenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroImagenFragment newInstance(String id) {
        RegistroImagenFragment fragment = new RegistroImagenFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ID);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registro_imagen, container, false);


        Toast.makeText(getContext(), "el usuario es "+id, Toast.LENGTH_LONG).show();
        // Button guardar = view.findViewById(R.id.btn_guardar);
        ruta = view.findViewById(R.id.ubi_uno);
        mAddPhotoButton = view.findViewById(R.id.add_photo_button);
        mAddPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogOpciones();
               // showCameraApp();
            }
        });

        mPhotoImage = view.findViewById(R.id.issue_photo);

        saveBtn = view.findViewById(R.id.btn_guardar);

        // Botón para remover foto
        mRemoveButton = view.findViewById(R.id.remove_photo_button);
        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhotoImage.setVisibility(View.GONE);
                mRemoveButton.setVisibility(View.GONE);
                saveBtn.setVisibility(View.GONE);

                mAddPhotoButton.setVisibility(View.VISIBLE);
            }
        });
        return view;

    }

    private void mostrarDialogOpciones() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        final CharSequence[] opciones={"Tomar Foto","Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(requireContext());
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, (dialogInterface, i) -> {
            if (opciones[i].equals("Tomar Foto")){
                showCameraApp();
            }else{
                if (opciones[i].equals("Elegir de Galeria")){
                    Intent intent=new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/");
                    startActivityForResult(Intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);
                }else{
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }



    private void showCameraApp() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d(TAG, "Error ocurrido cuando se estaba creando el archivo de la imagen. Detalle: " + ex.toString());
            }

            if (photoFile != null) {
                mPhotoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.proyectos.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoURI);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = DateTimeUtils.formatDateForFileName(new Date());

        String prefix = "JPEG_" + timeStamp + "_";
        File directory = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                prefix,
                ".jpg",
                directory
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case COD_SELECCIONA:
                Uri miPath=data.getData();
                String img = String.valueOf(miPath);
                Toast.makeText(getContext(), img, Toast.LENGTH_LONG).show();

                showPhoto(img);

                break;
            case REQUEST_IMAGE_CAPTURE:
                MediaScannerConnection.scanFile(getContext(), new String[]{mCurrentPhotoPath}, null,
                        (path, uri) -> Log.i("Path",""+path));
                showPhoto(mCurrentPhotoPath);
                break;

       /* if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                handleCameraPhoto();
            }

                if (resultCode == COD_FOTO) {
                    MediaScannerConnection.scanFile(getContext(), new String[]{mCurrentPhotoPath}, null,
                            (path, uri) -> Log.i("Path","  pat ga"+mCurrentPhotoPath));
                    Toast.makeText(getContext(), mCurrentPhotoPath, Toast.LENGTH_LONG).show();

                    handleCameraPhoto();
            }*/
        }
    }

    private void handleCameraPhoto() {
        if (mCurrentPhotoPath != null) {
            showPhoto(mCurrentPhotoPath);
        }
    }

    private void showPhoto(String imagen) {
        Glide.with(this).
                load(imagen).
                apply(RequestOptions.centerCropTransform()).
                into(mPhotoImage);
        mPhotoImage.setVisibility(View.VISIBLE);
        mRemoveButton.setVisibility(View.VISIBLE);
        saveBtn.setVisibility(View.VISIBLE);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarFoto(imagen);

            }
        });

        ruta.setText(mCurrentPhotoPath);
        mAddPhotoButton.setVisibility(View.GONE);

    }

    private void guardarFoto(String imagen) {

String URI_IMAGEN = Consultas.get(getContext()).guardarFotoUsuario(id, imagen);
if (URI_IMAGEN != null){
    Toast.makeText(getContext(), URI_IMAGEN, Toast.LENGTH_LONG).show();

}
        Intent in = new Intent(getContext(), ContentRegistro.class);
        Bundle bundle = new Bundle();
        bundle.putString("cod", imagen);
        //RegistroImagenFragment fragmentImg = RegistroImagenFragment.newInstance(String.valueOf(id));

        in.putExtras(bundle);
        getContext().startActivity(in);
    }
}
package com.example.proyectos.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.proyectos.R;


public class PerfilUsuarioFragment extends Fragment {

    //private FragmentoPerfilBinding binding;
    public PerfilUsuarioFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmento_perfil, container, false);
    }
    }


package com.example.proyectos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectos.R;
import com.example.proyectos.model.Empresa;

import java.util.List;

public class AdapterEmpresas extends RecyclerView.Adapter<AdapterEmpresas.HolderEmpresas>{

    private List<Empresa> listEmpresa;
    private Context context;

    public AdapterEmpresas(List<Empresa> listEmpresa, Context context) {
        this.listEmpresa = listEmpresa;
        this.context = context;
    }



    @NonNull
    @Override
    public AdapterEmpresas.HolderEmpresas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_cliente,parent,false);


        return new AdapterEmpresas.HolderEmpresas(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEmpresas.HolderEmpresas holder, int position) {
Empresa empresa = listEmpresa.get(position);

holder.nombreEmp.setText(empresa.getNombre());
        holder.correoEmp.setText(empresa.getCorreo_contacto());
        holder.descripcionEmp.setText(empresa.getDescripcion_corta());
    }

    @Override
    public int getItemCount() {
        return listEmpresa.size();
    }



    static class HolderEmpresas extends RecyclerView.ViewHolder{

        private TextView nombreEmp;
        private TextView correoEmp;
        private TextView descripcionEmp;
        public HolderEmpresas(View itemView){
            super(itemView);

            nombreEmp = (TextView) itemView.findViewById(R.id.nombre_comida);
            correoEmp = (TextView) itemView.findViewById(R.id.precio_comida);
            descripcionEmp = (TextView) itemView.findViewById(R.id.descripcion);


        }
    }
}

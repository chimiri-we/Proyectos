package com.example.proyectos.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyectos.R;
import com.example.proyectos.activitis.DetallePerfilActivity;
import com.example.proyectos.model.User;
import com.example.proyectos.model.Usuario;

import java.util.List;

public class AdapterListaUsuarios extends RecyclerView.Adapter<AdapterListaUsuarios.HolderUsuarios> {

    private List<User> usuariosList;
    private Context context;

//    RequestQueue requestQueue;
    //  Picasso picaso;
    String id;

    public AdapterListaUsuarios(List<User> list, Context context){
        this.usuariosList = list;
        this.context = context;
    //    requestQueue = Volley.newRequestQueue(context);
        // this.f=f;
    }

    @Override
    public HolderUsuarios onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cliente,parent,false);
        return new AdapterListaUsuarios.HolderUsuarios(v);
    }

    @Override
    public void onBindViewHolder(HolderUsuarios holder, @SuppressLint("RecyclerView") final int position) {
        //       Glide.get().load(atributosList.get(position).getFotoPerfil()).error(R.drawable.ic_edit).into(holder.imageView);
        User sl = (User) usuariosList.get(position);
        String nombre = usuariosList.get(position).getNombre();

        holder.tvNombre.setText(sl.getNombre());
        holder.tvCorreo.setText(sl.getApellido_pat());
        holder.tvTelefono.setText(sl.getApellido_mat());

      /*  int tipo = 1;
        int estado = 1;
        if (usuariosList.get(position).getTipo_solicitud() != tipo){
            holder.nombre.setText(sl.getNombre());
            holder.hora.setText(sl.getFecha_solicitud());
            if (usuariosList.get(position).getEstado_solicitud() != estado){
                holder.mensaje.setText("Tienes una solicitud de "+nombre+" por confirmar");
            }else{
                holder.mensaje.setVisibility(View.GONE);
            }
            if (usuariosList.get(position).getUrl_foto()!=null){
                verfoto(atributosList.get(position).getUrl_foto(), holder);
            }

        }else {
            holder.cardView.setVisibility(View.GONE);
        }
*/
        if (usuariosList.get(position).getUrlImagen()!=null){
            verfoto(usuariosList.get(position).getUrlImagen(), holder);
        }
        holder.contentCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetallePerfilActivity.class);
                i.putExtra("nombre", nombre);
                i.putExtra("id_usuario",usuariosList.get(position).getId());
                context.startActivity(i);
            }
        });

        holder.contentCliente.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context).
                        setMessage("¿Estas seguro que quieres eliminar a este amigo?").
                        setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //f.eliminarAmigo(atributosList.get(position).getId());
                                Toast.makeText(context, "Se elimino el amigo correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Toast.makeText(context, "Cancelando solicitud de eliminacion", Toast.LENGTH_SHORT).show();
                    }
                }).show();
                return true;
            }
        });



    }

    private void verfoto(String urlImagen, HolderUsuarios holder) {
        Glide.with(context)
                .asBitmap()
                .load(urlImagen)
                .error(R.drawable.ic_help)
                .centerCrop()
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);
    }

  /*  private void verfoto(String url_foto, HolderUsuarios holder) {
        String URL = "https://servicioparanegocio.es/Trabajos/"+url_foto;
        URL=URL.replace(" ","%20");

        ImageRequest imgReq = new ImageRequest(URL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                holder.imageView.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(imgReq);

    }*/



    @Override
    public int getItemCount() {
        return usuariosList.size();
    }



    static class HolderUsuarios extends RecyclerView.ViewHolder{

        View contentCliente;
       // CardView cardView;
        ImageView imageView;
        TextView tvNombre;
        TextView tvCorreo;
        TextView tvTelefono;

        public HolderUsuarios(View itemView) {
            super(itemView);
            contentCliente =  itemView.findViewById(R.id.content_cliente);
            imageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tvNombre = (TextView) itemView.findViewById(R.id.tv_name);
        //    tvCorreo = (TextView) itemView.findViewById(R.id.tv_correo);
          //  tvTelefono = (TextView) itemView.findViewById(R.id.tv_telefono);
        }
    }

}
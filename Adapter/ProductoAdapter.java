package com.pdm.panaderia.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pdm.panaderia.R;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder>{

    private List<Producto> lista_productos;
    private Context context;

    public ProductoAdapter(List<Producto> lista_productos, Context context) {
        this.lista_productos = lista_productos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.producto,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Double aux= ( lista_productos.get(position).getPrecio_producto());
        String s=aux.toString()+"€";
        holder.nombre_producto.setText(lista_productos.get(position).getNombre_producto());
        holder.precio_producto.setText(s);
        /*
        //FOTO
        byte[] fotoBytes = cursor.getBlob(0);
        Bitmap bitmap = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
        miImageView.setImageBitmap(bitmap);

         */



    }

    @Override
    public int getItemCount() {
        return lista_productos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imagen_producto;
        private TextView nombre_producto, precio_producto;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagen_producto=itemView.findViewById(R.id.imagen_producto);
            nombre_producto=itemView.findViewById(R.id.nombre_producto);
            precio_producto=itemView.findViewById(R.id.precio_producto);
        }
    }
}

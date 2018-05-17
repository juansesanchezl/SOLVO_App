package com.RecyclerViewClases;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.SQLib.Comentario;
import com.example.solvo.solvo.R;

import java.util.List;

public class LineAdapter extends RecyclerView.Adapter<LineHolder> {

    private final List<Comentario> mComentarios;

    public LineAdapter(List<Comentario> comentarios) {
        mComentarios = comentarios;
    }

    @Override
    public LineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LineHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_comentarios, parent, false));
    }

    @Override
    public void onBindViewHolder(LineHolder holder, int position) {
        for (Comentario com: mComentarios) {
            holder.comUsuarioSolvo.setText(com.getCOND_USER());
            holder.comComentarioSolvo.setText(com.getCOMENTARIO());
        }
    }

    @Override
    public int getItemCount() {
        return mComentarios != null ? mComentarios.size() : 0;
    }



}
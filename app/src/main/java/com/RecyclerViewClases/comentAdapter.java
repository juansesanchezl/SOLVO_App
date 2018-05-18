package com.RecyclerViewClases;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.SQLib.Comentario;
import com.example.solvo.solvo.R;

import java.util.List;

public class comentAdapter extends RecyclerView.Adapter<comentAdapter.ViewHolder> {

    private List<Comentario> comentarioList;

    public comentAdapter(List<Comentario> comentarios){
        this.comentarioList = comentarios;
    }

    @Override
    public int getItemCount() {
        return comentarioList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.lista_comentarios, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comentario comen = comentarioList.get(position);
        holder.userSolvo.setText("["+comen.getCOND_USER()+"]");
        holder.comenSolvo.setText(comen.getCOMENTARIO());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userSolvo;
        public TextView comenSolvo;
        public ViewHolder(View itemView) {
            super(itemView);
            this.userSolvo = (TextView) itemView.findViewById(R.id.comusuarioSolvo);
            this.comenSolvo = (TextView) itemView.findViewById(R.id.comcomenSolvo);
        }
    }


}

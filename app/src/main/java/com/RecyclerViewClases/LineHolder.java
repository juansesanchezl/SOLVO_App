package com.RecyclerViewClases;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.solvo.solvo.R;

public class LineHolder extends RecyclerView.ViewHolder {

    public TextView comUsuarioSolvo;
    public TextView comComentarioSolvo;


    public LineHolder(View itemView) {
        super(itemView);
        comUsuarioSolvo = (TextView) itemView.findViewById(R.id.comUsuarioSolvo);
        comComentarioSolvo = (TextView) itemView.findViewById(R.id.comComentarioSolvo);

    }
}
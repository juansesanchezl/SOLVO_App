package com.RecyclerViewClases;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.solvo.solvo.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView userSolvo;
    public TextView comenSolvo;
    public ViewHolder(View itemView) {
        super(itemView);
        this.userSolvo = (TextView) itemView.findViewById(R.id.comusuarioSolvo);
        this.comenSolvo = (TextView) itemView.findViewById(R.id.comcomenSolvo);
    }
}
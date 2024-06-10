package com.example.yaplacaklistesiuygulamas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotlarAdapter extends RecyclerView.Adapter<NotlarAdapter.NotlarViewHolder>
{
    private List<Not> notlar;

    private OnNotClickListener listener;

    public NotlarAdapter(List<Not> _notlar, OnNotClickListener listener)
    {
        this.notlar = _notlar;
        this.listener = listener;
    }

    public void setNotlar(List<Not> _notlar)
    {
        this.notlar = _notlar;
        notifyDataSetChanged();
    }

    @Override
    public NotlarAdapter.NotlarViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notlar_satiri,parent,false);
        return new NotlarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotlarAdapter.NotlarViewHolder holder, int position)
    {
        Not not = notlar.get(position);
        holder.baslikTextView.setText(not.getBaslik());
        holder.icerikTextView.setText(not.getİcerik());

        holder.silButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                listener.onSilme(not);
            }
        });

        holder.guncelleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                listener.onGüncelleme(not);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notlar.size();
    }

    public class NotlarViewHolder extends RecyclerView.ViewHolder
    {
        private TextView baslikTextView;
        private TextView icerikTextView;

        private Button silButton;
        private Button guncelleButton;

        public NotlarViewHolder(View itemView) {
            super(itemView);

            baslikTextView = itemView.findViewById(R.id.not_basligi);
            icerikTextView = itemView.findViewById(R.id.not_içeriği);

            silButton = itemView.findViewById(R.id.not_sil_btn);
            guncelleButton = itemView.findViewById(R.id.not_guncelle_btn);
        }
    }

    public interface OnNotClickListener
    {
        void onSilme(Not not);
        void onGüncelleme(Not not);
    }
}

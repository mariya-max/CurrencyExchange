package com.example.currencyExchange;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyExchange.Model.Valute;

import io.realm.RealmResults;

public class AdapterValuate extends RecyclerView.Adapter<AdapterValuate.ViewHolder> {

    private static ClickListener clickListener;
    private RealmResults<Valute> valuateResults;

    public AdapterValuate(RealmResults<Valute> valuateResults) {
        this.valuateResults = valuateResults;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Valute valute = valuateResults.get(position);
        String nameValuate = valute.getCharCode() + " / " + valute.getName();
        holder.charCode.setText(nameValuate);
        holder.value.setText(String.valueOf(valute.getValue()));
        holder.id = valuateResults.get(position).getId();
    }

    @Override
    public int getItemCount() {
        if (valuateResults == null) {
            return 0;
        }
        return valuateResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView charCode;
        private TextView value;
        private String id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            charCode = itemView.findViewById(R.id.charCode);
            value = itemView.findViewById(R.id.value);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, id);
        }
    }

    public void setOnClickListener(ClickListener clickListener) {
        AdapterValuate.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View view, String id);
    }

    public void updateList(RealmResults<Valute> valuateResults) {
        this.valuateResults = valuateResults;
        notifyDataSetChanged();
    }
}

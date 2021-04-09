package com.example.joueurdedevant;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class EvaluationListAdapter extends RecyclerView.Adapter<EvaluationListAdapter.EvaluationViewHolder> {

    private final LinkedList<Evaluation> evaluationList;
    private LayoutInflater inflater;

    public EvaluationListAdapter(Context context, LinkedList<Evaluation> evaluationList) {
        inflater = LayoutInflater.from(context);
        this.evaluationList = evaluationList;
    }

    class EvaluationViewHolder extends RecyclerView.ViewHolder {

        public final TextView evaluationItemClubname;
        public final TextView evaluationItemCategory;
        public final TextView evaluationItemDate;

        final EvaluationListAdapter adapter;

        public EvaluationViewHolder(@NonNull View itemView, EvaluationListAdapter adapter) {
            super(itemView);
            evaluationItemClubname = itemView.findViewById(R.id.clubname);
            evaluationItemCategory = itemView.findViewById(R.id.category);
            evaluationItemDate = itemView.findViewById(R.id.date);
            this.adapter = adapter;
        }
    }

    @NonNull
    @Override
    public EvaluationListAdapter.EvaluationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View evaluationView = inflater.inflate(R.layout.list_item_evaluation, parent, false);
        return new EvaluationViewHolder(evaluationView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull EvaluationListAdapter.EvaluationViewHolder holder, int position) {
        Evaluation e = evaluationList.get(position);
        Log.d("RecyclerView","Item n°"+position+" : "+ evaluationList.toString());

        holder.evaluationItemClubname.setText(e.getClub());
        holder.evaluationItemCategory.setText(e.getCategorie().toString());
        holder.evaluationItemDate.setText(e.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return evaluationList.size();
    }
}

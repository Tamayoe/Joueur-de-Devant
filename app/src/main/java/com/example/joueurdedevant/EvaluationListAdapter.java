package com.example.joueurdedevant;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EvaluationListAdapter extends RecyclerView.Adapter<EvaluationListAdapter.EvaluationViewHolder> {

    private final LinkedList<Evaluation> evaluationList;
    private LayoutInflater inflater;

    private AppDatabase db;
    private EvaluationDAO dao;

    public EvaluationListAdapter(Context context, LinkedList<Evaluation> evaluationList) {
        inflater = LayoutInflater.from(context);
        this.evaluationList = evaluationList;

        this.db = AppDatabase.getInstance(context);
        this.dao = db.evaluationDAO();
    }

    class EvaluationViewHolder extends RecyclerView.ViewHolder {

        public final TextView evaluationItemClubname;
        public final TextView evaluationItemCategory;
        public final Button evaluationItemDelete;

        final EvaluationListAdapter adapter;

        public EvaluationViewHolder(@NonNull View itemView, EvaluationListAdapter adapter) {
            super(itemView);
            evaluationItemClubname = itemView.findViewById(R.id.clubname);
            evaluationItemCategory = itemView.findViewById(R.id.category);
            evaluationItemDelete = itemView.findViewById(R.id.delete);
            this.adapter = adapter;

            //bouton pour supprimer chaque evaluation
            evaluationItemDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();

                if(position != RecyclerView.NO_POSITION) {
                    Evaluation clickedEvaluation = evaluationList.get(position);
                    Log.d("Activity : Layout",clickedEvaluation.toString());

                    new AlertDialog.Builder(itemView.getContext())
                            .setTitle("Attention :")
                            .setMessage("Voulez-vous vraiment supprimer l'evaluation "+clickedEvaluation.getCategorie()+" du club "+clickedEvaluation.getClub())
                            .setIcon(android.R.drawable.ic_menu_delete)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Toast.makeText(itemView.getContext(), "Evaluation Supprimée", Toast.LENGTH_SHORT).show();
                                    Log.d("Activity : Layout","Delete item n°"+position);
                                    dao.deleteEval(clickedEvaluation)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(() -> {
                                                        evaluationList.remove(position);
                                                        adapter.notifyItemRemoved(position);
                                                        Log.d("Adapter : Database","Supression réussie");
                                                    },
                                                    throwable -> Log.e("Erreur de suppression",throwable.getMessage())
                                            );
                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            });
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
        holder.evaluationItemCategory.setText("Evaluation "+e.getCategorie().toString()+" du "+e.getDate().getDayOfMonth()+"/"+e.getDate().getMonthValue()+"/"+e.getDate().getYear());
    }

    @Override
    public int getItemCount() {
        return evaluationList.size();
    }
}

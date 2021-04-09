package com.example.joueurdedevant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;


public class ListEvaluationActivity extends AppCompatActivity {

    //Data
    private LinkedList<Evaluation> evaluations = new LinkedList<Evaluation>();

    //Database
    private AppDatabase db;
    private EvaluationDAO dao;

    //Interface
    MaterialToolbar toolbar;
    private RecyclerView recyclerView;
    private EvaluationListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_evaluation);

        this.toolbar = findViewById(R.id.toolbar);
        this.toolbar.setNavigationOnClickListener(this::onReturn);
        this.recyclerView = findViewById(R.id.recyclerview);

        this.db = AppDatabase.getInstance(this);
        this.dao = db.evaluationDAO();


        //Evaluation eval1 = new Evaluation("Polo Scopo", Evaluation.Categorie.M15F);

        Log.d("ListEvaluationActivity","Insertion en bdd");
        /*
        dao.insertEval(eval1)
        .subscribeOn(Schedulers.io())
        .subscribe(new DisposableCompletableObserver() {
                       @Override
                       public void onComplete() {
                           Log.d("ListEvaluationActivity","Insertion réussie");
                       }

                       @Override
                       public void onError(@NonNull Throwable e) {
                           Log.d("ListEvaluationActivity","Echec de l'insertion");
                       }
                   }
        );
        Log.d("Activity : Controller","eval1.id = "+Integer.toString(eval1.getId()));
         */

        dao.getAllEval()
            .subscribeOn(Schedulers.io())
            .subscribe(res -> {
                Log.d("Activity : Database","Recuperation en bdd");
                Log.d("Activity : Database",res.size()+" résultats : ");
                for (Evaluation r : res) {
                    Log.d("Activity : Database",r.toString());
                    Log.d("Activity : Database","Categorie id : "+r.getCategorie().getId());
                }
                Boolean notEmpty = evaluations.addAll(res);
                if(notEmpty) {
                    Log.d("Activity : Database", "Données récupérés");
                    setRecyclerViewContent(evaluations);
                } else {
                    Log.d("Activity : Database", "Données vides");
                }
            },error -> System.err.println("The error message is: " + error.getMessage()));
    }

    public void setRecyclerViewContent(LinkedList<Evaluation> linkedList) {
        adapter = new EvaluationListAdapter(this, linkedList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void onReturn(View v) {
        finish();
    }
}
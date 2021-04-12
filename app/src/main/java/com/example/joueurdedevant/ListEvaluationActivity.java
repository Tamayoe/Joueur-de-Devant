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

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
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

        fetchData();
    }

    public Disposable fetchData() {
        return dao.getAllEval()
                .subscribeOn(Schedulers.io())
                .subscribe(res -> {
                    Log.d("Activity : Database","Recuperation en bdd");
                    Log.d("Activity : Database",res.size()+" résultats : ");
                    Boolean notEmpty = evaluations.addAll(res);
                    if(notEmpty) {
                        Log.d("Activity : Database", "Données convertis avec succès");
                        setRecyclerViewContent(evaluations);
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d("Activity : Database", "Données non convertis");
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
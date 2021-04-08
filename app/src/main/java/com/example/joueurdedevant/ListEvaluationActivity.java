package com.example.joueurdedevant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.TypeConverter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ListEvaluationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_evaluation);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(this::onReturn);

        AppDatabase db = AppDatabase.getInstance(this);
        EvaluationDAO dao = db.evaluationDAO();

        Evaluation eval1 = new Evaluation("Polo Scopo", Evaluation.Categorie.M15F);
        Log.d("ListEvaluationActivity","Creation de l'evaluation");
        Log.d("ListEvaluationActivity",eval1.toString());

        Log.d("ListEvaluationActivity","Insertion en bdd");
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

        dao.getAllEval()
            .subscribeOn(Schedulers.io())
            .subscribe(res -> {
                Log.d("Activity : Database","Recuperation en bdd");
                Log.d("Activity : Database",res.size()+" résultats : ");
                for (Evaluation r : res) {
                    Log.d("Activity : Database",r.toString());
                    Log.d("Activity : Database","Categorie id : "+r.getCategorie().getId());

                }
            },error -> System.err.println("The error message is: " + error.getMessage()));
    }

    public void onReturn(View v) {
        finish();
    }
}
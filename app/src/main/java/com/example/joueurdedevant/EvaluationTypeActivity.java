package com.example.joueurdedevant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import com.example.joueurdedevant.Evaluation.Categorie;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class EvaluationTypeActivity extends AppCompatActivity {

    //Database
    private AppDatabase db;
    private EvaluationDAO dao;

    //Interface
    private MaterialToolbar toolbar;
    private TextInputLayout textinput;
    private RadioGroup radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_type);

        this.db =  AppDatabase.getInstance(this);
        this.dao = db.evaluationDAO();

        //COMPOSANTS
        toolbar = findViewById(R.id.toolbar);
        textinput = findViewById(R.id.textinput);
        radio = findViewById(R.id.radiogroup);

        //TRAITEMENTS
        toolbar.setNavigationOnClickListener(this::onReturn);
    }

    public void onReturn(View v) {
        finish();
    }

    public void onCreate(View v) {
        Log.d("Activity : Controller","Starting evaluation creation process");
        //Recuperation des entrées utilisateur
        String clubname = textinput.getEditText().getText().toString();
        int checkedRadioId = radio.getCheckedRadioButtonId();

        //Configuration de la categorie
        Categorie categorie;
        switch (checkedRadioId) {
            case 1 :
                categorie = Categorie.M14G;
                break;
            case 2 :
                categorie = Categorie.M15F;
                break;
            case 3 :
                categorie = Categorie.SENIOR;
                break;
            default:
                categorie = Categorie.NULL;
                break;
        }

        //Création de l'objet evaluation
        Evaluation evaluation = new Evaluation(clubname, categorie);
        System.out.println(evaluation);

        //Insertion en base de données
        this.dao.insertEval(evaluation)
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableCompletableObserver() {
                               @Override
                               public void onComplete() {
                                   Log.d("Activity : Database","Insertion réussie");
                               }

                               @Override
                               public void onError(@NonNull Throwable e) {
                                   Log.d("Activity : Database","Echec de l'insertion");
                               }
                           }
                );


    }
}
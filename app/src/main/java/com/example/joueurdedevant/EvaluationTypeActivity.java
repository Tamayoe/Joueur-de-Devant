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

public class EvaluationTypeActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private TextInputLayout textinput;
    private RadioGroup radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_type);

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
        Log.i("EvaluationTypeActivity","Starting evaluation creation process");
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

    }
}
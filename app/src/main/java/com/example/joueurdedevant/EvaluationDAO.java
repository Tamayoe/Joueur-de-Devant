package com.example.joueurdedevant;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface EvaluationDAO {

    //EVALUATION ENTITY
    @Query("SELECT * FROM evaluations")
    public Single<List<Evaluation>> getAllEval();

    @Query("SELECT * FROM evaluations WHERE id=:id")
    public Single<Evaluation> getEval(int id);

    @Insert
    public Completable insertEval(Evaluation eval);

    @Delete
    public Completable deleteEval(Evaluation eval);
}

package com.example.joueurdedevant;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {

    //CATEGORIE
        @TypeConverter
        public static int fromCategorie(Evaluation.Categorie value) {
            return value.getId();
        }

        @TypeConverter
        public static Evaluation.Categorie toCategorie(int value) {
            return Evaluation.Categorie.values()[value + 1];
        }

    //DATE
        @TypeConverter
        public static Date fromTimestamp(Long value) {
            return value == null ? null : new Date(value);
        }

        @TypeConverter
        public static Long toTimestamp(Date date) {
            return date == null ? null : date.getTime();
        }
}

package com.example.joueurdedevant;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
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
        public static LocalDateTime toDate(String dateString) {
            if (dateString == null) {
                return null;
            } else {
                return LocalDateTime.parse(dateString);
            }
        }

        @TypeConverter
        public static String toDateString(LocalDateTime date) {
            if (date == null) {
                return null;
            } else {
                return date.toString();
            }
        }
}

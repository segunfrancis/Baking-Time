package com.project.segunfrancis.bakingtime.data_source.local;

import android.content.Context;

import com.project.segunfrancis.bakingtime.model.Ingredient;
import com.project.segunfrancis.bakingtime.model.IngredientsForWidget;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * Created by SegunFrancis
 */
@Database(entities = {IngredientsForWidget.class}, exportSchema = false, version = 1)
@TypeConverters(Converter.class)
public abstract class IngredientRoomDatabase extends RoomDatabase {
    public abstract IngredientDao mRecipeDao();

    private static final Object LOCK = new Object();
    private static volatile IngredientRoomDatabase INSTANCE;

    public static IngredientRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            IngredientRoomDatabase.class,
                            "recipe-database"
                    ).allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}

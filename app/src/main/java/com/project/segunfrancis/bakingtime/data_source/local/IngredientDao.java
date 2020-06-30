package com.project.segunfrancis.bakingtime.data_source.local;

import com.project.segunfrancis.bakingtime.model.IngredientsForWidget;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * Created by SegunFrancis
 */
@Dao
public interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIngredients(IngredientsForWidget ingredients);

    @Query("SELECT * FROM ingredient_for_widget")
    IngredientsForWidget getIngredients();
}

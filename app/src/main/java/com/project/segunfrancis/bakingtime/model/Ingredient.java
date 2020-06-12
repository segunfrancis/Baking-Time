package com.project.segunfrancis.bakingtime.model;

import com.squareup.moshi.Json;

/**
 * Created by SegunFrancis
 */
public class Ingredient {

    @Json(name = "quantity")
    private Integer quantity;

    @Json(name = "measure")
    private String measure;

    @Json(name = "ingredient")
    private String ingredient;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}

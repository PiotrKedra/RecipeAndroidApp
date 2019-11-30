package com.example.recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipe.database.Recipe;
import com.example.recipe.database.RecipeDatabaseHelper;

public class RecipeForm extends AppCompatActivity {

    private RecipeDatabaseHelper recipeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_form);
        recipeDB = new RecipeDatabaseHelper(this);
    }

    public void createRecipe(){
    }

    public void saveRecipe(View view) {
        Recipe recipe = getRecipe();
        saveRecipe(recipe);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private Recipe getRecipe() {
        EditText recipeName = findViewById(R.id.recipeNameField);
        EditText recipeEndOFWarrantyDate = findViewById(R.id.recipeEndOfWarrantyField);
        return Recipe.builder()
                .name(recipeName.getText().toString())
                .endOfWarrantyDate(recipeEndOFWarrantyDate.getText().toString())
                .build();
    }

    private void saveRecipe(Recipe recipe) {
        if(recipeDB.create(recipe))
            Toast.makeText(this, "Successfully save recipe", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Failed to save recipe", Toast.LENGTH_LONG).show();
    }
}

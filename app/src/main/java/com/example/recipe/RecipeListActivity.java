package com.example.recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.recipe.database.Recipe;
import com.example.recipe.database.RecipeDatabaseHelper;

import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    private RecipeDatabaseHelper recipeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        showRecipes();
    }

    private void showRecipes(){
        recipeDB = new RecipeDatabaseHelper(this);
        ArrayAdapter adapter = getRecipesAdapter();
        ListView listView = findViewById(R.id.recipeList);
        listView.setAdapter(adapter);
    }

    private ArrayAdapter getRecipesAdapter() {
        List<Recipe> recipes = recipeDB.getAll();
        String[] recipeNames = mapToNameArray(recipes);
        return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeNames);
    }

    private String[] mapToNameArray(List<Recipe> recipes) {
        return recipes.stream()
                .map(Recipe::getName)
                .toArray(String[]::new);
    }
}

package com.example.recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.recipe.database.Recipe;
import com.example.recipe.database.RecipeDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToRecipeForm(View view){
        Intent intent = new Intent(this, RecipeForm.class);
        startActivity(intent);
    }

    public void goToRecipeList(View view){
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);
    }
}

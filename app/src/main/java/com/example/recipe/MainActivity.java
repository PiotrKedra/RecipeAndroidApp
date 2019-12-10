package com.example.recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.recipe.database.Recipe;
import com.example.recipe.database.RecipeDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "chaing orientation", Toast.LENGTH_LONG).show();
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

    public void goToLocation(View view){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Toast.makeText(this, "AAAAAAAAAAAAAAAAA", Toast.LENGTH_SHORT).show();
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}

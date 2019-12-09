package com.example.recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipe.database.Recipe;
import com.example.recipe.database.RecipeDatabaseHelper;

import java.util.List;

public class RecipeListActivity extends AppCompatActivity implements ListFragment.ItemFromList {

    private RecipeDatabaseHelper recipeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "in create " + getResources().getConfiguration().orientation, Toast.LENGTH_LONG).show();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_recipe_list_horizontal);
        }else {
            setContentView(R.layout.activity_recipe_list);
        }
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

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof ListFragment) {
            ListFragment list = (ListFragment) fragment;
            list.setItemFromList(this);
        }
    }

    @Override
    public void sendItem(String name) {
        PropertiesFragment propertiesFragment = (PropertiesFragment) getSupportFragmentManager().findFragmentById(R.id.propertiesFragment);
        //((TextView) propertiesFragment.getView().findViewById(R.id.recipeNameField)).setText(name);
        propertiesFragment.updateProperties(name);
    }

    public void deleteRecipe(View view) {
        PropertiesFragment propertiesFragment = (PropertiesFragment) getSupportFragmentManager().findFragmentById(R.id.propertiesFragment);
        propertiesFragment.deleteRecipe(view);
        finish();
        startActivity(getIntent());
    }
}

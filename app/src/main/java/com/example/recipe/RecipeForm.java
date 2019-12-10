package com.example.recipe;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipe.database.Recipe;
import com.example.recipe.database.RecipeDatabaseHelper;

import java.util.Date;
import java.util.GregorianCalendar;


public class RecipeForm extends AppCompatActivity {

    private RecipeDatabaseHelper recipeDB;
    private Date endOfWarrantyDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_form);
        recipeDB = new RecipeDatabaseHelper(this);
    }

    public void saveRecipe(View view) {
        Recipe recipe = getRecipe();
        if (recipe.getName().isEmpty()) {
            Toast.makeText(this, "Pleas enter a name", Toast.LENGTH_LONG).show();
            return;
        }
        saveRecipe(recipe);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private Recipe getRecipe() {
        EditText recipeName = findViewById(R.id.recipeNameField);
        return Recipe.builder()
                .name(recipeName.getText().toString())
                .endOfWarrantyDate(this.endOfWarrantyDate.toString())
                .build();
    }

    public void openDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(RecipeForm.this,
                (datePicker, year, month, day) -> {
                    this.endOfWarrantyDate = new GregorianCalendar(year, month - 1, day).getTime();
                    TextView endOfWarrantyField = findViewById(R.id.recipeEndOfWarrantyField);
                    endOfWarrantyField.setText(this.endOfWarrantyDate.toString());
                }, 2019, 11, 5);
        datePickerDialog.show();
    }

    private void saveRecipe(Recipe recipe) {
        if (recipeDB.create(recipe))
            Toast.makeText(this, "Successfully save recipe", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Failed to save recipe", Toast.LENGTH_LONG).show();
    }
}

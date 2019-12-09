package com.example.recipe;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipe.database.Recipe;
import com.example.recipe.database.RecipeDatabaseHelper;

import java.util.Optional;


/**
 * A simple {@link Fragment} subclass.
 */
public class PropertiesFragment extends Fragment {

    View view;

    private RecipeDatabaseHelper recipeDB;

    private int currentID = 0;

    public PropertiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_properties, container, false);
        recipeDB = new RecipeDatabaseHelper(getActivity());
        view = inflate;
        return inflate;
    }

    public void updateProperties(String name) {
        Optional<Recipe> recipe = recipeDB.getByName(name);
        if(recipe.isPresent()) {
            TextView nameField = view.findViewById(R.id.nameField);
            nameField.setText(recipe.get().getName());

            TextView warrantyField = view.findViewById(R.id.warrantyField);
            warrantyField.setText(recipe.get().getEndOfWarrantyDate());

            currentID = recipe.get().getId();
        }else {
            Toast.makeText(getContext(), "Recipe not found", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteRecipe(View view){
        boolean delete = recipeDB.delete(currentID);
        if(delete){
            Toast.makeText(getContext(), "Recipe deleted", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(), "Error. Recipe not deleted", Toast.LENGTH_LONG).show();
        }

    }
}

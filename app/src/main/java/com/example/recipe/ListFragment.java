package com.example.recipe;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.recipe.database.Recipe;
import com.example.recipe.database.RecipeDatabaseHelper;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    ItemFromList itemFromList;

    public void setItemFromList(ItemFromList itemFromList) {
        this.itemFromList = itemFromList;
    }

    public interface ItemFromList{
        public void sendItem(String name);
    }

    private RecipeDatabaseHelper recipeDB;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        showRecipes(view);
        return view;
    }

    private void showRecipes(View view){
        recipeDB = new RecipeDatabaseHelper(getActivity());
        ArrayAdapter adapter = getRecipesAdapter();
        ListView listView = view.findViewById(R.id.idListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                (parent, view1, position, id) -> {
                    itemFromList.sendItem((String)listView.getItemAtPosition(position));
                    Toast.makeText(getContext(), "Cliked: " + position, Toast.LENGTH_LONG).show();
                }
        );
    }

    private ArrayAdapter getRecipesAdapter() {
        List<Recipe> recipes = recipeDB.getAll();
        String[] recipeNames = mapToNameArray(recipes);
        return new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, recipeNames);
    }

    private String[] mapToNameArray(List<Recipe> recipes) {
        return recipes.stream()
                .map(Recipe::getName)
                .toArray(String[]::new);
    }

    @Override
    public void onDetach() {
        itemFromList = null; // => avoid leaking, thanks @Deepscorn
        super.onDetach();
    }
}

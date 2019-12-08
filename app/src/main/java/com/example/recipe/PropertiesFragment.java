package com.example.recipe;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class PropertiesFragment extends Fragment {

    View view;

    public PropertiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_properties, container, false);
        view = inflate;
        return inflate;
    }

    public void updateProperties(String name) {
        TextView nameField = view.findViewById(R.id.nameField);
        nameField.setText(name);
        Toast.makeText(getContext(), name, Toast.LENGTH_LONG).show();
    }
}

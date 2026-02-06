package com.example.lab5_starter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class CityDialogFragment extends DialogFragment {
    interface CityDialogListener {
        void updateCity(City city, String title, String year);
        void addCity(City city);
        void deleteCity(String city);
    }
    private CityDialogListener listener;

    public static CityDialogFragment newInstance(City city){
        Bundle args = new Bundle();
        args.putSerializable("City", city);

        CityDialogFragment fragment = new CityDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CityDialogListener){
            listener = (CityDialogListener) context;
        }
        else {
            throw new RuntimeException("Implement listener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view;
        EditText editMovieName;
        EditText editMovieYear;
        Log.d("bruh", "ya");
        if (Objects.equals(getTag(), "Delete City")){
            view = getLayoutInflater().inflate(R.layout.fragment_city_details2, null);
            editMovieName = view.findViewById(R.id.edit_city_name_to_delete);
            editMovieYear = view.findViewById(R.id.edit_city_name_to_delete);
        }else {
            view = getLayoutInflater().inflate(R.layout.fragment_city_details, null);
            editMovieName = view.findViewById(R.id.edit_city_name);
            editMovieYear = view.findViewById(R.id.edit_province);
        }



        String tag = getTag();
        Bundle bundle = getArguments();
        City city;

        if (Objects.equals(tag, "City Details") && bundle != null){
            city = (City) bundle.getSerializable("City");
            assert city != null;
            editMovieName.setText(city.getName());
            editMovieYear.setText(city.getProvince());
        }
        else {
            city = null;}

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (!Objects.equals(getTag(), "Delete City")) {


            return builder
                    .setView(view)
                    .setTitle("City Details")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Continue", (dialog, which) -> {
                        String title = editMovieName.getText().toString();
                        String year = editMovieYear.getText().toString();
                        if (Objects.equals(tag, "City Details")) {
                            listener.updateCity(city, title, year);
                        } else {
                            listener.addCity(new City(title, year));
                        }
                    })
                    .create();

        }else{


            return builder
                    .setView(view)
                    .setTitle("Delete A Details")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Continue", (dialog, which) -> {
                        String title = editMovieName.getText().toString();
                        listener.deleteCity(title);
                    })
                    .create();

        }


    }
}

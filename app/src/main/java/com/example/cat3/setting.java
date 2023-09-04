package com.example.cat3;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;


public class setting extends Fragment {


    public setting() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        Button breakbtn = rootView.findViewById(R.id.breakbtn);
        Button studybtn = rootView.findViewById(R.id.studybtn);

        // Restore saved break time from SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        SharedPreferences.Editor editor = preferences.edit();
        String savedBreakTime = preferences.getString("break_time", "");
        breakbtn.setText(savedBreakTime);

        String savedStudyTime = preferences.getString("study_time", "");
        studybtn.setText(savedStudyTime);

        breakbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Inflate the custom dialog layout
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_break_time, null);

                // Create the custom dialog
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                dialogBuilder.setView(dialogView);
                AlertDialog dialog = dialogBuilder.create();

                // Find checkboxes and set their state if needed
                CheckBox checkBox5 = dialogView.findViewById(R.id.checkBox5);
                CheckBox checkBox10 = dialogView.findViewById(R.id.checkBox10);
                CheckBox checkBox15 = dialogView.findViewById(R.id.checkBox15);

                // Set click listener for the close button
                Button closeButton = dialogView.findViewById(R.id.closeButton);

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Check which checkboxes are selected and update the button text
                        StringBuilder selectedOptions = new StringBuilder();
                        if (checkBox5.isChecked()) {
                            selectedOptions.append("Away, ");
                        }
                        if (checkBox10.isChecked()) {
                            selectedOptions.append("Online, ");
                        }
                        if (checkBox15.isChecked()) {
                            selectedOptions.append("Offline, ");
                        }
                        // Update the button text if options are selected
                        if (selectedOptions.length() > 0) {
                            String buttonText = selectedOptions.substring(0, selectedOptions.length() - 2);
                            breakbtn.setText(buttonText);

                            // Save the buttonText as the rest time preference
                            editor.putString("break_time", buttonText);
                            editor.apply();
                        }

                        dialog.dismiss(); // Close the dialog
                    }
                });

                dialog.show(); // Show the dialog

            }
        });

        studybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the custom dialog layout
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_study_time, null);

                // Create the custom dialog
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                dialogBuilder.setView(dialogView);
                AlertDialog dialog = dialogBuilder.create();

                // Find checkboxes and set their state if needed
                CheckBox checkBox40 = dialogView.findViewById(R.id.checkBox40);
                CheckBox checkBox60 = dialogView.findViewById(R.id.checkBox60);
                CheckBox checkBox80 = dialogView.findViewById(R.id.checkBox80);

                // Set click listener for the close button
                Button closeButton = dialogView.findViewById(R.id.closeButton);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Check which checkboxes are selected and update the button text
                        StringBuilder selectedOptions = new StringBuilder();
                        if (checkBox40.isChecked()) {
                            selectedOptions.append("2 Days, ");
                        }
                        if (checkBox60.isChecked()) {
                            selectedOptions.append("6 Days, ");
                        }
                        if (checkBox80.isChecked()) {
                            selectedOptions.append("10 Days, ");
                        }

                        // Update the button text if options are selected
                        if (selectedOptions.length() > 0) {
                            String buttonText = selectedOptions.substring(0, selectedOptions.length() - 2);
                            studybtn.setText(buttonText);

                            // Save the buttonText as the rest time preference
                            editor.putString("study_time", buttonText);
                            editor.apply();
                        }

                        dialog.dismiss(); // Close the dialog
                    }
                });
                dialog.show(); //show the dialog
            }

        });
        return rootView;
    }
}
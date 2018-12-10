package com.aloine.resolute40.smartLocation.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.aloine.resolute40.R;
import com.aloine.resolute40.smartLocation.ViewMappedFarmActivity;

public class MyDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.custom_dialog_layout,null);
        builder.setView(v);



        Button save_mapping = v.findViewById(R.id.save_mapping);

        Button continue_mapping = v.findViewById(R.id.continue_mapping);

        ImageView exit_dialog = v.findViewById(R.id.exit_dialog);

        save_mapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Your settings has been saved", Toast.LENGTH_SHORT).show();
                view.getContext().startActivity(new Intent(view.getContext().getApplicationContext(), ViewMappedFarmActivity.class));
            }
        });

        continue_mapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "You want to continue", Toast.LENGTH_SHORT).show();
                getDialog().hide();

            }
        });

        exit_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "You want to exit the dialog button", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        });
        return builder.create();
    }
}

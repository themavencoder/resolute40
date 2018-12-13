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

import com.aloine.resolute40.AppInstance;
import com.aloine.resolute40.R;
import com.aloine.resolute40.auth.register.network.Client;
import com.aloine.resolute40.smartLocation.StartMappingActivity;
import com.aloine.resolute40.smartLocation.ViewMappedFarmActivity;
import com.aloine.resolute40.smartLocation.model.Keys;
import com.aloine.resolute40.smartLocation.model.PostLocationData;
import com.aloine.resolute40.smartLocation.network.ApiService;
import com.aloine.resolute40.smartLocation.network.PostLocationResponse;

import io.nlopez.smartlocation.SmartLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDialog extends DialogFragment {
    private ApiService mApiService;

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
                AppInstance app = AppInstance.getInstance();
                Keys keys = new Keys(app.getClient_token(),app.getSession_token());
                PostLocationData postLocationData = new PostLocationData(keys,app.getUsername(), app.getPointsToServer());
                postLocation(postLocationData);

                Toast.makeText(getActivity(), "Your settings has been saved", Toast.LENGTH_SHORT).show();
                SmartLocation.with(view.getContext()).location().stop();
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


    private void postLocation(PostLocationData data) {
        mApiService = Client.getClient().create(ApiService.class);
        Call<PostLocationResponse> call = mApiService.postLocation(data);
        call.enqueue(new Callback<PostLocationResponse>() {
            @Override
            public void onResponse(Call<PostLocationResponse> call, Response<PostLocationResponse> response) {
                if (response.body().getResponse() != null) {
                    switch (response.body().getResponse()) {
                        case "success":
                            Toast.makeText(getActivity(), "Posted to the server successfully", Toast.LENGTH_LONG).show();
                            break;
                        case "failed":
                            Toast.makeText(getActivity(), "Failure to post to the server successfully", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(getActivity(), "The default method is shown when the switch breaks", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostLocationResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failure to connect to the server", Toast.LENGTH_LONG).show();

            }
        });
    }
}

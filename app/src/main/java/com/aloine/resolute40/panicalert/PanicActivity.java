package com.aloine.resolute40.panicalert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.aloine.resolute40.AppInstance;
import com.aloine.resolute40.R;
import com.aloine.resolute40.auth.register.network.Client;
import com.aloine.resolute40.dashboard.DashboardActivity;
import com.aloine.resolute40.panicalert.dialog.PanicDialog;
import com.aloine.resolute40.panicalert.model.Keys;
import com.aloine.resolute40.panicalert.model.PanicData;
import com.aloine.resolute40.panicalert.model.PanicDetails;
import com.aloine.resolute40.panicalert.model.PanicResponse;
import com.aloine.resolute40.panicalert.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PanicActivity extends AppCompatActivity {
    private ApiService apiService;
    private PanicDialog panicDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic);
        /*panicDialog = new PanicDialog();
        panicDialog.setCancelable(false);
        panicDialog.show(getSupportFragmentManager(), "my_dialog");
        AppInstance app = AppInstance.getInstance();
        Keys keys = new Keys(app.getClient_token(),app.getSession_token());
        PanicDetails panicDetails = new PanicDetails(app.getUsername(),"A sample panic detail","True");
        PanicData panicData = new PanicData(keys,panicDetails);
        panicNetworkRequest(panicData);*/


    }

    private void panicNetworkRequest(PanicData data) {
        apiService = Client.getClient().create(ApiService.class);
        Call<PanicResponse> call = apiService.postLocation(data);
        call.enqueue(new Callback<PanicResponse>() {
            @Override
            public void onResponse(Call<PanicResponse> call, Response<PanicResponse> response) {
                if (response.body().getResponse() != null) {
                    switch (response.body().getResponse()) {
                        case "success":
                            panicDialog.dismiss();
                            Toast.makeText(PanicActivity.this, "Panic alert has been sent to the server", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PanicActivity.this, DashboardActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            break;
                        case "failed":
                            panicDialog.dismiss();
                            Toast.makeText(PanicActivity.this, "There was an error processing your request. Please try again", Toast.LENGTH_SHORT).show();
                            Intent intenter =  new Intent(PanicActivity.this, DashboardActivity.class);
                            intenter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intenter);
                            break;
                        default:
                            panicDialog.dismiss();
                            Toast.makeText(PanicActivity.this, "No action was carried out", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PanicResponse> call, Throwable t) {
                panicDialog.dismiss();
                Toast.makeText(PanicActivity.this, "Problem connecting to the internet. Please try again later", Toast.LENGTH_SHORT).show();
                Intent intent =  new Intent(PanicActivity.this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });


    }


}

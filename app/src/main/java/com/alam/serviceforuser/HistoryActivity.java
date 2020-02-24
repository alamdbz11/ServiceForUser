package com.alam.serviceforuser;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alam.serviceforuser.adapters.ComplaintAdapter;
import com.alam.serviceforuser.data.DataPreference;
import com.alam.serviceforuser.data.GlobalData;
import com.alam.serviceforuser.models.Complaint;
import com.alam.serviceforuser.utils.Loading;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Complaint> complaints = new ArrayList<>();
    private AppCompatActivity mContext;
    private TextView txtMgs;
    private ImageView imageViewLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        uiInitialization();
    }

    private void uiInitialization() {
        mContext = this;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMembers);
        imageViewLoading = findViewById(R.id.imageViewLoading);
        txtMgs = findViewById(R.id.txtMgs);

    }

    private void getComplaint() {
        complaints.clear();

        Log.e(GlobalData.TAG, "loadTicketList: " + GlobalData.BASE_URL + "&method=getComplaintList&Number1=" + new DataPreference(mContext).getSimOneNumber().substring(1));
         Loading.showLoadingImage(mContext, imageViewLoading);
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GlobalData.BASE_URL + "&method=getComplaintList&Number1=" + new DataPreference(mContext).getSimOneNumber().substring(1), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                  Loading.hideLoadingImage(mContext, imageViewLoading);

                Log.e(GlobalData.TAG, "loadTicketList: " + response.toString());
                try {
                    //JSONObject jsonObject = new JSONObject(response);
                    //JSONObject obj = new JSONObject(result.toString());
                    JSONArray arr = new JSONArray(response);

                    for (int i = 0; i < arr.length(); i++) {

                        JSONObject c = arr.getJSONObject(i);

                        String Id = c.getString("Id");
                        String ComplainType = c.getString("ComplainType");
                        String Name = c.getString("Name");
                        String Complaint = c.getString("Complaint");
                        String EntryDate = c.getString("EntryDate");
                        String Status = c.getString("Status");

                        if (Name.equals("null"))
                            Name = "";
                        complaints.add(new Complaint(Id, ComplainType, Name, Complaint, EntryDate, Status));

                    }
                    if(complaints.size()>0){
                        txtMgs.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        setDataInRecyclerView();
                    }else{
                       txtMgs.setVisibility(View.VISIBLE);
                       recyclerView.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {

                    Log.e(GlobalData.TAG, "JSONException ERROR : " + e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(GlobalData.TAG, "DATA: ERROR 1: " + error.toString());
                error.printStackTrace();
                Loading.hideLoadingImage(mContext, imageViewLoading);
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void setDataInRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ComplaintAdapter adapter = new ComplaintAdapter(this, complaints);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getComplaint();
    }

    public void back(View view) {
        finish();
    }
}

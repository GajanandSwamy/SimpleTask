package com.example.gajanand.simpletask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterUser mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        Log.d("Gajanand", "onCreate: called");


        FetchData fetchData = new FetchData();

        fetchData.execute();
    }

    class FetchData extends AsyncTask<Void, Integer, Integer> {

        ProgressDialog progress = new ProgressDialog(RecyclerViewActivity.this);
        List<UserListResponse> userListResponse;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            try {
                Log.d("Gajanand", "onPreExecute: ");
                progress.setMessage("Fetching data... ");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.show();
            } catch (Exception e) {
                Log.d("Gajanand", "onPreExecute: " + e.toString());
            }

        }

        @Override
        protected Integer doInBackground(Void... voids) {

            Log.d("Gajanand", "doInBackground: in");


            try {

                MyDb myDb = new MyDb(getApplicationContext());
                userListResponse = myDb.fetchAlldata();
            } catch (Exception e) {
                Log.d("Gajanand", "doInBackground: " + e.toString());

            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            Log.d("Gajanand", "onPostExecute: " + integer);

            progress.dismiss();

            for (UserListResponse userListResponse : userListResponse) {

                Log.d("Gajanand", "onPostExecute: " + userListResponse.getName());

            }

            mAdapter = new AdapterUser(userListResponse);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();


        }


    }

}



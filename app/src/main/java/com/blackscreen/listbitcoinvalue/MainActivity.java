package com.blackscreen.listbitcoinvalue;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.blackscreen.listbitcoinvalue.adapter.ListViewBitcoinAdapter;
import com.blackscreen.listbitcoinvalue.connections.JSONConnections;
import com.blackscreen.listbitcoinvalue.model.BitcoinValueModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private ListView mListViewBitcoin;
    private List<BitcoinValueModel> mBitcoinValuesList = new ArrayList<>();
    private ListViewBitcoinAdapter mListBitcoinAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Get elements
        mListViewBitcoin = findViewById(R.id.activity_main_listview_bitcoin);

        //Instance class to get all bitcoin values
        new BitcoinValues().execute();

    }

    private void createAndShowProgressDialog(@Nullable String title, @Nullable String message) {

        dismissProgressDialog();

        //Check if arguments is null
        if (title == null) {
            title = "Loading";
        }

        if (message == null) {
            message = "Doing some stuff";
        }

        mProgressDialog = ProgressDialog.show(this, title, message);

    }

    public void dismissProgressDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

    }

    private void populateBitcoinListView (List<BitcoinValueModel> bitcoinValueModels) {

        mBitcoinValuesList.clear();
        mBitcoinValuesList.addAll(bitcoinValueModels);

        if (mListBitcoinAdapter == null) {
            mListBitcoinAdapter = new ListViewBitcoinAdapter(mBitcoinValuesList, this);
            mListViewBitcoin.setAdapter(mListBitcoinAdapter);
        } else {
            mListBitcoinAdapter.notifyDataSetChanged();
        }

    }

    protected class BitcoinValues extends AsyncTask<Void, Void, List<BitcoinValueModel>> {

        @Override
        protected void onPreExecute() {

            createAndShowProgressDialog("Loading", "Getting Bitcoin Values");
            super.onPreExecute();
        }

        @Override
        protected List<BitcoinValueModel> doInBackground(Void... voids) {

            String[] jsonObjectNames = new String[]{"USD", "GBP", "EUR"};
            List<BitcoinValueModel> bitcoinValueModels = new ArrayList<>();

            //Get json as string
            String jsonAsString = JSONConnections.getJsonAsString("https://api.coindesk.com/v1/bpi/currentprice.json");
            if (jsonAsString != null) {

                //Instance some json objects and get all list of values, based on array above (jsonObjectNames)
                try {
                    JSONObject jsonObjectMain = new JSONObject(jsonAsString);
                    JSONObject jsonBpi = jsonObjectMain.getJSONObject("bpi");

                    //Get values, create model and add on list
                    for (String objectName : jsonObjectNames) {
                        JSONObject object = jsonBpi.getJSONObject(objectName);

                        //Get currency symbol
                        Currency currency = Currency.getInstance(objectName);

                        //Create and add model on list
                        bitcoinValueModels.add(new BitcoinValueModel(currency.getSymbol() + " " + object.getString("rate"),
                                object.getString("description")));

                    }

                    return bitcoinValueModels;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            return null;

        }

        @Override
        protected void onPostExecute(List<BitcoinValueModel> bitcoinValueModels) {
            super.onPostExecute(bitcoinValueModels);

            if (bitcoinValueModels != null) {
                populateBitcoinListView(bitcoinValueModels);
            } else {
                Toast.makeText(MainActivity.this, "it was not possible to recover the values", Toast.LENGTH_SHORT).show();
            }

            dismissProgressDialog();

        }
    }

}

package com.blackscreen.listbitcoinvalue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blackscreen.listbitcoinvalue.R;
import com.blackscreen.listbitcoinvalue.model.BitcoinValueModel;

import java.util.List;

/**
 * Created by Hamon on 08/02/2018.
 */

public class ListViewBitcoinAdapter extends BaseAdapter {

    private List<BitcoinValueModel> mBitcoinValuesList;
    private Context mContext;

    public ListViewBitcoinAdapter(List<BitcoinValueModel> bitcoinValuesList, Context context) {
        this.mBitcoinValuesList = bitcoinValuesList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mBitcoinValuesList.size();
    }

    @Override
    public Object getItem(int i) {
        return mBitcoinValuesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //Inflaite layout
        view  = LayoutInflater.from(mContext).inflate(R.layout.listview_item_bitcoin, null, false);

        //Get Model
        BitcoinValueModel model = mBitcoinValuesList.get(i);

        //Get elements
        TextView description = view.findViewById(R.id.listview_item_bitcoin_description);
        TextView value = view.findViewById(R.id.listview_item_bitcoin_value);

        //Set text into text view's
        description.setText(model.getDescription());
        value.setText(model.getValue());

        return view;
    }
}

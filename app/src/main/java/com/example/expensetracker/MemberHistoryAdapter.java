package com.example.expensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class MemberHistoryAdapter extends BaseAdapter {
    private final ArrayList mData;
    public MemberHistoryAdapter(Map<String, String> map) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
            }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String,String> getItem(int position) {
        return (Map.Entry<String, String>) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       final View inResult;
       if (convertView == null){
           inResult = LayoutInflater.from(parent.getContext()).inflate(R.layout.memberdata,parent,false);
       }else{
           inResult = convertView;
       }
        Map.Entry<String,String> item = getItem(position);
        ((TextView) inResult.findViewById(R.id.no)).setText(item.getKey());
        ((TextView) inResult.findViewById(R.id.digit)).setText(item.getValue());
        return inResult;
    }
}

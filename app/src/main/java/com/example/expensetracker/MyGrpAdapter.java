package com.example.expensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class MyGrpAdapter extends BaseAdapter {
    private final ArrayList DATA;
    public MyGrpAdapter(Map<String, String> keyVal) {
        DATA = new ArrayList();
        DATA.addAll(keyVal.entrySet());
    }

    @Override
    public int getCount() {
        return DATA.size();
    }

    @Override
    public Map.Entry<String,String> getItem(int i) {
        return (Map.Entry<String, String>) DATA.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View grpResult;
        if (view == null){
            grpResult = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grplistview,viewGroup,false);

        }else{
            grpResult = view;
        }
        Map.Entry<String,String> iTem = getItem(i);
        ((TextView) grpResult.findViewById(R.id.gid)).setText(iTem.getKey());
        ((TextView) grpResult.findViewById(R.id.gName)).setText(iTem.getValue());
        return grpResult;
    }
}

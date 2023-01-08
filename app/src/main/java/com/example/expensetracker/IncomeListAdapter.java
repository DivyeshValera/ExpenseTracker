package com.example.expensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class IncomeListAdapter extends BaseAdapter
{

    private final ArrayList data;
    public IncomeListAdapter(ArrayList<Income> incomeArrayList)
    {
        data = new ArrayList();
        data.addAll(incomeArrayList);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ArrayList<String> getItem(int i) {
        return (ArrayList<String>) data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final  View vi;
        if(view == null)
        {
            vi = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.income_view,viewGroup,false);
        }
        else
        {
            vi = view;
        }
        ArrayList<String> items = getItem(i);
        ((TextView)vi.findViewById(R.id.tvincome)).setText(items.get(i)+"/n");
        return vi;
    }



}

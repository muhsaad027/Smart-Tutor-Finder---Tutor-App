package com.example.saadiqbal.tutorsideapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    private ArrayList<String> fullList;
    private ArrayList<String> mOriginalValues;

    private  HashMap<String,String> progHashMap;

    private ArrayFilter mFilter;
    private  int tv_Program;
    private  int tv_Course;
    public AutoCompleteAdapter(Context context, int resource, int tv_Course,int tv_Program, List<String> objects,HashMap<String,String> obj) {

        super(context, resource, tv_Course, objects);
        progHashMap = obj;
        this.tv_Course = tv_Course;
        fullList = (ArrayList<String>) objects;
        mOriginalValues = new ArrayList<String>(fullList);
        this.tv_Program = tv_Program;
    }

    @Override
    public int getCount() {
        return fullList.size();
    }

    @Override
    public String getItem(int position) {
        return fullList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


      convertView = super.getView(position,convertView,parent);
           TextView    tv = (TextView) convertView.findViewById(tv_Program);
        TextView    courseTv = (TextView) convertView.findViewById(tv_Course);
       // tv.setTypeface(tv.getTypeface(), Typeface.ITALIC);
        courseTv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setText(""+progHashMap.get(fullList.get(position)));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }


    private class ArrayFilter extends Filter {
        private Object lock;

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {

            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (lock) {
                    mOriginalValues = new ArrayList<String>(fullList);

                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    ArrayList<String> list = new ArrayList<String>(mOriginalValues);
                    results.values = list;
                    results.count = list.size();
                }
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                ArrayList<String> values = mOriginalValues;
                int count = values.size();

                ArrayList<String> newValues = new ArrayList<String>(count);

                for (int i = 0; i < count; i++) {
                    String item = values.get(i);
                    if (item.toLowerCase().contains(prefixString)) {
                        newValues.add(item);
                    }

                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        if(results.values!=null){
        fullList = (ArrayList<String>) results.values;
        }else{
            fullList = new ArrayList<String>();
        }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

}

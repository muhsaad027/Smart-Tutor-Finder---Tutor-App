package com.example.saadiqbal.tutorsideapplication;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class AdapterTuitionCancel extends RecyclerView.Adapter<AdapterTuitionCancel.MyViewHolder> {

    private ArrayList<TutorCurrentTuitionsModel> arrayList ;
    Context acontext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
        public TextView mTextView;
        public TextView stdname,stdid,stddphone,stdstatus,reqtime;

        public MyViewHolder(View v){
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view_order_cancel);
            stdname= (TextView) v.findViewById(R.id.StdName);
            stdid = (TextView) v.findViewById(R.id.StdReqID);
            stddphone = (TextView) v.findViewById(R.id.StdPhone);
            stdstatus = (TextView) v.findViewById(R.id.StdStatus);
            reqtime= (TextView) v.findViewById(R.id.StdReqTime);

        }


    }

    public AdapterTuitionCancel(Context context, ArrayList<TutorCurrentTuitionsModel> arrayList) {
        this.arrayList = arrayList;
        acontext = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_order_cancel, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){

        TutorCurrentTuitionsModel current = arrayList.get(position);
        Log.v("Adcancel","ID : "+ current.getStudentID()+" NAME : "+current.getStudentName()+
                "Phone : "+current.getStudentPhone()+" Status : "+current.getStudentStatus()+" Request : "+current.getStduentRequesTime());
        holder.stdname.setText(current.getStudentName());
        holder.stdid.setText(current.getStudentID());
        holder.stddphone.setText(current.getStudentPhone());
        holder.stdstatus.setText(current.getStudentStatus());
        holder.reqtime.setText(current.getStduentRequesTime());
        //holder.tv_cancelby.setText(""+current.getCanceledBy());
        //holder.tv_jobcreationtime.setText(current.getJobCreationTime());
    }

    @Override
    public int getItemCount() { return arrayList.size(); }

}

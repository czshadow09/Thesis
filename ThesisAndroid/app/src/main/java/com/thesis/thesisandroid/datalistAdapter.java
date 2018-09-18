package com.thesis.thesisandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class datalistAdapter extends ArrayAdapter<data> {
    private static final String TAG = "datalistAdapter";
    private Context mContext;
    int mResource;

    /**
     *
     * @param context
     * @param resource
     * @param objects
     */
    public datalistAdapter(Context context, int resource, ArrayList<data> objects){
    super(context, resource, objects);
    mContext = context;
    mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
            //get exercise data //
        String exername = getItem(position).getexername();
        String power = getItem(position).getpower();
        String reps = getItem(position).getreps();
        String weight = getItem(position).getweight();
        String date = getItem(position).getdate();
        String onerepmax = getItem(position).getOnerepmax();

       data data = new data(exername,power,reps,weight,date,onerepmax);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView TVexername = (TextView)convertView.findViewById(R.id.reportexername);
        TextView TVpower= (TextView)convertView.findViewById(R.id.reportavrpower);
        TextView TVreps = (TextView)convertView.findViewById(R.id.reportnumreps);
        TextView TVweight = (TextView)convertView.findViewById(R.id.reportweight);
        TextView TVdate = (TextView)convertView.findViewById(R.id.date);
        TextView TVrepmax = (TextView)convertView.findViewById(R.id.datarepmax);

        TVexername.setText(exername);
        TVpower.setText(power);
        TVreps.setText(reps);
        TVweight.setText(weight);
        TVdate.setText(date);
        TVrepmax.setText(onerepmax);
        return convertView;

    }


}

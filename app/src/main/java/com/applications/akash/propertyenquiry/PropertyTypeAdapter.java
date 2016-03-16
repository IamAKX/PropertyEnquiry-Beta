package com.applications.akash.propertyenquiry;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Akash on 2/23/2016.
 */
public class PropertyTypeAdapter extends RecyclerView.Adapter<PropertyTypeAdapter.PropertyViewHolder> {

    private ArrayList<PropertyType> items;
    Context context;
    public PropertyTypeAdapter(ArrayList<PropertyType> items) {
        this.items = items;
    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card,parent,false);
        PropertyViewHolder propertyViewHolder = new PropertyViewHolder(v);
        return propertyViewHolder;
    }

    @Override
    public void onBindViewHolder(PropertyViewHolder holder, int position) {
        holder.tv.setText(items.get(position).getImage_name());
        holder.iv.setImageResource(items.get(position).getImage_id());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class PropertyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView iv;
        public PropertyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView)itemView.findViewById(R.id.textViewBanner);
            iv = (ImageView)itemView.findViewById(R.id.imageViewBanner);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   swapTheFragments(getAdapterPosition());
                }
            });
        }




        public void swapTheFragments(int adapterPosition) {

            Fragment myFragment = null;


            switch(adapterPosition)
            {
                case 0:
                    myFragment = new BuyFragment();
                     HomeActivity.ha.getSupportActionBar().setTitle("Buy");
                     HomeActivity.ha.recyclerView.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    myFragment = new RentFragment();
                    HomeActivity.ha.getSupportActionBar().setTitle("Rent");
                    HomeActivity.ha.recyclerView.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    myFragment = new ResaleFragment();
                    HomeActivity.ha.getSupportActionBar().setTitle("Resale");
                    HomeActivity.ha.recyclerView.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                    myFragment = new HostelFragment();
                    HomeActivity.ha.getSupportActionBar().setTitle("Hostel");
                    HomeActivity.ha.recyclerView.setVisibility(View.INVISIBLE);
                    break;
                case 4:
                    myFragment = new ProjectFragment();
                    HomeActivity.ha.getSupportActionBar().setTitle("Project");
                    HomeActivity.ha.recyclerView.setVisibility(View.INVISIBLE);
                    break;
                case 5:
                    myFragment = new HomeLoanFragment();
                    HomeActivity.ha.getSupportActionBar().setTitle("Home Loan");
                    HomeActivity.ha.recyclerView.setVisibility(View.INVISIBLE);
                    break;
                default:
                    break;
            }


            FragmentManager fm =HomeActivity.fm;

            fm.beginTransaction()
                    .replace(R.id.container,myFragment)
                    .commit();

        }
    }
}

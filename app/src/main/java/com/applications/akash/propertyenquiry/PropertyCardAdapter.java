package com.applications.akash.propertyenquiry;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Akash on 2/29/2016.
 */
public class PropertyCardAdapter extends RecyclerView.Adapter<PropertyCardAdapter.PViewHolder> {

    private ArrayList<PropertyCard> items;
    private static Context context;

    public PropertyCardAdapter(ArrayList<PropertyCard> items) {
        this.items = items;

    }

    public PropertyCardAdapter(Context c) {
        context=c;
    }

    @Override
    public PViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_card, parent, false);
        PViewHolder propertyViewHolder = new PViewHolder(v);
        return propertyViewHolder;
    }

    @Override
    public void onBindViewHolder(PViewHolder holder, int position) {
        int loader = R.drawable.loader;

        ImageLoader imgLoader = new ImageLoader(context);

        imgLoader.DisplayImage(items.get(position).getImg_url(), loader, holder.img);
        holder.name.setText(items.get(position).getName());
        holder.builder.setText(items.get(position).getBuilder());
        holder.address.setText(items.get(position).getAddress());
        holder.cost.setText(items.get(position).getCost());
        holder.possession.setText(items.get(position).getPossession());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PViewHolder extends RecyclerView.ViewHolder {

        ImageView img, phone;
        TextView possession, name, builder, address, cost;

        public PViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.property_image);
            phone = (ImageView) itemView.findViewById(R.id.phone);
            name = (TextView) itemView.findViewById(R.id.name);
            builder = (TextView) itemView.findViewById(R.id.builder);
            address = (TextView) itemView.findViewById(R.id.address);
            cost = (TextView) itemView.findViewById(R.id.price);
            possession = (TextView) itemView.findViewById(R.id.possession);
            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + items.get(getAdapterPosition()).getPhone()));
                    if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    v.getContext().startActivity(callIntent);
                }
            });


        }
    }
}

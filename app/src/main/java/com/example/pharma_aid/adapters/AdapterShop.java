package com.example.pharma_aid.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharma_aid.R;
import com.example.pharma_aid.activites.ShopDetails;
import com.example.pharma_aid.models.ModelShop;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterShop extends RecyclerView.Adapter<AdapterShop.HolderShop>{
    private Context context;
    public ArrayList<ModelShop> shopsLists;

    public AdapterShop(Context context, ArrayList<ModelShop> shopsLists) {
        this.context = context;
        this.shopsLists = shopsLists;
    }

    @NonNull
    @Override
    public HolderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_shop,parent,false);
        return new HolderShop(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderShop holder, int position) {
        //get data
        ModelShop modelShop=shopsLists.get(position);
        String accountType=modelShop.getAccountType();
        String address=modelShop.getAddress();
        String city=modelShop.getCity();
        String country=modelShop.getCountry();
        String deliveryFee=modelShop.getDeliveryFee();
        String email=modelShop.getEmail();
        String latitude=modelShop.getLatitude();
        String longitude=modelShop.getLongitude();
        String online=modelShop.getOnline();
        String name=modelShop.getName();
        String phone=modelShop.getPhone();
        final String uid=modelShop.getUid();
        String timestamp=modelShop.getTimestamp();
        String shopOpen=modelShop.getShopOpen();
        String state=modelShop.getState();
        String profileImage=modelShop.getProfileImage();
        String shopName=modelShop.getShopName();

        //set data
        holder.shopNameTv.setText(shopName);
        holder.phoneTv.setText(phone);
        holder.addressTv.setText(address);
        if(online.equals("true")){
            //shop owner is on online
            holder.onlineIv.setVisibility(View.VISIBLE);
        }
        else {
            //shop owner is on offline
            holder.onlineIv.setVisibility(View.GONE);
        }
        //check if the shop is open
        if(shopOpen.equals("true")){
            //shop open
            holder.shopClosedTv.setVisibility(View.GONE);
        }
        else{
            //shop closed
            holder.shopClosedTv.setVisibility(View.VISIBLE);
        }
        try {
            Picasso.get().load(profileImage).placeholder(R.drawable.ic_store_gray).into(holder.shopIv);

        }
        catch (Exception e){
            holder.shopIv.setImageResource(R.drawable.ic_store_gray);
        }
        //handle click listener, show shop details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ShopDetails.class);
                intent.putExtra("shopUid",uid);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return shopsLists.size(); //return number of records
    }

    //view holder
    class HolderShop extends RecyclerView.ViewHolder{
        //ui views of row_shop.xml
        private ImageView shopIv,onlineIv;
        private TextView shopClosedTv,shopNameTv,phoneTv,addressTv;
        private RatingBar ratingBar;

        public HolderShop(@NonNull View itemView) {
            super(itemView);

            //initialize ui views
            shopIv=itemView.findViewById(R.id.shopIv);
            onlineIv=itemView.findViewById(R.id.onlineIv);
            shopClosedTv=itemView.findViewById(R.id.shopClosedTv);
            shopNameTv=itemView.findViewById(R.id.shopNameTv);
            phoneTv=itemView.findViewById(R.id.phoneTv);
            addressTv=itemView.findViewById(R.id.addressTv);
            ratingBar=itemView.findViewById(R.id.ratingBar);

        }
    }
}

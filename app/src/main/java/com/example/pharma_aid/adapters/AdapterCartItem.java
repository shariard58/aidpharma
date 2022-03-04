package com.example.pharma_aid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharma_aid.R;
import com.example.pharma_aid.activites.ShopDetails;
import com.example.pharma_aid.models.ModelCartItem;
import com.example.pharma_aid.models.ModelProduct;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class AdapterCartItem extends RecyclerView.Adapter<AdapterCartItem.HolderCartItem>{

    private Context context;
    private ArrayList<ModelCartItem> cartItems;

    public AdapterCartItem(Context context, ArrayList<ModelCartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public HolderCartItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row_cartitem.xml
        View view= LayoutInflater.from(context).inflate(R.layout.row_cartitem, parent,false);
        return new HolderCartItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderCartItem holder, final int position) {
        //get data
        ModelCartItem modelCartItem= cartItems.get(position);
        final String id= modelCartItem.getId();
        String getpId= modelCartItem.getpId();
        String title= modelCartItem.getName();
        final String cost= modelCartItem.getCost();
        String price= modelCartItem.getPrice();
        String quantity= modelCartItem.getQuantity();

        //set data
        holder.itemTitleTv.setText(""+title);
        holder.itemPriceTv.setText(""+cost);
        holder.itemQuantityTv.setText("["+quantity+"]");
        holder.itemPriceEachTv.setText(""+price);
        //holder remove click Listener, delete item from cart
        holder.itemRemoveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //will create table if not exist, but in that case will must exist
                EasyDB easyDB= EasyDB.init(context,"ITEMS_DB")
                        .setTableName("ITEMS_TABLE")
                        .addColumn(new Column("Item_Id", new String[]{"text", "unique"}))
                        .addColumn(new Column("Item_PID", new String[]{"text", "not null"}))
                        .addColumn(new Column("Item_Name", new String[]{"text", "not null"}))
                        .addColumn(new Column("Item_Price_Each", new String[]{"text", "not null"}))
                        .addColumn(new Column("Item_Price", new String[]{"text", "not null"}))
                        .addColumn(new Column("Item_Quantity", new String[]{"text", "not null"}))
                        .doneTableColumn();
                easyDB.deleteRow(1, id); //column number 1 is item id
                Toast.makeText(context,"Remove from cart...",Toast.LENGTH_SHORT).show();
                //refresh list
                cartItems.remove(position);
                notifyItemChanged(position);
                notifyDataSetChanged();

                double tx= Double.parseDouble((((ShopDetails)context).allTotalPriceTv.getText().toString().trim().replace("৳","")));
                double totalPrice= tx - Double.parseDouble(cost.replace("৳",""));
                double deliveryFee= Double.parseDouble((((ShopDetails)context).deliveryFee.replace("৳","")));
                double sTotalPrice= Double.parseDouble(String.format("%.2f",totalPrice)) - Double.parseDouble(String.format("%.2f",deliveryFee));
                ((ShopDetails)context).allTotalPrice=0.00;
                ((ShopDetails)context).sTotalTv.setText("৳"+String.format("%.2f",sTotalPrice));
                ((ShopDetails)context).allTotalPriceTv.setText("৳"+String.format("%.2f",Double.parseDouble(String.format("%.2f",totalPrice))));

                //after removing the item from the cart, update the cart count
                ((ShopDetails)context).cartCount();
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    class HolderCartItem extends RecyclerView.ViewHolder{

        //ui views of row_cartitem.xml
        private TextView itemTitleTv,itemPriceTv,itemPriceEachTv,itemQuantityTv,itemRemoveTv;

        public HolderCartItem(@NonNull View itemView) {
            super(itemView);

            //itialize views
            itemTitleTv= itemView.findViewById(R.id.itemTitleTv);
            itemPriceTv= itemView.findViewById(R.id.itemPriceTv);
            itemPriceEachTv= itemView.findViewById(R.id.itemPriceEachTv);
            itemQuantityTv= itemView.findViewById(R.id.itemQuantityTv);
            itemRemoveTv= itemView.findViewById(R.id.itemRemoveTv);
        }
    }
}

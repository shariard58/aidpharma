package com.example.pharma_aid;

import android.widget.Filter;

import com.example.pharma_aid.adapters.AdapterProductSeller;
import com.example.pharma_aid.adapters.AdapterProductUser;
import com.example.pharma_aid.models.ModelProduct;

import java.util.ArrayList;

public class FilterProductUser extends Filter {

    private AdapterProductUser adapter;
    private ArrayList<ModelProduct> filterList;

    public FilterProductUser(AdapterProductUser adapter, ArrayList<ModelProduct> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //validate data for search query

        if (constraint!=null && constraint.length()>0)
        {
            //search filed not empty searching something perform search
            constraint=constraint.toString().toUpperCase();
            //store filtered list

            ArrayList<ModelProduct> filteredModels=new ArrayList<>();
            for (int i=0;i<filterList.size();i++)
            {
                //check search by title and category
                if (filterList.get(i).getProductTitle().toUpperCase().contains(constraint)||
                        filterList.get(i).getProductCategory().toUpperCase().contains(constraint))
                {
                    filteredModels.add(filterList.get(i));
                }
            }

            results.count=filteredModels.size();
            results.values=filteredModels;
        }

        else {

            //searcxh filed  empty , not searching,  sreturn original/all/complete list

            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.productsList=(ArrayList<ModelProduct>) results.values;
        //refresh adapter

        adapter.notifyDataSetChanged();

    }
}

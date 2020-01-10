package com.nabin.foodmandu.bll;

import android.widget.ListView;

import com.nabin.foodmandu.api.ItemApi;
import com.nabin.foodmandu.api.UsersAPI;
import com.nabin.foodmandu.model.Item;
import com.nabin.foodmandu.serverresponse.SignUpResponse;
import com.nabin.foodmandu.strictmode.strictmodeclass;
import com.nabin.foodmandu.url.Url;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class ItemBll {

   private List<Item> lsItemListView;

   public List<Item> getAllItems(){
       ItemApi itemApi= Url.getInstance().create(ItemApi.class);
     Call<List<Item>> listCall = itemApi.getALlItems();
       strictmodeclass.StrictMode();
       try {
         return  lsItemListView= listCall.execute().body();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return lsItemListView;


   }
}

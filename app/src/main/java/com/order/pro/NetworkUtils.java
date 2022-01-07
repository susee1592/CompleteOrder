package com.order.pro;

import android.content.Context;
import android.util.ArrayMap;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class NetworkUtils {
    public static Retrofit getRetrofit(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return new Retrofit.Builder()
                .baseUrl("http://103.197.121.76/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public static void loadData(OrderDetails detail){
        ApiService service = getRetrofit().create(ApiService.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("INF_SHORT_CODE", "CONTCTLESS");

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        Call<JsonObject> callAsync = service.getOrders(body);

        callAsync.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject data = response.body();
                try {
                    String jsonString=data.get("d").getAsString();
                    jsonString=jsonString.replaceAll("\\\"","\"");
                    List<Order> orders = Arrays.asList(new GsonBuilder().create().fromJson(jsonString, Order[].class));
                    detail.showOrders(orders);
                } catch (Exception e) {
                    detail.showOrders(null);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                System.out.println(throwable);
                detail.showOrders(null);
            }
        });
    }

    public static void updateData(String ORDER_NUMBER, OrderDetails detail, Context context){
        ApiService service = getRetrofit().create(ApiService.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("ORDER_NUMBER", ORDER_NUMBER);
        jsonParams.put("COMPLETED_BY", "ADMIN");
        jsonParams.put("INF_SHORT_CODE", "CONTCTLESS");

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
        Call<JsonObject> callAsync = service.updateOrder(body);
        callAsync.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject data = response.body();
                try {
                    String jsonString=data.get("d").getAsString();
                    jsonString=jsonString.replaceAll("\\\"","\"");
                    Gson gson=new GsonBuilder().create();
                    JsonObject res=gson.fromJson(jsonString,JsonObject.class);
                    String level=res.get("Message").getAsJsonObject().get("Level").getAsString();
                    if (level!=null && level.equalsIgnoreCase("3")) {
                        Toast.makeText(context,"Completed Successfully!",Toast.LENGTH_LONG).show();
                        List<Order> orders = Arrays.asList(gson.fromJson(res.get("orderDetails").getAsJsonArray().toString(), Order[].class));
                        detail.showOrders(orders);
                    }
                } catch (Exception e) {
                    detail.showOrders(null);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                System.out.println(throwable);
                Toast.makeText(context,"Network Issue! Please Try Again",Toast.LENGTH_LONG).show();
            }
        });
    }

    interface ApiService{
        @POST("/iDineSmart/iDinePOSService.asmx/GetContactLessOrders")
        public Call<JsonObject> getOrders(@Body RequestBody params);

        @POST("/iDineSmart/iDinePOSService.asmx/UpdateContactLessOrderStatus")
        public Call<JsonObject> updateOrder(@Body RequestBody params);
    }
}

package com.order.pro;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements OrderDetails {
    SwipeRefreshLayout swipeRefreshLayout;
    TextView status;
    RecyclerView recyclerView;
    ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            // network available
            refresh();
        }

        @Override
        public void onLost(Network network) {
            // network unavailable
            showStatus("Network not Available!");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        status = findViewById(R.id.status);
        recyclerView = findViewById(R.id.list);

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            NetworkRequest request = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build();
            connectivityManager.registerNetworkCallback(request, networkCallback);
        }

        swipeRefreshLayout.setOnRefreshListener(() -> refresh());
        if (!isNetworkConnected()){
            showStatus("Network not Available!");
        }
    }

    void refresh() {
        runOnUiThread(() -> {
            swipeRefreshLayout.setRefreshing(true);
            NetworkUtils.loadData(this);
        });
    }

    void showStatus(String sta) {
        runOnUiThread(() -> {
            if (status!=null) {
                status.setText(sta);
                status.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showOrders(List<Order> orders) {
        swipeRefreshLayout.setRefreshing(true);
        if (orders != null && orders.size() != 0) {
            status.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            OrderAdapter adapter = new OrderAdapter(this, orders, this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            showStatus("No data Found!");
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected())) {
            //Do something
            return false;
        }
        return true;
    }
}
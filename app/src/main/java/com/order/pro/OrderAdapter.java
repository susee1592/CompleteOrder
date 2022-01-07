package com.order.pro;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private final List<Order> mData;
    private final LayoutInflater mInflater;
    private final OrderDetails mDetail;

    OrderAdapter(Context context, List<Order> data, OrderDetails detail) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mDetail = detail;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = mData.get(position);
        holder.name.setText(order.getCUST_NAME());
        holder.number.setText(order.getCONT_DETAILS());
        holder.id.setText("" + order.getORDER_NUMBER());
        if (order.getORD_TYPE().equalsIgnoreCase("P")) {
            holder.type.setText("Take Away");
        } else {
            holder.type.setText("Dine In");
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    Order getItem(int id) {
        return mData.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView complete;
        TextView name;
        TextView number;
        TextView type;
        TextView id;

        ViewHolder(View itemView) {
            super(itemView);
            complete = itemView.findViewById(R.id.complete);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            type = itemView.findViewById(R.id.type);
            id = itemView.findViewById(R.id.order_id);
            complete.setOnClickListener(v -> {
                String num = "" + getItem(getAdapterPosition()).getORDER_NUMBER();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(itemView.getContext());
                builder1.setMessage("Do you wish to mark " + num + " as complete");
                builder1.setCancelable(true);
                builder1.setPositiveButton("YES",
                        (dialog, id) -> {
                            NetworkUtils.updateData(num, mDetail, itemView.getContext());
                            dialog.cancel();
                        });
                builder1.setNegativeButton("NO",
                        (dialog, id) -> dialog.cancel());

                AlertDialog alert11 = builder1.create();
                alert11.show();
            });
        }
    }
}
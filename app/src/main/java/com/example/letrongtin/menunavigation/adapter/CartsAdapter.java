package com.example.letrongtin.menunavigation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.letrongtin.menunavigation.R;
import com.example.letrongtin.menunavigation.activity.CartActivity;
import com.example.letrongtin.menunavigation.model.Cart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Le Trong Tin on 11/28/2017.
 */

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.MyViewHolder>{

    ArrayList<Cart> carts;
    Context context;

    public CartsAdapter(ArrayList<Cart> carts, Context context) {
        this.carts = carts;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Cart cart = carts.get(position);
        holder.name.setText(cart.getName());
        final DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.price.setText(decimalFormat.format(cart.getPrice()) + " VND");
        Picasso.with(context).load(cart.getImage())
                .fit()
                .placeholder(R.drawable.noimage)
                .error(R.drawable.errorimage)
                .into(holder.image);
        holder.btnValues.setText(cart.getQuantity()+"");

        int quantity = Integer.parseInt(holder.btnValues.getText().toString());
        if (quantity > 10){
            holder.btnPlus.setVisibility(View.INVISIBLE);
        } else if (quantity <= 1){
            holder.btnMinus.setVisibility(View.INVISIBLE);
        } else if (quantity>1){
            holder.btnPlus.setVisibility(View.VISIBLE);
            holder.btnMinus.setVisibility(View.VISIBLE);
        }

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newQuantity = cart.getQuantity() + 1;
                int currentQuantity = cart.getQuantity();
                long currenPrice = cart.getPrice();
                long newPrice = (currenPrice / currentQuantity) * newQuantity;

                cart.setQuantity(newQuantity);
                cart.setPrice(newPrice);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                holder.price.setText(decimalFormat.format(cart.getPrice()) + " VND");
                CartActivity.TotalMoney();
                if (newQuantity > 9){
                    holder.btnPlus.setVisibility(View.INVISIBLE);
                    holder.btnMinus.setVisibility(View.VISIBLE);
                    holder.btnValues.setText(String.valueOf(newQuantity));
                } else {
                    holder.btnPlus.setVisibility(View.VISIBLE);
                    holder.btnMinus.setVisibility(View.VISIBLE);
                    holder.btnValues.setText(String.valueOf(newQuantity));
                }
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newQuantity = cart.getQuantity() - 1;
                int currentQuantity = cart.getQuantity();
                long currenPrice = cart.getPrice();
                long newPrice = (currenPrice / currentQuantity) * newQuantity;

                cart.setQuantity(newQuantity);
                cart.setPrice(newPrice);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                holder.price.setText(decimalFormat.format(cart.getPrice()) + " VND");
                CartActivity.TotalMoney();
                if (newQuantity < 2){
                    holder.btnPlus.setVisibility(View.VISIBLE);
                    holder.btnMinus.setVisibility(View.INVISIBLE);
                    holder.btnValues.setText(String.valueOf(newQuantity));
                } else {
                    holder.btnPlus.setVisibility(View.VISIBLE);
                    holder.btnMinus.setVisibility(View.VISIBLE);
                    holder.btnValues.setText(String.valueOf(newQuantity));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public void removeItem(int position) {
        carts.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Cart cart, int position) {
        carts.add(position, cart);
        // notify item added by position
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView name, price;
        public Button btnMinus, btnValues, btnPlus;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnValues = itemView.findViewById(R.id.btnValues);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }
}

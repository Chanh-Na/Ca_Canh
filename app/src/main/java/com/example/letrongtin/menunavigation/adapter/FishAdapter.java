package com.example.letrongtin.menunavigation.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letrongtin.menunavigation.R;
import com.example.letrongtin.menunavigation.activity.MainActivity;
import com.example.letrongtin.menunavigation.model.Cart;
import com.example.letrongtin.menunavigation.model.Fish;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Le Trong Tin on 11/7/2017.
 */

public class FishAdapter extends RecyclerView.Adapter<FishAdapter.MyViewHolder>{

    ArrayList<Fish> fishes;
    Context context;

    public FishAdapter(ArrayList<Fish> fishes, Context context) {
        this.fishes = fishes;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Fish fish = fishes.get(position);
        holder.name.setText(fish.getName());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.price.setText(decimalFormat.format(fish.getPrice()) + " VND");
        Picasso.with(context).load(fish.getUrlImage())
                .fit()
                .placeholder(R.drawable.noimage)
                .error(R.drawable.errorimage)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return fishes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name, price;
        public ImageView thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            thumbnail = itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowDialog(fishes.get(getPosition()));
                }
            });
        }
    }

    private void ShowDialog(final Fish fish){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.fish_detail);

        TextView name = dialog.findViewById(R.id.name);
        TextView price = dialog.findViewById(R.id.price);
        ImageView image = dialog.findViewById(R.id.image);
        Button btnBuy = dialog.findViewById(R.id.btnBuy);
        final Spinner spinner = dialog.findViewById(R.id.spinner);

        TextView origin = dialog.findViewById(R.id.origin);
        TextView size = dialog.findViewById(R.id.size);
        TextView temperature = dialog.findViewById(R.id.temperature);
        TextView pH = dialog.findViewById(R.id.pH);
        TextView relation = dialog.findViewById(R.id.relation);
        TextView care = dialog.findViewById(R.id.care);
        TextView food = dialog.findViewById(R.id.food);

        name.setText(fish.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        price.setText(decimalFormat.format(fish.getPrice()) + " VND");
        Picasso.with(context).load(fish.getUrlImage())
                .fit()
                .placeholder(R.drawable.noimage)
                .error(R.drawable.errorimage)
                .into(image);

        origin.setText("Nguồn gốc: " + fish.getOrigin());
        size.setText("Kích thước: " + fish.getSize() + "cm");
        temperature.setText("Nhiệt độ nước: " + fish.getTemperature() + " độ C");
        pH.setText("Độ pH: " + fish.getPh());
        relation.setText("Quan hệ: " + fish.getRelation());
        care.setText("Chăm sóc: " + fish.getCare());
        food.setText("Thức ăn: " + fish.getFood());

        Integer[] number = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(context, android.R.layout.simple_spinner_dropdown_item, number);
        spinner.setAdapter(arrayAdapter);

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int quantity = Integer.parseInt(spinner.getSelectedItem().toString());
                if (MainActivity.carts.size() >0 ){
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.carts.size(); i++){
                        if (MainActivity.carts.get(i).getName().equals( fish.getName())){
                            MainActivity.carts.get(i).setQuantity(MainActivity.carts.get(i).getQuantity() + quantity) ;
                            if (MainActivity.carts.get(i).getQuantity() > 10){
                                MainActivity.carts.get(i).setQuantity(10) ;
                            }
                            MainActivity.carts.get(i).setPrice(fish.getPrice() * MainActivity.carts.get(i).getQuantity());
                            exists = true;
                        }
                    }

                    if (!exists){
                        long newPrice = quantity * fish.getPrice();
                        MainActivity.carts.add(new Cart(null, fish.getName(), newPrice, fish.getUrlImage(), quantity));
                    }

                } else {
                    long newPrice = quantity * fish.getPrice();
                    MainActivity.carts.add(new Cart(null, fish.getName(), newPrice, fish.getUrlImage(), quantity));
                }
                Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                dialog.dismiss();


            }
        });

        Window window = dialog.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
}




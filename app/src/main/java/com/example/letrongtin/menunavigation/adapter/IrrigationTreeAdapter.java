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
import com.example.letrongtin.menunavigation.model.IrrigationTree;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Le Trong Tin on 11/11/2017.
 */

public class IrrigationTreeAdapter extends RecyclerView.Adapter<IrrigationTreeAdapter.MyViewHolder>{

    ArrayList<IrrigationTree> irrigationTrees;
    Context context;

    public IrrigationTreeAdapter(ArrayList<IrrigationTree> irrigationTrees, Context context) {
        this.irrigationTrees = irrigationTrees;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        IrrigationTree irrigationTree = irrigationTrees.get(position);
        holder.name.setText(irrigationTree.getName());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.price.setText(decimalFormat.format(irrigationTree.getPrice()) + " VND");
        Picasso.with(context).load(irrigationTree.getUrlImage())
                .fit()
                .placeholder(R.drawable.noimage)
                .error(R.drawable.errorimage)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return irrigationTrees.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

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
                    ShowDialog(irrigationTrees.get(getPosition()));
                }
            });
        }
    }

    private void ShowDialog(final IrrigationTree irrigationTree){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.irrigationtree_detail);

        TextView name = dialog.findViewById(R.id.name);
        TextView price = dialog.findViewById(R.id.price);
        ImageView image = dialog.findViewById(R.id.image);
        Button btnBuy = dialog.findViewById(R.id.btnBuy);
        final Spinner spinner = dialog.findViewById(R.id.spinner);

        name.setText(irrigationTree.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        price.setText(decimalFormat.format(irrigationTree.getPrice()) + " VND");
        Picasso.with(context).load(irrigationTree.getUrlImage())
                .fit()
                .placeholder(R.drawable.noimage)
                .error(R.drawable.errorimage)
                .into(image);

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
                        if (MainActivity.carts.get(i).getName().equals( irrigationTree.getName())){
                            MainActivity.carts.get(i).setQuantity(MainActivity.carts.get(i).getQuantity() + quantity) ;
                            if (MainActivity.carts.get(i).getQuantity() > 10){
                                MainActivity.carts.get(i).setQuantity(10) ;
                            }
                            MainActivity.carts.get(i).setPrice(irrigationTree.getPrice() * MainActivity.carts.get(i).getQuantity());
                            exists = true;
                        }
                    }

                    if (!exists){
                        long newPrice = quantity * irrigationTree.getPrice();
                        MainActivity.carts.add(new Cart(null, irrigationTree.getName(), newPrice, irrigationTree.getUrlImage(), quantity));
                    }

                } else {
                    long newPrice = quantity * irrigationTree.getPrice();
                    MainActivity.carts.add(new Cart(null, irrigationTree.getName(), newPrice, irrigationTree.getUrlImage(), quantity));
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

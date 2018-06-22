package com.example.letrongtin.menunavigation.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letrongtin.menunavigation.R;
import com.example.letrongtin.menunavigation.adapter.CartsAdapter;
import com.example.letrongtin.menunavigation.utility.RecyclerItemTouchHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    RecyclerView recyclerViewCart;
    CartsAdapter cartAdapter;

    TextView notification;
    static TextView total;
    Button btnPay, btnContinueBuy;
    Toolbar toolbarCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //listViewCart = (ListView) findViewById(R.id.listviewCart);
        recyclerViewCart = (RecyclerView) findViewById(R.id.recycler_view_cart);
        notification = (TextView) findViewById(R.id.notification);
        total = (TextView) findViewById(R.id.total);
        btnPay = (Button) findViewById(R.id.btnPay);
        btnContinueBuy = (Button) findViewById(R.id.btnContinueBuy);
        toolbarCart = (Toolbar) findViewById(R.id.toolbarCart);

        if (MainActivity.carts == null){
            MainActivity.carts = new ArrayList<>();
        }

        cartAdapter = new CartsAdapter(MainActivity.carts, CartActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCart.setLayoutManager(mLayoutManager);
        recyclerViewCart.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCart.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerViewCart.setAdapter(cartAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerViewCart);

        ActionToolbar();

        CheckData();

        TotalMoney();

        EventButton();

    }

    private void CheckData() {
        if (MainActivity.carts.size() <= 0 ){
            notification.setVisibility(View.VISIBLE);
            recyclerViewCart.setVisibility(View.INVISIBLE);
        } else {
            notification.setVisibility(View.INVISIBLE);
            recyclerViewCart.setVisibility(View.VISIBLE);
        }
    }


    private void ActionToolbar() {
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static void TotalMoney(){
        long totals = 0;
        for (int i = 0; i < MainActivity.carts.size(); i++){
            totals += MainActivity.carts.get(i).getPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        total.setText(decimalFormat.format(totals) + " VND");
    }


    public void EventButton(){
        btnContinueBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.carts.size() <= 0){
                    Toast.makeText(CartActivity.this, "Giỏ hàng của bạn chưa có sản phẩm", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CartActivity.this, PayActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartsAdapter.MyViewHolder){
            cartAdapter.removeItem(viewHolder.getAdapterPosition());
            TotalMoney();
            CheckData();
        }
    }
}

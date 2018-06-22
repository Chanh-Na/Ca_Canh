package com.example.letrongtin.menunavigation.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.letrongtin.menunavigation.R;
import com.example.letrongtin.menunavigation.model.Order;
import com.example.letrongtin.menunavigation.model.OrderDetail;
import com.example.letrongtin.menunavigation.utility.CheckInternet;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.letrongtin.menunavigation.R.id.inputPhoneNumber;

public class PayActivity extends AppCompatActivity {

    EditText inputName, inputEmail, inputPhonenumber;
    Button btnConfirm, btnBack;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        inputName = (EditText) findViewById(R.id.inputName);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPhonenumber = (EditText) findViewById(inputPhoneNumber);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnBack = (Button) findViewById(R.id.btnBack);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String phonenumber = inputPhonenumber.getText().toString().trim();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phonenumber)){
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    String date = df.format(Calendar.getInstance().getTime());
                    String id = "order-"+date;
                    Order order = new Order(id,name,email,phonenumber);

                    if(CheckInternet.haveNetworkConnection(PayActivity.this)) {
                        mDatabase.child("Orders").push().setValue(order, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    Dialog();

                                } else {
                                    //Toast.makeText(PayActivity.this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        String iddetail = "orderdetail-"+date;
                        OrderDetail orderDetail = new OrderDetail(iddetail, id, MainActivity.carts);
                        mDatabase.child("OrderDetails").push().setValue(orderDetail, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            }
                        });


                    } else {
                        Toast.makeText(PayActivity.this, "Đặt hàng thất bại. Bạn vui lòng kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
                        finish();
                    }


                } else {
                    Toast.makeText(PayActivity.this, "Bạn phải nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void Dialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PayActivity.this);
        alertDialog.setTitle("Thông báo");
        alertDialog.setMessage("Đặt hàng thành công. Cảm ơn bạn đã mua hàng!");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.carts.clear();
                Intent intent = new Intent(PayActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        alertDialog.show();

    }


}

//ca-app-pub-2498644322314522~8457004985

//ca-app-pub-2498644322314522/2981317020

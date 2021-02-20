package italy.company.pietroclemente92.demetra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import italy.company.pietroclemente92.demetra.R;

public class UserProductViewActivity extends AppCompatActivity {

    Button shopViewButton;
    ImageView backBtn;
    private StorageReference mStorageRef;

    String extraNameProduct, extraProductorName;
    Button shopViewBtn;

    //Product Variable
    TextView regNameProduct, regTypeProduct, regPriceProduct, regCountProduct, regPesticideProduct, regKMProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product_view);

        //Parametre
        extraNameProduct = getIntent().getStringExtra("nameProduct");
        extraProductorName = getIntent().getStringExtra("productorName");

        //Hooks
        backBtn = findViewById(R.id.backBtn);
        shopViewBtn = findViewById(R.id.shop_view_btn);
        regNameProduct = findViewById(R.id.nameProduct);
        regTypeProduct = findViewById(R.id.typeProduct);
        regPriceProduct = findViewById(R.id.priceProduct);
        regCountProduct = findViewById(R.id.countProduct);
        regPesticideProduct = findViewById(R.id.pesticideProduct);
        regKMProduct = findViewById(R.id.kmProduct);
        shopViewButton = findViewById(R.id.shop_view_btn);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProductViewActivity.super.onBackPressed();
            }
        });

        shopViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProductViewActivity.this, UserShopViewActivity.class);
                intent.putExtra("idUsername", extraProductorName);
                startActivity(intent);
            }
        });

        showProduct();
    }

    private void showProduct() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    String tmpUsername = ds.child("idUsername").getValue(String.class);
                    String nameProduct = ds.child("name").getValue(String.class);

                    if (nameProduct.equals(extraNameProduct) && tmpUsername.equals(extraProductorName)) {
                        String categoryProduct = ds.child("type").getValue(String.class);
                        Double priceProduct = ds.child("price").getValue(Double.class);
                        Integer countProduct = ds.child("count").getValue(Integer.class);
                        boolean pesticideProduct = ds.child("pesticide").getValue(Boolean.class);
                        boolean kmProduct = ds.child("km").getValue(Boolean.class);


                        regNameProduct.setText(nameProduct);

                        if(categoryProduct.equals("Fruit")) {
                            regTypeProduct.setText("Frutta");
                        } else if(categoryProduct.equals("Vegetable")) {
                            regTypeProduct.setText("Ortaggi");
                        } else if(categoryProduct.equals("Other")) {
                            regTypeProduct.setText("Altro");
                        }

                        regPriceProduct.setText("Prezzo: " + String.valueOf(priceProduct) + " Euro");
                        regCountProduct.setText("Disponibilità: " + String.valueOf(countProduct));

                        if(pesticideProduct == true) {
                            regPesticideProduct.setText("Pesticidi: Si");
                        } else {
                            regPesticideProduct.setText("Pesticidi: No");
                        }
                        if(kmProduct == true) {
                            regKMProduct.setText("Prodotto a KM0: Si");
                        } else {
                            regKMProduct.setText("Prodotto a KM0: No");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

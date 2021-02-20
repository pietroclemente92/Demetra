package italy.company.pietroclemente92.demetra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import italy.company.pietroclemente92.demetra.adapter.UpdateProductAdapter;
import italy.company.pietroclemente92.demetra.adapter.UserProductAdapter;

public class UserProductListViewActivity extends AppCompatActivity {

    ListView simpleList;
    ImageView backBtn;

    //Variable Extra
    String extraCategory, extraNameProduct, extraRegionProduct, extraPesticide, extraKM;

    ArrayList<String> productList, shopNameList, regionList;
    ArrayList<Integer> iconList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product_list_view);

        //View
        simpleList = (ListView) findViewById(R.id.simpleListView);
        backBtn = findViewById(R.id.backBtn);

        //Variable Extra
        extraCategory = getIntent().getStringExtra("category");
        extraNameProduct = getIntent().getStringExtra("nameProduct");
        extraRegionProduct = getIntent().getStringExtra("regionProduct");
        extraPesticide = getIntent().getStringExtra("pesticideProduct");
        extraKM = getIntent().getStringExtra("kmProduct");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProductListViewActivity.super.onBackPressed();
            }
        });

        //Hook
        final String nameProduct = getIntent().getStringExtra("nameProduct");
        productList = new ArrayList<>();
        iconList = new ArrayList<>();
        shopNameList = new ArrayList<>();
        regionList = new ArrayList<>();

        final UserProductAdapter customAdapter = new UserProductAdapter(getApplicationContext(), productList, iconList, shopNameList, regionList);
        simpleList.setAdapter(customAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                iconList.clear();
                shopNameList.clear();
                regionList.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    String tmpCategory = ds.child("type").getValue(String.class);
                    String tmpRegion = ds.child("region").getValue(String.class);
                    Boolean tmpPesticide = ds.child("pesticide").getValue(Boolean.class);
                    Boolean tmpKM = ds.child("km").getValue(Boolean.class);
                    String tmpNameProduct = ds.child("name").getValue(String.class);
                    String tmpProductorName = ds.child("idUsername").getValue(String.class);

                    if(tmpCategory.equals(extraCategory) && tmpRegion.equals(extraRegionProduct) &&
                            tmpPesticide == Boolean.valueOf(extraPesticide) && tmpKM == Boolean.valueOf(extraKM)) {

                        switch (extraCategory) {
                            case "Fruit":
                                iconList.add(R.drawable.ic_fruit);
                                break;
                            case "Vegetable":
                                iconList.add(R.drawable.ic_vegetable);
                                break;
                            case "Other":
                                iconList.add(R.drawable.ic_other);
                                break;
                        }

                        if(extraNameProduct != null && extraNameProduct.equals(tmpNameProduct)) {
                            productList.add(tmpNameProduct);
                            shopNameList.add(tmpProductorName);
                            regionList.add(tmpRegion);
                        } else if(extraNameProduct.equals("")) {
                            productList.add(tmpNameProduct);
                            shopNameList.add(tmpProductorName);
                            regionList.add(tmpRegion);
                        }
                    }
                    customAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(UserProductListViewActivity.this, UserProductViewActivity.class);
                intent.putExtra("productorName", shopNameList.get(i));
                intent.putExtra("nameProduct", productList.get(i));
                startActivity(intent);
            }
        });
    }
}
package italy.company.pietroclemente92.demetra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import italy.company.pietroclemente92.demetra.adapter.UserProductAdapter;

public class UserShopListViewActivity extends AppCompatActivity {

    ListView simpleList;
    ImageView backBtn;

    //Variable Extra
    String extraNameShop, extraRegion, extraCity;

    ArrayList<String> regionList, shopNameList, cityList;
    ArrayList<Integer> iconList;
    ArrayList<String> idUsernameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_shop_list_view);

        //Hook

        //View
        simpleList = (ListView) findViewById(R.id.simpleListView);
        backBtn = findViewById(R.id.backBtn);

        //Variable Extra
        extraNameShop = getIntent().getStringExtra("nameShop");
        extraRegion = getIntent().getStringExtra("region");
        extraCity = getIntent().getStringExtra("city");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserShopListViewActivity.super.onBackPressed();
            }
        });

        //Hook
        regionList = new ArrayList<>();
        iconList = new ArrayList<>();
        shopNameList = new ArrayList<>();
        cityList = new ArrayList<>();
        idUsernameList = new ArrayList<>();

        final UserProductAdapter customAdapter = new UserProductAdapter(getApplicationContext(), shopNameList, iconList, regionList, cityList);
        simpleList.setAdapter(customAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("shop");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                regionList.clear();
                iconList.clear();
                shopNameList.clear();
                cityList.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    String tmpUsername = ds.child("idUsername").getValue().toString();
                    idUsernameList.add(tmpUsername);

                    String tmpShopName = ds.child("fullName").getValue().toString();
                    String tmpRegion = ds.child("region").getValue().toString();
                    String tmpCity = ds.child("city").getValue().toString();

                    if(tmpRegion.equals(extraRegion)) {
                        iconList.add(R.drawable.icon_shop_vector);

                        if((extraNameShop != null && extraNameShop.equals(tmpShopName)) && extraCity.equals("")) {
                            shopNameList.add(tmpShopName);
                            regionList.add(tmpRegion);
                            cityList.add(tmpCity);
                        } else if((extraCity != null && extraCity.equals(tmpCity)) && extraNameShop.equals("")) {
                            shopNameList.add(tmpShopName);
                            regionList.add(tmpRegion);
                            cityList.add(tmpCity);
                        } else if((extraNameShop != null && extraNameShop.equals(tmpShopName)) && (extraCity != null && extraCity.equals(tmpCity))) {
                            shopNameList.add(tmpShopName);
                            regionList.add(tmpRegion);
                            cityList.add(tmpCity);
                        } else if(extraNameShop.equals("") && extraCity.equals("")) {
                            shopNameList.add(tmpShopName);
                            regionList.add(tmpRegion);
                            cityList.add(tmpCity);
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
                Intent intent = new Intent(UserShopListViewActivity.this, UserShopViewActivity.class);
                intent.putExtra("idUsername", idUsernameList.get(i));
                startActivity(intent);
            }
        });
    }
}
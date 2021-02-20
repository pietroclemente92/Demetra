package italy.company.pietroclemente92.demetra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import italy.company.pietroclemente92.demetra.adapter.UpdateProductAdapter;
import italy.company.pietroclemente92.demetra.R;

import android.os.Bundle;
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

public class PrivateRemoveProductActivity extends AppCompatActivity {

    ListView simpleList;
    ImageView backBtn;

    ArrayList<String> productList, category;
    ArrayList<Integer> iconList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_remove_product);

        //View
        simpleList = (ListView) findViewById(R.id.simpleListView);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivateRemoveProductActivity.super.onBackPressed();
            }
        });

        //Hook
        final String idUsername = getIntent().getStringExtra("idUsername");
        productList = new ArrayList<>();
        iconList = new ArrayList<>();
        category = new ArrayList<>();

        final UpdateProductAdapter customAdapter = new UpdateProductAdapter(getApplicationContext(), productList, iconList);
        simpleList.setAdapter(customAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                iconList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    String tmpUsername = ds.child("idUsername").getValue(String.class);

                    if(tmpUsername.equals(idUsername)) {
                        String nameProduct = ds.child("name").getValue(String.class);
                        String typeProduct = ds.child("type").getValue(String.class);

                        productList.add(nameProduct);
                        category.add(typeProduct);
                        switch (typeProduct) {
                            case "Fruit":
                                iconList.add(R.drawable.apple_icon);
                                break;
                            case "Vegetable":
                                iconList.add(R.drawable.salad_icon);
                                break;
                            case "Other":
                                iconList.add(R.drawable.all_prod_icon);
                                break;
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
                String tmpName = productList.get(i);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("products").child(tmpName).child("idUsername").child(idUsername);

                reference.getParent().getParent().removeValue();

                productList.remove(i);
                iconList.remove(i);
                category.remove(i);

                customAdapter.notifyDataSetChanged();
            }
        });
    }
}

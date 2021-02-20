package italy.company.pietroclemente92.demetra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserSearchProductActivity extends AppCompatActivity {

    //Shop Variable
    TextInputLayout regNameProduct;
    Spinner regRegionProduct;
    CheckBox kmBox, pesticideBox;

    //Button
    Button findBtn;
    ImageView backbutton;

    //Variable
    String categoryProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_product);

        regNameProduct = findViewById(R.id.nameProduct);
        regRegionProduct = findViewById(R.id.regionSpinner);
        kmBox = findViewById(R.id.kmProduct);
        pesticideBox = findViewById(R.id.pesticideProduct);
        findBtn = findViewById(R.id.find_shop_btn);
        backbutton = findViewById(R.id.backBtn);

        //Variable
        categoryProduct = getIntent().getStringExtra("category");

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpNameProduct = regNameProduct.getEditText().getText().toString();
                String tmpRegionProduct = regRegionProduct.getSelectedItem().toString();
                boolean tmpPesticide = pesticideBox.isChecked();
                boolean tmpKM = kmBox.isChecked();

                Intent intent = new Intent(UserSearchProductActivity.this, UserProductListViewActivity.class);
                intent.putExtra("category", categoryProduct);
                intent.putExtra("nameProduct", tmpNameProduct);
                intent.putExtra("regionProduct", tmpRegionProduct);
                intent.putExtra("pesticideProduct", String.valueOf(tmpPesticide));
                intent.putExtra("kmProduct", String.valueOf(tmpKM));
                startActivity(intent);
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSearchProductActivity.super.onBackPressed();
            }
        });
    }
}
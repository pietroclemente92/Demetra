package italy.company.pietroclemente92.demetra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;

public class UserSearchShopActivity extends AppCompatActivity {

    //Shop Variable
    TextInputLayout regNameShop, regCityShop;
    Spinner regRegionShop;

    //Button
    Button findBtn;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_shop);

        regNameShop = findViewById(R.id.nameShop);
        regCityShop = findViewById(R.id.cityShop);
        regRegionShop = findViewById(R.id.regionSpinner);
        findBtn = findViewById(R.id.find_shop_btn);
        backBtn = findViewById(R.id.backBtn);

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpNameShop = regNameShop.getEditText().getText().toString();
                String tmpRegionShop = regRegionShop.getSelectedItem().toString();
                String tmpCityShop = regCityShop.getEditText().getText().toString();

                Intent intent = new Intent(UserSearchShopActivity.this, UserShopListViewActivity.class);
                intent.putExtra("nameShop", tmpNameShop);
                intent.putExtra("region", tmpRegionShop);
                intent.putExtra("city", tmpCityShop);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSearchShopActivity.super.onBackPressed();
            }
        });
    }
}
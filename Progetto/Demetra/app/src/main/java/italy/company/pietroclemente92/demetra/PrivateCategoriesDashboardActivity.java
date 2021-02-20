package italy.company.pietroclemente92.demetra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import italy.company.pietroclemente92.demetra.R;

public class PrivateCategoriesDashboardActivity extends AppCompatActivity {

    Button categoryFruit, categoryVegetable, categoryOther;
    ImageView backBtn;
    String idUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_categories_dashboard);

        //Hook
        categoryFruit = findViewById(R.id.category_fruit_btn);
        categoryVegetable = findViewById(R.id.category_vegetable_btn);
        categoryOther = findViewById(R.id.category_other_btn);
        backBtn = findViewById(R.id.backBtn);

        idUsername = getIntent().getStringExtra("idUsername");

        categoryFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrivateCategoriesDashboardActivity.this, PrivateAddProductActivity.class);
                intent.putExtra("idUsername", idUsername);
                intent.putExtra("category", "Fruit");
                startActivity(intent);
            }
        });

        categoryVegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrivateCategoriesDashboardActivity.this, PrivateAddProductActivity.class);
                intent.putExtra("idUsername", idUsername);
                intent.putExtra("category", "Vegetable");
                startActivity(intent);
            }
        });

        categoryOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrivateCategoriesDashboardActivity.this, PrivateAddProductActivity.class);
                intent.putExtra("idUsername", idUsername);
                intent.putExtra("category", "Other");
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivateCategoriesDashboardActivity.super.onBackPressed();
            }
        });
    }
}

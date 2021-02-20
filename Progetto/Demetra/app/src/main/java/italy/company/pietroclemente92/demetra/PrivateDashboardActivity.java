package italy.company.pietroclemente92.demetra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import italy.company.pietroclemente92.demetra.R;

public class PrivateDashboardActivity extends AppCompatActivity {

    Button newProductBtn, updateProductBtn, removeBtn, profileBtn, logoutBtn;
    String idUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_dashboard);

        //Hooks
        newProductBtn = findViewById(R.id.new_product_btn);
        updateProductBtn = findViewById(R.id.update_product_btn);
        removeBtn = findViewById(R.id.remove_product_btn);
        profileBtn = findViewById(R.id.profile_btn);
        logoutBtn = findViewById(R.id.logoutBtn);

        idUsername = getIntent().getStringExtra("username");

        newProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrivateDashboardActivity.this, PrivateCategoriesDashboardActivity.class);
                intent.putExtra("idUsername", idUsername);
                startActivity(intent);
            }
        });

        updateProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrivateDashboardActivity.this, PrivateUpdateViewProductActivity.class);
                intent.putExtra("idUsername", idUsername);
                startActivity(intent);
            }
        });

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrivateDashboardActivity.this, PrivateRemoveProductActivity.class);
                intent.putExtra("idUsername", idUsername);
                startActivity(intent);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrivateDashboardActivity.this, PrivateProfileActivity.class);
                intent.putExtra("idUsername", idUsername);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrivateDashboardActivity.this, LoginActivity.class);
                intent.putExtra("idUsername", idUsername);
                startActivity(intent);
            }
        });
    }
}

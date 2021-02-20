package italy.company.pietroclemente92.demetra;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserShopViewActivity extends AppCompatActivity {

    TextView usernameLabel, shopLabel;
    TextInputLayout fullname, email, password, phone, site, nationality, city, address;
    ImageView backBtn;

    String _IDUSERNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_shop_view);

        //IDUSERNAME
        _IDUSERNAME = getIntent().getStringExtra("idUsername");

        //Hooks
        usernameLabel = findViewById(R.id.username_label);
        shopLabel = findViewById(R.id.shop_label);
        fullname = findViewById(R.id.fullnameInput);
        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);
        phone = findViewById(R.id.phoneInput);
        site = findViewById(R.id.siteInput);
        nationality = findViewById(R.id.nationalityInput);
        city = findViewById(R.id.cityInput);
        address = findViewById(R.id.addressInput);
        backBtn = findViewById(R.id.backBtn);

        //ShowProfile
        usernameLabel.setText(_IDUSERNAME);
        showAllUserData();
        showAllShopData();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserShopViewActivity.super.onBackPressed();
            }
        });
    }

    private void showAllUserData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    String tmpUsername = ds.child("username").getValue(String.class);

                    if (tmpUsername.equals(_IDUSERNAME)) {
                        String emailData = ds.child("email").getValue().toString();
                        String passwordData = ds.child("password").getValue().toString();

                        email.getEditText().setText(emailData);
                        password.getEditText().setText(passwordData);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showAllShopData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("shop");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    String tmpUsername = ds.child("idUsername").getValue(String.class);

                    if (tmpUsername.equals(_IDUSERNAME)) {
                        String shopData = ds.child("fullName").getValue().toString();
                        String nationalityData = ds.child("nationality").getValue().toString();
                        String cityData = ds.child("city").getValue().toString();
                        String addressData = ds.child("address").getValue().toString();
                        String siteData = ds.child("site").getValue().toString();
                        String phoneData = ds.child("phoneNumber").getValue().toString();

                        shopLabel.setText(shopData);
                        fullname.getEditText().setText(shopData);
                        nationality.getEditText().setText(nationalityData);
                        city.getEditText().setText(cityData);
                        address.getEditText().setText(addressData);
                        site.getEditText().setText(siteData);
                        phone.getEditText().setText(phoneData);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
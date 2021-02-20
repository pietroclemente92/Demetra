package italy.company.pietroclemente92.demetra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import italy.company.pietroclemente92.demetra.R;

public class PrivateProfileActivity extends AppCompatActivity {

    TextView usernameLabel, shopLabel;
    TextInputLayout fullname, email, password, phone, site, nationality, city, address;
    ImageView saveBtn, backBtn;

    private String _IDUSERNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_profile);

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
        saveBtn = findViewById(R.id.saveBtn);
        backBtn = findViewById(R.id.backBtn);

        //ShowProfile
        usernameLabel.setText(_IDUSERNAME);
        showAllUserData();
        showAllShopData();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("shop").child(_IDUSERNAME);

                String fullNameStore = fullname.getEditText().getText().toString();
                String phoneData = phone.getEditText().getText().toString();
                String siteData = site.getEditText().getText().toString();
                String nationalityData = nationality.getEditText().getText().toString();
                String cityData = city.getEditText().getText().toString();
                String addressData = address.getEditText().getText().toString();

                reference.child("idUsername").setValue(_IDUSERNAME);
                reference.child("fullName").setValue(fullNameStore);
                reference.child("nationality").setValue(nationalityData);
                reference.child("city").setValue(cityData);
                reference.child("address").setValue(addressData);
                reference.child("site").setValue(siteData);
                reference.child("phoneNumber").setValue(phoneData);

                //RefreshData
                showAllUserData();
                showAllShopData();

                Toast.makeText(getBaseContext(), "Profilo aggiornato", Toast.LENGTH_LONG).show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivateProfileActivity.super.onBackPressed();
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

package italy.company.pietroclemente92.demetra;

import androidx.appcompat.app.AppCompatActivity;
import italy.company.pietroclemente92.demetra.helpers.ShopHelperClass;
import italy.company.pietroclemente92.demetra.R;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterShopActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 3000;

    ImageView image;
    TextView logoText;
    TextInputLayout regName, regAddress, regCity, regNationality, regPhoneNumber, regSite;
    Spinner regRegion;
    Button regBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shop);

        //Object
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);

        //Hook
        regName = findViewById(R.id.nameInput);
        regAddress = findViewById(R.id.addressInput);
        regCity = findViewById(R.id.cityInput);
        regNationality = findViewById(R.id.nationalityInput);
        regPhoneNumber = findViewById(R.id.phoneInput);
        regSite = findViewById(R.id.siteInput);
        regBtn = findViewById(R.id.reg_btn);
        regRegion = findViewById(R.id.regionSpinner);

        //Save data in FireBase on button click
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateName() || !validateAddress() || !validateCity() || !validateNationality() || !validatePhoneNumber() || !validateSite()) {
                    return;
                }

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("shop");

                Intent extraIntent = getIntent();

                String idUsername = extraIntent.getStringExtra("idUsername");
                String name = regName.getEditText().getText().toString();
                String address = regAddress.getEditText().getText().toString();
                String city = regCity.getEditText().getText().toString();
                String nationality = regNationality.getEditText().getText().toString();
                String phoneNumber = regPhoneNumber.getEditText().getText().toString();
                String site = regSite.getEditText().getText().toString();
                String region = regRegion.getSelectedItem().toString();

                ShopHelperClass shopHelperClass = new ShopHelperClass(idUsername, name, address, city, nationality, region, phoneNumber, site);
                reference.child(idUsername).setValue(shopHelperClass);

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent intent = new Intent(RegisterShopActivity.this, PrivateDashboardActivity.class);
                        Pair[] pairs = new Pair[10];
                        pairs[0] = new Pair<View, String>(image, "logo_image");
                        pairs[1] = new Pair<View, String>(logoText, "logo_text");
                        pairs[2] = new Pair<View, String>(regName, "name_tran");
                        pairs[3] = new Pair<View, String>(regAddress, "address_tran");
                        pairs[4] = new Pair<View, String>(regCity, "city_tran");
                        pairs[5] = new Pair<View, String>(regNationality, "nationality_tran");
                        pairs[6] = new Pair<View, String>(regRegion, "region_tran");
                        pairs[7] = new Pair<View, String>(regPhoneNumber, "phoneNumber_train");
                        pairs[8] = new Pair<View, String>(regSite, "site_train");
                        pairs[9] = new Pair<View, String>(regBtn, "button_tran");
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterShopActivity.this, pairs);
                            startActivity(intent, options.toBundle());
                        }
                    }
                },SPLASH_SCREEN);
            }
        });
    }

    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();

        if(val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateAddress() {
        String val = regAddress.getEditText().getText().toString();
        String addressVal = "[A-z\\s]+,+\\s+[0-9]+";

        if(val.isEmpty()) {
            regAddress.setError("Field cannot be empty");
            return false;
        } else if(!val.matches(addressVal)){
            regAddress.setError("Password is too weak. Es: Park Street, 61");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateCity() {
        String val = regCity.getEditText().getText().toString();

        if (val.isEmpty()) {
            regCity.setError("Field cannot be empty");
            return false;
        } else {
            regCity.setError(null);
            regCity.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateNationality() {
        String val = regNationality.getEditText().getText().toString();

        if (val.isEmpty()) {
            regNationality.setError("Field cannot be empty");
            return false;
        } else {
            regNationality.setError(null);
            regNationality.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNumber() {
        String val = regPhoneNumber.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhoneNumber.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNumber.setError(null);
            regPhoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateSite() {
        String val = regSite.getEditText().getText().toString();
        String siteVal = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";

        if (val.isEmpty()) {
            regSite.setError("Inserire Vuoto");
            return false;
        } else if (!val.matches(siteVal) && !val.equals("Vuoto")) {
            regSite.setError("Parametro Errato");
            return false;
        } else {
            regSite.setError(null);
            regSite.setErrorEnabled(false);
            return true;
        }
    }
}


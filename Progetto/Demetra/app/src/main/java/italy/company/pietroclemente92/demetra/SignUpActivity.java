package italy.company.pietroclemente92.demetra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import italy.company.pietroclemente92.demetra.helpers.UserHelperClass;
import italy.company.pietroclemente92.demetra.R;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout regName, regUsername, regEmail, regPassword;
    CheckBox regPrivate, regUser;
    Button regBtn, regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Object
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);

        //Hook
        regName = findViewById(R.id.nameInput);
        regUsername = findViewById(R.id.usernameInput);
        regEmail = findViewById(R.id.emailInput);
        regPassword = findViewById(R.id.passwordInput);
        regPrivate = findViewById(R.id.checkPrivate);
        regUser = findViewById(R.id.checkUser);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_to_login);

        //Return to Login Activity
        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this);
                    startActivity(intent, options.toBundle());
                }
            }
        });

        //Save data in FireBase on button click
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateName() || !validateUsername() || !validateEmail() || !validatePassword() || !validateCheckBox()) {
                    return;
                }

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                final String name = regName.getEditText().getText().toString();
                final String username = regUsername.getEditText().getText().toString();
                final String email = regEmail.getEditText().getText().toString();
                final String password = regPassword.getEditText().getText().toString();
                final boolean privateAccount = regPrivate.isChecked();
                final boolean userAccount = regUser.isChecked();

                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                Query checkUser = reference.orderByChild("username").equalTo(username);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         if(!dataSnapshot.exists()) {
                             UserHelperClass userHelperClass = new UserHelperClass(name, username, email, password, userAccount, privateAccount);
                             reference.child(username).setValue(userHelperClass);

                             new Handler().postDelayed(new Runnable(){
                                 @Override
                                 public void run(){
                                     Intent intent = null;

                                     if(regPrivate.isChecked()) {
                                         intent = new Intent(SignUpActivity.this, OnBoardingShoppingAreaActivity.class);
                                         intent.putExtra("username", regUsername.getEditText().getText().toString());
                                     } else {
                                         intent = new Intent(SignUpActivity.this, UserDashboardActivity.class);
                                     }

                                     Pair[] pairs = new Pair[11];
                                     pairs[0] = new Pair<View, String>(image, "logo_image");
                                     pairs[1] = new Pair<View, String>(logoText, "logo_text");
                                     pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
                                     pairs[3] = new Pair<View, String>(regName, "name_tran");
                                     pairs[4] = new Pair<View, String>(regUsername, "username_tran");
                                     pairs[5] = new Pair<View, String>(regEmail, "email_tran");
                                     pairs[6] = new Pair<View, String>(regPassword, "password_tran");
                                     pairs[7] = new Pair<View, String>(regUser, "userAccount_train");
                                     pairs[8] = new Pair<View, String>(regUser, "privateAccount_train");
                                     pairs[9] = new Pair<View, String>(regBtn, "button_tran");
                                     pairs[10] = new Pair<View, String>(regToLoginBtn, "login_tran");
                                     if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                         ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this, pairs);
                                         startActivity(intent, options.toBundle());
                                     }
                                 }
                             },SPLASH_SCREEN);
                         } else {
                             regUsername.setError("Campo esistente");
                         }
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                });
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

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if(val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if(val.length() >= 15) {
            regUsername.setError("Username too long");
            return false;
        } else if(!val.matches(noWhiteSpace)){
            regUsername.setError("White Spaces are not allowed");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateCheckBox() {
        if(!regPrivate.isChecked() && !regUser.isChecked()) {
            regPrivate.setError("Private cannot be empty");
            regUser.setError("User cannot be empty");
            return false;
        } else if(regPrivate.isChecked() && regUser.isChecked()) {
            regPrivate.setError("Private cannot be checked either");
            regUser.setError("User cannot be checked either");
            return false;
        } else {
            regPrivate.setError(null);
            regUser.setError(null);
            return true;
        }
    }
}

package italy.company.pietroclemente92.demetra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RecupereEmailActivity extends AppCompatActivity {

    ImageView backBtn;
    Button recupereBtn;

    TextInputLayout username, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupere_email);

        //Hook
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        backBtn = findViewById(R.id.backBtn);
        recupereBtn = findViewById(R.id.recupere_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecupereEmailActivity.super.onBackPressed();
            }
        });

        recupereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() && !validateEmail()) {
                    return;
                }

                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                final String usernameValue = username.getEditText().getText().toString();
                final String emailValue = email.getEditText().getText().toString();

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            String tmpUsername = ds.child("username").getValue().toString();
                            String tmpEmail = ds.child("email").getValue().toString();

                            if(usernameValue.equals(tmpUsername) && emailValue.equals(tmpEmail)) {
                                String tmpPassowrd = ds.child("password").getValue().toString();
                                String[] CC = {""};
                                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                                emailIntent.setData(Uri.parse("mailto:"));
                                emailIntent.setType("text/plain");
                                emailIntent.putExtra(Intent.EXTRA_EMAIL, emailValue);
                                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Recupero Email Demtra");
                                emailIntent.putExtra(Intent.EXTRA_TEXT, "La tua password è: " + tmpPassowrd);

                                try {
                                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                                    finish();
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(RecupereEmailActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if(val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else if(val.length() >= 15) {
            username.setError("Username too long");
            return false;
        } else if(!val.matches(noWhiteSpace)){
            username.setError("White Spaces are not allowed");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            email.setError("Invalid email address");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }
}
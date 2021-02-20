package italy.company.pietroclemente92.demetra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import italy.company.pietroclemente92.demetra.R;
import italy.company.pietroclemente92.demetra.helpers.OtherProductHelperClass;
import italy.company.pietroclemente92.demetra.helpers.ProductHelperClass;
import italy.company.pietroclemente92.demetra.helpers.VegetableHelperClass;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PrivateAddProductActivity extends AppCompatActivity {

    //Add Fruit Variable
    TextInputLayout regNameFruit, regPriceFruit, regCountFruit;
    CheckBox regPesticideFruit, regKMFruit;

    //Add Vegetable Variable
    TextInputLayout regNameVegetablee, regPriceVegetable, regCountVegetable;
    CheckBox regPesticideVegetable, regKMVegtable;

    //Add Other Produtct Variable
    TextInputLayout regNameOtherProduct, regExpirationOtherProduct, regPriceOtherProduct, regCountOtherProduct;

    String category, idUsername;
    ImageView goBtn, backBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        category = getIntent().getStringExtra("category");
        idUsername = getIntent().getStringExtra("idUsername");

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        final String formattedDate = df.format(c);


        if(category.equals("Other")) {
            setContentView(R.layout.activity_private_add_otheproduct);

            //Hook
            regNameOtherProduct = findViewById(R.id.nameInput);
            regExpirationOtherProduct = findViewById(R.id.expirationDateInput);
            regPriceOtherProduct = findViewById(R.id.priceInput);
            regCountOtherProduct = findViewById(R.id.countInput);
            goBtn = findViewById(R.id.addBtn);
            backBtn = findViewById(R.id.backBtn);

            goBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!validatePrice(regPriceOtherProduct) || !validateCount(regCountOtherProduct) || !validateRelease(regExpirationOtherProduct)) {
                        return;
                    }

                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("products");

                    final String name = regNameOtherProduct.getEditText().getText().toString();
                    final String expirationDate = regExpirationOtherProduct.getEditText().getText().toString();
                    final String price = regPriceOtherProduct.getEditText().getText().toString();
                    final String count = regCountOtherProduct.getEditText().getText().toString();

                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("products");
                    Query checkUser = reference.orderByChild("idUsername").equalTo(idUsername);

                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                Query checkNameProduct = reference.orderByChild("name").equalTo(name);

                                checkNameProduct.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(!dataSnapshot.exists()) {
                                            rootNode.getReference("shop").child(idUsername).child("region").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String region= dataSnapshot.getValue().toString();
                                                    ProductHelperClass shopHelperClass = new OtherProductHelperClass(idUsername, name, category, expirationDate, region, Double.parseDouble(price), Integer.parseInt(count), formattedDate);
                                                    reference.child(name).setValue(shopHelperClass);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                            Intent intent = new Intent(getApplicationContext(), PrivateDashboardActivity.class);
                                            intent.putExtra("username", idUsername);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            regNameFruit.setError("Prodotto esistente");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrivateAddProductActivity.super.onBackPressed();
                }
            });
        }

        if(category.equals("Vegetable")) {
            setContentView(R.layout.activity_private_add_vegetable);

            //Hook
            regNameVegetablee = findViewById(R.id.nameInput);
            regPriceVegetable = findViewById(R.id.priceInput);
            regCountVegetable = findViewById(R.id.countInput);
            regPesticideVegetable = findViewById(R.id.pesticide_value);
            regKMVegtable = findViewById(R.id.km_value);
            goBtn = findViewById(R.id.addBtn);
            backBtn = findViewById(R.id.backBtn);

            goBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!validatePrice(regPriceVegetable) || !validateCount(regCountVegetable)) {
                        return;
                    }

                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("products");

                    final String name = regNameVegetablee.getEditText().getText().toString();
                    final String price = regPriceVegetable.getEditText().getText().toString();
                    final String count = regCountVegetable.getEditText().getText().toString();
                    final boolean pesticide = regPesticideVegetable.isChecked();
                    final boolean km = regKMVegtable.isChecked();

                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("products");
                    Query checkUser = reference.orderByChild("idUsername").equalTo(idUsername);

                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                Query checkNameProduct = reference.orderByChild("name").equalTo(name);

                                checkNameProduct.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(!dataSnapshot.exists()) {
                                            rootNode.getReference("shop").child(idUsername).child("region").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String region= dataSnapshot.getValue().toString();
                                                    ProductHelperClass shopHelperClass = new VegetableHelperClass(idUsername, name, category, null, region, Double.parseDouble(price), Integer.parseInt(count), pesticide, km, formattedDate);
                                                    reference.child(name).setValue(shopHelperClass);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                            Intent intent = new Intent(getApplicationContext(), PrivateDashboardActivity.class);
                                            intent.putExtra("username", idUsername);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            regNameFruit.setError("Prodotto esistente");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrivateAddProductActivity.super.onBackPressed();
                }
            });
        }

        if(category.equals("Fruit")) {
            setContentView(R.layout.activity_private_add_fruit);

            //Hook
            regNameFruit = findViewById(R.id.nameInput);
            regPriceFruit = findViewById(R.id.priceInput);
            regCountFruit = findViewById(R.id.countInput);
            regPesticideFruit = findViewById(R.id.pesticide_value);
            regKMFruit = findViewById(R.id.km_value);
            goBtn = findViewById(R.id.addBtn);
            backBtn = findViewById(R.id.backBtn);

            goBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!validatePrice(regPriceFruit) || !validateCount(regCountFruit)) {
                        return;
                    }

                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("products");

                    final String name = regNameFruit.getEditText().getText().toString();
                    final String price = regPriceFruit.getEditText().getText().toString();
                    final String count = regCountFruit.getEditText().getText().toString();
                    final boolean pesticide = regPesticideFruit.isChecked();
                    final boolean km = regKMFruit.isChecked();

                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("products");
                    Query checkUser = reference.orderByChild("idUsername").equalTo(idUsername);

                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                Query checkNameProduct = reference.orderByChild("name").equalTo(name);

                                checkNameProduct.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(!dataSnapshot.exists()) {
                                            rootNode.getReference("shop").child(idUsername).child("region").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String region = dataSnapshot.getValue().toString();
                                                    ProductHelperClass shopHelperClass = new VegetableHelperClass(idUsername, name, category, null, region, Double.parseDouble(price), Integer.parseInt(count), pesticide, km, formattedDate);
                                                    reference.child(name).setValue(shopHelperClass);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                            Intent intent = new Intent(getApplicationContext(), PrivateDashboardActivity.class);
                                            intent.putExtra("username", idUsername);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            regNameFruit.setError("Prodotto esistente");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrivateAddProductActivity.super.onBackPressed();
                }
            });
        }
    }

    /*
    //Validate Game Input
    private Boolean validateConsoleGame() {
        String val = regConsoleGame.getEditText().getText().toString();
        boolean tmp = false;

        for(int i = 0; i < CONSOLE.length; i++) {
            if(val.equals(CONSOLE[i]))
                tmp = true;
        }

        if(val.isEmpty()) {
            regConsoleGame.setError("Field cannot be empty");
            return false;
        } else if(!tmp) {
            regConsoleGame.setError("Field cannot contain console");
            return false;
        } else {
            regConsoleGame.setError(null);
            regConsoleGame.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateDeveloperGame() {
        String val = regDeveloperGame.getEditText().getText().toString();
        boolean tmp = false;

        for(int i = 0; i < DEVELOPER_GAME.length; i++) {
            if(val.equals(DEVELOPER_GAME[i]))
                tmp = true;
        }

        if(!tmp) {
            regDeveloperGame.setError("Field cannot contain developer");
            return false;
        } else {
            regDeveloperGame.setError(null);
            regDeveloperGame.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateGenreGame() {
        String val = regGenreGame.getEditText().getText().toString();
        boolean tmp = false;

        for(int i = 0; i < GENRE_GAME.length; i++) {
            if(val.equals(GENRE_GAME[i]))
                tmp = true;
        }

        if(!tmp) {
            regGenreGame.setError("Field cannot contain genre");
            return false;
        } else {
            regGenreGame.setError(null);
            regGenreGame.setErrorEnabled(false);
            return true;
        }
    }

    //Validate Manga Input
    private Boolean validateFranchiseManga() {
        String val = regFranchiseManga.getEditText().getText().toString();
        boolean tmp = false;

        for(int i = 0; i < FRANCHISE_MANGA.length; i++) {
            if(val.equals(FRANCHISE_MANGA[i]))
                tmp = true;
        }

        if(val.isEmpty()) {
            regFranchiseManga.setError("Field cannot be empty");
            return false;
        } else if(!tmp) {
            regFranchiseManga.setError("Field cannot contain franchise");
            return false;
        } else {
            regFranchiseManga.setError(null);
            regFranchiseManga.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePublisherMangga() {
        String val = regPublisherManga.getEditText().getText().toString();
        boolean tmp = false;

        for(int i = 0; i < PUBLISHER_MANGA.length; i++) {
            if(val.equals(PUBLISHER_MANGA[i]))
                tmp = true;
        }

        if(val.isEmpty()) {
            regPublisherManga.setError("Field cannot be empty");
            return false;
        } else if(!tmp) {
            regPublisherManga.setError("Field cannot contain franchise");
            return false;
        } else {
            regPublisherManga.setError(null);
            regPublisherManga.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateGenreManga() {
        String val = regGenreManga.getEditText().getText().toString();
        boolean tmp = false;

        for(int i = 0; i < GENRE_MANGA.length; i++) {
            if(val.equals(GENRE_MANGA[i]))
                tmp = true;
        }

        if(val.isEmpty()) {
            regGenreManga.setError("Field cannot be empty");
            return false;
        } else if(!tmp) {
            regGenreManga.setError("Field cannot contain franchise");
            return false;
        } else {
            regGenreManga.setError(null);
            regGenreManga.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateVolumeManga() {
        String val = regVolumeManga.getEditText().getText().toString();

        if(val.isEmpty()) {
            regVolumeManga.setError("Field cannot be empty");
            return false;
        } else if(Integer.parseInt(val) == 0) {
            regVolumeManga.setError("Invalidate zero");
            return false;
        } else {
            regVolumeManga.setError(null);
            regVolumeManga.setErrorEnabled(false);
            return true;
        }
    }

    //Validate Card Game Input
    private Boolean validateFranchiseCard() {
        String val = regFranchiseCard.getEditText().getText().toString();
        boolean tmp = false;

        for(int i = 0; i < FRANCHISE_CARDGAME.length; i++) {
            if(val.equals(FRANCHISE_CARDGAME[i]))
                tmp = true;
        }

        if(val.isEmpty()) {
            regFranchiseCard.setError("Field cannot be empty");
            return false;
        } else if(!tmp) {
            regFranchiseCard.setError("Field cannot contain franchise");
            return false;
        } else {
            regFranchiseCard.setError(null);
            regFranchiseCard.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateExpansionCard() {
        String val = regExpansionCard.getEditText().getText().toString();

        if(val.isEmpty()) {
            regExpansionCard.setError("Field cannot be empty");
            return false;
        } else {
            regExpansionCard.setError(null);
            regExpansionCard.setErrorEnabled(false);
            return true;
        }
    }

    //Validate Figure Input

    private Boolean validatePublisherFigure() {
        String val = regPublisherFigure.getEditText().getText().toString();
        boolean tmp = false;

        for(int i = 0; i < PUBLISHER_FIGURE.length; i++) {
            if(val.equals(PUBLISHER_FIGURE[i]))
                tmp = true;
        }

        if(val.isEmpty()) {
            regPublisherFigure.setError("Field cannot be empty");
            return false;
        } else if(!tmp) {
            regPublisherFigure.setError("Field cannot contain franchise");
            return false;
        } else {
            regPublisherFigure.setError(null);
            regPublisherFigure.setErrorEnabled(false);
            return true;
        }
    }

    //Validate General Input
    private Boolean validateName(TextInputLayout textInputLayout) {
        String val = textInputLayout.getEditText().getText().toString();

        if(val.isEmpty()) {
            textInputLayout.setError("Field cannot be empty");
            return false;
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateAge(TextInputLayout textInputLayout) {
        String val = textInputLayout.getEditText().getText().toString();

        if(val.isEmpty()) {
            textInputLayout.setError("Field cannot be empty");
            return false;
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

     */
    private Boolean validateRelease(TextInputLayout textInputLayout) {
        String val = textInputLayout.getEditText().getText().toString();
        String dataVal = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$";

        if(val.isEmpty()) {
            textInputLayout.setError("Field cannot be empty");
            return false;
        } else if(val.equals(dataVal)) {
            textInputLayout.setError("Invalidate data");
            textInputLayout.setErrorEnabled(false);
            return true;
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePrice(TextInputLayout textInputLayout) {
        String val = textInputLayout.getEditText().getText().toString();

        if(val.isEmpty()) {
            textInputLayout.setError("Field cannot be empty");
            return false;
        } else if(Double.parseDouble(val) == 0) {
            textInputLayout.setError("Invalidate zero");
            return false;
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateCount(TextInputLayout textInputLayout) {
        String val = textInputLayout.getEditText().getText().toString();

        if(val.isEmpty()) {
            textInputLayout.setError("Field cannot be empty");
            return false;
        } else if(Integer.parseInt(val) == 0) {
            textInputLayout.setError("Invalidate zero");
            return false;
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

}

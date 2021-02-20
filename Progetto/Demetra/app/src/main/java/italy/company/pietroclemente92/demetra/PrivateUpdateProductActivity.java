package italy.company.pietroclemente92.demetra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PrivateUpdateProductActivity extends AppCompatActivity {

    TextView usernameLabel, productLabel;
    ImageView saveBtn, backBtn;
    String fullname, category, idUsername;

    //Add Fruit Variable
    TextInputLayout regNameFruit, regPriceFruit, regCountFruit;
    CheckBox regPesticideFruit, regKMFruit;

    //Add Vegetable Variable
    TextInputLayout regNameVegetablee, regPriceVegetable, regCountVegetable;
    CheckBox regPesticideVegetable, regKMVegtable;

    //Add Other Produtct Variable
    TextInputLayout regNameOtherProduct, regExpirationOtherProduct, regPriceOtherProduct, regCountOtherProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fullname = getIntent().getStringExtra("fullname");
        category = getIntent().getStringExtra("category");
        idUsername = getIntent().getStringExtra("idUsername");

        if(category.equals("Other")) {
            setContentView(R.layout.activity_private_update_other);

            productLabel = findViewById(R.id.product_label);
            productLabel.setText(fullname);

            usernameLabel = findViewById(R.id.username_label);
            usernameLabel.setText(idUsername);;

            //Hook
            regNameOtherProduct = findViewById(R.id.nameInput);
            regExpirationOtherProduct = findViewById(R.id.expirationDateInput);
            regPriceOtherProduct = findViewById(R.id.priceInput);
            regCountOtherProduct = findViewById(R.id.countInput);
            saveBtn = findViewById(R.id.saveBtn);
            backBtn = findViewById(R.id.backBtn);

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProductData();
                }
            });

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrivateUpdateProductActivity.super.onBackPressed();
                }
            });
        }

        if(category.equals("Vegetable")) {
            setContentView(R.layout.activity_private_update_vegetable);

            productLabel = findViewById(R.id.product_label);
            productLabel.setText(fullname);

            usernameLabel = findViewById(R.id.username_label);
            usernameLabel.setText(idUsername);;

            //Hook
            regNameVegetablee = findViewById(R.id.nameInput);
            regPriceVegetable = findViewById(R.id.priceInput);
            regCountVegetable = findViewById(R.id.countInput);
            regPesticideVegetable = findViewById(R.id.pesticide_value);
            regKMVegtable = findViewById(R.id.km_value);
            saveBtn = findViewById(R.id.saveBtn);
            backBtn = findViewById(R.id.backBtn);

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProductData();
                }
            });

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrivateUpdateProductActivity.super.onBackPressed();
                }
            });
        }

        if(category.equals("Fruit")) {
            setContentView(R.layout.activity_private_update_fruit);

            productLabel = findViewById(R.id.product_label);
            productLabel.setText(fullname);

            usernameLabel = findViewById(R.id.username_label);
            usernameLabel.setText(idUsername);;

            //Hook
            regNameFruit = findViewById(R.id.nameInput);
            regPriceFruit = findViewById(R.id.priceInput);
            regCountFruit = findViewById(R.id.countInput);
            regPesticideFruit = findViewById(R.id.pesticide_value);
            regKMFruit = findViewById(R.id.km_value);
            saveBtn = findViewById(R.id.saveBtn);
            backBtn = findViewById(R.id.backBtn);

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProductData();
                }
            });

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrivateUpdateProductActivity.super.onBackPressed();
                }
            });
        }

        //Show Product Data
        showAllProductData();
    }

    private void showAllProductData() {
        if(category.equals("Fruit")) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("products");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        String tmpUsername = ds.child("idUsername").getValue(String.class);
                        String nameProduct = ds.child("name").getValue(String.class);

                        if (nameProduct.equals(fullname) && tmpUsername.equals(idUsername)) {
                            Double priceProduct = ds.child("price").getValue(Double.class);
                            Integer countProduct = ds.child("count").getValue(Integer.class);
                            boolean pesticideProduct = ds.child("pesticide").getValue(Boolean.class);
                            boolean kmProduct = ds.child("km").getValue(Boolean.class);


                            regNameFruit.getEditText().setText(nameProduct);
                            regPriceFruit.getEditText().setText(String.valueOf(priceProduct));
                            regCountFruit.getEditText().setText(String.valueOf(countProduct));

                            if(pesticideProduct == true) {
                                regPesticideFruit.setActivated(true);
                            }
                            if(kmProduct == true) {
                                regKMFruit.setActivated(true);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if(category.equals("Vegetable")) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("products");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        String tmpUsername = ds.child("idUsername").getValue(String.class);
                        String nameProduct = ds.child("name").getValue(String.class);

                        if (nameProduct.equals(fullname) && tmpUsername.equals(idUsername)) {
                            Double priceProduct = ds.child("price").getValue(Double.class);
                            Integer countProduct = ds.child("count").getValue(Integer.class);
                            boolean pesticideProduct = ds.child("pesticide").getValue(Boolean.class);
                            boolean kmProduct = ds.child("km").getValue(Boolean.class);


                            regNameVegetablee.getEditText().setText(nameProduct);
                            regPriceVegetable.getEditText().setText(String.valueOf(priceProduct));
                            regCountVegetable.getEditText().setText(String.valueOf(countProduct));

                            if(pesticideProduct == true) {
                                regPesticideVegetable.setActivated(true);
                            }
                            if(kmProduct == true) {
                                regKMVegtable.setActivated(true);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if(category.equals("Other")) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("products");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        String tmpUsername = ds.child("idUsername").getValue(String.class);
                        String nameProduct = ds.child("name").getValue(String.class);

                        if (nameProduct.equals(fullname) && tmpUsername.equals(idUsername)) {
                            Double priceProduct = ds.child("price").getValue(Double.class);
                            Integer countProduct = ds.child("count").getValue(Integer.class);
                            String expirationDate = regExpirationOtherProduct.getEditText().getText().toString();


                            regNameOtherProduct.getEditText().setText(nameProduct);
                            regPriceOtherProduct.getEditText().setText(String.valueOf(priceProduct));
                            regCountOtherProduct.getEditText().setText(String.valueOf(countProduct));
                            regExpirationOtherProduct.getEditText().setText(String.valueOf(expirationDate));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void updateProductData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("products").child(fullname).child("idUsername").child(idUsername);


        if(category.equals("Other")) {
            if(!validatePrice(regPriceOtherProduct) || !validateCount(regCountOtherProduct) || !validateRelease(regExpirationOtherProduct)) {
                return;
            }

            String name = regNameOtherProduct.getEditText().getText().toString();
            String expirationDate = regExpirationOtherProduct.getEditText().getText().toString();
            String price = regPriceOtherProduct.getEditText().getText().toString();
            String count = regCountOtherProduct.getEditText().getText().toString();

            reference.getParent().getParent().child("name").getRef().setValue(name);
            reference.getParent().getParent().child("release").getRef().setValue(expirationDate);
            reference.getParent().getParent().child("price").getRef().setValue(Double.parseDouble(price));
            reference.getParent().getParent().child("count").getRef().setValue(Integer.parseInt(count));

            fullname = name;
            productLabel.setText(fullname);

            Toast.makeText(getBaseContext(), "Il prodotto è stato aggiornato correttamente", Toast.LENGTH_LONG).show();
        }

        if(category.equals("Vegetable")) {
            if(!validatePrice(regPriceVegetable) || !validateCount(regCountVegetable)) {
                return;
            }

            String name = regNameVegetablee.getEditText().getText().toString();
            String price = regPriceVegetable.getEditText().getText().toString();
            String count = regCountVegetable.getEditText().getText().toString();
            boolean pesticide = regPesticideVegetable.isChecked();
            boolean km = regKMVegtable.isChecked();

            reference.getParent().getParent().child("name").getRef().setValue(name);
            reference.getParent().getParent().child("price").getRef().setValue(Double.parseDouble(price));
            reference.getParent().getParent().child("count").getRef().setValue(Integer.parseInt(count));
            reference.getParent().getParent().child("km").getRef().setValue(km);
            reference.getParent().getParent().child("pesticide").getRef().setValue(pesticide);

            fullname = name;
            productLabel.setText(fullname);

            Toast.makeText(getBaseContext(), "Il prodotto è stato aggiornato correttamente", Toast.LENGTH_LONG).show();
        }

        if(category.equals("Fruit")) {
            if(!validatePrice(regPriceFruit) || !validateCount(regCountFruit)) {
                return;
            }

            String name = regNameFruit.getEditText().getText().toString();
            String price = regPriceFruit.getEditText().getText().toString();
            String count = regCountFruit.getEditText().getText().toString();
            boolean pesticide = regPesticideFruit.isChecked();
            boolean km = regKMFruit.isChecked();

            reference.getParent().getParent().child("name").getRef().setValue(name);
            reference.getParent().getParent().child("price").getRef().setValue(Double.parseDouble(price));
            reference.getParent().getParent().child("count").getRef().setValue(Integer.parseInt(count));
            reference.getParent().getParent().child("km").getRef().setValue(km);
            reference.getParent().getParent().child("pesticide").getRef().setValue(pesticide);

            fullname = name;
            productLabel.setText(fullname);

            Toast.makeText(getBaseContext(), "Il prodotto è stato aggiornato correttamente", Toast.LENGTH_LONG).show();
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

package italy.company.pietroclemente92.demetra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import italy.company.pietroclemente92.demetra.adapter.CategoriesAdapter;
import italy.company.pietroclemente92.demetra.helpers.CategoriesHelperClass;
import italy.company.pietroclemente92.demetra.adapter.FeaturedAdapter;
import italy.company.pietroclemente92.demetra.helpers.FeaturedHelperClass;
import italy.company.pietroclemente92.demetra.adapter.MostViewedAdapter;
import italy.company.pietroclemente92.demetra.adapter.MostViewedHelperClass;
import italy.company.pietroclemente92.demetra.R;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class UserDashboardActivity extends AppCompatActivity {

    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3;

    ImageView regFruitBtn, regVegetableBtn, regOtherBtn, regShopBtn, regLogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);


        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler);

        regShopBtn = findViewById(R.id.shopBtn);
        regFruitBtn = findViewById(R.id.fruitBtn);
        regVegetableBtn = findViewById(R.id.vegetableBtn);
        regOtherBtn = findViewById(R.id.otherBtn);
        regLogoutBtn = findViewById(R.id.logoutBtn);

        regLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboardActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        regShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboardActivity.this, UserSearchShopActivity.class);
                startActivity(intent);
            }
        });

        regFruitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboardActivity.this, UserSearchProductActivity.class);
                intent.putExtra("category", "Fruit");
                startActivity(intent);
            }
        });

        regVegetableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboardActivity.this, UserSearchProductActivity.class);
                intent.putExtra("category", "Vegetable");
                startActivity(intent);
            }
        });

        regOtherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboardActivity.this, UserSearchProductActivity.class);
                intent.putExtra("category", "Other");
                startActivity(intent);
            }
        });

        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();
    }

    private void featuredRecycler() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("products");
        Query query = reference.orderByChild("dataInsert");

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        final ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                int lenght = (int) dataSnapshot.getChildrenCount();

                for(int i = 0; i < 3 && i < lenght; i++) {
                    DataSnapshot ds = iterator.next();

                    String tmpNameProduct = ds.child("name").getValue().toString();
                    String tmpProductor = ds.child("idUsername").getValue().toString();
                    String tmpDialogCompose = "Il prodotto è stato aggiunto da: " + tmpProductor + ".";
                    String tmpCategory = ds.child("type").getValue().toString();
                    int icon = R.drawable.ic_fruit;

                    if(tmpCategory.equals("Fruit")) {
                        icon = R.drawable.slide_fruit;
                    } else if(tmpCategory.equals("Vegetable")) {
                        icon = R.drawable.slide_ortage;
                    } else {
                        icon = R.drawable.slide_other;
                    }

                    featuredLocations.add(new FeaturedHelperClass(icon, tmpNameProduct, tmpDialogCompose));
                }

                adapter = new FeaturedAdapter(featuredLocations);
                featuredRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //GradientDrawable gradient = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});
    }

    private void mostViewedRecycler() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("shop");
        Query query = reference.orderByChild("name");

        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        final ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                int lenght = (int) dataSnapshot.getChildrenCount();

                for(int i = 0; i < 3 && i < lenght; i++) {
                    DataSnapshot ds = iterator.next();

                    String tmpNameShop = ds.child("fullName").getValue().toString();
                    String tmpProductor = ds.child("idUsername").getValue().toString();
                    String tmpDialogCompose = "Il negozio di " + tmpProductor + " è a tua disposizione. Fai un salto nel suo negozio.";
                    int icon = R.drawable.icon_shop_vector;

                    mostViewedLocations.add(new MostViewedHelperClass(icon, tmpNameShop, tmpDialogCompose));
                }

                adapter = new MostViewedAdapter(mostViewedLocations);
                mostViewedRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void categoriesRecycler() {
        //All Gradients
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("products");
        Query query = reference.orderByChild("name");

        final ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                int lenght = (int) dataSnapshot.getChildrenCount();

                for(int i = 0; i < 3 && i < lenght; i++) {
                    DataSnapshot ds = iterator.next();

                    String tmpNameProduct = ds.child("name").getValue().toString();
                    String tmpProductor = ds.child("idUsername").getValue().toString();
                    String tmpDialogCompose = "Il prodotto è stato aggiunto da: " + tmpProductor + ".";
                    String tmpCategory = ds.child("type").getValue().toString();
                    int icon = R.drawable.ic_fruit;
                    GradientDrawable tmpGradient = null;

                    if(tmpCategory.equals("Fruit")) {
                        icon = R.drawable.slide_fruit;
                        tmpGradient = gradient1;
                    } else if(tmpCategory.equals("Vegetable")) {
                        icon = R.drawable.slide_ortage;
                        tmpGradient = gradient2;
                    } else {
                        icon = R.drawable.slide_other;
                        tmpGradient = gradient3;
                    }

                    categoriesHelperClasses.add(new CategoriesHelperClass(tmpGradient, icon, tmpNameProduct, tmpDialogCompose));
                }

                categoriesRecycler.setHasFixedSize(true);
                adapter = new CategoriesAdapter(categoriesHelperClasses);
                categoriesRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
                categoriesRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

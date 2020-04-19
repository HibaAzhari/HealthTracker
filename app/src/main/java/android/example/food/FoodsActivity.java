package android.example.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FoodsActivity extends AppCompatActivity implements FoodsAdapter.FoodsAdapterOnLongClickHandler {

    DatabaseManager nutritionDB;
    RecyclerView recyclerView;
    FoodsAdapter foodsAdapter;
    List<String> foodData;
    FloatingActionButton addFoodBtn;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);
        nutritionDB = new DatabaseManager(this);
        addFoodBtn = findViewById(R.id.add_food_btn);

        //setting up bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.nav_food:
                                break;
                            case R.id.nav_main:
                                intent = new Intent(FoodsActivity.this, MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0,0);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                break;
                            case R.id.nav_stats:
                                intent = new Intent(FoodsActivity.this, StatsActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0,0);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                break;
                        }
                        return false;
                    }
                });

        //recycler view set up stuff
        recyclerView = findViewById(R.id.rv_food_data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        foodsAdapter = new FoodsAdapter(this);
        recyclerView.setAdapter(foodsAdapter);

        //getting food data from database
        foodData = new ArrayList<>();
        Cursor res = nutritionDB.getFoods();
        if(res.getCount() != 0){
            while(res.moveToNext()){
                foodData.add(res.getString(1) + "\n" + res.getString(2) + " Calories");
            }
        }

        //passing data to recycler view adapter
        foodsAdapter.setFoodData(foodData);

        //setting main action button listener: adding food items to list
        addFoodBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                 AlertDialog.Builder alert = new AlertDialog.Builder(FoodsActivity.this);
                        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_food,null);

                        alert
                         .setTitle("New Food Item")
                         .setView(dialogView)
                         .setNegativeButton("Cancel", null)
                         .setPositiveButton("Add", new AlertDialog.OnClickListener(){

                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 //Add input data to DB and local list
                                 String label = ((EditText)dialogView.findViewById(R.id.et_label)).getText().toString();
                                 int cal = Integer.valueOf(((EditText)dialogView.findViewById(R.id.et_calories)).getText().toString());
                                 nutritionDB.addFoods(label, cal);
                                 foodsAdapter.addFoodItem(label, cal);
                                 Toast.makeText(getBaseContext(), "Added " + label, Toast.LENGTH_SHORT).show();
                             }
                         });
                         alert.create().show();
            }
        });
    }

    //deletion of food items
    @Override
    public void onLongClick(final int deletedFoodId) {

        String item = foodsAdapter.getItem(deletedFoodId);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
                .setTitle("Delete")
                .setMessage("Do you want to delete:\n\n" + item)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        nutritionDB.deleteFood(deletedFoodId + 1);
                        foodsAdapter.removeFoodItem(deletedFoodId);
                        Toast.makeText(getBaseContext(), "Item Deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .create()
                .show();
    }
}
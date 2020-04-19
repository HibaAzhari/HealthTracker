package android.example.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    DatabaseManager nutritionDB;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nutritionDB = new DatabaseManager(this);

        //setting up bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.nav_food:
                        intent = new Intent(MainActivity.this, FoodsActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        break;
                    case R.id.nav_main:
                        break;
                    case R.id.nav_stats:
                        intent = new Intent(MainActivity.this, StatsActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        break;
                }
                return false;
            }
        });

    }

}
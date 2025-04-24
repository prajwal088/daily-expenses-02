package com.prajwaldarekar.dailyexpenses02.activities;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.prajwaldarekar.dailyexpenses02.R;
import com.prajwaldarekar.dailyexpenses02.fragments.DashboardFragment;
import com.prajwaldarekar.dailyexpenses02.fragments.TransactionFragment;
// import com.prajwaldarekar.dailyexpenses02.fragments.StatisticsFragment;
// import com.prajwaldarekar.dailyexpenses02.fragments.CalendarFragment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Fragment activeFragment;
    private final Fragment dashboardFragment = new DashboardFragment();
    private final Fragment transactionFragment = new TransactionFragment();
    // private final Fragment statisticsFragment = new StatisticsFragment();
    // private final Fragment calendarFragment = new CalendarFragment();

    private final Map<Integer, Fragment> fragmentMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnItemSelectedListener(this::onNavigationItemSelected);

        // Add fragments to map
        fragmentMap.put(R.id.navigation_dashboard, dashboardFragment);
        fragmentMap.put(R.id.navigation_transactions, transactionFragment);
        // fragmentMap.put(R.id.navigation_statistics, statisticsFragment);
        // fragmentMap.put(R.id.navigation_calendar, calendarFragment);

        // Load all fragments and hide all except default (dashboard)
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Map.Entry<Integer, Fragment> entry : fragmentMap.entrySet()) {
            Fragment fragment = entry.getValue();
            transaction.add(R.id.fragment_container, fragment).hide(fragment);
        }

        // Show default fragment (dashboard)
        transaction.show(dashboardFragment).commit();
        activeFragment = dashboardFragment;
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = fragmentMap.get(item.getItemId());

        if (selectedFragment != null && selectedFragment != activeFragment) {
            getSupportFragmentManager().beginTransaction()
                    .hide(activeFragment)
                    .show(selectedFragment)
                    .commit();
            activeFragment = selectedFragment;
            return true;
        }
        return false;
    }
}

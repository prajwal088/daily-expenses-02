package com.prajwaldarekar.dailyexpenses02.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.prajwaldarekar.dailyexpenses02.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.prajwaldarekar.dailyexpenses02.fragments.DashboardFragment;
import com.prajwaldarekar.dailyexpenses02.fragments.CalendarFragment;
import com.prajwaldarekar.dailyexpenses02.fragments.StatisticsFragment;
import com.prajwaldarekar.dailyexpenses02.fragments.TransactionsFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment activeFragment;
    private final Fragment dashboardFragment = new DashboardFragment();
    private final Fragment transactionsFragment = new TransactionsFragment();
    private final Fragment statisticsFragment = new StatisticsFragment();
    private final Fragment calendarFragment = new CalendarFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize BottomNavigationView
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnItemSelectedListener(this::onNavigationItemSelected);

        // Load all fragments initially, show only Dashboard
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, calendarFragment, "CALENDAR").hide(calendarFragment)
                .add(R.id.fragment_container, statisticsFragment, "STATISTICS").hide(statisticsFragment)
                .add(R.id.fragment_container, transactionsFragment, "TRANSACTIONS").hide(transactionsFragment)
                .add(R.id.fragment_container, dashboardFragment, "DASHBOARD")
                .commit();

        activeFragment = dashboardFragment;
    }

    /**
     * Handles Bottom Navigation selection.
     */
    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = getFragmentForNavigation(item.getItemId());
        if (selectedFragment != null && selectedFragment != activeFragment) {
            getSupportFragmentManager().beginTransaction()
                    .hide(activeFragment)
                    .show(selectedFragment)
                    .setReorderingAllowed(true)
                    .commit();
            activeFragment = selectedFragment;
            return true;
        }
        return false;
    }

    /**
     * Returns the corresponding fragment for the navigation item.
     */
    private Fragment getFragmentForNavigation(int itemId) {
        switch (itemId) {
            case R.id.navigation_dashboard:
                return dashboardFragment;
            case R.id.navigation_transactions:
                return transactionsFragment;
            case R.id.navigation_statistics:
                return statisticsFragment;
            case R.id.navigation_calendar:
                return calendarFragment;
            default:
                return null;
        }
    }
}

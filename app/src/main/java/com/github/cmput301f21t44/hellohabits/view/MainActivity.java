package com.github.cmput301f21t44.hellohabits.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.github.cmput301f21t44.hellohabits.R;
import com.github.cmput301f21t44.hellohabits.databinding.ActivityMainBinding;
import com.github.cmput301f21t44.hellohabits.viewmodel.PreviousListViewModel;
import com.github.cmput301f21t44.hellohabits.viewmodel.ViewModelFactory;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private NavController mNavController;
    private PreviousListViewModel mPreviousListViewModel;
    private boolean mFromViewHabit;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        mAuth = FirebaseAuth.getInstance();

        mPreviousListViewModel = ViewModelFactory.getProvider(this)
                .get(PreviousListViewModel.class);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.TodaysHabitsFragment,
                R.id.AllHabitsFragment
        )
                .setOpenableLayout(binding.drawerLayout)
                .build();

        mNavController = Navigation
                .findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI
                .setupActionBarWithNavController(this, mNavController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, mNavController);

        mPreviousListViewModel.getFromViewHabit()
                .observe(this, val -> this.mFromViewHabit = val);
        requireUser();
    }

    /**
     * Navigates to login if there is no user
     */
    private void requireUser() {
        if (mAuth.getCurrentUser() == null) {
            mNavController.navigate(R.id.loginFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_sign_out) {
            showSignOutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (mFromViewHabit) {
            getOnBackPressedDispatcher().onBackPressed();
            mPreviousListViewModel.setFromViewHabit(false);
            return true;
        }

        return NavigationUI.navigateUp(mNavController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Show an AlertDialog when trying to sign out
     */
    private void showSignOutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Sign out")
                .setMessage("Are you sure you want to sign out?")
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setPositiveButton("YES", (dialog, b) -> signOut())
                .setNegativeButton("NO", null).show();
    }

    /**
     * Signs out and restarts MainActivity to clear all LiveData listeners
     */
    private void signOut() {
        mAuth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
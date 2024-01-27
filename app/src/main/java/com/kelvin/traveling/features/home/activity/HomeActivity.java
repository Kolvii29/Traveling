package com.kelvin.traveling.features.home.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.Manifest;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kelvin.traveling.R;
import com.kelvin.traveling.core.transformers.Pager2_VerticalFlipTransformer;
import com.kelvin.traveling.features.home.adapter.PAdapterHome;
import com.kelvin.traveling.features.login.activity.LogInActivity;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private MaterialToolbar toolbar;
    private TabLayout tabLayout;
    private static final int REQUEST_COARSE_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.topBarApp);
        tabLayout = findViewById(R.id.tabLayoutHome);
        viewPager2 = findViewById(R.id.main_content_fragment);

        PAdapterHome myPagerAdapter = new PAdapterHome(this);
        viewPager2.setAdapter(myPagerAdapter);
        viewPager2.setPageTransformer(new Pager2_VerticalFlipTransformer());

        setTabLayout();
        setSupportActionBar(toolbar);
        logsInfoUser();
        VerifyPermissions();
    }

    // Permisos de localización
    private void VerifyPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            // El permiso no está concedido, hay que solicitarlo
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_COARSE_LOCATION_PERMISSION) {
            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showPermissionDeniedDialog();
            } else {
                VerifyPermissions();
            }
        }
    }

    public void showPermissionDeniedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission Error")
                .setMessage("This application requires location permissions to function properly. Please grant the permission in the app settings.")
                .setPositiveButton("Ok", (dialogInterface, i) -> finishAffinity())
                .setCancelable(false)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemMenu = item.getItemId();

        if (itemMenu == R.id.castle_nav) {
            String urlDisney = "https://www.disneylandparis.com/es-es/";
            Intent goToUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(urlDisney));
            startActivity(goToUrl);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int currentFragment = viewPager2.getCurrentItem();

        if (currentFragment == 0) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
            finish();
        } else {
            viewPager2.setCurrentItem(0, true);
        }
    }

    public void setTabLayout() {
        viewPager2.setCurrentItem(0);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {

                    switch (position) {
                        case 0 -> tab.setIcon(R.drawable.ic_home);
                        case 1 -> tab.setIcon(R.drawable.ic_car);
                        case 2 -> tab.setIcon(R.drawable.ic_mountains);
                        case 3 -> tab.setIcon(R.drawable.ic_user);
                        case 4 -> tab.setIcon(R.drawable.ic_camera);
                    }

                });
        tabLayoutMediator.attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Objects.requireNonNull(tab.getIcon()).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Objects.requireNonNull(tab.getIcon()).setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void logsInfoUser() {
        HomeActivityArgs args = HomeActivityArgs.fromBundle(getIntent().getExtras());

        String userEmail = args.getEmail();
        String userPass = args.getPassword();
        Log.d("HomeActivity", "Datos Recibidos " + userEmail + userPass);

        String message = "Email: " + userEmail + "\nPassword: " + userPass;
        Snackbar snackbar = Snackbar.make(viewPager2, message, Snackbar.LENGTH_SHORT);
        snackbar.show();

        Log.d("HomeActivity", "Email: " + userEmail);
        Log.d("HomeActivity", "Password: " + userPass);
    }

    public void setToolbarVisibility(boolean isVisible) {
        if (isVisible) {
            toolbar.setVisibility(View.VISIBLE);
        } else {
            toolbar.setVisibility(View.GONE);
        }
    }

    public void setToolbarBackButton() {
        Drawable ic_back = ContextCompat.getDrawable(this, R.drawable.ic_back);
        assert ic_back != null;
        ic_back.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(ic_back);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(v -> {
            viewPager2.setPageTransformer(new Pager2_VerticalFlipTransformer());
            viewPager2.setCurrentItem(0, true);
            getSupportFragmentManager().popBackStack();
        });
    }
}
package com.example.intershalanotesassinement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;//http://localhost:53189/

import com.example.intershalanotesassinement.database.PrefManager;
import com.example.intershalanotesassinement.fragment.SignupFragment;
import com.example.intershalanotesassinement.fragment.MainNoteFragment;
import com.example.intershalanotesassinement.fragment.SigninFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    public static SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        PrefManager prefManager = PrefManager.getInstance(getApplicationContext());
        prefs = prefManager.getPrefs();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");
        actionBar.setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            logoutButtonClicked(item.getActionView());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (prefs.getBoolean("is_signed_up", false) && prefs.getBoolean("is_signed_in", false)) {
            // Register bhi hai, signup bhi hai
            Toast.makeText(getApplicationContext(), "dono hai", Toast.LENGTH_LONG).show();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main, new MainNoteFragment())
                    .commit();
        } else if (prefs.getBoolean("is_signed_up", true) && prefs.getBoolean("is_signed_in", false)) {
            // Register hai, lekin signin nahi hai
            Toast.makeText(getApplicationContext(), "signin nahi hai", Toast.LENGTH_LONG).show();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main, new SigninFragment())
                    .commit();
        } else {
            // Register nahi hai
            Toast.makeText(getApplicationContext(), "signup nahi hai", Toast.LENGTH_LONG).show();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main, new SignupFragment())
                    .commit();
        }


    }

    public void logoutButtonClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Logout", (dialog, which) -> {
            mAuth.signOut();
            prefs.edit().putBoolean("is_signed_in", false).apply();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, new SigninFragment())
                    .commit();

        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}

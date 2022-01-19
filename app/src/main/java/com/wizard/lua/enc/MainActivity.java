package com.wizard.lua.enc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText script = findViewById(R.id.enc_script);
        final EditText password = findViewById(R.id.enc_password);

        findViewById(R.id.enc_start).setOnClickListener(v -> {
            if(password.getText().length() == 0){
                Toast.makeText(this, R.string.toast_password_empty, Toast.LENGTH_SHORT).show();
            }else if(script.getText().length() == 0){
                Toast.makeText(this, R.string.toast_script_empty, Toast.LENGTH_SHORT).show();
            }else{
                String encrypted = Lua.encrypt(
                        password.getText().toString(),
                        script.getText().toString()
                );
                script.setText(encrypted);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.enc_browse_output){
            Toast.makeText(this, R.string.toast_not_supported, Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.enc_load_file){
            ListView file = new ListView(this);
            DirectoryAdapter adapter = new DirectoryAdapter(getDataDir());
            file.setAdapter(adapter);
            new AlertDialog.Builder(this)
                    .setTitle("Load lua file")
                    .setView(file)
                    .setCancelable(false)
                    .setNeutralButton("Cancel", (dialog, which) -> dialog.cancel())
                    .show();
        }else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
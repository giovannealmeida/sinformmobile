package com.giog.sinformmobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.giog.sinformmobile.R;
import com.giog.sinformmobile.model.Course;
import com.giog.sinformmobile.model.Guest;

public class GuestDetailsActivity extends ActionBarActivity {

    private Guest guest;
    private TextView tvInstructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_details);

        this.guest = (Guest) getIntent().getSerializableExtra("guest");
        if(guest == null){
            Toast.makeText(this,"Convidado inválido",Toast.LENGTH_SHORT).show();
            finish();
        }

        ((TextView) findViewById(R.id.tvAbout)).setText(guest.getAbout());
        ((TextView) findViewById(R.id.tvName)).setText(guest.getName());
        ((TextView) findViewById(R.id.tvEmail)).setText(guest.getEmail());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Convidado");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

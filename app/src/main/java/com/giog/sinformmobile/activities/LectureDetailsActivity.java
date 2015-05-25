package com.giog.sinformmobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.giog.sinformmobile.R;
import com.giog.sinformmobile.model.Lecture;

public class LectureDetailsActivity extends ActionBarActivity implements View.OnClickListener {

    private Lecture lecture;
    private TextView tvInstructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_details);

        this.lecture = (Lecture) getIntent().getSerializableExtra("lecture");
        if(lecture == null){
            Toast.makeText(this,"Palestra inválida",Toast.LENGTH_SHORT).show();
            finish();
        }

        SpannableString content = new SpannableString(lecture.getGuest().getName());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        tvInstructor = (TextView) findViewById(R.id.tvInstructor);
        tvInstructor.setText(content);
        tvInstructor.setOnClickListener(this);
        ((TextView) findViewById(R.id.tvAbout)).setText(lecture.getAbout());
        ((TextView) findViewById(R.id.tvDate)).setText(lecture.getFormattedDate()+" - "+ lecture.getDayOfWeek());
        ((TextView) findViewById(R.id.tvTime)).setText(lecture.getFormattedTime());
        ((TextView) findViewById(R.id.tvLocal)).setText(lecture.getLocal());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(lecture.getTitle());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvInstructor:
                startActivity(new Intent(this, GuestDetailsActivity.class).putExtra("guest", lecture.getGuest()));
                break;
        }
    }
}

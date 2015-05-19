package com.giog.sinformmobile.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.giog.sinformmobile.R;
import com.giog.sinformmobile.model.Course;

public class CourseDetailsActivity extends ActionBarActivity implements View.OnClickListener {

    private Course course;
    private TextView tvInstructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        this.course = (Course) getIntent().getSerializableExtra("course");
        if(course == null){
            Toast.makeText(this,"Curso inválido",Toast.LENGTH_SHORT).show();
            finish();
        }

        SpannableString content = new SpannableString(course.getGuest().getName());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        tvInstructor = (TextView) findViewById(R.id.tvInstructor);
        tvInstructor.setText(content);
        tvInstructor.setOnClickListener(this);
        ((TextView) findViewById(R.id.tvAbout)).setText(course.getAbout());
        ((TextView) findViewById(R.id.tvDate)).setText(course.getFormattedDate()+" - "+course.getDayOfWeek());
        ((TextView) findViewById(R.id.tvTime)).setText(course.getFormattedTime());
        ((TextView) findViewById(R.id.tvLocal)).setText(course.getLocal());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(course.getTitle());
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
                startActivity(new Intent(this, GuestDetailsActivity.class).putExtra("guest", course.getGuest()));
                break;
        }
    }
}

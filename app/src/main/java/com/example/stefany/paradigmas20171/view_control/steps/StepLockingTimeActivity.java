package com.example.stefany.paradigmas20171.view_control.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.view_control.access.LoginActivity;
import com.example.stefany.paradigmas20171.view_control.access.ProfileActivity;
import com.example.stefany.paradigmas20171.model.infrastructure.Session;
import com.example.stefany.paradigmas20171.view_control.steps.required_subjects.StepSemesterSubjectsActivity;

public class StepLockingTimeActivity extends AppCompatActivity {

    private Button btnContinue;
    private Button btnExit;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_locking_time);
        btnContinue = (Button) findViewById(R.id.button_affirmative);
        btnExit = (Button) findViewById(R.id.button_negative);
        spinner = (Spinner) findViewById(R.id.spinner);

        String[] spinnerValues = new String[]{"1 Período", "2 Períodos", "3 Períodos", "4 Períodos"};
        spinner.setAdapter(new ArrayAdapter(StepLockingTimeActivity.this, R.layout.spinner_item, spinnerValues));
        spinner.setSelection(0);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StepSemesterSubjectsActivity.setSemesterNumber(1);
                int numberOfPeriodsLocked = spinner.getSelectedItemPosition();
                Session.adjustPeriods(numberOfPeriodsLocked + 1);
                Intent intentGoLockingTime = new Intent(StepLockingTimeActivity.this, StepSemesterSubjectsActivity.class);
                startActivity(intentGoLockingTime);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.isLogged()){
                    Intent intentGoSubjects = new Intent(StepLockingTimeActivity.this, LoginActivity.class);
                    startActivity(intentGoSubjects);
                    finish();
                } else {
                    Intent intentGoSubjects = new Intent(StepLockingTimeActivity.this, ProfileActivity.class);
                    startActivity(intentGoSubjects);
                    finish();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intentForward = new Intent(StepLockingTimeActivity.this, StepLockingAskActivity.class);
        finish();
        startActivity(intentForward);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}

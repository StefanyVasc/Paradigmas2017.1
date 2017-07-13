package com.example.stefany.paradigmas20171.view_control.results;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.stefany.paradigmas20171.R;
import com.example.stefany.paradigmas20171.model.infrastructure.Session;
import com.example.stefany.paradigmas20171.model.infrastructure.Subject;

import java.util.ArrayList;

public class OptmizeResutsActivity extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<Subject> subjects;
    private Subject[] subjectsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optmize_resuts);
        gridView = (GridView) findViewById(R.id.grid_view);
        testSubjects();
        allocate();
        gridView.setAdapter(new GridAdapter(subjectsArray));
    }

    public void allocate(){
        subjectsArray = new Subject[10];
        for (Subject subject : subjects){
            String[] parts = subject.getSchedule().split(";");
            for (int i = 0; i < parts.length; i++) {
                String schedule = parts[i];
                Subject s = subject;
                s.setSchedule(schedule);
                subjectsArray[getPosition(schedule)] = s;
            }
        }
    }

    public int getPosition(String schedule){
        int position = 0;
        String[] parts = schedule.split(" ");
        String day = parts[0];
        String time = parts[1];
        position = position + getRow(day) + getColumn(time);
        return position;
    }

    public int getRow(String day){
        int rowAdd = 0;
        Log.d("TIMEOFSCHEDULE", day);
        String[] days = new String[]{"SEG", "TER", "QUA", "QUI", "SEX"};
        for (int i = 0; i < days.length; i++){
            if (day.equals(days[i])){
                rowAdd = i;
                break;
            }
        }
        return rowAdd;
    }

    public int getColumn(String time){
        int columnAdd = 0;
        Log.d("DAYOFSCHEDULE", time);
        if (time.contains("10-12")){
            columnAdd = 5;
        }
        return columnAdd;
    }

    public void testSubjects(){
        subjects = Session.getSubjectManager().getPeriod(1).getSubjects();
        subjects.get(0).setSchedule("SEG 10-12;QUA 10-12");
        subjects.get(1).setSchedule("SEG 08-10;QUA 08-10");
        subjects.get(2).setSchedule("QUI 10-12");
        subjects.get(3).setSchedule("TER 10-12;QUI 08-10;SEX 08-10");
        subjects.get(4).setSchedule("TER 08-10;SEX 10-12");
    }

    private class GridAdapter extends BaseAdapter {

        private Subject[] subjects;

        public GridAdapter(Subject[] subjects){
            this.subjects = subjects;
        }

        @Override
        public int getCount() {
            return subjects.length;
        }

        @Override
        public Object getItem(int i) {
            return subjects[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.grid_view_item, viewGroup, false);
            }
            try {
                TextView subjectName = (TextView) view.findViewById(R.id.text_subject_name);
                subjectName.setText(subjects[i].getDescription());
                TextView subjectSchedule = (TextView) view.findViewById(R.id.text_subject_schedule);
                subjectSchedule.setText(subjects[i].getSchedule());
            } catch (Exception e){
                e.printStackTrace();
            }
            return view;
        }
    }
}

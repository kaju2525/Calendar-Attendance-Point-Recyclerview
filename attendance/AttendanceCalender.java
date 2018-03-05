package com.reachmyschool.attendance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.reachmyschool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class AttendanceCalender extends AppCompatActivity implements OnRangeSelectedListener {

/*

    {
  "success": true,
  "attlist": [
    {
      "status": "1",
      "atten_date": "2018-01-10"
    },
    {
      "status": "2",
      "atten_date": "2018-01-12"
    },
    {
      "status": "1",
      "atten_date": "2018-01-20"
    },
    {
      "status": "1",
      "atten_date": "2018-01-13"
    },
    {
      "status": "3",
      "atten_date": "2018-01-14"
    },
    {
      "status": "3",
      "atten_date": "2018-01-26"
    },
    {
      "status": "4",
      "atten_date": "2018-01-22"
    },
    {
      "status": "4",
      "atten_date": "2018-03-2"
    }
  ]
}


*/




    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private MaterialCalendarView materialCalendarView;
    private List<Model_Attendance> list;
    private List<String> dateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_activity_calender_attendance);

        materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
        materialCalendarView.setOnRangeSelectedListener(this);

        loadJSON();
    }


    private void loadJSON() {

        AndroidNetworking.get("https://raw.githubusercontent.com/karunkumarpune/Calender/master/calender_select.json")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Attandance Date"," Response "+ response);

                        try {
                        JSONArray jsonArray = response.getJSONArray("attlist");
                        list = new ArrayList<>();
                        dateList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            String dateString = object1.getString("atten_date");
                            list.add(new Model_Attendance(dateString, object1.getString("status")));
                            dateList.add(dateString);
                            materialCalendarView.selectRange(CalendarDay.from(sdf.parse(dateString)), CalendarDay.from(sdf.parse(dateString)));
                        }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    @Override
    public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
        for (int i=0; i<dates.size(); i++){
            //    widget.setDateSelected(dates.get(i), true);
            Log.d("Date OnRange", dates.get(i).getDate().toString());
            int indexOfDate = dateList.indexOf(sdf.format(dates.get(i).getDate()));
            Log.d("DateIndex", String.valueOf(indexOfDate));
            if (indexOfDate >= 0) {
                if (list.get(indexOfDate).getStatus().equals("1")) {
                    widget.setDateSelected(dates.get(i), false);
                    widget.addDecorator(new BookingDecorator(this, list.get(indexOfDate).getStatus() , dates));
                }
                else if (list.get(indexOfDate).getStatus().equals("3")) {
                    widget.setDateSelected(dates.get(i), false);
                    widget.addDecorator(new BookingDecorator(this, list.get(indexOfDate).getStatus() , dates));
                } else if (list.get(indexOfDate).getStatus().equals("4")) {
                    widget.setDateSelected(dates.get(i), false);
                    widget.addDecorator(new BookingDecorator(this, list.get(indexOfDate).getStatus() , dates));
                }
                else {
                    widget.setDateSelected(dates.get(i), false);
                    widget.addDecorator(new BookingDecorator(this, list.get(indexOfDate).getStatus() , dates));
                }
            }else {
                widget.setDateSelected(dates.get(i), false);
            }

        }
    }

}



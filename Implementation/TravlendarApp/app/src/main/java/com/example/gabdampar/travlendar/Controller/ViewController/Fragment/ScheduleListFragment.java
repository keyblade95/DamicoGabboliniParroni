package com.example.gabdampar.travlendar.Controller.ViewController.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.gabdampar.travlendar.Controller.AppointmentManager;
import com.example.gabdampar.travlendar.Controller.ScheduleManager;
import com.example.gabdampar.travlendar.Controller.ViewController.AppointmentCreationActivity;
import com.example.gabdampar.travlendar.Controller.ViewController.ScheduleCreationActivity;
import com.example.gabdampar.travlendar.Controller.ViewController.ScheduleListViewAdapter;
import com.example.gabdampar.travlendar.R;

import static android.app.Activity.RESULT_OK;

public class ScheduleListFragment extends Fragment {

    final int SCHEDULE_CREATION_REQUEST = 0;    // request code for startActivityForResult
    ListView scheduleListView;
    ScheduleListViewAdapter arrayAdapter;

    public ScheduleListFragment() {
        setHasOptionsMenu(true);    // to inflate appbar options menu
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getTitle().toString().equals("Current schedules")){
                arrayAdapter.showCurrentSchedules=true;
                arrayAdapter.getFilter().filter("");
            }
            if(item.getTitle().toString().equals("Past schedules")){
                arrayAdapter.showCurrentSchedules=false;
                arrayAdapter.getFilter().filter("");
            }
            return true;
        }

    };

    @Override
    public void onResume(){
        super.onResume();
        arrayAdapter.getFilter().filter("");
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_list, container, false);

        BottomNavigationView navigation = view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        scheduleListView = view.findViewById(R.id.scheduleListView);
        arrayAdapter = new ScheduleListViewAdapter(getActivity(), ScheduleManager.GetInstance().schedulesList);
        arrayAdapter.getFilter().filter("");
        scheduleListView.setAdapter(arrayAdapter);

        return view;
    }

    //Set the Icon on the action bar for the creation of a new schedule
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_schedule_creation, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_new_schedule:
                // User chose to add a new appointment
                Intent intent = new Intent(getActivity(), ScheduleCreationActivity.class);
                startActivityForResult(intent, SCHEDULE_CREATION_REQUEST);

            default:
                // If we got here, the user's action was not recognized, invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        switch (requestCode) {
            case SCHEDULE_CREATION_REQUEST:
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    // The user picked a contact.
                    // The Intent's data Uri identifies which contact was selected.

                    // refresh schedule list
                }
            break;
        }

    }


}
/**
 * Created by gabdampar on 01/12/2017.
 */

package com.example.gabdampar.travlendar.Model;

import android.support.annotation.NonNull;

import com.example.gabdampar.travlendar.Model.travelMean.TravelMean;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;


public class Schedule implements Comparable {

    public ArrayList<ScheduledAppointment> getScheduledAppts() {
        return scheduledAppts;
    }

    private ArrayList<ScheduledAppointment> scheduledAppts = new ArrayList<>();
    public OptCriteria criteria;

    public LocalDate getDate(){
        return scheduledAppts.get(0).originalAppointment.getDate();
    }

    public Schedule(ArrayList<TemporaryAppointment> apps) {
        // TODO: convert TemporaryAppointments into ScheduledAppointments.. works??
        for(TemporaryAppointment a : apps) {
            TravelMean m = a.means != null ? a.means.get(0).getMean() : null;
            scheduledAppts.add(new ScheduledAppointment(a.originalAppt, a.startingTime, a.ETA, m ));
        }
    }

    public Schedule(ArrayList<TemporaryAppointment> apps, OptCriteria criteria) {
        // TODO: convert TemporaryAppointments into ScheduledAppointments.. works??
        this(apps);
        this.criteria = criteria;
    }

    public LocalTime getTotalTravelTime(){
        int totalTravelTime = 0;
        for(int i=1; i<this.getScheduledAppts().size()-1; i++){
            totalTravelTime += this.getScheduledAppts().get(i).dataFromPreviousToThis.getTime().getDuration();
        }
        LocalTime t = new LocalTime(totalTravelTime /60,totalTravelTime%60);
        return t;
    }


    public float getTotalCost() {
        float cost = 0;
        for (int i=1; i < scheduledAppts.size()-1; i++) {
            ScheduledAppointment a1 = scheduledAppts.get(i);
            ScheduledAppointment a2 = scheduledAppts.get(i+1);
            cost += a2.travelMeanToUse.EstimateCost(a1.originalAppointment.coords, a2.originalAppointment.coords);
        }
        return cost/1000; //must count the km
    }

    public float getTotalCarbon() {
        float cost = 0;
        for (int i=1; i < scheduledAppts.size()-1; i++) {
            ScheduledAppointment a1 = scheduledAppts.get(i);
            ScheduledAppointment a2 = scheduledAppts.get(i+1);
            cost += a2.travelMeanToUse.EstimateCarbon(a1.originalAppointment.coords, a2.originalAppointment.coords);
        }
        return cost/1000; //must count the km
    }


    public String toString() {
        StringBuilder res = new StringBuilder();

        for(ScheduledAppointment appt : scheduledAppts) {
            if(appt.travelMeanToUse != null) {
                res.append(String.format("| %s: %s --%s-- %s --- %s | ", appt.toString(), appt.startingTime.toString("HH:mm"), appt.travelMeanToUse.toString(), appt.ETA.toString("HH:mm"), appt.endingTime().toString("HH:mm")));
            } else {
                res.append(String.format("| %s: %s |", appt.toString(), appt.endingTime().toString("HH:mm") ));
            }
        }
        res.append("\n------------");
        return res.toString();
    }

    public String toStringGraphical(){
        StringBuilder res = new StringBuilder();

        for(ScheduledAppointment appt : scheduledAppts) {
            if(appt.travelMeanToUse != null) {
                res.append(String.format("%s a.time: %s", appt.toString(), appt.ETA.toString("HH:mm")));
            }
        }
        return res.toString();
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Schedule s2 = (Schedule) o;
        switch (criteria) {
            case OPTIMIZE_TIME:
                return this.getTotalTravelTime().compareTo(s2.getTotalTravelTime());
            case OPTIMIZE_COST:
                return Float.compare( this.getTotalCost(), s2.getTotalCost() );
            case OPTIMIZE_CARBON:
                return Float.compare( this.getTotalCarbon(), s2.getTotalCarbon() );
        }
        return 2;
    }
}
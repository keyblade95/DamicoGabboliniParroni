package com.example.gabdampar.travlendar.Model.travelMean.publicMeans;

import com.example.gabdampar.travlendar.Controller.MapUtils;
import com.example.gabdampar.travlendar.Model.TemporaryAppointment;
import com.example.gabdampar.travlendar.Model.travelMean.PublicTravelMean;
import com.example.gabdampar.travlendar.Model.travelMean.TravelMean;
import com.example.gabdampar.travlendar.Model.travelMean.TravelMeanEnum;
import com.example.gabdampar.travlendar.Model.travelMean.privateMeans.Walk;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by federico on 11/12/17.
 */

public class Metro extends PublicTravelMean {

    private static Metro instance;

    protected static final float AVG_SPEED = 4.5f;                   // m/s
    protected static final float AVG_CARBON_EMISSION_PER_KM = 2;     // g/km
    protected static final float TICKET_COST = 1.5f;                 // euro
    protected static final float AVG_WAITING_SEC = 2*60f;            // sec


    public static Metro GetInstance() {
        if (instance == null) {
            instance = new Metro();
            TravelMean.MeansCollection.add(instance);
        }
        return instance;
    }

    public Metro() {
        super();
        super.meanEnum = TravelMeanEnum.METRO;
    }

    @Override
    public float EstimateTime(TemporaryAppointment from, TemporaryAppointment to, float distance) {
        LatLng stationFrom = from.originalAppt.distanceOfEachTransitStop.get(TravelMeanEnum.METRO);
        LatLng stationTo = from.originalAppt.distanceOfEachTransitStop.get(TravelMeanEnum.METRO);
        float totalTime = 0;

        if(stationFrom == null || stationTo == null){
            return -1;
        }else{
            //distance travelled with bus
            totalTime += EstimateTime(stationFrom,stationTo);
            //distance from first appointment to the stop
            totalTime += Walk.GetInstance().EstimateTime(from.originalAppt.coords,stationFrom);
            totalTime += Walk.GetInstance().EstimateTime(stationTo,to.originalAppt.coords);
        }
        return totalTime;
    }

    @Override
    public float EstimateTime(LatLng from, LatLng to) {
        return (MapUtils.distance(from,to)*1.3f)/AVG_SPEED + AVG_WAITING_SEC;
    }

    @Override
    public float EstimateCost(TemporaryAppointment from, TemporaryAppointment to, float distance) {
        return TICKET_COST;
    }

    @Override
    public float EstimateCarbon(TemporaryAppointment from, TemporaryAppointment to, float distance) {
        return distance * AVG_CARBON_EMISSION_PER_KM ;
    }


}
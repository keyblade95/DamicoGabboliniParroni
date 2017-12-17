/**
 * Created by gabdampar on 02/12/2017.
 */

package com.example.gabdampar.travlendar.Model.travelMean.privateMeans;

import com.example.gabdampar.travlendar.Model.Appointment;
import com.example.gabdampar.travlendar.Model.travelMean.PrivateTravelMean;
import com.example.gabdampar.travlendar.Model.travelMean.TravelMean;
import com.example.gabdampar.travlendar.Model.travelMean.TravelMeanEnum;

public class Bike extends PrivateTravelMean {

    protected static float averageBikeSpeed	= 0.007f;

    public Bike() {
        super();
        super.descr= TravelMeanEnum.BIKE;
    }

    public static Bike GetInstance() {
        if(instance == null) {
            instance = new Bike();
            TravelMean.MeansCollection.add(instance);
        }
        return (Bike)instance;
    }


    @Override
    public float EstimateTime(Appointment from, Appointment to, float distance) {
        return distance / averageBikeSpeed * 1.2f;
    }

    @Override
    public float EstimateCost(Appointment from, Appointment to, float distance) {
        return 0;
    }

    @Override
    public float EstimateCarbon(Appointment from, Appointment to, float distance) {
        return 0;
    }

}
package com.example.compassapp;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SignalSmoother {
    //using a linked list to store the incoming values
    private LinkedList<Double> list ;
    private LinkedList<Double> list2 ;
    //sum and avg needed to calculate the smoothed value
    private double sum=0 ;
    private double avg=0 ;

    //size of the linked list
    private float smoothIntensity ;

    public SignalSmoother(float intensity){
        list=new LinkedList<Double>() ;
        list2=new LinkedList<Double>() ;
        smoothIntensity=intensity ;
     }

     public double pushAndCalculate(Double value){

        double value2=value<0?value+360:value ;
        if(list.size()<smoothIntensity){
            //if the list is not full,add the new value and calculate the average of the values in the list
            list.addFirst(value);
            list2.addFirst(value2);
            sum=sum+value ;
            avg = sum / list.size() ;
        }else {
            //if the list is full,add the new value at the beginning and remove the last element
            //then, calculate the new average out of the list values
            list.addFirst(value);
            list2.addFirst(value2);
            sum=sum+value ;
            sum=sum-list.removeLast() ;
            avg=sum/smoothIntensity ;
        }

        return avg ;
     }
}

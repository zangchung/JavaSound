package com.java.test;

import java.util.Calendar;

public class SimpleCodeTester {
    
    
    public void countClock(){
        
        int min, hour;
        
        Integer minInteger;
        
        StringBuilder minBuilder = new StringBuilder();
        
        int total = 0;
        
        for(int i=0; i < 60; i++){
            minBuilder.append(i);
            
            if(isInclusionNumber3(minBuilder))
                total += 60;
            
            minBuilder.delete(0, minBuilder.length());
        }
        
        for(int i=1; i < 24; i++){
            minBuilder.append(i);
            
            if(isInclusionNumber3(minBuilder))
                total += 3600;
            else
                total += 900;
            
            minBuilder.delete(0, minBuilder.length());
        }
        
        System.out.println("total="+total);
        
    }
    
    private boolean isInclusionNumber3(StringBuilder minBuilder){
        
        for(int i=0; i<minBuilder.length(); i++){
            if(minBuilder.charAt(i) == '3'){
                System.out.println("minBuilder="+minBuilder);
                return true;
            }
                
        }
        
        
        return false;
    }
    
}

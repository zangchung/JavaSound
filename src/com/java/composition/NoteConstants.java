package com.java.composition;

import java.util.ArrayList;

import java.util.List;

public class NoteConstants {
    public static final int C = 0;
    public static final int D = 2;
    public static final int E = 4;
    public static final int F = 5;
    public static final int G = 7;
    public static final int A = 9;
    public static final int B = 11;
    
    public static final int D_FLAT = 1;
    public static final int E_FLAT = 3;
    public static final int G_FLAT = 6;
    public static final int A_FLAT = 8;
    public static final int B_FLAT = 10;
    
    public static final int SIXTEENTH_NOTE_CHANCE_RANGE = 47;
    public static final int EIGHTH_NOTE_CHANCE_RANGE = 92;
    public static final int QUARTER_NOTE_CHANCE_RANGE = 96;
    public static final int HALF_NOTE_CHANCE_RANGE = 98;
    public static final int WHOLE_NOTE_CHANCE_RANGE = 99;
    
    
//    public static final int[] RELATIVE_CHORD_C = { 0, 2, 4, 5, 7, 9, 11};
    
    public static final List<Boolean> chanceList = new ArrayList<Boolean>();
    
    static{
        for(int i=0; i<3; i++){
            chanceList.add(true);
        }
        
        for(int i=0; i<7; i++){
            chanceList.add(false);
        }
    }
    
    public static final List<Integer> WHITE_KEYS = new ArrayList<Integer>();    
    static{
        WHITE_KEYS.add(C);
        WHITE_KEYS.add(D);
        WHITE_KEYS.add(E);
        WHITE_KEYS.add(F);
        WHITE_KEYS.add(G);
        WHITE_KEYS.add(A);
        WHITE_KEYS.add(B);
    };
    
    public static final List<Integer> BLACK_KEYS = new ArrayList<Integer>();
    static{
        BLACK_KEYS.add(D_FLAT);
        BLACK_KEYS.add(E_FLAT);
        BLACK_KEYS.add(G_FLAT);
        BLACK_KEYS.add(A_FLAT);
        BLACK_KEYS.add(B_FLAT);
    };
    
    public static List<Integer> CHORD_LIST = new ArrayList<Integer>();
    
    static{
        CHORD_LIST.add(C);
        CHORD_LIST.add(D);
        CHORD_LIST.add(E);
        CHORD_LIST.add(F);
        CHORD_LIST.add(G);
        CHORD_LIST.add(A);
        CHORD_LIST.add(B);
    };
    
    public static void setChordList(int startChord){
        CHORD_LIST = null;
        CHORD_LIST = new ArrayList<Integer>();
        CHORD_LIST.add(startChord);
        CHORD_LIST.add(startChord+2);
        CHORD_LIST.add(startChord+2);
        CHORD_LIST.add(startChord+1);
        CHORD_LIST.add(startChord+2);
        CHORD_LIST.add(startChord+2);
        CHORD_LIST.add(startChord+2);
    }
    
    public static List<Integer> getChordList(int octave){
        List<Integer> resultList = new ArrayList<Integer>();
        
        for(Integer chord:CHORD_LIST){
            resultList.add(chord + GenerateChord.ONE_OCTAVE*octave);
        }
        
        return resultList;
    }
   
    
}

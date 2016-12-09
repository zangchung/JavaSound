package com.java.composition;

public class CompositionUtil {
    
    public static final int COUNT_BLACK_KEY = 30; 
    public static final int WHITE_KEY_OCTAVE = 7;
    public static final int BLACK_KEY_OCTAVE = 5;
    
    public static boolean isWhiteKey(int note){
        
        int origNote = note % GenerateChord.ONE_OCTAVE;
        
        switch(origNote){
        case NoteConstants.C:
        case NoteConstants.D:
        case NoteConstants.E:
        case NoteConstants.F:
        case NoteConstants.G:
        case NoteConstants.A:
        case NoteConstants.B:
            return true;
        default:
            return false;
            
        }
    }
    
    public static int convertBlackKey(int note){
        int octave = note / GenerateChord.ONE_OCTAVE;
        int keyIndex = note % GenerateChord.ONE_OCTAVE;
        int index = NoteConstants.BLACK_KEYS.indexOf(keyIndex);
        
        int blackKey = (octave * BLACK_KEY_OCTAVE) + index;
        
        return blackKey;
    }
    
    public static int convertWhiteKey(int note){
        
        int octave = note / GenerateChord.ONE_OCTAVE;
        int keyIndex = note % GenerateChord.ONE_OCTAVE;
        int index = NoteConstants.WHITE_KEYS.indexOf(keyIndex);
        
        int whiteKey = COUNT_BLACK_KEY + (octave * WHITE_KEY_OCTAVE) + index; 
        
        
        return whiteKey;
    }
    
    public static int convertKey(int note){
        if(isWhiteKey(note)){
            return convertWhiteKey(note);
        } else {
            return convertBlackKey(note);
        }
    }
    
}

package com.java.composition;

import java.util.Random;

import java.util.List;

import java.util.ArrayList;


// tocheck: How to make random value different probability?

public class GenerateChord {
    
//    private final int[] RELATIVE_CHORD_C = { 1, 3, 5, 6, 8, 10, 12};
    private static final int MAJOR_SECOND_NOTE_GAP = 4;
    private static final int MAJOR_THIRD_NOTE_GAP = 3;
    
    private static final int MINOR_SECOND_NOTE_GAP = 3;
    private static final int MINOR_THIRD_NOTE_GAP = 4;
    
    // tocheck: can change enum class
    public static long WHOLE_NOTE = 4000;
    public static long HALF_NOTE = 2000;
    public static long QUARTER_NOTE = 1000;
    public static long EIGHTH_NOTE = 500;
    public static long SIXTEENTH_NOTE = 250;
    
    public static final long ORIG_WHOLE_NOTE = 4000;
    public static final long ORIG_HALF_NOTE = 2000;
    public static final long ORIG_QUARTER_NOTE = 1000;
    public static final long ORIG_EIGHTH_NOTE = 500;
    public static final long ORIG_SIXTEENTH_NOTE = 250;
    
    
    private static final int NOTE_LENGTH_COUNT = 2; // Originally 5
    
    public static final int ONE_OCTAVE = 12;
    
    private static final int MAX_RANDOM_COUNT = 10;
    
    public static final int FIRST_OF_CHORD = 0;
    public static final int SECOND_OF_CHORD = 1;
    public static final int THIRD_OF_CHORD = 2;
    
    
    private static final int EXTEND_CHORD_VALUE = 7;
    
    
    public ChordInfo generateStandardChord(){        
        
        return generateChord(NoteConstants.CHORD_LIST.get(0) + 3*ONE_OCTAVE, true, 3, getNoteLength());
    }
    
    public ChordInfo generateStandardChord(long noteLength){        
        
        return generateChord(NoteConstants.CHORD_LIST.get(0) + 3*ONE_OCTAVE, true, 3, noteLength);
    }
    
    public ChordInfo generateNextChord(ChordInfo preChord, long noteLength){
        int preNote = preChord.getNote();
        int preOctave = preChord.getOctave();
        
        Random random = new Random();        
        int note = 0;
        
        for(int i=0; i < MAX_RANDOM_COUNT; i++){
            int noteIndex = random.nextInt(NoteConstants.CHORD_LIST.size());
            note = NoteConstants.CHORD_LIST.get(noteIndex) + ONE_OCTAVE * preOctave;
            
            if(preNote != note)
                break;
            
        }
        
        boolean isMajor = random.nextBoolean();        
        
        return generateChord(note, isMajor, preOctave, noteLength);
    }
    
    public ChordInfo generateNextChord(ChordInfo preChord){
        
        return generateNextChord(preChord, getNoteLength());
    }   

    
    private ChordInfo generateChord(int note, boolean isMajor, int octave, long noteLength){
        List<Integer> rightChord = new ArrayList<Integer>();        
        rightChord.add(note);
        if(isMajor){
            rightChord.add(rightChord.get(FIRST_OF_CHORD)+MAJOR_SECOND_NOTE_GAP);
            rightChord.add(rightChord.get(SECOND_OF_CHORD)+MAJOR_THIRD_NOTE_GAP);
        } else {
            rightChord.add(rightChord.get(FIRST_OF_CHORD)+MINOR_SECOND_NOTE_GAP);
            rightChord.add(rightChord.get(SECOND_OF_CHORD)+MINOR_THIRD_NOTE_GAP);
        }
        
        if(isExtendChord()){
            rightChord.add(note-2+ONE_OCTAVE);
            System.out.println("add 7 key");
        }
        
        int baseNote = getBaseNote(note, isMajor);
        
        List<Integer> leftChord = new ArrayList<Integer>();
        leftChord.add(baseNote-(ONE_OCTAVE*2));
        leftChord.add(baseNote-ONE_OCTAVE);
        
        ChordInfo resultChordInfo = new ChordInfo(rightChord, leftChord);
        resultChordInfo.setIsMajor(isMajor);
        resultChordInfo.setLength(noteLength);
        resultChordInfo.setOctave(octave);
        
        Random random = new Random();
        int chanceIndex = random.nextInt(NoteConstants.chanceList.size());        
        ChordInfo.MelodyInfo melodyInfo = getMelody(resultChordInfo, NoteConstants.chanceList.get(chanceIndex));
        resultChordInfo.setMelodyInfo(melodyInfo);
        
        
        
        return resultChordInfo;
    }
    
//    public synchronized List<Integer> getMelody(List<Integer> rightChord, long noteLength){
//        
//        Random random = new Random();
//        List<Integer> melody = new ArrayList<Integer>();
//        int noteCount = (int) (noteLength / 1000);
//        
//        for(int i=0; i<noteCount; i++){
//            int noteIndex = random.nextInt(rightChord.size());
//            melody.add(rightChord.get(noteIndex) + ONE_OCTAVE);            
//        }        
//        
//        return melody;
//    }
    
    public synchronized ChordInfo.MelodyInfo getMelody(ChordInfo chordInfo, boolean useOtherNote){
        
        Random random = new Random();
        List<Integer> melody = new ArrayList<Integer>();
        List<Long> melodyLength = new ArrayList<Long>();
//        int noteCount = (int) (chordInfo.getLength() / 1000);
        
        int otherNoteCount = 0;
        List<Integer> chordList = NoteConstants.getChordList(chordInfo.getOctave());
        
        for(long countLength=0; countLength < chordInfo.getLength();){
            
            List<Integer> baseMelody = null;
            
            // tocheck: add feature or not
            if(useOtherNote && otherNoteCount < 2){
                if(random.nextBoolean()){
                    baseMelody = chordList;
                    otherNoteCount++;
                }
            }
            
            if(baseMelody == null)
                baseMelody = chordInfo.getRightNotes();
            
            int noteIndex = random.nextInt(baseMelody.size());
            melody.add(baseMelody.get(noteIndex) + ONE_OCTAVE);
            
//            int lengthRange = getNewLength(chordInfo.getLength() - countLength);
//            
//            long resultLength = getNoteLength(random.nextInt(lengthRange));            
            
            long resultLength = getNoteLength(chordInfo.getLength() - countLength);
            
            melodyLength.add(resultLength);
            countLength += resultLength;
        }
        
        
//        for(int i=0; i<noteCount; i++){
//            int noteIndex = random.nextInt(chordInfo.getRightNotes().size());
//            melody.add(chordInfo.getRightNotes().get(noteIndex) + ONE_OCTAVE);            
//        }
        
        return chordInfo.new MelodyInfo(melody, melodyLength);
    }
    
    private int getNewLength(long remainLength){
        
        if(remainLength > QUARTER_NOTE){
            return 2;
        } else {
            return 3;
        }
        
    }
    
    private int getLengthRange(long remainLength){
        
        if(remainLength >= WHOLE_NOTE){
            return 5;
        } else if(remainLength < WHOLE_NOTE && remainLength >= HALF_NOTE){
            return 4;            
        } else if(remainLength < HALF_NOTE && remainLength >= QUARTER_NOTE){
            return 3;
        } else if(remainLength < QUARTER_NOTE && remainLength >= EIGHTH_NOTE){
            return 2;
        } else if(remainLength < EIGHTH_NOTE && remainLength >= SIXTEENTH_NOTE){
            return 1;
        } else {
            System.out.println("getLengthRange() out of remainLength="+remainLength);            
            return -1;
        }
            
        
    }
    
    private boolean isExtendChord(){
        Random random = new Random();
        
        switch(random.nextInt(10)){
        case EXTEND_CHORD_VALUE:            
            return true;
        default:
            return false;
        }
        
        
        
    }
    
    private long getNoteLength(long remainLength){
        int randomValue = -1;
        
        if(remainLength >= WHOLE_NOTE){
            randomValue = NoteConstants.WHOLE_NOTE_CHANCE_RANGE + 1; 
        } else if(remainLength < WHOLE_NOTE && remainLength >= HALF_NOTE){
            randomValue = NoteConstants.HALF_NOTE_CHANCE_RANGE + 1;
        } else if(remainLength < HALF_NOTE && remainLength >= QUARTER_NOTE){
            randomValue = NoteConstants.QUARTER_NOTE_CHANCE_RANGE + 1;
        } else if(remainLength < QUARTER_NOTE && remainLength >= EIGHTH_NOTE){
            randomValue = NoteConstants.EIGHTH_NOTE_CHANCE_RANGE + 1;
        } else if(remainLength < EIGHTH_NOTE && remainLength >= SIXTEENTH_NOTE){
            randomValue = NoteConstants.SIXTEENTH_NOTE_CHANCE_RANGE + 1;
        } else {
            System.out.println("out of range in getNoteLength()!!! "+remainLength);
        }
        
        Random random = new Random();
        int noteRangeValue = random.nextInt(randomValue);
        return getNoteLength2(noteRangeValue);
        
    }
    
    private long getNoteLength2(int randomValue){
        
        
        if(randomValue == NoteConstants.WHOLE_NOTE_CHANCE_RANGE){
              
            return WHOLE_NOTE;
        } else if(randomValue <= NoteConstants.HALF_NOTE_CHANCE_RANGE 
                && randomValue > NoteConstants.QUARTER_NOTE_CHANCE_RANGE){
            
            return HALF_NOTE;
        } else if(randomValue <= NoteConstants.QUARTER_NOTE_CHANCE_RANGE 
                && randomValue > NoteConstants.EIGHTH_NOTE_CHANCE_RANGE){
            
            return QUARTER_NOTE;
        } else if(randomValue <= NoteConstants.EIGHTH_NOTE_CHANCE_RANGE 
                && randomValue > NoteConstants.SIXTEENTH_NOTE_CHANCE_RANGE){
            
            return EIGHTH_NOTE;
        } else if(randomValue <= NoteConstants.SIXTEENTH_NOTE_CHANCE_RANGE){
            
            return SIXTEENTH_NOTE;
        } else {
            
            System.out.println("out of range in getNoteLength()!!! ");
            return -1;
        }
        
    }
    
    
    private long getNoteLength(int randomValue){
        
        
        switch(randomValue){
        case 0:
            return SIXTEENTH_NOTE;
        case 1:
            return EIGHTH_NOTE;
        case 2:
            return QUARTER_NOTE;
        case 3:            
            return HALF_NOTE;
        case 4:            
            return WHOLE_NOTE;
        default:
            System.out.println("getNoteLength() out of value!!="+randomValue);
            return -1;
        }
        
    }
    
    private int getBaseNote(int note, boolean isMajor){
        
        Random random = new Random();
        int baseType = random.nextInt(3);
        
        switch(baseType){
        case FIRST_OF_CHORD:
            return note;
        case SECOND_OF_CHORD:
            if(isMajor){
                return note + MAJOR_SECOND_NOTE_GAP;
            } else {
                return note + MINOR_SECOND_NOTE_GAP;
            }
        case THIRD_OF_CHORD:
            if(isMajor){
                return note + MAJOR_SECOND_NOTE_GAP + MAJOR_THIRD_NOTE_GAP;
            } else {
                return note + MINOR_SECOND_NOTE_GAP + MINOR_THIRD_NOTE_GAP;
            }
        default:
            System.out.println("default!!!! baseType="+baseType);
            return note;
        }
        
        
        
    }
    
    private long getNoteLength(){
        Random random = new Random();
        int noteLengthConstant = random.nextInt(NOTE_LENGTH_COUNT);
        switch(noteLengthConstant){
        case 0:
            return WHOLE_NOTE;
        case 1:
            return HALF_NOTE;
        default:
            return WHOLE_NOTE;
        }
    }
    
    public static void setTempo(int tempo){
        double coefficient = 1;
        
        switch(tempo){
        case 60:
            coefficient = 1;
            break;
        case 90:
            coefficient = 0.76;
            break;
        case 120:
            coefficient = 0.5;
            break;
        default:
            System.out.println("out of range in setTempo()="+tempo);
            break;
        }
        
        WHOLE_NOTE = (long) (ORIG_WHOLE_NOTE * coefficient);
        HALF_NOTE = (long) (ORIG_HALF_NOTE * coefficient);
        QUARTER_NOTE = (long) (ORIG_QUARTER_NOTE * coefficient);
        EIGHTH_NOTE = (long) (ORIG_EIGHTH_NOTE * coefficient);
        SIXTEENTH_NOTE = (long) (ORIG_SIXTEENTH_NOTE * coefficient);       
        
    }
    
}

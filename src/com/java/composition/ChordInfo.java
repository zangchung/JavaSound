package com.java.composition;
import java.util.List;




public class ChordInfo {
    
//    public enum Pitch {NONE, SHARP, FLAT};
    
    private long mLength;
    final List<Integer> mRightNotes, mLeftNotes;
//    final List<Long> mMelodyLength;
    private int mOctave;
//    private Pitch mRightPitch, mLeftPitch;
    private boolean bIsMajor;
    private MelodyInfo mMelodyInfo;
    
    
    public ChordInfo(List<Integer> rightNotes, List<Integer> leftNotes){
        mRightNotes = rightNotes;
        mLeftNotes = leftNotes;
//        mMelody = melody;
//        mMelodyLength = melodyLength;
        
    }
    
    public void setMelodyInfo(MelodyInfo melodyInfo){
        mMelodyInfo = melodyInfo; 
    }
    
    public MelodyInfo getMelodyInfo(){
        return mMelodyInfo;
    }

    
    public void setLength(long length){
        mLength = length;
    }
    
    public void setOctave(int octave){
        mOctave = octave;
    }
    
    public void setIsMajor(boolean isMajor){
        bIsMajor = isMajor;
    }
    
    public boolean isMajor(){
        return bIsMajor;
    }
    
    public List<Integer> getRightNotes(){
        return mRightNotes;
    }
    
    public int getNote(){
        return mRightNotes.get(0);
    }
    
    public int getOctave(){
        return mOctave;
    }
    
    public long getLength(){
        return mLength;
    }
    
    public void print(){
        
        if(mRightNotes.size() < 1){
            
            System.out.println("mRightNotes.size()="+mRightNotes.size());
            
            return;
        }   
        
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mRightNotes=");
        stringBuilder.append(mRightNotes.toString());
        stringBuilder.append(" mLeftNotes=");
        stringBuilder.append(mLeftNotes);
        stringBuilder.append(" chordString=");
        stringBuilder.append(convertChordToString());
        
        int note = mRightNotes.get(GenerateChord.FIRST_OF_CHORD) - GenerateChord.ONE_OCTAVE*2;
        
        if(note !=  mLeftNotes.get(GenerateChord.FIRST_OF_CHORD)){
            stringBuilder.append('/');
            stringBuilder.append(convertBaseNoteToString());
        }        
        
        System.out.println(stringBuilder);
        System.out.println("mLength="+mLength);               
    }
    
    private String convertChordToString(){
        
        if(mRightNotes.size() < 1)
            return null;
        
        int note = mRightNotes.get(GenerateChord.FIRST_OF_CHORD);
        StringBuilder stringBuilder = getCode(note-(getOctave()*GenerateChord.ONE_OCTAVE));
        if(!isMajor()){
            stringBuilder.append('m');
        }
        
        return stringBuilder.toString();
    }
    
    private String convertBaseNoteToString(){
        
        if(mLeftNotes.size() < 1)
            return null;
        
        int note = mLeftNotes.get(GenerateChord.FIRST_OF_CHORD);
        return getCode(note % GenerateChord.ONE_OCTAVE).toString();        
                
    }
    
    public ChordInfo copy(ChordInfo.MelodyInfo melody){
        
        ChordInfo chordInfo = new ChordInfo(mRightNotes, mLeftNotes);
        chordInfo.setMelodyInfo(melody);
        chordInfo.setLength(mLength);
        chordInfo.setOctave(mOctave);
        chordInfo.setIsMajor(bIsMajor);
        
        return chordInfo;
        
    }
    
    private StringBuilder getCode(int note){
        
        switch(note){
        case NoteConstants.C:
            return new StringBuilder("C");
        case NoteConstants.D:
            return new StringBuilder("D");
        case NoteConstants.E:
            return new StringBuilder("E");
        case NoteConstants.F:
            return new StringBuilder("F");
        case NoteConstants.G:
            return new StringBuilder("G");
        case NoteConstants.A:
            return new StringBuilder("A");
        case NoteConstants.B:
            return new StringBuilder("B");
            
        case NoteConstants.D_FLAT:
            return new StringBuilder("Db");
        case NoteConstants.E_FLAT:
            return new StringBuilder("Eb");
        case NoteConstants.G_FLAT:
            return new StringBuilder("Gb");
        case NoteConstants.A_FLAT:
            return new StringBuilder("Ab");
        case NoteConstants.B_FLAT:
            return new StringBuilder("Bb");            
        
        default:
            System.out.println("default null!!!note="+note);
            String a = null;
            try{
                a.toCharArray();
            }catch(Exception e){
                e.printStackTrace();
            }
            
            return null;
        }
        
        
    } 
    
    public class MelodyInfo{
        final List<Integer> mMelody;
        final List<Long> mLength;
        
        public MelodyInfo(List<Integer> melody, List<Long> length){
            mMelody = melody;
            mLength = length;
        }
    }
    
    
    
}

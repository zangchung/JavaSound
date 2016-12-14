package com.java.composition;

import java.util.Random;

import java.util.ArrayList;

import java.util.List;


// tocheck: duplication code
public class CompositionFactory {
    
    private static CompositionFactory mCompositionFactory;
    
    private CompositionFactory(){
        
    }
    
    public static CompositionFactory getCompositionFactory(){
        if(mCompositionFactory == null)
            mCompositionFactory = new CompositionFactory();
        
        return mCompositionFactory;
    }
    
    // make 8 bars and standard chord is used first and last
    // the other bars are made freely.
    public List<ChordInfo> getFreeStyleChord(){
        GenerateChord generateChord = new GenerateChord();
        List<ChordInfo> chordInfoList = new ArrayList<ChordInfo>();
        
        long musicLength = GenerateChord.WHOLE_NOTE * 8;
        ChordInfo chordInfo = generateChord.generateStandardChord();
        chordInfoList.add(chordInfo);
        
        long tempLength = chordInfo.getLength();
        for(; tempLength < (musicLength - GenerateChord.WHOLE_NOTE);){
            ChordInfo preChordInfo = chordInfoList.get(chordInfoList.size()-1);                    
            ChordInfo nextChordInfo = generateChord.generateNextChord(preChordInfo);
            chordInfoList.add(nextChordInfo);
            tempLength += nextChordInfo.getLength();
        }
        
        ChordInfo lastChordInfo = generateChord.generateStandardChord(musicLength-tempLength);
        
        chordInfoList.add(lastChordInfo);
        
        return chordInfoList;
    }
    
    // make 8 bars and standard chord is used first, half bar and last
    // the other bars are made freely.
    public List<ChordInfo> getStandardThreeChord(){
        GenerateChord generateChord = new GenerateChord();
        List<ChordInfo> chordInfoList = new ArrayList<ChordInfo>();
        
        long musicLength = GenerateChord.WHOLE_NOTE * 8;
        long musicLengthHalf = musicLength / 2;
        ChordInfo chordInfo = generateChord.generateStandardChord();
        chordInfoList.add(chordInfo);
        
        long tempLength = chordInfo.getLength();
        for(; tempLength < (musicLength - GenerateChord.WHOLE_NOTE);){
            
            ChordInfo preChordInfo = chordInfoList.get(chordInfoList.size()-1);
            
            if(tempLength == musicLengthHalf){                
                ChordInfo standardChordInfo = generateChord.generateStandardChord();
                chordInfoList.add(standardChordInfo);
                tempLength += standardChordInfo.getLength();
                
            } else if( (musicLengthHalf - tempLength) > 0 && (musicLengthHalf - tempLength) <= GenerateChord.WHOLE_NOTE ){
                ChordInfo halfChordInfo = generateChord.generateNextChord(preChordInfo, musicLengthHalf - tempLength);                
                chordInfoList.add(halfChordInfo);
                tempLength += halfChordInfo.getLength();
            } else {
                ChordInfo nextChordInfo = generateChord.generateNextChord(preChordInfo);
                chordInfoList.add(nextChordInfo);
                tempLength += nextChordInfo.getLength();
            }                    
            
        }
        
        ChordInfo lastChordInfo = generateChord.generateStandardChord(musicLength-tempLength);
        
        chordInfoList.add(lastChordInfo);
        
        return chordInfoList;
    }
    
    // make 8 bars that first 4 bars is same last 4 bars
    // and different melody
    public List<ChordInfo> getTwinChord(){
        
        GenerateChord generateChord = new GenerateChord();
        List<ChordInfo> chordInfoList = new ArrayList<ChordInfo>();
        
        long musicLength = GenerateChord.WHOLE_NOTE * 4;
        ChordInfo chordInfo = generateChord.generateStandardChord();
        chordInfoList.add(chordInfo);
        
        long tempLength = chordInfo.getLength();
        for(; tempLength < musicLength;){
            ChordInfo preChordInfo = chordInfoList.get(chordInfoList.size()-1);
            ChordInfo nextChordInfo = null;            
            if( (musicLength - tempLength) > GenerateChord.WHOLE_NOTE ){
                nextChordInfo = generateChord.generateNextChord(preChordInfo);
                
            } else {
                nextChordInfo = generateChord.generateNextChord(preChordInfo, (musicLength - tempLength));
            }
            
            chordInfoList.add(nextChordInfo);
            tempLength += nextChordInfo.getLength();            
        }
        
        int listSize = chordInfoList.size();
        Random random = new Random();
        for(int i=0; i<listSize-1; i++){
            ChordInfo chordInfoTemp = chordInfoList.get(i);
            int chanceIndex = random.nextInt(NoteConstants.chanceList.size());
            ChordInfo.MelodyInfo melodyInfo = generateChord.getMelody(chordInfoTemp, NoteConstants.chanceList.get(chanceIndex));
            
            ChordInfo nextChordInfo = chordInfoTemp.copy(melodyInfo);
            chordInfoList.add(nextChordInfo);
            tempLength += nextChordInfo.getLength();
        }
        
        ChordInfo lastChordInfo = generateChord.generateStandardChord(musicLength*2-tempLength);
        
        chordInfoList.add(lastChordInfo);
        
        return chordInfoList;
        
    }
    
    public List<ChordInfo> getOwnMelody(){
        int baseOctave = 3*GenerateChord.ONE_OCTAVE;
        
        ChordInfo resultChordInfo = new ChordInfo(new ArrayList<Integer>(), new ArrayList<Integer>());
        resultChordInfo.setIsMajor(true);
        resultChordInfo.setLength(GenerateChord.WHOLE_NOTE);
        resultChordInfo.setOctave(baseOctave);
        
        List<Integer> melody = new ArrayList<Integer>();
        List<Long> melodyLength = new ArrayList<Long>();
        
        melody.add(NoteConstants.G + baseOctave);
        melodyLength.add(GenerateChord.SIXTEENTH_NOTE);
        melody.add(NoteConstants.A + baseOctave);
        melodyLength.add(GenerateChord.SIXTEENTH_NOTE);
        melody.add(NoteConstants.B + baseOctave);
        melodyLength.add(GenerateChord.SIXTEENTH_NOTE);
        melody.add(NoteConstants.C + baseOctave+GenerateChord.ONE_OCTAVE);
        melodyLength.add(GenerateChord.SIXTEENTH_NOTE);
        melody.add(NoteConstants.D + baseOctave+GenerateChord.ONE_OCTAVE);
        melodyLength.add(GenerateChord.SIXTEENTH_NOTE);
        melody.add(NoteConstants.D + baseOctave+GenerateChord.ONE_OCTAVE);
        melodyLength.add(GenerateChord.SIXTEENTH_NOTE);
        melody.add(NoteConstants.D + baseOctave+GenerateChord.ONE_OCTAVE);
        melodyLength.add(GenerateChord.SIXTEENTH_NOTE);
        melody.add(NoteConstants.D + baseOctave+GenerateChord.ONE_OCTAVE);
        melodyLength.add(GenerateChord.SIXTEENTH_NOTE);
        melody.add(NoteConstants.G + baseOctave);
        melodyLength.add(GenerateChord.HALF_NOTE);
        
        
        ChordInfo.MelodyInfo melodyInfo = resultChordInfo.new MelodyInfo(melody, melodyLength);
        resultChordInfo.setMelodyInfo(melodyInfo);
        
        List<ChordInfo> chordInfoList = new ArrayList<ChordInfo>();
        chordInfoList.add(resultChordInfo);
        
        return chordInfoList;
        
    }
    
}

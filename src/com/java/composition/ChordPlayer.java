package com.java.composition;

import SoundDemo.MidiSynthInterface;

import java.util.ArrayList;

import java.util.concurrent.TimeUnit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import SoundDemo.MidiSynth;

import java.util.List;

import java.util.concurrent.Callable;

public class ChordPlayer implements ChordPlayerInterface{

    
    private List<ChordInfo> mChordInfoList;
    private MidiSynthInterface mMidiSynthInterface;
    private static int mIndex;
    private List<MidiSynth.Key> mKeyList = new ArrayList<MidiSynth.Key>();
    private boolean bIsMelody, bIsChord;
    
    public ChordPlayer(List<ChordInfo> rightChordInfoList, MidiSynthInterface midiSynthInterface){
        mChordInfoList = rightChordInfoList;        
        mMidiSynthInterface = midiSynthInterface;
    }
    
    public ChordPlayer(List<ChordInfo> rightChordInfoList){
        mChordInfoList = rightChordInfoList;
    }
    
    
    public void init(){
        mIndex = 0;
    }
    
    private void addNote(int note){
        int keyIndex = CompositionUtil.convertKey(note);
        mKeyList.add(mMidiSynthInterface.getKey(keyIndex));
    }
    
    public void play(boolean isPlayMelody, boolean isChord){
        
        bIsMelody = isPlayMelody;
        bIsChord = isChord;
        
        if(mIndex >= mChordInfoList.size()){
            System.out.println("end play");
            return;
        }
        
        ChordInfo chordInfo = mChordInfoList.get(mIndex);
        chordInfo.print();
        
        
        for(int note: chordInfo.mRightNotes){
            addNote(note);
        }
        
        for(int note: chordInfo.mLeftNotes){
            addNote(note);
        }
        
        
        MelodyPlayer melodyPlayer = new MelodyPlayer(chordInfo.getMelodyInfo(), mMidiSynthInterface, this);
        melodyPlayer.init();
        melodyPlayer.play(bIsMelody);
        
        if(bIsChord){
            for(MidiSynth.Key key: mKeyList){            
                key.on();
            }
        }       
        
        mIndex++;
        
        mMidiSynthInterface.repaintKey();
        
    }
    
    
    @Override
    public void stopAndNextPlay(){
        
        if(bIsChord){
            for(MidiSynth.Key key: mKeyList){
                key.off();
            }
        }                
        
        ChordPlayer chordPlayer = new ChordPlayer(mChordInfoList, mMidiSynthInterface);
        chordPlayer.play(bIsMelody, bIsChord);
    }  
    
    
    

}

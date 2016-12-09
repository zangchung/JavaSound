package com.java.composition;

import SoundDemo.MidiSynthInterface;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.util.ArrayList;

import SoundDemo.MidiSynth;

import java.util.List;

import java.util.concurrent.Callable;


public class MelodyPlayer implements Callable<Void>{
        
//    private final List<Integer> mMelody;
    private ChordInfo.MelodyInfo mMelodyInfo; 
    private MidiSynthInterface mMidiSynth;    
    MidiSynth.Key mKey;
    private static int mIndex;    
    private ChordPlayerInterface mChordPlayerInterface;    
    private boolean bIsMelodyPlay;
    private int melodyIndex;
    
//    public MelodyPlayer(List<Integer> melody, MidiSynthInterface midiSynth, ChordPlayerInterface chordPlayerInterface){
//        mMelody = melody;
//        mMidiSynth = midiSynth;
//        mChordPlayerInterface = chordPlayerInterface;
//    }
    
    public MelodyPlayer(ChordInfo.MelodyInfo melodyInfo, MidiSynthInterface midiSynth, ChordPlayerInterface chordPlayerInterface){
        mMelodyInfo = melodyInfo;
        mMidiSynth = midiSynth;
        mChordPlayerInterface = chordPlayerInterface;
    }
    
    
    
    public void init(){
        mIndex = 0;
    }    
    
    public void play(boolean isMelodyPlay){
        
        bIsMelodyPlay = isMelodyPlay;
        
//        System.out.println("mMelody.size()="+mMelody.size());
        
        if(mIndex >= mMelodyInfo.mMelody.size()){
            System.out.println("stopAndNextPlay()");
            mChordPlayerInterface.stopAndNextPlay();
            return;
        }
        
        melodyIndex = mMelodyInfo.mMelody.get(mIndex);
        int keyIndex = CompositionUtil.convertKey(mMelodyInfo.mMelody.get(mIndex));
        mKey = mMidiSynth.getKey(keyIndex);
        
        if(bIsMelodyPlay)
            mKey.on();
        
        mMidiSynth.repaintKey();
        
        long melodyLength = mMelodyInfo.mLength.get(mIndex);
        System.out.println("play melodyIndex="+melodyIndex+" melodyLength="+melodyLength);
        
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);
        executor.schedule(this, melodyLength, TimeUnit.MILLISECONDS);
        
        mIndex++;
        
    }
    
    
    @Override
    public Void call() throws Exception {

        if(bIsMelodyPlay)
            mKey.off();
//        System.out.println("stop melodyIndex="+melodyIndex+" this="+this);
        
        MelodyPlayer melodyPlayer = new MelodyPlayer(mMelodyInfo, mMidiSynth, mChordPlayerInterface);
        melodyPlayer.play(bIsMelodyPlay);
        
        return null;
    }

}

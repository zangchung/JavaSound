package SoundDemo;

import SoundDemo.MidiSynth.Key;


public interface MidiSynthInterface {
    public Key getKey(int index);
    public void repaintKey();
}

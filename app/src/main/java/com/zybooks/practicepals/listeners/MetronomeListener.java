package com.zybooks.practicepals.listeners;

public interface MetronomeListener {
    void onTempoChanged(int newTempo);

    void onTimeSignatureChanged(String newTimeSignature);

    void onMetronomeStarted();

    void onMetronomeStopped();

}

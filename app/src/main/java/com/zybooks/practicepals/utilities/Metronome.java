package com.zybooks.practicepals.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.zybooks.practicepals.listeners.MetronomeListener;
import com.zybooks.practicepals.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Metronome {

    // Static variable for the single instance of Metronome
    private static Metronome instance = null;

    private int tempo;
    private int numerator;
    private int denominator = 4;
    private String signatureString;
    private boolean isPlaying;
    private SoundPool soundPool;
    private int metronomeSoundId;
    private ScheduledExecutorService scheduler;
    private MetronomeListener listener;
    public static final int MAX_METRONOME_BPM = 300;
    public static final int MIN_METRONOME_BPM = 10;

    // Private constructor to prevent instantiation from outside the class
    private Metronome(Context context, int initialTempo, int initialNumerator, MetronomeListener listener) {
        this.tempo = initialTempo;
        this.numerator = initialNumerator;
        this.isPlaying = false;
        this.listener = listener;

        updateSignatureString();

        // Initialize SoundPool
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        this.soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();

        this.metronomeSoundId = soundPool.load(context, R.raw.audio1, 1);
    }

    // Public method to return the single instance of Metronome
    public static synchronized Metronome getInstance(Context context, int initialTempo, int initialNumerator, MetronomeListener listener) {
        if (instance == null) {
            instance = new Metronome(context, initialTempo, initialNumerator, listener);
        }
        return instance;
    }

    // Method to start the metronome
    @SuppressLint("DiscouragedApi")
    public void start() {
        if (isPlaying) return;

        isPlaying = true;
        scheduler = Executors.newSingleThreadScheduledExecutor();
        long interval = 60_000 / tempo;

        scheduler.scheduleAtFixedRate(() -> {
            if (isPlaying) {
                soundPool.play(metronomeSoundId, 1, 1, 0, 0, 1); // Play metronome tick
            }
        }, 0, interval, TimeUnit.MILLISECONDS);

        listener.onMetronomeStarted();
    }

    // Method to stop the metronome
    public void stop() {
        if (!isPlaying) return;

        isPlaying = false;
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
        listener.onMetronomeStopped();
    }

    // Method to set the tempo and restart the metronome if playing
    public void setTempo(int newTempo) {
        this.tempo = newTempo;
        if (isPlaying) {
            restart();
        }
        listener.onTempoChanged(newTempo);
    }

    public int getTempo() {
        return tempo;
    }

    public void setNumerator(int newNumerator) {
        this.numerator = newNumerator;
        updateSignatureString();
        if (isPlaying) {
            restart();
        }
        listener.onTimeSignatureChanged(signatureString);
    }

    public void updateSignatureString() {
        this.signatureString = String.valueOf(this.numerator) + "/" + String.valueOf(this.denominator);
    }

    public String getSignatureString() {
        return signatureString;
    }

    public int getNumerator() {
        return numerator;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    // Method to release resources
    public void release() {
        soundPool.release();
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }

    // Private method to restart the metronome
    private void restart() {
        stop();
        start();
    }
}

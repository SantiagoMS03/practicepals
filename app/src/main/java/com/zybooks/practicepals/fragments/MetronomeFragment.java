package com.zybooks.practicepals.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.zybooks.practicepals.dialogs.metronome.BpmKeypadDialog;
import com.zybooks.practicepals.listeners.MetronomeListener;
import com.zybooks.practicepals.dialogs.metronome.TimeSignatureDialog;
import com.zybooks.practicepals.databinding.FragmentMetronomeBinding;

import com.zybooks.practicepals.R;
import com.zybooks.practicepals.utilities.Metronome;


public class MetronomeFragment extends Fragment implements MetronomeListener {

    private Metronome metronome;
    private FragmentMetronomeBinding binding;
    public MetronomeFragment() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Metronome object with default values
        metronome = Metronome.getInstance(requireContext(), 60, 4, this);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentMetronomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup Play/Pause button
        binding.buttonPlayPause.setOnClickListener(v -> toggleMetronome());


        // Setup Increase BPM button
        binding.buttonBpmIncrease.setOnClickListener(v -> {
            int currentTempo = metronome.getTempo();
            if (currentTempo < Metronome.MAX_METRONOME_BPM) {
                metronome.setTempo(currentTempo + 1);  // Increase BPM by 1
            }
        });

        // Setup Decrease BPM button
        binding.buttonBpmDecrease.setOnClickListener(v -> {
            int currentTempo = metronome.getTempo();
            if (currentTempo > Metronome.MIN_METRONOME_BPM) {  // Ensure BPM doesn't go below 1
                metronome.setTempo(currentTempo - 1);
            }
        });

        // Setup BPM listener to open dialog and change BPM
        binding.valueTempo.setOnClickListener(v -> {
            BpmKeypadDialog dialog = new BpmKeypadDialog();
            dialog.show(getChildFragmentManager(), "BpmKeypadDialog");
        });

        // Setup Time Signature dialog listener
        binding.valueTimeSignature.setOnClickListener(v -> {
            TimeSignatureDialog dialog = new TimeSignatureDialog();
            dialog.show(getChildFragmentManager(), "TimeSignatureDialog");
        });


    }

    private void toggleMetronome() {
        if (metronome.isPlaying()) {
            metronome.stop();
        } else {
            metronome.start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        metronome.release();  // Cleanup metronome resources
        binding = null;
    }

    @Override
    public void onTempoChanged(int newTempo) {
        binding.valueTempo.setText(String.valueOf(newTempo));
    }

    @Override
    public void onTimeSignatureChanged(String newTimeSignature) {
        binding.valueTimeSignature.setText(newTimeSignature);
    }

    @Override
    public void onMetronomeStarted() {
        binding.buttonPlayPause.setImageResource(R.drawable.ic_pause); // Update UI to show pause icon
    }

    @Override
    public void onMetronomeStopped() {
        binding.buttonPlayPause.setImageResource(R.drawable.ic_play); // Update UI to show play icon
    }

}

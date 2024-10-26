package com.zybooks.practicepals.dialogs.metronome;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.fragment.app.DialogFragment;

import com.zybooks.practicepals.utilities.Metronome;
import com.zybooks.practicepals.R;

public class TimeSignatureDialog extends DialogFragment {

    private TextView timeSignatureDisplay;
    private int numerator;
    private int denominator = 4;
    private final int MINIMUM_TIME_SIGNATURE_BEATS = 2;
    private final int MAXIMUM_TIME_SIGNATURE_BEATS = 16;
    private Metronome metronome;  // Reference to Metronome object

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.metronome = Metronome.getInstance(requireContext(), 60, 4, null);

        View view = inflater.inflate(R.layout.dialog_time_signature_keypad, container, false);

        timeSignatureDisplay = view.findViewById(R.id.text_time_signature_display);

        // Set initial values from Metronome object
        numerator = metronome.getNumerator();
        updateTimeSignatureDisplay();

        // Set up button click listeners for + and - in numerator
        view.findViewById(R.id.button_time_signature_increment).setOnClickListener(v -> {
            increaseNumerator();
        });
        view.findViewById(R.id.button_time_signature_decrement).setOnClickListener(v -> {
            decreaseNumerator();
        });

        return view;
    }

    private void increaseNumerator() {
        String toastMessage = "";
        if (numerator < MAXIMUM_TIME_SIGNATURE_BEATS) {
            metronome.setNumerator(++numerator);
            updateTimeSignatureDisplay();
        } else {
//            Toast.makeText(getContext(), "Maximum beats is " + MAXIMUM_TIME_SIGNATURE_BEATS, Toast.LENGTH_SHORT).show();
        }
    }

    private void decreaseNumerator() {
        String toastMessage = "";
        if (numerator > MINIMUM_TIME_SIGNATURE_BEATS) {
            metronome.setNumerator(--numerator);
            updateTimeSignatureDisplay();
        } else {
//            Toast.makeText(getContext(), "Minimum beats is " + MINIMUM_TIME_SIGNATURE_BEATS, Toast.LENGTH_SHORT).show();
        }

    }

    private void changeNumerator(int difference) {
    }


    @SuppressLint("SetTextI18n")
    private void updateTimeSignatureDisplay() {
        timeSignatureDisplay.setText(metronome.getSignatureString());
    }

}

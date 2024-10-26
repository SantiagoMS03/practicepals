package com.zybooks.practicepals.dialogs.metronome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.zybooks.practicepals.utilities.Metronome;
import com.zybooks.practicepals.R;


public class BpmKeypadDialog extends DialogFragment {

    private TextView bpmDisplay;
    private StringBuilder bpmValue = new StringBuilder();
    private Metronome metronome;  // Hold reference to Metronome object

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.metronome = Metronome.getInstance(requireContext(), 60, 4, null);
        View view = inflater.inflate(R.layout.dialog_bpm_keypad, container, false);

        bpmDisplay = view.findViewById(R.id.display_bpm);

        // Set current BPM in the display if available
        bpmDisplay.setText(String.valueOf(metronome.getTempo()));

        // Set up button click listeners for each number
        setNumberClickListener(view, R.id.button_0, "0");
        setNumberClickListener(view, R.id.button_1, "1");
        setNumberClickListener(view, R.id.button_2, "2");
        setNumberClickListener(view, R.id.button_3, "3");
        setNumberClickListener(view, R.id.button_4, "4");
        setNumberClickListener(view, R.id.button_5, "5");
        setNumberClickListener(view, R.id.button_6, "6");
        setNumberClickListener(view, R.id.button_7, "7");
        setNumberClickListener(view, R.id.button_8, "8");
        setNumberClickListener(view, R.id.button_9, "9");

        // Clear button logic
        view.findViewById(R.id.button_clear).setOnClickListener(v -> {
            bpmValue.setLength(0); // Clear the BPM input
            bpmDisplay.setText("-");
        });

        // Set button logic
        view.findViewById(R.id.button_set).setOnClickListener(v -> {
            if (bpmValue.length() > 0) {
                try {
                    int bpm = Integer.parseInt(bpmValue.toString());
                    if (bpm > Metronome.MAX_METRONOME_BPM) {
                        Toast.makeText(getContext(), "Maximum BPM is " + Metronome.MAX_METRONOME_BPM, Toast.LENGTH_SHORT).show();
                        bpm = Metronome.MAX_METRONOME_BPM;
                    }
                    // Use the Metronome object to set the tempo
                    metronome.setTempo(bpm);
                    bpmDisplay.setText(String.valueOf(bpm));
                    dismiss();

                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Invalid BPM", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void setNumberClickListener(View view, int buttonId, String number) {
        view.findViewById(buttonId).setOnClickListener(v -> {
            if (bpmValue.length() < 3) { // Restrict to 3 digits max
                bpmValue.append(number);
                bpmDisplay.setText(bpmValue.toString());
            }
        });
    }
}

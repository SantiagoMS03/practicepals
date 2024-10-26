package com.zybooks.practicepals.fragments;

import static com.zybooks.practicepals.utilities.Stopwatch.calculateMinutes;
import static com.zybooks.practicepals.utilities.Stopwatch.calculateSeconds;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.zybooks.practicepals.R;
import com.zybooks.practicepals.database.entities.Piece;
import com.zybooks.practicepals.database.entities.PracticeLog;
import com.zybooks.practicepals.dialogs.stopwatch.NewPracticeLogDialog;
import com.zybooks.practicepals.repository.PieceRepository;
import com.zybooks.practicepals.repository.PracticeLogRepository;
import com.zybooks.practicepals.ui.pieces.PieceSpinnerAdapter;
import com.zybooks.practicepals.utilities.Stopwatch;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StopwatchFragment extends Fragment {

    private Stopwatch stopwatch;
    private PieceRepository pieceRepository;
    private PracticeLogRepository practiceLogRepository;
    private Spinner pieceSpinner;
    private Button startButton;
    private Button saveButton;
    private TextView elapsedTimeView;

    // ScheduledExecutorService for updating the stopwatch display
    private ScheduledExecutorService scheduler;
    private Handler mainHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);

        // Initialize repositories
        pieceRepository = PieceRepository.getInstance(requireActivity().getApplication());
        practiceLogRepository = PracticeLogRepository.getInstance(requireActivity().getApplication());

        stopwatch = Stopwatch.getInstance();

        // UI elements
        pieceSpinner = view.findViewById(R.id.piece_spinner);  // Spinner to select piece
        startButton = view.findViewById(R.id.start_button);
        saveButton = view.findViewById(R.id.stop_button);
        elapsedTimeView = view.findViewById(R.id.elapsed_time);

        // Initialize the handler for updating UI
        mainHandler = new Handler(Looper.getMainLooper());

        // Set up start button
        startButton.setOnClickListener(v -> {
            if (!stopwatch.isRunning()) {
                stopwatch.start();
                startUpdatingElapsedTime();  // Start updating the UI
                startButton.setText("Pause");
            }
            else {
                stopwatch.stop();
                stopUpdatingElapsedTime(); // Stop updating the UI
                startButton.setText("Continue");
            }
        });

        // Set up stop button
        saveButton.setOnClickListener(v -> {
            if (stopwatch.isRunning()) {
                stopwatch.stop();
            }
            long elapsedTime = stopwatch.getElapsedTime();

            // Stop updating the UI
            stopUpdatingElapsedTime();

            // Update the UI with the final elapsed time
            updateElapsedTime(elapsedTime);

            // Get the selected piece
            Piece selectedPiece = (Piece) pieceSpinner.getSelectedItem();

            // Create a new PracticeLog entry and update the total time for the selected piece
            if (selectedPiece != null && elapsedTime != 0) {
                savePracticeLog(selectedPiece, elapsedTime);
            }

            // Reset the stopwatch for future use
            stopwatch.reset();

            startButton.setText("Start");
            setDefaultStateTimer();

        });

        // Set up the piece dropdown (spinner)
        setupPieceDropdown();

        return view;
    }

    // Function to load the pieces into the dropdown (spinner)
    private void setupPieceDropdown() {
        pieceRepository.getAllPieces().observe(getViewLifecycleOwner(), pieceList -> {
            if (pieceList != null) {
                PieceSpinnerAdapter pieceSpinnerAdapter = new PieceSpinnerAdapter(requireContext(), pieceList);
                pieceSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pieceSpinner.setAdapter(pieceSpinnerAdapter);
            }
        });
    }


    private void savePracticeLog(Piece piece, long elapsedTime) {
        NewPracticeLogDialog dialog = new NewPracticeLogDialog();
        Bundle args = new Bundle();
        args.putInt("piece_id", piece.piece_id);
        args.putLong("elapsedTime", elapsedTime);
        dialog.setArguments(args);
        dialog.show(getChildFragmentManager(), "NewPracticeLogDialog");
    }

    // Format the elapsed time to MM:SS
    @SuppressLint("DefaultLocale")
    private String formatElapsedTime(long elapsedTimeMillis) {
        long minutes = calculateMinutes(elapsedTimeMillis, 0);
        long seconds = calculateSeconds(elapsedTimeMillis, minutes, 0);
        return formatTime(minutes, seconds);  // Format as MM:SS
    }


    @SuppressLint("DefaultLocale")
    private String timeUnitToString(long timeUnit) {
        return String.format("%02d", timeUnit);
    }

    private String formatTime(long minutes, long seconds) {
        return timeUnitToString(minutes) + ":" + timeUnitToString(seconds);
    }

    // Update the elapsed time in the TextView
    @SuppressLint("SetTextI18n")
    private void updateElapsedTime(long elapsedTime) {
        mainHandler.post(() -> elapsedTimeView.setText(formatElapsedTime(elapsedTime)));
    }

    private void setDefaultStateTimer() {
        mainHandler.post(() -> elapsedTimeView.setText("00:00"));
    }


    // Start updating the elapsed time display every second using ScheduledExecutorService
    @SuppressLint("DiscouragedApi")
    private void startUpdatingElapsedTime() {
        scheduler = Executors.newScheduledThreadPool(1);  // Use a single background thread for scheduling
        scheduler.scheduleAtFixedRate(() -> {
            long elapsedTime = stopwatch.getElapsedTime();
            updateElapsedTime(elapsedTime);  // Update the UI
        }, 0, 1, TimeUnit.SECONDS);  // Update every 1 second
    }

    // Stop updating the elapsed time display
    @SuppressLint("SetTextI18n")
    private void stopUpdatingElapsedTime() {
        if (scheduler != null && !scheduler.isShutdown()) {
            long elapsedTime = stopwatch.getElapsedTime();
            scheduler.shutdown();
            scheduler = null;
        }
    }
}

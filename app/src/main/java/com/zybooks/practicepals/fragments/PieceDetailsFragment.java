package com.zybooks.practicepals.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.zybooks.practicepals.database.entities.Piece;
import com.zybooks.practicepals.database.entities.PracticeLog;
import com.zybooks.practicepals.databinding.FragmentMetronomeBinding;
import com.zybooks.practicepals.databinding.FragmentPieceDetailsBinding;
import com.zybooks.practicepals.repository.PieceRepository;
import com.zybooks.practicepals.repository.PracticeLogRepository;
import com.zybooks.practicepals.ui.practicelogs.PracticeLogAdapter;

import static com.zybooks.practicepals.utilities.Stopwatch.formatHoursMinutesSeconds;

import java.util.List;
//import com.zybooks.practicepals.ui.practicelogs.PracticeLogAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class PieceDetailsFragment extends Fragment {

    private FragmentPieceDetailsBinding binding;
    private PieceRepository pieceRepository;
    private PracticeLogAdapter logAdapter;
    private PracticeLogRepository practiceLogRepository;
    private int pieceId;  // To hold the Piece ID

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pieceRepository = PieceRepository.getInstance(requireActivity().getApplication());
        practiceLogRepository = PracticeLogRepository.getInstance(requireActivity().getApplication());

        // Get the pieceId from the arguments
        if (getArguments() != null) {
            pieceId = getArguments().getInt("pieceId", -1);  // Default to -1 if no ID passed
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentPieceDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Load piece data from the repository using the pieceId
        loadPieceDetails();

        // Set up RecyclerView for practice logs
        binding.practiceLogsRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        logAdapter = new PracticeLogAdapter(null);
        binding.practiceLogsRecyclerview.setAdapter(logAdapter);

        // Load practice logs for this piece
        loadPracticeLogs();
    }

    private void loadPieceDetails() {
        LiveData<Piece> pieceLiveData = pieceRepository.getPieceById(pieceId);
        pieceLiveData.observe(getViewLifecycleOwner(), new Observer<Piece>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(Piece piece) {
                if (piece != null) {
                    binding.pieceTitle.setText(piece.name);
                    binding.composerName.setText(piece.composer);
                    binding.addedOnDate.setText("Added on: " + new java.util.Date(piece.dateAdded).toString());
                    binding.timePracticed.setText(formatTimePracticed(piece.totalTimePracticed));
                }
            }
        });
    }

    private void loadPracticeLogs() {
        LiveData<List<PracticeLog>> logsLiveData = practiceLogRepository.getPracticeLogsForPiece(pieceId);
        logsLiveData.observe(getViewLifecycleOwner(), logs -> logAdapter.updateLogs(logs));
    }

    private String formatTimePracticed(long timePracticed) {
        return "Total time practiced: " + formatHoursMinutesSeconds(timePracticed);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

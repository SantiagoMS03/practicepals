package com.zybooks.practicepals.dialogs.stopwatch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.zybooks.practicepals.R;
import com.zybooks.practicepals.database.entities.Piece;
import com.zybooks.practicepals.database.entities.PracticeLog;
import com.zybooks.practicepals.repository.PieceRepository;
import com.zybooks.practicepals.repository.PracticeLogRepository;


public class NewPracticeLogDialog extends DialogFragment {
    PracticeLogRepository logRepository;
    PieceRepository pieceRepository;
    Piece piece;
    long elapsedTime;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_log, container, false);

        logRepository = PracticeLogRepository.getInstance(requireActivity().getApplication());
        pieceRepository = PieceRepository.getInstance(requireActivity().getApplication());

        Bundle args = getArguments();
        assert args != null;
        int piece_id = args.getInt("piece_id");
        pieceRepository.getPieceById(piece_id).observe(getViewLifecycleOwner(), pieceData -> {
            if (pieceData != null) {
                piece = pieceData;  // Set piece data once it's loaded
            }
        });

        elapsedTime = args.getLong("elapsedTime");

        view.findViewById(R.id.new_log_dialog_save_button).setOnClickListener(v -> {
            EditText e = view.findViewById(R.id.new_log_dialog_log_text);
            String logText = e.getText().toString();
            createPracticeLog(logText);
            dismiss();
        });

        return view;

    }

    // Create a PracticeLog and update the selected piece's total practice time
    private void createPracticeLog(String logText) {
        PracticeLog log = new PracticeLog();
        log.pieceId = piece.piece_id;
        log.logText = logText;
        log.logDate = System.currentTimeMillis();
        log.logPracticeTime = elapsedTime;
        logRepository.insert(log);

        piece.totalTimePracticed += elapsedTime;
        pieceRepository.update(piece);
    }

}

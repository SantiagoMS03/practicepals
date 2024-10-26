package com.zybooks.practicepals.dialogs.pieces;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.zybooks.practicepals.R;
import com.zybooks.practicepals.database.entities.Piece;
import com.zybooks.practicepals.repository.PieceRepository;

public class NewPieceDialog extends DialogFragment {

    private EditText piece_name;
    private EditText composer_name;
    private PieceRepository repository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_piece, container, false);

        repository = PieceRepository.getInstance(requireActivity().getApplication());

        piece_name = view.findViewById(R.id.name_input);
        composer_name = view.findViewById(R.id.composer_input);

        view.findViewById(R.id.save_button).setOnClickListener(v -> {
            if (piece_name.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Piece name must not be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (composer_name.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Composer name must not be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            Piece newPiece = new Piece();
            newPiece.name = piece_name.getText().toString();
            newPiece.composer = composer_name.getText().toString();
            newPiece.dateAdded = System.currentTimeMillis();
            newPiece.totalTimePracticed = 0;

            // Hide the keyboard
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getView() != null) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }

            repository.insert(newPiece);
            dismiss();
        });

        return view;
    }


}

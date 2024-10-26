package com.zybooks.practicepals.ui.pieces;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.practicepals.database.entities.Piece;
import com.zybooks.practicepals.repository.PieceRepository;
import com.zybooks.practicepals.R;

public class AddPieceActivity extends AppCompatActivity {
    private PieceRepository pieceRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_piece);

        pieceRepository = PieceRepository.getInstance(getApplication());

        EditText nameInput = findViewById(R.id.name_input);
        EditText composerInput = findViewById(R.id.composer_input);
        Button saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String composer = composerInput.getText().toString();
            long dateAdded = System.currentTimeMillis();

            Piece piece = new Piece();
            piece.name = name;
            piece.composer = composer;
            piece.dateAdded = dateAdded;
            piece.totalTimePracticed = 0;

            pieceRepository.insert(piece);
            finish();  // Close activity
        });
    }
}

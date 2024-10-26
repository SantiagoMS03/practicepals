package com.zybooks.practicepals.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zybooks.practicepals.R;
import com.zybooks.practicepals.dialogs.pieces.NewPieceDialog;
import com.zybooks.practicepals.repository.PieceRepository;
import com.zybooks.practicepals.database.entities.Piece;
import com.zybooks.practicepals.ui.pieces.PieceRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class PiecesFragment extends Fragment {

    private PieceRecyclerViewAdapter adapter;
    private PieceRepository pieceRepository;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pieces, container, false);

        pieceRepository = PieceRepository.getInstance(requireActivity().getApplication());

        // Set up the RecyclerView
        recyclerView = view.findViewById(R.id.pieces_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the adapter with an empty list
        adapter = new PieceRecyclerViewAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        FloatingActionButton addPieceButton = view.findViewById(R.id.add_piece_button);
        addPieceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to add a new piece (e.g., opening a dialog to enter piece details)
                addNewPiece();
            }
        });

        // Load pieces from the database
        loadPieces();

        // Set up listener to handle row clicks
        adapter.setOnItemClickListener(piece -> {
            Bundle bundle = new Bundle();
            bundle.putInt("pieceId", piece.piece_id);  // Assuming Piece has an 'id' field
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_PiecesFragment_to_PieceDetailsFragment, bundle);
        });

        return view;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void addNewPiece() {
//        // Example code to add a new piece (you can modify this to show a dialog or open another activity)
//
//        Piece newPiece = new Piece();
//        newPiece.name = "New Piece";
//        newPiece.composer = "Unknown Composer";
//        newPiece.dateAdded = System.currentTimeMillis();
//        newPiece.totalTimePracticed = 0;
//
//        // Add the new piece to the database using the repository
//        pieceRepository.insert(newPiece);
        NewPieceDialog dialog = new NewPieceDialog();
        dialog.show(getChildFragmentManager(), "NewPieceDialog");
    }


    private void loadPieces() {
        LiveData<List<Piece>> piecesLiveData = pieceRepository.getAllPieces();
        piecesLiveData.observe(getViewLifecycleOwner(), pieces -> adapter.updatePieceList(pieces));
    }
}

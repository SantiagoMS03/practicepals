package com.zybooks.practicepals.ui.pieces;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.practicepals.R;
import com.zybooks.practicepals.database.entities.Piece;

import java.util.ArrayList;
import java.util.List;

public class PieceRecyclerViewAdapter extends RecyclerView.Adapter<PieceRecyclerViewAdapter.PieceViewHolder> {

    private List<Piece> pieceList = new ArrayList<>();
    private OnItemClickListener listener;

    public PieceRecyclerViewAdapter() {}
    public PieceRecyclerViewAdapter(List<Piece> pieceList) {
        this.pieceList = pieceList;
    }

    public interface OnItemClickListener {
        void onItemClick(Piece piece);
    }

    // Set the listener for item clicks
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void updatePieceList(List<Piece> newPieceList) {
        this.pieceList = newPieceList;
        notifyDataSetChanged();  // Refresh the RecyclerView
    }

    public static class PieceViewHolder extends RecyclerView.ViewHolder {
        public TextView pieceNameTextView;

        public PieceViewHolder(View itemView) {
            super(itemView);
            pieceNameTextView = itemView.findViewById(R.id.piece_name);
        }

        public void bind(final Piece piece, final OnItemClickListener listener) {
            pieceNameTextView.setText(piece.name);
            if (listener != null) {
                itemView.setOnClickListener(v -> listener.onItemClick(piece));  // Handle click event
            }
        }
    }

    @NonNull
    @Override
    public PieceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row_piece, parent, false);
        return new PieceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PieceViewHolder holder, int position) {
        Piece piece = pieceList.get(position);
        holder.bind(piece, listener);  // Bind data and handle clicks
    }

    @Override
    public int getItemCount() {
        return pieceList.size();
    }
}

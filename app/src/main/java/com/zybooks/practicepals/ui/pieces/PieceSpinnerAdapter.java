package com.zybooks.practicepals.ui.pieces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zybooks.practicepals.R;
import com.zybooks.practicepals.database.entities.Piece;

import java.util.List;

public class PieceSpinnerAdapter extends ArrayAdapter<Piece> {

    private Context context;
    private List<Piece> pieceList;

    public PieceSpinnerAdapter(@NonNull Context context, List<Piece> pieceList) {
        super(context, android.R.layout.simple_spinner_item, pieceList);
        this.context = context;
        this.pieceList = pieceList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        Piece piece = pieceList.get(position);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(piece.name);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        Piece piece = pieceList.get(position);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(piece.name);

        return convertView;
    }

    // Optionally override to set a custom resource for dropdown items
    @Override
    public void setDropDownViewResource(int resource) {
        super.setDropDownViewResource(resource);
    }
}

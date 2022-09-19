package com.example.minesweeper;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MineGridRecyclerAdapter extends RecyclerView.Adapter<MineGridRecyclerAdapter.MineTileViewHolder>{
    private List<Cell> cells;
    private OnCellClickListener listener;

    public MineGridRecyclerAdapter(List<Cell> cells, OnCellClickListener listener) {
        this.cells = cells;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MineTileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell, parent, false);
        return new MineTileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MineTileViewHolder holder, int position) {
        holder.bind(cells.get(position));
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return cells.size();
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
        notifyDataSetChanged();
    }

    class MineTileViewHolder extends RecyclerView.ViewHolder {
        TextView valueTextView;

        public MineTileViewHolder(@NonNull View itemView) {
            super(itemView);

            valueTextView = itemView.findViewById(R.id.item_cell_value);
        }

        public void bind(final Cell cell) {
            itemView.setBackgroundColor(Color.GREEN); //background color for base cells

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.cellClick(cell);
                }
            });

            if (cell.isRevealed()) {
                if (cell.getIndex() == Cell.BOMB) { //if the random index with the bomb is where the curr index it set as bomb
                    valueTextView.setText(R.string.bomb);
                    valueTextView.setText(R.string.bomb);
                } else if (cell.getIndex() == Cell.BLANK) {  //if no number
                    valueTextView.setText("");
                    itemView.setBackgroundColor(Color.GRAY);
                } else {
                    valueTextView.setText(String.valueOf(cell.getIndex())); //setting color of text for each number
                    itemView.setBackgroundColor(Color.GRAY);
                }
            } else if (cell.isFlagged()) { //setting cell with flag
                valueTextView.setText(R.string.flag);
            }
        }
    }
}

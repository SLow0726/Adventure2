package com.slow.adventure.Character;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slow.adventure.R;

import java.util.ArrayList;

public class CharacterSelectAdapter extends RecyclerView.Adapter<CharacterSelectAdapter.ViewHolder> {

    private final ArrayList<CharacterItem> mData;
    private String flag;
    private final CharacterClickListener listener;

    final int First = 0, Second = 1, Third = 2;

    @Override
    public int getItemViewType(int position) {

        if (position % 3 == 0) {
            Log.d("TAG", "1");
            return First;
        } else if (position % 3 == 1) {
            Log.d("TAG", "2");
            return Second;
        } else
            Log.d("TAG", "3");
        return Third;

    }

    public CharacterSelectAdapter(ArrayList<CharacterItem> mData, CharacterClickListener listener) {
        this.mData = mData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CharacterSelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_character, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtView.setText(String.valueOf(mData.get(position).getCharacterName()));
        holder.imaView.setImageResource(mData.get(position).getCharacterImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position == First)
                    listener.onItemClick(view, position);

                else if (position == Second)
                    listener.onItemClick(view, position);

                else
                    listener.onItemClick(view, position);
            }
        });


    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtView;
        private final ImageView imaView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtView = itemView.findViewById(R.id.textShow);
            imaView = itemView.findViewById(R.id.imageShow);
        }

    }

}

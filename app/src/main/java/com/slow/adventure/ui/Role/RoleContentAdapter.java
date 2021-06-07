package com.slow.adventure.ui.Role;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.slow.adventure.Character.CharacterSelectAdapter;
import com.slow.adventure.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class RoleContentAdapter extends RecyclerView.Adapter<RoleContentAdapter.ViewHolder> {
    ArrayList<RoleData> mData;
    int posi;

    public RoleContentAdapter(ArrayList<RoleData> mData, int posi) {
        this.mData = mData;
        this.posi = posi;
    }

    @NotNull
    @Override
    public RoleContentAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_role_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull RoleContentAdapter.ViewHolder holder, int position) {
        holder.txtView.setText(String.valueOf(mData.get(posi).getRoleName()));
        holder.imaView.setImageResource(mData.get(posi).getRoleImage());
        holder.txtContent.setText(mData.get(posi).getRoleContent());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtView,txtContent;
        private final ImageView imaView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtView = itemView.findViewById(R.id.textRoleName);
            imaView = itemView.findViewById(R.id.imageRole);
            txtContent = itemView.findViewById(R.id.textViewRoleContent);

        }
    }
}

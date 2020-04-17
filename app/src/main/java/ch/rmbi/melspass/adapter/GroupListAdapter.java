package ch.rmbi.melspass.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.room.GroupWithPass;
import ch.rmbi.melspass.utils.Icon;
import ch.rmbi.melspass.utils.IconList;
import ch.rmbi.melspass.view.MainActivity;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupViewHolder> {

    Activity parentActivity;

    public void setParentActivity(Activity parentActivity) {
        this.parentActivity = parentActivity;
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvGroupName;
        private final TextView tvDescription;
        private final TextView tvPassCount;
        private final ImageButton bEdit;
        private final RelativeLayout rlGroupListItem;
        private final ImageView ivIcon;

        private GroupViewHolder(View itemView) {
            super(itemView);
            tvGroupName = itemView.findViewById(R.id.tvGroupName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPassCount = itemView.findViewById(R.id.tvPassCount);
            bEdit = itemView.findViewById(R.id.bEdit);
            rlGroupListItem = itemView.findViewById(R.id.rlGroupListItem);
            ivIcon = itemView.findViewById(R.id.ivIcon);


        }
    }

    private final LayoutInflater mInflater;
    //private int size = 0 ;
    private List<GroupWithPass> groupsWithPass; // Cached copy of words
    private int row_index ;
    public GroupListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.group_list_layout, parent, false);
        return new GroupViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {

        GroupWithPass current = groupsWithPass.get(position);

        holder.tvGroupName.setText(current.group.getName());
        holder.tvDescription.setText(current.group.getDescription());
        holder.tvPassCount.setText(String.valueOf(current.passList.size()));
        Icon icon = IconList.getInstance(parentActivity.getBaseContext()).getIcon(current.group.getImageIndex());
        holder.ivIcon.setImageDrawable(icon.getDrawable());
        holder.ivIcon.setContentDescription(icon.getDescription());

        holder.bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)parentActivity).showGroup(false,false,position);
            }
        });

        if(((MainActivity)parentActivity).getGroupPosition()==position){
            holder.rlGroupListItem.setSelected(true);
        }
        else
        {
            holder.rlGroupListItem.setSelected(false);
        }


    }

    public void setGroupsWithPass(List<GroupWithPass> grps) {
        groupsWithPass = grps;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (groupsWithPass != null)
            return groupsWithPass.size();
        return 0;
    }



}

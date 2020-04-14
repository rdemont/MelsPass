package ch.rmbi.melspass.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.room.GroupWithPass;
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
        private final ImageButton bDelete;

        private GroupViewHolder(View itemView) {
            super(itemView);
            tvGroupName = itemView.findViewById(R.id.tvGroupName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPassCount = itemView.findViewById(R.id.tvPassCount);
            bEdit = itemView.findViewById(R.id.bEdit);
            bDelete = itemView.findViewById(R.id.bDelete);



        }
    }

    private final LayoutInflater mInflater;
    private List<GroupWithPass> groupsWithPass; // Cached copy of words

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

        if (groupsWithPass != null) {
            GroupWithPass current = groupsWithPass.get(position);
            holder.tvGroupName.setText(current.group.getName());
            holder.tvDescription.setText(current.group.getDescription());
            holder.tvPassCount.setText(String.valueOf(current.passList.size()));
            if (current.passList.size() <= 0)
            {
                holder.bDelete.setVisibility(View.VISIBLE);
            }else {
                holder.bDelete.setVisibility(View.INVISIBLE);
            }
            holder.bEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)parentActivity).show(groupsWithPass.get(position).group,true,position);
                }
            });

        } else {
            // Covers the case of data not being ready yet.
            holder.tvGroupName.setText("No Word");
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
        else return 0;
    }



}

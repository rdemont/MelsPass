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
import ch.rmbi.melspass.room.Group;
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
        private final ImageButton bOrderUp;
        private final ImageButton bOrderDown;

        private GroupViewHolder(View itemView) {
            super(itemView);
            tvGroupName = itemView.findViewById(R.id.tvGroupName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPassCount = itemView.findViewById(R.id.tvPassCount);
            bEdit = itemView.findViewById(R.id.bEdit);
            rlGroupListItem = itemView.findViewById(R.id.rlGroupListItem);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            bOrderUp = itemView.findViewById(R.id.bOrderUp);
            bOrderDown = itemView.findViewById(R.id.bOrderDown);


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
        //holder.tvPassCount.setText(String.valueOf(current.group.getOrderNumber()));
        Icon icon = IconList.getInstance(parentActivity.getBaseContext()).getIcon(current.group.getImageIndex());
        holder.ivIcon.setImageDrawable(icon.getDrawable());
        holder.ivIcon.setContentDescription(icon.getDescription());
        if (position <= 0){
            holder.bOrderUp.setVisibility(View.INVISIBLE);
        }
        if (position >= ((MainActivity)parentActivity).getGroupsWithPass().size()-1){
            holder.bOrderDown.setVisibility(View.INVISIBLE);
        }


        holder.bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)parentActivity).showGroup(false,false,position);
            }
        });

        holder.bOrderDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Group gCurrent = ((MainActivity)parentActivity).getGroupsWithPass().get(position).group;
                Group gNext = ((MainActivity)parentActivity).getGroupsWithPass().get(position+1).group;
                int oCurrent = gCurrent.getOrderNumber();
                int oNext = gNext.getOrderNumber();
                ((MainActivity)parentActivity).setWaitingLiveDate(true);
                ((MainActivity)parentActivity).setWaitingNext(MainActivity.NEXT_GROUP_LIST);
                if (oCurrent == oNext)
                {
                    gCurrent.setOrderNumber(++oNext);
                    ((MainActivity)parentActivity).getViewModel().update(gCurrent);
                }else {
                    gCurrent.setOrderNumber(oNext);
                    gNext.setOrderNumber(oCurrent);
                    ((MainActivity)parentActivity).getViewModel().update(gCurrent,gNext);
                }
                ((MainActivity)parentActivity).showGroupList();

            }
        });

        holder.bOrderUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Group gCurrent = ((MainActivity)parentActivity).getGroupsWithPass().get(position).group;
                Group gPrevious = ((MainActivity)parentActivity).getGroupsWithPass().get(position-1).group;
                int oCurrent = gCurrent.getOrderNumber();
                int oPrevious = gPrevious.getOrderNumber();
                ((MainActivity)parentActivity).setWaitingLiveDate(true);
                ((MainActivity)parentActivity).setWaitingNext(MainActivity.NEXT_GROUP_LIST);
                if (oCurrent == oPrevious)
                {
                    gCurrent.setOrderNumber(--oPrevious);
                    ((MainActivity)parentActivity).getViewModel().update(gCurrent);
                }else {
                    gCurrent.setOrderNumber(oPrevious);
                    gPrevious.setOrderNumber(oCurrent);
                    ((MainActivity)parentActivity).getViewModel().update(gCurrent,gPrevious);
                }
                ((MainActivity)parentActivity).showGroupList();

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

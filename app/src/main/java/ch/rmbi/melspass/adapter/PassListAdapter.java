package ch.rmbi.melspass.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.room.Pass;
import ch.rmbi.melspass.view.MainActivity;

public class PassListAdapter extends RecyclerView.Adapter<PassListAdapter.PassViewHolder>{

    Activity parentActivity;

    public void setParentActivity(Activity parentActivity) {
        this.parentActivity = parentActivity;
    }

    class PassViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvUsername;
        private final TextView tvName;
        private final LinearLayout rlPassListItem;

        private PassViewHolder(View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUserName);
            tvName = itemView.findViewById(R.id.tvName);
            rlPassListItem = itemView.findViewById(R.id.rlPassListItem);
        }
    }

    private final LayoutInflater mInflater;
    private List<Pass> passList; // Cached copy of words

    public PassListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PassListAdapter.PassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.pass_list_layout, parent, false);
        return new PassListAdapter.PassViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PassListAdapter.PassViewHolder holder, int position) {

        if (passList != null) {
            Pass current = passList.get(position);

            holder.tvName.setText(current.getName());
            holder.tvUsername.setText(current.getUsername());

        } else {
            // Covers the case of data not being ready yet.
            holder.tvUsername.setText("No Word");
        }


        if(((MainActivity)parentActivity).getGroupPosition()==position){
            holder.rlPassListItem.setSelected(true);
        }
        else
        {
            holder.rlPassListItem.setSelected(false);
        }
    }

    public void setPass(List<Pass> ps) {
        passList = ps;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (passList != null)
            return passList.size();
        else return 0;
    }


}

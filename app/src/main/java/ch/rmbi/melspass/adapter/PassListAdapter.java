package ch.rmbi.melspass.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.room.Pass;
import ch.rmbi.melspass.view.MainActivity;

public class PassListAdapter extends RecyclerView.Adapter<PassListAdapter.PassViewHolder>{

    Activity parentActivity;
    View viewSelected;

    public View getViewSelected() {
        return viewSelected;
    }

    /*
        boolean isSearchResult = false ;
        public void setIssearchResult(boolean isSearch ){
            isSearchResult = isSearch ;
        }
    */
    public void setParentActivity(Activity parentActivity) {
        this.parentActivity = parentActivity;
    }

    class PassViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvUsername;
        private final TextView tvName;
        private final TextView tvPassword;
        private final TextView tvUrl;
        private final ImageButton bEdit;
        private final ImageButton bCopyUserName;
        private final ImageButton bCopyUserPass;
        private final ImageButton bOpenUrl;
        private final RelativeLayout rlPassListItem;

        private PassViewHolder(View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUserName);
            tvName = itemView.findViewById(R.id.tvName);
            tvPassword = itemView.findViewById(R.id.tvPassword);
            rlPassListItem = itemView.findViewById(R.id.rlPassListItem);
            tvUrl = itemView.findViewById(R.id.tvUrl);
            bEdit = itemView.findViewById(R.id.bEdit);
            bCopyUserName = itemView.findViewById(R.id.bCopyUserName);
            bCopyUserPass = itemView.findViewById(R.id.bCopyUserPass);
            bOpenUrl = itemView.findViewById(R.id.bOpenUrl);
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
        Pass current = passList.get(position);

        holder.tvName.setText(current.getName());
        holder.tvUsername.setText(current.getUsername());

        if(((MainActivity)parentActivity).getPassPosition()==position){
            holder.rlPassListItem.setSelected(true);
            viewSelected = holder.rlPassListItem;
        }
        else
        {
            holder.rlPassListItem.setSelected(false);
        }


        if (!((MainActivity)parentActivity).getIsLargeScreen()) {

            holder.tvPassword.setText(current.getUserPass());
            holder.tvUrl.setText(current.getUrl());


            holder.bEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) parentActivity).showPass(true, false, position);
                }
            });

            holder.bOpenUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = current.getUrl().toString();
                    if (url != null) {
                        if (!url.startsWith("http")) {
                            url = "http://" + url;
                        }
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        ((MainActivity) parentActivity).startActivity(browserIntent);
                    }
                }
            });

            holder.bCopyUserPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipData clip = ClipData.newPlainText("UserPass", current.getUserPass());
                    ClipboardManager clipboard = (ClipboardManager) ((MainActivity) parentActivity).getApplicationContext().getSystemService(((MainActivity) parentActivity).getApplicationContext().CLIPBOARD_SERVICE);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(
                            ((MainActivity) parentActivity).getApplicationContext(),
                            ((MainActivity) parentActivity).getString(R.string.copy_password),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });

            holder.bCopyUserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipData clip = ClipData.newPlainText("Username", current.getUsername());
                    ClipboardManager clipboard = (ClipboardManager) ((MainActivity) parentActivity).getApplicationContext().getSystemService(((MainActivity) parentActivity).getApplicationContext().CLIPBOARD_SERVICE);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(
                            ((MainActivity) parentActivity).getApplicationContext(),
                            ((MainActivity) parentActivity).getString(R.string.copy_username),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
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

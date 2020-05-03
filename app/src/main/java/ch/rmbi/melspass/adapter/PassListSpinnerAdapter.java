package ch.rmbi.melspass.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.room.Pass;
import ch.rmbi.melspass.room.PassWithGroup;

public class PassListSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    Activity parentActivity;

    private List<PassWithGroup> passWithGroupList ;
    private Context context ;

    public PassListSpinnerAdapter(Context context, List<PassWithGroup> passWithGroupList) {
        super();

        this.passWithGroupList = passWithGroupList;
        this.context = context;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position,convertView,parent);
    }

    @Override
    public int getCount() {
        return passWithGroupList.size();
    }

    @Override
    public Object getItem(int position) {
        return passWithGroupList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position,convertView,parent);
    }


    public View getCustomView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.pass_list_spinner_layout, parent, false);

        TextView tvGroup = (TextView) rowView.findViewById(R.id.tvGroup);
        tvGroup.setText(passWithGroupList.get(position).group.getName());

        TextView tvPass = (TextView) rowView.findViewById(R.id.tvPass);
        tvPass.setText(passWithGroupList.get(position).pass.getName());
        return rowView;

    }


}

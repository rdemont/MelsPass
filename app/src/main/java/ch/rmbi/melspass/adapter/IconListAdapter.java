package ch.rmbi.melspass.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.utils.Icon;
import ch.rmbi.melspass.utils.IconList;

public class IconListAdapter extends BaseAdapter implements SpinnerAdapter {
    Activity parentActivity;

    private List<Icon> icons ;
    private Context context ;

    public IconListAdapter(Context context,List<Icon> iconList) {
        super();

        this.icons = iconList;
        this.context = context;
    }



    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position,convertView,parent);
    }

    @Override
    public int getCount() {
        return icons.size();
    }

    @Override
    public Object getItem(int position) {
        return icons.get(position);
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
        View rowView = inflater.inflate(R.layout.drawable_list_layout, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.tvDescription);
        textView.setText(icons.get(position).getDescription());

        ImageView imageView = (ImageView)rowView.findViewById(R.id.ivDrawable);
        imageView.setImageDrawable(icons.get(position).getDrawable());

        return rowView;

    }

}

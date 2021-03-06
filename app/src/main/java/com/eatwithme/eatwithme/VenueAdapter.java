package com.eatwithme.eatwithme;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hii on 6/13/15.
 */
public class VenueAdapter extends ArrayAdapter<RowItem> {


    Context context;

    public VenueAdapter(Context context, int resourceId, //resourceId=your layout
                                 List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtAddress;
        TextView txtTitle;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtAddress = (TextView) convertView.findViewById(R.id.address);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.venue);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtTitle.setText(rowItem.mVenue);
        holder.txtAddress.setText(rowItem.mAddress);
        if(rowItem.mImage != null) {
            holder.imageView.setBackgroundDrawable(new BitmapDrawable(context.getResources(), rowItem.mImage));
        }else {
            holder.imageView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.group_placeholder));
        }
        return convertView;
    }
}

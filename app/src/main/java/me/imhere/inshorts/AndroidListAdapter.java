package me.imhere.inshorts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

class AndroidListAdapter extends ArrayAdapter
{
    private ArrayList<News> newses;
    private ArrayList<Integer> favs;
    private LayoutInflater layoutInflater;

    AndroidListAdapter(Context context, ArrayList<News> newses, ArrayList<Integer> favs)
    {
        super(context, R.layout.list_item, newses);
        this.newses             = newses;
        this.favs               = favs;
        layoutInflater          = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View row                = convertView;
        ViewHolder viewHolder;
        if (row == null)
        {
            row                 = layoutInflater.inflate(R.layout.list_item, parent, false);
            viewHolder          = new ViewHolder(row);
            row.setTag(viewHolder);
        }
        else
        {
            viewHolder          = (ViewHolder) row.getTag();
        }

        viewHolder.textViewTitle.setText(newses.get(position).getTitle());
        viewHolder.textViewPublisher.setText(newses.get(position).getPublisher());
        viewHolder.textViewDate.setText(getDate(newses.get(position).getTimestamp().getTime()));

        if(favs==null)
        {
            viewHolder.imageViewFav.setVisibility(View.INVISIBLE);
        }
        else
        {
            if(!favs.contains(newses.get(position).getId()))
            {
                viewHolder.imageViewFav.setVisibility(View.INVISIBLE);
            }
            else
            {
                viewHolder.imageViewFav.setVisibility(View.VISIBLE);
            }
        }

        return row;
    }
    private class ViewHolder
    {
        TextView textViewTitle;
        TextView textViewPublisher;
        TextView textViewDate;
        ImageView imageViewFav;
        ViewHolder(View view)
        {
            this.textViewTitle       = (TextView) view.findViewById(R.id.textViewTitle);
            this.textViewPublisher   = (TextView) view.findViewById(R.id.textViewPublisher);
            this.textViewDate        = (TextView) view.findViewById(R.id.textViewDate);
            this.imageViewFav        = (ImageView)view.findViewById(R.id.imageViewFav);
        }
    }
    private String getDate(long time)
    {
        Calendar cal                 = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        return DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
    }
}

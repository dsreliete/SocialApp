package com.socialapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.socialapp.R;
import com.socialapp.bean.Timeline;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by eliete on 4/20/16.
 */
public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {

    private List<Timeline> timelineList;
    private Context context;


    public TimelineAdapter(List<Timeline> list) {
        this.timelineList = list;
    }

    @Override
    public TimelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        return new TimelineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimelineViewHolder holder, int position) {
        final Timeline timeline = timelineList.get(position);

        if (timeline.getPicture() != null){
            holder.image.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(timeline.getPicture())
                    .placeholder(R.drawable.default_placeholder)
                    .into(holder.image);

            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(timeline.getLink());
                    context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            });
        }


        if (timeline.getDescription() != null){
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(timeline.getDescription());
        }

        if (timeline.getMessage() != null) {
            holder.message.setVisibility(View.VISIBLE);
            holder.message.setText(timeline.getMessage());
        }

        if (timeline.getStory() != null){
            holder.message.setVisibility(View.VISIBLE);
            holder.message.setText(timeline.getStory());
        }

        if (timeline.getDayHour() != null){
            String dayHour = getFormattedDate(timeline.getDayHour());
            holder.date.setVisibility(View.VISIBLE);
            holder.date.setText(dayHour);
        }
    }


    @Override
    public int getItemCount() {
        return timelineList.size();
    }

    public void add(List<Timeline> list) {
        timelineList.addAll(list);
        notifyDataSetChanged();
    }

    public class TimelineViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.image_timeline)
        ImageView image;
        @Bind(R.id.text_date)
        TextView date;
        @Bind(R.id.text_message)
        TextView message;
        @Bind(R.id.text_description)
        TextView description;

        public TimelineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public String getFormattedDate(String day){
        day = day.replace("T", "");
        Date date;
        SimpleDateFormat dateInFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ssz");
        SimpleDateFormat dateOutFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {

            date = dateInFormat.parse(day);
            return dateOutFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}

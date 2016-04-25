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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by eliete on 4/20/16.
 */
public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Timeline> timelineList;
    private Context context;
    private final int PIC_TEXT = 0;
    private final int PIC_TEXT2 = 1;
    private final int TEXT = 2;
    private final int TEXT2 = 3;
    private final int TEXT3 = 4;

    public TimelineAdapter(List<Timeline> list) {
        this.timelineList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        View view;

        switch (viewType){
            case PIC_TEXT:
                view = inflater.inflate(R.layout.recycler_item_pic_text, parent, false);
                holder = new PicTextViewHolder(view);
                break;
            case PIC_TEXT2:
                view = inflater.inflate(R.layout.recycler_item_pic_text_text, parent, false);
                holder = new PicText2ViewHolder(view);
                break;
            case TEXT:
                view = inflater.inflate(R.layout.recycler_item_text, parent, false);
                holder = new TextViewHolder(view);
                break;
            case TEXT2:
                view = inflater.inflate(R.layout.recycler_item_text_text, parent, false);
                holder = new Text2ViewHolder(view);
                break;
            case TEXT3:
                view = inflater.inflate(R.layout.recycler_item_text_text_text, parent, false);
                holder = new Text3ViewHolder(view);
                break;
            default:
                view = inflater.inflate(R.layout.recycler_item_text, parent, false);
                holder = new TextViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case PIC_TEXT:
                PicTextViewHolder picTextVH = (PicTextViewHolder) holder;
                configurePicTextVH(picTextVH, position);
                break;
            case PIC_TEXT2:
                PicText2ViewHolder picText2VH = (PicText2ViewHolder) holder;
                configurePicText2VH(picText2VH, position);
                break;
            case TEXT:
                TextViewHolder textVH = (TextViewHolder) holder;
                configureTextVH(textVH, position);
                break;
            case TEXT2:
                Text2ViewHolder text2VH = (Text2ViewHolder) holder;
                configureText2VH(text2VH, position);
                break;
            case TEXT3:
                Text3ViewHolder text3VH = (Text3ViewHolder) holder;
                configureText3VH(text3VH, position);
                break;
            default:
                textVH = (TextViewHolder) holder;
                configureTextVH(textVH, position);
                break;
        }
    }

    //view holder message or story + pic
    private void configurePicTextVH(PicTextViewHolder picTextVH, int position) {
        picTextVH.getDate().setText(getTimelineItem(position).getDayHour());

        if (getTimelineItem(position).getMessage() != null){
            picTextVH.getMessage().setText(getTimelineItem(position).getMessage());
        }else if (getTimelineItem(position).getStory() != null){
            picTextVH.getMessage().setText(getTimelineItem(position).getStory());
        }

        setImagePicasso(getTimelineItem(position).getPicture(), picTextVH.getImage());
        setImageLink(getTimelineItem(position).getLink(), picTextVH.getImage());

    }

    //view holder message or story + pic + desc
    private void configurePicText2VH(PicText2ViewHolder picText2VH, int position) {
        picText2VH.getDate().setText(getTimelineItem(position).getDayHour());

        if (getTimelineItem(position).getMessage() != null){
            picText2VH.getMessage().setText(getTimelineItem(position).getMessage());
        }else if (getTimelineItem(position).getStory() != null){
            picText2VH.getMessage().setText(getTimelineItem(position).getStory());
        }

        picText2VH.getDescription().setText(getTimelineItem(position).getDescription());

        setImagePicasso(getTimelineItem(position).getPicture(), picText2VH.getImage());
        setImageLink(getTimelineItem(position).getLink(), picText2VH.getImage());
    }


    //viewholder message or story
    private void configureTextVH(TextViewHolder textVH, int position) {
        textVH.getDate().setText(getTimelineItem(position).getDayHour());

        if (getTimelineItem(position).getMessage() != null){
            textVH.getMessage().setText(getTimelineItem(position).getMessage());
        }else if (getTimelineItem(position).getStory() != null){
            textVH.getMessage().setText(getTimelineItem(position).getStory());
        }

    }

    //viewholder message or story + desc
    private void configureText2VH(Text2ViewHolder text2VH, int position) {

        text2VH.getDate().setText(getTimelineItem(position).getDayHour());

        if (getTimelineItem(position).getMessage() != null){
            text2VH.getMessage().setText(getTimelineItem(position).getMessage());
        }else if (getTimelineItem(position).getStory() != null){
            text2VH.getMessage().setText(getTimelineItem(position).getStory());
        }

        text2VH.getDescription().setText(getTimelineItem(position).getDescription());
    }

    private void configureText3VH(Text3ViewHolder text3VH, int position) {
        text3VH.getDate().setText(getTimelineItem(position).getDayHour());

        text3VH.getMessage().setText(getTimelineItem(position).getMessage());

        text3VH.getStory().setText(getTimelineItem(position).getStory());

        text3VH.getDescription().setText(getTimelineItem(position).getDescription());
    }

    private Timeline getTimelineItem(int position){
        return timelineList.get(position);
    }

    private void setImagePicasso(String image, ImageView imageView){
        Picasso.with(context)
                .load(image)
                .placeholder(R.drawable.default_placeholder)
                .into(imageView);
    }

    private void setImageLink(final String link, ImageView imageView){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(link);
                context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
         int viewType = 2;
        if (getTimelineItem(position).getPicture() != null){
            if (getTimelineItem(position).getMessage() != null){
                if (getTimelineItem(position).getDescription() != null){
                    //viewholder mensagem + picture + description
                    viewType = PIC_TEXT2;
                }else if (getTimelineItem(position).getStory() != null){
                    // viewholder mensagem + picture + story
                    viewType = PIC_TEXT2;
                }else {
                    //viewholder mensagem + picture
                    viewType = PIC_TEXT;
                }
            }else if (getTimelineItem(position).getStory() != null){
                if (getTimelineItem(position).getDescription() != null){
                    //viewholder story + picture + description
                    viewType = PIC_TEXT2;
                }else {
                    //viewholder story + picture
                    viewType = PIC_TEXT;
                }
            }
        }else if (getTimelineItem(position).getMessage() != null){
            if (getTimelineItem(position).getDescription() != null && getTimelineItem(position).getStory() != null){
                //viewholder mensagem + description + story
                viewType =  TEXT3;
            } else if (getTimelineItem(position).getDescription() != null){
                //viewholder mensagem + description
                viewType = TEXT2;
            }else if (getTimelineItem(position).getStory() != null){
                //viewholder mensagem + description
                viewType = TEXT2;
            }else {
                //viewholder mensagem
                viewType = TEXT;
            }
        }else if (getTimelineItem(position).getStory() != null){
            if (getTimelineItem(position).getDescription() != null && getTimelineItem(position).getMessage() != null){
                //viewholder story + description + message
                viewType =  TEXT3;
            } else if (getTimelineItem(position).getDescription() != null){
                //viewholder story + description
                viewType = TEXT2;
            }else if (getTimelineItem(position).getMessage() != null) {
                //viewholder story + message
                viewType = TEXT2;
            } else {
                //viewholder story
                viewType = TEXT;
            }
        }
        return viewType;
    }

    @Override
    public int getItemCount() {
        return timelineList.size();
    }

    public void add(List<Timeline> list) {
        timelineList.addAll(list);
        notifyDataSetChanged();
    }

    public class PicTextViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.image_timeline)
        ImageView image;
        @Bind(R.id.text_date)
        TextView date;
        @Bind(R.id.text_message)
        TextView message;

        public PicTextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ImageView getImage() {
            return image;
        }

        public TextView getDate() {
            return date;
        }

        public TextView getMessage() {
            return message;
        }
    }

    public class PicText2ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.image_timeline)
        ImageView image;
        @Bind(R.id.text_date)
        TextView date;
        @Bind(R.id.text_message)
        TextView message;
        @Bind(R.id.text_description)
        TextView description;

        public PicText2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ImageView getImage() {
            return image;
        }

        public TextView getDate() {
            return date;
        }

        public TextView getMessage() {
            return message;
        }

        public TextView getDescription() {
            return description;
        }

    }

    public class TextViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.text_date)
        TextView date;
        @Bind(R.id.text_message)
        TextView message;


        public TextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getDate() {
            return date;
        }

        public TextView getMessage() {
            return message;
        }


    }

    public class Text2ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.text_date)
        TextView date;
        @Bind(R.id.text_message)
        TextView message;
        @Bind(R.id.text_description)
        TextView description;

        public Text2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getDate() {
            return date;
        }

        public TextView getMessage() {
            return message;
        }

        public TextView getDescription() {
            return description;
        }


    }

    public class Text3ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.text_date)
        TextView date;
        @Bind(R.id.text_message)
        TextView message;
        @Bind(R.id.text_description)
        TextView description;
        @Bind(R.id.text_story)
        TextView story;

        public Text3ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getDate() {
            return date;
        }

        public TextView getMessage() {
            return message;
        }

        public TextView getDescription() {
            return description;
        }

        public TextView getStory() {
            return story;
        }
    }

}

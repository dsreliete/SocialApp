package com.socialapp.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by eliete on 4/20/16.
 */
public class Timeline {

    private String description;
    private String link;
    private String story;
    private String message;
    private String picture;
    private String dayHour;
    private String url;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDayHour() {
        return dayHour;
    }

    public void setDayHour(String dayHour) {
        this.dayHour = dayHour;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static List<Timeline> getTimelineFromJson(String response) {
        try {
            String url = "";
            JSONObject object = new JSONObject(response);
            List<Timeline> timelineList = new ArrayList<>();

            if (object.has("paging")) {
                url = object.getJSONObject("paging").getString("next");
            }

            if (object.has("data")){
                JSONArray dataArray = object.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++){
                    Timeline timeline = new Timeline();
                    JSONObject jsonObject = dataArray.getJSONObject(i);

                    if (jsonObject.has("message"))
                        timeline.message = jsonObject.getString("message");

                    if (jsonObject.has("description"))
                        timeline.description = jsonObject.getString("description");

                    if (jsonObject.has("link"))
                        timeline.link = jsonObject.getString("link");

                    if (jsonObject.has("story"))
                        timeline.story = jsonObject.getString("story");

                    if (jsonObject.has("full_picture"))
                        timeline.picture = jsonObject.getString("full_picture");

                    if (jsonObject.has("created_time"))
                        timeline.dayHour = getFormattedDay(jsonObject.getString("created_time"));

                    if (url != null)
                        timeline.url = url;

                    timelineList.add(timeline);
                }
            }
            return timelineList;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    private static String getFormattedDay(String created_time) {
        String day = created_time.replace("T", "");
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


    @Override
    public String toString() {
        return "\n" + "Timeline{" +
                "description='" + description + '\'' + "\n" +
                ", link='" + link + '\'' + "\n" +
                ", story='" + story + '\'' + "\n" +
                ", message='" + message + '\'' + "\n" +
                ", picture='" + picture + '\'' + "\n" +
                ", dayHour='" + dayHour + '\'' + "\n" +
                ", url='" + url + '\'' + "\n" +
                '}';
    }
}

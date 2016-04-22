package com.socialapp.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by eliete on 4/16/16.
 */
public class Person implements Serializable {

    private String name;
    private String email;
    private String profilePicture;
    private String idFacebook;
    private String location;
    private String birthday;
    private String bio;
    private String religion;
    private String relationShip;
    private String school;
    private String employer;

    public Person() {}

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public String getLocation() {
        return location;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(String relationShip) {
        this.relationShip = relationShip;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }


    public static Person getPersonFomJson(JSONObject jsonObject) {
        Person person = new Person();
        try {
            if (jsonObject.has("id")){
                String id = jsonObject.getString("id");
                person.setIdFacebook(id);

                StringBuilder sb = new StringBuilder(("https://graph.facebook.com/"));
                sb.append(id);
                sb.append("/picture?width=200&height=200");

                person.setProfilePicture(sb.toString());
            }

            if (jsonObject.has("name"))
                person.setName(jsonObject.getString("name"));

            if (jsonObject.has("email"))
                person.setEmail(jsonObject.getString("email"));

            if (jsonObject.has("birthday"))
                person.setBirthday(jsonObject.getString("birthday"));

            if (jsonObject.has("location"))
                person.setLocation(jsonObject.getJSONObject("location").getString("name"));

            if (jsonObject.has("relationship_status"))
                person.setRelationShip(jsonObject.getString("relationship_status"));

            if (jsonObject.has("bio"))
                person.setBio(jsonObject.getString("bio"));

            if (jsonObject.has("religion"))
                person.setReligion(jsonObject.getString("religion"));

            if (jsonObject.has("education")){
                JSONArray eduArray = jsonObject.getJSONArray("education");
                for (int i = 0; i < eduArray.length(); i++){

                    JSONObject object = eduArray.getJSONObject(i);
                    person.setSchool(object.getJSONObject("school").getString("name"));
                }
            }

            if (jsonObject.has("work")){
                JSONArray workArray = jsonObject.getJSONArray("work");
                for (int i = 0; i < workArray.length(); i++){

                    JSONObject object = workArray.getJSONObject(i);
                    person.setEmployer(object.getJSONObject("employer").getString("name"));

                }
            }

        } catch (JSONException e){
            e.printStackTrace();
        }
        return person;
    }
}

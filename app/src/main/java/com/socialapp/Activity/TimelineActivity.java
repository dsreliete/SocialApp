package com.socialapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.socialapp.Adapter.TimelineAdapter;
import com.socialapp.CircleImageView;
import com.socialapp.EndlessRecyclerViewScrollListener;
import com.socialapp.bean.Person;
import com.socialapp.R;
import com.socialapp.bean.Timeline;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by eliete on 4/16/16.
 */
public class TimelineActivity extends AppCompatActivity  {

    Toolbar toolbar;
    NavigationView navigationView;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    CircleImageView circleImageView;
    TextView nameTextView;
    TextView birthTextView;
    TextView locationTextView;
    TextView emailTextView;

    public static final String TAG = MainActivity.class.getName();

    private TimelineAdapter adapter;
    private List<Timeline> list = new ArrayList<>();
    private GraphRequest graphRequest;
    private LinearLayoutManager layoutManager;
    private Person person;

    public TimelineActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null)
            person = (Person) savedInstanceState.getSerializable(MainActivity.PERSON);
        else{
            if (getIntent() != null){
                Bundle b = getIntent().getExtras();
                person = (Person) b.getSerializable(MainActivity.PERSON);
            }
        }

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (navigationView != null) {
            View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
            circleImageView = ButterKnife.findById(headerLayout, R.id.circle_image);
            nameTextView = ButterKnife.findById(headerLayout, R.id.name_textView);
            birthTextView = ButterKnife.findById(headerLayout, R.id.birth_textView);
            locationTextView = ButterKnife.findById(headerLayout, R.id.location_textView);
            emailTextView = ButterKnife.findById(headerLayout, R.id.email_textView);
            setupDrawerContent(navigationView);
        }

        initDownloadTimeline();

        if (person != null){
            renderNavigationHeader();
        }

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                executeNextRequest(graphRequest);
            }
        });
        adapter = new TimelineAdapter(new ArrayList<Timeline>());
        recyclerView.setAdapter(adapter);


    }

    private void renderNavigationHeader() {
        nameTextView.setText(person.getName());
        emailTextView.setText(person.getEmail());
        birthTextView.setText(person.getBirthday());
        locationTextView.setText(person.getLocation());


        Picasso.with(this)
                .load(person.getProfilePicture())
                .placeholder(R.drawable.default_placeholder)
                .into(circleImageView);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
    }

    public void selectDrawerItem(MenuItem menuItem) {

        Intent intent = null;

        switch(menuItem.getItemId()) {
            case R.id.nav_init:
                intent = new Intent(this, OtherInfoActivity.class);
                intent.putExtra(MainActivity.PERSON, person);
                break;
        }

        if (intent != null)
            startActivity(intent);

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());

    }


    private void initDownloadTimeline() {
        GraphRequest request = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/feed",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            String raw = response.getRawResponse();
                            List<Timeline> timelineList = Timeline.getTimelineFromJson(raw);

                            GraphRequest req = response.getRequestForPagedResults(GraphResponse.PagingDirection.NEXT);
                            graphRequest = setParametersRequest(req);

                            if (timelineList != null)
                                list.addAll(timelineList);

                            adapter.add(list);

                        }
                    }
                }
        );
        GraphRequest req = setParametersRequest(request);
        req.executeAsync();
    }

    private GraphRequest setParametersRequest(GraphRequest request) {
        if (request != null){
            Bundle parameters = new Bundle();
            parameters.putString("fields", "description,link,message,place,story,full_picture,created_time");
            parameters.putString("limit", "10");
            request.setParameters(parameters);
        }
        return request;
    }

    private void executeNextRequest(GraphRequest request){
        if (request != null) {
            GraphRequest.Callback callback = new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse response) {
                    if (response != null) {
                        String raw = response.getRawResponse();

                        GraphRequest req = response.getRequestForPagedResults(GraphResponse.PagingDirection.NEXT);
                        graphRequest = setParametersRequest(req);

                        List<Timeline> timelineList = Timeline.getTimelineFromJson(raw);

                        if (timelineList != null) {
                            list.clear();
                            list.addAll(timelineList);
                        }
                        adapter.add(list);
                        int currentSize = adapter.getItemCount();
                        adapter.notifyItemRangeInserted(currentSize, list.size() - 1);
                    }
                }
            };

            request.setCallback(callback);
            request.executeAsync();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_logout:
                LoginManager.getInstance().logOut();
                startActivity(new Intent(this, MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MainActivity.PERSON, person);
    }

}

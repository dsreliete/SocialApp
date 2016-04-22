package com.socialapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.socialapp.bean.Person;
import com.socialapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by eliete on 4/22/16.
 */
public class OtherInfoActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.relation_textView)
    TextView relationTextView;
    @Bind(R.id.religion_textView)
    TextView religionTextView;
    @Bind(R.id.work_textView)
    TextView workTextView;
    @Bind(R.id.education_textView)
    TextView educationTextView;
    @Bind(R.id.bio_textView)
    TextView bioTextView;

    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        ButterKnife.bind(this);

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


        Spannable religion = setSpan("Religião: ");
        Spannable relation = setSpan("Relacionamento: ");
        Spannable work = setSpan("Trabalho: ");
        Spannable education = setSpan("Educação: ");
        Spannable bio = setSpan("Sobre você: ");


        if (person != null){
            religionTextView.setText(religion + person.getReligion());
            relationTextView.setText(relation + person.getRelationShip());
            workTextView.setText(work + person.getEmployer() );
            educationTextView.setText(education + person.getSchool());
            bioTextView.setText(bio + person.getBio());
        }


    }

    public Spannable setSpan(String content){
        SpannableStringBuilder spannablecontent = new SpannableStringBuilder(content);
        StyleSpan boldSpan = new StyleSpan(android.graphics.Typeface.BOLD);
        spannablecontent.setSpan(boldSpan, 0, spannablecontent.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannablecontent;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(this, intent);
                return true;
            case R.id.action_logout:
                LoginManager.getInstance().logOut();
                startActivity(new Intent(this, MainActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

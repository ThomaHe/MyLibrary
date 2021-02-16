package fr.henry.mylibrary.ui.details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import fr.henry.mylibrary.R;

public class DetailsActivity extends AppCompatActivity {

    public static final String THUMBNAIL = "THUMBNAIL";
    public static final String TITLE = "TITLE";
    public static final String AUTHORS = "AUTHORS";
    public static final String PUBLISHER = "PUBLISHER";
    public static final String DATE = "DATE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String PREVIEW = "PREVIEW";

    private TextView titleTextView;
    private TextView authorsTextView;
    private TextView publisherTextView;
    private TextView dateTextView;
    private TextView descriptionTextView;
    private Button previewButton;
    private ImageView thumbnail;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();
        Bundle extras = getIntent().getExtras();
        fillView(extras);

        previewButton.setOnClickListener(v -> navigateToPreviewLink(extras.getString(PREVIEW)));
    }



    private void initView(){
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.details_page_title));
        }
        titleTextView=findViewById(R.id.details_bookTitle);
        authorsTextView=findViewById(R.id.details_bookAuthors);
        publisherTextView=findViewById(R.id.details_bookPublisher);
        dateTextView=findViewById(R.id.details_bookPublishedDate);
        descriptionTextView=findViewById(R.id.details_bookDescription);
        previewButton=findViewById(R.id.details_previewBtn);
        thumbnail= findViewById(R.id.details_bookThumbnail);

    }

    private void fillView(Bundle extras){
        Glide.with(this).load(extras.getString(THUMBNAIL)).into(thumbnail);
        titleTextView.setText(extras.getString(TITLE));
        authorsTextView.setText(extras.getString(AUTHORS));
        publisherTextView.setText(extras.getString(PUBLISHER));
        dateTextView.setText(extras.getString(DATE));
        descriptionTextView.setText(extras.getString(DESCRIPTION));
    }

    private void navigateToPreviewLink(String link) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(link));
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
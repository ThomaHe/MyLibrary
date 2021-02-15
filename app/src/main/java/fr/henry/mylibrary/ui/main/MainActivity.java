package fr.henry.mylibrary.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.henry.mylibrary.R;
import fr.henry.mylibrary.ui.catalog.CatalogActivity;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    MainContract.MainPresenter mMainPresenter;

    private EditText titleTextView;
    private EditText authorTextView;
    private Button searchButton;
    private Button libraryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        searchButton.setOnClickListener(v -> mMainPresenter.onSearchButtonClick(titleTextView.getText().toString(),authorTextView.getText().toString()));

        libraryButton.setOnClickListener(v -> mMainPresenter.onLibraryButtonClick());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.onDestroy();
    }

    @Override
    public void navigateToSearch(String title, String author) {
        Intent intent = new Intent(this, CatalogActivity.class);
        Bundle extras = new Bundle();
        extras.putString(CatalogActivity.TYPE,CatalogActivity.SEARCH);
        extras.putString(CatalogActivity.TITLE,title);
        extras.putString(CatalogActivity.AUTHOR,author);
        intent.putExtras(extras);

        startActivity(intent);
    }

    @Override
    public void navigateToLibrary() {
        Intent intent = new Intent(this, CatalogActivity.class);
        Bundle extras = new Bundle();
        extras.putString(CatalogActivity.TYPE,CatalogActivity.LIBRARY);
        intent.putExtras(extras);
        startActivity(intent);
    }
    @Override
    public void emptySearch(){
        Toast.makeText(this, "aucune info renseignée", Toast.LENGTH_SHORT);
    }

    private void initView(){
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        titleTextView = findViewById(R.id.editText_title);
        authorTextView = findViewById(R.id.editText_author);
        searchButton = findViewById(R.id.search_btn);
        libraryButton = findViewById(R.id.library_btn);

        mMainPresenter = new MainPresenter(this);
    }


}
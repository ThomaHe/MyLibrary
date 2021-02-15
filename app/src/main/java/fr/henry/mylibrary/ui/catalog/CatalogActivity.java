package fr.henry.mylibrary.ui.catalog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import fr.henry.mylibrary.R;
import fr.henry.mylibrary.data.Book;

public class CatalogActivity extends AppCompatActivity implements CatalogContract.CatalogView{

    public static final String TYPE = "TYPE";
    public static final String SEARCH = "SEARCH";
    public static final String LIBRARY = "LIBRARY";

    public static final String TITLE = "TITLE";
    public static final String AUTHOR = "AUTHOR";

    CatalogContract.CatalogPresenter mCatalogPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        initView();

        Bundle extras = getIntent().getExtras();
        if(extras.getString(TYPE).equals(SEARCH))
        {
            mCatalogPresenter.searchOnline(extras.getString(TITLE),extras.getString(AUTHOR), getResources().getString(R.string.api_key));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCatalogPresenter.onDestroy();
    }

    private void initView(){
        mCatalogPresenter = new CatalogPresenter(this, new CatalogModel());
    }

    @Override
    public void onGetResult(List<Book> bookList) {

    }

    @Override
    public void onNoResult() {

    }
}
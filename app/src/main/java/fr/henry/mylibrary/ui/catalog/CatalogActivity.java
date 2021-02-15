package fr.henry.mylibrary.ui.catalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import fr.henry.mylibrary.R;
import fr.henry.mylibrary.data.Book;

public class CatalogActivity extends AppCompatActivity implements CatalogContract.CatalogView, CatalogAdapter.OnItemClickListener{

    public static final String TYPE = "TYPE";
    public static final String SEARCH = "SEARCH";
    public static final String LIBRARY = "LIBRARY";

    public static final String TITLE = "TITLE";
    public static final String AUTHOR = "AUTHOR";

    CatalogContract.CatalogPresenter mCatalogPresenter;
    private CatalogAdapter mAdapter;
    private RecyclerView recyclerView;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        initView();

        Bundle extras = getIntent().getExtras();
        if(extras.getString(TYPE).equals(SEARCH))
        {
            if(actionBar != null) {
                actionBar.setTitle(getString(R.string.search_page_title));
            }
            mCatalogPresenter.searchOnline(extras.getString(TITLE),extras.getString(AUTHOR), getResources().getString(R.string.api_key));
        }
        else {
            if(actionBar != null) {
                actionBar.setTitle(getString(R.string.library_page_title));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCatalogPresenter.onDestroy();
    }

    private void initView(){
        mCatalogPresenter = new CatalogPresenter(this, new CatalogModel());
        recyclerView = findViewById(R.id.recycler_books);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onGetResult(List<Book> bookList) {
        setAdapter(bookList);
    }

    @Override
    public void onNoResult() {

    }

    private void setAdapter(List<Book> bookList){
        mAdapter = new CatalogAdapter(getApplicationContext(),bookList);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(Book book) {
        Toast.makeText(getApplicationContext(), "cliqué sur"+book.getTitle(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBookmarkClick(Book book) {
        Toast.makeText(getApplicationContext(), "bookmarké : "+book.getTitle(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
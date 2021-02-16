package fr.henry.mylibrary.ui.catalog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.henry.mylibrary.R;
import fr.henry.mylibrary.data.Book;
import fr.henry.mylibrary.data.database.BookDatabase;
import fr.henry.mylibrary.ui.details.DetailsActivity;

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
    private String pageType;

    private List<Book> bookList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);


        initView();

        Bundle extras = getIntent().getExtras();
        pageType = extras.getString(TYPE);
        if(pageType.equals(SEARCH))
        {
            if(actionBar != null) {
                actionBar.setTitle(getString(R.string.search_page_title));
            }
            mCatalogPresenter.searchOnline(extras.getString(TITLE),extras.getString(AUTHOR), getResources().getString(R.string.api_key));
        }
        else {
            if(actionBar != null)
                actionBar.setTitle(getString(R.string.library_page_title));

            mCatalogPresenter.getLibrary();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCatalogPresenter.onDestroy();
    }

    private void initView(){
        BookDatabase db = BookDatabase.getDatabase(getApplication());
        mCatalogPresenter = new CatalogPresenter(this, new CatalogModel(db));
        bookList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_books);
        setAdapter();

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onGetResult(List<Book> books) {
        runOnUiThread(() -> {
            bookList.clear();
            bookList.addAll(books);
            mAdapter.notifyDataSetChanged();
        });

    }

    @Override
    public void onNoResult() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.no_result_message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBookmarkAdded(String title) {
        runOnUiThread(() -> {
            String text = title +" " +getString(R.string.bookmark_added);
            Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void onBookmarkDeleted(Book book) {
       bookList.remove(book);
       mAdapter.notifyDataSetChanged();
    }

    private void setAdapter(){
        mAdapter = new CatalogAdapter(getApplicationContext(),bookList);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(Book book) {
        Bundle extras = mCatalogPresenter.createNavigationArguments(book);

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void onBookmarkClick(Book book) {
        if(pageType.equals(SEARCH)) {
            mCatalogPresenter.addBookmark(book);
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.delete_bookmark_message);
            builder.setPositiveButton(R.string.ok, (dialog, id) -> mCatalogPresenter.deleteBookmark(book));
            builder.setNegativeButton(R.string.cancel, (dialog, id) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
        }

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
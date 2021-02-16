package fr.henry.mylibrary.ui.catalog;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.Executors;

import fr.henry.mylibrary.data.Book;
import fr.henry.mylibrary.network.ApiService;
import fr.henry.mylibrary.network.response.Response;
import fr.henry.mylibrary.ui.details.DetailsActivity;
import retrofit2.Call;
import retrofit2.Callback;

class CatalogPresenter implements CatalogContract.CatalogPresenter{
    private CatalogContract.CatalogView catView;
    private CatalogContract.CatalogModel catModel;

    public CatalogPresenter(CatalogContract.CatalogView view, CatalogContract.CatalogModel model) {
        this.catView = view;
        this.catModel = model;
    }

    @Override
    public void searchOnline(String title, String author, String key) {
        //L'api ne sait pas faire de recherches sur l'auteur uniquement, elle renvoie de toute façon les livres contenant l'auteur dans le titre
        if(title.isEmpty()){
            title=author;
            author=null;
        }
        Call<Response> call = ApiService
                .retrofit
                .create(ApiService.class)
                .getBooks(title, author, key);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@Nullable Call<Response> call,
                                   @Nullable retrofit2.Response<Response> response) {
                if (response != null && response.isSuccessful()) {
                    if(response.body()!=null && response.body().getItems()!=null)
                        catView.onGetResult(catModel.mapApiToBooks(response.body()));
                    else
                        catView.onNoResult();
                }
            }

            @Override
            public void onFailure(@Nullable Call<Response> call,
                                  @Nullable Throwable t) {
                if (t != null) {
                    catView.onNoResult();
                }
            }
        });
    }

    @Override
    public void getLibrary() {
        try{
            Executors.newSingleThreadExecutor().execute(() -> {
            List<Book> books = catModel.getAllBooks();
            catView.onGetResult(books);
            });
        } catch (Exception e){
            Log.e("erreur récupération bibliothèque", String.valueOf(e));
        }
    }

    @Override
    public void addBookmark(Book book) {
        try{
            Executors.newSingleThreadExecutor().execute(() -> {
                catModel.addBookToDatabase(book);
                catView.onBookmarkAdded(book.getTitle());
            });
        } catch (Exception e){
            Log.e("erreur insert book", String.valueOf(e));
        }
    }

    @Override
    public void deleteBookmark(Book book) {
        try{
            Executors.newSingleThreadExecutor().execute(() -> catModel.deleteBook(book));
            catView.onBookmarkDeleted(book);
        } catch (Exception e){
            Log.e("erreur delete book", String.valueOf(e));
        }
    }

    @Override
    public void onDestroy() {
        catView = null;
    }

    @Override
    public Bundle createNavigationArguments(Book book) {
        Bundle extras = new Bundle();
        extras.putString(DetailsActivity.THUMBNAIL,book.getThumbnail());
        extras.putString(DetailsActivity.TITLE,book.getTitle());
        extras.putString(DetailsActivity.AUTHORS,book.getAuthors());
        extras.putString(DetailsActivity.PUBLISHER,book.getPublisher());
        extras.putString(DetailsActivity.DATE,book.getPublishedDate());
        extras.putString(DetailsActivity.DESCRIPTION,book.getDescription());
        extras.putString(DetailsActivity.PREVIEW,book.getPreviewLink());
       return extras;
    }


}

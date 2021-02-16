package fr.henry.mylibrary.ui.catalog;

import android.os.Bundle;

import java.util.List;

import fr.henry.mylibrary.data.Book;
import fr.henry.mylibrary.network.response.Response;

interface CatalogContract {

    interface CatalogView {
        void onGetResult(List<Book> bookList);
        void onNoResult();
        void onBookmarkAdded(String title);
        void onBookmarkDeleted(Book book);
    }

    interface CatalogPresenter{
        void searchOnline(String title, String author, String key);
        void getLibrary();
        void addBookmark(Book book);
        void deleteBookmark(Book book);
        void onDestroy();
        Bundle createNavigationArguments(Book book);

    }

    interface CatalogModel{
        List<Book> mapApiToBooks(Response apiResponse);
        void addBookToDatabase(Book book);
        void deleteBook(Book book);
        List<Book> getAllBooks();
    }
}

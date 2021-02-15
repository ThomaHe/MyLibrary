package fr.henry.mylibrary.ui.catalog;

import java.util.List;

import fr.henry.mylibrary.data.Book;
import fr.henry.mylibrary.network.response.Response;

interface CatalogContract {

    interface CatalogView {
        void onGetResult(List<Book> bookList);
        void onNoResult();
    }

    interface CatalogPresenter{
        void searchOnline(String title, String author, String key);
        void onDestroy();
    }

    interface CatalogModel{
        List<Book> mapApiToBooks(Response apiResponse);
    }
}

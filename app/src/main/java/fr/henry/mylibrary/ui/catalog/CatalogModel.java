package fr.henry.mylibrary.ui.catalog;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import fr.henry.mylibrary.data.Book;
import fr.henry.mylibrary.data.database.BookDB;
import fr.henry.mylibrary.data.database.BookDBDao;
import fr.henry.mylibrary.data.database.BookDatabase;
import fr.henry.mylibrary.network.response.Item;
import fr.henry.mylibrary.network.response.Response;
import fr.henry.mylibrary.network.response.VolumeInfo;

public class CatalogModel implements CatalogContract.CatalogModel{

    private BookDatabase mBookDatabase;
    private BookDBDao mBookDBDao;

    public CatalogModel(BookDatabase db) {
        this.mBookDatabase = db;
        this.mBookDBDao = mBookDatabase.bookDBDao();
    }

    @Override
    public List<Book> mapApiToBooks(Response apiResponse) {
        List<Book> mappedList = new ArrayList<>();
        try {
            for (Item item : apiResponse.getItems()) {
                Book newBook = new Book();
                if (item.getID() != null)
                    newBook.setId(item.getID());
                else
                    newBook.setId("");

                if (item.getVolumeInfo().getTitle() != null)
                    newBook.setTitle(concatTitles(item.getVolumeInfo()));
                else
                    newBook.setTitle("");

                if (item.getVolumeInfo().getAuthors() != null)
                    newBook.setAuthors(concatAuthors(item.getVolumeInfo()));
                else
                    newBook.setAuthors("");

                if (item.getVolumeInfo().getDescription() != null)
                    newBook.setDescription(item.getVolumeInfo().getDescription());
                else
                    newBook.setDescription("");

                if (item.getVolumeInfo().getPublisher() != null)
                    newBook.setPublisher(item.getVolumeInfo().getPublisher());
                else
                    newBook.setPublisher("");

                if (item.getVolumeInfo().getPublishedDate() != null)
                    newBook.setPublishedDate(item.getVolumeInfo().getPublishedDate());
                else
                    newBook.setPublishedDate("");

                if (item.getVolumeInfo().getImageLinks() != null)
                    newBook.setThumbnail(convertToHTTPS(item.getVolumeInfo()));
                else
                    newBook.setThumbnail("");

                if (item.getVolumeInfo().getPreviewLink() != null)
                    newBook.setPreviewLink(item.getVolumeInfo().getPreviewLink());
                else
                    newBook.setPreviewLink("");

                mappedList.add(newBook);
            }
        }
        catch (Exception e){
            Log.e("bug au mapping api", String.valueOf(e));
        }
        return mappedList;

    }

    @Override
    public void addBookToDatabase(Book book) {
        BookDB bookDB = convertBookToBookDB(book);
        mBookDBDao.insert(bookDB);
    }

    @Override
    public void deleteBook(Book book) {
        BookDB bookDB = convertBookToBookDB(book);
        mBookDBDao.delete(bookDB);
    }

    @Override
    public List<Book> getAllBooks() {

        List<BookDB> bookDBs = mBookDBDao.getAll();
        List<Book> books = new ArrayList<>();

        for(BookDB bookDB : bookDBs)
        {
            books.add(convertBookDBToBook(bookDB));
        }
        return books;
    }

    private Book convertBookDBToBook(BookDB bookDB)
    {
        Book newBook = new Book();
        try {
            newBook.setId(bookDB.id);
            newBook.setTitle(bookDB.title);
            newBook.setAuthors(bookDB.authors);
            newBook.setPublisher(bookDB.publisher);
            newBook.setPublishedDate(bookDB.publishedDate);
            newBook.setDescription(bookDB.description);
            newBook.setThumbnail(bookDB.thumbnail);
            newBook.setPreviewLink(bookDB.previewLink);
        }
        catch (Exception e){
        Log.e("bug au mapping depuis la db", String.valueOf(e));
        }
        return newBook;
    }

    private BookDB convertBookToBookDB(Book book)
    {
        BookDB newBookDB = new BookDB();
        try {
            newBookDB.id = book.getId();
            newBookDB.title = book.getTitle();
            newBookDB.authors = book.getAuthors();
            newBookDB.publisher = book.getPublisher();
            newBookDB.publishedDate = book.getPublishedDate();
            newBookDB.description = book.getDescription();
            newBookDB.thumbnail = book.getThumbnail();
            newBookDB.previewLink = book.getPreviewLink();
        }
        catch (Exception e){
            Log.e("bug au mapping vers la db", String.valueOf(e));
        }
        return newBookDB;

    }
    private String concatTitles(VolumeInfo info){
        if(info.getSubtitle()!=null && !info.getTitle().equals(info.getSubtitle())) { //on évite de mettre 2 fois le même titre
            return info.getTitle() + ", " + info.getSubtitle();
        }
        else
            return info.getTitle();
    }

    private String concatAuthors(VolumeInfo info){
        return String.join(", ", info.getAuthors());
    }

    //convert to https for the glide library
    private String convertToHTTPS(VolumeInfo info) {
        String url = info.getImageLinks().getThumbnail();
        url = url.replaceFirst("http", "https");
        return url;
    }
}

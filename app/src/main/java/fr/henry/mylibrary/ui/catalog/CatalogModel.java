package fr.henry.mylibrary.ui.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import fr.henry.mylibrary.data.Book;
import fr.henry.mylibrary.network.response.Item;
import fr.henry.mylibrary.network.response.Response;
import fr.henry.mylibrary.network.response.VolumeInfo;

public class CatalogModel implements CatalogContract.CatalogModel{

    @Override
    public List<Book> mapApiToBooks(Response apiResponse) {
        List<Book> mappedList = new ArrayList<Book>();

        for( Item item : apiResponse.getItems())
        {
            Book newBook = new Book();
            newBook.setID(item.getID());
            newBook.setTitle(concatTitles(item.getVolumeInfo()));
            newBook.setAuthors(concatAuthors(item.getVolumeInfo()));
            newBook.setDescription(item.getVolumeInfo().getDescription());
            newBook.setPublisher(item.getVolumeInfo().getPublisher());
            newBook.setPublishedDate(item.getVolumeInfo().getPublishedDate());
            newBook.setThumbnail(item.getVolumeInfo().getImageLinks().getSmallThumbnail());
            newBook.setPreviewLink(item.getVolumeInfo().getPreviewLink());

            mappedList.add(newBook);
        }
        return mappedList;
    }

    private String concatTitles(VolumeInfo info){
        return  info.getTitle()+", "+info.getSubtitle();
    }

    private String concatAuthors(VolumeInfo info){
        return String.join(", ", info.getAuthors());
    }
}

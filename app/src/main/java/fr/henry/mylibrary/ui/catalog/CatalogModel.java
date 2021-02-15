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
            if(item.getID()!=null)
                newBook.setID(item.getID());
            else
                newBook.setID("");

            if(item.getVolumeInfo().getTitle()!=null)
                newBook.setTitle(concatTitles(item.getVolumeInfo()));
            else
                newBook.setTitle("");

            if(item.getVolumeInfo().getAuthors()!=null)
                newBook.setAuthors(concatAuthors(item.getVolumeInfo()));
            else
                newBook.setAuthors("");

            if(item.getVolumeInfo().getDescription()!=null)
                newBook.setDescription(item.getVolumeInfo().getDescription());
            else
                newBook.setDescription("");

            if(item.getVolumeInfo().getPublisher()!=null)
                newBook.setPublisher(item.getVolumeInfo().getPublisher());
            else
                newBook.setPublisher("");

            if(item.getVolumeInfo().getPublishedDate()!=null)
                newBook.setPublishedDate(item.getVolumeInfo().getPublishedDate());
            else
                newBook.setPublishedDate("");

            if(item.getVolumeInfo().getImageLinks().getThumbnail()!=null)
                newBook.setThumbnail(convertToHTTPS(item.getVolumeInfo()));
            else
                newBook.setThumbnail("");

            if(item.getVolumeInfo().getPreviewLink()!=null)
                newBook.setPreviewLink(item.getVolumeInfo().getPreviewLink());
            else
                newBook.setPreviewLink("");

            mappedList.add(newBook);
        }
        return mappedList;
    }

    private String concatTitles(VolumeInfo info){
        if(info.getSubtitle()!=null) {
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

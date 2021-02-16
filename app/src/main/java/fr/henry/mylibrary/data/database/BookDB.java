package fr.henry.mylibrary.data.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BookDB {
    @PrimaryKey
    @ColumnInfo(name = "id")
    public String id;

    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "authors")
    public String authors;
    @ColumnInfo(name = "publisher")
    public String publisher;
    @ColumnInfo(name = "publishedDate")
    public String publishedDate;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "thumbnail")
    public String thumbnail;
    @ColumnInfo(name = "previewLink")
    public String previewLink;

}

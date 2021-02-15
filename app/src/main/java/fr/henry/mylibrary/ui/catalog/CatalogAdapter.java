package fr.henry.mylibrary.ui.catalog;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import fr.henry.mylibrary.R;
import fr.henry.mylibrary.data.Book;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.MyViewHolder> {

    private Context ctx;
    private List<Book> books;
    private OnItemClickListener onItemClickListener;

    public CatalogAdapter(Context ctx, List<Book> bookList) {
        this.ctx = ctx;
        this.books=bookList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/{

        public ImageView book_thumbnail;
        public TextView book_title;
        public TextView book_author;
        public TextView book_description;
        public ImageButton btn_bookmark;

        public MyViewHolder(View itemView) {
            super(itemView);
            book_thumbnail=(ImageView) itemView.findViewById(R.id.book_thumbnail);
            book_title=(TextView) itemView.findViewById(R.id.book_title);
            book_author=(TextView) itemView.findViewById(R.id.book_author);
            book_description=(TextView) itemView.findViewById(R.id.book_description);
            btn_bookmark=(ImageButton) itemView.findViewById(R.id.btn_bookmark);
        }
    }
    @NonNull
    @Override
    public CatalogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogAdapter.MyViewHolder holder, int position) {
        final Book book = books.get(position);

        Glide.with(ctx).load(book.getThumbnail()).into(holder.book_thumbnail);
        holder.book_title.setText(book.getTitle());
        holder.book_author.setText(book.getAuthors());
        holder.book_description.setText(book.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null) {
                    onItemClickListener.onItemClick(books.get(position));
                }
            }
        });

        holder.btn_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null) {
                    onItemClickListener.onBookmarkClick(books.get(position));
                }
            }
        });

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
        void onBookmarkClick(Book book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

}

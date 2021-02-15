package fr.henry.mylibrary.ui.catalog;

import androidx.annotation.Nullable;

import java.util.List;

import fr.henry.mylibrary.data.Book;
import fr.henry.mylibrary.network.ApiService;
import fr.henry.mylibrary.network.response.Response;
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
        Call<Response> call = ApiService
                .retrofit
                .create(ApiService.class)
                .getBooks(title, author, key);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(@Nullable Call<Response> call,
                                   @Nullable retrofit2.Response<Response> response) {
                if (response != null && response.isSuccessful()) {
                    if(response.body()!=null && response.body().getItems().size()>0)
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
    public void onDestroy() {
        catView = null;
    }
}

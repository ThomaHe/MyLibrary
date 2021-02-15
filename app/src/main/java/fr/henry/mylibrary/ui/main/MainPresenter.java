package fr.henry.mylibrary.ui.main;

class MainPresenter implements MainContract.MainPresenter {
    private MainContract.MainView mainView;

    public MainPresenter(MainContract.MainView view) {
        this.mainView = view;
    }

    @Override
    public void onSearchButtonClick(String title, String author) {
        if ((title==null || title.isEmpty())&&(author==null || author.isEmpty()))
        {
            mainView.emptySearch();
        }
        else {
            mainView.navigateToSearch(title, author);
        }

    }

    @Override
    public void onLibraryButtonClick() {
        mainView.navigateToLibrary();
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }
}

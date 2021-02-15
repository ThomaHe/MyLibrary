package fr.henry.mylibrary.ui.main;

interface MainContract {

    interface MainView {
        void navigateToSearch(String title, String author);
        void navigateToLibrary();
    }

    interface MainPresenter{
        void onSearchButtonClick(String title, String author);
        void onLibraryButtonClick();

        void onDestroy();
    }
}

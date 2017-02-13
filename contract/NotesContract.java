package gpijanski.myandroidmvp.contract;


import gpijanski.myandroidmvp.presenter.BasePresenter;
import gpijanski.myandroidmvp.ui.BaseView;

public interface NotesContract {

    interface View extends BaseView<Presenter> {

        void showNotes();
        void showAddNote();
        void initRealm();
    }

    interface Presenter extends BasePresenter {

        void loadNotes();
        void addNewNote(String newNoteTitle, String newNoteText);
    }
}

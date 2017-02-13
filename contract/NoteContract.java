package gpijanski.myandroidmvp.contract;


import gpijanski.myandroidmvp.presenter.BasePresenter;
import gpijanski.myandroidmvp.ui.BaseView;

public interface NoteContract {

    interface View extends BaseView<Presenter> {
        void fetchNoteDetails(String noteTitle, String noteDate, String noteDescription);
        void initRealm();
    }

    interface Presenter extends BasePresenter {
        void updateNote(String title, String text, String newTitle, String newText);
    }
}

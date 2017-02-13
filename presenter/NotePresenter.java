package gpijanski.myandroidmvp.presenter;


import android.support.annotation.NonNull;

import gpijanski.myandroidmvp.contract.NoteContract;
import gpijanski.myandroidmvp.model.Note;
import io.realm.Realm;

import static com.google.common.base.Preconditions.checkNotNull;


public class NotePresenter implements NoteContract.Presenter {

    private static NoteContract.View mNoteView;

    private Realm mRealm;

    public NotePresenter(@NonNull NoteContract.View noteView) {
        mNoteView = checkNotNull(noteView, "noteView is null");
        mNoteView.setPresenter(this);

        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {
        mRealm.close();
    }

    @Override
    public void updateNote(String title, String text, String newTitle, String newText) {
        mRealm.beginTransaction();

        Note note = mRealm.where(Note.class).equalTo("title", title).equalTo("text", text).findFirst();
        note.setTitle(newTitle);
        note.setText(newText);

        mRealm.commitTransaction();
    }
}

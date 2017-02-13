package gpijanski.myandroidmvp.presenter;


import android.support.annotation.NonNull;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import gpijanski.myandroidmvp.adapter.NotesRecyclerViewAdapter;
import gpijanski.myandroidmvp.contract.NotesContract;
import gpijanski.myandroidmvp.model.Note;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.google.common.base.Preconditions.checkNotNull;

public class NotesPresenter implements NotesContract.Presenter {

    private final NotesContract.View mNotesView;

    private Realm mRealm;

    private List<Note> notes;

    public NotesRecyclerViewAdapter adapter;

    public NotesPresenter(@NonNull NotesContract.View notesView, Realm mRealm) {
        mNotesView = checkNotNull(notesView, "notesView is null");
        mNotesView.setPresenter(this);

        this.mRealm = mRealm;

        notes = new ArrayList<>();

        adapter = new NotesRecyclerViewAdapter(notes);

        start();
    }

    @Override
    public void start() {
        loadNotes();
    }

    @Override
    public void finish() {
        mRealm.close();
    }

    @Override
    public void loadNotes() {
        adapter.clear();

        RealmResults<Note> notes = mRealm.where(Note.class).findAll().sort("date", Sort.DESCENDING);
        adapter.addNotes(notes);

    }

    @Override
    public void addNewNote(String newNoteTitle, String newNoteText) {
        mRealm.beginTransaction();
        Note note = mRealm.createObject(Note.class);
        note.setTitle(newNoteTitle);
        note.setText(newNoteText);
        note.setDate(DateTime.now().toString("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS"));
        mRealm.commitTransaction();

        loadNotes();
    }
}

package gpijanski.myandroidmvp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gpijanski.myandroidmvp.R;
import gpijanski.myandroidmvp.commons.NewNoteDialog;
import gpijanski.myandroidmvp.contract.NotesContract;
import gpijanski.myandroidmvp.presenter.NotesPresenter;
import io.realm.Realm;

import static com.google.common.base.Preconditions.checkNotNull;

public class NotesActivity extends AppCompatActivity implements NotesContract.View, NewNoteDialog.NoticeDialogListener {

    @BindView(R.id.notesRv)
    RecyclerView notesRecyclerView;

    @BindView(R.id.addNoteFb)
    FloatingActionButton addNoteFb;

    private NotesContract.Presenter mPresenter;
    private NotesPresenter mNotesPresenter;

    private LinearLayoutManager layoutManager;

    DialogFragment newDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);

        initRealm();
        mNotesPresenter = new NotesPresenter(this, Realm.getDefaultInstance());

        showNotes();
    }

    @Override
    public void setPresenter(@NonNull NotesContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void initRealm() {
        Realm.init(this);
    }

    @OnClick(R.id.addNoteFb)
    @Override
    public void showAddNote() {
        newDialog = NewNoteDialog.newInstance();
        newDialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void showNotes() {
        notesRecyclerView.setAdapter(mNotesPresenter.adapter);
        notesRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onDialogPositiveClick(String newNoteTitle, String newNoteText) {
        mPresenter.addNewNote(newNoteTitle, newNoteText);
        showNotes();
    }

    @Override
    public void onDialogNegativeClick() {

    }

    @Override
    public void onResume() {
        super.onResume();
        showNotes();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.finish();
    }
}

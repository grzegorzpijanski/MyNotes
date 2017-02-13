package gpijanski.myandroidmvp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gpijanski.myandroidmvp.R;
import gpijanski.myandroidmvp.contract.NoteContract;
import gpijanski.myandroidmvp.presenter.NotePresenter;
import io.realm.Realm;

import static com.google.common.base.Preconditions.checkNotNull;

public class NoteDetailsActivity extends AppCompatActivity implements NoteContract.View {

    @BindView(R.id.titleVs)
    ViewSwitcher titleViewSwitcher;

    @BindView(R.id.noteDescriptionVs)
    ViewSwitcher noteDescriptionViewSwitcher;

    @BindView(R.id.noteTitleTv)
    TextView noteTitleTextView;

    @BindView(R.id.noteTitleEt)
    EditText noteTitleEditText;

    @BindView(R.id.noteDescriptionEt)
    EditText noteDescriptionEditText;

    @BindView(R.id.noteDescriptionTv)
    TextView noteDescriptionTextView;

    @BindView(R.id.IvContainer)
    RelativeLayout IvRelativeLayout;

    @BindView(R.id.editIv)
    ImageView editImageView;

    @BindView(R.id.checkIv)
    ImageView checkImageView;

    private NoteContract.Presenter mPresenter;
    private NotePresenter mNotePresenter;

    private String noteTitle;
    private String noteDate;
    private String noteDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_note_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            noteTitle = intent.getStringExtra("title");
            noteDate = intent.getStringExtra("date");
            noteDescription = intent.getStringExtra("description");
        }

        initRealm();
        mNotePresenter = new NotePresenter(this);

        fetchNoteDetails(noteTitle, noteDate, noteDescription);
    }

    @Override
    public void setPresenter(@NonNull NoteContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void initRealm() {
        Realm.init(this);
    }

    @Override
    public void fetchNoteDetails(String noteTitle, String noteDate, String noteDescription) {
        noteTitleTextView.setText(noteTitle);
        noteDescriptionTextView.setText(noteDescription);
    }

    @OnClick(R.id.IvContainer)
    public void IvListener() {
        if (editImageView.getVisibility() == View.VISIBLE) {
            editImageView.setVisibility(View.GONE);
            checkImageView.setVisibility(View.VISIBLE);

            titleViewSwitcher.showNext();
            noteDescriptionViewSwitcher.showNext();

            noteTitleEditText.setText(noteTitle);
            noteDescriptionEditText.setText(noteDescription);
        }
        else {
            checkImageView.setVisibility(View.GONE);
            editImageView.setVisibility(View.VISIBLE);

            titleViewSwitcher.showPrevious();
            noteDescriptionViewSwitcher.showPrevious();

            noteTitleTextView.setText(noteTitleEditText.getText().toString());
            noteDescriptionTextView.setText(noteDescriptionEditText.getText().toString());

            mNotePresenter.updateNote(noteTitle, noteDescription,
                    noteTitleTextView.getText().toString(),
                    noteDescriptionTextView.getText().toString());
        }
    }

}

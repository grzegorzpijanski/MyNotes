package gpijanski.myandroidmvp.holder;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import butterknife.BindView;
import gpijanski.myandroidmvp.R;
import gpijanski.myandroidmvp.commons.RecyclerViewItemViewHolder;
import gpijanski.myandroidmvp.model.Note;
import gpijanski.myandroidmvp.ui.NoteDetailsActivity;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.functions.Action1;

public class NoteViewHolder extends RecyclerViewItemViewHolder<Note> {

    @BindView(R.id.noteCardVg)
    CardView noteCardCardView;

    @BindView(R.id.noteTitleTv)
    TextView noteTitleTextView;

    @BindView(R.id.noteDateTv)
    TextView noteDateTextView;

    private Context context;

    private Realm mRealm;

    private final Action1<Note> onDeleteAction;

    public NoteViewHolder(View itemView, Action1<Note> onDeleteAction) {
        super(itemView);
        this.onDeleteAction = onDeleteAction;
        context = itemView.getContext();
    }

    @Override
    public void bindView(Note instance) {
        noteTitleTextView.setText(instance.getTitle());
        DateTime date = DateTime.parse(instance.getDate(),
                DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS"));
        noteDateTextView.setText(String.format(context.getResources().getString(R.string.note_date),
                String.valueOf(date.getDayOfMonth()), String.valueOf(date.toString("MMM"))));

        noteCardCardView.setOnClickListener(view -> {
            Intent intent = new Intent(context, NoteDetailsActivity.class);
            intent.putExtra("title",instance.getTitle());
            intent.putExtra("date", instance.getDate());
            intent.putExtra("description", instance.getText());
            context.startActivity(intent);
        });

        noteCardCardView.setOnLongClickListener(view -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete note")
                    .setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton(android.R.string.yes, (dialogInterface, which) ->  {
                        onDeleteAction.call(instance);
                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        RealmResults<Note> results = mRealm.where(Note.class).equalTo("title", instance.getTitle()).findAll();
                        results.deleteAllFromRealm();
                        mRealm.commitTransaction();


                    })
                    .setNegativeButton(android.R.string.no, (dialogInterface, which) -> {})
                    .setIcon(R.drawable.ic_warning)
                    .show();
            return true;
        });
    }
}

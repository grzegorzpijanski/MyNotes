package gpijanski.myandroidmvp.commons;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import gpijanski.myandroidmvp.R;

public class NewNoteDialog extends DialogFragment {

    @BindView(R.id.newNoteTitleEt)
    EditText newNoteTitleEditText;

    @BindView(R.id.newNoteTextEt)
    EditText newNoteTextEditText;

    public static NewNoteDialog newInstance() {
        return new NewNoteDialog();
    }

    public interface NoticeDialogListener {

        void onDialogPositiveClick(String newNoteTitle, String newNoteText);
        void onDialogNegativeClick();
    }

    NoticeDialogListener mListener;

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(Bundle onSavedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.add_note_dialog, null);
        builder.setView(view);
        ButterKnife.bind(this, view);
        try {
            mListener = (NoticeDialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }

        builder.setPositiveButton(R.string.note_add, (dialogInterface, which) -> {
            if (newNoteTitleEditText.getText().toString().length() > 0)
                mListener.onDialogPositiveClick(newNoteTitleEditText.getText().toString(),
                        newNoteTextEditText.getText().toString());
            else
                Toast.makeText(getContext(), getResources().getString(R.string.type_title),
                        Toast.LENGTH_SHORT).show();

        }).setNegativeButton(R.string.cancel, (dialogInterface, which) ->
                    mListener.onDialogNegativeClick()
                );
        return builder.create();
    }
}

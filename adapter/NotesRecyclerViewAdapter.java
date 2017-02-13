package gpijanski.myandroidmvp.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import gpijanski.myandroidmvp.R;
import gpijanski.myandroidmvp.commons.RecyclerViewItemViewHolder;
import gpijanski.myandroidmvp.holder.NoteViewHolder;
import gpijanski.myandroidmvp.model.Note;

public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewItemViewHolder> {

    private List<Note> notes;

    public NotesRecyclerViewAdapter(List<Note> notes) {
        this. notes = notes;
    }

    @Override
    public RecyclerViewItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.note_item, parent, false), (instance) -> {
            notes.remove(instance);
            notifyDataSetChanged();
        });
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.bindView(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void addNote(Note note) {
        this.notes.add(note);
    }

    public void addNotes(List<Note> notes) {
        this.notes.addAll(notes);
    }

    public void removeNote(int position) {
        this.notes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, notes.size());
    }

    public void clear() {
        this.notes.clear();
    }


}

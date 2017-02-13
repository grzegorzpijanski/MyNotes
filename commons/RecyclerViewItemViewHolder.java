package gpijanski.myandroidmvp.commons;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

public class RecyclerViewItemViewHolder<T> extends RecyclerView.ViewHolder {
    private final WeakReference<Context> contextWeakReference;
    private T currentInstance;

    public RecyclerViewItemViewHolder(View itemView) {
        super(itemView);

        contextWeakReference = new WeakReference<>(itemView.getContext());

        ButterKnife.bind(this, itemView);
    }

    public void bindView(T instance) {
        currentInstance = instance;
    }
}
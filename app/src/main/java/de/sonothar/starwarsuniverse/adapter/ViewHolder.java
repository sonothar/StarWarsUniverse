package de.sonothar.starwarsuniverse.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by dennis on 30.03.15.
 */
public final class ViewHolder extends RecyclerView.ViewHolder {
    private final TextView textView;

    public static ViewHolder newInstance(View itemView) {
        TextView textView = (TextView) itemView.findViewById(android.R.id.text1);
        return new ViewHolder(itemView, textView);
    }

    private ViewHolder(View itemView, TextView textView) {
        super(itemView);
        this.textView = textView;
    }

    public void setText(CharSequence text) {
        textView.setText(text);
    }
}
package de.sonothar.starwarsuniverse.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.sonothar.starwarsuniverse.models.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennis on 30.03.15.
 */
public class FilmAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Film> films;

    public FilmAdapter(Context context, List<Film> films) {

        if(films != null)
            this.films = films;
        else
            this.films = new ArrayList<Film>();
    }

    public void addFilms(List<Film> moreFilms){
        films.addAll(moreFilms);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);

        return ViewHolder.newInstance(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.setText(films.get(position).title);
    }

    @Override
    public int getItemCount() {
        return films.size();
    }
}

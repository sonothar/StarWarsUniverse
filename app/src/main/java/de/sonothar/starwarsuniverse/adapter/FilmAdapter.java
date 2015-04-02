package de.sonothar.starwarsuniverse.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.sonothar.starwarsuniverse.models.Film;

/**
 * Created by dennis on 30.03.15.
 */
public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {

    private List<Film> films;

    public FilmAdapter(List<Film> films) {
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
                .inflate(android.R.layout.simple_list_item_2, parent, false);

        return ViewHolder.newInstance(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.setTitle(films.get(position));
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView episodeView;
        private final TextView titleView;

        public static ViewHolder newInstance(View itemView) {
            return new ViewHolder(itemView);
        }

        private ViewHolder(View itemView) {
            super(itemView);
            episodeView = (TextView) itemView.findViewById(android.R.id.text1);
            titleView  = (TextView) itemView.findViewById(android.R.id.text2);
        }

        public void setTitle(Film episode) {
            episodeView.setText("Star Wars - Episode " + getEpisodeStr(episode.episodeId));
            titleView.setText(episode.title);
        }

        private String getEpisodeStr(int episodeId) {

            switch (episodeId){
                case 1: return "I";
                case 2: return "II";
                case 3: return "III";
                case 4: return "IV";
                case 5: return "V";
                case 6: return "VI";
                default:
                    return "";
            }
        }

    }
}

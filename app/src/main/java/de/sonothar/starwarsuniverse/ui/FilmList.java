package de.sonothar.starwarsuniverse.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.sonothar.starwarsuniverse.R;
import de.sonothar.starwarsuniverse.models.Film;
import de.sonothar.starwarsuniverse.models.SWModelList;
import de.sonothar.starwarsuniverse.sw.StarWarsApi;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilmList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmList extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private FilmAdapter adapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FilmDetail.
     */
    public static FilmList newInstance() {
        FilmList fragment = new FilmList();
        return fragment;
    }

    public FilmList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_list, container, false);

        ((ActionBarActivity) getActivity()).getSupportActionBar()
        ViewCompat.setTransitionName(title, "Films");



        setupRecyclerView(v);
        return v;
    }

    private void setupRecyclerView(View v) {
        adapter = new FilmAdapter(null, new OnItemClickListener() {
            @Override
            public void onItemClicked(Film film) {
                handleItemClick(film);
            }
        });

        recyclerView = (RecyclerView) v.findViewById(R.id.list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void handleItemClick(Film film) {
        Toast.makeText(getActivity(), film.openingCrawl, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onStart() {
        super.onStart();

        loadFilms();
    }

    private void loadFilms() {
        StarWarsApi.getApi().getAllFilms(new Callback<SWModelList<de.sonothar.starwarsuniverse.models.Film>>() {
            @Override
            public void success(SWModelList<de.sonothar.starwarsuniverse.models.Film> filmSWModelList, Response response) {
                Collections.sort(filmSWModelList.results, new Comparator<de.sonothar.starwarsuniverse.models.Film>() {
                    @Override
                    public int compare(de.sonothar.starwarsuniverse.models.Film lhs, de.sonothar.starwarsuniverse.models.Film rhs) {
                        if (lhs.episodeId == rhs.episodeId)
                            return 0;
                        else
                            return lhs.episodeId > rhs.episodeId ? 1 : -1;
                    }
                });

                adapter.addFilms(filmSWModelList.results);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });

    }

    private interface OnItemClickListener{
        void onItemClicked(Film film);
    }

    private static class FilmAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<de.sonothar.starwarsuniverse.models.Film> films;
        private OnItemClickListener listener;

        public FilmAdapter(List<Film> films, OnItemClickListener listener) {
            if (films != null)
                this.films = films;
            else
                this.films = new ArrayList<>();

            this.listener = listener;
        }

        public void addFilms(List<de.sonothar.starwarsuniverse.models.Film> moreFilms) {
            films.addAll(moreFilms);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_list_item, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            final Film film = films.get(position);

            viewHolder.setTitle(film);
            viewHolder.setOnItemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(film);
                }
            });
        }

        @Override
        public int getItemCount() {
            return films.size();
        }

    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private View parent;
        private final TextView episodeView;
        private final TextView titleView;

        private ViewHolder(View itemView) {
            super(itemView);
            this.parent = itemView;
            this.episodeView = (TextView) itemView.findViewById(R.id.item_title);
            this.titleView = (TextView) itemView.findViewById(R.id.item_info);

        }

        public void setTitle(de.sonothar.starwarsuniverse.models.Film episode) {
            episodeView.setText("Star Wars - Episode " + getEpisodeStr(episode.episodeId));
            titleView.setText(episode.title);
        }

        public void setOnItemClickListener(View.OnClickListener listener){
            parent.setOnClickListener(listener);
        }

        private String getEpisodeStr(int episodeId) {

            switch (episodeId) {
                case 1:
                    return "I";
                case 2:
                    return "II";
                case 3:
                    return "III";
                case 4:
                    return "IV";
                case 5:
                    return "V";
                case 6:
                    return "VI";
                default:
                    return "";
            }
        }

    }

}

package de.sonothar.starwarsuniverse.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Collections;
import java.util.Comparator;

import de.sonothar.starwarsuniverse.R;
import de.sonothar.starwarsuniverse.adapter.FilmAdapter;
import de.sonothar.starwarsuniverse.models.Film;
import de.sonothar.starwarsuniverse.models.SWModelList;
import de.sonothar.starwarsuniverse.sw.StarWarsApi;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FilmList extends Activity {

    private RecyclerView recyclerView;
    private FilmAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        adapter = new FilmAdapter(null);

        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadFilms(0);
    }

    private void loadFilms(int page) {
        StarWarsApi.getApi().getAllFilms(new Callback<SWModelList<Film>>() {
            @Override
            public void success(SWModelList<Film> filmSWModelList, Response response) {
                Collections.sort(filmSWModelList.results, new Comparator<Film>() {
                    @Override
                    public int compare(Film lhs, Film rhs) {
                        if (lhs.episodeId == rhs.episodeId)
                            return 0;
                        else
                            return lhs.episodeId > rhs.episodeId ? 1 : -1;
                    }
                });

                adapter.addFilms(filmSWModelList.results);
                adapter.notifyDataSetChanged();
                if (filmSWModelList.hasMore())
                    loadFilms(filmSWModelList.getNextPage());
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_generic_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

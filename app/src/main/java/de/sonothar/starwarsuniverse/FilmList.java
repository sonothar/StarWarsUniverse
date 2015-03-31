package de.sonothar.starwarsuniverse;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import de.sonothar.starwarsuniverse.models.Film;
import de.sonothar.starwarsuniverse.models.SWModelList;
import de.sonothar.starwarsuniverse.sw.StarWars;

import de.sonothar.starwarsuniverse.adapter.FilmAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FilmList extends Activity {

    private StarWars starWars;
    private RecyclerView recyclerView;
    private FilmAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        adapter = new FilmAdapter(this, null);

        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void loadFilms(int page){
        starWars.getAllFilms(0, new Callback<SWModelList<Film>>() {
            @Override
            public void success(SWModelList<Film> filmSWModelList, Response response) {
                adapter.addFilms(filmSWModelList.results);
                if(filmSWModelList.hasMore())
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

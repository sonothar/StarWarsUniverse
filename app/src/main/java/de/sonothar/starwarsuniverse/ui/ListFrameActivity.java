package de.sonothar.starwarsuniverse.ui;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class ListFrameActivity extends ActionBarActivity implements OnFragmentInteractionListener {

    public static final String ACTION_FILM_LIST = "de.sonothar.action.FILM_LIST";
    public static final String ACTION_PEOPLE_LIST = "de.sonothar.action.PEOPLE_LIST";
    public static final String ACTION_PLANET_LIST = "de.sonothar.action.PLANET_LIST";
    public static final String ACTION_STARSHIP_LIST = "de.sonothar.action.STARSHIP_LIST";
    public static final String ACTION_VEHICLE_LIST = "de.sonothar.action.VEHICLE_LIST";
    public static final String ACTION_SPECIES_LIST = "de.sonothar.action.SPECIES_LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().getAction() == null || getIntent().getAction().isEmpty()){
            throw new UnsupportedOperationException("No Action found");
        }

        switch (getIntent().getAction()){
            case ACTION_FILM_LIST:
                attachFragment(FilmList.newInstance(), "Films");
                break;
            case ACTION_PEOPLE_LIST:
                attachFragment(PeopleList.newInstance(), "People");
                break;
            case ACTION_PLANET_LIST:
                attachFragment(PlanetList.newInstance(), "Planet");
                break;
            case ACTION_SPECIES_LIST:
                attachFragment(SpeciesList.newInstance(), "Species");
                break;
            case ACTION_STARSHIP_LIST:
                attachFragment(StarshipList.newInstance(), "Starships");
                break;
            case ACTION_VEHICLE_LIST:
                attachFragment(VehcleList.newInstance(), "Vehicles");
                break;
            default:
                throw new UnsupportedOperationException("Action unknown: " + getIntent().getAction());

        }
    }

    private void attachFragment(Fragment frag, String title){
        getFragmentManager().beginTransaction()
                .add(android.R.id.content, frag, frag.getClass().getSimpleName())
                .commit();

        setTitle(title);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

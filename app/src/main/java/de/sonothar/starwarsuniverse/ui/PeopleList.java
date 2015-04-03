package de.sonothar.starwarsuniverse.ui;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.sonothar.starwarsuniverse.R;
import de.sonothar.starwarsuniverse.models.*;
import de.sonothar.starwarsuniverse.models.People;
import de.sonothar.starwarsuniverse.sw.StarWarsApi;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PeopleList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PeopleList extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private PeopleAdapter adapter;

    private int nextPage = 1;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PeopleList.
     */
    // TODO: Rename and change types and number of parameters
    public static PeopleList newInstance() {
        PeopleList fragment = new PeopleList();
        return fragment;
    }

    public PeopleList() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.activity_list, container, false);

        adapter = new PeopleAdapter(null);

        recyclerView = (RecyclerView) v.findViewById(R.id.list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadPeople();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void loadPeople() {
        StarWarsApi.getApi().getAllPeople(nextPage, new Callback<SWModelList<People>>() {
            @Override
            public void success(SWModelList<People> peopleSWModelList, Response response) {
                adapter.addPeople(peopleSWModelList.results);
                adapter.notifyDataSetChanged();

                        setNextPage(peopleSWModelList);

            }

            @Override
            public void failure(RetrofitError error) {
            }
        });

    }

    private void setNextPage(SWModelList<People> peopleSWModelList) {
        if(peopleSWModelList.hasMore())
            nextPage = peopleSWModelList.getNextPage();
        else
            nextPage = -1;
    }

    private class PeopleAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<People> people;

        public PeopleAdapter(List<People> people) {
            if (people != null)
                this.people = people;
            else
                this.people = new ArrayList<>();
        }

        public void addPeople(List<People> morePeople) {
            people.addAll(morePeople);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);

            return ViewHolder.newInstance(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.setTitle(people.get(position));

            if(position + 1 == people.size())
                loadPeople();
        }

        @Override
        public int getItemCount() {
            return people.size();
        }

    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        private final TextView genderView;

        public static ViewHolder newInstance(View itemView) {
            return new ViewHolder(itemView);
        }

        private ViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(android.R.id.text1);
            genderView = (TextView) itemView.findViewById(android.R.id.text2);
        }

        public void setTitle(People person) {
            nameView.setText(person.name);
            genderView.setText(person.gender);
        }
    }
}

package de.sonothar.starwarsuniverse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import de.sonothar.starwarsuniverse.R;


public class MainActivity extends ActionBarActivity {

    private static String[] API_LIST = {"Films", "People", "Planets", "Starships", "Vehicles", "Species"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        MenuAdapter adapter = new MenuAdapter(API_LIST, new OnListItemClickListener() {
            @Override
            public void onListItemClick(int position) {
                handleListItemClick(position);
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(adapter);
    }

    private void handleListItemClick(int position) {

        Intent intent = new Intent(this, ListFrameActivity.class);

        switch (position){
            case 0:
                // Films
                intent.setAction(ListFrameActivity.ACTION_FILM_LIST);
                startActivity(intent);
                break;
            case 1:
                // People
                intent.setAction(ListFrameActivity.ACTION_PEOPLE_LIST);
                startActivity(intent);
                break;
            case 2:
                // Planets
                intent.setAction(ListFrameActivity.ACTION_PLANET_LIST);
                startActivity(intent);
                break;
            case 3:
                // Starships
                intent.setAction(ListFrameActivity.ACTION_STARSHIP_LIST);
                startActivity(intent);
                break;
            case 4:
                // Vehicles
                intent.setAction(ListFrameActivity.ACTION_VEHICLE_LIST);
                startActivity(intent);
                break;
            case 5:
                // Species
                intent.setAction(ListFrameActivity.ACTION_SPECIES_LIST);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "Unknown inout", Toast.LENGTH_LONG).show();
        }

    }

    private interface OnListItemClickListener{
        void onListItemClick(int position);
    }

    private static class MenuAdapter extends RecyclerView.Adapter<ViewHolder>{

        private String[] list;
        private OnListItemClickListener listener;

        private MenuAdapter(String[] list, OnListItemClickListener listner){
            this.list = list;
            this.listener = listner;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.main_list_item,viewGroup, false);
            return ViewHolder.newInstance(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder s, final int position) {
            final String text = list[position];
            s.setTitle(text);
            s.setOnClicklistener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onListItemClick(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.length;
        }
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder{

        private View parent;
        private TextView title;

        public static ViewHolder newInstance(View parent) {
            TextView title = (TextView) parent.findViewById(R.id.item_title);
            return new ViewHolder(parent, title);
        }

        public ViewHolder(View itemView, TextView title) {
            super(itemView);

            this.parent = itemView;
            this.title = title;
        }

        public void setTitle(String titleStr){
            title.setText(titleStr);
        }

        public void setOnClicklistener(View.OnClickListener listener){
            parent.setOnClickListener(listener);
        }

    }
}

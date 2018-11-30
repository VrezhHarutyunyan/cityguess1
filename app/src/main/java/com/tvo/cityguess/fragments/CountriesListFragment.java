package com.tvo.cityguess.fragments;

import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tvo.cityguess.R;
import com.tvo.cityguess.database.Database;
import com.tvo.cityguess.database.Database.ListItem;

import java.util.ArrayList;


public class CountriesListFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View countriesListFragment = inflater.inflate( R.layout.fragment_country_list, container, false );


        Database database = Database.sharedDatabase(getActivity());
        ListView countriesListView = (ListView)countriesListFragment.findViewById(R.id.country_list_view);
        ArrayList<ListItem> countriesWithCapitals = database.getCountriesWithCapitalsArray();
        CountriesArrayAdapter adapter = new CountriesArrayAdapter( getActivity(), R.layout.
                list_item_layout, countriesWithCapitals);
        countriesListView.setAdapter(adapter);

        return countriesListFragment;
    }

    public class CountriesArrayAdapter extends ArrayAdapter<ListItem>{


            private int resource;
            private LayoutInflater inflater;
            private ArrayList<ListItem> countriesWithCapitals;

            public CountriesArrayAdapter(Context context, int resource, ArrayList<ListItem>
                    countriesWithCapitals) {
                super(context, resource, countriesWithCapitals );

                this.countriesWithCapitals = countriesWithCapitals;
                this.inflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
                this.resource = resource;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = inflater.inflate(resource, parent, false);
                }

                ListItem listItem = getItem(position);
                TextView country = (TextView)convertView.findViewById(R.id.
                        country_text_view);
                TextView capital = (TextView)convertView.findViewById(R.id.
                        capital_text_view);
                country.setText(listItem.getCountry());
                capital.setText(listItem.getCapital());
                return convertView;
            }
    }

}

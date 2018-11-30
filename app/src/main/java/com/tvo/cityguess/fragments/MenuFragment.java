package com.tvo.cityguess.fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tvo.cityguess.R;

public class MenuFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    OnGameButtonClickedListener gameButtonClickedListener;

    OnCountryListButtonClickedListener countryListButtonClickedListener;

    OnAchievementsButtonClickedListener achievementsButtonClickedListener;

    OnAboutUsButtonClickedListener aboutUsButtonClickedListener;

    // Container Activity must implement this interfaces
    public interface OnCountryListButtonClickedListener {
         void onCountryListButtonClicked();
    }

    public void setCountryListButtonClickedListener(OnCountryListButtonClickedListener countryListButtonClickedListener ){
        this.countryListButtonClickedListener = countryListButtonClickedListener;
    }

    public interface OnGameButtonClickedListener {
        void onGameButtonClicked();
    }

    public void setGameButtonClickedListener( OnGameButtonClickedListener gameButtonClickedListener ){
        this.gameButtonClickedListener = gameButtonClickedListener;
    }

    public interface OnAchievementsButtonClickedListener {
        void onAchievementsButtonClicked();
    }

    public void setAchievementsButtonClickedListener( OnAchievementsButtonClickedListener achievementsButtonClickedListener ){
        this.achievementsButtonClickedListener = achievementsButtonClickedListener;
    }

    public interface OnAboutUsButtonClickedListener {
        void onAboutUsButtonClicked();
    }

    public void setAboutUsButtonClickedListener( OnAboutUsButtonClickedListener aboutUsButtonClickedListener ){
        this.aboutUsButtonClickedListener = aboutUsButtonClickedListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate( R.layout.fragment_main, container, false );

}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Typeface fontawesome = Typeface.createFromAsset( getActivity(). getAssets(), "fontawesome.ttf");
        Typeface ubuntuMedium = Typeface.createFromAsset(getActivity().getAssets(), "Ubuntu-M.ttf");
        Typeface ubuntuRegular = Typeface.createFromAsset(getActivity().getAssets(), "Ubuntu-R.ttf");
        Typeface ubuntuLiteItalic = Typeface.createFromAsset(getActivity().getAssets(), "Ubuntu-LI.ttf");


        // Setup menu buttons
        Button game = (Button) getView().findViewById(R.id.game);
        Button achievements = (Button) getView().findViewById(R.id.achievements);
        Button countryList = (Button) getView().findViewById(R.id.country_list);
        Button aboutUs = (Button) getView().findViewById(R.id.about_us);
//        Button adFree = (Button) getView().findViewById(R.id.ad_free);
        achievements.setTypeface(ubuntuMedium);
        countryList.setTypeface(ubuntuMedium);
        aboutUs.setTypeface(ubuntuMedium);
//        adFree.setTypeface(ubuntuMedium);

        // Setup listeners
        game.setOnClickListener(this);
        achievements.setOnClickListener(this);
        countryList.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
//        adFree.setOnClickListener(this);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            gameButtonClickedListener = (OnGameButtonClickedListener) activity;
            achievementsButtonClickedListener = (OnAchievementsButtonClickedListener) activity;
            countryListButtonClickedListener = (OnCountryListButtonClickedListener) activity;
            aboutUsButtonClickedListener = (OnAboutUsButtonClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement correspond listener");
        }
    }

    /**
     * View.OnClickListener Methods
     */

    public void onClick(View view) {
        if(view.getId() == R.id.game){
            gameButtonClickedListener.onGameButtonClicked();
        } else if(view.getId() == R.id.achievements){
            achievementsButtonClickedListener.onAchievementsButtonClicked();
        } else if ( view.getId() == R.id.country_list ) {
            countryListButtonClickedListener.onCountryListButtonClicked();
        }  else if(view.getId() == R.id.about_us){
            aboutUsButtonClickedListener.onAboutUsButtonClicked();
        }
    }

}




































package com.tvo.cityguess.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;

import com.crashlytics.android.Crashlytics;
import com.tvo.cityguess.R;
import com.tvo.cityguess.fragments.AboutUsFragment;
import com.tvo.cityguess.fragments.AchievementsFragment;
import com.tvo.cityguess.fragments.CountriesListFragment;
import com.tvo.cityguess.fragments.GameStartFragment;
import com.tvo.cityguess.fragments.MenuFragment;

;import io.fabric.sdk.android.Fabric;

/**
 * Created by victor on 5/7/15.
 */
public class MenuAndGameActivity  extends FragmentActivity implements MenuFragment.OnGameButtonClickedListener,
        MenuFragment.OnAchievementsButtonClickedListener,MenuFragment.OnCountryListButtonClickedListener,MenuFragment.OnAboutUsButtonClickedListener{

    public boolean inStartGame = false;

    private GameStartFragment gameFragment = new GameStartFragment();

    private CountriesListFragment countriesListFragment = new CountriesListFragment();

    private AchievementsFragment achievementsFragment = new AchievementsFragment();

    private AboutUsFragment aboutUsFragment = AboutUsFragment.newInstance( null, null );

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_menu_and_game);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            setUpMenuFragment();

        }

    }

    @Override
    protected void onResume() {

        super.onResume();

//        setUpMenuFragment();

    }

    public void setUpMenuFragment(){

            MenuFragment firstFragment = new MenuFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment, "MENU FRAGMENT").commit();

    }

    private String getLastFragmentName(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();

        return fragmentTag;
    }

    @Override
    public void onBackPressed() {

        MenuFragment menuFragment = new MenuFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();

        fragmentManager.popBackStack();
//        String lastFragmentTag = getLastFragmentName();
//
//        int enterAnimation = 0;
//        int exitAnimation = 0;
//
//        if( lastFragmentTag.equals("GAME FRAGMENT") ){
//            enterAnimation = ;
//            exitAnimation = ;
//        } else if( lastFragmentTag.equals("COUNTRY LIST FRAGMENT") ){
//            enterAnimation = ;
//            exitAnimation = ;
//        } else if( lastFragmentTag.equals("ACHIEVEMENTS FRAGMENT") ){
//            enterAnimation = ;
//            exitAnimation = ;
//        }
//
//
//        // Replace whatever is in the fragment_container view with this fragment,
//        // and add the transaction to the back stack so the user can navigate back
//        transaction.setCustomAnimations( enterAnimation, exitAnimation );

//        transaction.replace(R.id.fragment_container, menuFragment, "MENU FRAGMENT");
        transaction.addToBackStack(null);
        inStartGame = false;

        // Commit the transaction
        transaction.commit();

        super.onBackPressed();

        /*this.finish();
        overridePendingTransition(R.anim.from_down_slide_in, R.anim.to_up_slide_out);*/

    }

    @Override
    public void onGameButtonClicked() {

        Bundle args = new Bundle();

        if( gameFragment.isDetached() ) {
            gameFragment.setArguments(args);
        }

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back

        transaction.setCustomAnimations( R.anim.from_up_slide_in, R.anim.to_down_slide_out , R.anim.from_down_slide_in, R.anim.to_up_slide_out );
        transaction.replace(R.id.fragment_container, gameFragment, "GAME FRAGMENT");
        transaction.addToBackStack("GAME FRAGMENT");
        inStartGame = true;
        // Commit the transaction
        transaction.commit();

    }

    @Override
    public void onAchievementsButtonClicked() {

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back

        transaction.setCustomAnimations( R.anim.from_right_slide_in, R.anim.to_left_slide_out, R.anim.from_left_slide_in, R.anim.to_right_slide_out);
        transaction.replace(R.id.fragment_container, achievementsFragment, "ACHIEVEMENTS FRAGMENT");
        transaction.addToBackStack("ACHIEVEMENTS FRAGMENT");

        // Commit the transaction
        transaction.commit();

    }

    @Override
    public void onCountryListButtonClicked() {

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back

        transaction.setCustomAnimations( R.anim.from_down_slide_in, R.anim.to_up_slide_out, R.anim.from_up_slide_in, R.anim.to_down_slide_out );
        transaction.replace(R.id.fragment_container, countriesListFragment, "COUNTRY LIST FRAGMENT");
        transaction.addToBackStack("COUNTRY LIST FRAGMENT");

        // Commit the transaction
        transaction.commit();

    }

    @Override
    public void onAboutUsButtonClicked() {

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back

        transaction.setCustomAnimations( R.anim.from_left_slide_in, R.anim.to_right_slide_out, R.anim.from_right_slide_in, R.anim.to_left_slide_out);
        transaction.replace(R.id.fragment_container, aboutUsFragment, "ABOUT_US FRAGMENT");
        transaction.addToBackStack("ABOUT_US FRAGMENT");

        // Commit the transaction
        transaction.commit();

    }

}


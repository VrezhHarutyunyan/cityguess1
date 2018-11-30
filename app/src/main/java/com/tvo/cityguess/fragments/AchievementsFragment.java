package com.tvo.cityguess.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tvo.cityguess.R;
import com.tvo.cityguess.helpers.GameActivityStrings;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnAchievementsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AchievementsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class AchievementsFragment extends android.support.v4.app.Fragment {

//    private OnAchievementsFragmentInteractionListener achievementsFragmentInteractionListener;

    public AchievementsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AchievementsFragment.
     */
    public static AchievementsFragment newInstance() {
        AchievementsFragment fragment = new AchievementsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View achievementsFragmentView = inflater.inflate(R.layout.fragment_achievements, container, false);
        
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.preferences_file_name), Context.MODE_PRIVATE);
        int rightAnswersInOneGameAchievement = sharedPreferences.getInt(GameActivityStrings.ACHIEVEMENT_RIGHT_ANSWERS_IN_GAME, 0);
        int rightAnswersInRowAchievement = sharedPreferences.getInt(GameActivityStrings.ACHIEVEMENT_RIGHT_ANSWERS_IN_ROW,0);
        int totalRightAnswersAchievement = sharedPreferences.getInt(GameActivityStrings.ACHIEVEMENT_TOTAL_RIGHT_ANSWERS,0);
//        String timeInSurvivalAchievement = sharedPreferences.getString(GameActivityStrings.ACHIEVEMENT_TIME_IN_SURVIVAL,"0");
//        float totalTimeInGameAchievement = sharedPreferences.getFloat(GameActivityStrings.ACHIEVEMENT_TOTAL_TIME_IN_GAME,0f);

        TextView rightAnswersInOneGameTextView = ( TextView )achievementsFragmentView.findViewById(R.id.right_answers_one_game);
        TextView rightAnswersInRowTextView = ( TextView )achievementsFragmentView.findViewById(R.id.right_answers_in_row);
        TextView totalRightAnswersTextView = ( TextView )achievementsFragmentView.findViewById(R.id.total_right_answers);
//        TextView timeInSurvivalTextView = ( TextView )achievementsFragmentView.findViewById(R.id.time_in_survival);
//        TextView totalTimeInGameTextView = ( TextView )achievementsFragmentView.findViewById(R.id.total_time_in_game);

        rightAnswersInOneGameTextView.setText( String.valueOf(rightAnswersInOneGameAchievement) );
        rightAnswersInRowTextView.setText( String.valueOf(rightAnswersInRowAchievement) );
        totalRightAnswersTextView.setText( String.valueOf(totalRightAnswersAchievement) );
//        timeInSurvivalTextView.setText( timeInSurvivalAchievement );
//        totalTimeInGameTextView.setText( String.valueOf(totalTimeInGameAchievement) );

        return achievementsFragmentView;
        
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        achievementsFragmentInteractionListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

//    public interface OnAchievementsFragmentInteractionListener {
//
//        // TODO: Update argument type and name
//        void onAchievementsFragmentInteraction(Uri uri);
//    }
//
//    public void setOnAchievementsFragmentListener( OnAchievementsFragmentInteractionListener achievementsFragmentInteractionListener ){
//            this.achievementsFragmentInteractionListener = achievementsFragmentInteractionListener;
//    }

}



















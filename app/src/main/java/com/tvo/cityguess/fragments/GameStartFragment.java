package com.tvo.cityguess.fragments;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tvo.cityguess.R;
import com.tvo.cityguess.activities.GameActivity;
import com.tvo.cityguess.database.Database;
import com.tvo.cityguess.helpers.GameActivityStrings;


public class GameStartFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    int quizDetailsHeight;
    int survivalDetailsHeight;

    public float initialTime;
    public float prizeTime;
    public float punishTime;
    public int quizLevelsNumber;


    private LinearLayout survivalDetails;
    ViewTreeObserver survivalDetailsObserver;

    LinearLayout quizDetails;
    ViewTreeObserver quizDetailsObserver;

    private TextView quizDifficultyTextView;
    private TextView survivalDifficultyTextView;
    private String chosenGameDifficulty;

    String[] difficulty = new String[3];
    int survivalDifficultyPosition = 0;
    int quizDifficultyPosition = 0;

    private static final float SURVIVAL_TIME = 60f;
    private static final float QUIZ_QUESTION_TIME = 5.0f;

    public GameStartFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        chosenGameDifficulty = "easy";

        return inflater.inflate( R.layout.fragment_game_start, container, false );
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Database.sharedDatabase(getActivity());

        difficulty[0] = getResources().getString(R.string.easy);
        difficulty[1] = getResources().getString(R.string.medium);
        difficulty[2] = getResources().getString(R.string.hard);

        chosenGameDifficulty = "easy";

        Typeface fontawesome = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome.ttf");

        // Setup survival menu
        Button survival = (Button) getView().findViewById(R.id.survival);
        Button startSurvival = (Button) getView().findViewById(R.id.start_survival);
        TextView survivalDescription = (TextView) getView().findViewById(R.id.survival_description);
        survivalDifficultyTextView = (TextView) getView().findViewById(R.id.survival_difficulty);

        TextView survivalRightArrow = (TextView) getView().findViewById(R.id.survival_right_arrow);
        TextView survivalLeftArrow = (TextView) getView().findViewById(R.id.survival_left_arrow);
        survivalRightArrow.setTypeface(fontawesome);
        survivalLeftArrow.setTypeface(fontawesome);

        // Setup quiz menu
        Button quiz = (Button) getView().findViewById(R.id.quiz);
        Button startQuiz = (Button) getView().findViewById(R.id.start_quiz);
        TextView quizDescription = (TextView) getView().findViewById(R.id.quiz_description);
        quizDifficultyTextView = (TextView) getView().findViewById(R.id.quiz_difficulty);

        TextView quizRightArrow = (TextView) getView().findViewById(R.id.quiz_right_arrow);
        TextView quizLeftArrow = (TextView) getView().findViewById(R.id.quiz_left_arrow);
        quizRightArrow.setTypeface(fontawesome);
        quizLeftArrow.setTypeface(fontawesome);

        //Setup listeners
        quiz.setOnClickListener(this);
        survival.setOnClickListener(this);
        startQuiz.setOnClickListener(this);
        startSurvival.setOnClickListener(this);
        quizLeftArrow.setOnClickListener(quizArrowsOnClickListener);
        quizRightArrow.setOnClickListener(quizArrowsOnClickListener);
        survivalLeftArrow.setOnClickListener(survivalArrowsOnClickListener);
        survivalRightArrow.setOnClickListener(survivalArrowsOnClickListener);

        quizDetails = (LinearLayout) getView().findViewById(R.id.quiz_details);
        survivalDetails = (LinearLayout) getView().findViewById(R.id.survival_details);

        quizDetails.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        quizDetailsHeight = quizDetails.getMeasuredHeightAndState();

        survivalDetails.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        survivalDetailsHeight = survivalDetails.getMeasuredHeight();

    }

    private void startGame(String gameType) {

        Intent intent = new Intent(getActivity(), GameActivity.class);
        intent.putExtra(GameActivityStrings.INITIAL_TIME, initialTime);
        intent.putExtra(GameActivityStrings.PRIZE_TIME, prizeTime);
        intent.putExtra(GameActivityStrings.PUNISHMENT_TIME, punishTime);
        intent.putExtra(GameActivityStrings.QUIZ_LEVELS, quizLevelsNumber);
        intent.putExtra(GameActivityStrings.GAME_TYPE, gameType);

        if( gameType.equals(GameActivityStrings.GAME_TYPE_QUIZ) ){
            chosenGameDifficulty = quizDifficultyTextView.getText().toString();
        } else {
            chosenGameDifficulty = survivalDifficultyTextView.getText().toString();
        }

        intent.putExtra(GameActivityStrings.GAME_DIFFICULTY, chosenGameDifficulty);

        startActivity(intent);

    }

    View.OnClickListener quizArrowsOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            if( view.getId() == R.id.quiz_left_arrow ){

                if( quizDifficultyPosition == 0) {
                    quizDifficultyTextView.setText(difficulty[2]);
                    quizDifficultyPosition = 2;
                } else {
                    quizDifficultyPosition--;
                    quizDifficultyTextView.setText(difficulty[quizDifficultyPosition%3]);
                }

            } else{
                quizDifficultyPosition++;
                quizDifficultyTextView.setText(difficulty[quizDifficultyPosition%3]);
            }

        }

    };

    View.OnClickListener survivalArrowsOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if( view.getId() == R.id.survival_left_arrow ){
                if( survivalDifficultyPosition == 0) {
                    survivalDifficultyTextView.setText(difficulty[2]);
                    survivalDifficultyPosition = 2;
                } else {
                    survivalDifficultyPosition--;
                    survivalDifficultyTextView.setText(difficulty[survivalDifficultyPosition % 3]);
                }
            }
            else{
                survivalDifficultyPosition++;
                survivalDifficultyTextView.setText(difficulty[survivalDifficultyPosition % 3]);
            }

        }

    };

    private void openCloseQuizDetails() {

        final LinearLayout quizDetails = (LinearLayout) getView().findViewById(R.id.quiz_details);
        final LinearLayout survivalDetails = (LinearLayout) getView().findViewById(R.id.survival_details);
        Button survivalButton = (Button) getView().findViewById(R.id.survival);
        Button quizButton = (Button) getView().findViewById(R.id.quiz);

        final boolean closing = quizDetails.getVisibility() == View.VISIBLE;


        if (!closing && survivalDetails.getVisibility() == View.VISIBLE) {
            openCloseDetails(survivalDetails, true, survivalDetailsHeight);
            survivalButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_button_green));
            survivalButton.setTextColor(getResources().getColor(R.color.white));
        }

        if (!closing) {
            quizButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            quizButton.setTextColor(getResources().getColor(R.color.green));
        } else {
            quizButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_button_green));
            quizButton.setTextColor(getResources().getColor(R.color.white));
        }

        quizDetailsObserver.removeOnGlobalLayoutListener(quizDetailsLayoutListener);

        openCloseDetails(quizDetails, closing, quizDetailsHeight);

    }

    private void openCloseSurvivalDetails() {

        final LinearLayout survivalDetails = (LinearLayout) getView().findViewById(R.id.survival_details);
        final LinearLayout quizDetails = (LinearLayout) getView().findViewById(R.id.quiz_details);
        Button survivalButton = (Button) getView().findViewById(R.id.survival);
        Button quizButton = (Button) getView().findViewById(R.id.quiz);

        final boolean closing = survivalDetails.getVisibility() == View.VISIBLE;

        if (!closing && quizDetails.getVisibility() == View.VISIBLE) {
            openCloseDetails(quizDetails, true, quizDetailsHeight);
            quizButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_button_green));
            quizButton.setTextColor(getResources().getColor(R.color.white));
        }

        if (!closing) {
            survivalButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            survivalButton.setTextColor(getResources().getColor(R.color.green));
        } else {
            survivalButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_button_green));
            survivalButton.setTextColor(getResources().getColor(R.color.white));
        }

        survivalDetailsObserver.removeOnGlobalLayoutListener(survivalDetailsLayoutListener);
        openCloseDetails(survivalDetails, closing, survivalDetailsHeight);

    }

    private void openCloseDetails(final LinearLayout details, final boolean closing, final int height) {

        if (!closing) {
            details.getLayoutParams().height = 0;
            details.requestLayout();
            details.setVisibility(View.VISIBLE);
        }

        int startValue = closing ? height : 0;

        Log.d("kfdslfk", "kdsfk");

        int endValue = height - startValue;

        ValueAnimator detailsAnimator = ValueAnimator.ofInt(startValue, endValue);
        detailsAnimator.setDuration(250);
        detailsAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int height = (int)animation.getAnimatedValue();
                details.getLayoutParams().height = height;
                details.requestLayout();

                if (height == 0 && closing) {
                    details.setVisibility(View.GONE);
                }

            }

        });

        detailsAnimator.start();

        detailsAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                Log.d("vmn","vmvsd");

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }

        });

    }

    public void onClick(View view){

        if (view.getId() == R.id.survival) {
            openCloseSurvivalDetails();
        } else if (view.getId() == R.id.start_survival) {
            initialTime = SURVIVAL_TIME;
            startGame(GameActivityStrings.GAME_TYPE_SURVIVAL);
        } else if (view.getId() == R.id.quiz) {
            openCloseQuizDetails();
        } else if (view.getId() == R.id.start_quiz) {
            startGame(GameActivityStrings.GAME_TYPE_QUIZ);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        initialTime = QUIZ_QUESTION_TIME;
        prizeTime = 0.5f;
        punishTime = 0.5f;
        quizLevelsNumber = 20;

        initQuizDetailsHeight();
        initSurvivalDetailsHeight();

    }

    public void initQuizDetailsHeight( ){

        quizDetails = (LinearLayout) getView().findViewById(R.id.quiz_details);

        quizDetailsObserver = quizDetails.getViewTreeObserver();
        quizDetailsObserver.addOnGlobalLayoutListener(quizDetailsLayoutListener);

    }

    public void initSurvivalDetailsHeight( ){

        final LinearLayout survivalDetails = (LinearLayout) getView().findViewById(R.id.survival_details);

        survivalDetailsObserver = survivalDetails.getViewTreeObserver();
        survivalDetailsObserver.addOnGlobalLayoutListener(survivalDetailsLayoutListener);

    }

    ViewTreeObserver.OnGlobalLayoutListener quizDetailsLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {

            quizDetailsHeight = quizDetails.getHeight();
            quizDetails.setVisibility(View.GONE);
            quizDetails.getLayoutParams().height = 0;
            quizDetails.requestLayout();
            quizDetailsObserver.removeOnGlobalLayoutListener(quizDetailsLayoutListener);

        }

    };

    ViewTreeObserver.OnGlobalLayoutListener survivalDetailsLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {

            survivalDetailsHeight = survivalDetails.getHeight();
            survivalDetails.setVisibility(View.GONE);
            survivalDetails.getLayoutParams().height = 0;
            survivalDetails.requestLayout();
            survivalDetailsObserver.removeOnGlobalLayoutListener(survivalDetailsLayoutListener);

        }

    };

    @Override
    public void onPause() {
        super.onPause();
    }

}






























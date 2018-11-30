package com.tvo.cityguess.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tvo.cityguess.R;
import com.tvo.cityguess.database.Database;
import com.tvo.cityguess.helpers.DialogsShownHashMap;
import com.tvo.cityguess.helpers.GameActivityStrings;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class GameActivity extends Activity {

    private static final String SAVED_CITY_1 = "savedCity1";
    private static final String SAVED_CITY_2 = "savedCity2";
    private static final String SAVED_CITY_3 = "savedCity3";
    private static final String SAVED_CITY_4 = "savedCity4";
    private static final String SAVED_COUNTRY = "savedCountry";

    private static final String RIGHT_ANSWER = "savedRightAnswer";
    private static final String REMAINING_TIME = "remainingTime";

    /**
     * Game state
     */
    private float initialTime;
    private float prizeTime;
    private float punishmentTime;
    private float remainingTime;
    private float spentInOneGameTime = 0;
    private float totalSpentInGameTime;
    private int totalSpentInGameTimeInt;
    private int quizLevelsNumber;
    private String gameType;
    private String gameDifficulty;

    // As the random country choosing sometimes gives the same countries at the one game session, we remember the previous 50 countries,
    // and  compare each of them with the 51 one, and if it not occurs in that 50 countries, then we place it in a game.
    private int queueSize = 50;
    private ArrayBlockingQueue<String> notRepeatQuestionQueue = new ArrayBlockingQueue<>(queueSize);

    DialogsShownHashMap whichDialogIsShown = new DialogsShownHashMap();

//    private int[] survivalTimeAchievements = { 3, 120, 600, 1800, 1802 /* The last is dummy, in order to organize a loop */ };
//
//    private String[] survivalAchievementsValues = { "40", "2", "10", "30" };
//
//    private int[] totalTimeAchievements = { 1, 2, 3, 5, 6 /* The last is dummy, in order to organize a loop */  };

    /**
     * Level state
     */
    private int completedQuizzesCounter;
    private int rightAnswersCounter;
    private int rightAnswersInRowCounter = 0;
    private int totalRightAnswersCounter;
    private int wrongAnswersCounter;

    private int activeDialogsCount = 0;

    /**
     * UI
     */
    private ViewGroup gameActivityLayout;

    private TextView completedCountTextView;
    private TextView rightCountTextView;
    private TextView wrongCountTextView;
    private TextView timerCountTextView;

    private String rightAnswer;

    // We fix previous answer right fact in order to count right answers in a row.
    private boolean previousAnswerRight = false;

    // We fix game end fact in order to determine the time of showing time spent in survival dialog.
    private boolean gameIsEnded = false;

//    // We make them global in order to use it when game is ended, for deciding activity finishing organization
//    private boolean needToShowOneSurvivalAchievement;
//
//    private  boolean needsToShowTotalTimeAchievementDialog;
//
    // This SharedPreferences is used for storing total right answers of player
    private static SharedPreferences sharedPreferences;

    private static SharedPreferences.Editor sharedPreferencesEditor;

    // This Random  is used for random arranging cities in buttons
    private Random random = new Random(System.nanoTime());

    // This handler is used for executing repeating task using Runnable
    private Handler timerHandler = new Handler();

    private Runnable timerUIRunnable = new Runnable() {

        @Override
        public void run() {

            timerCountTextView.setText(String.format(Locale.US, "%.2f", remainingTime));

                if (gameType.equals(GameActivityStrings.GAME_TYPE_QUIZ)) {

                    if ( completedQuizzesCounter == quizLevelsNumber ){

                        // If completed quizzes number reachs the maximum allowed quizzes number we need to end the game.
                        endGame();

                    }

                    if ( remainingTime < 0.01f ) {

                        // If remainingTime < 0.01f and game type is quiz, it means that either we need to start  quiz new part, or we need to end the game.
                        completedQuizzesCounter++;
                        wrongAnswersCounter++;
                        previousAnswerRight = false;

                        if ( remainingTime < -0.01 ) {

                            //In that case we need to end the game.
                            endGame();

                        } else {

                            // And in that case we need to setup quiz new question
                            setupQuiz();

                        }

                    }

                } else if ( gameType.equals(GameActivityStrings.GAME_TYPE_SURVIVAL) ) {

                    // If remainingTime < 0.01f and game type is survival, it means that we need to end the game.
                    if( remainingTime < 0.01 ) {
                            endGame();
                    }

                }

        }

    };

    private Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            remainingTime = remainingTime - 0.01f;
            spentInOneGameTime += 0.01f;


            runOnUiThread(timerUIRunnable);

            if ( remainingTime < 0.01f ) {
                return;
            }

            timerHandler.postDelayed( timerRunnable, 10 );
        }

    };

    /**
     * Instantiating or re-instantiating game
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main_page);

        sharedPreferences = getSharedPreferences(getString(R.string.preferences_file_name), MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

//        completedCountTextView = (TextView) findViewById(R.id.completed_quizzes_text);
//        rightCountTextView = (TextView) findViewById(R.id.right_answers_text);
//        wrongCountTextView = (TextView) findViewById(R.id.wrong_answers_text);
//        timerCountTextView = (TextView) findViewById(R.id.timer_count);

        gameActivityLayout = (ViewGroup) findViewById( R.id.game_layout );

        completedCountTextView = (TextView) findViewById(R.id.completed_quizzes);
        rightCountTextView = (TextView) findViewById(R.id.right_answers);
        wrongCountTextView = (TextView) findViewById(R.id.wrong_answers);
        timerCountTextView = (TextView) findViewById(R.id.timer_count);


        TextView country = (TextView)findViewById(R.id.country_name);
        LinearLayout citiesLayout = (LinearLayout) findViewById(R.id.cities);
        Button city1 = (Button)citiesLayout.getChildAt(0);
        Button city2 = (Button)citiesLayout.getChildAt(1);
        Button city3 = (Button)citiesLayout.getChildAt(2);
        Button city4 = (Button)citiesLayout.getChildAt(3);

        city1.setOnClickListener(cityButtonClickListener);
        city2.setOnClickListener(cityButtonClickListener);
        city3.setOnClickListener(cityButtonClickListener);
        city4.setOnClickListener(cityButtonClickListener);

        initialTime = getIntent().getFloatExtra(GameActivityStrings.INITIAL_TIME, 0.0f);
        prizeTime = getIntent().getFloatExtra(GameActivityStrings.PRIZE_TIME, 0.0f);
        punishmentTime = getIntent().getFloatExtra(GameActivityStrings.PUNISHMENT_TIME, 0.0f);
        quizLevelsNumber = getIntent().getIntExtra(GameActivityStrings.QUIZ_LEVELS, 0);
        gameType = getIntent().getStringExtra(GameActivityStrings.GAME_TYPE);
        gameDifficulty = getIntent().getStringExtra(GameActivityStrings.GAME_DIFFICULTY);
        remainingTime = initialTime;

        setupQuiz();

        if ( savedInstanceState != null ) {
            country.setText(savedInstanceState.getString(SAVED_COUNTRY));
            city1.setText(savedInstanceState.getString(SAVED_CITY_1));
            city2.setText(savedInstanceState.getString(SAVED_CITY_2));
            city3.setText(savedInstanceState.getString(SAVED_CITY_3));
            city4.setText(savedInstanceState.getString(SAVED_CITY_4));

            rightAnswer = savedInstanceState.getString(RIGHT_ANSWER);

            remainingTime = savedInstanceState.getFloat(REMAINING_TIME);
            rightAnswersCounter = savedInstanceState.getInt(GameActivityStrings.RIGHT_ANSWERS_IN_GAME_COUNT);
            wrongAnswersCounter = savedInstanceState.getInt(GameActivityStrings.WRONG_ANSWERS_COUNT);
            completedQuizzesCounter = savedInstanceState.getInt(GameActivityStrings.COMPLETED_QUIZZES);



            timerCountTextView.setText(String.format(Locale.US, "%.2f", remainingTime));
            rightCountTextView.setText( String.valueOf(rightAnswersCounter) );
            wrongCountTextView.setText( String.valueOf(wrongAnswersCounter) );
            completedCountTextView.setText( String.valueOf(completedQuizzesCounter) );
            
        }

        runJustBeforeBeingDrawn( gameActivityLayout, new Runnable() {

            @Override
            public void run() {
                recreateTextView();
            }

        });

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        LinearLayout citiesLayout = (LinearLayout) findViewById(R.id.cities);

        savedInstanceState.putString(SAVED_COUNTRY,((TextView)findViewById(R.id.country_name)).getText().toString() );
        savedInstanceState.putString(SAVED_CITY_1,((Button)citiesLayout.getChildAt(0)).getText().toString() );
        savedInstanceState.putString(SAVED_CITY_2,((Button)citiesLayout.getChildAt(1)).getText().toString() );
        savedInstanceState.putString(SAVED_CITY_3,((Button)citiesLayout.getChildAt(2)).getText().toString() );
        savedInstanceState.putString(SAVED_CITY_4,((Button)citiesLayout.getChildAt(3)).getText().toString() );
        savedInstanceState.putString(RIGHT_ANSWER, rightAnswer);
        savedInstanceState.putInt(GameActivityStrings.COMPLETED_QUIZZES, completedQuizzesCounter);
        savedInstanceState.putInt(GameActivityStrings.RIGHT_ANSWERS_IN_GAME_COUNT, rightAnswersCounter);
        savedInstanceState.putInt(GameActivityStrings.WRONG_ANSWERS_COUNT, wrongAnswersCounter);
        savedInstanceState.putFloat(REMAINING_TIME, remainingTime);
    }

    @Override
    public void onResume() {
        timerHandler.postDelayed(timerRunnable, 0);
        super.onResume();
    }

    @Override
    public void onPause() {
        timerHandler.removeCallbacks(timerRunnable);
        super.onPause();
    }

    public View.OnClickListener cityButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if( gameType.equals(GameActivityStrings.GAME_TYPE_QUIZ) ){
                remainingTime = initialTime;
            }

            completedQuizzesCounter++;
            completedCountTextView.setText( String.valueOf(completedQuizzesCounter) );
            Button button = (Button) view;

            if( button.getText().equals(rightAnswer) ) {
                previousAnswerRight = true;
                rightAnswersCounter++;
                totalRightAnswersCounter++;

                // In common case, we check, whether previousAnswerRight == true. If it is, we increase rightAnswersInRowCounter.
                // But if there is a first question, we can not do so.  Instead we determine, whether it is the first question by checking
                // completedQuizzesCounter == 0 condition, and if it is true, we increase rightAnswersInRowCounter ( because we in a right answer case. :) ) .
                if( completedQuizzesCounter == 0
                        || previousAnswerRight) {
                    rightAnswersInRowCounter++;
                }

                // Save right answer achievements
                saveRightAnswerAchievements();

                rightCountTextView.setText( String.valueOf(rightAnswersCounter) );
            }
            else {
                rightAnswersInRowCounter = 0;
                previousAnswerRight = false;
                wrongAnswersCounter++;
                wrongCountTextView.setText( String.valueOf(wrongAnswersCounter) );
            }

            setupQuiz();
        }
    };

    private void setupQuiz() {

        Database.QuizLevel level = Database.sharedDatabase(this).generateQuizLevel( gameDifficulty );
        String selectedCountryName = level.question();

        TextView countryName = (TextView) findViewById(R.id.country_name);
        LinearLayout citiesLayout = (LinearLayout) findViewById(R.id.cities);


        if( !notRepeatQuestionQueue.contains(selectedCountryName) ) {

            notRepeatQuestionQueue.add(selectedCountryName);
            if( notRepeatQuestionQueue.size() == 50 ){
                notRepeatQuestionQueue.remove();
            }

        } else {

           setupQuiz();
           return;

        }

        rightAnswer = level.guesses().get(0);

        countryName.setText(selectedCountryName);

        for( int randomNumberMax = 4; randomNumberMax > 0 ; randomNumberMax--) {
            int randomNumber = random.nextInt(randomNumberMax);
            String city = level.guesses().get(randomNumber);
            Button cityButton = (Button) citiesLayout.getChildAt(randomNumberMax - 1);
            cityButton.setText(city);
            level.guesses().remove(randomNumber);
        }

        if (gameType != null) {
            float timeDiff = previousAnswerRight ? prizeTime : -punishmentTime;

            if (gameType.equals(GameActivityStrings.GAME_TYPE_SURVIVAL)) {
                remainingTime = remainingTime + timeDiff;
            } else if (gameType.equals(GameActivityStrings.GAME_TYPE_QUIZ)) {
                timerHandler.removeCallbacks(timerRunnable);
                initialTime = initialTime + timeDiff;
                remainingTime = initialTime;
                timerHandler.postDelayed(timerRunnable, 10);
            }

            timerCountTextView.setText(String.format(Locale.US, "%.2f", remainingTime));

            // We call this function here, because here another quiz is set up, and it is right time to check different variations of right answers ( total, in a row etc. ).
//            showAchievementsDialogIfNeeded();

        }
    }

    protected void recreateTextView() {

////        gameActivityLayout.removeAllViews();
//        final int maxWidth = gameActivityLayout.getWidth();
//        final int maxHeight = gameActivityLayout.getHeight();
//        final AutoResizeTextView autoResizeTextView = new AutoResizeTextView(GameActivity.this);
//        autoResizeTextView.setGravity(Gravity.CENTER);
////        final int width = _widthSeekBar.getProgress() * maxWidth / _widthSeekBar.getMax();
//        final int height = _heightSeekBar.getProgress() * maxHeight / _heightSeekBar.getMax();
//        final int maxLinesCount = 3/*Integer.parseInt(_linesCountTextView.getText().toString())*/;
//
//        autoResizeTextView.setMaxLines(maxLinesCount);
//        autoResizeTextView.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, maxHeight, getResources().getDisplayMetrics()));
//        autoResizeTextView.setEllipsize(TextUtils.TruncateAt.END);
//
//        // since we use it only once per each click, we don't need to cache the results, ever
//        autoResizeTextView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
//        autoResizeTextView.setBackgroundColor(0xff00ff00);
//
//        final String text = _contentEditText.getText().toString();
//        autoResizeTextView.setText(text);
//        gameActivityLayout.addView(autoResizeTextView);

    }

    public static void runJustBeforeBeingDrawn(final View view, final Runnable runnable) {
        final ViewTreeObserver.OnPreDrawListener preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                runnable.run();
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        };
        view.getViewTreeObserver().addOnPreDrawListener(preDrawListener);
    }

    private void endGame() {

        gameIsEnded = true;

        totalSpentInGameTime = sharedPreferences.getFloat(GameActivityStrings.TOTAL_TIME_IN_GAME, 0f);
        totalSpentInGameTime += spentInOneGameTime;

        sharedPreferencesEditor.putFloat(GameActivityStrings.TOTAL_TIME_IN_GAME, totalSpentInGameTime);

        // Commit the edits!
        sharedPreferencesEditor.commit();

//        showAchievementsDialogIfNeeded();

//        if( !needToShowOneSurvivalAchievement && !needsToShowTotalTimeAchievementDialog) {
            startGameEndActivity();
//        }

    }

//    private void showAchievementsDialogIfNeeded(){
//
//        String messagePrefix = "";
//
//        int whichRightAnswerDialogHasShown =  whichDialogIsShown.get(GameActivityStrings.RIGHT_ANSWERS_DIALOG_SHOWN) ;
//
//        boolean needToShowAchievementsDialog = ( ( rightAnswersCounter == 10 ) && ( whichRightAnswerDialogHasShown != 10 ) ) ||
//                ( ( rightAnswersCounter == 25 ) && ( whichRightAnswerDialogHasShown != 25 ) ) ||
//                ( ( rightAnswersCounter == 50 ) && ( whichRightAnswerDialogHasShown != 50 ) );
//
//        if( needToShowAchievementsDialog ){
//
//            messagePrefix = getString(R.string.game_achievements_dialog_prefix);
//            showDialog( getResources().getString(R.string.congratulations),
//                    rightAnswersCounter + " " + messagePrefix);
//            activeDialogsCount++;
//
//            sharedPreferencesEditor.putInt(GameActivityStrings.ACHIEVEMENT_RIGHT_ANSWERS_IN_GAME, rightAnswersCounter);
//
//            whichDialogIsShown.put(GameActivityStrings.RIGHT_ANSWERS_DIALOG_SHOWN, rightAnswersCounter);
//
//        }
//
//        //  We count right answers in a row , and if its count reach 5, 10 or 15, we show corresponding achievement dialog.
//
//        if( previousAnswerRight  ){
//            rightAnswersInRowCounter++;
//        }
//
//        int whichAnswerInRowDialogHasShown = whichDialogIsShown.get(GameActivityStrings.RIGHT_ANSWERS_IN_ROW_DIALOG_SHOWN) ;
//
//        boolean needToShowRowAchievementsDialog = ( ( rightAnswersInRowCounter == 5  ) && ( whichAnswerInRowDialogHasShown !=  5 ) )||
//                ( ( rightAnswersInRowCounter == 10  ) && ( whichAnswerInRowDialogHasShown !=  10 ) )||
//                ( ( rightAnswersInRowCounter == 15  ) && ( whichAnswerInRowDialogHasShown !=  15 ) );
//
//        if( needToShowRowAchievementsDialog ){
//
//            messagePrefix = getString(R.string.row_achievements_dialog_prefix);
//            showDialog( getResources().getString(R.string.congratulations),
//                    rightAnswersInRowCounter + " " + messagePrefix);
//            activeDialogsCount++;
//            sharedPreferencesEditor.putInt(GameActivityStrings.ACHIEVEMENT_RIGHT_ANSWERS_IN_ROW, rightAnswersInRowCounter);
//
//            whichDialogIsShown.put(GameActivityStrings.RIGHT_ANSWERS_IN_ROW_DIALOG_SHOWN, rightAnswersInRowCounter);
//
//        }
//
//        totalRightAnswersCounter = sharedPreferences.getInt(GameActivityStrings.ACHIEVEMENT_TOTAL_RIGHT_ANSWERS, 0);
//
//        int whichTotalRightAnswerDialogHasShown = whichDialogIsShown.get(GameActivityStrings.TOTAL_RIGHT_ANSWERS_DIALOG_SHOWN) ;
//
//        boolean needsToShowTotalAchievementsDialog = ( ( totalRightAnswersCounter == 100 ) && ( whichTotalRightAnswerDialogHasShown != 100 ) ) ||
//                ( ( totalRightAnswersCounter == 500 ) && ( whichTotalRightAnswerDialogHasShown != 500 ) ) ||
//                ( ( totalRightAnswersCounter == 2500 ) && ( whichTotalRightAnswerDialogHasShown != 2500 ) );
//
//        if( needsToShowTotalAchievementsDialog ){
//
//            messagePrefix = getString(R.string.total_achievements_dialog_prefix);
//            showDialog( getResources().getString(R.string.congratulations),
//                    totalRightAnswersCounter + " " + messagePrefix);
//            activeDialogsCount++;
//
//            whichDialogIsShown.put(GameActivityStrings.TOTAL_RIGHT_ANSWERS_DIALOG_SHOWN, totalRightAnswersCounter);
//
//        }
//
//        // We do not put total right answers here, because we do it in another place
//
//        // This dialogs must be shown on game end
//        if( gameIsEnded  ) {
//
//            if( gameType.equals(GameActivityStrings.GAME_TYPE_SURVIVAL) ) {
//
//                needToShowOneSurvivalAchievement = survivalTimeAchievements[0] < spentInOneGameTime;
//
//                if (needToShowOneSurvivalAchievement) {
//                    showSurvivalAchievementsDialog();
//                }
//
//            }
//
//            // This is total time spent in game achievements dialog
//            totalSpentInGameTime = sharedPreferences.getFloat(GameActivityStrings.TOTAL_TIME_IN_GAME, 0);
//            totalSpentInGameTimeInt = Math.round(totalSpentInGameTime) / 1000;
//
//            needsToShowTotalTimeAchievementDialog = totalTimeAchievements[0] < totalSpentInGameTimeInt;
//
//            if (needsToShowTotalTimeAchievementDialog) {
//                showTotalTimeAchievementsDialog();
//            }
//
//        }
//
//        // Commit the edits!
//        sharedPreferencesEditor.commit();
//
//    }

//    private void showSurvivalAchievementsDialog(){
//
//        String spentInOneSurvivalString = "";
//        String unitOfTime;
//
//        if( ( survivalTimeAchievements[0] < spentInOneGameTime) &&  ( spentInOneGameTime < survivalTimeAchievements[1] ) ){
//            unitOfTime = getString(R.string.second);
//        } else {
//            unitOfTime = getString(R.string.minute);
//        }
//
//        int i;
//        for( i = 0; i < survivalTimeAchievements.length; i++ ) {
//
//            if ((survivalTimeAchievements[i] < spentInOneGameTime) && (spentInOneGameTime < survivalTimeAchievements[i + 1])) {
//                spentInOneSurvivalString = survivalAchievementsValues[i];
//                sharedPreferencesEditor.putString(GameActivityStrings.ACHIEVEMENT_TIME_IN_SURVIVAL, survivalAchievementsValues[i]);
//                sharedPreferencesEditor.commit();
//            }
//
//        }
//
////                else if( ( survivalTimeAchievements[1] < spentInOneGameTime ) && ( spentInOneGameTime < survivalTimeAchievements[2] ) ){
////                    spentInOneSurvivalString = survivalAchievementsValues[1];
////                }  else if( ( survivalTimeAchievements[2] < spentInOneGameTime ) && ( spentInOneGameTime < survivalTimeAchievements[3] ) ){
////                    spentInOneSurvivalString = survivalAchievementsValues[2];
////                }  else if(  survivalTimeAchievements[3] < spentInOneGameTime  ){
////                    spentInOneSurvivalString = survivalAchievementsValues[3];
////                }
//
//        String messagePrefix = getString(R.string.time_survival_achievements_dialog_prefix);
//        showDialog(getString(R.string.congratulations), spentInOneSurvivalString + " "+ unitOfTime + " "
//                + messagePrefix);
//        activeDialogsCount++;
//    }
//
//    private void showTotalTimeAchievementsDialog(){
//
//        String totalSpentInGameString = "";
//        String unitOfTime = getString(R.string.hours);
//
//        if ( totalSpentInGameTime < totalTimeAchievements[1] ){
//            unitOfTime = unitOfTime.substring(0,4);
//        }
//
//        int i;
//        for( i = 0; i < totalTimeAchievements.length; i++ ) {
//
//            if ((totalTimeAchievements[i] <= totalSpentInGameTimeInt) && (totalSpentInGameTimeInt < totalTimeAchievements[i + 1])) {
//                totalSpentInGameString = String.valueOf( totalTimeAchievements[i] );
//                sharedPreferencesEditor.putFloat(GameActivityStrings.ACHIEVEMENT_TOTAL_TIME_IN_GAME, totalTimeAchievements[i]);
//                sharedPreferencesEditor.commit();
//            }
//
//        }
//
//
//        String messagePrefix = getString(R.string.total_time_achievements_dialog_prefix);
//        showDialog(getString(R.string.congratulations), totalSpentInGameString + " "+ unitOfTime + " "
//                + messagePrefix);
//        activeDialogsCount++;
//    }

//    private void showDialog(String title, String message) {
//
//        AchievementReachedDialogFragment newFragment = AchievementReachedDialogFragment.newInstance(
//                title, message);
//        newFragment.setOnAchievementDialogListener(AchievementDialogOkListener);
//
//        newFragment.show( getFragmentManager(), "dialog" );
//        // We pause the game timer when dialog appears.
//        timerHandler.removeCallbacks(timerRunnable);
//
//    }

//    AchievementReachedDialogFragment.AchievementDialogListener AchievementDialogOkListener = new AchievementReachedDialogFragment.AchievementDialogListener() {
//        @Override
//        public void onOkClick() {
//
//            if( gameIsEnded ){
//                startGameEndActivity();
//            }
//
//            activeDialogsCount--;
//
//            if( activeDialogsCount == 0 ) {
//                timerHandler.postDelayed(timerRunnable, 0);
//            }
//
//        }
//
//    };

    private void saveRightAnswerAchievements(){
        sharedPreferencesEditor.putInt(GameActivityStrings.ACHIEVEMENT_TOTAL_RIGHT_ANSWERS, totalRightAnswersCounter);

        if (sharedPreferences.getInt(GameActivityStrings.ACHIEVEMENT_RIGHT_ANSWERS_IN_GAME, 0) < rightAnswersCounter) {
            sharedPreferencesEditor.putInt(GameActivityStrings.ACHIEVEMENT_RIGHT_ANSWERS_IN_GAME, rightAnswersCounter);
        }

        if (sharedPreferences.getInt(GameActivityStrings.ACHIEVEMENT_RIGHT_ANSWERS_IN_ROW, 0) < rightAnswersInRowCounter) {
            sharedPreferencesEditor.putInt(GameActivityStrings.ACHIEVEMENT_RIGHT_ANSWERS_IN_ROW, rightAnswersInRowCounter);
        }

        sharedPreferencesEditor.commit();
    }

    public  void startGameEndActivity(){

        Intent intent = new Intent(this, EndGameActivity.class);
        intent.putExtra(GameActivityStrings.GAME_TYPE, gameType);
        intent.putExtra(GameActivityStrings.GAME_DIFFICULTY, gameDifficulty);
//        intent.putExtra(GameActivityStrings.COMPLETED_QUIZZES, completedQuizzesCounter);
        intent.putExtra(GameActivityStrings.RIGHT_ANSWERS_IN_GAME_COUNT, rightAnswersCounter);
        intent.putExtra(GameActivityStrings.WRONG_ANSWERS_COUNT, wrongAnswersCounter);
        startActivity(intent);

        finish();

    }

}
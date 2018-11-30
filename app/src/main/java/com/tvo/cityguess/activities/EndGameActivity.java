package com.tvo.cityguess.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.tvo.cityguess.R;
import com.tvo.cityguess.helpers.GameActivityStrings;


public class EndGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Intent intent = getIntent();

        TextView gameTypeTextView = (TextView) findViewById(R.id.game_type);
        TextView gameDifficultyTextView = (TextView) findViewById(R.id.game_difficulty);
//        TextView completedQuizzesCountTextView = (TextView)findViewById(R.id.completed_quizzes_count);
        TextView rightAnswersCountTextView = (TextView) findViewById(R.id.right_answers_count);
        TextView wrongAnswersCountTextView = (TextView) findViewById(R.id.wrong_answers_count);

        gameTypeTextView.setText( intent.getStringExtra(GameActivityStrings.GAME_TYPE) );
        gameDifficultyTextView.setText( intent.getStringExtra(GameActivityStrings.GAME_DIFFICULTY) );
//        completedQuizzesCountTextView.setText( String.valueOf( intent.getIntExtra(GameActivityStrings.COMPLETED_QUIZZES,0) ) );
        rightAnswersCountTextView.setText( String.valueOf( intent.getIntExtra( GameActivityStrings.RIGHT_ANSWERS_IN_GAME_COUNT, 0) ) );
        wrongAnswersCountTextView.setText( String.valueOf( intent.getIntExtra( GameActivityStrings.WRONG_ANSWERS_COUNT, 0) ) );

    }

}

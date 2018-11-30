package com.tvo.cityguess.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.tvo.cityguess.R;

/**
 * Created by victor on 6/23/16.
 */
public class AchievementReachedDialogFragment extends DialogFragment {

    private static final String TITLE = "title";
    private static final String MESSAGE = "message";

    private static AchievementDialogListener achievementDialogListener;

    // Default constructor. Every fragment must have an empty constructor, so it can be instantiated when restoring its activity's state.
    public AchievementReachedDialogFragment() {
    }

    /**
     * Create a new instance of MyFragment that will be initialized
     * with the given arguments.
     */
    public static AchievementReachedDialogFragment newInstance(String title, String message) {

        AchievementReachedDialogFragment achievementReachedDialogFragment = new AchievementReachedDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putString(TITLE, title);
        arguments.putString(MESSAGE, message);
        achievementReachedDialogFragment.setArguments(arguments);

        return achievementReachedDialogFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View  view = inflater.inflate(R.layout.dialog_fragment_achievement_reached, container, false);

        Bundle arguments = getArguments();

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView messageView = (TextView) view.findViewById(R.id.message);

        String title = arguments.getString(TITLE);
        String message = arguments.getString(MESSAGE);

        titleView.setText(title);
        messageView.setText(message);

        Button okButton = (Button) view.findViewById(R.id.ok);
        okButton.setOnClickListener(OnDialogOkClickListener);

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    View.OnClickListener OnDialogOkClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
            achievementDialogListener.onOkClick();
        }
    };

    public interface AchievementDialogListener{
        public void onOkClick();
    }

    public void setOnAchievementDialogListener( AchievementDialogListener achievementDialogListener ){
        this.achievementDialogListener = achievementDialogListener;
    }

}










































package ru.uj.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "ru.uj.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "ru.uj.geoquiz.answer_shown";
    private static final String KEY_4 = "index4";
    private boolean mAnswerIsTrue;
    private Button mShowAnswerButton;
    private TextView mAnswerTextView;
    private TextView mCheaterCountText;
    int count;
    boolean showAnswer;

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerTextView = findViewById(R.id.answer_text_view);
        mShowAnswerButton = findViewById(R.id.show_answer_button);
        mCheaterCountText = findViewById(R.id.cheater_count_text);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        count = getIntent().getIntExtra("cheatCount1", 0);

        if (savedInstanceState != null) {
            count = savedInstanceState.getInt(KEY_4, 0);
            if (savedInstanceState.containsKey("show")) {
                showAnswer = savedInstanceState.getBoolean("show");
                if (showAnswer) {
                    Intent data = new Intent();
                    data.putExtra("cheatCount2", count);
                    setResult(RESULT_OK, data);
                }
            }

        }

        setCountCheatText();

        if (count == 0) {
            mShowAnswerButton.setEnabled(false);
        }
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public void onClickShowAnswer(View view) {
        count--;
        setCountCheatText();

        if (count == 0) {
            mShowAnswerButton.setEnabled(false);
        }
        if (mAnswerIsTrue) {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
        setAnswerShownResult(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = mShowAnswerButton.getWidth() / 2;
            int cy = mShowAnswerButton.getHeight() / 2;
            float radius = mShowAnswerButton.getWidth();
            Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            });
            anim.start();
        } else {
            mShowAnswerButton.setVisibility(View.INVISIBLE);
        }
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        showAnswer = isAnswerShown;
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        data.putExtra("cheatCount2", count);
        setResult(RESULT_OK, data);
    }

    private void setCountCheatText() {
        Resources res = getResources();
        String countCheat = String.format(res.getString(R.string.count_cheat, count), count);
        mCheaterCountText.setText(countCheat);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_4, count);
        outState.putBoolean("show", showAnswer);
    }
}

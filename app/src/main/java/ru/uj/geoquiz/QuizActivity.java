package ru.uj.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private TextView mQuestionTextView;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private static final String KEY_1 = "index1";
    private static final String KEY_2 = "index2";
    private static final String KEY_3 = "index3";
    private static final int REQUEST_CODE_CHEAT = 0;
    private int mCurrentIndex = 0;
    private int mAnsweredCount = 0;
    static int mCountCheat = 3;
    private Question[] mQuestionBank;

    public QuizActivity() {
        mQuestionBank = new Question[]{
                new Question(R.string.question_australia, true, false, false, false),
                new Question(R.string.question_oceans, true, false, false, false),
                new Question(R.string.question_mideast, false, false, false, false),
                new Question(R.string.question_africa, false, false, false, false),
                new Question(R.string.question_americas, true, false, false, false),
                new Question(R.string.question_asia, true, false, false, false)
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_1, 0);
            mQuestionBank = (Question[]) savedInstanceState.getParcelableArray(KEY_2);
            mAnsweredCount = savedInstanceState.getInt(KEY_3, 0);
        }

        mQuestionTextView = findViewById(R.id.question_text_view);
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);
        mPrevButton = findViewById(R.id.prev_button);
        updateQuestion();

        if (mQuestionBank[mCurrentIndex].isAnswered() == true) {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        }

        if (mQuestionBank.length == mAnsweredCount) {
            mNextButton.setEnabled(false);
            mPrevButton.setEnabled(false);
        }

    }

    public void onClickButtonTrue(View view) {
        checkAnswer(true);
    }

    public void onClickButtonFalse(View view) {
        checkAnswer(false);
    }

    public void onClickNext(View view) {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();

        if (mQuestionBank[mCurrentIndex].isAnswered() == false) {
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        } else {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        }
    }

    public void onClickPrev(View view) {
        mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
        if (mCurrentIndex == -1) {
            mCurrentIndex = mQuestionBank.length - 1;
        }
        updateQuestion();

        if (mQuestionBank[mCurrentIndex].isAnswered() == false) {
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        } else {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        }
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {

        if (mQuestionBank[mCurrentIndex].isAnswered() == false) {
            mQuestionBank[mCurrentIndex].setAnswered(true);
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        }

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (mQuestionBank[mCurrentIndex].isCheater()) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mQuestionBank[mCurrentIndex].setQuestionAnsweredTrue(true);
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

        int answeredCount = 0;
        for (Question answer :
                mQuestionBank) {
            if (answer.isAnswered() == true) {
                answeredCount++;
            }
        }

        if (mQuestionBank.length == answeredCount) {
            mNextButton.setEnabled(false);
            mPrevButton.setEnabled(false);
            mAnsweredCount = answeredCount;
            int questionTrueAnswerCount = 0;
            for (Question trueAnswer :
                    mQuestionBank) {
                if (trueAnswer.isQuestionAnsweredTrue() == true) {
                    questionTrueAnswerCount++;
                }
            }
            int AnswerTruePercent = (questionTrueAnswerCount * 100) / mQuestionBank.length;
            Resources res = getResources();
            String textResult = String.format(res.getString(R.string.result_question), AnswerTruePercent);
            Toast.makeText(this, textResult, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_1, mCurrentIndex);
        savedInstanceState.putParcelableArray(KEY_2, mQuestionBank);
        savedInstanceState.putInt(KEY_3, mAnsweredCount);
    }

    public void onClickCheat(View view) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
        startActivityForResult(intent, REQUEST_CODE_CHEAT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null)
                return;

            mQuestionBank[mCurrentIndex].setCheater(CheatActivity.wasAnswerShown(data));
        }
    }
}

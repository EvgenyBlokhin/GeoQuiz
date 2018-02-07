package ru.uj.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private TextView mQuestionTextView;
    private Button mTrueButton;
    private Button mFalseButton;
    private static final String KEY_INDEX = "index";
    private int mCurrentIndex = 0;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true, false, false),
            new Question(R.string.question_oceans, true, false, false),
            new Question(R.string.question_mideast, false, false, false),
            new Question(R.string.question_africa, false, false, false),
            new Question(R.string.question_americas, true, false, false),
            new Question(R.string.question_asia, true, false, false)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mQuestionTextView = findViewById(R.id.question_text_view);
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        updateQuestion();
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

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            mQuestionBank[mCurrentIndex].setQuestionAnsweredTrue(true);
        } else {
            messageResId = R.string.incorrect_toast;
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
            int questionTrueAnswerCount = 0;
            for (Question trueAnswer :
                    mQuestionBank) {
                if (trueAnswer.isQuestionAnsweredTrue() == true) {
                    questionTrueAnswerCount++;
                }
            }
            int AnswerTruePercent = (questionTrueAnswerCount * 100) / mQuestionBank.length;
//           Прочитай статью по инициализации строковых ресурсов из кода
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
}

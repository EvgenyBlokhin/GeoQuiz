package ru.uj.geoquiz;

/**
 * Created by Blokhin Evgeny on 02.02.2018.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mIsAnswered;
    private boolean mQuestionAnsweredTrue;

    public Question(int textResId, boolean answerTrue, boolean isAnswered, boolean QuestionAnsweredTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mIsAnswered = isAnswered;
        mQuestionAnsweredTrue = QuestionAnsweredTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isAnswered() {
        return mIsAnswered;
    }

    public void setAnswered(boolean answered) {
        mIsAnswered = answered;
    }

    public boolean isQuestionAnsweredTrue() {
        return mQuestionAnsweredTrue;
    }

    public void setQuestionAnsweredTrue(boolean questionAnsweredTrue) {
        mQuestionAnsweredTrue = questionAnsweredTrue;
    }
}

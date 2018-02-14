package ru.uj.geoquiz;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Blokhin Evgeny on 02.02.2018.
 */

public class Question implements Parcelable {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mIsAnswered;
    private boolean mQuestionAnsweredTrue;
    private boolean mIsCheater;

    public Question(int textResId, boolean answerTrue, boolean isAnswered, boolean QuestionAnsweredTrue, boolean IsCheater) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mIsAnswered = isAnswered;
        mQuestionAnsweredTrue = QuestionAnsweredTrue;
        mIsCheater = IsCheater;
    }

    protected Question(Parcel in) {
        mTextResId = in.readInt();
        mAnswerTrue = in.readByte() != 0;
        mIsAnswered = in.readByte() != 0;
        mQuestionAnsweredTrue = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mTextResId);
        dest.writeByte((byte) (mAnswerTrue ? 1 : 0));
        dest.writeByte((byte) (mIsAnswered ? 1 : 0));
        dest.writeByte((byte) (mQuestionAnsweredTrue ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

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

    public boolean isCheater() {
        return mIsCheater;
    }

    public void setCheater(boolean cheater) {
        mIsCheater = cheater;
    }
}

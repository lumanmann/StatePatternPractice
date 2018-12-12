package com.lumanman.statepatternpractice;

import android.widget.Toast;

import java.util.ArrayList;

public class LogoutState implements State {
    public static final int LOGOUT = 0;

    interface LogoutCallback {
        public void showToast(String message);
        public void changeState(Member member);

    }

    LogoutCallback logoutCallback;

    public LogoutState(LogoutCallback logoutCallback) {
        this.logoutCallback = logoutCallback;
    }

    @Override
    public int getState() {
        return this.LOGOUT;
    }

    @Override
    public void changePasswordClicked(ArrayList<Member> members) {
        logoutCallback.showToast("請先登入");
    }

    @Override
    public void loginClicked(String acc, String password, ArrayList<Member> members) {
        Member tmp = new Member(acc, password);

        for (Member member : members) {
            if (member.isEqual(tmp)) {
                logoutCallback.changeState(tmp);
                logoutCallback.showToast("成功登入");

                return;
            }
        }

        logoutCallback.showToast("帳號/密碼不正確");

    }

    @Override
    public void logoutClicked(State currentState) {
        logoutCallback.showToast("請先登入才能登出");
    }
}

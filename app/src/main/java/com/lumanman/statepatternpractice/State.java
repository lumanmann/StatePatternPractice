package com.lumanman.statepatternpractice;

import android.widget.EditText;

import java.util.ArrayList;

public interface State {
    public int getState();
    public void changePasswordClicked(ArrayList<Member> members);
    public void loginClicked(String acc, String password, ArrayList<Member> members);
    public void logoutClicked(State currentState);

}

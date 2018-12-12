package com.lumanman.statepatternpractice;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginState implements State {
    public static final int LOGIN = 1;
    private Member currentMember;
    private Context context;


    interface LoginCallback {
        public void changeStatus();
    }

    LoginState.LoginCallback loginCallback;


    public LoginState(Context context, Member currentMember, LoginCallback loginCallback){
        this.context = context;
        this.currentMember = currentMember;
        this.loginCallback = loginCallback;
    }

    @Override
    public int getState() {
        return this.LOGIN;
    }

    @Override
    public void changePasswordClicked(ArrayList<Member> memberInfo) {
        View view = View.inflate(context, R.layout.changepw_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.setView(view)
                .setCancelable(true)
                .show( );

        EditText et_acc = view.findViewById(R.id.editText_acc);
        final EditText et_pw = view.findViewById(R.id.editText_pw);
        final EditText et_conformPw = view.findViewById(R.id.editText_confirmPw);
        Button confirmBtn = view.findViewById(R.id.button_confirm);
        Button cancelBtn = view.findViewById(R.id.button_cancel);

        et_acc.setEnabled(false);
        et_acc.setText(currentMember.getAccount());

        final ArrayList<Member> members = memberInfo;

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_pw.getText().toString().equals(et_conformPw.getText().toString())) {

                    for (Member member : members) {
                        if (member.isEqual(currentMember)) {
                            members.remove(member);
                            currentMember.changePassword(et_pw.getText().toString());
                            members.add(currentMember);
                            Toast.makeText(context, "Password changed", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            return;
                        }
                    }

                } else {
                    Toast.makeText(context, "Passwords not equal", Toast.LENGTH_SHORT).show();
                }


            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void loginClicked(String acc, String password, ArrayList<Member> members) {
        Toast.makeText(context, "Already logged in", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logoutClicked(State currentState) {
        loginCallback.changeStatus();
        Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show();
    }
}

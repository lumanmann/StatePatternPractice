package com.lumanman.statepatternpractice;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    Button loginBtn;
    Button logoutBtn;
    Button changePwBtn;
    EditText et_acc;
    EditText et_pw;

    ArrayList<Member> memberInfo;
    State currentState;
    LogoutState logoutState;
    AlertDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDefaultData();

        View view = View.inflate(MainActivity.this, R.layout.login_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        dialog = builder.setView(view).show( );

        loginBtn = view.findViewById(R.id.button_login);
        logoutBtn = view.findViewById(R.id.button_logout);
        changePwBtn = view.findViewById(R.id.button_changePw);
        et_acc = view.findViewById(R.id.editText_acc);
        et_pw = view.findViewById(R.id.editText_pw);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button_login:
                        currentState.loginClicked(et_acc.getText().toString(), et_pw.getText().toString(), memberInfo);
                        break;
                    case R.id.button_logout:
                        currentState.logoutClicked(currentState);
                        break;
                    case R.id.button_changePw:
                        currentState.changePasswordClicked(memberInfo);
                        break;

                }
                //Log.d("test ", currentState.getState() + "");
            }
        };

        loginBtn.setOnClickListener(onClickListener);
        logoutBtn.setOnClickListener(onClickListener);
        changePwBtn.setOnClickListener(onClickListener);

        logoutState = new LogoutState(new LogoutState.LogoutCallback() {
            @Override
            public void showToast(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void changeState(Member member) {
                switchState(member);
            }

        });


        currentState = logoutState;

    }

    private void getDefaultData()  {
        memberInfo = new ArrayList<Member>();
        memberInfo.add(new Member("test", "test"));
        memberInfo.add(new Member("test1", "test1"));
        memberInfo.add(new Member("test2", "test2"));
    }

    private void switchState(Member currentMember) {
        if (currentState.getState() == LoginState.LOGIN){
            currentState = logoutState;
        } else {
            currentState = new LoginState(MainActivity.this,currentMember, new LoginState.LoginCallback() {
                @Override
                public void changeStatus() {
                    switchState(null);
                }
            });


        }
    }
}

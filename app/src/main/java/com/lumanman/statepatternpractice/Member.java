package com.lumanman.statepatternpractice;

public class Member {
    private String account;
    private String password;

    public Member(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public void changePassword(String newpassword) {
        this.password = newpassword;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }
     public Boolean isEqual(Member other){
        if (other.getAccount().equals(this.account) && other.getPassword().equals(this.password)) {
            return true;
        }
        return false;
     }
}

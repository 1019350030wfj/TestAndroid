package com.jayden.testandroid.thirdlib.retrofit;

/**
 * Created by Jayden on 2017/3/23.
 */

public class Contributor {
    public final String login;
    public final int contributions;

    public Contributor(String login, int contributions) {
        this.login = login;
        this.contributions = contributions;
    }
}

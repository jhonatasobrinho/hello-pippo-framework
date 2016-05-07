package com.newgen;

import ro.pippo.core.Pippo;

/**
 * Created by newgen on 5/7/16.
 */
public class MainClass {
    public static void main(String[] args) {
        Pippo pippo = new Pippo(new BaseApplication());
        pippo.start();
    }
}

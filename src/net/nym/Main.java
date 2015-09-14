package net.nym;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) {
	// write your code here
        //-verbose -keystore demo.keystore -signedjar demo_signed.apk

        MyDisplay display = new MyDisplay();
        display.open();

    }
}

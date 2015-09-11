package net.nym;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) {
	// write your code here
        //-verbose -keystore demo.keystore -signedjar demo_signed.apk
//        File unSigned = new File("abc.apk");
//        File signed = new File("abc_signed.apk");
//        Signer.signedAndAligned(new File("lifecare"), "lifecare123", "shop", "shop123", unSigned);

        MyDisplay display = new MyDisplay();
        display.open();

    }
}

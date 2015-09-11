package net.nym;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) {
	// write your code here
        //-verbose -keystore demo.keystore -signedjar demo_signed.apk
        File unSigned = new File("abc.apk");
        File signed = new File("abc_signed.apk");
//        String[] params = {"-verbose","-keystore","lifecare","-storepass","lifecare123","-keypass","shop123",unSigned.getAbsolutePath(),"shop","-signedjar",signed.getAbsolutePath()};
//        String[] params = {"-help"};
        Signer.signedAndAligned(new File("lifecare"), "lifecare123", "shop", "shop123", unSigned);

//        try {
//            File align = new File("abc_signed_aligned.apk");
//            /**
//             * 签名之后，用zipalign(压缩对齐)优化你的APK文件
//             * */
//            Runtime.getRuntime().exec(String.format("E:/sdk/build-tools/22.0.1/zipalign -v 4 %s %s",signed.getAbsolutePath(),align.getAbsolutePath()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}

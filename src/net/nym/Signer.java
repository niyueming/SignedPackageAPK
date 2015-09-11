package net.nym;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/9/11 0011.
 */
public class Signer {

    /**
     * 签名你的apk文件后优化之
     * @param keystore 密钥库文件File
     * @param storepass 密钥库密码
     * @param key 别名
     * @param keypass 别名密码
     * @param unSigned 未签名文件File
     *
     * */
    public static String signedAndAligned(File keystore,String storepass,String key,String keypass,File unSigned){
        String signStr = signed(keystore,storepass,key,keypass,unSigned);
        if (signStr.contains("失败")){
            return signStr;
        }
        File signed = new File(signStr.substring(signStr.lastIndexOf(":") + 1));
        String alignStr = aligned(signed);
        return String.format("%s\n%s",signStr,alignStr);
    }


    /**
     * 签名你的apk文件后优化之
     * @param keystore 密钥库文件File
     * @param storepass 密钥库密码
     * @param key 别名
     * @param keypass 别名密码
     * @param unSigned 未签名文件File
     * @param signed 签名后文件File路径
     *
     * */
    public static String signedAndAligned(File keystore,String storepass,String key,String keypass,File unSigned,File signed){
        String signStr = signed(keystore,storepass,key,keypass,unSigned,signed);
        if (signStr.contains("失败")){
            return signStr;
        }
        String alignStr = aligned(signed);
        return String.format("%s\n%s",signStr,alignStr);
    }


    /**
     * 签名你的apk文件后优化之
     * @param keystore 密钥库文件File
     * @param storepass 密钥库密码
     * @param key 别名
     * @param keypass 别名密码
     * @param unSigned 未签名文件File
     * @param signed 签名后文件File路径
     * @param aligned 压缩对齐后文件File
     *
     * */
    public static String signedAndAligned(File keystore,String storepass,String key,String keypass,File unSigned,File signed,File aligned){
        String signStr = signed(keystore,storepass,key,keypass,unSigned,signed);
        if (signStr.contains("失败")){
            return signStr;
        }
        String alignStr = aligned(signed,aligned);
        return String.format("%s\n%s",signStr,alignStr);
    }

    /**
     * @param keystore 密钥库文件File
     * @param storepass 密钥库密码
     * @param key 别名
     * @param keypass 别名密码
     * @param unSigned 未签名文件File
     *
     * */
    public static String signed(File keystore,String storepass,String key,String keypass,File unSigned){
        File signed = null;
        if (unSigned.getAbsolutePath().lastIndexOf(".") != -1){
            signed = new File(unSigned.getParentFile(),unSigned.getName().substring(0,unSigned.getName().lastIndexOf(".")) + "_signed.apk");
        }else {
            signed = new File(unSigned.getAbsolutePath() + "_signed.apk");
        }
        return signed(keystore,storepass,key,keypass,unSigned,signed);

    }

    /**
     * 签名你的apk文件
     * @param keystore 密钥库文件File
     * @param storepass 密钥库密码
     * @param key 别名
     * @param keypass 别名密码
     * @param unSigned 未签名文件File
     * @param signed 签名后文件File路径
     *
     * */
    public static String signed(File keystore,String storepass,String key,String keypass,File unSigned,File signed){
        if (!keystore.exists()){
            throw new RuntimeException("file keystore is not exist");
        }

        if (StringUtils.isEmpty(storepass)){
            throw new NullPointerException("storepass is empty");
        }

        if (StringUtils.isEmpty(key)){
            throw new NullPointerException("key is empty");
        }

        if (StringUtils.isEmpty(keypass)){
            throw new NullPointerException("keypass is empty");
        }

        if (!unSigned.exists()){
            throw new RuntimeException("file unSigned is not exist");
        }


        String[] params = {"-verbose","-keystore",keystore.getAbsolutePath(),"-storepass",storepass,"-keypass",keypass,unSigned.getAbsolutePath(),key,"-signedjar",signed.getAbsolutePath()};
//        String[] params = {"-help"};
        try {
            /**
             * 签名
             * */
            sun.security.tools.jarsigner.Main.main(params);
            return String.format("%s签名成功，签名后文件路径:%s",unSigned.getName(),signed.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("%s签名失败", unSigned.getName());
    }

    /**
     * 用zipalign(压缩对齐)优化你的签名后APK文件
     * @param signed 签名后文件File路径(未签名的apk不可优化)
     *
     * */
    public static String aligned(File signed){
        File aligned = null;
        if (signed.getAbsolutePath().lastIndexOf(".") != -1){
            aligned = new File(signed.getParentFile(),signed.getName().substring(0,signed.getName().lastIndexOf(".")) + "_aligned.apk");
        }else {
            aligned = new File(signed.getAbsolutePath() + "_aligned.apk");
        }

        return aligned(signed,aligned);
    }

    /**
     * 用zipalign(压缩对齐)优化你的签名后APK文件
     * @param signed 签名后文件File路径(未签名的apk不可优化)
     * @param aligned 压缩对齐后文件File
     *
     * */
    public static String aligned(File signed,File aligned){
        if (!signed.exists()){
            throw new RuntimeException("file signed is not exist");
        }

//        Map<String,String> map = System.getenv();
//        Set<String> keys = map.keySet();
//        String path = null;
//        for ( Iterator it = keys.iterator(); it.hasNext(); )
//        {
//            String key = (String ) it.next();
//            if (key.equalsIgnoreCase("Path")){
//                path = map.get(key);
//                System.out.println(path + "");
//                break;
//            }
//        }
//
//        String[] paths = path.split(";");


        try {

            /**
             * 签名之后，用zipalign(压缩对齐)优化你的APK文件
             * */
            Runtime.getRuntime().exec(String.format("zipalign -v 4 %s %s", signed.getAbsolutePath(), aligned.getAbsolutePath()));
            return String.format("%s优化(zipalign)成功，优化后文件路径:%s",signed.getName(),aligned.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format("%s优化(zipalign)失败",signed.getName());
    }
}

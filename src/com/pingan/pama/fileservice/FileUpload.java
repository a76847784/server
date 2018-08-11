package com.pingan.pama.fileservice;

import com.alibaba.fastjson.JSONObject;
import com.pingan.pama.common.Security.AESTools;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

import javax.imageio.ImageIO;

/**
 * Created by ZHAOZENGJIE004 on 2018/6/7.
 * 外网文件上传demo
 */
public class FileUpload {

    //加签 私钥
    private static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCI1ExqVSDJvku2Bt19xk7In/svIHlOPwEDql8AVBUrNdcw8MNMoYU2iLz7bIST+Wlz73mVJvI2XzWyoHmAKE3jAG/PsVd3ziSiWmm81+RBWQaMM+/XnjcuOhyuTblVKk4yxGOy66TJpsT5Ewm4stmiI+52S+m9UlSkSfUCZaL5hjTgXqV27lV3rMw5/4UCvwj/yNAo+WXjw04G1fQtBHh6n/eLgubkuQOwM19TmVleqSjERWO4PmZ47zONGZpsYR3Lm2sYLdQEitICPuwBP1OcHhGn356TdpJG+PI8+KPHYFWKV10wUOpwy7IFhWFgxrmdiaTbAzQ/BrIpwfMPeYdVAgMBAAECggEAGASsJT4sMz2kLJ+n4Zwd2Dm87djVcla85tfRNU2NKbiZvY2Fur3flGMMSVYTL6ZoTGGNBhGAEutDkcd5jHWGtoItHlgS8oTNCXK1fOCowI/QBmxXfZHYPdUVk8Mw9TIn5+FxXsbedkc/7DjbTACZE00bXsiUjxcUpIIW8ATHRuVzy80YEyH5oUrGlgJ1bjJlq85TftkAz6HURN0kqUSKCNP4JjBRW02b03WhlQZjXeqr2eIgr4w+8NWsd/bUWNH5iQhtLMiYMkiAwF74A2kUVn/VKb5NAMPGpI5RT6LXKQmAz6c3+ozX/pokWAnQbS5JlGtB/UNxaBE3cQyRDF8zQQKBgQD2gK+nQdr08XXZZvmIUrc4SVc/HmhYVu3DK36HTFAEbk0nFnbC9G3tI/I0WSQjaVWtNv+g/1KbKTmf7idZpZ4Zdo8Ei6gw7RsArzWdhf575NfrGrKp9WZJkgc7fXHY8J0INhl3PbPPckLrbzTGObvI8cz8X2o7SQ3sGBQC43/vhwKBgQCOGeDA9i/u3yEN3dwTGqveSFXPabW70o+rdudp4v4VCpZxKnHNhBO93h0YOgNxrc2/GZnoyEysZmTQM7OrmWcahLcqndcPzQG7DUG7YTzA8CYz3kcnMoTnUdoVgMTzKhBYhNeKwnCK5TUJ1QVKh4+Yqo+nOZt+lFsN8NVpvfMxQwKBgHeW+SEJFyQsnQMrLTaRAqjBFB3gL7lj+xfr2wfi4xMzPCURhe6RTV92SARTlu/DI7jUbPJ7zGTQfE916AtbCTfLSBlpc+DAt4/+cOGKZ2tOG97I5hMEpmpc+TtIYdpg6Z5ZOMOb4Mds5MB6BD2DNvKHNO7fj5dRmvqGt/aqzp+zAoGAHXA9AJy/92OgTGge9JI88tLXfB2xOZT4kJQUgvo0mxVTb2RUqTfogxglvOQf3IgeLK3YFcKRJ9IapotfeHhSRMsjyx9h83MMHb00VeZiGLJnp60v3m333Jg3bub7ZkjblAsJQETh3dR4NW49J2CIHh+PnRwxG3sOlgfDnqFsZVMCgYEA3KNjKIMKU/XpcUd3kOcbq/tRxQCqmwivMssI+P187x2MWoIBVGxgRm1G6QiCK+gIE9FJZNDukp4udyouNTNzKquPtehlFF4/ZUL86LCbLu9NaZhJdNBinox3N/YETxzTgoeP80xdK+ilYoajf/9qOsD8Yxj1A2qve8wiNbjF6bc";
//    private static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAysB1J7/Irt7XK4A4sngU7/Yebdi1ZhWeu2k3haVfaA89WhYYPaWg8ouq9Ec2WHd8SNVpc9QePOJKFCJmLPHTuwPO1frc4zOolrSoQK59/+pUeu/VDmUzIYHEdkZEpqDvQ4XewyioD1AS6BLMcmajmJ8CJRWeuDrOnSfIO6tr0xVISZ5tOIcKRH/ID4Z+TWm5SBCljQ8AQrD9QPnoWnhcPcuHjgYh6ArfmZmCqV5rnU/nkApESju5YGIrRVL1SmqVn1IIGV+gxxQj2tlQle+TaNQIhNOJprLn8ivs7UyrHCokmJWPxUxCVPq7qpKtaZdaBMf73ha2/NxH1gO/oXqqGQIDAQAB";
    private static String aseKey = "wJpSgmjYvIysTHxlxVT9eQ==";//AES加密�?
    private static String channel ="8866";//创新大赛专用渠道
    private static String grantToChannel="8889";//授权渠道，不要改
    private static String url = "https://pama-fileservice.pa18.com/file-service-proxy/file";//文件服务地址


    public static void main(String[] args) throws Exception {
    	File file=new File("D:\\Users\\ANDONG557\\Desktop\\0.jpg");
    	System.out.println(file.getName());
    	BufferedImage image=ImageIO.read(file);
    	
    	ByteArrayOutputStream bao=new ByteArrayOutputStream();
    	
    	ImageIO.write(image,"jpg",bao);
    	
        uploadFile(bao.toByteArray(),file.getName());
    }

    /**
     * 上传文件返回fileid
     * @param fileContent
     * @param fileName
     * @return 
     * @throws Exception
     */
    public static String uploadFile(byte[] fileContent,String fileName) throws Exception {
        /**
         * 资源初始�?
         */
        CloseableHttpClient client = HttpClients.createDefault();

        /**
         * 文件上传
         */
        // 获取文件信息，这里假设获取一个文件信�?
        //byte[] fileContent = "这里是一个文本文件的内容".getBytes(); // 此处模拟已将文件读取到内�?
        //对文件信息进行加�?
        byte[] keyBytes = Base64.decodeBase64(aseKey);
        fileContent = AESTools.encrypt(fileContent,keyBytes);
        //String fileName = "filename.txt";

        // 对加密后的文件获取散列�??
        String sha1 = DigestUtils.sha1Hex(fileContent);

        //拼接参数
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("appid", channel);
        map.put("signType", "SHA1WithRSA");
        map.put("encryptType", "AES");
        map.put("method", "file.upload");
        map.put("version", "1.0");
        Date date = new Date();
        String timestamp = String.valueOf(date.getTime()) ;
        map.put("timestamp", timestamp);

        JSONObject bizContent = new JSONObject();
        bizContent.put("fileSHA1",sha1);
        //授权�?8889使用该文�?
        bizContent.put("grantTo",grantToChannel);
        //对bizContent进行加密
        String encrypt = AESTools.encrypt(bizContent.toJSONString(), aseKey);
        map.put("bizContent", encrypt);

        map.put("rsaVer","1.0");
        map.put("aesVer","1.0");

        //拼接加签字符�?
        List<String> pairs = new ArrayList<String>();
        for (Map.Entry entry : map.entrySet()) {
            String pair = String.format("%s=%s", entry.getKey(), entry.getValue());
            pairs.add(pair);
        }

        //加签
        String stringForSign = StringUtils.join(pairs, '&');
        String sign = sha1WithRSA(stringForSign, privateKey, "SHA1WithRSA");

        // 提交表单
        HttpPost uploadPost = new HttpPost(url);
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        meBuilder.addBinaryBody("file", fileContent, ContentType.DEFAULT_BINARY, fileName);
        meBuilder.addTextBody("appid", channel);
        meBuilder.addTextBody("signType", "SHA1WithRSA");
        meBuilder.addTextBody("encryptType", "AES");
        meBuilder.addTextBody("method", "file.upload");
        meBuilder.addTextBody("version", "1.0");
        meBuilder.addTextBody("timestamp", timestamp);
        meBuilder.addTextBody("bizContent", encrypt);
        meBuilder.addTextBody("rsaVer", "1.0");
        meBuilder.addTextBody("aesVer", "1.0");
        meBuilder.addTextBody("sign", sign);

        uploadPost.setEntity(meBuilder.build());
        CloseableHttpResponse uploadResp = client.execute(uploadPost);
        String jsonStr = IOUtils.toString(uploadResp.getEntity().getContent(), "UTF-8");
        System.out.println("上传文件返回报文：\n" + jsonStr);
        JSONObject object = JSONObject.parseObject(jsonStr);
        //报文解密
        String returnMsg = object.getString("bizContent");
        String decrypt = AESTools.decrypt(returnMsg, aseKey);

        System.out.println("---:"+decrypt);
        
        //获取文件id
        JSONObject fileIdObject = JSONObject.parseObject(decrypt);
		System.out.println(fileIdObject.get("fileId"));
        
        client.close();
        
        return fileIdObject.get("fileId").toString();
    }


    private static String sha1WithRSA(String str, String privateKeyStr, String signType) throws Exception {
        byte[] privateKey = Base64.decodeBase64(privateKeyStr);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(signType);
        signature.initSign(privateK);
        signature.update(str.getBytes("UTF-8"));
        return Base64.encodeBase64String(signature.sign());
    }
}

package com.pingan.pama.test.invoke.productCenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingan.pama.protocol.SignUtils;
import com.pingan.pama.protocol.encrypt.AESUtils;
import com.pingan.pama.test.invoke.util.AuthResultEnum;
import com.pingan.pama.test.invoke.util.InvokeUtil;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZHAOZENGJIE004 on 2018/4/17.
 */
public class IdentityAuthentication extends InvokeUtil {

    private static String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDJwL/ViPsIfq+Dn5tKnfWErRWBQeLdpUzc78NHZ/QNpp/IpbpLQ6VbwfyswBEaHiP9fWKGGkyVtnlYo6eQMyiUEarJHP29ukSzXuqCGJRhJKPRJ/fN/bWEXd2i+sKvSpsH79eZBbyrPr4VtM0cHLuGrpE92+HiMn4ifc5wAH648tXPhBDcLdQffOgZ9wOzmePJBIpzXintbv6iva1+yEQElgolDklBzJVa3C8ENyztFZoSE2yfs0YVPSd/BXad45vsL3lf2JX1oWk6IIuZlNQsQ/DNnG+jfdIAUW5TiWXdarPXL8IqD3aHX5hsH8StHFb/2YQgrFUDPFID2VxABHcLAgMBAAECggEAQn9ue1Jf84fWD9sukySHRQjLmsP9o7KVKAk64zFHRqyR48+EHMSaHylMQCA6QtKGaEnIjIzki0AOtQGT/DpweIVuNkWA+OUJOdniD6lMVsx91jWHPvUNvCg4IjzmqGI9wRrzg/NZIVRX0M28ATUTNiwZoTr1M9oSqwmuXko7enex4s8JqhCE8Nu6zFQVmvqy55qvG9oLpRT/akjBeKJKwm2TKcDRc1CAUKSpkk6sAK/MvhKas+VdPwT3v7f5yQmNYTIv3KaXhrIoKG4rk8WW1FM62fUwq5+C8roD77yScZfBZuRplM1V/bdUzhqqHErSPBmqXOm5L6rHGlx82D4hCQKBgQDjwteGjbqxPpm3//dInG15sWT4SSWagOnJulDeXf2uYWRe3Wa7XYG8402yXgJcHX3EJ2Ake/FRhFik9kXKfpMDqBcdP5SYytaQZnwZXQEu8iLnR/JQxJK+VKRo8QSf+XqiFVoD4qyqmggtKhEXmiYNNdUe7I9gTFeC0biRMtFUtwKBgQDixGfYMSax/YYrzIEx+2b8JObTC2HMAZokXDLJuytScaPHO7Vn8TwMNUXDx/fBC1+YmYPI0LDpVpfw/fYce//3JogCo4Z19s3xIHVoh/X+BJ6lpiL5BRnb6EuzvoCfd2zE0ptSlWFdctq8se2Vrhu4YuyCbAnTpQjH4cTuaD3kTQKBgQC3Jgrh7D5gIRLDX1WeUgvGe9vRvCA86cUVrnkxFyvTegWYMzlVGZAmMDJeaDJFmIF1pqQtMm2PO3l8zXy5pKbbneNYZbQ8WX/IhmDslFlLQvLGQJieUjQeUToUGRu6+Iagp8LGtRRb65j/mIA+FnrazJsgTuHMchc83yAnwlGKaQKBgBCp/jeU5RMPXly5moThxJ+i1pOLspQbSgiB/fl1F6nnT/HEIYCR6ae8AnRwrXQyMcQ1A8ouC9IZ2vCKuRs+wYCYBEatXaQtjtS5XedglmMkU8Q4gTlrdosCdw2uYiVV0VhlYaSf9Ze4aPqU2LC3KrJSEq9C4TX8WrjVjnM2ldXBAoGBAJMWK2JQF4aGWgKVeOU8+TV6jFSR4H/sRbBkMbEKDCZzLQaAT+W0DLOAL7S8jQhrN7qdYnejnOzFhErSmbznwfNZnkt66TTOMwE4wuT/m1X1lTOmmck14K8/7PKm6GLiwDxqs1J2GDh1e6dh2Pz+7rS5ZWmaacyatfjShnv1Zfc+";
    private static String eKey = "M1GAZvW+heTlGmrsq+5iKg==";
    private static String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlx2qSZJ1MfyooSDijmYbc+OM+mqZsg4AVl/VwhNtOpNHuh2gSO+kvC8SSMekeV9eVqbOPEw39I8rOqT3FrbwObIs6EdgQIrLcLc39ngdV7H342kApyKKS82YD/r4oAVAyoY+k2gqcIStAOC120A9oG/Ykud7as2LvpDM3gnXFWnoR7d+XYaFxSknASQOOuekoHQrQtDpILgRVxvGVyVmN6W5d6TFtG9QNjf7xIxIFIXa0iGD4yL/UPzbXPrER6W0+FRxmLcwLKwUXLn1xiOFjoFiCIMAuTsVr5Cq82gY8lLe5JwwSl/5MnXvfahHxvIeYMLth0/UNd+uNGHECHxCRQIDAQAB";
    private static String url = "https://pama-open.pingan.com/api";

    
    public static void main(String[] args) throws Exception {
    	new IdentityAuthentication().authQueryForMap("984287291259148880479445");
//    	new IdentityAuthentication().IdentityAuthByIdentityNoAndUserName("安栋","130682198910100653");
//    	new IdentityAuthentication().IdentityAuth("安栋","130682198910100653","d7084ba0041473e7fcf5d0389500b14fef6ca429","cb71c0c3048a74e188bc1834f5ffdaca73a65339");
    	
	}
    
    /**
     * 两项身份验证比对，返回channelBizNo，为查询使用
     * @return
     * @throws Exception
     */
    public String IdentityAuthByIdentityNoAndUserName(String userName,String identityNo) throws Exception {
        return IdentityAuth(userName,identityNo,null,null);
    }
    
    
    /**
     * 库照比对，返回channelBizNo，为查询使用
     * @return
     * @throws Exception
     */
    public String IdentityAuth(String userName,String identityNo,String fileId1)throws Exception {
    	return IdentityAuth(userName,identityNo,fileId1,null);
    }
    
   
    /**
     * 两照比对服务
     * @param userName
     * @param identityNo
     * @param fileId1
     * @param fileId2
     * @return
     * @throws Exception
     */
    public String IdentityAuth(String userName,String identityNo,String fileId1,String fileId2) throws Exception {
        String service = "pama_std_fep.identityAuth";

        Map<String, Object> param = new HashMap<String, Object>();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = format.format(date);
        String date2=format2.format(date);

        String channelBizNo = RandomStringUtils.random(24, "1234567890");
        param.put("channelBizNo", channelBizNo);
        param.put("channelDate", date1);
        
        //fileId1、fileId2不为空则表示两照对比。fileid1、fileid2同时为空表示两项认证，否则为三项认证
        if(StringUtils.isNotEmpty(fileId1)&&StringUtils.isNotEmpty(fileId2))
        	param.put("operationType", "R0200W05");
        else if(StringUtils.isNotEmpty(fileId1)&&StringUtils.isEmpty(fileId2))
        	param.put("operationType", "R0200W08");
        else
        	param.put("operationType", "R0200W07");
        
        //TODO 
        param.put("custName", userName);
        param.put("idType", "1");
        param.put("idNumber", identityNo);

        param.put("phone", "18385867406");

        param.put("terminalBrowser", "ie");
        param.put("terminalSDK", "o236");
        param.put("terminalType", "PC");
        param.put("terminalSystem", "pamastore");

        //add
        param.put("platId", "8889");
        
        if(StringUtils.isNotEmpty(fileId1)){
        	param.put("file_image1", fileId1);
        	param.put("image1Category", "1");
        	param.put("image1Mark", "0");
        	param.put("image1CrossMark", "0");
        	param.put("image1Type", "jpg");
        	param.put("landmarkTerminal1", "{\"landmark_terminal\":{\"face_rect_top_left\":{\"x\":0,\"y\":0},\"face_rect_width\":0,\"face_rect_height\":0,\"eye_left\":{\"x\":0,\"y\":0},\"eye_right\":{\"x\":0,\"y\":0},\"mouth_center\":{\"x\":0,\"y\":0}}}");
        	param.put("qualityTerminal1", "{\"quality_terminal\":{\"brightness\":0,\"blur_motion\":0,\"blur_gaussian\":0,\"deflection_h\":0,deflection_v\":0},\"width\":0,\"height\":0,\"}}");
        }
        
        if(StringUtils.isNotEmpty(fileId2)){
        	param.put("file_image2", fileId2);
        	param.put("image2Category", "2");
        	param.put("image2Mark", "0");
        	param.put("image2CrossMark", "0");
        	param.put("image2Type", "jpg");
        	param.put("landmarkTerminal2", "{\"landmark_terminal\":{\"face_rect_top_left\":{\"x\":0,\"y\":0},\"face_rect_width\":0,\"face_rect_height\":0,\"eye_left\":{\"x\":0,\"y\":0},\"eye_right\":{\"x\":0,\"y\":0},\"mouth_center\":{\"x\":0,\"y\":0}}}");
        	param.put("qualityTerminal2", "{\"quality_terminal\":{\"brightness\":0,\"blur_motion\":0,\"blur_gaussian\":0,\"deflection_h\":0,deflection_v\":0},\"width\":0,\"height\":0,\"}}");
        }
        
        param.put("entityAuthCode", "8889");
        param.put("entityAuthDate", date2);

        Map<String, Object> map = SignUtils.makeSignedMsgMapV2(param, "M070000008", "PRD21800000409", service, "1.0", eKey, "AES", "1.0", privateKey, "SHA256WithRSA");

        String res = sendJson(url, JSON.toJSONString(map));
        JSONObject obj = new JSONObject();
        HashMap params = obj.parseObject(res, HashMap.class);
        String resString = AESUtils.decrypt(params.get("bizContent").toString(), eKey);
        System.err.println(resString);
        
        return channelBizNo;
    }

    /**
     * 执行中状态会自动再查询，直到查到其它状态
     * @param channelBizNo
     * @return
     * @throws Exception
     */
    public static String authQueryFinalResult(IdentityAuthentication iauth,String channelBizNo) throws Exception {
    	
    	String authResult=iauth.authQuery(channelBizNo);
    	//如果是比对中，休息1s继续查询
    	while(AuthResultEnum.ZERO.getValue().equals(authResult)){
			Thread.sleep(1000);
			authResult=iauth.authQuery(channelBizNo);
		}
    	
    	return authResult;
    }
    /**
     * 比对结果是否一致
     * @param channelBizNo
     * @return
     * @throws Exception
     */
    public String authQuery(String channelBizNo) throws Exception {

        String service = "pama_pauth.identityAuthQuery";

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("channelSecond", "8889");
        param.put("channelBizNo", channelBizNo);

        Map<String, Object> map = SignUtils.makeSignedMsgMapV2(param, "M070000008", "PRD21800000409", service, "1.0", eKey, "AES", "1.0", privateKey, "SHA256WithRSA");

        String res = sendJson(url, JSON.toJSONString(map));

        JSONObject obj = new JSONObject();
        HashMap params = obj.parseObject(res, HashMap.class);
        String resString = AESUtils.decrypt(params.get("bizContent").toString(), eKey);
        JSONObject obj2=JSONObject.parseObject(resString);
        JSONObject obj3=JSONObject.parseObject(obj2.getString("bizContent"));
        System.err.println("------:"+resString);
        
        return obj3.get("authResult").toString();
    }
    
    /**
     * 返回真正的结果
     * @param iauth
     * @param channelBizNo
     * @return
     * @throws Exception
     */
    public static String authQueryForMapFinalResult(IdentityAuthentication iauth,String channelBizNo) throws Exception {
    	Map<String,String> rsMap=iauth.authQueryForMap(channelBizNo);
    	String authResult=rsMap.get("authResult");
    	//如果是比对中，休息1s继续查询
    	while(AuthResultEnum.ZERO.getValue().equals(authResult)){
			Thread.sleep(1000);
			rsMap=iauth.authQueryForMap(channelBizNo);
			authResult=rsMap.get("authResult");
		}
    	
    	return rsMap.get("contrastSimilarity")==null?"0":rsMap.get("contrastSimilarity");
    }
    
    /**
     * 比对结果是否一致,返回结果包含相似度
     * @param channelBizNo
     * @return
     * @throws Exception
     */
    public Map<String,String> authQueryForMap(String channelBizNo) throws Exception {

        String service = "pama_pauth.identityAuthQuery";

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("channelSecond", "8889");
        param.put("channelBizNo", channelBizNo);

        Map<String, Object> map = SignUtils.makeSignedMsgMapV2(param, "M070000008", "PRD21800000409", service, "1.0", eKey, "AES", "1.0", privateKey, "SHA256WithRSA");

        String res = sendJson(url, JSON.toJSONString(map));

        JSONObject obj = new JSONObject();
        HashMap params = obj.parseObject(res, HashMap.class);
        String resString = AESUtils.decrypt(params.get("bizContent").toString(), eKey);
        JSONObject obj2=JSONObject.parseObject(resString);
        JSONObject obj3=JSONObject.parseObject(obj2.getString("bizContent"));
        System.err.println("------:"+resString);
        
        Map<String,String> resultMap=new HashMap<>();
        resultMap.put("authResult", obj3.get("authResult").toString());
        if(obj3.get("contrastSimilarity")!=null)
        	resultMap.put("contrastSimilarity",obj3.get("contrastSimilarity").toString());
        
        System.out.println("*******"+resultMap);
        
        return resultMap;
    }
}

package com.qa.inHouse.Rough;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tmobile.secureid.qa.data.SecureIdData;

public class SecureIdRequestBuilder {

	public enum DAT_REQUESTTYPE {
		DAT_TOKEN_V4, DAT_TOKEN_V3, DEVICEObject, CARRIER_TOKEN_INFO, TOKEN1, TOKEN2, DEVICE_ATTRIBUTESObject, IAMObject
	}

	public enum FP_REQUESTTYPE {
		FORGOTPASSWORD_INIT_SESSION, URLPARAMETERS, FORGOTPASSWORD_VALIDATESECURITY_ANSWERS, ANSWER1, ANSWER2, SUBTOKEN;
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	public static JSONObject getDATRequestBody(DAT_REQUESTTYPE DAT_REQUESTTYPE, SecureIdData secureIdData) {
		JSONObject jsonObject = new JSONObject();
		switch (DAT_REQUESTTYPE) {
		case DAT_TOKEN_V3:
			jsonObject.put("device", getDATRequestBody(DAT_REQUESTTYPE.DEVICEObject, secureIdData));
			jsonObject.put("Device_Attributes",
					getDATRequestBody(DAT_REQUESTTYPE.DEVICE_ATTRIBUTESObject, secureIdData));
			jsonObject.put("trans_id", "TestiOSProduction2020-06-10 16:06");
			jsonObject.put("isPushNotificationsEnabled", "true");
			jsonObject.put("iam", getDATRequestBody(DAT_REQUESTTYPE.IAMObject, secureIdData));
			jsonObject.put("carrier_token_info",
					"AHmeFRW3oA3uu+/oZNH3hn+mohIUceQ7fxoOTJgq+1E5GDUAr2Zq5/LAcYA+5/MSkTy7ckKRjcWq+SaGhbulrv1zuEdh21rIcXtnjXD4ONAt0gWRDKnZs3t/IK/bmv8nO99ITkav7oqUTKRp9EB7S4Gg6SuT8BzGL48meqi808b7cIaKAHHJKnSlty3wBRKX/X/mSpTt2Qut0WAm2TGx8ry0aFdMrW9EQmLC431lNnFe3dOeBh49uolpLhxv6P/wV++idnIiUK02vLnDR1jbpzB3zacf1mgX2ZwEuVapgGZn");
			break;
		case DAT_TOKEN_V4: // main
			jsonObject.put("device", getDATRequestBody(DAT_REQUESTTYPE.DEVICEObject, secureIdData));

			List<HashMap<String, JSONObject>> jsonArrayListCARRIER_TOKEN_INFO = new ArrayList<HashMap<String, JSONObject>>();
			jsonArrayListCARRIER_TOKEN_INFO.add(getDATRequestBody(DAT_REQUESTTYPE.TOKEN1, secureIdData));
			jsonArrayListCARRIER_TOKEN_INFO.add(getDATRequestBody(DAT_REQUESTTYPE.TOKEN2, secureIdData));
			jsonObject.put("carrier_token_info", jsonArrayListCARRIER_TOKEN_INFO);

			jsonObject.put("Device_Attributes",
					getDATRequestBody(DAT_REQUESTTYPE.DEVICE_ATTRIBUTESObject, secureIdData));
			jsonObject.put("trans_id", "TestiOSProduction2020-06-10 16:06");
			jsonObject.put("iam", getDATRequestBody(DAT_REQUESTTYPE.IAMObject, secureIdData));
			jsonObject.put("isPushNotificationsEnabled", "true");
			break;
		case DEVICEObject:
			jsonObject.put("sdk_version", "5.1.1");
			jsonObject.put("app_version", "7.3.1");
			jsonObject.put("model", "iPhone 7 Plus");
			jsonObject.put("device_id", "E0B10BB5-0168-497A-A117-ADE48980EB76");
			jsonObject.put("os", "iOS");
			jsonObject.put("os_version", "13.4.1");
			break;
		case TOKEN1:
			List<HashMap<String, String>> token = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> subTokenPair0 = new HashMap<String, String>();
			subTokenPair0.put("name", "id");
			subTokenPair0.put("value", "1");
			token.add(0, subTokenPair0);
			HashMap<String, String> subTokenPair1 = new HashMap<String, String>();
			subTokenPair1.put("name", "carrier_token");
			subTokenPair1.put("value",
					"APSmcgcCKhpxy3p044N47aBxqywcYkm4uY05GwBwRVfYrBEkP4eEDNLnUNhO7Y15UjSsMQzRiGzriPiSCM5BA8DKPxzK919ub3zcxJdQ6+tXcdIlfYAk+JJGJWVwmW6mE+nU2DagWk6PK09tBwu/v/+Vv5U/a68hmWD5uuzUPFI/EMZa0wqWmKqNvB0qENEN7EcOoAMSvKys/jc2YBFsfRjiooSbP4jNteNo3jTCt/lhX0BTLkAEXJf4ztumTDww/6XlfEhxluYyLrPiS6mlSRt2h7BABLUSBzJglMGNMvcZ");
			token.add(1, subTokenPair1);
			HashMap<String, String> subTokenPair2 = new HashMap<String, String>();
			subTokenPair2.put("name", "sim_type");
			subTokenPair2.put("value", "SIM");
			token.add(2, subTokenPair2);
			HashMap<String, String> subTokenPair3 = new HashMap<String, String>();
			subTokenPair3.put("name", "carrier");
			subTokenPair3.put("value", "TMobile");
			token.add(3, subTokenPair3);
			jsonObject.put("token", token);
			break;
		case TOKEN2:
			List<HashMap<String, String>> token2 = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> subTokenPair01 = new HashMap<String, String>();
			subTokenPair01.put("name", "id");
			subTokenPair01.put("value", "1");
			token2.add(0, subTokenPair01);
			HashMap<String, String> subTokenPair11 = new HashMap<String, String>();
			subTokenPair11.put("name", "carrier_token");
			subTokenPair11.put("value",
					"APSmcgcCKhpxy3p044N47aBxqywcYkm4uY05GwBwRVfYrBEkP4eEDNLnUNhO7Y15UjSsMQzRiGzriPiSCM5BA8DKPxzK919ub3zcxJdQ6+tXcdIlfYAk+JJGJWVwmW6mE+nU2DagWk6PK09tBwu/v/+Vv5U/a68hmWD5uuzUPFI/EMZa0wqWmKqNvB0qENEN7EcOoAMSvKys/jc2YBFsfRjiooSbP4jNteNo3jTCt/lhX0BTLkAEXJf4ztumTDww/6XlfEhxluYyLrPiS6mlSRt2h7BABLUSBzJglMGNMvcZ");
			token2.add(1, subTokenPair11);
			HashMap<String, String> subTokenPair21 = new HashMap<String, String>();
			subTokenPair21.put("name", "sim_type");
			subTokenPair21.put("value", "SIM");
			token2.add(2, subTokenPair21);
			HashMap<String, String> subTokenPair31 = new HashMap<String, String>();
			subTokenPair31.put("name", "carrier");
			subTokenPair31.put("value", "TMobile");
			token2.add(3, subTokenPair31);
			jsonObject.put("token", token2);
			break;
		case DEVICE_ATTRIBUTESObject:
			jsonObject.put("Pop_Signing_Key",
					"-----BEGIN PUBLIC KEY-----\\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAok5XFkWrJLqfRf2R2QLc\\nF9+PVza2tzoWQEjZfNb/K/3KnGYRmYT23QRqfTQpRCJq0sO6n3P881aRlJkltrcd\\nW1/OZUUPMldNfXGwfyy7clTJFN8SHysctvYEXxqgU+B0eqyf0kamfuPh8BRgYolE\\nOUd0pTmdQ8BDZCqQEFa8DdDcmQyyOHA5ESu52xi5ClpyxfTMkkQRfkiJDAsy+5tC\\ncSPu7zpYkoxR1Y7xIa4iR3tpwqVlHxljQUEC1JcM2IkjjVnOjXhY2jHImA3qkyDy\\nkajyXESJiGykxACHZowDxtoz4iKOvp5MDiwNkY03KuXQANV9ucihOlygUWOlDEVO\\n3wIDAQAB\\n-----END PUBLIC KEY-----\\n");
			jsonObject.put("uuid", "E0B10BB5-0168-497A-A117-ADE48980EB76");
			break;
		case IAMObject:
			jsonObject.put("client_id", "TMOApp");
			break;
		default:
			break;
		}
		return jsonObject;
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	public static JSONObject getForgotPasswordRequest(FP_REQUESTTYPE FP_REQUESTTYPE, SecureIdData secureIdData) {
		JSONObject jsonObject = new JSONObject();
		switch (FP_REQUESTTYPE) {
		case URLPARAMETERS:
			jsonObject.put("redirect_uri", "https://e6.my.t-mobile.com");
			jsonObject.put("scope", "TMO_ID_profile associated_lines billing_information extended_lines");
			jsonObject.put("client_id", "MYTMO");
			jsonObject.put("access_type", "ONLINE");
			jsonObject.put("response_type", "code");
			jsonObject.put("approval_prompt", "auto");
			jsonObject.put("device_type", "desktop");
			jsonObject.put("prompt", "select_account");
			jsonObject.put("login_hint", "2068290185");
			break;
		case FORGOTPASSWORD_INIT_SESSION:
			jsonObject.put("urlParameters", getForgotPasswordRequest(FP_REQUESTTYPE.URLPARAMETERS, secureIdData));
			jsonObject.put("transId", "1591264511456_rjmjm_7032114298");
		case FORGOTPASSWORD_VALIDATESECURITY_ANSWERS:
			List<JSONObject> SECURITYANSWERS = new ArrayList<JSONObject>();
			SECURITYANSWERS.add(getForgotPasswordRequest(FP_REQUESTTYPE.ANSWER1, secureIdData));
			SECURITYANSWERS.add(getForgotPasswordRequest(FP_REQUESTTYPE.ANSWER2, secureIdData));
			jsonObject.put("securityAnswers", SECURITYANSWERS);
			jsonObject.put("transId", secureIdData.getZipCode());
			jsonObject.put("iam", getDATRequestBody(DAT_REQUESTTYPE.IAMObject, secureIdData));
			break;
		case ANSWER1:
			jsonObject.put("answer", secureIdData.getSecurityQuestionAns1());
			break;
		case ANSWER2:
			jsonObject.put("answer", secureIdData.getSecurityQuestionAns2());
			break;
		default:
			break;
		}
		return jsonObject;
	}
//@Test(sec)
	public static void main(String[] args) {
		SecureIdData s = new SecureIdData();
		s.setSecurityQuestionAns1("ans2");
		s.setSecurityQuestionAns2("ans4");
		s.setZipCode("6986686");
		String outPut = getForgotPasswordRequest(FP_REQUESTTYPE.FORGOTPASSWORD_VALIDATESECURITY_ANSWERS, s)
				.toString();

		/*
		 * PrettyPrinting
		 */
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(outPut);
		String prettyJsonString = gson.toJson(je);
		System.out.println(prettyJsonString);
	}
}

package com.bridgelabz.socialLogin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FBConnection {
	public static String fb_APP_ID = "147887982603303";
	public static String fb_APP_SECRETE = "8aac06c34542eb49d11ec159f3a64080";
	public static String redirectURI = "http://localhost:8080/TODOAPP/getFacebookLogin";
	public static String user_Access_URL = "https://graph.facebook.com/v2.9/me?access_token=";
	public static String binding = "&fields=id,name,email,first_name,last_name,picture";

	public static String getFBURL() {
		String fbLoginUrl = "";

		try {
			fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id=" + fb_APP_ID + "&redirect_uri="
					+ URLEncoder.encode(redirectURI) + "&scope=public_profile,email";
			System.out.println("fbLoginUrl: " + fbLoginUrl);
			return fbLoginUrl;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return fbLoginUrl;
	}

	public static String getFBAcessToken(String code) {

		String fburl = "client_id=" + fb_APP_ID + "&redirect_uri=" + URLEncoder.encode(redirectURI) + "&client_secret="
				+ fb_APP_SECRETE + "&code=" + code;

		try {
			URL url = new URL("https://graph.facebook.com/v2.10/oauth/access_token?" + fburl);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
			writer.write(fburl);
			System.out.println("fbUrl " + fburl);
			writer.flush();

			String fbResponse = "";
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				fbResponse = fbResponse + line;
			}

			ObjectMapper objectMapper = new ObjectMapper();
			String fbToken = objectMapper.readTree(fbResponse).get("access_token").asText();
			System.out.println("fbToken: " + fbToken);
			return fbToken;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fburl;
	}

	public static String getFbProfileInfo(String fbAccessToken) {

		try {
			URL fbURL = new URL(user_Access_URL + fbAccessToken + binding);
			URLConnection urlConnection = fbURL.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String fbProfileInfo = "";
			String info;
			while ((info = bufferedReader.readLine()) != null) {
				fbProfileInfo = fbProfileInfo + info;
			}
			return fbProfileInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

package com.bridgelabz.utility;

import java.util.Calendar;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenGenerator {
	private static final String key = "secreteKey";

	public static String generateToken(int id) {
		Calendar calendar = Calendar.getInstance();

		Date issuerTime = calendar.getTime();
		calendar.add(Calendar.MINUTE, 30);
		Date expirationtime = calendar.getTime();

		String token = Jwts.builder().setId(String.valueOf(id)).setIssuedAt(issuerTime)
				.compressWith(CompressionCodecs.DEFLATE).setExpiration(expirationtime)
				.signWith(SignatureAlgorithm.HS512, key).compact();
		System.out.println(token);
		return token;
	}

	public static int verfiyToken(String jwtToken) {
		try {
			System.out.println(jwtToken);
			Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken).getBody();
			return Integer.parseInt(claims.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}

package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {
	
	//넘겨받은 숫자가 1자리수이면, 앞에 0 붙이기
	//누군가가 이 메서드를 호출하면, 처리결과를 반환받는다.
	public static String getNumString(int num) {
		//static 메서드이므로 선언하지 않고 클래스.메서드 사용
		String str=null;
		if(num<10) {//한자리수
			str="0"+num; //05
		}else {//두자리수
			str=Integer.toString(num);
		}
		return str;
	}
	
	
	//확장자 추출하여 반환받기
	static public String getExtend(String filename) {
		int lastIndex = filename.lastIndexOf(".");
		System.out.println(lastIndex);
		return filename.substring(lastIndex/*+1, filename.length()*/);
	}
	
	
	//비밀번호 암호화
	//자바의 보안과 관련된 기능을 지원하는 api모여있는 패키지가
	//java.security 이다.
	public static String getConvertedPassword(String pass) {
		//암호화 객체
		StringBuffer hexString=null;
		try {
			MessageDigest digest=MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(pass.getBytes("UTF-8"));
			
			//String은 불변이다!! 따라서 그 값이 변경될 수 없다
			//따라서 String 객체는 반복문 횟수가 클 때는 절대
			//누적식을 사용해서는 안된다!!
			//해결책) 변경가능한 문자열 객체를 지원하는 StringBuffer,
			//StringBuilder 등을 활용하자
			
			hexString=new StringBuffer(); 
			for(int i=0;i<hash.length;i++) {
				String hex=Integer.toHexString(0xff & hash[i]);
				//System.out.println(hex);
				if(hex.length()==1) {
					hexString.append("0");
				}
				hexString.append(hex);
			}
			//System.out.println(hexString.toString());
			//System.out.println(hexString.length());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexString.toString();
	}
	/*
	 * //주석 = 드래그 후 (ctrl + shift + /) 
	 * public static void main(String[] args) {
	 * String result = getConvertedPassword("minzino");
	 * System.out.println(result.length()); }
	 */
}

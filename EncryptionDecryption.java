package 정보보호공학;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionDecryption {
	private static IvParameterSpec iv;
	
	 public static void main(String[] args) throws NoSuchAlgorithmException,BadPaddingException
     , NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException 
     , UnsupportedEncodingException, InvalidAlgorithmParameterException {
		 
		 Scanner sc=new Scanner(System.in);
		 String algo, mode, text, key;
		 
		 System.out.println("문자를 입력하세요 : ");
		 text=sc.next();	//암호화할 문자 입력
		 System.out.println("키를 입력하세요 : ");
		 key=sc.next();		//키 입력
		 System.out.println("알고리즘을 선택하세요 : ");
		 algo=sc.next();	//사용할 알고리즘 입력
		 System.out.println("모드를 선택하세요 : ");
		 mode=sc.next();	//사용할 동작 모드 입력
		 
		 //Cipher 객체 생성
		 Cipher cipher = Cipher.getInstance(algo+"/"+mode+"/"+"PKCS5Padding");
		 
		 //암호화 Cipher 초기화
		 SecretKeySpec secretKeySpec=new SecretKeySpec(key.getBytes(), algo);
		 
		 //////// 암호화 ////////
		 if(!mode.equals("ECB")) {  //ECB는 아니고 AES이면 초기화 길이 8byte
			 if(!algo.equals("AES")) {	
				 iv=new IvParameterSpec(new String("ZzangJin").getBytes());
				 cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec,iv);
			 }
			 else {		//AES가 아니고 ECB도 아니면 초기화 길이 16byte
				 iv=new IvParameterSpec(new String("BanYujinZzangVvv").getBytes());
				 cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec,iv);
			 }
		 }
		 else {		//ECB이면 초기화 벡터 필요X
			 cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		 }
		 
		 //암호화된 바이트 문자로 바꾸기
		 byte[] BytesEncrypt=cipher.doFinal(text.getBytes("UTF-8"));
		 String code=new String(BytesEncrypt);
		 
		 //////// 복호화 ////////
		 if(!mode.contentEquals("ECB")) {	//ECB가 아니면 초기화 벡터 필요
			 cipher.init(cipher.DECRYPT_MODE, secretKeySpec,iv);
		 }
		 else {	//ECB가 아니면 초기화 벡터 필요X
			 cipher.init(cipher.DECRYPT_MODE, secretKeySpec);
		 }
		 //복호화된 바이트 문자로 바꾸기
		 byte[] BytesDecrypt=cipher.doFinal(BytesEncrypt);
		 String DecrypText=new String(BytesDecrypt,"UTF-8");
		 
		 System.out.println("평문 : "+text);
		 System.out.println("암호화된 문자 : "+code);
		 System.out.println("복호화된 문자 : "+DecrypText);
	 }
}
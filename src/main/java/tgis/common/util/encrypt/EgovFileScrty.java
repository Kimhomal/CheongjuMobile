/**
 *  Class Name : EgovFileScrty.java
 *  Description : Base64인코딩/디코딩 방식을 이용한 데이터를 암호화/복호화하는 Business Interface class
 *  Modification Information
 *
 *     수정일         수정자                   수정내용
 *   -------    --------    ---------------------------
 *   2009.02.04    박지욱          최초 생성
 *
 *  @author 공통 서비스 개발팀 박지욱
 *  @since 2009. 02. 04
 *  @version 1.0
 *  @see
 *
 *  Copyright (C) 2009 by MOPAS  All right reserved.
 */
package tgis.common.util.encrypt;

import java.util.Map;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.io.*;
import java.security.*;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EgovFileScrty {

	// 파일구분자
    static final char FILE_SEPARATOR     = File.separatorChar;

	/**
	 * 파일을 암호화하는 기능
	 * @param String source 암호화할 파일
	 * @param String target 암호화된 파일
	 * @return boolean result 암호화여부 True/False
	 * @exception Exception
	*/
	public static boolean encryptFile(String source, String target) throws Exception {

		// 암호화 여부
		boolean result = false;

		String source1 = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		String target1 = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcFile = new File(source1);
		BufferedReader tempbr = null;
		BufferedReader br = null;
		try {
			if (srcFile.exists() && srcFile.isFile()) {

				// 1. 암호화 타겟 파일을 생성 (기존에 파일이 존재하면 지우고 쓰도록 하기위해 append = false)
				boolean append = false;
				File tarFile = new File(target1);
				if (!tarFile.exists()) {
					tarFile.createNewFile();
					append = true;
				}

				// 2. 총 라인수를 구한다
				tempbr = new BufferedReader(new InputStreamReader(new FileInputStream(source1)));
				String templine = "";
				int linecnt = 0;
		        while ((templine = tempbr.readLine()) != null) {
		        	linecnt++;
		        }

				// 파일을 한라인씩 읽어서 암호화하여 데이터를 만들어 암호화 파일에 Write 한다
				br = new BufferedReader(new InputStreamReader(new FileInputStream(source1)));
				String line = "";
				int cnt = 0;
		        while ((line = br.readLine()) != null) {
		        	cnt++;
		        	if (cnt > 1) append = true;
		        	String endata = encode(line);
		        	endata = endata.replace('\n', ' ').replace('\r', ' ');
		        	BufferedWriter  bw = new BufferedWriter(new FileWriter(target1, append));
		        	bw.write(endata);
		        	if (cnt < linecnt) {
		        		bw.newLine();
		        	}
		        	bw.flush();
		        	bw.close();
		        }
		        result = true;
		        br.close();

			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (tempbr != null) tempbr.close();
			if (br != null) br.close();
		}
		return result;
	}

	/**
	 * 파일을 복호화하는 기능
	 * @param String source 복호화할 파일
	 * @param String target 복호화된 파일
	 * @return boolean result 복호화여부 True/False
	 * @exception Exception
	*/
	public static boolean decryptFile(String source, String target) throws Exception {

		// 복호화 여부
		boolean result = false;

		String source1 = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		String target1 = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcFile = new File(source1);
		BufferedReader tempbr = null;
		BufferedReader br = null;
		try {
			if (srcFile.exists() && srcFile.isFile()) {

				// 1. 복호화 타겟 파일을 생성 (기존에 파일이 존재하면 지우고 쓰도록 하기위해 append = false)
				boolean append = false;
				File tarFile = new File(target1);
				if (!tarFile.exists()) {
					tarFile.createNewFile();
					append = true;
				}

				// 2. 총 라인수를 구한다
				tempbr = new BufferedReader(new InputStreamReader(new FileInputStream(source1)));
				String templine = "";
				int linecnt = 0;
		        while ((templine = tempbr.readLine()) != null) {
		        		linecnt++;
		        }
		        tempbr.close();

		        // 파일을 한라인씩 읽어서 복호화하여 데이터를 만들어 복호화 파일에 Write 한다.
				br = new BufferedReader(new InputStreamReader(new FileInputStream(source1)));
				String line = "";
				int cnt = 0;
		        while ((line = br.readLine()) != null) {
		        	cnt++;
		        	if (cnt > 1) append = true;
		        	line = line.replaceAll(" ", "");
		        	String dedata = decode(line);
		        	BufferedWriter  bw = new BufferedWriter(new FileWriter(target1, append));
		        	bw.write(dedata);
		        	if (cnt < linecnt) {
		        		bw.newLine();
		        	}
		        	bw.flush();
		        	bw.close();

		        }
		        result = true;
		        br.close();

			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (tempbr != null) tempbr.close();
			if (br != null) br.close();
		}
		return result;
	}

	/**
	 * 데이터를 암호화하는 기능
	 * @param String data 암호화할 데이터
	 * @return String result 암호화된 데이터
	 * @exception Exception
	*/
	public static String encode(String data) throws Exception {

		// 암호화 데이터
		String result = "";

		if(data == null) {
            return "";
		}

		BASE64Encoder encode = new BASE64Encoder();
		byte [] b = data.getBytes();
		result = encode.encode(b);

		return result;
	}

	/**
	 * 데이터를 복호화하는 기능
	 * @param String data 복호화할 데이터
	 * @return String result 복호화된 데이터
	 * @exception Exception
	*/
	public static String decode(String data) throws Exception {

		// 복호화 데이터
		String result = "";

		if(data == null) {
            return "";
		}

		try {
			BASE64Decoder decode = new BASE64Decoder();
			byte [] b = decode.decodeBuffer(data);
			result = new String(b);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 비밀번호를 암호화하는 기능(복호화가 되면 안되므로 MD5 인코딩 방식 적용)
	 * @param String data 암호화할 비밀번호
	 * @return String result 암호화된 비밀번호
	 * @exception Exception
	*/
/*
	public static String encryptPassword(String data) throws Exception {

		if(data == null) {
            return "";
		}

		byte[] plainText = null; // 평문
		byte[] hashValue = null; // 해쉬값
		plainText = data.getBytes();
		MessageDigest md = MessageDigest.getInstance("MD5");
		//MessageDigest md = MessageDigest.getInstance("SHA-256");
		hashValue = md.digest(plainText);

		StringBuffer sb = new StringBuffer();
		for (byte b : hashValue) {
			sb.append(Integer.toHexString(b & 0xff));
		}
		return sb.toString();
	}
*/
	public static String encryptPassword(String data) throws Exception {

		if(data == null) {
            return "";
		}

		byte[] plainText = null; // 평문
		byte[] hashValue = null; // 해쉬값
		plainText = data.getBytes();

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		hashValue = md.digest(plainText);

        BASE64Encoder encoder = new BASE64Encoder();

		return encoder.encode(hashValue);

	}

	@RequestMapping(value="/checkInputPW.do")
	public void checkInputPW(Map<String, Object> commandMap, HttpServletResponse response) throws Exception {
		String strInput = (String)commandMap.get("inputpw");
    	String strPW = (String)commandMap.get("pw");
    	//System.out.println("strPW:"+strPW);
    	//System.out.println("strInput:"+strInput);
		String strBool = "false";
		if(strInput != null && strPW != null) {
			strInput = encryptPassword(strInput);
			//System.out.println("strEncryptInput:"+strInput);
			if(strInput.equals(strPW))
				strBool = "true";
		}

		response.setCharacterEncoding("utf-8");
		response.getWriter().println(strBool);
	}
}
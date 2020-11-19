package tgis.common.util.com;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.cmm.service.EgovProperties;

/**
 * 파일 관리를 위한 유틸
 *
 * @author khb
 * @since 2016.03.25
 * @version 1.0
 * @see <pre>
 *  == 개정이력(Modification Information) ==
 *
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *         2016.05.03        khb          최초 생성
 *
 * </pre>
 */
public class FileUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

	private final static String UPLOAD_PATH = EgovProperties.getProperty("Globals.fileStorePath");


	 /**
	* 압축 메소드
	* @param path 경로
	* @param outputFileName 출력파일명
	*/
    public static void compress(String path,String outputFileName) throws Throwable{
	   File file = new File(path);
	   int pos = outputFileName.lastIndexOf(".");
	   if(!outputFileName.substring(pos).equalsIgnoreCase(".zip")){
		  outputFileName += ".zip";
	   }
	   // 압축 경로 체크
	   if(!file.exists()){
		  throw new Exception("Not File!");
	   }
	   // 출력 스트림
	   FileOutputStream fos = null;
	   // 압축 스트림
	   ZipOutputStream zos = null;
	   try{
		  fos = new FileOutputStream(new File(outputFileName));
		  zos = new ZipOutputStream(fos);
		  // 디렉토리 검색
		  searchDirectory(file,zos);
	   }catch(Throwable e){
		  throw e;
	   }finally{
		  if(zos != null) zos.close();
		  if(fos != null) fos.close();
	   }
    }
    /**
	* 다형성
	*/
    private static void searchDirectory(File file, ZipOutputStream zos) throws Throwable{
	   searchDirectory(file,file.getPath(),zos);
    }
    /**
	* 디렉토리 탐색
	* @param file 현재 파일
	* @param root 루트 경로
	* @param zos 압축 스트림
	*/
    private static void searchDirectory(File file,String root,ZipOutputStream zos)
		  throws Exception{
	   //지정된 파일이 디렉토리인지 파일인지 검색
	   if(file.isDirectory()){
		  //디렉토리일 경우 재탐색(재귀)
		  File[] files = file.listFiles();
		  for(File f : files){
			 searchDirectory(f,root,zos);
		  }
	   }else{
		  //파일일 경우 압축을 한다.
		  compressZip(file,root,zos);
	   }
    }
    /**
	* 압축 메소드
	* @param file
	* @param root
	* @param zos
	* @throws Exception
	*/
    private static void compressZip(File file, String root, ZipOutputStream zos) throws Exception{
	   FileInputStream fis = null;
	   try{
//		  String zipFileNam =  root.substring(root.lastIndexOf("\\\\") + 1);
//		  String zipName = file.getPath().replace(root+"\\", "");
		  // 파일을 읽어드림
		  fis = new FileInputStream(file);
		  // Zip엔트리 생성(한글 깨짐 버그)
		  ZipEntry zipentry = new ZipEntry(file.getName());
		  // 스트림에 밀어넣기(자동 오픈)
		  zos.putNextEntry(zipentry);
		  int length = (int)file.length();
		  byte[] buffer = new byte[length];
		  //스트림 읽어드리기
		  fis.read(buffer, 0, length);
		  //스트림 작성
		  zos.write(buffer, 0, length);
		  //스트림 닫기
		  zos.closeEntry();

	   }catch(Throwable e){
		  throw e;
	   }finally{
		  if(fis != null) fis.close();
	   }
    }

	/**
	 * 파일 확장자 반환
	 *
	 * @param fileName
	 * @return
	 */
	public static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * 임시 파일 생성
	 *
	 * @param path
	 * @param ext
	 * @return
	 */
	public static File makeTempFile(String path) {
		File dir = new File(ComStringUtils.filterSystemPath(path));
		if (!dir.exists()) {
			dir.setExecutable(false, true);
			dir.setReadable(true);
			dir.setWritable(true);
			dir.mkdirs();
		}
		return dir;
	}

	/**
	 * 파일 객체로 디렉토리 삭제
	 *
	 * @param dir
	 * @return
	 */
	public static boolean rmDir(File dir) {
		boolean state = true;
		try {
			if (!dir.exists()) {
				return false;
			}
			File[] files = dir.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						rmDir(file);
					} else {
						file.delete();
					}
				}
				dir.delete();
			}
		} catch (Exception e) {
			state = false;
		}
		return state;
	}

	/**
	 * 디렉토리 명으로 디렉토리 삭제
	 *
	 * @param dirName
	 * @return
	 */
	public static boolean rmDir(String dirName) {
		boolean state = true;
		try {
			File dir = new File(ComStringUtils.filterSystemPath(dirName));
			return rmDir(dir);
		} catch (Exception e) {
			state = false;
		}
		return state;
	}

	/**
	 * 파일 삭제
	 *
	 * @param fileName
	 * @return
	 */
	public static boolean rmFile(String fileName) {
		boolean state = true;
		try {
			File file = new File(ComStringUtils.filterSystemPath(fileName));
			file.delete();
		} catch (Exception e) {
			state = false;
		}
		return state;
	}

	/**
	 * 디렉토리 생성
	 *
	 * @param dirName
	 * @return
	 */
	public static boolean mkDir(String dirName) {
		boolean state = true;
		try {
			File dir = new File(ComStringUtils.filterSystemPath(dirName));
			if (!dir.exists()) {
				dir.setExecutable(false, true);
				dir.setReadable(true);
				dir.setWritable(true);
				state = dir.mkdirs();
			} else {
				state = false;
			}
		} catch (Exception e) {
			state = false;
		}
		return state;
	}

	/**
	 * 파일 업로드
	 *
	 * @param saveFileName
	 * @param storePath
	 * @return
	 */
	 public static String writeFileSave(Entry<String, MultipartFile> fileEntry, String saveFileName, String storePath, String KeyStr) throws IllegalStateException, IOException {

	 String storePathString = "";

	 if ("".equals(storePath) || storePath == null) {
	 storePathString = EgovProperties.getProperty("Globals.fileStorePath");
	 } else {
	 storePathString = storePath;
	 }

	 String strDay =  EgovStringUtil.getTimeStamp();

	String yearDir = strDay.substring(0, 4);
	String monthDir = strDay.substring(4, 6);

	storePathString = storePathString + KeyStr  + "/" + yearDir + "/" + monthDir + "/";

	 mkDir(storePathString); // 디렉토리 생성

	 String filePath = "";

	 MultipartFile file = fileEntry.getValue();

	 String orginFileName = file.getOriginalFilename();

//	 String fileExt = getFileExtension(orginFileName);

	 String newName = saveFileName;

	 if (!"".equals(orginFileName)) {
		 filePath = ComStringUtils.filterSystemPath(storePathString + newName);
		 file.transferTo(new File(filePath));
	 }

	 	return storePathString;
	 }

	 /**
		 * 파일 업로드
		 *
		 * @param saveFileName
		 * @param storePath
		 * @return
		 */
		 public static String writeFileSave(MultipartFile file, String saveFileName, String storePath, String KeyStr) throws IllegalStateException, IOException {

		 String storePathString = "";

		 if ("".equals(storePath) || storePath == null) {
		 storePathString = EgovProperties.getProperty("Globals.fileStorePath");
		 } else {
		 storePathString = storePath;
		 }

		 String strDay =  EgovStringUtil.getTimeStamp();

		String yearDir = strDay.substring(0, 4);
		String monthDir = strDay.substring(4, 6);

		storePathString = storePathString + KeyStr  + "/" + yearDir + "/" + monthDir + "/";

		 mkDir(storePathString); // 디렉토리 생성

		 String filePath = "";

		 String orginFileName = file.getOriginalFilename();

//		 String fileExt = getFileExtension(orginFileName);

		 String newName = saveFileName;

		 if (!"".equals(orginFileName)) {
			 filePath = ComStringUtils.filterSystemPath(storePathString + newName);
			 file.transferTo(new File(filePath));
		 }

		 	return storePathString;
		 }

	public static void setImage(String filePath, String fileNam, HttpServletResponse response) throws IOException {
		File file = null;
		FileInputStream fis = null;

		BufferedInputStream in = null;
		ByteArrayOutputStream bStream = null;

		String type = "";

		try {

			file = new File(ComStringUtils.filterSystemPath(filePath));
			fis = new FileInputStream(file);

			in = new BufferedInputStream(fis);
			bStream = new ByteArrayOutputStream();

			int imgByte;
			while ((imgByte = in.read()) != -1) {
				bStream.write(imgByte);
			}

			if (fileNam != null && !"".equals(fileNam)) {

				String ext = FileUtil.getFileExtension(fileNam);

				if ("jpg".equals(ext.toLowerCase())) {
					type = "image/jpeg";
				} else {
					type = "image/" + ext.toLowerCase();
				}
				type = "image/" + ext.toLowerCase();

			}
			response.setHeader("Content-Type", type);
			response.setContentLength(bStream.size());
			bStream.writeTo(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();

		} finally {
			if (bStream != null) {
				try {
					bStream.close();
				} catch (Exception ignore) {
					LOGGER.debug("IGNORE: {}", ignore.getMessage());
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception ignore) {
					LOGGER.debug("IGNORE: {}", ignore.getMessage());
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception ignore) {
					LOGGER.debug("IGNORE: {}", ignore.getMessage());
				}
			}
		}

	}

	/**
	 * @param filePath
	 * @param fileNam
	 * @param response
	 * @throws IOException
	 */
	public static void fileDown(String filePath, String fileNam, HttpServletResponse response) throws IOException {

		File file = new File(ComStringUtils.filterSystemPath(filePath + "/" + fileNam));

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
		byte[] inputData = null;
		try {
			int read = 0;
			int bufSize = 2048;
			inputData = new byte[bufSize];
			while ((read = bis.read(inputData)) > 0) {
				bos.write(inputData, 0, read);
			}
			bos.flush();

		} catch (Exception e) {
			LOGGER.debug("Exception", e.getMessage());

		} finally {
			bis.close();
			bos.close();
		}
	}

	/**
	 * 서버 파일 업로드
	 *
	 * @param files
	 * @param KeyStr
	 *		  파일명 시작 String
	 * @param storePath
	 *	       디렉토리(폴더)
	 * @param gbn
	 *            : default="" 파일명 비교값(""->없으면 전부 업로드)
	 * @return 기존 파일 이름 / 새로운 파일 이름(날짜) / 파일경로를 List형태로 return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> parseFileInfList(Map<String, MultipartFile> files, String KeyStr, String storePath, String gbn) throws Exception {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS", Locale.KOREA);

		String storePathString = "";

		if (("".equals(storePath)) || (storePath == null)) {
			storePathString = UPLOAD_PATH;
		} else {
			storePathString = UPLOAD_PATH + storePath + "/";
		}

		File saveFolder = new File(storePathString);

		if ((!saveFolder.exists()) || (saveFolder.isFile())) {
			saveFolder.mkdirs();
		}

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		String originalName = null;
		String newFilename = null;
		String filePath = null;

		if (!files.isEmpty()) {
			Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();

			MultipartFile multipartFile = null;

			int fileSeq = 0;

			while (itr.hasNext()) {
				Entry<String, MultipartFile> entry = itr.next();

				multipartFile = entry.getValue();

				if (multipartFile.getName().contains(gbn) || "".equals(gbn)) {
					if (!multipartFile.isEmpty()) {
						originalName = multipartFile.getOriginalFilename();
						newFilename = simpleDateFormat.format(Long.valueOf(new Timestamp(System.currentTimeMillis()).getTime()));
						newFilename = KeyStr + newFilename + fileSeq++;
						filePath = storePathString + newFilename;

						map = new HashMap<String, Object>();
						map.put("originalName", originalName);
						map.put("fileName", newFilename);
						map.put("filePath", storePathString);
						list.add(map);

						try {
							multipartFile.transferTo(new File(filePath));
						} catch (Exception e) {
							e.printStackTrace();
							LOGGER.debug(e.toString());

							if (list.size() != 0) {
								for (int i = 0; i < list.size(); i++) {
									map = list.get(i);
									newFilename = (String) map.get("fileName");

									if (!"".equals(newFilename)) {
										filePath = storePath + newFilename;
										rmFile(filePath);
									}
								}
							}
						}
					}
				}
			}
		}

		return list;
	}

	/**
	 * @param files
	 * @param KeyStr
	 * @param storePath
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> parseFileInfList(Map<String, MultipartFile> files, String KeyStr, String storePath) throws Exception {
		return parseFileInfList(files, KeyStr, storePath, "");
	}

	/**
	 * 파일 업로드(개별)
	 *
	 * @param multipartFile
	 * @param KeyStr
	 * @param storePath
	 * @param gbn
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> parseFileInf(MultipartFile multipartFile, String KeyStr, String storePath, int fileSeq) throws Exception {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS", Locale.KOREA);

		String storePathString = "";

		if (("".equals(storePath)) || (storePath == null)) {
			storePathString = UPLOAD_PATH;
		} else {
			storePathString = UPLOAD_PATH + storePath + "/";
		}

		File saveFolder = new File(storePathString);

		if ((!saveFolder.exists()) || (saveFolder.isFile())) {
			saveFolder.mkdirs();
		}

		Map<String, Object> map = null;
		String originalName = null;
		String newFilename = null;
		String filePath = null;

		if (!multipartFile.isEmpty()) {
			originalName = multipartFile.getOriginalFilename();
			newFilename = simpleDateFormat.format(Long.valueOf(new Timestamp(System.currentTimeMillis()).getTime()));
			newFilename = KeyStr + newFilename + fileSeq;
			filePath = storePathString + newFilename;

			map = new HashMap<String, Object>();
			map.put("originalName", originalName);
			map.put("fileName", newFilename);
			map.put("filePath", storePathString);

			try {
				multipartFile.transferTo(new File(filePath));
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.debug(e.toString());

				newFilename = (String) map.get("fileName");

				if (!"".equals(newFilename)) {
					filePath = storePath + newFilename;
					rmFile(filePath);
				}
			}
		}

		return map;
	}
}

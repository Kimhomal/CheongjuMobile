package tgis.fclts.service.impl;
import static tgis.common.util.com.FileUtil.getFileExtension;
import static tgis.common.util.com.FileUtil.mkDir;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.MethodInvoker;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import tgis.common.util.com.ComDateUtils;
import tgis.common.util.com.ComStringUtils;
import tgis.common.util.com.FileUtil;
import tgis.fclts.service.FcltsMngMapper;
import tgis.fclts.service.FcltsMngService;

@Service("fcltsMngService")
public class FcltsMngServiceImpl implements FcltsMngService{

	@Resource(name = "fcltsMngMapper")
	private FcltsMngMapper fcltsMngMapper;

	public int insertFacility(HashMap<String, Object> params, HttpServletRequest request) throws Exception {
		String fileYn = (String) params.get("fileYn");

		if("Y".equals(fileYn)) { //파일 첨부가 구분값이 Y
		final Map<String, MultipartFile> files = ((MultipartHttpServletRequest) request).getFileMap();

		Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();

		while(itr.hasNext()) {
			String atchFileId = fcltsMngMapper.atchFileId();
			fcltsMngMapper.insertFileMaster(atchFileId);
			int fileSn = 0;

			String saveFileName = "fclts_" + ComDateUtils.getCurFullDateTime();
			Map.Entry<String, MultipartFile> fileEntry = itr.next();

			String storePathString = EgovProperties.getProperty("Globals.fileStorePath");
			storePathString = storePathString + params.get("facilityId").toString() + "/";

			mkDir(storePathString);

			String filePath = "";

			MultipartFile file = fileEntry.getValue();

			String orginFileName = file.getOriginalFilename();

			if (!"".equals(orginFileName)) {
				filePath = ComStringUtils.filterSystemPath(storePathString + saveFileName);
				file.transferTo(new File(filePath));
			}

			params.put("fileExtsn", getFileExtension(orginFileName));
			params.put("fileStreCours", storePathString);
			params.put("fileSize", file.getSize());
			params.put("orignlFileNm", orginFileName);
			params.put("streFileNm", saveFileName);
			params.put("fileSn", fileSn);
			params.put("atchFileId", atchFileId);

			fcltsMngMapper.insertFileDetail(params);
			params.put("fileSn", fileSn++);

			fcltsMngMapper.insertFile(params);
			}
		}

		int cnt = 0;
		String methodName = "insertFcltsInfo_" + ((String) params.get("facilityId")).toUpperCase();

		try {
			MethodInvoker invoker = new MethodInvoker();
			invoker.setTargetObject(fcltsMngMapper);
			invoker.setTargetMethod(methodName);
			invoker.setArguments(new Object[] { params });
			invoker.prepare();
			cnt = (int) invoker.invoke();
		} catch (NoSuchMethodException ex) {
			return cnt;
		}
		return cnt;
	}

	public EgovMap addr(HashMap<String, Object> params) throws Exception {

		return fcltsMngMapper.addr(params);
	}

	public EgovMap css(HashMap<String, Object> params) throws Exception {
		return fcltsMngMapper.css(params);
	}

	public EgovMap sch(HashMap<String, Object> params) throws Exception {
		return fcltsMngMapper.sch(params);
	}

	public EgovMap police(HashMap<String, Object> params) throws Exception {
		return fcltsMngMapper.police(params);
	}

	public EgovMap road(HashMap<String, Object> params) throws Exception {
		return fcltsMngMapper.road(params);
	}

	public List<?> getIgtSignList(HashMap<String, Object> params) throws Exception {
		return fcltsMngMapper.getIgtSignList(params);
	}

	public EgovMap selectFcltsInfo(HashMap<String, Object> params) throws Exception {

		EgovMap result = new EgovMap();

		String methodName = "selectFcltsInfo_" + ((String) params.get("facilityId")).toUpperCase();

		try {
			MethodInvoker invoker = new MethodInvoker();
			invoker.setTargetObject(fcltsMngMapper);
			invoker.setTargetMethod(methodName);
			invoker.setArguments(new Object[] { params });
			invoker.prepare();
			result = (EgovMap) invoker.invoke();

		} catch (NoSuchMethodException ex) {
			//throw new Exception( "Method '" + methodName + "' not found", ex);
			result = null;
		}

		return result;
	}

	public int deleteFacility(HashMap<String, Object> params,HttpServletRequest request) throws Exception {

		int cnt = 0;

		List<?> fileList =  fcltsMngMapper.selectFileInfo(params);

		if(fileList.size() > 0){
			for(int i = 0; i < fileList.size(); i++){
				EgovMap 	fileInfo = (EgovMap)fileList.get(i);

				String filePath = (String)fileInfo.get("filePath");
				String fileName = (String)fileInfo.get("fileNam");

				FileUtil.rmFile(filePath + "/" + fileName);

				params.put("sn",fileInfo.get("sn").toString());
				params.put("atchFileId", fileInfo.get("atchFileId"));

				fcltsMngMapper.deleteFileDetail(params);	// COMTNFILEDETAIL 테이블에서 삭제
				fcltsMngMapper.deleteFileMaster(params);	// COMTNFILE 테이블에서 삭제
				fcltsMngMapper.deleteFileInfo(params);	// FACILITY_PHOTO_ALBUM 테이블에서 삭제
			}
		}

		String methodName = "deleteFcltsInfo_" + ((String) params.get("facilityId")).toUpperCase();

		try {
			MethodInvoker invoker = new MethodInvoker();
			invoker.setTargetObject(fcltsMngMapper);
			invoker.setTargetMethod(methodName);
			invoker.setArguments(new Object[] { params });
			invoker.prepare();
//			invoker.invoke();
			cnt = (int) invoker.invoke();
		} catch (NoSuchMethodException ex) {
			return cnt;
		}

		return cnt;
	}

	public int updateFacility(HashMap<String, Object> params,HttpServletRequest request) throws Exception {

		int cnt = 0;
		String fileYn = (String) params.get("fileYn");
		String preFileYn = (String) params. get("preFileYn");
		String delFile = (String) params.get("delFile");

		if("Y".equals(fileYn)) { //파일 첨부가 구분값이 Y
			String  facilityId = params.get("facilityId").toString();

			final Map<String, MultipartFile> files = ((MultipartHttpServletRequest) request).getFileMap();  //request에서 파일 객체 받고

			if("Y".equals(preFileYn)) { // 수정일 경우 전에 등록한 파일이 있는 경우
				if("Y".equals(delFile)) {// 수정일 경우 전에 등록한 파일을 지웠을 경우

					String delFileId = (String)params.get("delFileId");

					String[] arryDelFileId = delFileId.split(",");

					params.put("arryDelFileId",arryDelFileId );

					for(int i =0; i < arryDelFileId.length; i++) {
						params.put("sn", arryDelFileId[i]);
						EgovMap fileInfo = fcltsMngMapper.selectFileInfoDetail(params);

						String filePath = fileInfo.get("filePath").toString();
						String fileName = fileInfo.get("fileNam").toString();
						FileUtil.rmFile(filePath + "/" + fileName);

						params.put("atchFileId", fileInfo.get("atchFileId").toString());

						fcltsMngMapper.deleteFileDetail(params);	// COMTNFILEDETAIL 테이블에서 삭제
						fcltsMngMapper.deleteFileMaster(params);	// COMTNFILE 테이블에서 삭제
						fcltsMngMapper.deleteFileInfo(params);	// FACILITY_PHOTO_ALBUM 테이블에서 삭제
					}

					Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();

					while(itr.hasNext()) {
						String atchFileId = fcltsMngMapper.atchFileId();
						fcltsMngMapper.insertFileMaster(atchFileId);
						int fileSn = 0;

						String saveFileName = "fclts_" + ComDateUtils.getCurFullDateTime();
						Map.Entry<String, MultipartFile> fileEntry = itr.next();

						String storePathString = EgovProperties.getProperty("Globals.fileStorePath");
						storePathString = storePathString + params.get("facilityId").toString() + "/";

						mkDir(storePathString);

						String filePath = "";

						MultipartFile file = fileEntry.getValue();

						String orginFileName = file.getOriginalFilename();

						if (!"".equals(orginFileName)) {
							filePath = ComStringUtils.filterSystemPath(storePathString + saveFileName);
							file.transferTo(new File(filePath));
						}

						params.put("fileExtsn", getFileExtension(orginFileName));
						params.put("fileStreCours", storePathString);
						params.put("fileSize", file.getSize());
						params.put("orignlFileNm", orginFileName);
						params.put("streFileNm", saveFileName);
						params.put("fileSn", fileSn);
						params.put("atchFileId", atchFileId);

						fcltsMngMapper.insertFileDetail(params);
						params.put("fileSn", fileSn++);

						fcltsMngMapper.insertFile(params);
					} // 첨부할 파일이 없을때 까지 반복
				}else{// 수정일 경우 전에 등록한 파일을 지우지않았을 경우
					Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();

					while(itr.hasNext()) {
						String atchFileId = fcltsMngMapper.atchFileId();
						fcltsMngMapper.insertFileMaster(atchFileId);
						int fileSn = 0;

						String saveFileName = "fclts_" + ComDateUtils.getCurFullDateTime();
						Map.Entry<String, MultipartFile> fileEntry = itr.next();

						String storePathString = EgovProperties.getProperty("Globals.fileStorePath");
						storePathString = storePathString + params.get("facilityId").toString() + "/";

						mkDir(storePathString);

						String filePath = "";

						MultipartFile file = fileEntry.getValue();

						String orginFileName = file.getOriginalFilename();

						if (!"".equals(orginFileName)) {
							filePath = ComStringUtils.filterSystemPath(storePathString + saveFileName);
							file.transferTo(new File(filePath));
						}

						params.put("fileExtsn", getFileExtension(orginFileName));
						params.put("fileStreCours", storePathString);
						params.put("fileSize", file.getSize());
						params.put("orignlFileNm", orginFileName);
						params.put("streFileNm", saveFileName);
						params.put("fileSn", fileSn);
						params.put("atchFileId", atchFileId);

						fcltsMngMapper.insertFileDetail(params);
						params.put("fileSn", fileSn++);
						fcltsMngMapper.insertFile(params); // 파일 테이블에 파일 정보 저장
					}
				}
			} else {// 수정일 경우 전에 등록한 파일이 없는 경우
				Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();

				while(itr.hasNext()) {
					String atchFileId = fcltsMngMapper.atchFileId();
					fcltsMngMapper.insertFileMaster(atchFileId);
					int fileSn = 0;

					String saveFileName = "fclts_" + ComDateUtils.getCurFullDateTime();
					Map.Entry<String, MultipartFile> fileEntry = itr.next();

					String storePathString = EgovProperties.getProperty("Globals.fileStorePath");
					storePathString = storePathString + params.get("facilityId").toString() + "/";

					mkDir(storePathString);

					String filePath = "";

					MultipartFile file = fileEntry.getValue();

					String orginFileName = file.getOriginalFilename();

					if (!"".equals(orginFileName)) {
						filePath = ComStringUtils.filterSystemPath(storePathString + saveFileName);
						file.transferTo(new File(filePath));
					}

					params.put("fileExtsn", getFileExtension(orginFileName));
					params.put("fileStreCours", storePathString);
					params.put("fileSize", file.getSize());
					params.put("orignlFileNm", orginFileName);
					params.put("streFileNm", saveFileName);
					params.put("fileSn", fileSn);
					params.put("atchFileId", atchFileId);

					fcltsMngMapper.insertFileDetail(params);
					params.put("fileSn", fileSn++);

					fcltsMngMapper.insertFile(params);
				}
			}
			String methodName = "updateFcltsInfo_" + ((String) params.get("facilityId")).toUpperCase();
			try {
				MethodInvoker invoker = new MethodInvoker();
				invoker.setTargetObject(fcltsMngMapper);
				invoker.setTargetMethod(methodName);
				invoker.setArguments(new Object[] { params });
				invoker.prepare();
				cnt = (int) invoker.invoke();
			} catch (NoSuchMethodException ex) {
				return cnt;
			}

		} else {
			if("Y".equals(preFileYn)&&"Y".equals(delFile)) {

				String delFileId = (String)params.get("delFileId");

				String[] arryDelFileId = delFileId.split(",");

				params.put("arryDelFileId",arryDelFileId );

				for(int i =0; i < arryDelFileId.length; i++) {
					params.put("sn", arryDelFileId[i]);
					EgovMap fileInfo = fcltsMngMapper.selectFileInfoDetail(params);

					String filePath = fileInfo.get("filePath").toString();
					String fileName = fileInfo.get("fileNam").toString();
					FileUtil.rmFile(filePath + "/" + fileName);

					params.put("atchFileId", fileInfo.get("atchFileId").toString());

					fcltsMngMapper.deleteFileDetail(params);	// COMTNFILEDETAIL 테이블에서 삭제
					fcltsMngMapper.deleteFileMaster(params);	// COMTNFILE 테이블에서 삭제
					fcltsMngMapper.deleteFileInfo(params);	// FACILITY_PHOTO_ALBUM 테이블에서 삭제
				}
			}

			String methodName = "updateFcltsInfo_" + ((String) params.get("facilityId")).toUpperCase();

			try {
				MethodInvoker invoker = new MethodInvoker();
				invoker.setTargetObject(fcltsMngMapper);
				invoker.setTargetMethod(methodName);
				invoker.setArguments(new Object[] { params });
				invoker.prepare();
				cnt = (int) invoker.invoke();
			} catch (NoSuchMethodException ex) {
				return cnt;
			}
		}
		return cnt;
	}

	@Override
	public EgovMap selectReportFileInf(HashMap<String, Object> params) throws Exception {
		return fcltsMngMapper.selectReportFileInf(params);
	}

	@Override
	public EgovMap selectFacFileInf(HashMap<String, Object> params) throws Exception {
		return fcltsMngMapper.selectFacFileInf(params);
	}

	public List<?> selectFileInfo(HashMap<String, Object> params) throws Exception {
		return fcltsMngMapper.selectFileInfo(params);
	}

	public List<?> selectWorkList(HashMap<String, Object> params) throws Exception {

		List<?> result = null;

		String methodName = "selectWorkList_" + ((String) params.get("facilityId")).toUpperCase();

		try {
			MethodInvoker invoker = new MethodInvoker();
			invoker.setTargetObject(fcltsMngMapper);
			invoker.setTargetMethod(methodName);
			invoker.setArguments(new Object[] { params });
			invoker.prepare();
			result = (List<?>) invoker.invoke();

		} catch (NoSuchMethodException ex) {
			//throw new Exception( "Method '" + methodName + "' not found", ex);
			result = null;
		}

		return result;
	}

	public int insertWorkList(HashMap<String, Object> params, HttpServletRequest request) throws Exception {
		int cnt = 0;
		String methodName = "insertWorkList_" + ((String) params.get("facilityId")).toUpperCase();

		try {
			MethodInvoker invoker = new MethodInvoker();
			invoker.setTargetObject(fcltsMngMapper);
			invoker.setTargetMethod(methodName);
			invoker.setArguments(new Object[] { params });
			invoker.prepare();
			cnt = (int) invoker.invoke();
		} catch (NoSuchMethodException ex) {
			return cnt;
		}
		return cnt;

	}

	public EgovMap selectInfo(HashMap<String, Object> params) throws Exception {

		EgovMap result = null;

		String methodName = "selectInfo_" + ((String) params.get("facilityId")).toUpperCase();

		try {
			MethodInvoker invoker = new MethodInvoker();
			invoker.setTargetObject(fcltsMngMapper);
			invoker.setTargetMethod(methodName);
			invoker.setArguments(new Object[] { params });
			invoker.prepare();
			result = (EgovMap) invoker.invoke();

		} catch (NoSuchMethodException ex) {
			//throw new Exception( "Method '" + methodName + "' not found", ex);
			result = null;
		}

		return result;
	}

	public int updateWorkList(HashMap<String, Object> params, HttpServletRequest request) throws Exception {
		int cnt = 0;
		String methodName = "updateWorkList_" + ((String) params.get("facilityId")).toUpperCase();

		try {
			MethodInvoker invoker = new MethodInvoker();
			invoker.setTargetObject(fcltsMngMapper);
			invoker.setTargetMethod(methodName);
			invoker.setArguments(new Object[] { params });
			invoker.prepare();
			cnt = (int) invoker.invoke();
		} catch (NoSuchMethodException ex) {
			return cnt;
		}
		return cnt;

	}

	public int deleteWorkList(HashMap<String, Object> params, HttpServletRequest request) throws Exception {
		int cnt = 0;
		String methodName = "deleteWorkList_" + ((String) params.get("facilityId")).toUpperCase();

		try {
			MethodInvoker invoker = new MethodInvoker();
			invoker.setTargetObject(fcltsMngMapper);
			invoker.setTargetMethod(methodName);
			invoker.setArguments(new Object[] { params });
			invoker.prepare();
			cnt = (int) invoker.invoke();
		} catch (NoSuchMethodException ex) {
			return cnt;
		}
		return cnt;

	}


}

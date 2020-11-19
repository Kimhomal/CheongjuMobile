package tgis.report.atcMng.service.impl;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import tgis.common.util.com.ComDateUtils;
import tgis.common.util.com.ComStringUtils;
import tgis.common.util.com.FileUtil;
import tgis.report.atcMng.service.AtcMngMapper;
import tgis.report.atcMng.service.AtcMngService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static tgis.common.util.com.FileUtil.getFileExtension;
import static tgis.common.util.com.FileUtil.mkDir;


@Service("atcMngService")
public class AtcMngServiceImpl implements AtcMngService {
	
	@Resource(name = "atcMngMapper")
	private AtcMngMapper atcMngMapper;

	public List<?> selectInfoList(HashMap<String, Object> params) throws Exception {
		return atcMngMapper.selectInfoList(params);
	}

	public EgovMap selectInfo(HashMap<String, Object> params) throws Exception {
		return  atcMngMapper.selectInfo(params);
	}

	public List<?> selectFileInfo(HashMap<String, Object> params) throws Exception {
		return atcMngMapper.selectFileInfo(params);
	}

	public void insertInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception {
		atcMngMapper.insertInfo(params);
		String fileYn = (String) params.get("fileYn");

		if("Y".equals(fileYn)) { //파일 첨부가 구분값이 Y
			final Map<String, MultipartFile> files = ((MultipartHttpServletRequest) request).getFileMap();  //request에서 파일 객체 받고

			Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();

			while(itr.hasNext()) {
				String atchFileId = atcMngMapper.atchFileId();
				atcMngMapper.insertFileMaster(atchFileId);
				int fileSn = 0;

				String saveFileName = "atcMng_" + ComDateUtils.getCurFullDateTime();
				Map.Entry<String, MultipartFile> fileEntry = itr.next();

				String storePathString = EgovProperties.getProperty("Globals.fileStorePath");
				storePathString = storePathString + "ATC/";

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

				atcMngMapper.insertFileDetail(params);
				params.put("fileSn", fileSn++);

				atcMngMapper.insertFile(params);
			}
		}
	}

	public void updateInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception {
		String fileYn = (String) params.get("fileYn");
		String preFileYn = (String) params. get("preFileYn");
		String delFile = (String) params.get("delFile");

		if("Y".equals(fileYn)) { //파일 첨부가 구분값이 Y
			final Map<String, MultipartFile> files = ((MultipartHttpServletRequest) request).getFileMap();  //request에서 파일 객체 받고

			if("Y".equals(preFileYn)) { // 수정일 경우 전에 등록한 파일이 있는 경우
				if("Y".equals(delFile)) {// 수정일 경우 전에 등록한 파일을 지웠을 경우

					String delFileId = (String)params.get("delFileId");

					String[] arryDelFileId = delFileId.split(",");

					params.put("arryDelFileId",arryDelFileId );

					for(int i =0; i < arryDelFileId.length; i++) {
						params.put("sn", arryDelFileId[i]);
						EgovMap fileInfo = atcMngMapper.selectFileInfoDetail(params);

						String filePath = fileInfo.get("filePath").toString();
						String fileName = fileInfo.get("fileNam").toString();
						FileUtil.rmFile(filePath + "/" + fileName);

						params.put("atchFileId", fileInfo.get("atchFileId").toString());

						atcMngMapper.deleteFileDetail(params);	// COMTNFILEDETAIL 테이블에서 삭제
						atcMngMapper.deleteFileMaster(params);	// COMTNFILE 테이블에서 삭제
						atcMngMapper.deleteFileInfo(params);	// FACILITY_PHOTO_ALBUM 테이블에서 삭제
					}

					Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();

					while(itr.hasNext()) {
						String atchFileId = atcMngMapper.atchFileId();
						atcMngMapper.insertFileMaster(atchFileId);
						int fileSn = 0;

						String saveFileName = "atcMng_" + ComDateUtils.getCurFullDateTime();
						Map.Entry<String, MultipartFile> fileEntry = itr.next();

						String storePathString = EgovProperties.getProperty("Globals.fileStorePath");
						storePathString = storePathString + "ATC/";

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

						atcMngMapper.insertFileDetail(params);
						params.put("fileSn", fileSn++);

						atcMngMapper.insertFile(params);
					} // 첨부할 파일이 없을때 까지 반복
				}else{// 수정일 경우 전에 등록한 파일을 지우지않았을 경우
					Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();

					while(itr.hasNext()) {
						String atchFileId = atcMngMapper.atchFileId();
						atcMngMapper.insertFileMaster(atchFileId);
						int fileSn = 0;

						String saveFileName = "atcMng_" + ComDateUtils.getCurFullDateTime();
						Map.Entry<String, MultipartFile> fileEntry = itr.next();

						String storePathString = EgovProperties.getProperty("Globals.fileStorePath");
						storePathString = storePathString + "ATC/";

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

						atcMngMapper.insertFileDetail(params);
						params.put("fileSn", fileSn++);
						atcMngMapper.insertFile(params); // 파일 테이블에 파일 정보 저장
					}
				}
			} else {// 수정일 경우 전에 등록한 파일이 없는 경우
				Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();

				while(itr.hasNext()) {
					String atchFileId = atcMngMapper.atchFileId();
					atcMngMapper.insertFileMaster(atchFileId);
					int fileSn = 0;

					String saveFileName = "atcMng_" + ComDateUtils.getCurFullDateTime();
					Map.Entry<String, MultipartFile> fileEntry = itr.next();

					String storePathString = EgovProperties.getProperty("Globals.fileStorePath");
					storePathString = storePathString + "ATC/";

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

					atcMngMapper.insertFileDetail(params);
					params.put("fileSn", fileSn++);

					atcMngMapper.insertFile(params);
				}
			}
			atcMngMapper.updateInfo(params);
		} else {
			if("Y".equals(preFileYn)&&"Y".equals(delFile)) {

				String delFileId = (String)params.get("delFileId");

				String[] arryDelFileId = delFileId.split(",");

				params.put("arryDelFileId",arryDelFileId );

				for(int i =0; i < arryDelFileId.length; i++) {
					params.put("sn", arryDelFileId[i]);
					EgovMap fileInfo = atcMngMapper.selectFileInfoDetail(params);

					String filePath = fileInfo.get("filePath").toString();
					String fileName = fileInfo.get("fileNam").toString();
					FileUtil.rmFile(filePath + "/" + fileName);

					params.put("atchFileId", fileInfo.get("atchFileId").toString());

					atcMngMapper.deleteFileDetail(params);	// COMTNFILEDETAIL 테이블에서 삭제
					atcMngMapper.deleteFileMaster(params);	// COMTNFILE 테이블에서 삭제
					atcMngMapper.deleteFileInfo(params);	// FACILITY_PHOTO_ALBUM 테이블에서 삭제
				}
			}
			atcMngMapper.updateInfo(params);
		}
	}

	public void deleteInfo(HashMap<String, Object> params) throws Exception {
		List<?> fileList =  atcMngMapper.selectFileInfo(params);

		if(fileList.size() > 0){
			for(int i = 0; i < fileList.size(); i++){
				EgovMap fileInfo = (EgovMap)fileList.get(i);

				String filePath = (String)fileInfo.get("filePath");
				String fileName = (String)fileInfo.get("fileNam");

				FileUtil.rmFile(filePath + "/" + fileName);

				params.put("sn",fileInfo.get("sn").toString());
				params.put("atchFileId", fileInfo.get("atchFileId"));
			}

			atcMngMapper.deleteFileDetail(params);	// COMTNFILEDETAIL 테이블에서 삭제
			atcMngMapper.deleteFileMaster(params);	// COMTNFILE 테이블에서 삭제
			atcMngMapper.deleteFileInfo(params);	// FACILITY_PHOTO_ALBUM 테이블에서 삭제
		}
		atcMngMapper.deleteInfo(params);
	}

	@Override
	public List<?> selectExcelList(HashMap<String, Object> params) throws Exception {
		return atcMngMapper.selectExcelList(params);
	}

}

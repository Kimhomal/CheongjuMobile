package tgis.board.law.service.impl;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import tgis.board.law.service.LawMapper;
import tgis.board.law.service.LawService;
import tgis.common.util.com.ComDateUtils;
import tgis.common.util.com.FileUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service("lawService")
public class LawServiceImpl implements LawService {
	
	@Resource(name = "lawMapper")
	private LawMapper lawMapper;

	public List<?> selectInfoList(HashMap<String, Object> params) throws Exception {
		return lawMapper.selectInfoList(params);
	}

	public EgovMap selectInfo(HashMap<String, Object> params) throws Exception {
		params.put("visited", lawMapper.selectMaxVisited(params));
		lawMapper.updateVisited(params);
		return  lawMapper.selectInfo(params);
	}

	public List<?> selectFileInfo(HashMap<String, Object> params) throws Exception {
		return lawMapper.selectFileInfo(params);
	}

	public int selectLawMaxID(HashMap<String, Object> params) throws Exception {

		return lawMapper.selectLawMaxID(params);
	}

	public void insertInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception {
		String fileYn = params.get("fileYn").toString();

		if("Y".equals(fileYn)) {
			final Map<String, MultipartFile> files = ((MultipartHttpServletRequest) request).getFileMap();

			Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();

			while (itr.hasNext()) {
				String saveFileName = "law_" + ComDateUtils.getCurFullDateTime();
				Map.Entry<String, MultipartFile> fileEntry = itr.next();
				String FilePath = FileUtil.writeFileSave( fileEntry, saveFileName, "", "law");

				params.put("oglNam", fileEntry.getValue().getOriginalFilename());
				params.put("fileNam", saveFileName);
				params.put("filePath", FilePath);

				lawMapper.insertFile(params);
			}
			lawMapper.insertInfo(params);
		} else {
			lawMapper.insertInfo(params);
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

					List<?> fileList =  lawMapper.selectFileInfo(params); // 삭제된 파일 정보 불러옴

					for(int i = 0; i < fileList.size(); i++){

						EgovMap fileInfo = (EgovMap)fileList.get(i);

						String filePath = (String)fileInfo.get("filePath");
						String fileName = (String)fileInfo.get("fileNam");

						FileUtil.rmFile(filePath+fileName); // 실제 파일 삭제
					}

					lawMapper.deleteFileInfo(params); // 파일 테이블에서 삭제

					Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();

					while(itr.hasNext()) {
						String saveFileName = "law_" + ComDateUtils.getCurFullDateTime();
						Map.Entry<String, MultipartFile> fileEntry = itr.next();
						String FilePath = FileUtil.writeFileSave( fileEntry, saveFileName, "", "law");

						params.put("oglNam", fileEntry.getValue().getOriginalFilename()); // 실제 파일명
						params.put("fileNam", saveFileName); // 저장된 파일명
						params.put("filePath", FilePath); // 저장된 파일 위치

						lawMapper.insertFile(params);
					} // 첨부할 파일이 없을때 까지 반복
				}else{// 수정일 경우 전에 등록한 파일을 지우지않았을 경우
					Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();

					while(itr.hasNext()) {
						String saveFileName = "law_" + ComDateUtils.getCurFullDateTime();
						Map.Entry<String, MultipartFile> fileEntry = itr.next();
						String FilePath = FileUtil.writeFileSave( fileEntry, saveFileName, "", "law");

						params.put("oglNam", fileEntry.getValue().getOriginalFilename());
						params.put("fileNam", saveFileName);
						params.put("filePath", FilePath);

						lawMapper.insertFile(params); // 파일 테이블에 파일 정보 저장
					}
				}
			} else {// 수정일 경우 전에 등록한 파일이 없는 경우
				Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();

				while(itr.hasNext()) {
					String saveFileName = "law_" + ComDateUtils.getCurFullDateTime();
					Map.Entry<String, MultipartFile> fileEntry = itr.next();
					String FilePath = FileUtil.writeFileSave( fileEntry, saveFileName, "", "law");

					params.put("oglNam", fileEntry.getValue().getOriginalFilename());
					params.put("fileNam", saveFileName);
					params.put("filePath", FilePath);

					lawMapper.insertFile(params);
				}
			}
			lawMapper.updateInfo(params);
		} else {
			if("Y".equals(preFileYn)&&"Y".equals(delFile)) {

				String delFileId = (String)params.get("delFileId");

				String[] arryDelFileId = delFileId.split(",");

				params.put("arryDelFileId",arryDelFileId );

				List<?> fileList =  lawMapper.selectFileInfo(params);

				for(int i = 0; i < fileList.size(); i++){
					EgovMap 	fileInfo = (EgovMap)fileList.get(i);
					String filePath = (String)fileInfo.get("filePath");
					String fileName = (String)fileInfo.get("fileNam");
					FileUtil.rmFile(filePath+fileName);
				}
				lawMapper.deleteFileInfo(params);
			}
			lawMapper.updateInfo(params);
		}
	}

	public void deleteInfo(HashMap<String, Object> params) throws Exception {
		List<?> fileList =  lawMapper.selectFileInfo(params);

		if(fileList.size() > 0){
			for(int i = 0; i < fileList.size(); i++){

				EgovMap 	fileInfo = (EgovMap)fileList.get(i);

				String filePath = (String)fileInfo.get("filePath");
				String fileName = (String)fileInfo.get("fileNam");

				FileUtil.rmFile(filePath+fileName);
			}

			lawMapper.deleteFileInfo(params);
		}
		lawMapper.deleteInfo(params);
	}

	public void deleteFileInfo(HashMap<String, Object> params) throws Exception {
		lawMapper.deleteFileInfo(params);
	}

}

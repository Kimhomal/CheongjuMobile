package tgis.board.qna.service.impl;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import tgis.board.qna.service.QnaMapper;
import tgis.board.qna.service.QnaService;
import tgis.common.util.com.ComDateUtils;
import tgis.common.util.com.FileUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service("qnaService")
public class QnaServiceImpl implements QnaService {
	
	@Resource(name = "qnaMapper")
	private QnaMapper qnaMapper;

	public List<?> selectInfoList(HashMap<String, Object> params) throws Exception {
		return qnaMapper.selectInfoList(params);
	}

	public EgovMap selectInfo(HashMap<String, Object> params) throws Exception {
		params.put("visited", qnaMapper.selectMaxVisited(params));
		qnaMapper.updateVisited(params);
		return  qnaMapper.selectInfo(params);
	}

	public List<?> selectFileInfo(HashMap<String, Object> params) throws Exception {
		return qnaMapper.selectFileInfo(params);
	}

	public int selectQnaMaxID(HashMap<String, Object> params) throws Exception {

		return qnaMapper.selectQnaMaxID(params);
	}

	public void insertInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception {
		String fileYn = params.get("fileYn").toString();

		if("Y".equals(fileYn)) {
			final Map<String, MultipartFile> files = ((MultipartHttpServletRequest) request).getFileMap();

			Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();

			while (itr.hasNext()) {
				String saveFileName = "qna_" + ComDateUtils.getCurFullDateTime();
				Map.Entry<String, MultipartFile> fileEntry = itr.next();
				String FilePath = FileUtil.writeFileSave( fileEntry, saveFileName, "", "qna");

				params.put("oglNam", fileEntry.getValue().getOriginalFilename());
				params.put("fileNam", saveFileName);
				params.put("filePath", FilePath);

				qnaMapper.insertFile(params);
			}
			qnaMapper.insertInfo(params);
		} else {
			qnaMapper.insertInfo(params);
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

					List<?> fileList =  qnaMapper.selectFileInfo(params); // 삭제된 파일 정보 불러옴

					for(int i = 0; i < fileList.size(); i++){

						EgovMap fileInfo = (EgovMap)fileList.get(i);

						String filePath = (String)fileInfo.get("filePath");
						String fileName = (String)fileInfo.get("fileNam");

						FileUtil.rmFile(filePath+fileName); // 실제 파일 삭제
					}

					qnaMapper.deleteFileInfo(params); // 파일 테이블에서 삭제

					Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();

					while(itr.hasNext()) {
						String saveFileName = "qna_" + ComDateUtils.getCurFullDateTime();
						Map.Entry<String, MultipartFile> fileEntry = itr.next();
						String FilePath = FileUtil.writeFileSave( fileEntry, saveFileName, "", "qna");

						params.put("oglNam", fileEntry.getValue().getOriginalFilename()); // 실제 파일명
						params.put("fileNam", saveFileName); // 저장된 파일명
						params.put("filePath", FilePath); // 저장된 파일 위치

						qnaMapper.insertFile(params);
					} // 첨부할 파일이 없을때 까지 반복
				}else{// 수정일 경우 전에 등록한 파일을 지우지않았을 경우
					Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();

					while(itr.hasNext()) {
						String saveFileName = "qna_" + ComDateUtils.getCurFullDateTime();
						Map.Entry<String, MultipartFile> fileEntry = itr.next();
						String FilePath = FileUtil.writeFileSave( fileEntry, saveFileName, "", "qna");

						params.put("oglNam", fileEntry.getValue().getOriginalFilename());
						params.put("fileNam", saveFileName);
						params.put("filePath", FilePath);

						qnaMapper.insertFile(params); // 파일 테이블에 파일 정보 저장
					}
				}
			} else {// 수정일 경우 전에 등록한 파일이 없는 경우
				Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();

				while(itr.hasNext()) {
					String saveFileName = "qna_" + ComDateUtils.getCurFullDateTime();
					Map.Entry<String, MultipartFile> fileEntry = itr.next();
					String FilePath = FileUtil.writeFileSave( fileEntry, saveFileName, "", "qna");

					params.put("oglNam", fileEntry.getValue().getOriginalFilename());
					params.put("fileNam", saveFileName);
					params.put("filePath", FilePath);

					qnaMapper.insertFile(params);
				}
			}
			qnaMapper.updateInfo(params);
		} else {
			if("Y".equals(preFileYn)&&"Y".equals(delFile)) {

				String delFileId = (String)params.get("delFileId");

				String[] arryDelFileId = delFileId.split(",");

				params.put("arryDelFileId",arryDelFileId );

				List<?> fileList =  qnaMapper.selectFileInfo(params);

				for(int i = 0; i < fileList.size(); i++){
					EgovMap 	fileInfo = (EgovMap)fileList.get(i);
					String filePath = (String)fileInfo.get("filePath");
					String fileName = (String)fileInfo.get("fileNam");
					FileUtil.rmFile(filePath+fileName);
				}
				qnaMapper.deleteFileInfo(params);
			}
			qnaMapper.updateInfo(params);
		}
	}

	public void updateSeq(HashMap<String, Object> params) throws Exception {
		qnaMapper.updateSeq(params);
	}

	public void answerInfo(HashMap<String, Object> params, HttpServletRequest request) throws Exception {
		qnaMapper.answerInfo(params);
	}

	public void deleteInfo(HashMap<String, Object> params) throws Exception {
		List<?> fileList =  qnaMapper.selectFileInfo(params);

		if(fileList.size() > 0){
			for(int i = 0; i < fileList.size(); i++){

				EgovMap 	fileInfo = (EgovMap)fileList.get(i);

				String filePath = (String)fileInfo.get("filePath");
				String fileName = (String)fileInfo.get("fileNam");

				FileUtil.rmFile(filePath+fileName);
			}

			qnaMapper.deleteFileInfo(params);
		}
		qnaMapper.deleteInfo(params);
	}

	public void deleteFileInfo(HashMap<String, Object> params) throws Exception {
		qnaMapper.deleteFileInfo(params);
	}

}

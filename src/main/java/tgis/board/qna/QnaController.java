package tgis.board.qna;

import com.nhncorp.lucy.security.xss.XssPreventer;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tgis.board.qna.service.QnaService;
import tgis.common.annotation.AuthHandler;
import tgis.common.util.com.FileUtil;
import tgis.user.auth.service.ResourceVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;


@Controller
public class QnaController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QnaController.class);
	
	@Resource(name = "qnaService")
	private QnaService qnaService;

	//Q&A 페이지
	@AuthHandler(handler="M008005000")
	@RequestMapping(value = "/board/qna/selectQnaList.do")
	public String qnaPage() throws Exception {
		return "board/qna/selectInfoList";
	}

	//Q&A 조회
	@AuthHandler(handler="M008005000")
	@RequestMapping(value = "/board/qna/selectInfoList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ModelMap selectInfoList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("rows", rows);
		params.put("page", page);

		// 리스트조회
		List<?> resultList = qnaService.selectInfoList(params);

		double totalPage = 0.0;
		Long totalRows = 0L;

		if (resultList.size() > 0) {
			totalRows = Long.valueOf(((EgovMap) resultList.get(0)).get("totalrows").toString());
			totalPage = Math.ceil(totalRows / rows) + 1;
		}

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("records", totalRows);	// 총 개수
		jsonMap.put("total", totalPage);	// 총 페이지 수
		jsonMap.put("page", page);			// 현재 페이지
		jsonMap.put("root", resultList);	// 리스트

		model.addAttribute("jsonView", jsonMap);	// JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

	//등록, 수정, 상세보기창
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M008005000")
	@RequestMapping(value = "/board/qna/crudInfo.do")
	public String initPage(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String method = (String) params.get("method");

		if (!"insert".equals(method)) {
			EgovMap result = qnaService.selectInfo(params);
			if(Integer.parseInt((result.get("filecnt").toString()))>0){
				List<?> fileList = qnaService.selectFileInfo(params);
				params.put("fileYn", "Y");
				model.addAttribute("fileList", fileList);
			}
			model.addAttribute("result", result);
		}

		model.addAttribute("params", params);

		HashMap<String,Object> resources = (HashMap<String,Object>) request.getSession().getAttribute("authResourceVO");
		ResourceVO vo = (ResourceVO) resources.get("M008005000");

		return "board/qna/" + method + "Info";
	}

	@RequestMapping(value = "/board/qna/fileDown.do")
	public void fileDown(@RequestParam HashMap<String, Object> params, HttpServletResponse response) throws Exception {
		String path = EgovProperties.getProperty("Globals.fileStorePath") + params.get("path").toString();
		String rlFileNam = params.get("rlFileNam").toString();
		String svFileNam = params.get("svFileNam").toString();
		String fname = URLEncoder.encode(svFileNam, "utf-8");

		response.setCharacterEncoding("UTF-8");
		response.setHeader("Accept-Ranges", "bytes");
		rlFileNam = URLEncoder.encode(rlFileNam, "UTF-8").replace("+","%20");

		response.setHeader("Content-Type", "application/octet-stream;charset=utf-8;");
		response.setHeader("Content-Disposition", "attachment;filename=" + rlFileNam + ";");

		FileUtil.fileDown(path, fname, response);
	}

	//등록,수정,삭제
	@SuppressWarnings("unchecked")
	@AuthHandler(handler="M008005000")
	@RequestMapping(value = "/board/qna/{method}Info", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap cudInfo(@PathVariable String method, @RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			if ("insert".equals(method)) {
				HashMap<String, Object> usrInfo = (HashMap<String, Object>)request.getSession().getAttribute("auth");
				String usrId = (String) usrInfo.get("usrId");

				params.put("usrId", usrId);
				int ntMaxId = qnaService.selectQnaMaxID(params);
				params.put("qnaid", ntMaxId);

				// XSS Filter 적용
				params.put("subject", XssPreventer.escape(params.get("subject").toString()));
				params.put("writerNm", XssPreventer.escape(params.get("writerNm").toString()));
				params.put("ctt", XssPreventer.escape(params.get("ctt").toString()));

				qnaService.insertInfo(params, request);
			} else if ("update".equals(method)) {
				// XSS Filter 적용
				params.put("subject", XssPreventer.escape(params.get("subject").toString()));
				params.put("writerNm", XssPreventer.escape(params.get("writerNm").toString()));
				params.put("ctt", XssPreventer.escape(params.get("ctt").toString()));

				qnaService.updateInfo(params, request);
			} else if ("answer".equals(method)) {
				HashMap<String, Object> usrInfo = (HashMap<String, Object>)request.getSession().getAttribute("auth");
				String usrId = (String) usrInfo.get("usrId");
				params.put("usrId", usrId);

				int ntMaxId = qnaService.selectQnaMaxID(params);
				params.put("qnaid", ntMaxId);

				// XSS Filter 적용
				params.put("subject", XssPreventer.escape(params.get("subject").toString()));
				params.put("writerNm", XssPreventer.escape(params.get("writerNm").toString()));
				params.put("ctt", XssPreventer.escape(params.get("ctt").toString()));

				qnaService.updateSeq(params);
				qnaService.answerInfo(params, request);
			} else {
				qnaService.deleteInfo(params);
			}
			jsonMap.put("respFlag", "Y");

		} catch (Exception ex) {
			LOGGER.debug(ex.toString());
			jsonMap.put("respFlag", "N");
		}

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}

}

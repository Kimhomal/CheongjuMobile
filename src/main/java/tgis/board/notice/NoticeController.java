package tgis.board.notice;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhncorp.lucy.security.xss.XssPreventer;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import tgis.board.notice.service.NoticeService;
import tgis.common.annotation.AuthHandler;
import tgis.common.util.com.FileUtil;
import tgis.common.util.encrypt.EncryptSDK;
import tgis.user.auth.service.ResourceVO;



@Controller
public class NoticeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);
	
	@Resource(name = "noticeService")
	private NoticeService noticeService;
	
	//공지사항 페이지
	@AuthHandler(handler="M008001000")
	@RequestMapping(value = "/board/notice/selectNoticeList.do")
	public String noticePage() throws Exception {
		return "board/notice/selectInfoList";
	}
	
	//공지사항 조회
	@AuthHandler(handler="M008001000")
	@RequestMapping(value = "/board/notice/selectInfoList", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ModelMap selectInfoList(@RequestParam HashMap<String, Object> params, ModelMap model) throws Exception {
		int rows = Integer.parseInt(params.get("rows").toString());
		int page = Integer.parseInt(params.get("page").toString());

		params.put("rows", rows);
		params.put("page", page);

		// 리스트조회
		List<?> resultList = noticeService.selectInfoList(params);
		
		String key = EgovProperties.getProperty("Globals.AriaKey");
		
//		for(int i=0; i<resultList.size(); i++) {
//			EgovMap selectInfo = (EgovMap)resultList.get(i);
//			
//			String pw = String.valueOf(selectInfo.get("pw"));
//			String telNum = String.valueOf(selectInfo.get("telNum"));
//			
//			if(!("null").equals(pw)) {
//				pw = EncryptSDK.decrypt(pw, key);
//			} else {
//				pw = "";
//			}
//			
//			if(!("null").equals(telNum)) {
//				telNum = EncryptSDK.decrypt(telNum, key);
//			} else {
//				telNum = "";
//			}
//			
//			selectInfo.put("pw", pw);
//			selectInfo.put("telNum", telNum);
//			
//			}

		double totalPage = 0.0;
		Long totalRows = 0L;

		if (resultList.size() > 0) {
			totalRows = Long.valueOf(((EgovMap) resultList.get(0)).get("totalrows").toString());
			totalPage = Math.ceil(totalRows / rows) + 1;
			
		}

		HashMap<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("records", totalRows); // 총 개수
		jsonMap.put("total", totalPage); // 총 페이지 수
		jsonMap.put("page", page); // 현재 페이지
		jsonMap.put("root", resultList); // 리스트

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}
	
	//등록,수정, 상세보기창
	@AuthHandler(handler="M008001000")
	@RequestMapping(value = "/board/notice/crudInfo.do")
	public String initPage(@RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		String method = (String) params.get("method");
		
		if (!"insert".equals(method)) {
			EgovMap result = noticeService.selectInfo(params);
			if(Integer.parseInt((result.get("filecnt").toString()))>0){
				List<?> fileList = noticeService.selectFileInfo(params);
				params.put("fileYn", "Y");
				model.addAttribute("fileList", fileList);
			}
			model.addAttribute("result", result);

			if(result.get("ip") != null && result.get("ip") != "") {
				String ip = result.get("ip").toString();

				if(!"update".equals(method)) {
					ip = ip.replace(".", "-").split("-")[0]+".***.***."+ip.replace(".", "-").split("-")[3];
				}
			}
		}

		model.addAttribute("params", params);

		return "board/notice/" + method + "Info";
		
//권한주고 다시
//		if (!"insert".equals(method)) {
//			EgovMap result = noticeService.selectInfo(params);
//			if(Integer.parseInt((result.get("filecnt").toString()))>0){
//				List<?> fileList = noticeService.selectFileInfo(params);
//				params.put("fileYn", "Y");
//				model.addAttribute("fileList", fileList);
//			}
//			model.addAttribute("result", result);
//
//			if(result.get("ip") != null && result.get("ip") != "") {
//				String ip = result.get("ip").toString();
//
//				if(!"update".equals(method)) {
//					ip = ip.replace(".", "-").split("-")[0]+".***.***."+ip.replace(".", "-").split("-")[3];
//				}
//				result.put("ip", ip);
//			}
//		} else {
//			HashMap<String, Object> usrInfo = (HashMap<String, Object>) request.getSession().getAttribute("auth");
//			String buseNam = (String)usrInfo.get("buseNam");
//			params.put("buseNam", buseNam);
//		}
//
//		model.addAttribute("params", params);
//
//		HashMap<String,Object> resources = (HashMap<String,Object>) request.getSession().getAttribute("authResourceVO");
//		ResourceVO vo = (ResourceVO) resources.get("M009001000");
//
//		if(!"select".equals(method)) {
//			if ("TRUE".equals(vo.getcuMenuId())) {
//				return "board/notice/" + method + "Info";
//			} else {
//				return "board/notice/selectInfoList";
//			}
//		} else {
//			return "board/notice/" + method + "Info";
//		}
		
		
	}
	
	//등록,수정,삭제
	@AuthHandler(handler="M008001000")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/board/notice/{method}Info", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ModelMap cudInfo(@PathVariable String method, @RequestParam HashMap<String, Object> params, ModelMap model, HttpServletRequest request) throws Exception {
		HashMap<String, Object> jsonMap = new HashMap<String, Object>();

		try {

			if ("insert".equals(method)) {
				HashMap<String, Object> usrInfo = (HashMap<String, Object>)request.getSession().getAttribute("auth");
				String usrId = (String) usrInfo.get("usrId");
				String usrNam = (String) usrInfo.get("usrNam");
				params.put("usrId", usrId);
				params.put("usrNam", usrNam);
				int ntMaxId = noticeService.selectNoticeMaxID(params);
				params.put("noticeid", ntMaxId);
				

				// XSS Filter 적용
				params.put("subject", XssPreventer.escape(params.get("subject").toString()));
//				params.put("telNum", XssPreventer.escape(params.get("telNum").toString()));
				params.put("ctt", XssPreventer.escape(params.get("ctt").toString()));
				
				
				noticeService.insertInfo(params, request);
			} else if ("update".equals(method)) {
				// XSS Filter 적용
				params.put("subject", XssPreventer.escape(params.get("subject").toString()));
				params.put("telNum", XssPreventer.escape(params.get("telNum").toString()));
				params.put("ctt", XssPreventer.escape(params.get("ctt").toString()));

				noticeService.updateInfo(params, request);
			} else {
				noticeService.deleteInfo(params);
			}

			jsonMap.put("respFlag", "Y");

		} catch (Exception ex) {
			LOGGER.debug(ex.toString());
			jsonMap.put("respFlag", "N");
		}

		model.addAttribute("jsonView", jsonMap); // JSON으로 리턴하기 위해서는 모델키를 'jsonView'로 지정해야함
		return model;
	}
	
	@RequestMapping(value = "/board/notice/fileDown.do")
	public void fileDown(@RequestParam HashMap<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String path = EgovProperties.getProperty("Globals.fileStorePath") + params.get("path").toString();

		String rlFileNam = params.get("rlFileNam").toString();

		String svFileNam = params.get("svFileNam").toString();

		String fname = URLEncoder.encode(svFileNam, "utf-8");

		response.setCharacterEncoding("UTF-8");
		response.setHeader("Accept-Ranges", "bytes");
		String agent = request.getHeader("User-Agent");
		rlFileNam = URLEncoder.encode(rlFileNam, "UTF-8").replace("+","%20");

		response.setHeader("Content-Type", "application/octet-stream;charset=utf-8;");

		if(agent.indexOf("MSIE") > -1 || agent.indexOf("Trident") > -1) {
			response.setHeader("Content-Disposition", "attachment;filename=" + rlFileNam + ";");
		} else{
			response.setHeader("Content-Disposition", "attachment;filename=" + rlFileNam + ";");
		}

		FileUtil.fileDown(path, fname, response);
	}
	
	
}

package tgis.main;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tgis.main.service.MainPageService;
import tgis.board.notice.service.NoticeService;

/**
 * 템플릿 메인 페이지 컨트롤러 클래스(Sample 소스)
 * @author 실행환경 개발팀 JJY
 * @since 2011.08.31
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.08.31  JJY            최초 생성
 *
 * </pre>
 */
@Controller
public class MainPageController {

	@Resource(name = "mainPageService")
	private MainPageService masterService;
	
	@Resource(name = "noticeService")
	private NoticeService noticeService;

	private static final Logger LOGGER = LoggerFactory.getLogger(MainPageController.class);

//	@AuthExclude
	@RequestMapping(value = "/index.do")
	public String indexPage(@RequestParam HashMap<String, Object> params,HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		//공지사항 리스트조회

		List<?> resultList = masterService.mainNoticeList(params);

		model.addAttribute("resultList", resultList);
		return "index";
	}
	

}
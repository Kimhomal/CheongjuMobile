package tgis.introduce;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tgis.common.annotation.AuthHandler;

/**
 * 템플릿 소개 메뉴 컨트롤러 클래스(Sample 소스)
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
public class IntroduceController {

//	@AuthHandler(handler="M001002000")
	@RequestMapping("/introduce/introduce01.do")
	public String introduce01() throws Exception {
		return "introduce/introduce01";
	}

}
package tgis.main.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import tgis.main.service.MainPageMapper;
import tgis.main.service.MainPageService;

/**
 * 지도서비스 > 시설물관리 요청을 처리하는 비즈니스(ServiceImpl) 클래스
 *
 * @author 공간정보기술(주)
 * @since 2017.08.23
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일                   수정자                   수정내용
 *  ------------       ----------------    ---------------------------
 *   2017.08.23     공간정보기술(주)            최초 생성
 *
 *      </pre>
 */
@Service("mainPageService")
public class MainPageServiceImpl implements MainPageService {

	@Resource(name = "mainPageMapper")
	private MainPageMapper masterMapper;

	public List<?> mainNoticeList(HashMap<String, Object> params) throws Exception {
		return masterMapper.mainNoticeList(params);
	}

}

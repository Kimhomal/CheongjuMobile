package tgis.main.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * 지도서비스 > 시설물관리를 처리하는 Service 인터페이스
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
@Transactional
public interface MainPageService {

	List<?> mainNoticeList(HashMap<String, Object> params) throws Exception;



}

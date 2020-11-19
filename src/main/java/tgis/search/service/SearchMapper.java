package tgis.search.service;

import java.util.HashMap;
import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("searchMapper")
public interface SearchMapper {

	List<?> searchCross(HashMap<String, Object> params) throws Exception;

	List<?> searchJibun(HashMap<String, Object> params) throws Exception;

	//시설물 검색
	List<?> selectFacil_GT_A001_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A003_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A004_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A005_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A008_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A049_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A054_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A055_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A057_L(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A061_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A062_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A064_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A065_L(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A067_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A068_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A069_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A078_L(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A079_L(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A080_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A081_L(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A082_L(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A083_L(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A084_L(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A085_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A090_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_A110_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C024_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C051_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C059_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C087_L(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C088_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C091_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C092_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C093_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C094_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C095_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C096_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C097_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C098_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C100_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C101_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C103_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C104_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C107_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C109_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C111_P(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C113_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C114_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C115_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C117_A(HashMap<String, Object> params) throws Exception;
	List<?> selectFacil_GT_C118_P(HashMap<String, Object> params) throws Exception;

}

package tgis.statistics.service;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.HashMap;
import java.util.List;

@Mapper("statisticsMngMapper")
public interface StatisticsMngMapper {

    // 전체통계 > 교통안전시설물 개수 조회
    EgovMap trafficCnt() throws Exception;

    // 전체통계 > 도로안전시설물 개수 조회
    EgovMap rodCnt() throws Exception;

    // 전체통계 > 특수교통운영구역 개수 조회
    EgovMap specialCnt() throws Exception;

    // 전체통계 > 기본도 개수 조회
    EgovMap baseCnt() throws Exception;

    // 전체통계 > 기타 개수 조회
    EgovMap etcCnt() throws Exception;

    // 교통안전시설물 구별 전체 통계
    List<?> selectGuStatistics_GT_A004_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A005_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A049_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A054_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A055_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A057_L(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A064_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A062_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A068_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A078_L(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A079_L(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A080_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A081_L(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A082_L(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A083_L(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A084_L(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A085_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A090_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A110_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A111_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C024_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A061_P(HashMap<String, Object> params) throws Exception;


    // 도로안전시설물 구별 전체 통계
    List<?> selectGuStatistics_GT_A069_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C100_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C088_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C104_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A067_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C109_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C051_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C091_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C059_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C092_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C093_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C095_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C096_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C097_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C094_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C098_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C107_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_A065_L(HashMap<String, Object> params) throws Exception;


    // 특수교통운영구역 구별 전체 통계
    List<?> selectGuStatistics_GT_C101_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C103_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C115_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C117_A(HashMap<String, Object> params) throws Exception;



    // 기본도 구별 전체 통계
    List<?> selectGuStatistics_GT_A008_A(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C114_A(HashMap<String, Object> params) throws Exception;


    // 기타 구별 전체 통계
    List<?> selectGuStatistics_GT_C118_P(HashMap<String, Object> params) throws Exception;
    List<?> selectGuStatistics_GT_C111_P(HashMap<String, Object> params) throws Exception;


    // 교통안전시설물 경찰서별 전체 통계
    List<?> selectPoliceStatistics_GT_A004_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A005_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A049_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A054_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A055_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A057_L(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A062_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A064_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A068_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A078_L(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A079_L(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A080_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A081_L(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A082_L(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A083_L(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A084_L(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A085_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A090_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A110_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A111_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C024_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A061_P(HashMap<String, Object> params) throws Exception;


    // 도로안전시설물 경찰서별 전체 통계
    List<?> selectPoliceStatistics_GT_A069_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C100_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C088_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C104_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A067_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C109_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C051_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C091_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C059_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C092_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C093_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C095_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C096_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C097_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C094_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C098_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C107_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_A065_L(HashMap<String, Object> params) throws Exception;


    // 특수교통운영구역 경찰서별 전체 통계
    List<?> selectPoliceStatistics_GT_C101_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C103_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C115_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C117_A(HashMap<String, Object> params) throws Exception;



    // 기본도 경찰서별 전체 통계
    List<?> selectPoliceStatistics_GT_A008_A(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C114_A(HashMap<String, Object> params) throws Exception;


    // 기타 경찰서별 전체 통계
    List<?> selectPoliceStatistics_GT_C118_P(HashMap<String, Object> params) throws Exception;
    List<?> selectPoliceStatistics_GT_C111_P(HashMap<String, Object> params) throws Exception;


    // 교통안전시설물 동별 전체 통계
    List<?> selectDongStatistics_GT_A004_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A005_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A049_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A054_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A055_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A057_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A062_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A064_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A068_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A078_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A079_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A080_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A081_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A082_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A083_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A084_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A085_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A090_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A110_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A111_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C024_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A061_P(HashMap<String, Object> params) throws Exception;


    // 도로안전시설물 동별 전체 통계
    List<?> selectDongStatistics_GT_A069_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C100_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C088_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C104_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A067_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C109_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C051_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C091_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C059_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C092_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C093_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C095_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C096_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C097_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C094_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C098_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C107_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_A065_L(HashMap<String, Object> params) throws Exception;


    // 특수교통운영구역 동별 전체 통계
    List<?> selectDongStatistics_GT_C101_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C103_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C115_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C117_A(HashMap<String, Object> params) throws Exception;



    // 기본도 동별 전체 통계
    List<?> selectDongStatistics_GT_A008_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C114_A(HashMap<String, Object> params) throws Exception;


    // 기타 동별 전체 통계
    List<?> selectDongStatistics_GT_C118_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDongStatistics_GT_C111_P(HashMap<String, Object> params) throws Exception;


    // 교통안전시설물 교차로별 전체 통계
    List<?> selectCrossStatistics_GT_A004_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A005_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A049_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A054_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A055_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A057_L(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A062_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A064_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A068_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A078_L(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A079_L(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A080_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A081_L(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A082_L(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A083_L(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A084_L(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A085_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A090_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A110_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A111_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C024_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A061_P(HashMap<String, Object> params) throws Exception;


    // 도로안전시설물 교차로별 전체 통계
    List<?> selectCrossStatistics_GT_A069_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C100_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C088_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C104_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A067_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C109_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C051_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C091_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C059_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C092_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C093_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C095_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C096_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C097_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C094_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C098_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C107_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_A065_L(HashMap<String, Object> params) throws Exception;


    // 특수교통운영구역 교차로별 전체 통계
    List<?> selectCrossStatistics_GT_C101_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C103_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C115_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C117_A(HashMap<String, Object> params) throws Exception;



    // 기본도 교차로별 전체 통계
    List<?> selectCrossStatistics_GT_A008_A(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C114_A(HashMap<String, Object> params) throws Exception;


    // 기타 교차로별 전체 통계
    List<?> selectCrossStatistics_GT_C118_P(HashMap<String, Object> params) throws Exception;
    List<?> selectCrossStatistics_GT_C111_P(HashMap<String, Object> params) throws Exception;


    // 교통안전시설물 상세내역 전체 통계
    List<?> selectDetailFcltsList_GT_A004_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A005_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A049_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A054_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A055_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A057_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A064_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A068_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A062_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A078_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A079_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A080_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A081_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A082_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A083_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A084_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A085_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A090_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A110_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A111_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C024_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A061_P(HashMap<String, Object> params) throws Exception;


    // 도로안전시설물 상세내역 전체 통계
    List<?> selectDetailFcltsList_GT_A069_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C100_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C088_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C104_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A067_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C109_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C051_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C091_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C059_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C092_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C093_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C095_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C096_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C097_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C094_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C098_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C107_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_A065_L(HashMap<String, Object> params) throws Exception;


    // 특수교통운영구역 상세내역 전체 통계
    List<?> selectDetailFcltsList_GT_C101_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C103_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C115_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C117_A(HashMap<String, Object> params) throws Exception;



    // 기본도 상세내역 전체 통계
    List<?> selectDetailFcltsList_GT_A008_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C114_A(HashMap<String, Object> params) throws Exception;


    // 기타 상세내역 전체 통계
    List<?> selectDetailFcltsList_GT_C118_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsList_GT_C111_P(HashMap<String, Object> params) throws Exception;


    // 교통안전시설물 상세내역 엑셀
    List<?> selectDetailFcltsExcelDown_GT_A004_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A005_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A049_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A054_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A055_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A057_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A064_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A062_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A068_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A078_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A079_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A080_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A081_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A082_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A083_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A084_L(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A085_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A090_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A110_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A111_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C024_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A061_P(HashMap<String, Object> params) throws Exception;


    // 도로안전시설물 상세내역 엑셀
    List<?> selectDetailFcltsExcelDown_GT_A069_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C100_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C088_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C104_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A067_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C109_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C051_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C091_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C059_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C092_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C093_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C095_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C096_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C097_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C094_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C098_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C107_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_A065_L(HashMap<String, Object> params) throws Exception;


    // 특수교통운영구역 상세내역 엑셀
    List<?> selectDetailFcltsExcelDown_GT_C101_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C103_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C115_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C117_A(HashMap<String, Object> params) throws Exception;



    // 기본도 상세내역 엑셀
    List<?> selectDetailFcltsExcelDown_GT_A008_A(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C114_A(HashMap<String, Object> params) throws Exception;


    // 기타 상세내역 엑셀
    List<?> selectDetailFcltsExcelDown_GT_C118_P(HashMap<String, Object> params) throws Exception;
    List<?> selectDetailFcltsExcelDown_GT_C111_P(HashMap<String, Object> params) throws Exception;

}

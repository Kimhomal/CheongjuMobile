package tgis.statistics.service.impl;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;
import org.springframework.util.MethodInvoker;
import tgis.statistics.service.StatisticsMngMapper;
import tgis.statistics.service.StatisticsMngService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("statisticsMngService")
public class StatisticsMngServiceImpl implements StatisticsMngService {
	@Resource(name = "statisticsMngMapper")
	private StatisticsMngMapper masterMapper;

	public EgovMap trafficCnt() throws Exception {
		return masterMapper.trafficCnt();
	}

	public EgovMap rodCnt() throws Exception {
		return masterMapper.rodCnt();
	}

	public EgovMap specialCnt() throws Exception {
		return masterMapper.specialCnt();
	}
	public EgovMap baseCnt() throws Exception {
		return masterMapper.baseCnt();
	}

	public EgovMap etcCnt() throws Exception {
		return masterMapper.etcCnt();
	}

	public List<?> selectGuStatistics(HashMap<String, Object> params) throws Exception {
		List<?> result = null;
		String methodName = "selectGuStatistics_" + ((String) params.get("facilityId")).toUpperCase();
		try {
			MethodInvoker invoker = new MethodInvoker();
			invoker.setTargetObject(masterMapper);
			invoker.setTargetMethod(methodName);
			invoker.setArguments(new Object[] { params });
			invoker.prepare();
			result = (List<?> ) invoker.invoke();
		} catch (NoSuchMethodException ex) {
			result = null;
		}
		return result;
	}

	public List<?> selectPoliceStatistics(HashMap<String, Object> params) throws Exception {
		List<?> result = null;
		String methodName = "selectPoliceStatistics_" + ((String) params.get("facilityId")).toUpperCase();
		try {
			MethodInvoker invoker = new MethodInvoker();
			invoker.setTargetObject(masterMapper);
			invoker.setTargetMethod(methodName);
			invoker.setArguments(new Object[] { params });
			invoker.prepare();
			result = (List<?> ) invoker.invoke();
		} catch (NoSuchMethodException ex) {
			result = null;
		}
		return result;
	}

	public List<?> selectDongStatistics(HashMap<String, Object> params) throws Exception {
		List<?> result = null;
		String methodName = "selectDongStatistics_" + ((String) params.get("facilityId")).toUpperCase();
		try {
			MethodInvoker invoker = new MethodInvoker();
			invoker.setTargetObject(masterMapper);
			invoker.setTargetMethod(methodName);
			invoker.setArguments(new Object[] { params });
			invoker.prepare();
			result = (List<?> ) invoker.invoke();
		} catch (NoSuchMethodException ex) {
			result = null;
		}
		return result;
	}

	public List<?> selectDetailFcltsList(HashMap<String, Object> params) throws Exception {
		List<?> result = null;
		String methodName = "selectDetailFcltsList_" + ((String) params.get("facilityId")).toUpperCase();
		try {
			MethodInvoker invoker = new MethodInvoker();
			invoker.setTargetObject(masterMapper);
			invoker.setTargetMethod(methodName);
			invoker.setArguments(new Object[] { params });
			invoker.prepare();
			result = (List<?> ) invoker.invoke();
		} catch (NoSuchMethodException ex) {
			result = null;
		}
		return result;
	}

	public List<?> selectDetailFcltsExcelDown(HashMap<String, Object> params) throws Exception {
		List<?> result = null;
		String methodName = "selectDetailFcltsExcelDown_" + ((String) params.get("facilityId")).toUpperCase();
		try {
			MethodInvoker invoker = new MethodInvoker();
			invoker.setTargetObject(masterMapper);
			invoker.setTargetMethod(methodName);
			invoker.setArguments(new Object[] { params });
			invoker.prepare();
			result = (List<?> ) invoker.invoke();
		} catch (NoSuchMethodException ex) {
			result = null;
		}
		return result;
	}

	public List<?> selectCrossStatistics(HashMap<String, Object> params) throws Exception {
		List<?> result = null;
		String methodName = "selectCrossStatistics_" + ((String) params.get("facilityId")).toUpperCase();
		try {
			MethodInvoker invoker = new MethodInvoker();
			invoker.setTargetObject(masterMapper);
			invoker.setTargetMethod(methodName);
			invoker.setArguments(new Object[] { params });
			invoker.prepare();
			result = (List<?> ) invoker.invoke();
		} catch (NoSuchMethodException ex) {
			result = null;
		}
		return result;
	}



}

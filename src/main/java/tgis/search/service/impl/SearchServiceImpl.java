package tgis.search.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.MethodInvoker;

import egovframework.com.json.JSONArray;
import egovframework.com.json.JSONObject;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import tgis.search.service.SearchMapper;
import tgis.search.service.SearchService;

@Service("searchService")
public class SearchServiceImpl implements SearchService {

	@Resource(name = "searchMapper")
	private SearchMapper searchMapper;

	public List<?> searchCross(HashMap<String, Object> params) throws Exception {

		return searchMapper.searchCross(params);

	}

	public Map<String, Object> getDaumAddr(HashMap<String, Object> params) throws Exception {
		Map<String, Object> map = null;

		try {
			URL url = new URL((String) params.get("apiUrl"));

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", (String) params.get("apikey"));
//			conn.setRequestProperty("Authorization", "KakaoAK c39050419b19dc7b3b21d781264c19d9");
			conn.setRequestProperty("charset", "UTF-8");
			conn.setRequestProperty("Cache-Control", "no-cache");

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

			StringBuffer sb = new StringBuffer();
			String strTemp;

			while ((strTemp = br.readLine()) != null) {
				sb.append(strTemp);
			}
			br.close();

			map = new HashMap<String, Object>();

			JSONObject jsonMeta = (JSONObject) (new JSONObject(sb.toString())).get("meta");
			JSONArray jsonDoc = (JSONArray) (new JSONObject(sb.toString())).get("documents");

			map.put("totalRows", jsonMeta.get("pageable_count"));

			List<Object> resultList = new ArrayList<Object>();

			for (int i = 0; i < jsonDoc.length(); i++) {

				JSONObject tmpJsonObject = (JSONObject) jsonDoc.get(i);

				@SuppressWarnings("unchecked")
				Iterator<String> itr = tmpJsonObject.keys();

				Map<String, Object> tmpMap = new HashMap<String, Object>();

				while (itr.hasNext()) {
					String key = itr.next();

					if ("address".equals(key)) {
						try{
							JSONObject adrObject = (JSONObject)tmpJsonObject.get(key);
							tmpMap.put("address", adrObject.get("address_name"));
						}catch(Exception e){
							tmpMap.put("address", null);
						}
					} else if ("road_address".equals(key)){
						try{
							JSONObject adrObject = (JSONObject)tmpJsonObject.get(key);
							tmpMap.put("road_address", adrObject.get("address_name"));
						}catch(Exception e){
							tmpMap.put("road_address", null);
						}
					} else if ("x".equals(key)) {
						tmpMap.put("x", tmpJsonObject.get(key));
					} else if ("y".equals(key)) {
						tmpMap.put("y", tmpJsonObject.get(key));
					}
				}
				resultList.add(tmpMap);
			}
			map.put("resultList", resultList);

		} catch (Exception e) {
			e.printStackTrace();
			map = null;
		}

		return map;
	}

	public Map<String, Object> getKeyword(HashMap<String, Object> params) throws Exception {
		Map<String, Object> map = null;

		try {
			URL url = new URL((String) params.get("apiUrl"));

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("GET");
//			conn.setRequestProperty("Authorization", (String) params.get("apikey"));
			conn.setRequestProperty("Authorization", "KakaoAK c39050419b19dc7b3b21d781264c19d9");
			conn.setRequestProperty("charset", "UTF-8");
			conn.setRequestProperty("Cache-Control", "no-cache");

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

			StringBuffer sb = new StringBuffer();
			String strTemp;

			while ((strTemp = br.readLine()) != null) {
				sb.append(strTemp);
			}
			br.close();

			map = new HashMap<String, Object>();

			JSONObject jsonMeta = (JSONObject) (new JSONObject(sb.toString())).get("meta");
			JSONArray jsonDoc = (JSONArray) (new JSONObject(sb.toString())).get("documents");

			map.put("totalRows", jsonMeta.get("pageable_count"));

			List<Object> resultList = new ArrayList<Object>();

			for (int i = 0; i < jsonDoc.length(); i++) {

				JSONObject tmpJsonObject = (JSONObject) jsonDoc.get(i);

				@SuppressWarnings("unchecked")
				Iterator<String> itr = tmpJsonObject.keys();

				Map<String, Object> tmpMap = new HashMap<String, Object>();

				while (itr.hasNext()) {
					String key = itr.next();

					if ("address_name".equals(key)) {
						tmpMap.put("name", tmpJsonObject.get(key));
					} else if ("place_name".equals(key)) {
						tmpMap.put("placeName", tmpJsonObject.get(key));
					} else if ("x".equals(key)) {
						tmpMap.put("x", tmpJsonObject.get(key));
					} else if ("y".equals(key)) {
						tmpMap.put("y", tmpJsonObject.get(key));
					}
				}
				resultList.add(tmpMap);
			}
			map.put("resultList", resultList);

		} catch (Exception e) {
			e.printStackTrace();
			map = null;
		}

		return map;
	}

	public List<?> selectFacil(HashMap<String, Object> params) throws Exception {

		List<?> result = null;

		String methodName = "selectFacil_" + ((String) params.get("facil")).toUpperCase();

		try {
			MethodInvoker invoker = new MethodInvoker();
			invoker.setTargetObject(searchMapper);
			invoker.setTargetMethod(methodName);
			invoker.setArguments(new Object[] { params });
			invoker.prepare();
			result = (List<?>) invoker.invoke();

		} catch (NoSuchMethodException ex) {
			//throw new Exception( "Method '" + methodName + "' not found", ex);
			result = null;
		}

		return result;
	}

}

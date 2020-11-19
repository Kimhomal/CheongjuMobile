package tgis.user.auth.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("authResourceMapper")
public interface ResourceMapper {

	public List<ResourceVO> getAuthResourceList(LoginVO loginvo) throws Exception;

	public List<ResourceVO> getNotAuthResourceList() throws Exception;
}

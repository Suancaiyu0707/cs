package com.casaba.mer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casaba.dao.entity.MerInfoChangeDectail;
import com.casaba.dao.entity.MerSetmealChange;
import com.casaba.dao.mapper.MerInfoChangeDectailMapper;
import com.casaba.dao.mapper.MerSetmealChangeMapper;
import com.casaba.mer.bean.MerInfoChangeBean;
import com.casaba.mer.bean.MerSetmealChangeBean;
import com.casaba.mer.service.IMerChangeInfoService;

@Service
public class MerChangeInfoServiceImpl implements IMerChangeInfoService {
	
	@Autowired
	private MerSetmealChangeMapper merSetmealChangeMapper;
	
	@Autowired
	private MerInfoChangeDectailMapper merInfoChangeDectailMapper;
	
	
	@Override
	public List<MerInfoChangeBean> queryMerChangeList(Integer merId) {
		List<MerSetmealChange> list=merSetmealChangeMapper.queryListByMerId(merId);
		if(list==null || list.size()==0){
			return null;
		}
		List<MerInfoChangeBean> listBean=new ArrayList<MerInfoChangeBean>();
		MerInfoChangeBean changeBean=null;
		for(MerSetmealChange change :list){
			 changeBean=new MerInfoChangeBean();
			 BeanUtils.copyProperties(change, changeBean);
			 listBean.add(changeBean);
		}
		return listBean;
	}

	@Override
	public List<MerSetmealChangeBean> querySetmealInfoList(Integer merId) {
		
		List<MerInfoChangeDectail> list=merInfoChangeDectailMapper.queryListByMerId(merId);
		if(list==null || list.size()==0){
			return null;
		}
		List<MerSetmealChangeBean> listBean=new ArrayList<MerSetmealChangeBean>();
		MerSetmealChangeBean changeBean=null;
		for(MerInfoChangeDectail change :list){
			 changeBean=new MerSetmealChangeBean();
			 BeanUtils.copyProperties(change, changeBean);
			 listBean.add(changeBean);
		}
		return listBean;
	}

}

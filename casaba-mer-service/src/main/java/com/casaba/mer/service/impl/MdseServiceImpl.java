package com.casaba.mer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casaba.dao.entity.MdseInfo;
import com.casaba.dao.entity.MerchantOrder;
import com.casaba.dao.entity.SaleDetail;
import com.casaba.dao.mapper.MdseInfoMapper;
import com.casaba.dao.mapper.MerchantOrderMapper;
import com.casaba.dao.mapper.SaleDetailMapper;
import com.casaba.mer.bean.MdseInfoBean;
import com.casaba.mer.bean.SaleDetailBean;
import com.casaba.mer.service.IMdseService;

@Service
public class MdseServiceImpl implements IMdseService {
	
	@Autowired
	private MdseInfoMapper mdseInfoMapper;
	
	@Autowired
	private SaleDetailMapper saleDetailMapper;
	
	
	@Autowired
	private MerchantOrderMapper merOrderMapper;
	
	@Override
	public List<MdseInfoBean> queryMdseList() {
		
		List<MdseInfo> mdseList=mdseInfoMapper.queryMdseList();
		if(mdseList==null || mdseList.size()==0){
			return null;
		}
		List<MdseInfoBean> mdseBeanList=new ArrayList<MdseInfoBean>();
		MdseInfoBean mdseBean=null;
		for(MdseInfo mdseInfo:mdseList){
			mdseBean=new MdseInfoBean();
			BeanUtils.copyProperties(mdseInfo, mdseBean);
			mdseBeanList.add(mdseBean);
		}
		return mdseBeanList;
	}

	@Override
	public SaleDetailBean querySaleInfo(Integer saleId) {
		SaleDetail sale=saleDetailMapper.selectById(saleId);
		if(sale==null) {
			return null;
		}
		//
		SaleDetailBean saleBean=new SaleDetailBean();
		
		BeanUtils.copyProperties(sale, saleBean);
		return saleBean;
	}

	@Override
	public List<MdseInfoBean> queryUpgradeMdseList(Integer merId) {
		
		MerchantOrder order=merOrderMapper.selectLastBymerId(merId);
		if(order==null){
			return queryMdseList();
		}
		List<MdseInfo> mdseList=mdseInfoMapper.queryMdseList();
		if(mdseList==null || mdseList.size()==0){
			return null;
		}
		List<MdseInfoBean> mdseBeanList=new ArrayList<MdseInfoBean>();
		MdseInfoBean mdseBean=null;
		SaleDetailBean saleBean=null;
		List<SaleDetailBean> saleBeanList=null;
		boolean flag=false;
		for(MdseInfo mdseInfo:mdseList){
			mdseBean=new MdseInfoBean();
			BeanUtils.copyProperties(mdseInfo, mdseBean);
			List<SaleDetail> saleList=saleDetailMapper.selectByMdseId(mdseInfo.getId());
			if(saleList==null || saleList.size()==0){
				mdseBeanList.add(mdseBean);
				continue;
			}
			//判断当前套餐是不是商户已经购买的套餐			
			if(order.getMdseId()==mdseInfo.getId()){
				//是 当前商品及后面的商品都可以购买
				flag=true;
			}
			//不是，就下个
			if(!flag){
				continue;
			}
			saleBeanList=new ArrayList<SaleDetailBean>();
			for(SaleDetail sale: saleList){
				//排除少于当前购买的年限
				if(sale.getPurchaseYear()<order.getPurchaseYear()){
					continue;
				}
				saleBean=new SaleDetailBean();
				BeanUtils.copyProperties(sale, saleBean);
				saleBeanList.add(saleBean);
			}
			mdseBean.setSaleInfoList(saleBeanList);
			mdseBeanList.add(mdseBean);
		}
		return mdseBeanList;
		
	}

	@Override
	public List<MdseInfoBean> queryRenewalMdseList(Integer merId) {
		
		MerchantOrder order=merOrderMapper.selectLastBymerId(merId);
		if(order==null){
			return queryMdseList();
		}
		List<MdseInfo> mdseList=mdseInfoMapper.queryMdseList();
		if(mdseList==null || mdseList.size()==0){
			return null;
		}
		List<MdseInfoBean> mdseBeanList=new ArrayList<MdseInfoBean>();
		MdseInfoBean mdseBean=null;
		SaleDetailBean saleBean=null;
		List<SaleDetailBean> saleBeanList=null;
		boolean flag=false;
		for(MdseInfo mdseInfo:mdseList){
			mdseBean=new MdseInfoBean();
			BeanUtils.copyProperties(mdseInfo, mdseBean);
			List<SaleDetail> saleList=saleDetailMapper.selectByMdseId(mdseInfo.getId());
			if(saleList==null || saleList.size()==0){
				mdseBeanList.add(mdseBean);
				continue;
			}
			//判断当前套餐是不是商户已经购买的套餐			
			if(order.getMdseId()==mdseInfo.getId()){
				//是 当前商品及后面的商品都可以购买
				flag=true;
			}
			//不是，就下个
			if(!flag){
				continue;
			}
			saleBeanList=new ArrayList<SaleDetailBean>();
			for(SaleDetail sale: saleList){
				//排除少于当前购买的年限
				if(sale.getPurchaseYear()<order.getPurchaseYear()){
					continue;
				}
				saleBean=new SaleDetailBean();
				BeanUtils.copyProperties(sale, saleBean);
				saleBeanList.add(saleBean);
			}
			mdseBean.setSaleInfoList(saleBeanList);
			mdseBeanList.add(mdseBean);
		}
		return mdseBeanList;
		
	}

	@Override
	public List<MdseInfoBean> queryMdseSaleList() {
		
		List<MdseInfo> mdseList=mdseInfoMapper.queryMdseList();
		if(mdseList==null || mdseList.size()==0){
			return null;
		}
		List<MdseInfoBean> mdseBeanList=new ArrayList<MdseInfoBean>();
		MdseInfoBean mdseBean=null;
		SaleDetailBean saleBean=null;
		List<SaleDetailBean> saleBeanList=null;
		for(MdseInfo mdseInfo:mdseList){
			mdseBean=new MdseInfoBean();
			BeanUtils.copyProperties(mdseInfo, mdseBean);
			List<SaleDetail> saleList=saleDetailMapper.selectByMdseId(mdseInfo.getId());
			if(saleList==null || saleList.size()==0){
				mdseBeanList.add(mdseBean);
				continue;
			}
			saleBeanList=new ArrayList<SaleDetailBean>();
			for(SaleDetail sale: saleList){
				saleBean=new SaleDetailBean();
				BeanUtils.copyProperties(sale, saleBean);
				saleBeanList.add(saleBean);
			}
			mdseBean.setSaleInfoList(saleBeanList);
			mdseBeanList.add(mdseBean);
		}
		return mdseBeanList;
		
	}

}

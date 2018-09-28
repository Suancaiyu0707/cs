package com.casaba.mer.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import com.casaba.agent.core.bean.AgentBean;
import com.casaba.agent.core.bean.AgentDetailBean;
import com.casaba.agent.core.service.AgentDetailService;
import com.casaba.agent.core.service.AgentService;
import com.casaba.common.enums.MerChangeType;
import com.casaba.common.enums.MerOrderStatus;
import com.casaba.common.enums.MerOrderType;
import com.casaba.common.enums.MerStatus;
import com.casaba.common.enums.MerType;
import com.casaba.common.exception.AgentServiceException;
import com.casaba.common.exception.MerServiceException;
import com.casaba.common.paging.PageInfo;
import com.casaba.common.util.MsgUtil;
import com.casaba.dao.bean.QueryMer;
import com.casaba.dao.entity.MdseInfo;
import com.casaba.dao.entity.MerInfoChangeDectail;
import com.casaba.dao.entity.MerSetmealChange;
import com.casaba.dao.entity.Merchant;
import com.casaba.dao.entity.MerchantBusiness;
import com.casaba.dao.entity.MerchantOrder;
import com.casaba.dao.entity.SaleDetail;
import com.casaba.dao.mapper.MdseInfoMapper;
import com.casaba.dao.mapper.MerInfoChangeDectailMapper;
import com.casaba.dao.mapper.MerSetmealChangeMapper;
import com.casaba.dao.mapper.MerchantBusinessMapper;
import com.casaba.dao.mapper.MerchantMapper;
import com.casaba.dao.mapper.MerchantOrderMapper;
import com.casaba.dao.mapper.SaleDetailMapper;
import com.casaba.mer.bean.FreeMerchantBean;
import com.casaba.mer.bean.MerBusinessBean;
import com.casaba.mer.bean.MerOrderBean;
import com.casaba.mer.bean.MerchantBean;
import com.casaba.mer.bean.QueryMerBean;
import com.casaba.mer.service.IMerchantService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class MerchantServiceImpl implements IMerchantService {

    private static final Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);
    
    //一年的天数
	//private static final BigDecimal YEAR_DAY=new BigDecimal(365);
    private static final String  FREE_MDSE_NAME="试用版";
    //初始密码
    private static final String  INIT_PWD="suhaodian888";
	
	@Autowired
	private MerchantMapper merchantMapper;
	@Autowired
	private MerchantOrderMapper merOrderMapper;
	@Autowired
	private MerchantBusinessMapper merBusinessMapper;
	
	@Autowired
	private SaleDetailMapper saleDetailMapper;
	@Autowired
	private MdseInfoMapper mdseInfoMapper;
	@Autowired
	private MerSetmealChangeMapper merSetmealChangeMapper;
	@Autowired
	private MerInfoChangeDectailMapper merDectailMapper;
	
	@Autowired
	private AgentDetailService agentDetailService;
	
	@Autowired
	private AgentService agentService;
	
	//private static Map<Integer,String> agentNameMap=Maps.newHashMap();
	
	@Transactional
	@Override
	public Integer addMer(MerchantBean merInfo) {
		
		MdseInfo mdseInfo=mdseInfoMapper.selectById(merInfo.getMdseId());
		if(mdseInfo==null){
			throw new MerServiceException("无效的商品id");
		}
		SaleDetail sale=saleDetailMapper.selectByMdseIdAndId(merInfo.getSaleId(),merInfo.getMdseId());
		if(sale==null){
			throw new MerServiceException("无效的商品销售id");
		}
		//新增商户信息
		Merchant mer=new Merchant();
		mer.setMobile(merInfo.getMobile());
		String pwd=DigestUtils.md5DigestAsHex(INIT_PWD.getBytes());
		mer.setPwd(pwd);
		mer.setMerName(merInfo.getMerName());
		mer.setProvince(merInfo.getProvince());
		mer.setCity(merInfo.getCity());
		mer.setArea(merInfo.getArea());
		mer.setAgentId(merInfo.getAgentId());		
		mer.setMerType(MerType.TYPE_PAY.getCode());
		mer.setMerStatus(MerStatus.STATUS_WW.getCode());
		mer.setMdseId(mdseInfo.getId());
		mer.setMdseName(mdseInfo.getMdseName());
		mer.setOperator(merInfo.getOperator());
		mer.setCreateTime(new Date());
		mer.setUpdateTime(new Date());
		merchantMapper.insert(mer);
		Integer merId=mer.getId();
		//插入商户企业相关信息
		MerBusinessBean mbBean=merInfo.getMerBusiness();
		MerchantBusiness mb=new MerchantBusiness();
		if(mbBean!=null) {
			BeanUtils.copyProperties(mbBean, mb);
		}
		mb.setMerchantId(merId);
		merBusinessMapper.insertSelective(mb);
		
		Integer orderId=saveMerchantOrder(sale, merId, mdseInfo, MerOrderType.TYPE_NEW.getCode(),
				MerOrderStatus.STATUS_WW.getCode(), null, null, BigDecimal.ZERO,merInfo.getOperator());
		
		//将订单号更新进商户信息
		merchantMapper.updateOrderIdById(merId, orderId);
		
		return merId;
	}

	
	/**
	 * 添加付费并开通商户
	 */
	@Transactional
	@Override
	public void addAndOpenMer(MerchantBean mer){
		Integer merId=addMer(mer);
		openMer(merId, mer.getOperator());
	}
	
	
	@Transactional
	@Override
	public void openMer(int merId,String operator) {
		try{
			Merchant mer=merchantMapper.selectById(merId);
			if(mer==null) {
				throw new MerServiceException("无效的商户");
			}
			if(!MerStatus.STATUS_WW.getCode().equals(mer.getMerStatus())) {
				throw new MerServiceException("商户已经开通了，不能重复开通。");
			}
			//查询当前商户代理商的折扣及余额信息
			AgentDetailBean agentBean=agentDetailService.queryBalance(mer.getAgentId());
			if(agentBean==null){
				throw new MerServiceException("代理商已被冻结或无效，不能开通商户。");
			}
			//查询之前选择的套餐信息
			MerchantOrder order=merOrderMapper.selectById(mer.getOrderId());
			//代理商折扣
			BigDecimal discount=agentBean.getDiscount();
			//代理商需消耗费用
			BigDecimal consumePrice=order.getSellPrice().multiply(discount);
			//判断代理金额
			if(agentBean.getBalance().compareTo(consumePrice)<0){
				throw new MerServiceException("代理商余额不足，请给代理商充值后再操作");
			}
			
			Date currentTime=new  Date();
			//计算时间
			MerchantOrder merOrder=new MerchantOrder();
			merOrder.setStartTime(currentTime);
			//计算购买和赠送的年限
			int addYear=order.getPurchaseYear()+order.getGiveYear();
			Date endTime=calcExpireTime_Year(currentTime, addYear);
			merOrder.setEndTime(endTime);
			merOrder.setOrderStatus(MerOrderStatus.STATUS_ING.getCode());
			merOrder.setOrderId(mer.getOrderId());
			merOrder.setConsumePrice(consumePrice);
			merOrderMapper.updateOpenById(merOrder);
			
			//将商户修改成进行中
			merchantMapper.updateStatusAndTimeById(merId, MerStatus.STATUS_WW.getCode(), MerStatus.STATUS_ING.getCode(), 
					currentTime, endTime);
			
			//扣代理商的钱 
			agentDetailService.deductionBalance(mer.getAgentId(), consumePrice, mer.getOrderId()+"", "商户提单  "+mer.getMobile(), operator);
			
			//保存套餐变更信息
			saveMerSetmeal(null, merOrder.getStartTime(), endTime,  MerChangeType.TYPE_NEW.getDesc(), "", mer.getMdseName(), operator, merId);
			
			//商户服务开通成功后 把商户初始密码，通过短信发送至商户手机号中
			openMerSendSms(mer.getMobile());
		} catch (AgentServiceException e) {
			logger.error("开通商户扣款业务异常",  e);
			throw new MerServiceException("代理商余额不足，扣款失败");
		} catch (MerServiceException e) {
			logger.error("开通商户发生业务异常",  e);
			throw new MerServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("开通商户发生系统异常",  e);
			throw new MerServiceException("开通商户失败");
		}
	}
	
	
	

	@Override
	public void updateMer(MerchantBean mer) {
		Integer merId=mer.getId();
		Merchant merchant=merchantMapper.selectById(merId);
		if(merchant==null){
			throw new MerServiceException("无效的商品id");
		}
		
		String merName=mer.getMerName();
		String province=mer.getProvince();
		String city=mer.getCity();
		String area=mer.getArea();
		Integer agentId=mer.getAgentId();
		String operator=mer.getOperator();
		
		Merchant up_mer=new Merchant();
		up_mer.setId(merId);
		//变更商户名称
		if(  !merchant.getMerName().equals(merName)) {
			up_mer.setMerName(merName);
			saveMerInfoChange(merchant.getMerName(), merName, operator, "变更商户名称", merId);
		}
		//变更商户区域
		if( !merchant.getProvince().equals(province) 
				|| !merchant.getCity().equals(city) 
				|| !merchant.getArea().equals(area) ) {
			up_mer.setProvince(province);
			up_mer.setCity(city);
			up_mer.setArea(area);
			
			saveMerInfoChange(merchant.getProvince()+merchant.getCity()+merchant.getArea()
			, province+city+area, operator, "变更商户区域", merId);
		}
		//变更所属代理商
		if(agentId!=null && merchant.getAgentId() != agentId) {
			up_mer.setAgentId(agentId);
			saveMerInfoChange(merchant.getAgentId()+"", agentId+"", operator, "变更所属代理商", merId);
		}
		
		merchantMapper.updateMerInfoById(up_mer);
		
		//更新企业相关信息
		MerBusinessBean merBsBean=mer.getMerBusiness();
		if(merBsBean!=null && merBsBean.getMerchantId()!=null) {
			MerchantBusiness merBs=new MerchantBusiness();
			BeanUtils.copyProperties(merBsBean, merBs);
			merBusinessMapper.updateSelective(merBs);
		}
		
	}

	@Override
	public MerchantBean queryMerDetail(int merId) {
		Merchant mer=merchantMapper.selectById(merId);
		if(mer==null) {
			return null;
		}

		MerchantBean merBean=new MerchantBean();
		BeanUtils.copyProperties(mer, merBean);
		merBean.setAgentName(getAgentName(mer.getAgentId()));
		MerchantBusiness bus=merBusinessMapper.queryByMerId(merId);
		MerBusinessBean busBean = new MerBusinessBean();
		if(bus!=null) {
			BeanUtils.copyProperties(bus, busBean);
		}
		merBean.setMerBusiness(busBean);
		return merBean;
	}

	
	private MerchantBean packMerInfo(Map<String, Object> map) {
		MerchantBean merBean=new MerchantBean();

		merBean.setId((Integer) map.get("id"));
		merBean.setMobile((String) map.get("mobile"));
		merBean.setMerName((String) map.get("mer_name"));
		merBean.setProvince((String) map.get("province"));
		merBean.setCity((String) map.get("city"));
		merBean.setArea((String) map.get("area"));
		merBean.setCreateTime((Date) map.get("create_time"));
		merBean.setAgentName((String) map.get("agent_name"));
		merBean.setFreezeTime((Date) map.get("freeze_time"));
		merBean.setMdseName((String) map.get("mdse_name"));
		merBean.setStartTime((Date) map.get("start_time"));
		merBean.setEndTime((Date) map.get("end_time"));
		merBean.setMerStatus((String) map.get("mer_status"));
		merBean.setOperator((String) map.get("operator"));
		
		MerBusinessBean mbusBean=new MerBusinessBean();
		mbusBean.setCompanyName((String) map.get("company_name"));
		mbusBean.setProvince((String) map.get("province"));
		mbusBean.setCity((String) map.get("city"));
		mbusBean.setArea((String) map.get("area"));
		mbusBean.setAddress((String) map.get("address"));
		mbusBean.setCompanyPhone((String) map.get("company_phone"));
		mbusBean.setContacts((String) map.get("contacts"));
		mbusBean.setContactMobile((String) map.get("contact_mobile"));
		mbusBean.setContactTelephone((String) map.get("contact_telephone"));
		mbusBean.setQq((String) map.get("qq"));
		mbusBean.setEmail((String) map.get("email"));
		
		merBean.setMerBusiness(mbusBean);
		
		return merBean;
	}
	
	
	@Override
	public PageInfo<MerchantBean> queryMerDtailList(QueryMerBean queryBean) {
		PageInfo<MerchantBean> resultPage= new PageInfo<MerchantBean>();
		if(queryBean==null){
			 resultPage.setList(Lists.newArrayList());
             resultPage.setTotal(0);
             resultPage.setPageNum(0);
             resultPage.setPageSize(20);
             return resultPage;
		}
		
		QueryMer query =packQuery(queryBean);
		
		PageHelper.startPage(queryBean.getPage(),queryBean.getPageSize());
	    Page<Map<String, Object>> listPages =merchantMapper.queryMerDetailList(query);
	    
	    if(listPages == null){
            resultPage.setList(Lists.newArrayList());
            resultPage.setTotal(0);
            resultPage.setPageNum(0);
            resultPage.setPageSize(20);
            return resultPage;
        }
        List<Map<String, Object>> merDetailList= listPages.getResult();
        if(CollectionUtils.isEmpty(merDetailList)){
            resultPage.setList(Lists.newArrayList());
            resultPage.setTotal(listPages.getTotal());//总记录数
            resultPage.setPages(listPages.getPages());//总页数
            resultPage.setPageNum(listPages.getPageNum());//页码
            resultPage.setPageSize(listPages.getPageSize());//每页大小
            return resultPage;
        }
        
        List<MerchantBean> result = Lists.newArrayList();
        for(Map<String, Object> map:merDetailList){
        	MerchantBean merBean=packMerInfo(map);
        	result.add(merBean);
        }
	    
        resultPage.setList(result);
        resultPage.setTotal(listPages.getTotal());//总记录数
        resultPage.setPages(listPages.getPages());//总页数
        resultPage.setPageNum(listPages.getPageNum());//页码
        resultPage.setPageSize(listPages.getPageSize());//每页大小
        return resultPage;
        
	}
	
	

	@Override
	public PageInfo<MerchantBean> queryMerList(QueryMerBean queryBean) {
		
		PageInfo<MerchantBean> resultPage= new PageInfo<MerchantBean>();
		if(queryBean==null){
			 resultPage.setList(Lists.newArrayList());
             resultPage.setTotal(0);
             resultPage.setPageNum(0);
             resultPage.setPageSize(20);
             return resultPage;
		}
		
		QueryMer query = packQuery(queryBean);
		
		PageHelper.startPage(queryBean.getPage(),queryBean.getPageSize());
	    Page<Merchant> listPages =merchantMapper.queryMerList(query);
	    
	    if(listPages == null){
	            resultPage.setList(Lists.newArrayList());
	            resultPage.setTotal(0);
	            resultPage.setPageNum(0);
	            resultPage.setPageSize(20);
	            return resultPage;
        }
        List<Merchant> merDetailList= listPages.getResult();
        if(CollectionUtils.isEmpty(merDetailList)){
            resultPage.setList(Lists.newArrayList());
            resultPage.setTotal(listPages.getTotal());//总记录数
            resultPage.setPages(listPages.getPages());//总页数
            resultPage.setPageNum(listPages.getPageNum());//页码
            resultPage.setPageSize(listPages.getPageSize());//每页大小
            return resultPage;
        }
        
        List<MerchantBean> result = Lists.newArrayList();
        MerchantBean merBean=null;
        for(Merchant mer:merDetailList){
        	merBean=new MerchantBean();
        	BeanUtils.copyProperties(mer, merBean);
        	merBean.setAgentName(getAgentName(mer.getAgentId()));
        	result.add(merBean);
        }
	
        resultPage.setList(result);
        resultPage.setTotal(listPages.getTotal());//总记录数
        resultPage.setPages(listPages.getPages());//总页数
        resultPage.setPageNum(listPages.getPageNum());//页码
        resultPage.setPageSize(listPages.getPageSize());//每页大小
        return resultPage;
	}
	
	/**
	 * 封装QueryBean
	 * @param queryBean
	 * @return
	 */
	private QueryMer packQuery(QueryMerBean queryBean){
		
		QueryMer query=new QueryMer();
		BeanUtils.copyProperties(queryBean, query);
		
		if(StringUtils.isNotBlank(queryBean.getProvince())){
			String[] provinces=queryBean.getProvince().split(",");
			List<String>provinceList=Lists.newArrayList();
			for(String province:provinces){
				provinceList.add(province);
			}
			query.setProvinceList(provinceList);
		}
		if(StringUtils.isNotBlank(queryBean.getCity())){
			String[] citys=queryBean.getCity().split(",");
			List<String>cityList=Lists.newArrayList();
			for(String city:citys){
				cityList.add(city);
			}
			query.setCityList(cityList);
		}
		if(StringUtils.isNotBlank(queryBean.getArea())){
			String[] areas=queryBean.getProvince().split(",");
			List<String> areaList=Lists.newArrayList();
			for(String area:areas){
				areaList.add(area);
			}
			query.setAreaList(areaList);
		}
		
		return query;
	}
	
	
	@Transactional
	@Override
	public void freezeMer(int merId) {
		Merchant merchant =merchantMapper.selectById(merId);
		if(merchant==null || MerStatus.STATUS_WW.getCode().equals(merchant.getMerStatus()) || MerStatus.STATUS_FROZEN.getCode().equals(merchant.getMerStatus())) {
			throw new MerServiceException("商户当前状态不能进行冻结");
		}
		int num=merchantMapper.updateFreezeStatusById(merId, 
				merchant.getMerStatus(), MerStatus.STATUS_FROZEN.getCode(),new Date());
		if(num!=1){
			throw new MerServiceException("冻结商户失败");
		}
		
	}
	
	@Transactional
	@Override
	public void thawMer(int merId) {
		Merchant merchant =merchantMapper.selectById(merId);
		if(merchant==null || !MerStatus.STATUS_FROZEN.getCode().equals(merchant.getMerStatus())) {
			throw new MerServiceException("商户当前状态不能进行解冻");
		}
		int num=merchantMapper.updateFreezeStatusById(merId,  
				MerStatus.STATUS_FROZEN.getCode(),MerStatus.STATUS_ING.getCode(),null);
		if(num!=1){
			throw new MerServiceException("解冻商户失败");
		}
		
	}

	
	

	@Override
	public MerOrderBean calcUpgradeMoney(int merId, int mdseId, int saleId) {
		
		Merchant mer=merchantMapper.selectById(merId);
		if(mer==null){
			throw new MerServiceException("无效的商户");
		}
		
		if(MerStatus.STATUS_FROZEN.getCode().equals(mer.getMerStatus())|| MerStatus.STATUS_WW.getCode().equals(mer.getMerStatus())) {
			throw new MerServiceException("商户已经被冻结不能进行套餐升级");
		}
		
		MdseInfo mdseInfo=mdseInfoMapper.selectById(mdseId);
		if(mdseInfo==null){
			throw new MerServiceException("无效的商品id");
		}
		SaleDetail sale=saleDetailMapper.selectByMdseIdAndId(saleId,mdseId);
		if(sale==null){
			throw new MerServiceException("无效的商品销售id");
		}
		//商户升级套餐需消耗的费用
		BigDecimal orderPrice=sale.getSellPrice();
		
		//MerchantOrder oldOrder=merOrderMapper.selectById(mer.getOrderId());
		//根据商户id 查询当前商户最后一条购买信息
		MerchantOrder lastOrder=merOrderMapper.selectLastBymerId(merId);
		if(lastOrder==null){
			throw new MerServiceException("计算升级费用失败");
		}
		
		//判断升级的套餐是否高于当前版本套餐  TODO
		if(lastOrder.getPurchaseYear()>sale.getPurchaseYear()|| lastOrder.getSellPrice().compareTo(orderPrice)>0){
			throw new MerServiceException("升级的套餐需高于当前版本套餐");
		}
		Date endTime=null;
		Date currentTime=new Date();
		//购买年限+赠送年限
		int addYear=sale.getPurchaseYear()+sale.getGiveYear();

		//最后一笔订单处于未生效
		if(lastOrder.getOrderStatus().equals(MerOrderStatus.STATUS_WW.getCode())) {
			//查询出商户当前订单
			MerchantOrder curOrder=merOrderMapper.selectById(mer.getOrderId());
			if(!MerOrderStatus.STATUS_ING.getCode().equals(curOrder.getOrderStatus())) {
				logger.error("当前商户套餐有异常，不能升级 curOrder= "+curOrder);
				throw new MerServiceException("当前商户套餐有异常，不能升级");
			}
			//计算商户购买套餐结束时间（不包含赠送时间） 
			Date curEndTime=calcExpireTime_Year(currentTime, curOrder.getPurchaseYear());
			
			//剩余天数
			int spareDay= getDiffDate(new Date(), curEndTime, Calendar.DAY_OF_MONTH);
			if(spareDay>0) {
				//当前套餐总天数
				int totalDay= getDiffDate(curOrder.getStartTime(), curEndTime, Calendar.DAY_OF_MONTH);
				//计算剩余费用，(总价/总天数*剩余天数）)
				BigDecimal spareMoney=curOrder.getSellPrice().divide(new BigDecimal(totalDay),2, RoundingMode.HALF_UP).multiply(new BigDecimal(spareDay));
				
				//计算升级套餐不包含赠送年限的结束时间
				Date upgradeEndTime=calcExpireTime_Year(currentTime, sale.getPurchaseYear());
				//计算升级总天数
				int upgradeDays=getDiffDate(currentTime, upgradeEndTime, Calendar.DAY_OF_MONTH);
				 
				//剩余未使用的时间 差价 (总价/升级套餐总天数*剩余天数
				BigDecimal diffPrice=orderPrice.divide(new BigDecimal(upgradeDays),2, RoundingMode.HALF_UP).multiply(new BigDecimal(spareDay));
				
				//总售价+剩余时间使用升级套餐差价-当前套餐未使用金额 -减去未使用的续期套餐费用
				orderPrice=orderPrice.add(diffPrice).subtract(spareMoney).subtract(lastOrder.getSellPrice());
				
				//套餐结束时间
				endTime=calcExpireTime_Year(currentTime, addYear);
				
				//加上当前为使用时间
				endTime=calcExpireTime_DAY(endTime, spareDay);
			}
			
		}else if(lastOrder.getOrderStatus().equals(MerOrderStatus.STATUS_ING.getCode())) { 
			//计算商户购买套餐结束时间（不包含赠送时间） 
			Date curEndTime=calcExpireTime_Year(currentTime, lastOrder.getPurchaseYear());
			//剩余天数
			int spareDay = getDiffDate(new Date(), curEndTime, Calendar.DAY_OF_MONTH);
			if(spareDay>0) {
				//当前套餐总天数
				int totalDay= getDiffDate(lastOrder.getStartTime(), curEndTime, Calendar.DAY_OF_MONTH);
				//计算剩余费用，(总价/总天数*剩余天数）)
				BigDecimal spareMoney=lastOrder.getSellPrice().divide(new BigDecimal(totalDay),2, RoundingMode.HALF_UP).multiply(new BigDecimal(spareDay));
		
				//总售价-当前套餐未使用金额
				orderPrice=orderPrice.subtract(spareMoney);
				
				//套餐结束时间
				endTime=calcExpireTime_Year(currentTime, addYear);
				
				
			}else {
				
				//套餐结束时间
				endTime=calcExpireTime_Year(currentTime, addYear);
				
				
			}
		}
		
		AgentDetailBean agentBean=agentDetailService.queryBalance(mer.getAgentId());
		if(agentBean==null){
			throw new MerServiceException("代理商已被冻结，不能进行商户升级。");
		}
		//代理商折扣
		BigDecimal discount=agentBean.getDiscount();
		//代理商需消耗费用
		BigDecimal consumePrice=orderPrice.multiply(discount);
	
		MerOrderBean merOrderBean=new MerOrderBean();
		
		merOrderBean.setConsumePrice(consumePrice);
		merOrderBean.setStartTime(currentTime);
		merOrderBean.setEndTime(endTime);
		merOrderBean.setSellPrice(orderPrice);
		
		return merOrderBean;
	}
	
	
	
	
	/**
	 * 升级
	 */
	 @Transactional(propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public void upgradeMer(MerOrderBean order) {
		
		try {
			
			if(order==null) {
				throw new MerServiceException("无效的参数");
			}
			
			
			Merchant mer=merchantMapper.selectById(order.getMerId());
			if(mer==null){
				throw new MerServiceException("无效的商户");
			}
			
			Integer mdseId=order.getMdseId();
			Integer saleId=order.getSaleId();
			
			MdseInfo mdseInfo=mdseInfoMapper.selectById(mdseId);
			if(mdseInfo==null){
				throw new MerServiceException("无效的商品id");
			}
			SaleDetail sale=saleDetailMapper.selectByMdseIdAndId(saleId,order.getMdseId());
			if(sale==null){
				throw new MerServiceException("无效的商品销售id");
			}
			
			Integer merId=order.getMerId();
			
			
			MerOrderBean merOrderBean=calcUpgradeMoney(merId, order.getMdseId(), order.getSaleId());
			
			MerchantOrder oldOrder=merOrderMapper.selectById(mer.getOrderId());
	
			Date currentTime=new Date();
			
			Date endTime=merOrderBean.getEndTime();
			
			
			AgentDetailBean agentBean=agentDetailService.queryBalance(mer.getAgentId());
			if(agentBean==null){
				throw new MerServiceException("代理商已被冻结，不能进行商户升级。");
			}
			BigDecimal consumePrice=merOrderBean.getConsumePrice();
			//判断代理金额
			if(agentBean.getBalance().compareTo(consumePrice)<0){
				throw new MerServiceException("代理商余额不足，请给代理商充值后再操作");
			}
			String operator=mer.getOperator();
			
			//扣代理商的钱 
			agentDetailService.deductionBalance(mer.getAgentId(), consumePrice, mer.getOrderId()+"", "商户升级  "+mer.getMobile(), operator);
			
			Date startTime=currentTime;
			//保存购买的套餐信息
			Integer orderId=saveMerchantOrder(sale, merId, mdseInfo, 
					MerOrderType.TYPE_UPGRADE.getCode(), MerOrderStatus.STATUS_ING.getCode()
					, startTime, endTime, consumePrice,operator);
			
			
			
			//将订单号更新进商户信息
			merchantMapper.updateStatusAndTimeMdseNameById(merId, MerStatus.STATUS_ING.getCode(), mdseInfo.getMdseName(), 
					mdseId, startTime, endTime, orderId);
			
			 
			String oldMdseName=oldOrder.getMdseName();
			Date   oldEndTime=oldOrder.getEndTime();
			
			if(!MerOrderStatus.STATUS_END.getCode().equals(oldOrder.getOrderStatus())) {
				//将老的订单置为结束状态
				merOrderMapper.updateEndById(mer.getOrderId(), MerOrderStatus.STATUS_END.getCode());
			}
			
			//商户是否有未使用的续期订单
			MerchantOrder renewalOrder=merOrderMapper.queryRenewalOrder(oldOrder.getMerchantId(), MerOrderType.TYPE_RENEWAL.getCode(), MerOrderStatus.STATUS_WW.getCode());
			if(renewalOrder!=null) {
				//将续期未使用的套餐订单置为已结束				
				merOrderMapper.updateEndById(renewalOrder.getOrderId(), MerOrderStatus.STATUS_END.getCode());
			}
	
			//保存套餐变更信息
			saveMerSetmeal(oldEndTime, startTime, endTime, 
					MerChangeType.TYPE_UPGRADE.getDesc(), oldMdseName, mdseInfo.getMdseName(), operator, merId);
		} catch (AgentServiceException e) {
			logger.error("商户升扣款业务异常",  e);
			throw new MerServiceException("代理商余额不足，扣款失败");
		} catch (MerServiceException e) {
			logger.error("商户升级发生业务异常",  e);
			throw new MerServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("商户升级发生系统异常",  e);
			throw new MerServiceException("升级失败");
		}
	}
	
	
	/**
	 * 续期
	 */
	@Transactional
	@Override
	public void renewalMer(MerOrderBean order) {
		try {
			Integer merId=order.getMerId();
			Merchant mer=merchantMapper.selectById(merId);
			if(mer==null){
				throw new MerServiceException("无效的商户");
			}
			if(MerStatus.STATUS_FROZEN.getCode().equals(mer.getMerStatus())|| MerStatus.STATUS_WW.getCode().equals(mer.getMerStatus())) {
				throw new MerServiceException("商户已经被冻结,不能进行套餐续期");
			}
			
			MerchantOrder lastOrder=merOrderMapper.selectLastBymerId(merId);
			
			if(MerOrderType.TYPE_RENEWAL.getCode().equals(lastOrder.getOrderType()) && MerOrderStatus.STATUS_WW.getCode().equals(lastOrder.getOrderStatus())) {
				throw new MerServiceException("商户已经有一笔未生效续期,不能进行套餐续期");
			}
			MdseInfo mdseInfo=mdseInfoMapper.selectById(order.getMdseId());
			if(mdseInfo==null){
				throw new MerServiceException("无效的商品id");
			}
			SaleDetail sale=saleDetailMapper.selectByMdseIdAndId(order.getSaleId(),order.getMdseId());
			if(sale==null){
				throw new MerServiceException("无效的商品销售id");
			}
	
			MerchantOrder oldOrder=merOrderMapper.selectById(mer.getOrderId());
			
			AgentDetailBean agentBean=agentDetailService.queryBalance(mer.getAgentId());
			if(agentBean==null){
				throw new MerServiceException("代理商已被冻结，不能开通商户。");
			}
			//代理商折扣
			BigDecimal discount=agentBean.getDiscount();
			//代理商需消耗费用
			BigDecimal consumePrice=sale.getSellPrice().multiply(discount);
			//判断代理金额
			if(agentBean.getBalance().compareTo(consumePrice)<0){
				throw new MerServiceException("代理商余额不足，请给代理商充值后再操作");
			}
			
			String operator=order.getOperator();
			//扣代理商的钱 
			agentDetailService.deductionBalance(mer.getAgentId(), consumePrice, mer.getOrderId()+"", "商户升级  "+mer.getMobile(), operator);
		
			
			
			Date currentTime= new Date();
			String orderType=MerOrderType.TYPE_RENEWAL.getCode();
			String orderStatus="";
			Date startTime=null;
			
			String	oldMdseName=oldOrder.getMdseName();
			Date	oldEndTime=oldOrder.getEndTime();
			
			
			//判断当前套餐是否已经到期了，到期立即启用的新的套餐
			if(MerStatus.STATUS_END.getCode().equals(mer.getMerStatus())) {
				orderStatus=MerOrderStatus.STATUS_ING.getCode();
				startTime=currentTime;
			}else {
				//没有过期用当前套餐结束时间作为续期套餐的开始时间
				orderStatus=MerOrderStatus.STATUS_WW.getCode();
				startTime=oldOrder.getEndTime();
			}
			
	
			//计算续期套餐结束时间
			int addYear=sale.getPurchaseYear()+sale.getGiveYear();
			Date endTime=calcExpireTime_Year(startTime, addYear);
			
			//保存续期套餐信息
			Integer orderId=saveMerchantOrder(sale, merId, mdseInfo, orderType, 
					orderStatus, startTime, endTime, consumePrice,order.getOperator());
			
			
			//判断当前套餐是否已经到期了，到期立即启用的新的套餐
			if(MerStatus.STATUS_END.getCode().equals(mer.getMerStatus())) {
				//将订单号更新进商户信息 及状态 变更 进行中
				merchantMapper.updateStatusAndOrderIdById(merId, orderId,MerStatus.STATUS_ING.getCode());
			}
	
			//保存套餐变更信息
			saveMerSetmeal(oldEndTime, startTime, endTime,  MerChangeType.TYPE_RENEWAL.getDesc(), oldMdseName, mdseInfo.getMdseName(), operator, merId);
			
		} catch (AgentServiceException e) {
			logger.error("商户续期扣款业务异常",  e);
			throw new MerServiceException("代理商余额不足，扣款失败");
		} catch (MerServiceException e) {
			logger.error("商户续期发生业务异常",  e);
			throw new MerServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("商户续期发生系统异常",  e);
			throw new MerServiceException("续期失败");
		}
	}
	
	@Transactional
	@Override
	public void addFreeMerchant(FreeMerchantBean merInfo) {
	
		Date startTime=merInfo.getStartTime();
		Date endTime=merInfo.getEndTime();
		//新增商户信息
		Merchant mer=new Merchant();
		mer.setMobile(merInfo.getMobile());
		String pwd=DigestUtils.md5DigestAsHex("suhaodian888".getBytes());
		mer.setPwd(pwd);
		mer.setMerCode(merInfo.getMerCode());
		mer.setMerName(merInfo.getMerName());
		mer.setProvince(merInfo.getProvince());
		mer.setCity(merInfo.getCity());
		mer.setArea(merInfo.getArea());
		mer.setMerCode(merInfo.getMerCode());
		mer.setStartTime(startTime);
		mer.setEndTime(endTime);
		mer.setMdseName(merInfo.getMdseName());
		mer.setMdseId(merInfo.getMdseId());
		mer.setMerType(MerType.TYPE_FREE.getCode());
		mer.setMerStatus(MerStatus.STATUS_ING.getCode());
		mer.setCreateTime(new Date());
		mer.setOperator("system");
		Integer merId=merchantMapper.insert(mer);
		
		//插入商户企业相关信息		
		MerchantBusiness mb=new MerchantBusiness();		
		mb.setMerchantId(merId);
		merBusinessMapper.insertSelective(mb);
		
		
		//新增套餐信息
		MerchantOrder merOrder=new MerchantOrder();
		merOrder.setMdseId(-1);
		merOrder.setMdseName(FREE_MDSE_NAME);
		merOrder.setMerchantId(merId);
		Date currentTime= new Date();
		merOrder.setCreateTime(currentTime);
		merOrder.setOrderTime(currentTime);
		merOrder.setSellPrice(BigDecimal.ZERO);
		merOrder.setPurchaseYear(0);
		merOrder.setGiveYear(0);
		merOrder.setConsumePrice(BigDecimal.ZERO);
		merOrder.setOrderType(MerOrderType.TYPE_REGISTER.getCode());		
		merOrder.setOrderStatus(MerOrderStatus.STATUS_ING.getCode());
		Integer orderId=merOrderMapper.insert(merOrder);
		
		//将订单号更新进商户信息
		merchantMapper.updateOrderIdById(merId, orderId);
		
		
		//插入套餐变更数据
		saveMerSetmeal(null, startTime, endTime, MerChangeType.TYPE_REGISTER.getDesc(), "", 
				FREE_MDSE_NAME, "system", merId);
		
	}

	@Transactional
	@Override
	public void orderMer(MerOrderBean order) {
		try{
			Integer merId=order.getMerId();
			Merchant mer=merchantMapper.selectById(merId);
			if(mer==null){
				throw new MerServiceException("无效的商户");
			}
			
			if(!MerType.TYPE_FREE.getCode().equals(mer.getMerType())) {
				throw new MerServiceException("商户不是免费商户不能进行开单");
			}
			
			if(MerStatus.STATUS_FROZEN.getCode().equals(mer.getMerStatus())) {
				throw new MerServiceException("商户已经被冻结不能开单");
			}
			
			MdseInfo mdseInfo=mdseInfoMapper.selectById(order.getMdseId());
			if(mdseInfo==null){
				throw new MerServiceException("无效的商品id");
			}
			SaleDetail sale=saleDetailMapper.selectByMdseIdAndId(order.getSaleId(),order.getMdseId());
			if(sale==null){
				throw new MerServiceException("无效的商品销售id");
			}
			Integer agentId=order.getAgentId();
			if(agentId==null) {
				throw new MerServiceException("请选择代理商");
			}
			
			AgentDetailBean agentBean=agentDetailService.queryBalance(agentId);
			if(agentBean==null){
				throw new MerServiceException("代理商已被冻结，不能开通商户。");
			}
			//代理商折扣
			BigDecimal discount=agentBean.getDiscount();
			//代理商需消耗费用
			BigDecimal consumePrice=sale.getSellPrice().multiply(discount);
			//判断代理金额
			if(agentBean.getBalance().compareTo(consumePrice)<0){
				throw new MerServiceException("代理商余额不足，请给代理商充值后再操作");
			}
			String operator=order.getOperator();
			Date currentTime= new Date();
			int addYear=sale.getPurchaseYear()+sale.getGiveYear();
			Date endTime=calcExpireTime_Year(currentTime, addYear);
			//扣代理商的钱 
			agentDetailService.deductionBalance(agentId, consumePrice, mer.getOrderId()+"", "商户提单  "+mer.getMobile(), operator);
			
			
			//新增套餐记录信息
			Integer orderId=saveMerchantOrder(sale, merId, mdseInfo, 
					MerOrderType.TYPE_OPEN.getCode(), MerOrderStatus.STATUS_ING.getCode(), currentTime, endTime, consumePrice, order.getOperator());
			
			//将订单号更新进商户信息
			merchantMapper.updateStaTypeAndOrderIdById(merId
					,MerStatus.STATUS_ING.getCode(),MerType.TYPE_PAY.getCode(),agentId);
			
			merchantMapper.updateStatusAndTimeMdseNameById(merId,  MerOrderStatus.STATUS_ING.getCode(), mdseInfo.getMdseName(), 
					mdseInfo.getId(), currentTime, endTime,orderId);
			
			
			String oldMdseName=mer.getMerName();
			Date oldEndTime=mer.getEndTime();
			
	
			//保存套餐变更信息
			saveMerSetmeal(oldEndTime, currentTime, endTime, 
					MerChangeType.TYPE_OPEN.getDesc(), oldMdseName, mdseInfo.getMdseName(), operator, merId);
		} catch (AgentServiceException e) {
			logger.error("免费商户开单扣款业务异常",  e);
			throw new MerServiceException("代理商余额不足，扣款失败");
		} catch (MerServiceException e) {
			logger.error("免费商户开单发生业务异常",  e);
			throw new MerServiceException(e.getMessage());
		} catch (Exception e) {
			logger.error("免费商户开单发生系统异常",  e);
			throw new MerServiceException("商户开单失败");
		}

	}
	
	
	@Override
	public void freeMerRenewal(Integer merId ,Date endTime,String operator) {
		
		
		Merchant mer=merchantMapper.selectById(merId);
		if(mer==null){
			throw new MerServiceException("无效的商户");
		}
		if(MerStatus.STATUS_FROZEN.getCode().equals(mer.getMerStatus())|| MerStatus.STATUS_WW.getCode().equals(mer.getMerStatus())) {
			throw new MerServiceException("商户已经被冻结不能进行套餐续期");
		}
		if(mer.getEndTime().compareTo(endTime)>=1) {
			throw new MerServiceException("续期时间不能小于等于当前套餐结束时间");
		}
		MerchantOrder oldOrder=merOrderMapper.selectById(mer.getOrderId());

		Date startTime=new Date();
		
		//商户状态将更新为 进行中,和开始结束时间
		merchantMapper.updateStatusAndTimeById(merId, mer.getMerStatus(),MerOrderStatus.STATUS_ING.getCode()
				, startTime, endTime);
		
		//保存套餐续期信息		
		String	oldMdseName=oldOrder.getMdseName();
		Date	oldEndTime=oldOrder.getEndTime();
		saveMerSetmeal(oldEndTime, startTime, endTime,  MerChangeType.TYPE_RENEWAL.getDesc(),
				oldMdseName, oldMdseName, operator, merId);
		
	}
	
	
	@Override
	public void exeExpireMer() {
		//查询出所有到期但未置为到期状态订单
		List<Merchant> orderList=merchantMapper.queryExpireMer(new Date(), MerOrderStatus.STATUS_ING.getCode());
		if(orderList==null || orderList.size()==0) {
			logger.info("没有查询到需要过期的商户套餐");
			return;
		}
		List<Integer> orderIds=Lists.newArrayList();
		List<Integer> merIds=Lists.newArrayList();
		Map<Integer,Integer> merIdMap=Maps.newHashMap();
		for(Merchant mer : orderList){	
			merIds.add(mer.getId());
			orderIds.add(mer.getOrderId());
			merIdMap.put(mer.getId(), mer.getId());
		}
		
		//批量将订单置为到期
		merOrderMapper.batchOrderStatus(orderIds, MerOrderStatus.STATUS_END.getCode());
		orderIds=Lists.newArrayList();
		//查询商户是否有续期套餐
		List<MerchantOrder> renewalOrders= merOrderMapper.queryRenewalOrderList(merIds, MerOrderType.TYPE_RENEWAL.getCode(), MerOrderStatus.STATUS_WW.getCode());
		if(renewalOrders!=null && renewalOrders.size()>0){
			for(MerchantOrder order:renewalOrders){
				Integer merId=order.getMerchantId();
				//更新有续期套餐的商户套餐单号 并更新进行中
				merchantMapper.updateStatusAndTimeMdseNameById(merId, 
						MerOrderStatus.STATUS_ING.getCode(), order.getMdseName(),order.getMdseId(),
						order.getStartTime(), order.getEndTime(),order.getOrderId());
				//删除已经有续期套餐的商户
				merIdMap.remove(merId);
				orderIds.add(order.getOrderId());
			}
		}
		Set<Integer> set=merIdMap.keySet();
		if(set==null || set.size()==0){
			return;
		}
		List<Integer> merIdList=Lists.newArrayList();
		merIdList.addAll(set);
		//批量将没有续期套餐的商户置为到期
		merchantMapper.batchMerEnd(merIdList, MerOrderStatus.STATUS_END.getCode());
		
		//批量将续期订单从WW 置为进行中 ING
		merOrderMapper.batchOrderStatus(orderIds, MerOrderStatus.STATUS_ING.getCode());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 计算过期时间
	 * @return
	 */
	private  Date calcExpireTime_Year(Date currentTime,int addYear){
		Calendar calendar =Calendar.getInstance();
		calendar.add(Calendar.YEAR, addYear);
		return calendar.getTime();
	}

	
	/**
	 * 计算过期时间
	 * @return
	 */
	private  Date calcExpireTime_DAY(Date currentTime,int addDay){
		Calendar calendar =Calendar.getInstance();		
		calendar.add(Calendar.DAY_OF_MONTH, addDay);	
		return calendar.getTime();
	}



	

  /**
     * 时间相减
     * @param strDateBegin
     * @param strDateEnd
     * @param iType
     * @return
	 * @throws ParseException 
     */
    public static int getDiffDate(Date strDateBegin, Date strDateEnd, int iType) {
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(strDateBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(strDateEnd);
        long lBegin = calBegin.getTimeInMillis();
        long lEnd = calEnd.getTimeInMillis();
        if (iType == Calendar.SECOND)
            return (int) ((lEnd - lBegin) / 1000L);
        if (iType == Calendar.MINUTE)
            return (int) ((lEnd - lBegin) / 60000L);
        if (iType == Calendar.HOUR)
            return (int) ((lEnd - lBegin) / 3600000L);
        if (iType == Calendar.DAY_OF_MONTH) {
            return (int) ((lEnd - lBegin) / 86400000L);
        }
        return -1;
    }
	
	
    
	/**
	 * 保存商户信息变更记录
	 * @param beforeInfo
	 * @param afterInfo
	 * @param operator
	 * @param project
	 * @param merId
	 */
	private void saveMerInfoChange(String beforeInfo,String afterInfo,String operator,String project,Integer merId){
		Date currentTime=new Date();
		MerInfoChangeDectail dectail=new MerInfoChangeDectail();
		dectail.setAfterInfo(afterInfo);
		dectail.setBeforeInfo(beforeInfo);
		dectail.setChangeTime(currentTime);
		dectail.setMerId(merId);
		dectail.setOperator(operator);
		dectail.setProject(project);
		dectail.setCreateTime(currentTime);
		dectail.setUpdateTime(currentTime);
		merDectailMapper.insert(dectail);
	}
	
	
	
	/**
	 * 新增商户订单信息
	 * @param sale 商品销售
	 * @param merInfo 商品信息
	 * @param merId 商户id
	 * @param mdseinfo 商品信息
	 * @param orderType 类型
	 * @param orderStatus 状态
	 * @param startTime
	 * @param endTime
	 * @param consumePrice
	 * @return
	 */
	 Integer saveMerchantOrder(SaleDetail sale,
			Integer merId,MdseInfo mdseInfo,String orderType,String orderStatus
			,Date startTime,Date endTime,BigDecimal consumePrice,String operator){
		
		
		MerchantOrder merOrder=new MerchantOrder();
		merOrder.setMdseId(mdseInfo.getId());
		merOrder.setMdseName(mdseInfo.getMdseName());
		merOrder.setMerchantId(merId);
		Date currentTime= new Date();
		merOrder.setCreateTime(currentTime);
		merOrder.setOrderTime(currentTime);
		merOrder.setSellPrice(sale.getSellPrice());
		merOrder.setPurchaseYear(sale.getPurchaseYear());
		merOrder.setGiveYear(sale.getGiveYear());
		merOrder.setConsumePrice(consumePrice);
		merOrder.setOrderType(orderType);
		merOrder.setStartTime(startTime);
		merOrder.setEndTime(endTime);
		merOrder.setOrderStatus(orderStatus);
		merOrder.setOperator(operator);
		merOrder.setUpdateTime(new Date());
		merOrderMapper.insert(merOrder);		
		
		return 	merOrder.getOrderId();	
	}
	
	
	
	/**
	 * 保存套餐变更信息
	 * @param oldEndTime
	 * @param startTime
	 * @param endTime
	 * @param changeType
	 * @param oldMdseName
	 * @param newMdseName
	 * @param operator
	 * @param merId
	 */
	 void saveMerSetmeal(Date oldEndTime,Date startTime,Date newEndTime,
			String changeType,String oldMdseName,String newMdseName,
			String operator,Integer merId){
		
		//插入套餐变更数据 
		MerSetmealChange setmeal=new MerSetmealChange();
		setmeal.setBeforeTime(oldEndTime);
		setmeal.setAfterTime(newEndTime);
		setmeal.setStartTime(oldEndTime);
		setmeal.setChangeType(changeType);
		setmeal.setChangeTime(new Date());
		setmeal.setBeforeInfo(oldMdseName);
		setmeal.setAfterInfo(newMdseName);
		setmeal.setOperator(operator);
		setmeal.setMerId(merId);
		merSetmealChangeMapper.insert(setmeal);
	}
	
	
	/**
	*
	*得到代理商名称
	*/
	private String getAgentName(Integer agentId){
		
		AgentBean agentBean =agentService.loadAgentById(agentId);
		if(agentBean==null){
			return null;
		}
		return agentBean.getAgentName();
	}
	
	


	@Override
	public boolean isExistMerMobile(String mobile) {
		Merchant mer=merchantMapper.queryMerByMobile(mobile);
		if(mer!=null) {
			return true;
		}
		return false;
	}


	@Override
	public MerchantBean queryMerByMerCode(String merCode) {
		
		Merchant mer=merchantMapper.queryMerByMerCode(merCode);
		if(mer==null){
			return null;
		}
		MerchantBean merBean=new MerchantBean();
		BeanUtils.copyProperties(mer, merBean);
		return merBean;
	}


	@Override
	public MerchantBean queryMerByMerId(Integer merId) {
		Merchant mer=merchantMapper.selectById(merId);
		if(mer==null){
			return null;
		}
		MerchantBean merBean=new MerchantBean();
		BeanUtils.copyProperties(mer, merBean);
		return merBean;
	}


	@Override
	public MerOrderBean calcRenewalMoney(int merId, int mdseId, int saleId) {
		

		Merchant mer=merchantMapper.selectById(merId);
		if(mer==null){
			throw new MerServiceException("无效的商户");
		}
		
		if(MerStatus.STATUS_FROZEN.getCode().equals(mer.getMerStatus())|| MerStatus.STATUS_WW.getCode().equals(mer.getMerStatus())) {
			throw new MerServiceException("商户已经被冻结不能进行套餐升级");
		}
		
		MdseInfo mdseInfo=mdseInfoMapper.selectById(mdseId);
		if(mdseInfo==null){
			throw new MerServiceException("无效的商品id");
		}
		SaleDetail sale=saleDetailMapper.selectByMdseIdAndId(saleId,mdseId);
		if(sale==null){
			throw new MerServiceException("无效的商品销售id");
		}
		//商户升级套餐需消耗的费用
		BigDecimal orderPrice=sale.getSellPrice();
		Date endTime=null;
		Date currentTime=new Date();
		//购买年限+赠送年限
		int addYear=sale.getPurchaseYear()+sale.getGiveYear();
		//套餐结束时间
		endTime=calcExpireTime_Year(currentTime, addYear);
		AgentDetailBean agentBean=agentDetailService.queryBalance(mer.getAgentId());
		if(agentBean==null){
			throw new MerServiceException("代理商已被冻结，不能进行商户升级。");
		}
		//代理商折扣
		BigDecimal discount=agentBean.getDiscount();
		//代理商需消耗费用
		BigDecimal consumePrice=orderPrice.multiply(discount);
		
		MerOrderBean merOrderBean=new MerOrderBean();
		
		merOrderBean.setConsumePrice(consumePrice);
		merOrderBean.setStartTime(currentTime);
		merOrderBean.setEndTime(endTime);
		merOrderBean.setSellPrice(orderPrice);
		
		return merOrderBean;
	}


	@Override
	public MerOrderBean calcOrderMoney(int mdseId, int saleId, int agentId) {

		
		MdseInfo mdseInfo=mdseInfoMapper.selectById(mdseId);
		if(mdseInfo==null){
			throw new MerServiceException("无效的商品id");
		}
		SaleDetail sale=saleDetailMapper.selectByMdseIdAndId(saleId,mdseId);
		if(sale==null){
			throw new MerServiceException("无效的商品销售id");
		}
		//商户升级套餐需消耗的费用
		BigDecimal orderPrice=sale.getSellPrice();
		Date endTime=null;
		Date currentTime=new Date();
		//购买年限+赠送年限
		int addYear=sale.getPurchaseYear()+sale.getGiveYear();
		//套餐结束时间
		endTime=calcExpireTime_Year(currentTime, addYear);
		AgentDetailBean agentBean=agentDetailService.queryBalance(agentId);
		if(agentBean==null){
			throw new MerServiceException("代理商已被冻结，不能进行商户升级。");
		}
		//代理商折扣
		BigDecimal discount=agentBean.getDiscount();
		//代理商需消耗费用
		BigDecimal consumePrice=orderPrice.multiply(discount);
		
		MerOrderBean merOrderBean=new MerOrderBean();
		
		merOrderBean.setConsumePrice(consumePrice);
		merOrderBean.setStartTime(currentTime);
		merOrderBean.setEndTime(endTime);
		merOrderBean.setSellPrice(orderPrice);
		
		return merOrderBean;
	
	}

	
	/**
	 *	商户服务开通成功后 把商户初始密码，通过短信发送至商户手机号中
	 * @param mobile
	 */
	private void openMerSendSms(String mobile){
		String content="尊敬的用户您好，欢迎使用速好店系统，登录名"+mobile+"，初始密码suhaodian888，登录网址www.suhaodian.com，请尽快登录后台并修改密码";
		MsgUtil.sendSms(mobile, content);
	}
	
	
	
	
	
	
	
	
	

}

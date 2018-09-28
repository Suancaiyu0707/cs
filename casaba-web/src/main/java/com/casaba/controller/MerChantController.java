package com.casaba.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import com.casaba.agent.core.service.AgentService;
import com.casaba.aspect.SsoFilter;
import com.casaba.common.constants.SystemConstant;
import com.casaba.common.enums.MerStatus;
import com.casaba.common.enums.MerType;
import com.casaba.common.exception.MerServiceException;
import com.casaba.common.exception.MerServiceException;
import com.casaba.common.paging.PageInfo;
import com.casaba.common.result.ResultObject;
import com.casaba.common.util.RegExpValidatorUtils;
import com.casaba.dao.entity.AgentInfo;
import com.casaba.mer.bean.MdseInfoBean;
import com.casaba.mer.bean.MerBusinessBean;
import com.casaba.mer.bean.MerInfoChangeBean;
import com.casaba.mer.bean.MerOrderBean;
import com.casaba.mer.bean.MerSetmealChangeBean;
import com.casaba.mer.bean.MerchantBean;
import com.casaba.mer.bean.QueryMerBean;
import com.casaba.mer.service.IMdseService;
import com.casaba.mer.service.IMerChangeInfoService;
import com.casaba.mer.service.IMerchantService;
import com.casaba.util.CSVUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/***
 * 商户管理
 * @author tqlei
 */
@RestController
@RequestMapping("hq/mer")
public class MerChantController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(MerChantController.class);
    @Autowired
    private IMerchantService merchantService ;
    @Autowired
    private IMdseService mdseService;
    @Autowired
    private IMerChangeInfoService merChangeInfoService;

    @Autowired
    private AgentService agentService;

    /**
     	* 查询商户列表
     * @param request
     * @param response
     * @return
     */
    @SsoFilter(isDoFilter = true)
    @RequestMapping("/merList")
    public Object queryMerList(HttpServletRequest request,HttpServletResponse response,
    		 @RequestBody QueryMerBean queryMerBean){

    	 if(queryMerBean==null || queryMerBean.getPage()<1){
    		 queryMerBean = new QueryMerBean();
    		 queryMerBean.setPage(1);
    		 queryMerBean.setPageSize(20);
         }
    	 
    	queryMerBean.setMerType(MerType.TYPE_PAY.getCode());
        PageInfo<MerchantBean> pageResult = null;
        try{
            pageResult = merchantService.queryMerList(queryMerBean);
            
        }catch(Exception e){
            logger.error(ExceptionUtils.getFullStackTrace(e));
            ResultObject.setNotOK(SystemConstant.system_error);

        }
        return ResultObject.setOK(pageResult);
    }

  
    /**
	 *  	查询代理商列表
	 * @return List<MdseInfoBean>
	 */
    @SsoFilter(isDoFilter = true)
    @RequestMapping("/queryAgents")
	public Object queryAgents(HttpServletRequest request,HttpServletResponse response){
		
    	try{

        	List<AgentInfo> list=agentService.selectAgents();
    		
        	return ResultObject.setOK(list);
        }catch (MerServiceException be){
            logger.error("queryAgents mer fail,e={}",be.getErrorMsg());
            return ResultObject.setNotOK(be.getErrorMsg());
        }catch (Exception e){
            logger.error("queryAgents mer fail,e={}",e);
            return ResultObject.setNotOK(SystemConstant.system_error);
        }
    	
    	
    	
	}
    
    
    /**
     *	 查询商品销售列表，用于查询（不包含下面的销售信息）
     * @return List<MdseInfoBean>
     */
    @SsoFilter(isDoFilter = true)
    @RequestMapping("/mdseList")
    public Object queryMdseList(HttpServletRequest request,HttpServletResponse response){
    	
    	
    	try{
    		
        	List<MdseInfoBean> list=mdseService.queryMdseList();
        	return ResultObject.setOK(list);
        }catch (MerServiceException be){
            logger.error("queryMdseList mer fail,e={}",be.getErrorMsg());
            return ResultObject.setNotOK(be.getErrorMsg());
        }catch (Exception e){
            logger.error("queryMdseList mer fail,e={}",e);
            return ResultObject.setNotOK(SystemConstant.system_error);
        }
    }
    
    
    
    
    /**
	 * 查询商品销售列表及下面的销售信息
	 * @return List<MdseInfoBean>
	 */
    @SsoFilter(isDoFilter = true)
    @RequestMapping("/mdseSaleList")
	public Object queryMdseSaleList(HttpServletRequest request,HttpServletResponse response){
		
    	  try{
    		  List<MdseInfoBean> list=mdseService.queryMdseSaleList();
    	      return ResultObject.setOK(list);
          }catch (MerServiceException be){
              logger.error("queryMdseSaleList mer fail,e={}",be.getErrorMsg());
              return ResultObject.setNotOK(be.getErrorMsg());
          }catch (Exception e){
              logger.error("queryMdseSaleList mer fail,e={}",e);
              return ResultObject.setNotOK(SystemConstant.system_error);
          }
	}
    
    
    
    /***
     *	计算新建商户开单费用
     * @param request
     * @param response
     * @param merId 商户id
     * @param mdseId 商品id
     * @param saleId 商品销售id
     * @return
     */
    @SsoFilter(isDoFilter = true)
    @RequestMapping("/calcOrderMoney")
    public Object  calcOrderMoney(HttpServletRequest request,HttpServletResponse response,
            @RequestParam("mdseId") Integer mdseId,@RequestParam("saleId") Integer saleId,@RequestParam("agentId") int agentId){
    	
    	if(mdseId==null){
            logger.error("mdseId is empty");
            return ResultObject.setNotOK("商户mdseId为空");
        }
    	if(saleId==null){
            logger.error("saleId is empty");
            return ResultObject.setNotOK("商户saleId为空");
        }
    	try {
    		MerOrderBean order=merchantService.calcOrderMoney(mdseId, saleId,agentId);
    		return ResultObject.setOK(order);
    	 }catch (MerServiceException be){
             logger.error("calcRenewalMoney error,merId={},e={}", mdseId,be.getErrorMsg());
             return ResultObject.setNotOK(be.getErrorMsg());
         }catch (Exception e){
             logger.error("calcRenewalMoney error,merId={},e={}", mdseId,e);
             return ResultObject.setNotOK(SystemConstant.system_error);
         }
    	
    }
    
    
    
    
    
    
    
    
    /**
     *	  保存商户信息
     * @param request
     * @param response
     * @param merInfo
     * @return
     */
    @SsoFilter(isDoFilter = true)
    @RequestMapping("/addMer")
    public Object addMer(HttpServletRequest request, HttpServletResponse response,
                           @RequestBody MerchantBean merInfo){
      try {  
    	  String operator= getOperator(request);
    	  checkMer(merInfo,operator);
          merchantService.addMer(merInfo);            
	    }catch (MerServiceException be){
	        logger.error("add merChant fail,e={}",be.getErrorMsg());
	        return ResultObject.setNotOK(be.getErrorMsg());
	    }catch (Exception e){
	        logger.error("add merChant fail,e={}",e);
	        return ResultObject.setNotOK(SystemConstant.system_error);
	    }
	        return ResultObject.setOK(null);
    }
    
    /**
     * 	开通商户
     * @param request
     * @param response
     * @param merInfo
     * @return
     */
    @SsoFilter(isDoFilter = true)
    @RequestMapping("/openMer")
    public Object openMer(HttpServletRequest request, HttpServletResponse response,
    		 @RequestParam("merId")Integer merId){
    	try {  
    		 String operator= getOperator(request);
    		merchantService.openMer(merId, operator);    
    	}catch (MerServiceException be){
    		logger.error("openMer fail,e={}",be.getErrorMsg());
    		return ResultObject.setNotOK(be.getErrorMsg());
    	}catch (Exception e){
    		logger.error("openMer merChant fail,e={}",e);
    		return ResultObject.setNotOK(SystemConstant.system_error);
    	}
    	return ResultObject.setOK(null);
    }
    
    
    /**
     * 	保存并开通商户
     * @param request
     * @param response
     * @param merInfo
     * @return
     */
    @SsoFilter(isDoFilter = true)
    @RequestMapping("/addOpenMer")
    public Object addOpenMer(HttpServletRequest request, HttpServletResponse response,
    		@RequestBody MerchantBean merInfo){
    	try {  
    		 String operator= getOperator(request);
    		checkMer(merInfo,operator);
    		merchantService.addAndOpenMer(merInfo);     
    	}catch (MerServiceException be){
    		logger.error("add merChant fail,e={}",be.getErrorMsg());
    		return ResultObject.setNotOK(be.getErrorMsg());
    	}catch (Exception e){
    		logger.error("add merChant fail,e={}",e);
    		return ResultObject.setNotOK(SystemConstant.system_error);
    	}
    	return ResultObject.setOK(null);
    }
    
    
    private void checkMer(MerchantBean  merInfo, String operator) {
    
            if(merInfo==null){
                logger.error("merInfo is empty");
                throw new MerServiceException("请输入必填参数");
            }
            if(StringUtils.isEmpty(merInfo.getMobile())){
                logger.error("mobile is empty");
                throw new MerServiceException("手机号为空");
            }

            if(!RegExpValidatorUtils.isPhone(merInfo.getMobile())){
                logger.error("mobile is error");
                throw new MerServiceException("手机号格式有误");
            }
            if(merchantService.isExistMerMobile(merInfo.getMobile())){
                logger.error("mobile is exists");
                throw new MerServiceException("手机号已注册");
            }
            if(StringUtils.isEmpty(merInfo.getMerName())){
                logger.error("merName is empty");
                throw new MerServiceException("商户名称为空");
            }
            if(merInfo.getMdseId()==null){
                logger.error("mdseId is empty");
                throw new MerServiceException("套餐不能为空");
            }
            if(merInfo.getSaleId()==null){
                logger.error("saleId is empty");
                throw new MerServiceException("购买年限不能为空");
            }
          
            if(merInfo.getAgentId()==null){
            	logger.error("agentId is empty");
            	throw new MerServiceException("所属代理商不能为空");
            }
     
            if(StringUtils.isEmpty(merInfo.getProvince())){
                logger.error("province is empty");
                throw new MerServiceException("请选择所在区域省份");
            }
            
            if(StringUtils.isEmpty(merInfo.getCity())){
                logger.error("city is empty");
                throw new MerServiceException("请选择所在区域市");
            }
            
            if(StringUtils.isEmpty(merInfo.getArea())){
                logger.error("area is empty");
                throw new MerServiceException("请选择所在区域区");
            }
            
            MerBusinessBean  merbsBean=	merInfo.getMerBusiness();
            if(merbsBean!=null){
            	
            	 if(!StringUtils.isEmpty(merbsBean.getContacts())
                         &&RegExpValidatorUtils.IsNotChinese(merbsBean.getContacts())){
                     logger.error("contacts is error");
                     throw new MerServiceException("联系人姓名长度为6~16位字符，可以为'数字/字母/中划线/下划线'");
                 }
                 if(!StringUtils.isEmpty(merbsBean.getCompanyPhone())
                         &&!RegExpValidatorUtils.isPhone(merbsBean.getCompanyPhone())){
                     logger.error("phone is error");
                     throw new MerServiceException("联系人手机号有误");
                 }

                 if(!StringUtils.isEmpty(merbsBean.getEmail())
                         &&!RegExpValidatorUtils.isEmail(merbsBean.getEmail())){
                     logger.error("email is error");
                     throw new MerServiceException("邮箱格式有误");
                 }
            }
           merInfo.setOperator(operator); 
    }
    
    /**
     * 	变更所属代理商
     * @param request
     * @param response
     * @param merInfo
     * @return
     */
    @SsoFilter(isDoFilter = true)
    @RequestMapping("/updateMerAgent")
    public Object updateMerAgent(HttpServletRequest request, HttpServletResponse response,
    		 @RequestParam("merId")Integer merId, @RequestParam("agentId")Integer agentId){
       
        if(merId==null){
            logger.error("merName is empty");
            return ResultObject.setNotOK("商户id为空");
        }
       
 
        if(agentId==null){
        	logger.error("saleId is empty");
        	return ResultObject.setNotOK("所属代理商不能为空");
        }
        MerchantBean merInfo=new MerchantBean();
        try{
        	merInfo.setAgentId(agentId);
        	merInfo.setId(merId);
            merchantService.updateMer(merInfo);
        }catch (MerServiceException be){
            logger.error("updateMerAgent fail,e={}",be.getErrorMsg());
            return ResultObject.setNotOK(be.getErrorMsg());
        }catch (Exception e){
            logger.error("updateMerAgent fail,e={}",e);
            return ResultObject.setNotOK(SystemConstant.system_error);
        }
    	
    	return ResultObject.setOK(null);

    }
    /**
     * 	编辑商户信息
     * @param request
     * @param response
     * @param merInfo
     * @return
     */
    @SsoFilter(isDoFilter = true)
    @RequestMapping("/modifyMer")
    public Object modifyMer(HttpServletRequest request, HttpServletResponse response,
    		@RequestBody MerchantBean merInfo){
        if(merInfo==null){
            logger.error("merInfo is error");
            return ResultObject.setNotOK(SystemConstant.system_error);
        }
        if(merInfo.getId()==null){
            logger.error("merName is empty");
            return ResultObject.setNotOK("商户id为空");
        }
        if(StringUtils.isEmpty(merInfo.getMerName())){
        	logger.error("merName is empty");
        	return ResultObject.setNotOK("商户名称为空");
        }
 
        if(merInfo.getAgentId()==null){
        	logger.error("saleId is empty");
        	return ResultObject.setNotOK("所属代理商不能为空");
        }
 
        if(StringUtils.isEmpty(merInfo.getProvince())){
            logger.error("province is empty");
            return ResultObject.setNotOK("请选择所在区域省份");
        }
        
        if(StringUtils.isEmpty(merInfo.getCity())){
            logger.error("city is empty");
            return ResultObject.setNotOK("请选择所在区域市");
        }
        
        if(StringUtils.isEmpty(merInfo.getArea())){
            logger.error("area is empty");
            return ResultObject.setNotOK("请选择所在区域区");
        }
        MerBusinessBean  merbsBean=	merInfo.getMerBusiness();
        if(merbsBean!=null){
	    	 if(!StringUtils.isEmpty(merbsBean.getContacts())
	                 &&RegExpValidatorUtils.IsNotChinese(merbsBean.getContacts())){
	             logger.error("contacts is error");
	             return ResultObject.setNotOK("联系人姓名长度为6~16位字符，可以为'数字/字母/中划线/下划线'");
	         }
	         if(!StringUtils.isEmpty(merbsBean.getCompanyPhone())
	                 &&!RegExpValidatorUtils.isPhone(merbsBean.getCompanyPhone())){
	             logger.error("phone is error");
	             return ResultObject.setNotOK("联系人手机号有误");
	         }
	
	         if(!StringUtils.isEmpty(merbsBean.getEmail())
	                 &&!RegExpValidatorUtils.isEmail(merbsBean.getEmail())){
	             logger.error("email is error");
	             return ResultObject.setNotOK("邮箱格式有误");
	         }
        }
        String operator=getOperator(request);
        merInfo.setOperator(operator);
        try{
            merchantService.updateMer(merInfo);
        }catch (MerServiceException be){
            logger.error("modify mer fail,e={}",be.getErrorMsg());
            return ResultObject.setNotOK(be.getErrorMsg());
        }catch (Exception e){
            logger.error("modify mer fail,e={}",e);
            return ResultObject.setNotOK(SystemConstant.system_error);
        }
        return ResultObject.setOK(null);
    }

    /**
     * 	查询代理商详情
     * @param request
     * @param response
     * @param merId 代理商id
     * @return
     */
    @RequestMapping("/loadMerDetail")
    public Object loadMerDetail(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam("merId")Integer merId){
    	
    	 if(merId==null){
             logger.error("merId is empty");
             return ResultObject.setNotOK("商户id为空");
         }
    	
    	Map<String,Object> result = Maps.newHashMap();
    	MerchantBean merInfo= merchantService.queryMerDetail(merId);
        result.put("merInfo",merInfo);

        List<MerInfoChangeBean> merChangeInfoList=merChangeInfoService.queryMerChangeList(merId);
        //信息变更记录
        result.put("merChangeInfoList",merChangeInfoList);
    	//套餐变更记录
    	List<MerSetmealChangeBean> setmealInfoList=merChangeInfoService.querySetmealInfoList(merId);
        result.put("setmealInfoList",setmealInfoList);

        return ResultObject.setOK(result);

    }

    
	/**
	 *	 续期商户
	 */
    @RequestMapping("/renewalMer")
	public Object renewalMer(HttpServletRequest request,HttpServletResponse response,
			@RequestBody MerOrderBean order){
		
    	if(order==null) {
    		 logger.error("order is error");
             return ResultObject.setNotOK("请选择参数");
    	}
    	Integer merId= order.getMerId();
    	if(merId==null){
             logger.error("merId is empty");
             return ResultObject.setNotOK("商户id为空");
         }
    	 if(order.getMdseId()==null){
             logger.error("mdseId is empty");
             return ResultObject.setNotOK("请选择版本");
         }
    	 if(order.getSaleId()==null){
             logger.error("saleId is empty");
             return ResultObject.setNotOK("请选择年限");
         }
    	 
    	 order.setOperator(getOperator(request));
		
		 try {
		 	merchantService.renewalMer(order);
    		return ResultObject.setOK(null);
    	 }catch (MerServiceException be){
             logger.error("renewalMer error,merId={},e={}", merId,be.getErrorMsg());
             return ResultObject.setNotOK(be.getErrorMsg());
         }catch (Exception e){
             logger.error("renewalMer error,merId={},e={}", merId,e);
             return ResultObject.setNotOK(SystemConstant.system_error);
         }
	}
    
    /**
	 * 	升级商户
	 */
    @RequestMapping("/upgradeMer")
	public Object upgradeMer(HttpServletRequest request,HttpServletResponse response,
			@RequestBody MerOrderBean order){
    	 if(order==null){
             logger.error("merId is empty");
             return ResultObject.setNotOK("参数为空");
         }
    	 Integer merId= order.getMerId();
    	 if(merId==null){
             logger.error("merId is empty");
             return ResultObject.setNotOK("商户id为空");
         }
    	 if(order.getMdseId()==null){
             logger.error("mdseId is empty");
             return ResultObject.setNotOK("请选择版本");
         }
    	 if(order.getSaleId()==null){
             logger.error("saleId is empty");
             return ResultObject.setNotOK("请选择年限");
         }
    	 
    	 order.setOperator(getOperator(request));
		 
		 
		 try {
		 	merchantService.upgradeMer(order);
    		return ResultObject.setOK(null);
    	 }catch (MerServiceException be){
             logger.error("upgradeMer error,merId={},e={}", merId,be.getErrorMsg());
             return ResultObject.setNotOK(be.getErrorMsg());
         }catch (Exception e){
             logger.error("upgradeMer error,merId={},e={}", merId,e);
             return ResultObject.setNotOK(SystemConstant.system_error);
         }
		 
	}
    
	/**
	 * 续期商品查询
	 * 查询当前商户能可续期版本信息及所属的销售信息
	 * 排除当前低于商户已经购买过的套餐及年限
	 * @return List<MdseInfoBean>
	 */
    @RequestMapping("/renewalMdses")
	public Object queryRenewalMdseList(HttpServletRequest request,HttpServletResponse response,
            @RequestParam("merId") Integer merId){
		
		List<MdseInfoBean> list=mdseService.queryRenewalMdseList(merId);
		
    	return ResultObject.setOK(list);
	}
    
    
    
    

    
    /***
     *	计算套餐续期费用
     * @param request
     * @param response
     * @param merId 商户id
     * @param mdseId 商品id
     * @param saleId 商品销售id
     * @return
     */
    @RequestMapping("/calcRenewalMoney")
    public Object  calcRenewalMoney(HttpServletRequest request,HttpServletResponse response,
            @RequestParam("merId") Integer merId,@RequestParam("mdseId") Integer mdseId,@RequestParam("saleId") Integer saleId){
    	if(merId==null){
            logger.error("merId is empty");
            return ResultObject.setNotOK("商户id为空");
        }
    	if(mdseId==null){
            logger.error("mdseId is empty");
            return ResultObject.setNotOK("商户mdseId为空");
        }
    	if(saleId==null){
            logger.error("saleId is empty");
            return ResultObject.setNotOK("商户saleId为空");
        }
    	try {
    		MerOrderBean order=merchantService.calcRenewalMoney(merId, mdseId, saleId);
    		return ResultObject.setOK(order);
    	 }catch (MerServiceException be){
             logger.error("calcRenewalMoney error,merId={},e={}", merId,be.getErrorMsg());
             return ResultObject.setNotOK(be.getErrorMsg());
         }catch (Exception e){
             logger.error("calcRenewalMoney error,merId={},e={}", merId,e);
             return ResultObject.setNotOK(SystemConstant.system_error);
         }
    	
    }
    
    
	/**
	 * 升级商品查询
	 * 查询当前商户能可续期版本信息及所属的销售信息
	 * 排除当前低于商户已经购买过的套餐及年限
	 * @return List<MdseInfoBean>
	 */
    @RequestMapping("/upgradeMdses")
	public Object queryUpgradeMdseList(HttpServletRequest request,HttpServletResponse response,
            @RequestParam("merId") Integer merId){
		try {
		
	    	List<MdseInfoBean> list=mdseService.queryUpgradeMdseList(merId);
			
	    	return ResultObject.setOK(list);
	    	
	    }catch (MerServiceException be){
	        logger.error("queryUpgradeMdseList error,merId={},e={}", merId,be.getErrorMsg());
	        return ResultObject.setNotOK(be.getErrorMsg());
	    }catch (Exception e){
	        logger.error("queryUpgradeMdseList error,merId={},e={}", merId,e);
	        return ResultObject.setNotOK(SystemConstant.system_error);
	    }
	}
    
    /***
     *	计算套餐升级费用
     * @param request
     * @param response
     * @param merId 商户id
     * @return
     */
    @RequestMapping("/calcUpgradeMoney")
    public Object  calcUpgradeMoney(HttpServletRequest request,HttpServletResponse response,
            @RequestParam("merId") Integer merId,@RequestParam("mdseId") Integer mdseId,@RequestParam("saleId") Integer saleId){
    	if(merId==null){
            logger.error("merId is empty");
            return ResultObject.setNotOK("商户id为空");
        }
    	if(mdseId==null){
            logger.error("mdseId is empty");
            return ResultObject.setNotOK("商户mdseId为空");
        }
    	if(saleId==null){
            logger.error("saleId is empty");
            return ResultObject.setNotOK("商户saleId为空");
        }
    	try {
    		MerOrderBean order=merchantService.calcUpgradeMoney(merId, mdseId, saleId);
    		return ResultObject.setOK(order);
    	 }catch (MerServiceException be){
             logger.error("calcUpgradeMoney error,merId={},e={}", merId,be.getErrorMsg());
             return ResultObject.setNotOK(be.getErrorMsg());
         }catch (Exception e){
             logger.error("calcUpgradeMoney error,merId={},e={}", merId,e);
             return ResultObject.setNotOK(SystemConstant.system_error);
         }
    	
    }
    
    

    /***
     *	冻结商户
     * @param request
     * @param response
     * @param merId 商户id
     * @return
     */
    @RequestMapping("/freezeMer")
    public Object freezeMer(HttpServletRequest request,HttpServletResponse response,
                           @RequestParam("merId") Integer merId){
    	 if(merId==null){
             logger.error("merId is empty");
             return ResultObject.setNotOK("商户id为空");
         }
    	
    	try{
        	merchantService.freezeMer(merId);
            return ResultObject.setOK(null);
        }catch (MerServiceException be){
            logger.error("freezeMer error,id={},e={}", merId,be.getErrorMsg());
            return ResultObject.setNotOK(be.getErrorMsg());
        }catch (Exception e){
            logger.error("freezeMer error,merId={},e={}", merId,e);
            return ResultObject.setNotOK(SystemConstant.system_error);
        }

    }
    /***
     *	解冻商户
     * @param request
     * @param response
     * @param merId 商户id
     * @return
     */
    @RequestMapping("/unfreezeMer")
    public Object unfreezeMer(HttpServletRequest request,HttpServletResponse response,
                              @RequestParam("merId") Integer merId){
        
    	 if(merId==null){
             logger.error("merId is empty");
             return ResultObject.setNotOK("商户id为空");
         }
    	try{
        	merchantService.thawMer(merId);
            return ResultObject.setOK(null);
        }catch (MerServiceException be){
            logger.error("unfreezeMer error,merId={},e={}", merId,be.getErrorMsg());
            return ResultObject.setNotOK(be.getErrorMsg());
        }catch (Exception e){
            logger.error("unfreezeMer error,merId={},e={}", merId,e);
            return ResultObject.setNotOK(SystemConstant.system_error);
        }
    }
   
 
    
    

    

    /***
     *	 导出总部商户报表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/export")
    public void exportMer(HttpServletRequest request,HttpServletResponse response,
    		@RequestBody QueryMerBean  query) throws Exception {

    	 if(query==null || query.getPage()<1){
    		 query = new QueryMerBean();
    		 query.setPage(1);
    		 query.setPageSize(20);
         }
    	 query.setMerType(MerType.TYPE_PAY.getCode());
    	
    	PageInfo<MerchantBean> pageResult =null;
        try{
        	pageResult = merchantService.queryMerDtailList(query);

        }catch(Exception e){
            logger.error(ExceptionUtils.getFullStackTrace(e));
            ResultObject.setNotOK(SystemConstant.system_error);
            return;
        }

        
        File csvFile = createCSVFile(request,pageResult.getList());

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(csvFile.getName(), "UTF-8"));

        response.setHeader("Content-Length", String.valueOf(csvFile.length()));

        bis = new BufferedInputStream(new FileInputStream(csvFile));
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        while (true) {
            int bytesRead;
            if (-1 == (bytesRead = bis.read(buff, 0, buff.length))) break;
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();


    }

    
    
    
    
    
    /***
       *	 开始创建临时csv 
     * @param request
     * @param list 数据列表
     * @return
     * @throws Exception
     */
    private File createCSVFile(HttpServletRequest request,List<MerchantBean> list) throws Exception {
        // 设置表格头
    	 Object[] head = {"商户ID","登录手机号","商户名称","所在区域","所属代理商","当前套餐版本","付费套餐开始时间","套餐有效期",
    			 "注册/创建时间","创建人","商户状态","到期时间","冻结时间"
    	 		+ "公司名称","公司地址","公司电话","联系人姓名","联系人手机号","联系人固定电话","联系人QQ","联系人Email"};


        List<Object> headList = Arrays.asList(head);


        // 设置数据
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        List<Object> rowList = null;
        for(MerchantBean bean:list){
            rowList= Lists.newArrayList();
            rowList.add(bean.getId());//商户ID
            rowList.add(bean.getMobile());//登录手机号
            rowList.add(bean.getMerName());//商户名称
            rowList.add(bean.getProvince()+bean.getCity()+bean.getArea());//所在区域
            rowList.add(bean.getAgentName());//所属代理商
            rowList.add(bean.getMdseName());//当前套餐版本
            rowList.add(bean.getStartTime());//付费套餐开始时间
            rowList.add(bean.getEndTime());//套餐有效期
            rowList.add(bean.getCreateTime());//注册/创建时间
            rowList.add(bean.getOperator());//创建人
            rowList.add(MerStatus.getDesc(bean.getMerStatus()));//商户状态
            rowList.add(bean.getEndTime());//到期时间
            rowList.add(bean.getFreezeTime());//冻结时间
            MerBusinessBean bsbean=bean.getMerBusiness();
            rowList.add(bsbean.getCompanyName());//公司名称
            rowList.add(bsbean.getProvince()+bsbean.getCity()+bsbean.getArea()+bsbean.getAddress());//公司地址
            rowList.add(bsbean.getCompanyPhone());//公司电话
            rowList.add(bsbean.getContacts());//联系人姓名
            rowList.add(bsbean.getContactMobile());//联系人手机号
            rowList.add(bsbean.getContactTelephone());//联系人固定电话
            rowList.add(bsbean.getQq());//联系人QQ
            rowList.add(bsbean.getEmail());//联系人Email

            dataList.add(rowList);
        }

        String fileName = new String(
                ("商户列表-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())).getBytes(), "UTF-8");
        // 导出CSV文件
        File csvFile = CSVUtils.createCSVFile(headList, dataList, fileName);

        return csvFile;
    }

    //TODO     
    private String getOperator(HttpServletRequest request){
    	//this.getCurrentHqInfo(request).getHqName();
    	return "测试";
    }
    
}

package com.casaba.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang.StringUtils;


import com.casaba.aspect.SsoFilter;
import com.casaba.common.constants.SystemConstant;
import com.casaba.common.enums.MerStatus;
import com.casaba.common.enums.MerType;
import com.casaba.common.exception.BaseException;
import com.casaba.common.exception.MerServiceException;
import com.casaba.common.paging.PageInfo;
import com.casaba.common.result.ResultObject;
import com.casaba.common.util.RegExpValidatorUtils;
import com.casaba.mer.bean.FreeMerchantBean;
import com.casaba.mer.bean.MdseInfoBean;
import com.casaba.mer.bean.MerBusinessBean;
import com.casaba.mer.bean.MerOrderBean;
import com.casaba.mer.bean.MerchantBean;
import com.casaba.mer.bean.QueryMerBean;
import com.casaba.mer.service.IMdseService;
import com.casaba.mer.service.IMerchantService;
import com.casaba.util.CSVUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/***
 * 免费商户
 * @author tqlei
 */
@RestController
@RequestMapping("hq/freeMer")
public class FreeMerController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(FreeMerController.class);
    @Autowired
    private IMerchantService merchantService;

    @Autowired
    private IMdseService mdseService;
    
    /***
       *  免费商户注册
     * @param request
     * @param response
     * @param merId 商户id
     * @return
     */
    @SsoFilter(isDoFilter = false)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object freeMerRegister(HttpServletRequest request,HttpServletResponse response,
    		@RequestBody FreeMerchantBean mer){
    	
    	Map<String,Object> result = Maps.newHashMap();
		
    	try{
    		checkMerInfo(mer);
    		
    		merchantService.addFreeMerchant(mer);
    		result.put("code", "0");
	    	result.put("message", "成功");
    	}catch (MerServiceException be){
    		logger.error("freeMerRegister error,mer={},e={}", mer,be.getErrorMsg());
    		result.put("code", "0");
	    	result.put("message", be.getErrorMsg());
    	}catch (Exception e){
    		logger.error("freeMerRegister error,mer={},e={}", mer,e);
    		result.put("code", "0");
	    	result.put("message", "注册失败");
    	}
    	return result;
    	
    }
    
    
    /***
	   * 根据商户编码查询商户信息
	   * @param request
	   * @param response
	   * @param merCode 商户编码
	   * @return
	   */
	  @SsoFilter(isDoFilter = false)
	  @RequestMapping(value = "/queryMer", method = RequestMethod.POST)
	  public Object queryMer(HttpServletRequest request,HttpServletResponse response,
	  		@RequestParam("merCode") String merCode){
		  Map<String,Object> result = Maps.newHashMap();
	  	try{
	    	MerchantBean merBean=merchantService.queryMerByMerCode(merCode);
	    	result.put("code", "0");
	    	result.put("message", "");
	    	result.put("data", merBean);
	  	}catch (MerServiceException be){
	  		logger.error("queryMer error,mer={},e={}", merCode,be.getErrorMsg());
	  		result.put("code", "-1");
	    	result.put("message", "查询失败");
	  	}catch (Exception e){
	  		logger.error("queryMer error,mer={},e={}", merCode,e);
	  		result.put("code", "-1");
	    	result.put("message", "查询失败");
	  	}
	  	return result;
	  }
	  
	  
	  
	  private void checkMerInfo(FreeMerchantBean mer){
		  
		  if(mer==null){
             logger.error("mer is empty");
             throw new MerServiceException("参数为空");
         }
         if(StringUtils.isEmpty(mer.getMerCode())){
             logger.error("merCode is empty");
             throw new MerServiceException("商户编码为空");
         }
         if(StringUtils.isEmpty(mer.getMobile())){
        	 logger.error("mobile is empty");
        	 throw new MerServiceException("手机号为空");
         }

         if(!RegExpValidatorUtils.isPhone(mer.getMobile())){
             logger.error("mobile is error");
             throw new MerServiceException("手机号格式有误");
         }
         if(merchantService.isExistMerMobile(mer.getMobile())){
             logger.error("mobile is exists");
             throw new MerServiceException("手机号已注册");
         }
         if(StringUtils.isEmpty(mer.getMerName())){
             logger.error("merName is empty");
             throw new MerServiceException("商户名称为空");
         }
     
         if(StringUtils.isEmpty(mer.getMdseName())){
             logger.error("merName is empty");
             throw new MerServiceException("商品名称为空");
         }
         if(mer.getMdseId()==null){
             logger.error("mdseId is empty");
             throw new MerServiceException("套餐不能为空");
         }
    
  
         if(StringUtils.isEmpty(mer.getProvince())){
             logger.error("province is empty");
             throw new MerServiceException("请选择所在区域省份");
         }
         
         if(StringUtils.isEmpty(mer.getCity())){
             logger.error("city is empty");
             throw new MerServiceException("请选择所在区域市");
         }
         
         if(StringUtils.isEmpty(mer.getArea())){
             logger.error("area is empty");
             throw new MerServiceException("请选择所在区域区");
         }
	    	
		  
	  }
    
    /**
 	* 	查询免费商户列表
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
	 
	 queryMerBean.setMerType(MerType.TYPE_FREE.getCode());
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
	 *	 修改商户信息
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
            logger.error("merId is empty");
            return ResultObject.setNotOK("商户id为空");
        }
        if(StringUtils.isEmpty(merInfo.getMerName())){
        	logger.error("merName is empty");
        	return ResultObject.setNotOK("商户名称为空");
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
       
        merInfo.setOperator(getOperator(request));
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
     * 	查询商户详情
     * @param request
     * @param response
     * @param merId 商户id
     * @return
     */
	@SsoFilter(isDoFilter = true)
    @RequestMapping("/loadMerDetail")
    public Object loadMerDetail(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam("merId")Integer merId){
    	try {
	    	 if(merId==null){
	             logger.error("merId is empty");
	             return ResultObject.setNotOK("商户id为空");
	         }
	        
	    	MerchantBean merInfo= merchantService.queryMerDetail(merId);
	    	
	        return ResultObject.setOK(merInfo);
        
		}catch (MerServiceException be){
	        logger.error("loadMerDetail error,merId={},e={}", merId,be.getErrorMsg());
	        return ResultObject.setNotOK(be.getErrorMsg());
	    }catch (Exception e){
	        logger.error("loadMerDetail error,merId={},e={}", merId,e);
	        return ResultObject.setNotOK(SystemConstant.system_error);
	    }


    }

 
    /***
     *	免费商户续期
     * @param request
     * @param response
     * @param merId 商户id
     * @param endTime 结束时间  yyyyMMddHHmmss
     * @return
     */
    @SsoFilter(isDoFilter = true)
    @RequestMapping("/renewalMer")
    public Object renewalMer(HttpServletRequest request,HttpServletResponse response,
                   @RequestParam("merId") Integer merId, @RequestParam("endTime") String endTime){
        if(merId==null){
            logger.error("merId is empty");
            return ResultObject.setNotOK("商户id为空");
        }
        if(StringUtils.isBlank(endTime)){
            logger.error("buyYear is empty");
            return ResultObject.setNotOK("请正确选择购买年限");
        }
        try{
        	DateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
        	String operator=getOperator(request);
        	merchantService.freeMerRenewal(merId, df.parse(endTime), operator);
        }catch (MerServiceException be){
            logger.error("renewalMer error,merId={},e={}", merId,be.getErrorMsg());
            return ResultObject.setNotOK(be.getErrorMsg());
        }catch (Exception e){
            logger.error("renewalMer error,merId={},e={}", merId,e);
            return ResultObject.setNotOK(SystemConstant.system_error);
        }
        return ResultObject.setOK(null);
    }
    
    
    
    /**
       *        用户商户开单   查询商品销售列表及下面的销售信息
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
     *	计算免费商户开单费用
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
            @RequestParam("mdseId") Integer mdseId,@RequestParam("saleId") Integer saleId,@RequestParam("agentId") Integer agentId){
    	
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
    	 }catch (BaseException be){
             logger.error("calcRenewalMoney error,merId={},e={}", mdseId,be.getErrorMsg());
             return ResultObject.setNotOK(be.getErrorMsg());
         }catch (Exception e){
             logger.error("calcRenewalMoney error,merId={},e={}", mdseId,e);
             return ResultObject.setNotOK(SystemConstant.system_error);
         }
    	
    }
    
    
    
    
    
    
    
    
    
    /***
     *	免费商户开单
     * @param request
     * @param response
     * @param merId 商户id
     * @param buyYear 商户id
     * @return
     */
    @SsoFilter(isDoFilter = true)
    @RequestMapping("/orderMer")
    public Object orderMer(HttpServletRequest request,HttpServletResponse response,
			@RequestBody MerOrderBean order){
        if(order==null){
            logger.error("order is empty");
            return ResultObject.setNotOK("order为空");
        }
        if(order.getMerId()==null){
            logger.error("merId is empty");
            return ResultObject.setNotOK("请正确选择购买的版本");
        }
        if(order.getMdseId()==null){
        	logger.error("mdseId is empty");
        	return ResultObject.setNotOK("请正确选择购买的版本");
        }
        if(order.getSaleId()==null){
            logger.error("SaleId is empty");
            return ResultObject.setNotOK("请正确选择购买年限");
        }
        if(order.getAgentId()==null){
            logger.error("agentId is empty");
            return ResultObject.setNotOK("请正确选择代理商");
        }
        String operator=getOperator(request);
        
        order.setOperator(operator);
    	try{
        	merchantService.orderMer(order);
        }catch (MerServiceException be){
            logger.error("orderMer error, order={},e={}",  order,be.getErrorMsg());
            return ResultObject.setNotOK(be.getErrorMsg());
        }catch (Exception e){
            logger.error("orderMer error, order={},e={}",  order,e);
            return ResultObject.setNotOK(SystemConstant.system_error);
        }
        return ResultObject.setOK(null);
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
        }catch (BaseException be){
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
        }catch (BaseException be){
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
    	 query.setMerType(MerType.TYPE_FREE.getCode());
    	
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
       * 开始创建临时csv
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
                ("免费商户列表-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())).getBytes(), "UTF-8");
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

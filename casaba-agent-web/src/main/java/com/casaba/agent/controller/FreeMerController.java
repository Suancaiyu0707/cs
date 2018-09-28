package com.casaba.agent.controller;

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

import com.casaba.agent.aspect.SsoFilter;
import com.casaba.common.constants.SystemConstant;
import com.casaba.common.enums.MerType;
import com.casaba.common.exception.BaseException;
import com.casaba.common.exception.MerServiceException;
import com.casaba.common.paging.PageInfo;
import com.casaba.common.result.ResultObject;
import com.casaba.mer.bean.MerOrderBean;
import com.casaba.mer.bean.MerchantBean;
import com.casaba.mer.bean.QueryMerBean;
import com.casaba.mer.service.IMerchantService;

/***
 * 免费商户
 * @author tqlei
 */
@RestController
@RequestMapping("agent/freeMer")
public class FreeMerController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(FreeMerController.class);
    @Autowired
    private IMerchantService merchantService;

   
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
        if(order.getMdseId()==null){
            logger.error("merId is empty");
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
    
    
    
       
    
    //TODO     
    private String getOperator(HttpServletRequest request){
    	//this.getCurrentHqInfo(request).getHqName();
    	return "测试";
    }
}

package com.ctrip.flight.faq.soa;

import com.ctrip.flight.faq.service.TripFaqBusinessService;
import com.ctrip.framework.foundation.Foundation;
import com.ctriposs.baiji.rpc.common.types.AckCodeType;
import com.ctriposs.baiji.rpc.common.types.CheckHealthRequestType;
import com.ctriposs.baiji.rpc.common.types.CheckHealthResponseType;
import com.ctriposs.baiji.rpc.common.types.ErrorDataType;
import com.ctriposs.baiji.rpc.common.types.ResponseStatusType;
import com.ctrip.framework.spring.boot.soa.SOAService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * FAQ 服务实现
 * 该类作为SOA服务入口，类似于Controller的角色，将业务逻辑委托给TripFaqBusinessService处理
 */
@SOAService
public class TripFaqServiceImpl implements TripFaqService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripFaqServiceImpl.class);
    
    private final TripFaqBusinessService businessService;

    public TripFaqServiceImpl(TripFaqBusinessService businessService) {
        this.businessService = businessService;
    }

    @Override
    public GetQuestionsResponseType getQuestions(GetQuestionsRequestType request) throws Exception {
        LOGGER.info("Received FAQ request for affiliateCode: {}, brand: {}, locale: {}, currency: {}", 
                   request.getAffiliateCode(), request.getBrand(), request.getLocale(), request.getCurrency());
        
        // 参数验证
        if (request.getAffiliateCode() == null) {
            LOGGER.warn("Request rejected: affiliateCode is missing");
            return createErrorResponse("INVALID_PARAM", "affiliateCode is a mandatory parameter");
        }

        try {
            // 业务处理委托给TripFaqBusinessService
            TripFaqResponse faqData = businessService.getFaqData(
                request.getAffiliateCode(),
                request.getBrand(),
                request.getLocale(),
                request.getCurrency(),
                request.getSessionId()
            );
            
            LOGGER.info("Successfully processed FAQ request for affiliateCode: {}", request.getAffiliateCode());
            return createSuccessResponse(faqData);
            
        } catch (IllegalArgumentException e) {
            // 业务验证错误
            LOGGER.warn("Business validation error: {}", e.getMessage());
            return createErrorResponse("BUSINESS_ERROR", e.getMessage());
            
        } catch (Exception e) {
            // 系统错误
            LOGGER.error("Error processing FAQ request", e);
            return createErrorResponse("SYSTEM_ERROR", "Failed to fetch FAQ data: " + e.getMessage());
        }
    }
    
    @Override
    public CheckHealthResponseType checkHealth(CheckHealthRequestType request) throws Exception {
        LOGGER.info("Health check request received");
        CheckHealthResponseType response = new CheckHealthResponseType(createSuccessStatus());
        return response;
    }

    /**
     * 创建成功响应
     */
    private GetQuestionsResponseType createSuccessResponse(TripFaqResponse data) {
        GetQuestionsResponseType response = new GetQuestionsResponseType();
        response.setData(data);
        response.setResponseStatus(createSuccessStatus());
        return response;
    }
    
    /**
     * 创建错误响应
     */
    private GetQuestionsResponseType createErrorResponse(String errorCode, String errorMessage) {
        GetQuestionsResponseType response = new GetQuestionsResponseType();
        response.setResponseStatus(createErrorStatus(errorCode, errorMessage));
        return response;
    }

    /**
     * 创建成功状态
     */
    private ResponseStatusType createSuccessStatus() {
        ResponseStatusType status = new ResponseStatusType();
        status.setAck(AckCodeType.Success);
        status.setTimestamp(Calendar.getInstance());
        return status;
    }

    /**
     * 创建错误状态
     */
    private ResponseStatusType createErrorStatus(String errorCode, String errorMessage) {
        ResponseStatusType status = new ResponseStatusType();
        status.setAck(AckCodeType.Failure);
        
        ErrorDataType error = new ErrorDataType();
        error.setErrorCode(errorCode);
        error.setMessage(errorMessage);
        
        List<ErrorDataType> errors = new ArrayList<>();
        errors.add(error);
        status.setErrors(errors);
        
        status.setTimestamp(Calendar.getInstance());
        return status;
    }
} 
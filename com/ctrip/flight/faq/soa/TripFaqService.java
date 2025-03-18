package com.ctrip.flight.faq.soa;

import com.ctriposs.baiji.rpc.common.BaijiContract;
import com.ctriposs.baiji.rpc.common.types.CheckHealthRequestType;
import com.ctriposs.baiji.rpc.common.types.CheckHealthResponseType;

@SuppressWarnings("all")
@BaijiContract(serviceName = "TravixFaqService", serviceNamespace = "http://soa.ctrip.com/34567", codeGeneratorVersion = "BJCG-2.8.4.0")
public interface TripFaqService {

    CheckHealthResponseType checkHealth(CheckHealthRequestType request) throws Exception;


    /**
     * 获取 FAQ 问题列表
     */
    GetQuestionsResponseType getQuestions(GetQuestionsRequestType request) throws Exception;
}
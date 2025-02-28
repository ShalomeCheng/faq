package com.ctrip.flight.faq.controller;

import com.ctrip.flight.faq.model.result.QuestionsResult;
import com.ctrip.flight.faq.model.response.TravixFaqResponse;
import com.ctrip.flight.faq.model.vo.*;
import com.ctrip.flight.faq.service.TripFaqService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

/**
 * FAQ 查询控制器
 */
@RestController
@RequestMapping("/v2")
public class TripFaqController {

    private final TripFaqService tripFaqService;

    public TripFaqController(TripFaqService tripFaqService) {
        this.tripFaqService = tripFaqService;
    }

    /**
     * 获取所有 FAQ 问题
     *
     * @param brand 品牌
     * @param affiliateCode 渠道代码
     * @param locale 语言地区
     * @param currencyCode 货币代码
     * @param sessionId 会话ID（从请求头获取）
     * @return FAQ 问题列表
     */
    @GetMapping("/questions/{brand}/{affiliateCode}/{locale}/{currencyCode}")
    public CompletableFuture<ResponseEntity<?>> getAllTripQuestions(
            @PathVariable String brand,
            @PathVariable String affiliateCode,
            @PathVariable String locale,
            @PathVariable String currencyCode,
            @RequestHeader(value = "Session-Id", required = false) String sessionId) {

        var localeOption = Locale.tryCreate(locale);
        var currencyOption = Currency.tryCreate(currencyCode);
        var affiliateCodeOption = AffiliateCode.tryCreate(affiliateCode);
        var brandOption = Brand.tryCreate(brand);
        var sessionOption = SessionIdentity.tryCreate(sessionId);

        return tripFaqService.getQuestions(
                localeOption.orElseGet(Locale::getDefault),
                currencyOption.orElseGet(Currency::getDefault),
                affiliateCodeOption.orElse(null),
                brandOption.orElseGet(Brand::getDefault),
                sessionOption.orElseGet(SessionIdentity::anonymous)
        ).thenApply(result -> {
            if (result.isSuccess()) {
                return ResponseEntity.ok(result.value());
            } else {
                return ResponseEntity.internalServerError().body(result.failureMessage());
            }
        });
    }
} 
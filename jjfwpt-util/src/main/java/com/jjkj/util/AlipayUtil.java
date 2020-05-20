/**
 *
 */
package com.jjkj.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.jjkj.exception.BusinessException;
import com.jjkj.support.pay.AliPay;
import com.jjkj.support.pay.AliPayConfig;
import com.jjkj.support.pay.vo.PayResult;
import com.jjkj.support.pay.vo.RefundResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;



/**
 * 支付宝
 * @author WangJun
 * @version 2017年10月21日 下午11:59:47
 */
public final class AlipayUtil {
    private static final Logger logger = LogManager.getLogger(AlipayUtil.class);

    /**
     * 下单并获取支付二维码内容
     * @param out_trade_no 商户订单号
     * @param subject 交易主题
     * @param body 交易详情
     * @param amount 交易金额
     * @param ip 客户端IP
     * @param timeout 订单失效时间
     * @param callBack 回调地址
     * @return 支付参数
     */
    public static String precreate(String out_trade_no, String subject, String body, BigDecimal amount, String ip,
        String timeout, String callBack) {
        // 实例化客户端
        AlipayClient alipayClient = AliPayConfig.build().getAlipayClient();
        return precreate(alipayClient, out_trade_no, subject, body, amount, ip, timeout, callBack);
    }

    /**
     * 下单并获取支付二维码内容
     * @param out_trade_no 商户订单号
     * @param subject 交易主题
     * @param body 交易详情
     * @param amount 交易金额
     * @param ip 客户端IP
     * @param timeout 订单失效时间
     * @param callBack 回调地址
     * @return 支付参数
     */
    public static String precreate(AlipayClient alipayClient, String out_trade_no, String subject, String body,
        BigDecimal amount, String ip, String timeout, String callBack) {
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setSubject(subject);
        model.setBody(body);
        model.setOutTradeNo(out_trade_no);
        model.setTimeoutExpress(timeout);
        model.setTotalAmount(amount.toString());
        model.setQrCodeTimeoutExpress(timeout);
        request.setBizModel(model);
        request.setNotifyUrl(callBack);
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradePrecreateResponse response = alipayClient.sdkExecute(request);
            logger.info(response.getBody());// 就是orderString 可以直接给客户端请求，无需再做处理。
            if (!response.isSuccess()) {
                throw new RuntimeException(response.getSubMsg());
            }
            return response.getQrCode();
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 下单并获取支付签名
     * @param out_trade_no 商户订单号
     * @param subject 交易主题
     * @param body 交易详情
     * @param amount 交易金额
     * @param ip 客户端IP
     * @param timeout 订单失效时间
     * @param callBack 回调地址
     * @return 支付参数
     */
    public static String getSign(String out_trade_no, String subject, String body, BigDecimal amount, String ip,
        String timeout, String callBack) {
        // 实例化客户端
        AlipayClient alipayClient = AliPayConfig.build().getAlipayClient();
        return getSign(alipayClient, out_trade_no, subject, body, amount, ip, timeout, callBack);
    }

    /**
     * 下单并获取支付签名
     * @param out_trade_no 商户订单号
     * @param subject 交易主题
     * @param body 交易详情
     * @param amount 交易金额
     * @param ip 客户端IP
     * @param timeout 订单失效时间
     * @param callBack 回调地址
     * @return 支付参数
     */
    public static String getSign(AlipayClient alipayClient, String out_trade_no, String subject, String body,
        BigDecimal amount, String ip, String timeout, String callBack) {
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setSubject(subject);
        model.setBody(body);
        model.setOutTradeNo(out_trade_no);
        model.setTimeoutExpress(timeout);
        model.setTotalAmount(amount.toString());
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(callBack);
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            logger.info(response.getBody());// 就是orderString 可以直接给客户端请求，无需再做处理。
            if (!response.isSuccess()) {
                throw new RuntimeException(response.getSubMsg());
            }
            return response.getBody();
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<?, ?> searchTreade(String outTradeNo, String tradeNo) {
        // 实例化客户端
        AlipayClient alipayClient = AliPayConfig.build().getAlipayClient();
        return searchTreade(alipayClient, outTradeNo, tradeNo);
    }

    public static Map<?, ?> searchTreade(AlipayClient alipayClient, String outTradeNo, String tradeNo) {
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(outTradeNo);
        model.setTradeNo(tradeNo);
        request.setBizModel(model);
        Map<String, Object> result = InstanceUtil.newHashMap();
        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if (!response.isSuccess()) {
                result.put("trade_state_desc", response.getSubMsg());
                result.put("trade_state", "0");
            } else {
                Map<?, ?> body = JSON.parseObject(response.getBody(), Map.class);
                Map<?, ?> resultMap = JSON.parseObject(body.get("alipay_trade_query_response").toString());
                Object trade_status = resultMap.get("trade_status");
                if ("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {
                    Date date = DateUtil.stringToDate((String)resultMap.get("send_pay_date"));
                    result.put("time_end", date);
                    result.put("trade_no", resultMap.get("trade_no"));
                    result.put("trade_state", "1");
                } else {
                    result.put("trade_state_desc", resultMap.get("msg"));
                    result.put("trade_state", "2");
                }
            }
        } catch (AlipayApiException e) {
            logger.error("", e);
            result.put("trade_state_desc", e.getMessage());
            result.put("trade_state", "0");
        }
        return result;
    }

    /**
     * 退款
     * @param outTradeNo 订单支付时传入的商户订单号,不能和 trade_no同时为空。
     * @param tradeNo 支付宝交易号，和商户订单号不能同时为空
     * @param outRequestNo 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
     * @param refundAmount 退款金额
     * @param refundReason 退款原因
     * @return 支付参数
     */
    public static RefundResult refund(String outTradeNo, String tradeNo, String outRequestNo, BigDecimal refundAmount,
        String refundReason) {
        // 实例化客户端
        AlipayClient alipayClient = AliPayConfig.build().getAlipayClient();
        return refund(alipayClient, outTradeNo, tradeNo, outRequestNo, refundAmount, refundReason);
    }

    /**
     * 退款
     * @param outTradeNo 订单支付时传入的商户订单号,不能和 trade_no同时为空。
     * @param tradeNo 支付宝交易号，和商户订单号不能同时为空
     * @param outRequestNo 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
     * @param refundAmount 退款金额
     * @param refundReason 退款原因
     * @return 支付参数
     */
    public static RefundResult refund(AlipayClient alipayClient, String outTradeNo, String tradeNo, String outRequestNo,
                                      BigDecimal refundAmount, String refundReason) {
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(outTradeNo);
        model.setTradeNo(tradeNo);
        model.setRefundAmount(refundAmount.toString());
        model.setRefundReason(refundReason);
        model.setOutRequestNo(outRequestNo);
        request.setBizModel(model);
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            logger.info(response.getBody());
            if (!response.isSuccess()) {
                throw new RuntimeException(response.getSubMsg());
            }
            Map<?, ?> body = JSON.parseObject(response.getBody(), Map.class);
            Map<?, ?> result = JSON.parseObject(body.get("alipay_trade_refund_response").toString());
            return new RefundResult((String)result.get("trade_no"), outTradeNo, refundAmount.toString(),
                DateUtil.stringToDate((String)result.get("gmt_refund_pay")),
                "Y".equals(result.get("fund_change")) ? "1" : "2");
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 扫描支付
     * @param authCode
     * @param subject
     * @param outTradeNo
     * @param totalAmount
     * @param callBack
     * @return
     */
    public static PayResult micropay(String authCode, String subject, String outTradeNo, BigDecimal totalAmount,
                                     String callBack) {
        return micropay(AliPayConfig.build().getAlipayClient(), authCode, subject, outTradeNo, totalAmount, callBack);
    }

    /**
     * 扫描支付
     * @param alipayClient
     * @param authCode
     * @param subject
     * @param outTradeNo
     * @param totalAmount
     * @param callBack
     * @return
     */
    public static PayResult micropay(AlipayClient alipayClient, String authCode, String subject, String outTradeNo,
        BigDecimal totalAmount, String callBack) {
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        AlipayTradePayModel model = new AlipayTradePayModel();
        model.setScene("bar_code");
        model.setAuthCode(authCode);
        model.setSubject(subject);
        model.setOutTradeNo(outTradeNo);
        model.setTimeoutExpress("3m");
        model.setTotalAmount(totalAmount.toString());
        request.setBizModel(model);// 填充业务参数
        request.setNotifyUrl(callBack);
        try {
            AlipayTradePayResponse response = alipayClient.execute(request);
            logger.info(response.getBody());
            if (!response.isSuccess()) {
                throw new BusinessException(response.getSubMsg());
            } else {
                String result = AliPay.tradePay(model, callBack);
                Map<?, ?> body = JSON.parseObject(result, Map.class);
                Map<?, ?> resultMap = JSON.parseObject(body.get("alipay_trade_query_response").toString());
                Object trade_status = resultMap.get("trade_status");
                if ("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {
                    String tradeNo = (String)resultMap.get("trade_no");
                    String gmtCreate = (String)resultMap.get("gmt_create");
                    return new PayResult(tradeNo, DateUtil.stringToDate(gmtCreate),
                        (String)resultMap.get("buyer_logon_id"), "TRADE_SUCCESS".equals(trade_status) ? "1" : "2");
                } else {
                    throw new RuntimeException((String)resultMap.get("sub_msg"));
                }
            }
        } catch (AlipayApiException e) {
            throw new BusinessException("付款失败", e);
        }
    }
}

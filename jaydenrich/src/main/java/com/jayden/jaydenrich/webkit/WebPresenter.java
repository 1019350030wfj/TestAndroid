package com.jayden.jaydenrich.webkit;

import android.webkit.JavascriptInterface;

import com.jayden.jaydenrich.utils.LogUtils;


/**
 * 这个只是一个例子
 * Created by Jayden on 2016/3/28.
 * Email : 1570713698@qq.com
 */
public class WebPresenter extends AbsWebManager {

    @Override
    public BaseJSBridge getBridge(BaseWebViewListener listener) {
        return new JSBridge(listener);
    }

    //回调给fragment或者activity
    public interface WebViewListener extends BaseWebViewListener {

        void  contactCustom(String orderId);//联系客服

        void goPay(String orderId, String img, String title, String price); //去付款

        void applyRefund(String status, String orderId);//申请退款

        void cancelBook(String orderId); //取消预约

        void howConsume(String id, String type); //如何消费

        void dialPhone(String phone);//拨打电话
    }

    //这边是与h5交互的方法
    public static class JSBridge extends BaseJSBridge{

        WebViewListener listener;

        public JSBridge(BaseWebViewListener listener) {
            this.listener = (WebViewListener)listener;
        }

        /**
         * 拨打电话
         * @param phone
         */
        @JavascriptInterface
        public void dialPhone(String phone) {
            LogUtils.d("jayden","dialPhone phone="+ phone);
            if (listener != null) {
                listener.dialPhone(phone);
            }
        }

        /**
         * 联系客服
         * @param orderId
         */
        @JavascriptInterface
        public void contactCustom(String orderId) {
            LogUtils.d("jayden","contactCustom oderId="+ orderId);
            if (listener != null) {
                listener.contactCustom(orderId);
            }
        }

        /**
         * 去付款
         * @param orderId
         */
        @JavascriptInterface
        public void goPay(String orderId,String img,String title,String price) {
            LogUtils.d("jayden","goPay oderId="+ orderId);
            if (listener != null) {
                listener.goPay(orderId,img,title,price);
            }
        }

        /**
         * 申请退款
         * @param orderId
         */
        @JavascriptInterface
        public void applyRefund(String status,String orderId) {
            LogUtils.d("jayden","applyRefund status="+ status + "orderId = " + orderId);
            if (listener != null) {
                listener.applyRefund(status,orderId);
            }
        }

        /**
         * 取消预约
         * @param orderId
         */
        @JavascriptInterface
        public void cancelBook( String orderId) {
            LogUtils.d("jayden","cancelBook orderId = " + orderId);
            if (listener != null) {
                listener.cancelBook(orderId);
            }
        }

        /**
         * 如何消费
         * @param id
         */
        @JavascriptInterface
        public void howConsume(String id,String type) {
            LogUtils.d("jayden","howConsume id="+ id);
            if (listener != null) {
                listener.howConsume(id,type);
            }
        }
    }
}

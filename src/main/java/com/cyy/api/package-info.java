/**
 * <p>包含所有需要调用网络API的类。</p>
 * 
 * 有以下类:
 * <ul>
 *  <li>CurrencyRate: 获取人民币对美元汇率<li>
 *  <li>InjectorPrice: 获取脑浆价格<li>
 *  <li>PlexPrice: 获取PLEX价格<li>
 * </ul>
 * 所有类会在默认构造方法中调用API获取相应数据。由于要调用网络API，响应时间会
 * 有点慢
 */
package com.cyy.api;
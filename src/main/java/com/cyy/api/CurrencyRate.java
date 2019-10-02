package com.cyy.api;

import java.io.IOException;
import java.util.Scanner;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * <p>汇率</p>
 * 
 * <p>获取美元对人民币汇率并通过getter方法获取。没有setter方法。</p>
 */
public class CurrencyRate {
  /**
   * 汇率
   */
  public double rate;

  /**
   * <p>默认构造函数，初始化汇率值。</p>
   * 
   * <p>目前只有美元对人民币汇率。调用 https://freecurrencyrates.com 的API获取人民币对美元汇率</p>
   */
  public CurrencyRate() {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet(
        "https://freecurrencyrates.com/api/action.php?s=fcr&iso=USD-CNY&f=USD&v=1&do=cvals&ln=zh-hans");
    CloseableHttpResponse response = null;
    try {
      response = httpClient.execute(httpGet);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    HttpEntity httpEntity = response.getEntity();
    Scanner sc = null;
    try {
      sc = new Scanner(httpEntity.getContent());
    } catch (UnsupportedOperationException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    JSONObject jsonObject = JSONObject.parseObject(sc.nextLine());
    rate = new Double(jsonObject.getString("CNY").substring(0, 8));
  }

  /**
   * 获取汇率
   * @return 美元对人民币汇率
   */
  public double getRate() {
    return rate;
  }

}
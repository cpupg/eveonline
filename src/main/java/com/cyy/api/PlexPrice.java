package com.cyy.api;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 欧服plex价格。
 * 
 * 初始化时会调用API查询欧服PLEX价格，预计国服开服后会加入国服API。
 * 
 * 类中有以下参数：
 * <ul>
 * <li>volume：订单数量
 * <li>avg：均价
 * <li>stddev：标准差，值越高表明价格浮动越高
 * <li>median：中位数
 * <li>percentile：税金
 * <li>max：最高价格
 * <li>min：最低价格
 * </ul>
 * 参数以K,V形式保存在map成员对象中
 */
public class PlexPrice {

  /**
   * 买单信息
   */
  public Map<String, String> buy;
  /**
   * 卖单信息
   */
  public Map<String, String> sell;

  /**
   * <p>构造函数，调用API获取欧服PLEX价格。</p>
   * 
   * <p>获取的价格为伏尔戈星域价格。在后续计算中分别取平均价格和最低售价来计算需要的技能注射器
   * 的数量</p>
   */
  public PlexPrice() {
    buy = new Hashtable<String, String>();
    sell = new Hashtable<String, String>();
    CloseableHttpClient httpClient = HttpClients.createDefault();
    // 市场限制在伏尔戈星域
    HttpGet httpGet = new HttpGet("http://api.evemarketer.com/ec/marketstat?typeid=44992&regionlimit=10000002");
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
    StringBuffer xml = new StringBuffer(sc.nextLine());
    xml.append(sc.nextLine());
    Document document = null;
    try {
      document = DocumentHelper.parseText(xml.toString());
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    List<Element> list = null;
    Element root = document.getRootElement();
    // API返回的XML的第三层保存了数据
    // 循环结束，list保存了买单和卖单的数据
    for (int i = 0; i < 3; i++) {
      list = root.content();
      root = list.get(0);
    }
    // 获取买单数据
    List<Element> buyData = list.get(0).content();
    for (Element var : buyData) {
      buy.put(var.getName(), var.getText());
    }
    // 获取卖单数据
    List<Element> sellData = list.get(1).content();
    for (Element var : sellData) {
      sell.put(var.getName(), var.getText());
    }
  }
  
  /**
   * 重写toString()
   */
  @Override
  public String toString() {
    return "PlexPrice [buy=" + buy + ", sell=" + sell + "]";
  }

  /**
   * 获取买单信息
   * 
   * @return 买单信息
   */
  public Map<String, String> getBuy() {
    return buy;
  }

  /**
   * 设置买单信息
   * 
   * @param buy 买单信息
   */
  public void setBuy(Map<String, String> buy) {
    this.buy = buy;
  }

  /**
   * 获取卖单信息
   * 
   * @return 卖单信息
   */
  public Map<String, String> getSell() {
    return sell;
  }

  /**
   * 设置卖单信息
   * 
   * @param sell 卖单信息
   */
  public void setSell(Map<String, String> sell) {
    this.sell = sell;
  }

}
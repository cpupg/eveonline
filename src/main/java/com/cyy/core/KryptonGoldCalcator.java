package com.cyy.core;

import java.text.DecimalFormat;
import java.util.Scanner;

import com.cyy.api.CurrencyRate;
import com.cyy.api.InjectorPrice;
import com.cyy.api.PlexPrice;
import com.cyy.skills.SkillsCalcator;


/**
 * 氪金计算器，根据输入计算氪金金额
 */
public class KryptonGoldCalcator {
  
  /**
   * <p>主函数，在程序入口运行calcator()方法即可使用功。</p>
   */
  public void calcator() {
    // 当前技能，要注入的技能，要达到的技能
    int currentSkills = 0;
    int targetSkills = 0;
    int incrementSkills = 0;
    SkillsCalcator skillsCalcator = new SkillsCalcator();
    PlexPrice plexPrice = new PlexPrice();
    InjectorPrice injectorPrice = new InjectorPrice();
    // 注入器均价/最低售价
    double injectorAverageSellPrice, injectorSellMinPrice;
    // PLEX均价/最高出价
    double plexAverageBuyPrice, plexBuyMaxPrice;
    // 注入器平均售价/最低售价
    injectorAverageSellPrice = new Double(injectorPrice.getSell().get("avg")).doubleValue();
    injectorSellMinPrice = new Double(injectorPrice.getSell().get("min")).doubleValue();
    // PLEX平均售价/最高出价
    plexAverageBuyPrice = new Double(plexPrice.getSell().get("avg")).doubleValue();
    plexBuyMaxPrice = new Double(plexPrice.getBuy().get("max")).doubleValue();
    boolean tag = true;
    Scanner input = new Scanner(System.in);
    int userInput = 0;
    while(tag) {
      System.out.println("1：注入技能x\n2:注入到技能x\n0:退出");
      userInput = input.nextInt();
      // userInput = 1;// 生产环境请使用上一行
      if(userInput == 0 ) {
        return;
      } else if(userInput > 0 && userInput < 3) {
        System.out.println("请输入当前技能");
        currentSkills = input.nextInt();
        // currentSkills = 4841296;// 生产环境请使用上一行
        System.out.println("请输入要增加的技能");
        incrementSkills = input.nextInt();
        // incrementSkills = 1500000;// 生产环境请使用上一行
        int injectorNum = skillsCalcator.calcByIncrement(currentSkills, incrementSkills);
        // 平均需要多少PLEX
        double plexAverageNum = injectorNum*injectorAverageSellPrice/plexAverageBuyPrice;
        // 最少需要多少PLEX
        double plexMinNum = injectorNum*injectorSellMinPrice/plexBuyMaxPrice;
        outputAverageCost(plexAverageNum, plexMinNum);
      } else if (userInput == 2) {
        System.out.println("请输入当前技能");
        currentSkills = input.nextInt();
        System.out.println("请输入要达到的技能");
        targetSkills = input.nextInt();
        if(targetSkills <= currentSkills) {
          System.out.println("大哥你这是抽脑浆，不是磕脑浆");
          continue;
        }
        int injectorNum = skillsCalcator.calcByIncrement(currentSkills, targetSkills);
        // 平均需要多少PLEX
        double plexAverageNum = injectorNum*injectorAverageSellPrice/plexAverageBuyPrice;
        // 最少需要多少PLEX
        double plexMinNum = injectorNum*injectorSellMinPrice/plexBuyMaxPrice;
        outputAverageCost(plexAverageNum, plexMinNum);
      } else {
        System.out.println("参数错误，请重新输入");
      }
      // tag = false;// 生产环境请删除此行
    }
    input.close();
  }

  /**
   * 计算平均氪金金额和最少氪金金额
   * @param plexAverageNum 平均购买plex数量
   * @param plexMinNum 最少购买plex数量
   */
  public void outputAverageCost(double plexAverageNum, double plexMinNum) {
    // 氪金项目
    int plex[] = {110, 240, 500, 1100, 2860, 7430, 15400};
    // 氪金列表
    double usd[] = {3.74, 7.49, 14.99, 29.99, 74.99, 212.49, 424.99};
    double cny[] = new double[7];
    // PLEX人民币均价
    double average[] = new double[7];
    // 人民币平均氪金列表
    double avgCny[] = new double[7];
    // 人民币最少氪金列表
    double minCny[] = new double[7];
    // 汇率
    double currencyRate = new CurrencyRate().getRate();
    // 平均购买数量和最少购买数量向上取整
    int averyBuyCont[] = new int[7];
    int minBuyCont[] = new int[7];
    for(int i = 0; i < 7; i++) {
      // 计算plex人民币价格和均价
      average[i] = usd[i] *currencyRate / plex[i];
      cny[i] = average[i] * plex[i];
      // 计算平均氪金和最少氪金
      avgCny[i] = plexAverageNum * usd[i] * currencyRate / plex[i];
      minCny[i] = plexMinNum * usd[i] * currencyRate / plex[i];
      // 计算平均购买数量和最少购买数量取整值
      averyBuyCont[i] = (int) Math.ceil(plexAverageNum / plex[i] );
      minBuyCont[i] = (int) Math.ceil(plexMinNum / plex[i] );
    }
    // 表头
    System.out.println("DLC\t\t110PLEX\t\t240PLEX\t\t500PLEX\t\t1100PLEX\t\t2860PLEX\t\t7430PLEX\t\t15400PLEX\t\t");
    // 格式化输出数字，保留两位小数
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###.##");
    // dlc包人民币价格
    System.out.print("价格\t\t");
    for (double var : cny) {
      System.out.print(decimalFormat.format(var) + "\t\t");
    }
    System.out.println();
    // plex人民币均价
    System.out.print("均价\t\t");
    for (double var : average) {
      System.out.print(decimalFormat.format(var) + "\t\t");
    }
    System.out.println();
    // dlc平均购买数量
    System.out.print("平均购买数量\t");
    for (int var: plex ) {
      System.out.print(decimalFormat.format(plexAverageNum / var) + "\t\t");
    }
    System.out.println();
    // 平均人民币氪金
    System.out.print("平均氪金\t");
    for (double var : avgCny) {
      System.out.print(decimalFormat.format(var) + "\t\t");
    }
    System.out.println();
    System.out.print("取整氪金\t" );
    for (int i = 0; i < 7; i++ ) {
      System.out.print(decimalFormat.format(( averyBuyCont[i] * cny[i] )) + "\t\t" );
    }
    // dlc最少购买数量
    System.out.println();
    System.out.print("最少购买数量\t");
    for (int var: plex ) {
      System.out.print(decimalFormat.format(plexMinNum / var) + "\t\t");
    }
    System.out.println();
    System.out.print("最少氪金\t");
    for (double var : minCny) {
      System.out.print(decimalFormat.format(var) + "\t\t");
    }
    System.out.println();
    System.out.print("取整氪金\t");
    for(int i = 0; i < 7; i++ ) {
      System.out.print(decimalFormat.format(( cny[i] * minBuyCont[i] )) + "\t\t" );
    }
    System.out.println();
  }

}
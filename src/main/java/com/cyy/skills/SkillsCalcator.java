package com.cyy.skills;

/**
 * <p>SkillsCalcator
 * <p>脑浆计算器
 * <p>脑浆数量取上限，并输出剩余技能点数量。
 * <p>例如：当前技能点为500W，要增加30W，目前一贯脑浆可以注入50W技能点，所以需要一罐脑浆，
 * 增加50W后，还剩30W，输出30W。
 */
public class SkillsCalcator {
  /**
   * 通过当前技能点和要增加的技能点计算脑浆数量
   * @param currentSkills 当前技能点
   * @param incrementSkills 要增加的技能点
   * @return 需要的脑浆数量
   */

  public int calcByIncrement(int currentSkills, int incrementSkills){
    int incre = 0;
    int count = 0;
    while(incrementSkills > 0 ){
      incre = getSkills(currentSkills);
      currentSkills = currentSkills + incre;
      incrementSkills = incrementSkills - incre;
      count++;
    }
    System.out.println("当前技能点:" + currentSkills);
    System.out.println("剩余技能点: " + -incrementSkills);
    return count;
  }

  /**
   * 通过当前技能和目标技能计算脑浆数量
   * @param currentSkills
   * @param targetSkills
   * @return 需要的脑浆数量
   */
  public  int caclByTarget(int currentSkills, int targetSkills) {
    int count = 0;
    int incre = 0;
    while(targetSkills - currentSkills > 0) {
      incre = getSkills(currentSkills);
      count++;
      currentSkills = currentSkills + incre;
    }
    System.out.println("当前技能点:" + currentSkills);
    System.out.println("剩余技能点：" + (currentSkills - targetSkills));
    return count;
  }

  /**
   * 根据当前技能返回注入的技能点数
   * @param currentSkills 当前技能
   * @return 注入的技能点
   */
  public int getSkills(int currentSkills) {
    if(currentSkills < 5000000) {
      return 500000;
    } else if (currentSkills < 50000000) {
      return 400000;
    } else if (currentSkills < 80000000) {
      return 300000;
    } else {
      return 150000;
    }
  }
}
package com.cyy;

import java.util.Scanner;

import com.cyy.skills.SkillsCalcator;

/**
 * Hello world!
 * line1<br/>
 * line2<br/>
 */
public class App 
{
    public static void main( String[] args )
    {
        Scanner sc = new Scanner(System.in);
        SkillsCalcator calc = new SkillsCalcator();
        System.out.println("输入参数");
        int num = calc.calcByIncrement(sc.nextInt(), sc.nextInt());
        System.out.println("脑浆数量:" + num);
        sc.close();
    }
}

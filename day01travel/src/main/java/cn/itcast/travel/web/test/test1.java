package cn.itcast.travel.web.test;

import org.junit.Test;

public class test1 {
    static String b;
    static String a=null;
    public  static void test() {
        String c="";
        System.out.println(a==b);
        System.out.println(a==c);
    }

    public static void main(String[] args) {
       // test();

        String s2 = new String("aaa");
        String s ="aaa";
        System.out.println(s==s2);

    }

}

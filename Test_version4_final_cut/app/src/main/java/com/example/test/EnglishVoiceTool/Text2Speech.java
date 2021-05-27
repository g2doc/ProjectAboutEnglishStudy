package com.example.test.EnglishVoiceTool;


/**
 * description: add in version 4.0 by zhang hao
 * 封装 Google 提供的 TTS 工具类
 * TextToSpeech 类 实现语音播报
 * TextToSpeech 是 Google 原生开发的一个类，可以将我们录入的文字内容转化成语音播报出来
 * date: 2021-05-26
 *
 */


/**
 *  学完单例模式, 当然要炫技了
 *  来个 双重检测锁 + volatile 懒汉 单例模式
 *  也称为 DCL 懒汉式单例
 */

public class Text2Speech {
    /*单例 首先构造器私有化*/
    private Text2Speech(){}

    public static Text2Speech getInstance(){
        return Inner.LASTS;
    }

    // 在静态内部类里去创建对象
    public static class Inner{
        /*LazySingle 在需要的时候才初始化实例对象*/
        private static final Text2Speech LASTS = new Text2Speech();
    }


    // 上面的 lazy 用了 static ，所以这里也要加 static
//    public static Text2Speech getInstance(){
//        if( null==lazyText2Speech) { // 保证线程安全, 只有一个实例对象生成 //
//            // 如果 lazyText2Speech 是空, 先对 对象加锁，保证只有一个
//            synchronized (Text2Speech.class) {
//                if( null==lazyText2Speech){
//                    lazyText2Speech = new Text2Speech(); // 极限情况下有问题: 不是一个原子性 操作
//                    /**
//                     * 非原子操作 new 一个对象， 3步
//                     * 1、分配 内存空间
//                     * 2、执行构造方法 ， 初始化对象
//                     * 3、把这个对象 指向 这个空间
//                     * 底层是 3 步操作
//                     * 期望的是 执行 1->2->3
//                     * 真实可能执行 1->3->2
//                     * volatile 可以禁止指令重拍
//                     *
//                     * 之后还可以 搞一个 静态内部类
//                     */
//                }
//            }
//        }
//        return lazyText2Speech;
//        依然存在风险， 因为反射的存在可以破坏单例模式 ,  从而通过反射 创建对象
//    }
    /**
     * 当前 lazySingle 只满足单线程 单例， 并发有风险
     * 多线程用 synchronized,  加锁
     *
     * 另外还有可能的问题是   指令重排序
     * new 的机器码不止一个， 由于指令重排序，可能在 未完成 实例化的时候, 对象已经不是 null 了
     * 这时候 另外一个线程就有可能 得到未完成实例化的 对象
     *
     * 什么是 原子性 操作????
     * Java 并发再多看一看 ???
     */

}

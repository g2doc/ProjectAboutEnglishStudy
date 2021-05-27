package com.example.test.EnglishVoiceTool;

/**
 *  存放 口语练习的相关篇幅
 *  数据容器采用 String[]
 */
public class EnglishArticle {
    private String[] PersonText;
    private String[] ComputerText;
    private String[] AllText;
    private String   title;
    private String[] Explain;

    private static volatile EnglishArticle HungryEnglishArticle = new EnglishArticle();

    // 错误的地方
    //public static String[] getAllText(){}   静态方法内无法获取实例的 成员变量
    /*
    * 静态成员函数不属于任何一个类对象，没有this指针，而非静态成员必须随类对象的产生而产生，所以静态成员函数”看不见”非静态成员，自然也就不能访问了
    * 但是如果静态成员函数通过引用一个对象，是可以直接访问成员的，也体现了它成员函数的特权
    * static修饰的在加载时是先于非静态的加载，也就是如果在static中如果调用了非静态的方法或变量会报错，因为此时非静态的方法和变量还不存在（可以这样理解）
所以除了加static修饰，还可以先new创建一个对象，通过对象去调用成员方法和变量。
    * */
    public String[] getAllText(){
        //return getInstance().AllText;
        //String[] temp = All
        return AllText;
    }

    public String[] getExplain(){
        return Explain;
    }

    public String[] getPersonText(){return PersonText;}

    public String[] getComputerText(){return ComputerText;}

    /**
     * 一样的, 双重检测锁 +  volatile  单例模式
     * @return EnglishArticle Object
     */
    public static EnglishArticle getInstance(){
        if( null == HungryEnglishArticle){
            synchronized (EnglishArticle.class){
                if( null == HungryEnglishArticle){
                    HungryEnglishArticle = new EnglishArticle();
                }
            }
        }
        return HungryEnglishArticle;
    }


    // 私有构造, 单例模式中的  饿汉模式 --> 类加载就初始化 实例对象 和 申请资源
    private EnglishArticle(){
        System.out.println("饿汉单例:"+"ok");
        title = "The Train Trip ";
        /* 都是 9 句话*/
        /* 可以切换角色 */
        PersonText = new String[]{ // Todd 9 句
                "Devon,I hear that you took the train across Russia",
                "Wow! That's a long way!",
                "Man, that's a cool trip. That must of cost a lot of money",
                "OK,Cool! What was the landscape like?",
                "Wow! Just wild camels?",
                "So how did you eat on this train?",
                "So was this a luxurious train or was it a pretty basic... ?",
                "Wow! Would you do it again?",
                "OK,thanks a lot Devon.",


        };    //A: Todd 8 句
        ComputerText = new String[]{  //Devon
                "Yes, I started in Moscow,travelled through Russia,Mongolia,and ended up in China",
                "Yes, it was. It took three weeks. I  did it as part of a tour with ten other people",
                "It did cost a lot of money but not a lot of people can say they have done that, and so ,I looked in..." +
                        "I researched the trip several months before I actually took it.A friend and I did it together and there was" +
                        "one other American and the rest of the people were from Switzerland.",
                "A lot of it was flat and for miles around you could see absolutely nothing, and as you got into Siberia there was " +
                        "scattered trees, and when we got into Mongolia you could see some camels every once in awhile, but besides" +
                        "that there was a whole lot of nothing",
                "Wild camels around the drinking whole. Yes, saw that more than once",
                "We stopped several times along the way and upon every platform you could buy food that local people were selling." +
                        "A lot of it consisted of dried fish and other types of Russian delicacies and a lot of noodles that you heated" +
                        "up with hot water. Every train had hot water on it on every carriage so you were always able to make noodles if" +
                        " you were desperate .",
                "It was pretty much the most  basic you could get. There were no animals on board but sometimes they were coal heated so it" +
                        " was very basic.",
                "Great question! Would I do it again? Yes,I would do it again but I would wait several years to do it.One because it does cost" +
                        " a lot of money and two because there are many other things to do.. to see.",
                "Don't mention it"


        };  //B: Devon
        AllText = new String[]{
                "[Todd ] Devon,I hear that you took the train across Russia\n"+
                "[Devon] Yes, I started in Moscow,travelled through Russia,Mongolia,and ended up in China\n"+
                "[Todd ] Wow! That's a long way!\n"+
                "[Devon] Yes, it was. It took three weeks. I  did it as part of a tour with ten other people\n"+
                "[Todd ] Man, that's a cool trip. That must of cost a lot of money\n"+
                "[Devon] It did cost a lot of money but not a lot of people can say they have done that, and so ,I looked in..." +
                        "I researched the trip several months before I actually took it.A friend and I did it together and there was" +
                        "one other American and the rest of the people were from Switzerland\n" +
                "[Todd ] OK,Cool! What was the landscape like? \n" +
                "[Devon] A lot of it was flat and for miles around you could see absolutely nothing, and as you got into Siberia there was " +
                        "scattered trees, and when we got into Mongolia you could see some camels every once in awhile, but besides" +
                        "that there was a whole lot of nothing \n" +
                "[Todd ] Wow! Just wild camels? \n" +
                "[Devon] Wild camels around the drinking whole. Yes, saw that more than once \n" +
                "[Todd ] So how did you eat on this train? \n" +
                "[Devon] We stopped several times along the way and upon every platform you could buy food that local people were selling." +
                        "A lot of it consisted of dried fish and other types of Russian delicacies and a lot of noodles that you heated" +
                        "up with hot water. Every train had hot water on it on every carriage so you were always able to make noodles if" +
                        " you were desperate .  \n" +
                "[Todd ] So was this a luxurious train or was it a pretty basic... ? \n" +
                "[Devon] It was pretty much the most  basic you could get. There were no animals on board but sometimes they were coal heated so it" +
                        " was very basic. \n" +
                "[Todd ] Wow! Would you do it again? \n" +
                "[Devon] Great question! Would I do it again? Yes,I would do it again but I would wait several years to do it.One because it does cost" +
                        " a lot of money and two because there are many other things to do.. to see. \n" +
                "[Todd ] OK,thanks a lot Devon. \n" +
                "[Devon] Don't mention it"
        };
        Explain = new String[]{
                "[Todd ] Devon, 我听说你坐火车穿越了整个俄罗斯。\n" +
                "[Devon] 是的,我从莫斯科开始，穿越俄罗斯，蒙古，最后来到中国 \n" +
                "[Todd ] 哇！那是有很长的路要走！ \n" +
                "[Devon] 是的，是的，我和其他十个人花了三个星期一起去旅行 \n" +
                "[Todd ] 伙计，这么一次很酷的旅行，一定花了很多钱。\n" +
                "[Devon] 它确实花了很多钱, 但不是很多人都可以说他们已经这样做了，所以，我看了...我在实际参加旅行前几个月就演技过这次旅行. \n" +
                        "我和一个朋友一起做的，还有一个美国人，其他人都来自瑞士\n" +
                "[Todd ] 好的，太酷了！ 风景怎么样呢？ \n" +
                "[Devon] 很多都是平地。周围几英里，你几乎什么都看不见，当你进入西伯利亚时，有散落的树木，当你进入蒙古时，每隔一段时间都可以看到一些骆驼，" +
                        "但是除此之外，很多地方什么都没有 \n" +
                "[Todd ] 哇！那是野生的骆驼吗？ \n"+
                "[Devon] 是的，野生的骆驼围绕着消息喝水，不止一次看到 \n"+
                "[Todd ] 那你在列车上吃的什么样呀？\n" +
                "[Devon] 我们沿途停了好几次，在每一个平台上，你都可以买到当地人卖的食物，其中很多包括干鱼和其他类型的俄罗斯美食以及大量的面条，你用热水加热。" +
                        "每列火车上都有热水，所以如果你绝望的话，你也总是可以做面条的 \n" +
                "[Todd ] 那么这是一列豪华的火车还是一列基本水平的火车呀？ \n" +
                "[Devon] 这几乎是你能得到的最基本的，上面没有动物，有时候是用煤来加入的，所以是基础版的列车 \n"+
                "[Todd ] 哇！那你有机会还会再乘坐一次吗？\n" +
                "[Devon] 好问题！我还会再乘坐一次吗？答案是肯定的，但是要等几年之后了，因为它毕竟要花很多钱，另外我也还有很多其他的事情要做。\n" +
                "[Todd ] 好的，非常感谢你。\n" +
                "[Devon] 千万别客气",

        };
    }




//    public static String[] getPersonText(){
//        return getInstance().PersonText;
//    }
//
//    public static String[] getComputerText(){}
//

}


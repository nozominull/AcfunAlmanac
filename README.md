AcfunAlmanac
============

Displays an almanac originally used by acfun for Android.


![image](https://raw.github.com/xuyangbill/AcfunAlmanac/master/screenshots/1.png)
![image](https://raw.github.com/xuyangbill/AcfunAlmanac/master/screenshots/2.png)
![image](https://raw.github.com/xuyangbill/AcfunAlmanac/master/screenshots/3.png)
![image](https://raw.github.com/xuyangbill/AcfunAlmanac/master/screenshots/4.png)
![image](https://raw.github.com/xuyangbill/AcfunAlmanac/master/screenshots/5.png)


```

package test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class test {

    public static class ListItem {

        private String name;
        private String good;
        private String bad;

        public ListItem(final String name, final String good, final String bad) {
            this.name = name;
            this.good = good;
            this.bad = bad;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getGood() {
            return good;
        }

        public void setGood(final String good) {
            this.good = good;
        }

        public String getBad() {
            return bad;
        }

        public void setBad(final String bad) {
            this.bad = bad;
        }
    }

    private static long rnd(final long a, final long b) {
        long n = a % 11117;
        for (long i = 0; i < 25 + b; i++) {
            n = n * n;
            n = n % 11117;
        }
        return n;
    }

    public static void main(final String[] args) {
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        final long seed = calendar.get(Calendar.YEAR) * 37621
                + (calendar.get(Calendar.MONTH) + 1) * 539
                + calendar.get(Calendar.DATE);

        final ArrayList<ListItem> list = new ArrayList<>();
        list.add(new ListItem("认真读书", "好好学习，天天向上", "你在看啥？"));
        list.add(new ListItem("组模型", "今天的喷漆会很完美", "消光喷多了"));
        list.add(new ListItem("投稿情感区", "问题圆满解决", "会被当事人发现"));
        list.add(new ListItem("逛文章区", "发现志同道合的老哥", "看到丧尸在晒妹"));
        list.add(new ListItem("和女神聊天", "女神好感度上升", "我去洗澡了，呵呵"));
        list.add(new ListItem("换新耳机", "守护〇〇的微笑", "蓝牙断连"));
        list.add(new ListItem("熬夜", "夜间的效率更高", "明天有很重要的事"));
        list.add(new ListItem("锻炼", "八分钟给你比利般的身材", "会拉伤肌肉"));
        list.add(new ListItem("散步", "遇到妹子主动搭讪", "走路会踩到水坑"));
        list.add(new ListItem("打排位赛", "遇到大腿上分500", "我方三人挂机"));
        list.add(new ListItem("汇报工作", "被夸奖工作认真", "上班偷玩游戏被扣工资"));
        list.add(new ListItem("抚摸猫咪", "才不是特意蹭你的呢", "死开！愚蠢的人类"));
        list.add(new ListItem("遛狗", "遇见女神遛狗搭讪", "狗狗随地大小便被罚款"));
        list.add(new ListItem("烹饪", "黑暗料理界就由我来打败", "难道这就是……仰望星空派？"));
        list.add(new ListItem("告白", "其实我也喜欢你好久了", "对不起，你是一个好人"));
        list.add(new ListItem("求站内信", "认识新老师", "再看一遍葫芦娃"));
        list.add(new ListItem("追新番", "完结之前我绝不会死", "会被剧透"));
        list.add(new ListItem("打卡日常", "怒回首页", "会被老板发现"));
        list.add(new ListItem("下副本", "配合默契一次通过", "会被灭到散团"));
        list.add(new ListItem("抢沙发", "沙发入手弹无虚发", "会被挂起来羞耻play"));
        list.add(new ListItem("网购", "商品大减价", "问题产品需要退换"));
        list.add(new ListItem("跳槽", "新工作待遇大幅提升", "再忍一忍就加薪了"));
        list.add(new ListItem("拼乐高", "顺利完工", "踩到零件"));
        list.add(new ListItem("早睡", "早睡早起方能养生", "会在半夜醒来，然后失眠"));
        list.add(new ListItem("逛街", "物美价廉大优惠", "会遇到奸商"));

        final long sg = rnd(seed, 8) % 100;
        final ArrayList<ListItem> first = new ArrayList<>();
        for (long i = 0, l = rnd(seed, 9) % 3 + 2; i < l; i++) {
            final int n = (int) (sg * 0.01 * list.size());
            final ListItem a = list.get(n);

            first.add(a);
            list.remove(n);

        }

        final long sb = rnd(seed, 4) % 100;
        final ArrayList<ListItem> second = new ArrayList<>();
        for (long i = 0, l = rnd(seed, 7) % 3 + 2; i < l; i++) {
            final int n = (int) (sb * 0.01 * list.size());
            final ListItem a = list.get(n);

            second.add(a);
            list.remove(n);
        }

        for (int i = 0; i < first.size(); i++) {
            System.out.println(first.get(i).getName() + ' ' + first.get(i).getGood());
        }
        System.out.println();
        for (int i = 0; i < second.size(); i++) {
            System.out.println(second.get(i).getName() + ' ' + second.get(i).getBad());
        }

    }
}
```

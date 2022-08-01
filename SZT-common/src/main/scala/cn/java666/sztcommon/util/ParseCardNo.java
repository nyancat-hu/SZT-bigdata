package cn.java666.sztcommon.util;

import java.util.HashMap;
import java.util.StringJoiner;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.sun.istack.internal.NotNull;
import org.junit.Test;

/**
 * @author Geek
 * @date 2020-04-15 12:21:49
 *
 * 支持自动识别明文和密文，一键互转
 *
 * 日志当中卡号脱敏字段密文反解猜想，
 * 由脱敏的密文卡号反推真实卡号，
 * 因为所有卡号密文当中没有J开头的数据，
 * 但是有A开头的数据，A != 0，
 * 所以卡号映射关系如图！！！ .file/.pic/parse_card_no.png
 * 类似摩斯电码解密。。。
 */
public class ParseCardNo {
    static HashMap<Character, Character> char2NOMap = new HashMap<>();
    static HashMap<Character, Character> NO2CharMap = new HashMap<>();

    static {
        char2NOMap.put('A', '1');
        char2NOMap.put('B', '2');
        char2NOMap.put('C', '3');
        char2NOMap.put('D', '4');
        char2NOMap.put('E', '5');
        char2NOMap.put('F', '6');
        char2NOMap.put('G', '7');
        char2NOMap.put('H', '8');
        char2NOMap.put('I', '9');
        char2NOMap.put('J', '0');

        NO2CharMap.put('1', 'A');
        NO2CharMap.put('2', 'B');
        NO2CharMap.put('3', 'C');
        NO2CharMap.put('4', 'D');
        NO2CharMap.put('5', 'E');
        NO2CharMap.put('6', 'F');
        NO2CharMap.put('7', 'G');
        NO2CharMap.put('8', 'H');
        NO2CharMap.put('9', 'I');
        NO2CharMap.put('0', 'J');
    }

    public static String parse(@NotNull String no) {
        if (StrUtil.isBlank(no)) {
            //如果给定的字符串为空或仅包含空格代码点，则此方法返回 true ，否则返回 false 。
            return "无效卡序列";
        }

        char[] array = no.toCharArray();
        StringJoiner joiner = new StringJoiner("");
        for (char c : array) {
            if (NumberUtil.isNumber(no)) {
                // 明文转密文
                String v = NO2CharMap.get(c).toString();
                joiner.add(v);
            } else {
                // 密文转明文
                String v = char2NOMap.get(c).toString();
                joiner.add(v);
            }
        }
        return joiner.toString();
    }

    /** 支持自动识别明文和密文，一键互转 */
    @Test
    public void test1() {
        System.out.println(parse("FFEBFACFD"));
        System.out.println(parse("665261364"));

        System.out.println(parse("FFEBEFGAJ"));
        System.out.println(parse("665256710"));
        System.out.println(parse("\n"));
    }
}

/*
665261364
FFEBFACFD
665256710
FFEBEFGAJ
滚！！！
*/

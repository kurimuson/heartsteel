package com.crimson.app.heartsteel.util;

public class CommonUtils {

    // 生成n位数字字母混合字符串
    public static String generateCharMixedExt(int n) {
        String[] chars = {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "+", "=",
                "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")",
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
                "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
        };
        var res = new StringBuilder();
        for (var i = 0; i < n; i++) {
            var num = (int) Math.round(Math.random() * n);
            res.append(chars[num]);
        }
        return res.toString();
    }

}

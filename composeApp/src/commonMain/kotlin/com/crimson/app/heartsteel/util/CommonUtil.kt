package com.crimson.app.heartsteel.util

object CommonUtil {

    /**
     * 生成包含数字、符号和大小写字母的随机字符串
     * @param n 生成的字符串长度
     * @return 随机字符串
     */
    fun generateCharMixedExt(n: Int): String {
        // 移除nullability，使用Kotlin风格的数组定义
        val chars = arrayOf(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "+", "=",
            "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        )

        val res = StringBuilder()
        // 使用Kotlin标准库的随机函数，确保跨平台兼容性
        repeat(n) {
            // 修复原代码的越界问题：基于chars长度生成索引
            val index = (chars.indices).random()
            res.append(chars[index])
        }
        return res.toString()
    }

    /**
     * 将毫秒数转换为 "MM:SS" 格式的时间字符串
     * @param millis 毫秒数（可为负数，会自动转为0）
     * @return 格式化后的时间字符串，例如 "02:30"、"00:05"
     */
    fun formatMillisToTime(millis: Int): String {
        // 转换为总秒数，确保非负
        val totalSeconds = (millis / 1000).coerceAtLeast(0)
        // 计算分钟和秒
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60

        // 手动实现两位数补零（跨平台兼容）
        fun Int.padZero(): String {
            return if (this < 10) "0$this" else toString()
        }
        // 拼接结果
        return "${minutes.padZero()}:${seconds.padZero()}"
    }

}

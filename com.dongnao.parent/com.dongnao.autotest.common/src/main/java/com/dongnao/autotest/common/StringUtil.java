package com.dongnao.autotest.common;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 帮助类
 * 
 * @author mrluo735
 *
 */
public final class StringUtil {
	/**
	 * Empty
	 */
	public static final String Empty = "";

	/**
	 * isNullOrEmpty
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrEmpty(String value) {
		if (null == value || value.isEmpty())
			return true;
		return false;
	}

	/**
	 * isNullOrWhiteSpace
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrWhiteSpace(String value) {
		if (null == value || value.isEmpty())
			return true;
		for (char ch : value.toCharArray()) {
			if (!Character.isWhitespace(ch))
				return false;
		}
		return true;
	}

	/**
	 * 格式化字符串
	 * 
	 * @param pattern
	 * @param arguments
	 * @return
	 */
	public static String format(String pattern, Object... arguments) {
		if (null == arguments || arguments.length < 1)
			return pattern;
		Object[] args = new String[arguments.length];
		int i = 0;
		for (Object item : arguments) {
			if (null == item)
				args[i] = null;
			else
				args[i] = String.valueOf(arguments[i]);
			i++;
		}
		return MessageFormat.format(pattern, args);
	}

	// region defaultIfNull
	/**
	 * 如果字符串是 <code>null</code> ，则返回空字符串 <code>""</code> ，否则返回字符串本身。
	 * 
	 * <pre>
	 *    StringUtil.defaultIfNull(null)  = &quot;&quot; 
	 *    StringUtil.defaultIfNull(&quot;&quot;)    = &quot;&quot; 
	 *    StringUtil.defaultIfNull(&quot;  &quot;)  = &quot;  &quot; 
	 *    StringUtil.defaultIfNull(&quot;bat&quot;) = &quot;bat&quot;
	 * </pre>
	 * 
	 * @param str
	 *            要转换的字符串
	 * 
	 * @return 字符串本身或空字符串 <code>""</code>
	 */
	public static String defaultIfNull(String str) {
		return (str == null) ? "" : str;
	}

	/**
	 * 如果字符串是 <code>null</code> ，则返回指定默认字符串，否则返回字符串本身。
	 * 
	 * <pre>
	 *    StringUtil.defaultIfNull(null, &quot;default&quot;)  = &quot;default&quot; 
	 *    StringUtil.defaultIfNull(&quot;&quot;, &quot;default&quot;)    = &quot;&quot; 
	 *    StringUtil.defaultIfNull(&quot;  &quot;, &quot;default&quot;)  = &quot;  &quot; 
	 *    StringUtil.defaultIfNull(&quot;bat&quot;, &quot;default&quot;) = &quot;bat&quot;
	 * </pre>
	 * 
	 * @param str
	 *            要转换的字符串
	 * @param defaultStr
	 *            默认字符串
	 * 
	 * @return 字符串本身或指定的默认字符串
	 */
	public static String defaultIfNull(String str, String defaultStr) {
		return (str == null) ? defaultStr : str;
	}
	// endregion

	// region trim
	/**
	 * 除去字符串头尾部的空白，如果字符串是 <code>null</code> ，依然返回 <code>null</code>。
	 * 
	 * <p>
	 * 注意，和 <code>String.trim</code> 不同，此方法使用
	 * <code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * 
	 * <pre>
	 *    StringUtil.trim(null)          = null 
	 *    StringUtil.trim(&quot;&quot;)            = &quot;&quot; 
	 *    StringUtil.trim(&quot;     &quot;)       = &quot;&quot; 
	 *    StringUtil.trim(&quot;abc&quot;)         = &quot;abc&quot; 
	 *    StringUtil.trim(&quot;    abc    &quot;) = &quot;abc&quot;
	 * </pre>
	 * </p>
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 除去空白的字符串，如果原字串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String trim(String str) {
		return trim(str, null, 0);
	}

	/**
	 * 除去字符串头尾部的指定字符，如果字符串是 <code>null</code> ，依然返回 <code>null</code>。
	 * 
	 * <pre>
	 *    StringUtil.trim(null, *)          = null 
	 *    StringUtil.trim(&quot;&quot;, *)            = &quot;&quot; 
	 *    StringUtil.trim(&quot;abc&quot;, null)      = &quot;abc&quot; 
	 *    StringUtil.trim(&quot;  abc&quot;, null)    = &quot;abc&quot; 
	 *    StringUtil.trim(&quot;abc  &quot;, null)    = &quot;abc&quot; 
	 *    StringUtil.trim(&quot; abc &quot;, null)    = &quot;abc&quot; 
	 *    StringUtil.trim(&quot;  abcyx&quot;, &quot;xyz&quot;) = &quot;  abc&quot;
	 * </pre>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为 <code>null</code> 表示除去空白字符
	 * 
	 * @return 除去指定字符后的的字符串，如果原字串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String trim(String str, String stripChars) {
		return trim(str, stripChars, 0);
	}

	/**
	 * 除去字符串头部的空白，如果字符串是 <code>null</code> ，则返回 <code>null</code>。
	 * 
	 * <p>
	 * 注意，和 <code>String.trim</code> 不同，此方法使用
	 * <code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * 
	 * <pre>
	 *    StringUtil.trimStart(null)         = null 
	 *    StringUtil.trimStart(&quot;&quot;)           = &quot;&quot; 
	 *    StringUtil.trimStart(&quot;abc&quot;)        = &quot;abc&quot; 
	 *    StringUtil.trimStart(&quot;  abc&quot;)      = &quot;abc&quot; 
	 *    StringUtil.trimStart(&quot;abc  &quot;)      = &quot;abc  &quot; 
	 *    StringUtil.trimStart(&quot; abc &quot;)      = &quot;abc &quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 除去空白的字符串，如果原字串为 <code>null</code> 或结果字符串为 <code>""</code> ，则返回
	 *         <code>null</code>
	 */
	public static String trimStart(String str) {
		return trim(str, null, -1);
	}

	/**
	 * 除去字符串头部的指定字符，如果字符串是 <code>null</code> ，依然返回 <code>null</code>。
	 * 
	 * <pre>
	 *    StringUtil.trimStart(null, *)          = null 
	 *    StringUtil.trimStart(&quot;&quot;, *)            = &quot;&quot; 
	 *    StringUtil.trimStart(&quot;abc&quot;, &quot;&quot;)        = &quot;abc&quot; 
	 *    StringUtil.trimStart(&quot;abc&quot;, null)      = &quot;abc&quot; 
	 *    StringUtil.trimStart(&quot;  abc&quot;, null)    = &quot;abc&quot; 
	 *    StringUtil.trimStart(&quot;abc  &quot;, null)    = &quot;abc  &quot; 
	 *    StringUtil.trimStart(&quot; abc &quot;, null)    = &quot;abc &quot; 
	 *    StringUtil.trimStart(&quot;yxabc  &quot;, &quot;xyz&quot;) = &quot;abc  &quot;
	 * </pre>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为 <code>null</code> 表示除去空白字符
	 * 
	 * @return 除去指定字符后的的字符串，如果原字串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String trimStart(String str, String stripChars) {
		return trim(str, stripChars, -1);
	}

	/**
	 * 除去字符串尾部的空白，如果字符串是 <code>null</code> ，则返回 <code>null</code>。
	 * 
	 * <p>
	 * 注意，和 <code>String.trim</code> 不同，此方法使用
	 * <code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
	 * 
	 * <pre>
	 *    StringUtil.trimEnd(null)       = null 
	 *    StringUtil.trimEnd(&quot;&quot;)         = &quot;&quot; 
	 *    StringUtil.trimEnd(&quot;abc&quot;)      = &quot;abc&quot; 
	 *    StringUtil.trimEnd(&quot;  abc&quot;)    = &quot;  abc&quot; 
	 *    StringUtil.trimEnd(&quot;abc  &quot;)    = &quot;abc&quot; 
	 *    StringUtil.trimEnd(&quot; abc &quot;)    = &quot; abc&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 除去空白的字符串，如果原字串为 <code>null</code> 或结果字符串为 <code>""</code> ，则返回
	 *         <code>null</code>
	 */
	public static String trimEnd(String str) {
		return trim(str, null, 1);
	}

	/**
	 * 除去字符串尾部的指定字符，如果字符串是 <code>null</code> ，依然返回 <code>null</code>。
	 * 
	 * <pre>
	 *    StringUtil.trimEnd(null, *)          = null 
	 *    StringUtil.trimEnd(&quot;&quot;, *)            = &quot;&quot; 
	 *    StringUtil.trimEnd(&quot;abc&quot;, &quot;&quot;)        = &quot;abc&quot; 
	 *    StringUtil.trimEnd(&quot;abc&quot;, null)      = &quot;abc&quot; 
	 *    StringUtil.trimEnd(&quot;  abc&quot;, null)    = &quot;  abc&quot; 
	 *    StringUtil.trimEnd(&quot;abc  &quot;, null)    = &quot;abc&quot; 
	 *    StringUtil.trimEnd(&quot; abc &quot;, null)    = &quot; abc&quot; 
	 *    StringUtil.trimEnd(&quot;  abcyx&quot;, &quot;xyz&quot;) = &quot;  abc&quot;
	 * </pre>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为 <code>null</code> 表示除去空白字符
	 * 
	 * @return 除去指定字符后的的字符串，如果原字串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String trimEnd(String str, String stripChars) {
		return trim(str, stripChars, 1);
	}

	/**
	 * 除去字符串头尾部的指定字符，如果字符串是 <code>null</code> ，依然返回 <code>null</code>
	 * 
	 * <pre>
	 *    StringUtil.trim(null, *)          = null 
	 *    StringUtil.trim(&quot;&quot;, *)            = &quot;&quot; 
	 *    StringUtil.trim(&quot;abc&quot;, null)      = &quot;abc&quot; 
	 *    StringUtil.trim(&quot;  abc&quot;, null)    = &quot;abc&quot; 
	 *    StringUtil.trim(&quot;abc  &quot;, null)    = &quot;abc&quot; 
	 *    StringUtil.trim(&quot; abc &quot;, null)    = &quot;abc&quot; 
	 *    StringUtil.trim(&quot;  abcyx&quot;, &quot;xyz&quot;) = &quot;  abc&quot;
	 * </pre>
	 * 
	 * @param str
	 *            要处理的字符串
	 * @param stripChars
	 *            要除去的字符，如果为 <code>null</code> 表示除去空白字符
	 * @param mode
	 *            <code>-1</code> 表示trimStart， <code>0</code>
	 *            表示trim全部，<code>1</code> 表示trimEnd
	 * @return 除去指定字符后的的字符串，如果原字串为 <code>null</code> ，则返回 <code>null</code>
	 */
	private static String trim(String str, String stripChars, int mode) {
		if (str == null) {
			return null;
		}

		int length = str.length();
		int start = 0;
		int end = length;

		// 扫描字符串头部
		if (mode <= 0) {
			if (stripChars == null) {
				while ((start < end) && (Character.isWhitespace(str.charAt(start)))) {
					start++;
				}
			} else if (stripChars.length() == 0) {
				return str;
			} else {
				while ((start < end) && (stripChars.indexOf(str.charAt(start)) != -1)) {
					start++;
				}
			}
		}

		// 扫描字符串尾部
		if (mode >= 0) {
			if (stripChars == null) {
				while ((start < end) && (Character.isWhitespace(str.charAt(end - 1)))) {
					end--;
				}
			} else if (stripChars.length() == 0) {
				return str;
			} else {
				while ((start < end) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
					end--;
				}
			}
		}

		if ((start > 0) || (end < length)) {
			return str.substring(start, end);
		}

		return str;
	}
	// endregion

	/**
	 * 连接字符串
	 * 
	 * @param separator
	 * @param value
	 * @return
	 */
	public static String join(String separator, String[] value) {
		if (isNullOrEmpty(separator))
			return "";
		if (null == value || value.length < 1)
			return "";
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < value.length; i++) {
			buffer.append(value[i]);
			if (i < value.length - 1)
				buffer.append(separator);
		}
		return buffer.toString();
	}

	/**
	 * 连接字符串
	 * 
	 * @param separator
	 * @param value
	 * @return
	 */
	public static String join(String separator, List<String> value) {
		if (isNullOrEmpty(separator))
			return "";
		if (null == value || value.size() < 1)
			return "";
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < value.size(); i++) {
			buffer.append(value.get(i));
			if (i < value.size() - 1)
				buffer.append(separator);
		}
		return buffer.toString();
	}

	/**
	 * 转换为非null的字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String toNonNull(String value) {
		if (null == value)
			return "";
		return value;
	}

	/**
	 * 转换成List<String>
	 * 
	 * @param value
	 * @param split
	 *            分隔符
	 * @return
	 */
	public static List<String> toList(String value, String split) {
		List<String> list = new ArrayList<String>();
		if (isNullOrWhiteSpace(value))
			return list;
		String[] array = value.split(split);
		if (null == array || array.length < 1)
			return list;

		for (String item : array) {
			list.add(item.trim());
		}
		return list;
	}

	/**
	 * 填充固定长度字符串，不足补空格
	 * 
	 * @param src
	 *            源字符串
	 * @param length
	 *            固定字符串长度
	 * @param paddingChar
	 *            填补内容
	 * @param leadingPad
	 *            前补还是后补
	 * @return
	 */
	public static String padFixLength(String src, int totalWidth, char paddingChar, boolean leadingPad) {
		if (src == null) {
			src = "";
		}
		if (totalWidth <= src.length()) {
			return src;
		}
		StringBuilder result = new StringBuilder(src);
		for (int i = src.length(), j = totalWidth; i < j; i++) {
			if (leadingPad) {
				result.insert(0, paddingChar);
			} else {
				result.append(paddingChar);
			}
		}
		return result.toString();
	}

	/**
	 * 从左边开始填充空格
	 * 
	 * @param src
	 * @param totalWidth
	 * @return
	 */
	public static String padLeft(String src, int totalWidth) {
		return padFixLength(src, totalWidth, ' ', true);
	}

	/**
	 * 从左边开始填充空格
	 * 
	 * @param src
	 * @param totalWidth
	 * @param paddingChar
	 * @return
	 */
	public static String padLeft(String src, int totalWidth, char paddingChar) {
		return padFixLength(src, totalWidth, paddingChar, true);
	}

	/**
	 * 从右边开始填充空格
	 * 
	 * @param src
	 * @param totalWidth
	 * @return
	 */
	public static String padRight(String src, int totalWidth) {
		return padFixLength(src, totalWidth, ' ', false);
	}

	/**
	 * 切分成数组
	 * 
	 * @param str
	 */
//	public static String[] tokenizeToStringArray(final String str) {
//		final String defaultDelimiter = "[;]";
//		return tokenizeToStringArray(str, defaultDelimiter);
//	}

	/**
	 * 切分成数组
	 * 
	 * @param str
	 * @param delimiters
	 */
//	public static String[] tokenizeToStringArray(final String str, final String delimiters) {
//		return str == null ? null : org.springframework.util.StringUtils.tokenizeToStringArray(str, delimiters);
//	}

	/**
	 * 从右边开始填充空格
	 * 
	 * @param src
	 * @param totalWidth
	 * @param paddingChar
	 * @return
	 */
	public static String padRight(String src, int totalWidth, char paddingChar) {
		return padFixLength(src, totalWidth, paddingChar, false);
	}

	// region 字符串类型判断 is...
	/**
	 * 判断字符串是否只包含unicode字母。
	 * 
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 * 
	 * <pre>
	 *    StringUtil.isAlpha(null)   = false 
	 *    StringUtil.isAlpha(&quot;&quot;)     = true 
	 *    StringUtil.isAlpha(&quot;  &quot;)   = false 
	 *    StringUtil.isAlpha(&quot;abc&quot;)  = true 
	 *    StringUtil.isAlpha(&quot;ab2c&quot;) = false 
	 *    StringUtil.isAlpha(&quot;ab-c&quot;) = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果字符串非 <code>null</code> 并且全由unicode字母组成，则返回 <code>true</code>
	 */
	public static boolean isAlpha(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isLetter(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否只包含unicode字母和空格 <code>' '</code>。
	 * 
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 * 
	 * <pre>
	 *    StringUtil.isAlphaSpace(null)   = false 
	 *    StringUtil.isAlphaSpace(&quot;&quot;)     = true 
	 *    StringUtil.isAlphaSpace(&quot;  &quot;)   = true 
	 *    StringUtil.isAlphaSpace(&quot;abc&quot;)  = true 
	 *    StringUtil.isAlphaSpace(&quot;ab c&quot;) = true 
	 *    StringUtil.isAlphaSpace(&quot;ab2c&quot;) = false 
	 *    StringUtil.isAlphaSpace(&quot;ab-c&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果字符串非 <code>null</code> 并且全由unicode字母和空格组成，则返回 <code>true</code>
	 */
	public static boolean isAlphaSpace(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isLetter(str.charAt(i)) && (str.charAt(i) != ' ')) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断字符串是否只包含unicode字母和数字。
	 * 
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 * 
	 * <pre>
	 *    StringUtil.isAlphanumeric(null)   = false 
	 *    StringUtil.isAlphanumeric(&quot;&quot;)     = true 
	 *    StringUtil.isAlphanumeric(&quot;  &quot;)   = false 
	 *    StringUtil.isAlphanumeric(&quot;abc&quot;)  = true 
	 *    StringUtil.isAlphanumeric(&quot;ab c&quot;) = false 
	 *    StringUtil.isAlphanumeric(&quot;ab2c&quot;) = true 
	 *    StringUtil.isAlphanumeric(&quot;ab-c&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果字符串非 <code>null</code> 并且全由unicode字母数字组成，则返回 <code>true</code>
	 */
	public static boolean isAlphaNumeric(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isLetterOrDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否只包含unicode字母数字和空格 <code>' '</code>。
	 * 
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 * 
	 * <pre>
	 *    StringUtil.isAlphanumericSpace(null)   = false 
	 *    StringUtil.isAlphanumericSpace(&quot;&quot;)     = true 
	 *    StringUtil.isAlphanumericSpace(&quot;  &quot;)   = true 
	 *    StringUtil.isAlphanumericSpace(&quot;abc&quot;)  = true 
	 *    StringUtil.isAlphanumericSpace(&quot;ab c&quot;) = true 
	 *    StringUtil.isAlphanumericSpace(&quot;ab2c&quot;) = true 
	 *    StringUtil.isAlphanumericSpace(&quot;ab-c&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果字符串非 <code>null</code> 并且全由unicode字母数字和空格组成，则返回
	 *         <code>true</code>
	 */
	public static boolean isAlphaNumericSpace(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isLetterOrDigit(str.charAt(i)) && (str.charAt(i) != ' ')) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断字符串是否只包含unicode数字。
	 * 
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 * 
	 * <pre>
	 *    StringUtil.isNumeric(null)   = false 
	 *    StringUtil.isNumeric(&quot;&quot;)     = true 
	 *    StringUtil.isNumeric(&quot;  &quot;)   = false 
	 *    StringUtil.isNumeric(&quot;123&quot;)  = true 
	 *    StringUtil.isNumeric(&quot;12 3&quot;) = false 
	 *    StringUtil.isNumeric(&quot;ab2c&quot;) = false 
	 *    StringUtil.isNumeric(&quot;12-3&quot;) = false 
	 *    StringUtil.isNumeric(&quot;12.3&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果字符串非 <code>null</code> 并且全由unicode数字组成，则返回 <code>true</code>
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否只包含unicode数字和空格 <code>' '</code>。
	 * 
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 * 
	 * <pre>
	 *    StringUtil.isNumericSpace(null)   = false 
	 *    StringUtil.isNumericSpace(&quot;&quot;)     = true 
	 *    StringUtil.isNumericSpace(&quot;  &quot;)   = true 
	 *    StringUtil.isNumericSpace(&quot;123&quot;)  = true 
	 *    StringUtil.isNumericSpace(&quot;12 3&quot;) = true 
	 *    StringUtil.isNumericSpace(&quot;ab2c&quot;) = false 
	 *    StringUtil.isNumericSpace(&quot;12-3&quot;) = false 
	 *    StringUtil.isNumericSpace(&quot;12.3&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果字符串非 <code>null</code> 并且全由unicode数字和空格组成，则返回 <code>true</code>
	 */
	public static boolean isNumericSpace(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isDigit(str.charAt(i)) && (str.charAt(i) != ' ')) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断字符串是否只包含unicode空白。
	 * 
	 * <p>
	 * <code>null</code> 将返回 <code>false</code> ，空字符串 <code>""</code> 将返回
	 * <code>true</code>。
	 * </p>
	 * 
	 * <pre>
	 *    StringUtil.isWhitespace(null)   = false 
	 *    StringUtil.isWhitespace(&quot;&quot;)     = true 
	 *    StringUtil.isWhitespace(&quot;  &quot;)   = true 
	 *    StringUtil.isWhitespace(&quot;abc&quot;)  = false 
	 *    StringUtil.isWhitespace(&quot;ab2c&quot;) = false 
	 *    StringUtil.isWhitespace(&quot;ab-c&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果字符串非 <code>null</code> 并且全由unicode空白组成，则返回 <code>true</code>
	 */
	public static boolean isWhitespace(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 是否为中文字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		final Pattern CHINESE_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");

		boolean temp = false;
		Matcher m = CHINESE_PATTERN.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}

	/**
	 * 是否为手机号码
	 * <p>
	 * 目前仅包含中国大陆和中国香港
	 * </P>
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isMobileInGlobal(String number) {
		final Pattern PATTERN_MOBILEPHONE = Pattern.compile("^0?1[3456789]\\d{9}$");
		final Pattern PATTERN_MOBILEPHONE_HK = Pattern.compile("^[5,6,9]\\d{7}$");
		// 是否为大陆手机号
		boolean isMatched = PATTERN_MOBILEPHONE.matcher(number).matches();
		// 是否为香港手机号
		if (!isMatched) {
			isMatched = PATTERN_MOBILEPHONE_HK.matcher(number).matches();
		}
		return isMatched;
	}

	/**
	 * 是否为手机号码
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isMobile(String number) {
		final Pattern PATTERN_MOBILEPHONE = Pattern.compile("^0?1[3456789]\\d{9}$");
		// 是否为大陆手机号
		boolean isMatched = PATTERN_MOBILEPHONE.matcher(number).matches();
		return isMatched;
	}

	/**
	 * 判断是否为固定电话号码
	 * 
	 * @param number
	 *            固定电话号码
	 * @return
	 */
	public static boolean isTelephone(String number) {
		final Pattern PATTERN_TELEPHONE = Pattern.compile("^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$");
		Matcher match = PATTERN_TELEPHONE.matcher(number);
		return match.matches();
	}

	/**
	 * 获取固定号码号码中的区号
	 * 
	 * @param strNumber
	 * @return
	 */
	public static String getZipFromTelephone(String strNumber) {
		final Pattern PATTERN_ZIPCODE = Pattern.compile("^(010|02\\d|0[3-9]\\d{2})\\d{6,8}$");
		Matcher matcher = PATTERN_ZIPCODE.matcher(strNumber);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	/**
	 * 是否为邮箱
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str) {
		Pattern emailPattern = Pattern.compile("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w-]+\\.)+[\\w]+[\\w]$");
		return emailPattern.matcher(str).matches();
	}

	/**
	 * 是否为url地址
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isUrl(String str) {
		// Pattern urlPattern =
		// Pattern.compile("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
		Pattern urlPattern = Pattern.compile(
				"^(http|https|ftp|ftps|tcp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&%\\$\\-]+)*@)*((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|localhost|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{1,10}))(\\:[0-9]+)*(/($|[a-zA-Z0-9\\.\\,\\?\\'\\\\+&%\\$#\\=~_!\\-]+))*$");
		return urlPattern.matcher(str).find();
	}
	// endregion

	/**
	 * 将字符串的首字符转成大写（ <code>Character.toTitleCase</code> ），其它字符不变。
	 * 
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 * 
	 * <pre>
	 *    StringUtil.capitalize(null)  = null 
	 *    StringUtil.capitalize(&quot;&quot;)    = &quot;&quot; 
	 *    StringUtil.capitalize(&quot;cat&quot;) = &quot;Cat&quot; 
	 *    StringUtil.capitalize(&quot;cAt&quot;) = &quot;CAt&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要转换的字符串
	 * 
	 * @return 首字符为大写的字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String capitalize(String str) {
		int strLen;

		if ((str == null) || ((strLen = str.length()) == 0)) {
			return str;
		}

		return new StringBuffer(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1))
				.toString();
	}

	/**
	 * 将字符串的首字符转成小写，其它字符不变。
	 * 
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 * 
	 * <pre>
	 *    StringUtil.uncapitalize(null)  = null 
	 *    StringUtil.uncapitalize(&quot;&quot;)    = &quot;&quot; 
	 *    StringUtil.uncapitalize(&quot;Cat&quot;) = &quot;cat&quot; 
	 *    StringUtil.uncapitalize(&quot;CAT&quot;) = &quot;cAT&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要转换的字符串
	 * 
	 * @return 首字符为小写的字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String uncapitalize(String str) {
		int strLen;

		if ((str == null) || ((strLen = str.length()) == 0)) {
			return str;
		}

		return new StringBuffer(strLen).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1))
				.toString();
	}

	/**
	 * 反转字符串的大小写。
	 * 
	 * <p>
	 * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
	 * 
	 * <pre>
	 *    StringUtil.swapCase(null)                 = null 
	 *    StringUtil.swapCase(&quot;&quot;)                   = &quot;&quot; 
	 *    StringUtil.swapCase(&quot;The dog has a BONE&quot;) = &quot;tHE DOG HAS A bone&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            要转换的字符串
	 * 
	 * @return 大小写被反转的字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
	 */
	public static String swapCase(String str) {
		int strLen;

		if ((str == null) || ((strLen = str.length()) == 0)) {
			return str;
		}

		StringBuffer buffer = new StringBuffer(strLen);

		char ch = 0;

		for (int i = 0; i < strLen; i++) {
			ch = str.charAt(i);

			if (Character.isUpperCase(ch)) {
				ch = Character.toLowerCase(ch);
			} else if (Character.isTitleCase(ch)) {
				ch = Character.toLowerCase(ch);
			} else if (Character.isLowerCase(ch)) {
				ch = Character.toUpperCase(ch);
			}

			buffer.append(ch);
		}

		return buffer.toString();
	}

	/**
	 * 计算text的长度（一个中文算两个字符）
	 * 
	 * @param text
	 * @return
	 */
	public static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}

	/**
	 * 显示标准大小
	 * 
	 * @param longSize
	 * @param scale
	 *            精度
	 * @return
	 */
	public static String displayStdSize(long longSize, int scale) {
		// byte字节参考量
		final long SIZE_BT = 1024L;
		// KB字节参考量
		final long SIZE_KB = SIZE_BT * 1024L;
		// MB字节参考量
		final long SIZE_MB = SIZE_KB * 1024L;
		// GB字节参考量
		final long SIZE_GB = SIZE_MB * 1024L;
		// TB字节参考量
		// final long SIZE_TB = SIZE_GB * 1024L;

		if (longSize >= 0 && longSize < SIZE_BT) {
			return longSize + "B";
		} else if (longSize >= SIZE_BT && longSize < SIZE_KB) {
			BigDecimal decimal = new BigDecimal(Double.valueOf(longSize + "").toString());
			BigDecimal sizeB = new BigDecimal(Double.valueOf(SIZE_BT + "").toString());
			String result = decimal.divide(sizeB, scale, BigDecimal.ROUND_HALF_UP).toString();
			return result + "KB";
		} else if (longSize >= SIZE_KB && longSize < SIZE_MB) {
			BigDecimal decimal = new BigDecimal(Double.valueOf(longSize + "").toString());
			BigDecimal sizeKB = new BigDecimal(Double.valueOf(SIZE_KB + "").toString());
			String result = decimal.divide(sizeKB, scale, BigDecimal.ROUND_HALF_UP).toString();
			return result + "MB";
		} else if (longSize >= SIZE_MB && longSize < SIZE_GB) {
			BigDecimal decimal = new BigDecimal(Double.valueOf(longSize + "").toString());
			BigDecimal sizeMB = new BigDecimal(Double.valueOf(SIZE_MB + "").toString());
			String result = decimal.divide(sizeMB, scale, BigDecimal.ROUND_HALF_UP).toString();
			return result + "GB";
		} else {
			BigDecimal decimal = new BigDecimal(Double.valueOf(longSize + "").toString());
			BigDecimal sizeGB = new BigDecimal(Double.valueOf(SIZE_GB + "").toString());
			String result = decimal.divide(sizeGB, scale, BigDecimal.ROUND_HALF_UP).toString();
			return result + "TB";
		}
	}

	/**
	 * 显示标准大小
	 * <p>
	 * 两位小数
	 * </p>
	 * 
	 * @param longSize
	 * @return
	 */
	public static String displayStdSize(long longSize) {
		final int SCALE = 2;
		return displayStdSize(longSize, SCALE);
	}

	/**
	 * 去除html代码
	 * 
	 * @param inputString
	 *            含html标签的字符串
	 * @return
	 */
	public static String htmlToText(String htmlString) {
		String htmlStr = htmlString;
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		java.util.regex.Pattern p_ba;
		java.util.regex.Matcher m_ba;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
																										// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
																									// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String patternStr = "\\s+";

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_ba = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
			m_ba = p_ba.matcher(htmlStr);
			htmlStr = m_ba.replaceAll(""); // 过滤空格

			textStr = htmlStr;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return textStr;// 返回文本字符串
	}

	/**
	 * 转义html
	 * 
	 * <p>
	 * <br />
	 * Given a string, this method replaces all occurrences of '<' with '&lt;',
	 * all occurrences of '>' with '&gt;', and (to handle cases that occur
	 * inside attribute values), all occurrences of double quotes with '&#034;'
	 * and all occurrences of single quotes with '&#039;' and all occurrences of
	 * '&' with '&amp;'. Without such filtering, an arbitrary string could not
	 * safely be inserted in a Web page.
	 */
	public static String escapeHtml(String html) {
		if (isNullOrWhiteSpace(html)) {
			return html;
		}
		String result = html;

		result = result.replaceAll("&", "&amp;");
		result = result.replaceAll("\"", "&quot;");
		result = result.replaceAll("<", "&lt;");
		result = result.replaceAll(">", "&gt;");
		result = result.replaceAll("'", "&#039;");
		result = result.replaceAll("\\ ", "&nbsp;");
		result = result.replaceAll("\n", "<br>");
		result = result.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");

		return result;
	}

	/**
	 * 转义html
	 * <p>
	 * 不包含br
	 * </p>
	 * 
	 * @param html
	 * @return
	 */
	public static String escapeHtmlNonBR(String html) {
		if (isNullOrWhiteSpace(html)) {
			return html;
		}

		String result = html;

		result = result.replaceAll("&", "&amp;");
		result = result.replaceAll("\"", "&quot;");
		result = result.replaceAll("<", "&lt;");
		result = result.replaceAll(">", "&gt;");
		result = result.replaceAll("'", "&#39;");
		result = result.replaceAll("\\ ", "&nbsp;");

		return result;
	}

	/**
	 * 将转义的字符串还原
	 * 
	 * @param text
	 * @return
	 */
	public static String unescapeHtml(String text) {
		String result = text;
		result = result.replaceAll("&amp;", "&");
		result = result.replaceAll("&quot;", "\"");
		result = result.replaceAll("&lt;", "<");
		result = result.replaceAll("&gt;", ">");
		result = result.replaceAll("&#39;", "'");
		result = result.replaceAll("&nbsp;", "\\ ");
		result = result.replaceAll("<br>", "\n");
		result = result.replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;", "\t");
		return result;
	}

	/**
	 * 将转义的字符串还原
	 * 
	 * @param text
	 * @return
	 */
	public static String unescapeHtmlNoBR(String text) {
		String result = text;
		result = result.replaceAll("&amp;", "&");
		result = result.replaceAll("&quot;", "\"");
		result = result.replaceAll("&lt;", "<");
		result = result.replaceAll("&gt;", ">");
		result = result.replaceAll("&#39;", "'");
		result = result.replaceAll("&nbsp;", "\\ ");
		return result;
	}

	/**
	 * 总是消除所有的HTML标签的内容，并返回一个子串过长字符返回省略号
	 * 
	 * @param content
	 * @param maxLength
	 * @return
	 */
	public static String ellipsis(String content, int maxLength) {
		StringBuffer substring = new StringBuffer();
		int offset = 0;
		boolean flag = true;
		while (offset < content.length()) {
			char c = content.charAt(offset);

			int length = substring.length();
			if (length >= maxLength && allowToCut(c)) {
				substring.append(" ...");
				break;
			}

			if (length >= maxLength + 20) {
				substring.append(" ...");
				break;
			}

			if (c == '<')
				flag = false;

			if (flag)
				substring.append(c);

			if (c == '>')
				flag = true;

			offset++;
		}
		return substring.toString();
	}

	private static boolean allowToCut(char c) {
		if (c <= 57 && c >= 48)
			return false;
		if (c <= 90 && c >= 65)
			return false;
		if (c <= 122 && c >= 97)
			return false;
		return true;
	}
}

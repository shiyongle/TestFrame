/**
 * @title JavaUtil.java
 * @description TODO
 * @package lm.com.framework
 * @author mrluo735
 * @since JDK1.7
 * @date 2017年1月3日上午9:32:41
 * @version v1.0
 */
package com.dongnao.autotest.common;

/**
 * java工具类
 * <p>
 * System.getProperty()方法可以获取的值
 * </p>
 * 
 * @author easy
 *
 */
public class JavaUtil {
	/**
	 * Java 安装目录
	 */
	private static final String JAVA_HOME = "java.home";
	/**
	 * Java 类路径
	 */
	private static final String JAVA_CLASS_PATH = "java.class.path";
	/**
	 * Java 类格式版本号
	 */
	private static final String JAVA_CLASS_VERSION = "java.class.version";
	/**
	 * JAVA 使用的 JIT 编译器的名称
	 */
	private static final String JAVA_COMPILER = "java.compiler";
	/**
	 * JAVA 一个或多个扩展目录的路径
	 */
	private static final String JAVA_EXT_DIRS = "java.ext.dirs";
	/**
	 * JAVA 默认的临时文件路径
	 */
	private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";
	/**
	 * JAVA 加载库时搜索的路径列表
	 */
	private static final String JAVA_LIBRARY_PATH = "java.library.path";
	/**
	 * Java 运行时环境规范名称
	 */
	private static final String JAVA_SPECIFICATION_NAME = "java.specification.name";
	/**
	 * Java 运行时环境规范供应商
	 */
	private static final String JAVA_SPECIFICATION_VENDOR = "java.specification.vendor";
	/**
	 * Java 运行时环境规范版本
	 */
	private static final String JAVA_SPECIFICATION_VERSION = "java.specification.version";
	/**
	 * Java 运行时环境供应商
	 */
	private static final String JAVA_VENDOR = "java.vendor";
	/**
	 * Java 运行时环境供应商的URL
	 */
	private static final String JAVA_VENDOR_URL = "java.vendor.url";
	/**
	 * Java 运行时环境版本
	 */
	private static final String JAVA_VERSION = "java.version";
	/**
	 * Java 虚拟机规范名称
	 */
	private static final String JAVA_VM_SPECIFICATION_NAME = "java.vm.specification.name";
	/**
	 * Java 虚拟机规范供应商
	 */
	private static final String JAVA_VM_SPECIFICATION_VENDOR = "java.vm.specification.vendor";
	/**
	 * Java 虚拟机规范版本
	 */
	private static final String JAVA_VM_SPECIFICATION_VERSION = "java.vm.specification.version";
	/**
	 * Java 虚拟机实现名称
	 */
	private static final String JAVA_VM_NAME = "java.vm.name";
	/**
	 * Java 虚拟机实现供应商
	 */
	private static final String JAVA_VM_VENDOR = "java.vm.vendor";
	/**
	 * Java 虚拟机实现版本
	 */
	private static final String JAVA_VM_VERSION = "java.vm.version";
	/**
	 * 文件分隔符（在 UNIX 系统中是“/”）
	 */
	private static final String FILE_SEPARATOR = "file.separator";
	/**
	 * 行分隔符（在 UNIX 系统中是“/n”）
	 */
	private static final String LINE_SEPARATOR = "line.separator";
	/**
	 * 路径分隔符（在 UNIX 系统中是“:”）
	 */
	private static final String PATH_SEPARATOR = "path.separator";
	/**
	 * 操作系统的名称
	 */
	private static final String OS_NAME = "os.name";
	/**
	 * 操作系统的架构
	 */
	private static final String OS_ARCH = "os.arch";
	/**
	 * 操作系统的版本
	 */
	private static final String OS_VERSION = "os.version";
	/**
	 * 用户的账户名称
	 */
	private static final String USER_NAME = "user.name";
	/**
	 * 用户的主目录
	 */
	private static final String USER_HOME = "user.home";
	/**
	 * 用户的当前工作目录
	 */
	private static final String USER_DIR = "user.dir";

	/**
	 * Java 安装目录
	 * 
	 * @return
	 */
	public static String getJavaHome() {
		return System.getProperty(JAVA_HOME);
	}

	/**
	 * Java 类路径
	 * 
	 * @return
	 */
	public static String getJavaClassPath() {
		return System.getProperty(JAVA_CLASS_PATH);
	}

	/**
	 * Java 类格式版本号
	 * 
	 * @return
	 */
	public static String getJavaClassVersion() {
		return System.getProperty(JAVA_CLASS_VERSION);
	}

	/**
	 * Java JIT 编译器的名称
	 * 
	 * @return
	 */
	public static String getJavaCompiler() {
		return System.getProperty(JAVA_COMPILER);
	}

	/**
	 * Java 一个或多个扩展目录的路径
	 * 
	 * @return
	 */
	public static String getJavaExtDirs() {
		return System.getProperty(JAVA_EXT_DIRS);
	}

	/**
	 * Java 默认的临时文件路径
	 * 
	 * @return
	 */
	public static String getJavaIOTempdir() {
		return System.getProperty(JAVA_IO_TMPDIR);
	}

	/**
	 * Java 加载库时搜索的路径列表
	 * 
	 * @return
	 */
	public static String getJavaLibraryPath() {
		return System.getProperty(JAVA_LIBRARY_PATH);
	}

	/**
	 * Java 运行时环境规范名称
	 * 
	 * @return
	 */
	public static String getJavaSpecificationName() {
		return System.getProperty(JAVA_SPECIFICATION_NAME);
	}

	/**
	 * Java 运行时环境规范供应商
	 * 
	 * @return
	 */
	public static String getJavaSpecificationVendor() {
		return System.getProperty(JAVA_SPECIFICATION_VENDOR);
	}

	/**
	 * Java 运行时环境规范版本
	 * 
	 * @return
	 */
	public static String getJavaSpecificationVersion() {
		return System.getProperty(JAVA_SPECIFICATION_VERSION);
	}

	/**
	 * Java 运行时环境供应商
	 * 
	 * @return
	 */
	public static String getJavaVendor() {
		return System.getProperty(JAVA_VENDOR);
	}

	/**
	 * Java 运行时环境供应商的URL
	 * 
	 * @return
	 */
	public static String getJavaVendorUrl() {
		return System.getProperty(JAVA_VENDOR_URL);
	}

	/**
	 * Java 运行时环境版本
	 * 
	 * @return
	 */
	public static String getJavaVersion() {
		return System.getProperty(JAVA_VERSION);
	}

	/**
	 * Java 虚拟机规范名称
	 * 
	 * @return
	 */
	public static String getJavaVMSpecificationName() {
		return System.getProperty(JAVA_VM_SPECIFICATION_NAME);
	}

	/**
	 * Java 虚拟机规范供应商
	 * 
	 * @return
	 */
	public static String getJavaVMSpecificationVendor() {
		return System.getProperty(JAVA_VM_SPECIFICATION_VENDOR);
	}

	/**
	 * Java 虚拟机规范版本
	 * 
	 * @return
	 */
	public static String getJavaVMSpecificationVersion() {
		return System.getProperty(JAVA_VM_SPECIFICATION_VERSION);
	}

	/**
	 * Java 虚拟机实现名称
	 * 
	 * @return
	 */
	public static String getJavaVMName() {
		return System.getProperty(JAVA_VM_NAME);
	}

	/**
	 * Java 虚拟机实现供应商
	 * 
	 * @return
	 */
	public static String getJavaVMVendor() {
		return System.getProperty(JAVA_VM_VENDOR);
	}

	/**
	 * Java 虚拟机实现版本
	 * 
	 * @return
	 */
	public static String getJavaVMVersion() {
		return System.getProperty(JAVA_VM_VERSION);
	}

	/**
	 * 文件分隔符（在 UNIX 系统中是“/”）
	 * 
	 * @return
	 */
	public static String getFileSeparator() {
		return System.getProperty(FILE_SEPARATOR);
	}

	/**
	 * 行分隔符（Windows系统中是“\r\n”，Unix/Linux系统中是“/n”，Mac系统中是“/r”）
	 * 
	 * @return
	 */
	public static String getLineSeparator() {
		return System.getProperty(LINE_SEPARATOR);
	}

	/**
	 * 路径分隔符（Windows系统中是“\”，Linux系统中是“/”，Unix 系统中是“:”）
	 * 
	 * @return
	 */
	public static String getPathSeparator() {
		return System.getProperty(PATH_SEPARATOR);
	}

	/**
	 * 操作系统的名称
	 * 
	 * @return
	 */
	public static String getOSName() {
		return System.getProperty(OS_NAME);
	}

	/**
	 * 操作系统的架构
	 * 
	 * @return
	 */
	public static String getOSArch() {
		return System.getProperty(OS_ARCH);
	}

	/**
	 * 操作系统的版本
	 * 
	 * @return
	 */
	public static String getOSVersion() {
		return System.getProperty(OS_VERSION);
	}

	/**
	 * 用户的账户名称
	 * 
	 * @return
	 */
	public static String getUserName() {
		return System.getProperty(USER_NAME);
	}

	/**
	 * 用户的主目录
	 * 
	 * @return
	 */
	public static String getUserHome() {
		return System.getProperty(USER_HOME);
	}

	/**
	 * 用户的当前工作目录
	 * 
	 * @return
	 */
	public static String getUserDir() {
		return System.getProperty(USER_DIR);
	}
}

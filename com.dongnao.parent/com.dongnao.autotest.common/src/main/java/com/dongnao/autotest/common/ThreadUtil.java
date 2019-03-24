package com.dongnao.autotest.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程帮助类
 * 
 * @author mrluo735
 *
 */
public class ThreadUtil {
	/**
	 * 开始新任务
	 * 
	 * @param runnable
	 */
	public static void startNew(Runnable runnable) {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(runnable);
	}

	/**
	 * 获取当前类名
	 * 
	 * @return
	 */
	public static String getCurrentClassName(int level) {
		// level:0是当前;1:调用者
		return Thread.currentThread().getStackTrace()[level].getClassName();
	}

	/**
	 * 获取当前方法名
	 * 
	 * @return
	 */
	public static String getCurrentMethodName(int level) {
		// level:0是当前;1:调用者
		return Thread.currentThread().getStackTrace()[level].getMethodName();
	}
}

package com.dongnao.autotest.common;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 
 * @author easy
 *
 */
public class SnapshotUtil {
	/**
	 * 截屏
	 * 
	 * @param path
	 * @param fileName
	 */
	public static void capture(String path, String fileName) {
		try {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle screenRectangle = new Rectangle(screenSize);
			Robot robot = new Robot();
			BufferedImage image = robot.createScreenCapture(screenRectangle);
			// 截图保存的路径
			File screenFile = new File(path);
			// 如果路径不存在,则创建
			if (!screenFile.getParentFile().exists()) {
				screenFile.getParentFile().mkdirs();
			}
			// 判断文件是否存在，不存在就创建文件
			if (!screenFile.exists() && !screenFile.isDirectory()) {
				screenFile.mkdir();
			}

			File f = new File(screenFile, fileName);
			ImageIO.write(image, "png", f);
		} catch (Exception ex) {

		}
	}
}

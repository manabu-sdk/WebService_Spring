package com.example.demo.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;

import com.example.demo.controller.MainController;

public class AppLogger {
	static protected Logger logger = LogManager.getLogger(MainController.class);

	public AppLogger() {
		super();
	}

	public static void writeLog(Level level, String message) {
		if (level == Level.DEBUG) {
			logger.debug(message);
		} else if (level == Level.INFO) {
			logger.info(message);
		} else if (level == Level.WARN) {
			logger.warn(message);
		} else if (level == Level.ERROR) {
			logger.error(message);
		} else if (level == Level.FATAL) {
			logger.fatal(message);
		} else if (level == Level.TRACE) {
			logger.trace(message);
		} else {
			logger.log(level, message);
		}
	}

}

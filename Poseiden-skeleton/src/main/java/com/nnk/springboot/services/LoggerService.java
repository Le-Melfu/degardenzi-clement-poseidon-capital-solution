package com.nnk.springboot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggerService {
    private final Logger logger;

    public LoggerService() {
        this.logger = LoggerFactory.getLogger(LoggerService.class);
    }

    public LoggerService(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    /**
     * Log an error message
     * 
     * @param message the message to log
     */
    public void e(String message) {
        logger.error("{}", message);
    }

    /**
     * Log an error message with parameters
     * 
     * @param message the message to log
     * @param args    the arguments to format the message
     */
    public void e(String message, Object... args) {
        logger.error(message, args);
    }

    /**
     * Log an error message with throwable
     * 
     * @param message   the message to log
     * @param throwable the throwable to log
     */
    public void e(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    /**
     * Log a debug message
     * 
     * @param message the message to log
     */
    public void d(String message) {
        logger.debug("{}", message);
    }

    /**
     * Log a debug message with parameters
     * 
     * @param message the message to log
     * @param args    the arguments to format the message
     */
    public void d(String message, Object... args) {
        logger.debug(message, args);
    }

    /**
     * Log an info message
     * 
     * @param message the message to log
     */
    public void i(String message) {
        logger.info("{}", message);
    }

    /**
     * Log an info message with parameters
     * 
     * @param message the message to log
     * @param args    the arguments to format the message
     */
    public void i(String message, Object... args) {
        logger.info(message, args);
    }

    /**
     * Log a warning message
     * 
     * @param message the message to log
     */
    public void w(String message) {
        logger.warn("{}", message);
    }

    /**
     * Log a warning message with parameters
     * 
     * @param message the message to log
     * @param args    the arguments to format the message
     */
    public void w(String message, Object... args) {
        logger.warn(message, args);
    }
}

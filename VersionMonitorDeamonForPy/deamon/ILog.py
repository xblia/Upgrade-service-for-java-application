#coding=utf-8
'''
Created on 2015年12月31日

@author: xiaobolx
'''
import logging
import os

from deamon.ConfigCenter import ConfigCenter


CFG_SECTION_LOG = "LOG"
CFG_KEY_OPENLOG = "open-log"
CFG_KEY_LOGLEVEL = "log-level"
CFG_KEY_OPENDEBUG = "open-debug"

class ILog():
    logger = None
    def __init__(self):
        pass
    
    @staticmethod
    def loggerCfg(basePath, fileName):
        
        ILog.logger = logging.getLogger('mylogger')
        if ConfigCenter.getConfig(CFG_SECTION_LOG, CFG_KEY_OPENLOG) == 'on':
            ILog.logger.setLevel(ILog.__getLogLevel())
        else:
            ILog.logger.setLevel(logging.CRITICAL)
        fh = logging.FileHandler(os.path.join(basePath, fileName))
        fh.setLevel(logging.DEBUG)
        
        ch = logging.StreamHandler()
        ch.setLevel(logging.DEBUG)
        
        formatter = logging.Formatter('%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s')
        fh.setFormatter(formatter)
        ch.setFormatter(formatter)
        
        ILog.logger.addHandler(fh)
        if ConfigCenter.getConfig(CFG_SECTION_LOG, CFG_KEY_OPENDEBUG) == 'on':
            ILog.logger.addHandler(ch)
        
    @staticmethod
    def __getLogLevel():
        level = ConfigCenter.getConfig(CFG_SECTION_LOG, CFG_KEY_LOGLEVEL)
        if level == "error":
            return logging.ERROR
        elif level == "info":
            return logging.INFO
        elif level == "debug":
            return logging.DEBUG
        return logging.CRITICAL
        
    @staticmethod
    def getLogger():
        if None == ILog.logger:
            ILog.loggerCfg("", "verion_update.log")
        return ILog.logger
        
if __name__ == '__main__':
    ILog.getLogger().info("Hello....")

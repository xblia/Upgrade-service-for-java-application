#coding=utf-8
'''
Created on 2015年12月31日

@author: xiaobolx
'''
import ConfigParser
class ConfigCenter:
    config = ConfigParser.ConfigParser()
    config.read("base.cfg.ini")
    def __init__(self):
        pass
    
    @staticmethod
    def getConfig(section, key):
        print ConfigCenter.config.sections()
        info = None
        try:
            info = ConfigCenter.config.get(section, key)
        except Exception as e:
            pass
        return info
    

if __name__ == '__main__':
    info = None
    try:
        info = ConfigCenter.getConfig("LOG", "log-level")
    except Exception as e:
        pass
    
    print info
    

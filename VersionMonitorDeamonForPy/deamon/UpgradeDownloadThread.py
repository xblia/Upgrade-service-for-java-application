#coding=utf-8
'''/*
 * Copyright 2015 lixiaobo
 *
 * VersionUpgrade project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */'''
'''
Created on 2015年12月30日

@author: xiaobolx
'''
import json
import os
import threading
import time
import wx
from deamon.HttpClient import HttpClient
from deamon.IPyDeamonConstant import ResultEvent
import deamon.IPyDeamonConstant
from deamon.ILog import ILog


class UpgradeDownloadThread(threading.Thread):
    def __init__(self, win, args):
        threading.Thread.__init__(self)
        self.win = win
        self.args = args
        self.basePath = os.getcwd() + os.path.sep
        
    def run(self):
        time.sleep(2)
        if self.win is None or self.args is None:
            ILog.getLogger().error("Parameters is none")
            return
        self.printMsgToUI({"info":"Loading upgrade files..."})
        upgradeInfo = self.loadUpgradeInfoFile(self.args[1])
        
        if upgradeInfo == None:
            self.printMsgToUI({"info":"Loading error, please try later"})
            return
        self.printMsgToUI({"softName":upgradeInfo["softName"]})
        iIndex = 0
        iFilesLen = len(upgradeInfo["updateFilePath"])
        for fileRelativePath in upgradeInfo["updateFilePath"]:
            iIndex+=1
            url = deamon.IPyDeamonConstant.BASE_URL + fileRelativePath
            fileName = fileRelativePath[fileRelativePath.find("/", 1)+1:]
            progressInfo = "Update "+fileName
            val = int((iIndex*1.0/iFilesLen)*100)
            self.printMsgToUI({"info":progressInfo, "val":val})
            HttpClient.downloadFile(url, self.basePath+fileName)
        
        for i in range(5, 0, -1):
            self.printMsgToUI({"info":"Upgrade finished, Automatic restart after " + str(i) + " seconds"})
            time.sleep(1)
        
        self.printMsgToUI({"startup":upgradeInfo["startupName"], "basePath":self.basePath})
            
    def loadUpgradeInfoFile(self, filePath):
        try:
            f = open(self.basePath+filePath, "rb")
            jsonInfo = f.read()
            return json.loads(jsonInfo)
        except Exception as e:
            self.printMsgToUI({"info":str(e)})
        return None
        
    def printMsgToUI(self, msg):
        wx.PostEvent(self.win, ResultEvent(deamon.IPyDeamonConstant.MSGID_NOTIFY_PROGRESS, msg))
        ILog.getLogger().error(msg)
        print msg
        
if __name__ == '__main__':
    for i in range(5, 0, -1):
        print i
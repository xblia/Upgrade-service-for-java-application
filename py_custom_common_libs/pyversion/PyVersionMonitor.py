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
Created on 2015年12月28日

@author: xiaobolx
'''
import json
import threading
import time

import wx

from pyversion.ClearTempFiles import ClearTempFiles
from pyversion.HttpClient import HttpClient
from pyversion.ILog import ILog
from pyversion.IMessageDef import IMessageDef, ResultEvent
from pyversion.PyUtils import PyUtils
from pyversion.ZTestVersion import TestVersion


class PyVersionMonitor(threading.Thread):
    def __init__(self, versionInfoProvider, parentFrame):
        threading.Thread.__init__(self) 
        self.versionInfoProvider = versionInfoProvider
        self.parentFrame = parentFrame
        self.isRunning = True
        self.lastShowAlert = 0
        self.uiIsShow = False

    def genRequestMsg(self):
        currVerCode = self.versionInfoProvider.getVersionCode()
        currVerName = self.versionInfoProvider.getVersionName()
        softName = self.versionInfoProvider.getSoftName()
        clientIp = PyUtils.getLocalHostURL()
        clientName = PyUtils.getLocalUserName()
        msgId = IMessageDef.genMsgId()
        msgType = IMessageDef.MSGTYPE_VERSION_CHCECK_REQ
        requestMsg = {}
        
        requestMsg["msgIdentified"] = IMessageDef.MSGIDENTIFIED_VERSIONSERVICE
        requestMsg["clientIp"] = clientIp
        requestMsg["clientName"] = clientName
        requestMsg["currVersionCode"] = currVerCode
        requestMsg["currVersionName"] = currVerName
        requestMsg["softName"] = softName
        requestMsg["msgType"] = msgType
        requestMsg["msgid"] = msgId
        return json.dumps(requestMsg)
    

    def parseAndControlVersion(self, srcJson, jsonInfnObj):
        lastVersionCode = jsonInfnObj["latestVersionCode"]
        if lastVersionCode > self.versionInfoProvider.getVersionCode():
            self.uiIsShow = True
            wx.PostEvent(self.parentFrame, ResultEvent(IMessageDef.EVT_SHOW_FEATURE, jsonInfnObj))
        if jsonInfnObj.has_key("broadcastMsg"):
            wx.PostEvent(self.parentFrame, ResultEvent(IMessageDef.EVT_SHOW_BROADCAST_MSG, jsonInfnObj))
        ILog.getLogger().info("end parseAndControlVersion")
    
    
    def run(self):
        time.sleep(3)
        ClearTempFiles().clearTempFiles()
        if self.versionInfoProvider is None:
            ILog.getLogger().info("Version info provider is None.")
            return
        ILog.getLogger().info("PyVersionMonitor start...")
        while self.isRunning:
            if time.time() - self.lastShowAlert > IMessageDef.INTERVAL_SECOND_ALERT_UPGRADE:
                reqMsg = self.genRequestMsg()
                rspMsg = HttpClient.requestWidthPost(IMessageDef.BASE_URL, reqMsg)
                if rspMsg is None:
                    continue
                jsonInfo = json.loads(rspMsg)
                if jsonInfo is None:
                    continue
                if not self.uiIsShow:
                    self.parseAndControlVersion(rspMsg, jsonInfo)
            iIndex = 0
            while iIndex <= 30:
                if not self.isRunning:
                    break
                time.sleep(1)
                iIndex+=1
                ILog.getLogger().info("sleep 2...."+str(self.uiIsShow))

        ILog.getLogger().info("PyVersion Monitor Exit...")
        
    def stopThread(self):
        self.isRunning = False
    
        
    def setUIIsShow(self, uiIsShow):
        self.lastShowAlert = time.time()
        self.uiIsShow = uiIsShow
if __name__ == '__main__':
    pyVerMonitor = PyVersionMonitor(TestVersion())
    pyVerMonitor.start()

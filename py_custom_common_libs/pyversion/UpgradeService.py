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
Created on 2015年12月29日

@author: xiaobolx
'''
import json
import os
import shutil
import subprocess
import threading
import time

import wx

from pyversion.HttpClient import HttpClient
from pyversion.ILog import ILog
from pyversion.IMessageDef import IMessageDef, ResultEvent


class UpgradeService(threading.Thread):
    def __init__(self, win, jsonInfoObj, debug = True):
        threading.Thread.__init__(self)
        self.win = win
        self.jsonInfoObj = jsonInfoObj
        
        self.basePath = os.getcwd()+os.path.sep
        

    def __genDeamonFullPath(self):
        deamonAppTempFolder = IMessageDef.TEMP_FILE_PREFIX + str(time.time()) + "_Deamon"
        srcDeamonPath = self.basePath + "Deamon"
        destDeamonPath = self.basePath + deamonAppTempFolder
        try:
            #os.rename(srcDeamonPath, destDeamonPath)
            shutil.copytree(srcDeamonPath, destDeamonPath)
        except Exception as e:
            ILog.getLogger().info("error: "+str(e))
            return None
        return destDeamonPath + os.path.sep + IMessageDef.DEAMON_APP_NAME
    

    def __genUpgradeFileListReq(self):
        latestVersionCode = 0
        msgIdentified = IMessageDef.MSGIDENTIFIED_VERSIONSERVICE
        msgType = IMessageDef.MSGTYPE_VERSION_UPDATE_REQ
        msgid = IMessageDef.genMsgId()
        softName = self.jsonInfoObj["softName"]
        
        requestMsg = {}
        requestMsg["latestVersionCode"] = latestVersionCode
        requestMsg["msgIdentified"] = msgIdentified
        requestMsg["msgType"] = msgType
        requestMsg["msgid"] = msgid
        requestMsg["softName"] = softName
        return json.dumps(requestMsg)
    
    
    def run(self):
        if self.jsonInfoObj is None:
            return
        jsonUpgradeReq = self.__genUpgradeFileListReq()
        jsonUpgradeRsp = HttpClient.requestWidthPost(IMessageDef.BASE_URL, jsonUpgradeReq)
        
        if jsonUpgradeRsp is None:
            return
        
        self.__beginUpgrade(jsonUpgradeRsp)
        
    def __beginUpgrade(self, jsonUpgradeRsp):
        jsonFileName = IMessageDef.TEMP_FILE_PREFIX + str(time.time()) + ".bin"
        deamonAppTempFullPath = self.__genDeamonFullPath()
        
        jsonFileFullPath = self.basePath + jsonFileName
        jsonFile = open(jsonFileFullPath, "w")
        jsonFile.write(jsonUpgradeRsp)
        jsonFile.close()
        
        try:
            subprocess.Popen([deamonAppTempFullPath, jsonFileName, jsonFileName])
            #os.system("%s %s %s" % (deamonAppTempFullPath, jsonFileName, jsonFileName))
        except Exception as e:
            ILog.getLogger().info(str(e))
        wx.PostEvent(self.win, ResultEvent(IMessageDef.EVT_UPGRADESERVICE_FEATUREUI_ID, "CLOSE"))
    
if __name__ == '__main__':
    UpgradeService("ADBD", "ADBD").start()
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
import wx

class IMessageDef():
    def __init__(self):
        pass
    
    #Message Define
    EVT_SHOW_FEATURE                = wx.NewId()
    EVT_SHOW_BROADCAST_MSG          = wx.NewId()
    EVT_VER_FEATUREUI_MAINFRAME     = wx.NewId()
    EVT_UPGRADESERVICE_FEATUREUI_ID = wx.NewId()
    
    BASE_URL = "http://xblia2-OptiPlex-9020:8023"
    
    MSGIDENTIFIED_VERSIONSERVICE = 0x11585668;
    
    MSGTYPE_REQUEST_ERROR = 0x0000;
    MSGTYPE_VERSION_CHCECK_REQ = 0x0101;
    MSGTYPE_VERSION_CHCECK_RSP = 0x0102;
    
    MSGTYPE_VERSION_UPDATE_REQ = 0x0201;
    MSGTYPE_VERSION_UPDATE_RSP = 0x0202;
    
    MSGTYPE_GET_ONLINELIST_REQ = 0x0301;
    MSGTYPE_GET_ONLINELIST_RSP = 0x0302;
    
    YES          = "YES";
    NO           = "NO";
    SUCC         = "SUCC";
    FAIL         = "FAIL";
        
    
    INTERVAL_SECOND_ALERT_UPGRADE = 60*60
    
    DEAMON_APP_NAME = "UpgradePyDeamon.exe"
    TEMP_FILE_PREFIX = "TempFile_Upgrade_xx_"
    VERSION_INFO = "Version Upgrade Service v1.0"
    
    g_iMsgId = 1
    @staticmethod
    def genMsgId():
        IMessageDef.g_iMsgId+=1
        return IMessageDef.g_iMsgId;
    

class ResultEvent(wx.PyEvent):
    
    '''Simple event to carry arbitrary result data.'''
    def __init__(self, eventId, data):
        '''Init Result Event.'''
        wx.PyEvent.__init__(self)
        self.SetEventType(eventId)
        self.data = data
        
    @staticmethod
    def BIND_EVENT(win, EventId, func):
        '''Bind Event.'''
        win.Connect(-1, -1, EventId, func)

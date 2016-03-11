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
Created on 2016年1月4日

@author: xiaobolx
'''
import wx

from pyversion.IMessageDef import ResultEvent, IMessageDef
from pyversion.IVersionInfoProvider import IVersionInfoProvider
from pyversion.PyVersionMonitor import PyVersionMonitor
from pyversion.UpgradeUI import UpgradeUI


class PyVerFrame(wx.Frame, IVersionInfoProvider):

    
    def __init__(self, app, parent, frameId=-1, title=wx.EmptyString, pos=wx.DefaultPosition, size=wx.DefaultSize, style=wx.DEFAULT_FRAME_STYLE, name=wx.FrameNameStr):
        wx.Frame.__init__(self, parent, frameId, title, pos, size, style, name)
        self.app = app
        self.__initVersionMonitor()
        self.Bind(wx.EVT_CLOSE,self.OnVerFrameClose)
        ResultEvent.BIND_EVENT(self, IMessageDef.EVT_SHOW_FEATURE, self.OnShowVerFeature)
        ResultEvent.BIND_EVENT(self, IMessageDef.EVT_SHOW_BROADCAST_MSG, self.OnShowBroadcastMsg)
        ResultEvent.BIND_EVENT(self, IMessageDef.EVT_VER_FEATUREUI_MAINFRAME, self.OnVerFeatureUIMsg)
    
    def __initVersionMonitor(self):
        self.pyVerMonitor = PyVersionMonitor(self, self)
        self.pyVerMonitor.start()
        
    def OnVerFrameClose(self, event):
        self.OnClose(event)
        self.pyVerMonitor.stopThread()
        self.app.ExitMainLoop()
        event.Skip()
        
    def OnClose(self, event):
        pass
    
    def OnShowVerFeature(self, event):
        UpgradeUI(self, event.data)
        
    def OnShowBroadcastMsg(self, event):
        strMsg = event.data["broadcastMsg"]["msgInfo"]
        wx.MessageBox(strMsg, "From Version Service Notify.")
    def OnVerFeatureUIMsg(self, event):
        print "Event...."
        if event.data == "CLOSE":
            self.pyVerMonitor.setUIIsShow(False)
        elif event.data == "EXIT":
            self.Close()
    
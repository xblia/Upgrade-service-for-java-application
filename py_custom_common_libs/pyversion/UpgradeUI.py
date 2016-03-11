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
Created on 2015-12-28

@author: xiaobolx
'''
import sys

from wx import App
import wx

from pyversion.ILog import ILog
from pyversion.IMessageDef import IMessageDef, ResultEvent
from pyversion.UpgradeService import UpgradeService


# Define notification event for thread completion
class UpgradeUI(wx.Frame):
    
    def __init__(self, parent, jsonInfo):
        uiStyle = wx.SYSTEM_MENU | wx.SYSTEM_MENU | wx.CAPTION | wx.CLOSE_BOX | wx.MINIMIZE_BOX
        super(UpgradeUI, self).__init__(None, -1, IMessageDef.VERSION_INFO, size=(400, 380), style=uiStyle)
        self.parent = parent
        self.jsonInfo = jsonInfo
        self.initUI()
        self.displayInfo()
        self.Center()
        self.Show()
        #Bind Event
        ResultEvent.BIND_EVENT(self, IMessageDef.EVT_UPGRADESERVICE_FEATUREUI_ID, self.onUserAction)
        
    def initUI(self):
        mainPanel = wx.Panel(self)
        mainPanel.SetBackgroundColour('0xcccccc')
        mainGridSizer = wx.GridBagSizer(5, 5)
        labelFont = wx.Font(9, wx.FONTFAMILY_MODERN, wx.FONTSTYLE_NORMAL, wx.FONTWEIGHT_BOLD, underline=False,
                           faceName="Microsoft YaHei", encoding=wx.FONTENCODING_DEFAULT) 
        contentFont = wx.Font(9, wx.FONTFAMILY_MODERN, wx.FONTSTYLE_NORMAL, wx.FONTWEIGHT_NORMAL, underline=False,
                           faceName="Microsoft YaHei", encoding=wx.FONTENCODING_DEFAULT) 
        self.infoLabel = wx.StaticText(mainPanel, label="Version:")
        self.infoLabel.SetFont(labelFont)
        self.infoSeparator = wx.StaticBox(mainPanel, label="Info")
        self.textContent = wx.TextCtrl(mainPanel, style=wx.TE_MULTILINE|wx.NO_BORDER)
        self.textContent.SetFont(contentFont)
        self.remindMeLateBtn = wx.Button(mainPanel, label="Remind Later", size=(-1, 35))
        self.upradeNowBtn = wx.Button(mainPanel, label="Upgrade Now")
        
        self.Bind(wx.EVT_CLOSE,self.OnClose)
        self.remindMeLateBtn.Bind(wx.EVT_BUTTON, self.OnBtnRemindMeLater)
        self.upradeNowBtn.Bind(wx.EVT_BUTTON, self.OnBtnUpgradeNow)
        
        mainGridSizer.Add(self.infoLabel, pos=(0, 0), span=(1, 6), flag=wx.LEFT|wx.RIGHT|wx.TOP|wx.EXPAND, border=10)
        textBoxSizer = wx.StaticBoxSizer(self.infoSeparator, wx.HORIZONTAL)
        textBoxSizer.Add(self.textContent, proportion=1, flag=wx.RIGHT|wx.LEFT|wx.EXPAND|wx.ALL)
        mainGridSizer.Add(textBoxSizer, pos=(1, 0), span=(3, 6), flag=wx.RIGHT|wx.LEFT|wx.EXPAND, border=10)
        
        btnGridSizer = wx.GridSizer(1, 2, 5, 5)
        btnGridSizer.Add(self.remindMeLateBtn, flag=wx.LEFT|wx.EXPAND,  border=10)
        btnGridSizer.Add(self.upradeNowBtn, flag=wx.RIGHT|wx.EXPAND, border=10)
        mainGridSizer.Add(btnGridSizer, pos=(4, 0), span=(1, 6), flag=wx.EXPAND|wx.BOTTOM, border=5)
        
        mainGridSizer.AddGrowableRow(1)
        mainGridSizer.AddGrowableCol(0)
        mainPanel.SetSizer(mainGridSizer)
        
    def displayInfo(self):
        if self.jsonInfo is None:
            self.textContent.AppendText("Get Version Info Error. There are some thing wrong with our server, please try later again.")
            return
        softName = self.jsonInfo["softName"]
        currVer = self.jsonInfo["currVersionName"]
        lastVer = self.jsonInfo["latestVersionName"]
        self.Title = softName + " " + IMessageDef.VERSION_INFO
        self.infoLabel.LabelText=softName + " " + currVer + " To " + lastVer
        
        versionDetail = self.jsonInfo["versionInfoDetail"]
        for i in range(len(versionDetail)):
            detailTitle = versionDetail[i]["title"]
            detailInfo = versionDetail[i]["detail"]
            self.textContent.AppendText(detailTitle + "\r\n")
            for j in range(0, len(detailInfo)):
                self.textContent.AppendText(detailInfo[j] + "\r\n")
            self.textContent.AppendText("\r\n")
    
    def onUserAction(self, event):
        if event.data == "CLOSE":
            self.Close()
            wx.PostEvent(self.parent, ResultEvent(IMessageDef.EVT_VER_FEATUREUI_MAINFRAME, "EXIT"))
    
    def OnClose(self, event):
        wx.PostEvent(self.parent, ResultEvent(IMessageDef.EVT_VER_FEATUREUI_MAINFRAME, "CLOSE"))    
        event.Skip()
        ILog.getLogger().info("Upgrade UI OnClose")
    
    def OnBtnRemindMeLater(self, event):
        self.Close()
    
    def OnBtnUpgradeNow(self, event):
        UpgradeService(self, self.jsonInfo).start()
    
    @staticmethod
    def startUpUpgradeUI(srcStrJson, jsonInfo):
        app = App()
        UpgradeUI(app, srcStrJson, jsonInfo)
        app.MainLoop()
        return UpgradeUI.optCode
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
import os
import sys
import wx
import deamon
from deamon.IPyDeamonConstant import ResultEvent
from deamon.UpgradeDownloadThread import UpgradeDownloadThread


class UpgradePyFrame(wx.Frame):
    VERSION_INFO = " Upgrade service v1.0 "

    def __init__(self, args):
        uiStyle = wx.SYSTEM_MENU | wx.SYSTEM_MENU | wx.CAPTION | wx.CLOSE_BOX | wx.MINIMIZE_BOX 
        super(UpgradePyFrame, self).__init__(None, -1, UpgradePyFrame.VERSION_INFO, size=(500, 200), style=uiStyle)
        self.initUI()
        self.Center()
        self.Show()
        ResultEvent.BIND_EVENT(self, deamon.IPyDeamonConstant.MSGID_NOTIFY_PROGRESS, self.onProgress)
        if not self.startUpgradeThread(args):
            sys.exit()
        
    def onCancel(self, event):
        sys.exit()
    
    def onProgress(self, event):
        if event.data.has_key("softName"):
            self.Title = event.data["softName"] + UpgradePyFrame.VERSION_INFO
        
        if event.data.has_key("info"):
            self.infoLabel.Label = event.data["info"]
            
        if event.data.has_key("val"):
            self.progressBar.SetValue(event.data["val"])
            
        if event.data.has_key("startup"):
            try:
                #subprocess.Popen(event.data["basePath"]+event.data["startup"], shell=False)
                os.startfile(event.data["basePath"]+event.data["startup"], "open")
            except Exception as e:
                print e
            sys.exit()
    def initUI(self):
        mainPanel = wx.Panel(self)
        mainPanel.SetBackgroundColour('0xcccccc')
        mainGridSizer = wx.GridBagSizer(5, 5)
        labelFont = wx.Font(9, wx.FONTFAMILY_MODERN, wx.FONTSTYLE_NORMAL, wx.FONTWEIGHT_LIGHT, underline=False,
                           faceName="Microsoft YaHei", encoding=wx.FONTENCODING_DEFAULT) 
        
        self.infoLabel = wx.StaticText(mainPanel, label="Collecting Info...")
        self.infoLabel.SetFont(labelFont)
        self.progressBar = wx.Gauge(mainPanel, range=100)
        self.btnCancel = wx.Button(mainPanel, label="CANCEL", size=(-1, 30))
        
        mainGridSizer.Add(self.infoLabel, pos=(0, 0), span=(1, 6), flag=wx.LEFT|wx.RIGHT|wx.TOP|wx.EXPAND, border=10)
        mainGridSizer.Add(self.progressBar, pos=(1, 0), span=(1, 7), flag=wx.TOP|wx.BOTTOM|wx.EXPAND, border=35)
        mainGridSizer.Add(self.btnCancel, pos=(2, 6), flag=wx.RIGHT|wx.BOTTOM|wx.EXPAND, border=10)
        
        self.btnCancel.Bind(wx.EVT_BUTTON, self.onCancel)
        mainGridSizer.AddGrowableRow(1)
        mainGridSizer.AddGrowableCol(0)
        mainPanel.SetSizer(mainGridSizer)
        
    def startUpgradeThread(self, args):
        print "startUpgradeThread", args
        if args is None or len(args) < 2:
            wx.MessageBox("Parameter error, upgrade service stopped."+os.getcwd())
            return False
        
        upDownThread = UpgradeDownloadThread(self, args)
        upDownThread.start()
        return True
        
if __name__ == '__main__':
    args = sys.argv
    app = wx.App()
    upgradeUI = UpgradePyFrame(args)
    app.MainLoop()
    
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
import wx

MSGID_NOTIFY_PROGRESS = wx.NewId()

BASE_URL = "http://xblia2-OptiPlex-9020:8023"

class ResultEvent(wx.PyEvent):
    
    """Simple event to carry arbitrary result data."""
    def __init__(self, eventId, data):
        """Init Result Event."""
        wx.PyEvent.__init__(self)
        self.SetEventType(eventId)
        self.data = data
        
    @staticmethod
    def BIND_EVENT(win, EventId, func):
        """Bind Event."""
        win.Connect(-1, -1, EventId, func)
            
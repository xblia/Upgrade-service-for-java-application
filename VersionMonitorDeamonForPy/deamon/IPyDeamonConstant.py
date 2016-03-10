#coding=utf-8
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
            
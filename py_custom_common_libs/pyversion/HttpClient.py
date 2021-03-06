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
import urllib
import urllib2
from pyversion.ILog import ILog

class HttpClient():
    def __init__(self):
        pass
    
    
    @staticmethod
    def request(fullUrl, message):
        proxy_support = urllib2.ProxyHandler({})
        opener = urllib2.build_opener(urllib2.HTTPHandler, proxy_support)
        f = opener.open(fullUrl)
        a = f.read()
        return a
    
    
    @staticmethod
    def requestWidthPost(fullUrl, message):
        try:
            proxy_support = urllib2.ProxyHandler({})
            req = urllib2.Request(fullUrl)  
            data = urllib.urlencode({"msg":message})  
            #enable cookie  
            opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(), proxy_support)  
            response = opener.open(req, data)  
            return response.read()
        except Exception as e:
            ILog.getLogger().error(e)
        return None
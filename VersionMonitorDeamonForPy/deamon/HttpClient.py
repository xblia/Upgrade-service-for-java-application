#coding=utf-8
'''
Created on 2015年12月28日

@author: xiaobolx
'''
import urllib
import urllib2

CHUNK = 16 * 1024

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
        proxy_support = urllib2.ProxyHandler({})
        req = urllib2.Request(fullUrl)  
        data = urllib.urlencode({"msg":message})  
        #enable cookie  
        opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(), proxy_support)  
        response = opener.open(req, data)  
        return response.read()
    
    @staticmethod
    def downloadFile(url, fileName):
        proxy_support = urllib2.ProxyHandler({})
        opener = urllib2.build_opener(urllib2.HTTPHandler, proxy_support)
        response = opener.open(url)
        with open(fileName, 'wb') as f:
            while True:
                chunk = response.read(CHUNK)
                if not chunk: break
                f.write(chunk)
                
if __name__ == '__main__':
    url = "http://xblia2-optiplex-9020:8023/EasyTranfer.zip"
    fileName = "EasyTranfer.zip"
    HttpClient.downloadFile(url, fileName)
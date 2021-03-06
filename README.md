# Upgrade-service-for-java-application
版本控制和推送组件，主要用于监测客户端的使用情况，向客户端推送消息，新版本软件向客户即时推送以达到自动更新版本的功能
  特性如下：
  1. 低侵入式的，Java软件中只需要在启动时调用一下服务组件即可，在python软件中只需要继承一个类即可完成版本的监测和新版本的推送服务
  2. 高稳定性，采用netty框架进行消息开发，持续30*24小时或更长时间的运行
  3. 安全性较高，采用json做为通信交换方式，并验证报文的头信息
  4. 高度可配置性，同一服务器可控制多个软件的版本，全可视化界面配置，可配置软件信息，更新内容特性描述等
  5. 即时性，同时支持上千用户的并发即时版本推送，在局域网可达到20MB/s的速率
  6. 多语言支持，目前支持python和java两种语言的接口，不过你也可以根据现有的接口开发其它语言支持调用
  
#现对其基本工程介绍如下：
  1. CommonUtils和CommonsDefUI是属于Utils的封装，CommonsDefUI里面有对其Swing UI的定制，使其更优雅
  2. VersionMonitorPro为服务端工程，部署在服务端
  3. VersionMonitorClient为客户端调用接口
  4. py_custom_common_libs为python语言的客户端调用接口
  4. VersionMonitorDeamon为版本升级守护进程
  5. VersionMonitorDeamonForPy为python语言的版本升级守护进程
  6. VersionMonitorMsg为消息中间键
  7. VersionInfoMaintain为版本可视化版本工具
  

#服务端部署说明：
  1. 使用VersionMonitorPro打包可运行的jar并拷贝version_cfg.xml到jar同级目录version_cfg.xml
  2. version_cfg.xml有可视化工具进行编辑
    
#可视化编辑工具：
  VersionInfoMaintain为可视化编辑工具，运行其主类为com\versionmaintain\panel\VersionMaintainMainFrame.java
  
#Java客户端调用
java客户端工程需要引用VersionMonitorClient和VersionMonitorMsg两个工程，将其加入lib
如果使用Eclipse，则Build Path--> Project---> add Project进行加入
调用形式如下：
  1. 客户端需要实现一接口：
    package com.cats.version.client;

    public interface IVersionInfoProvider
    {
    	String getSoftName();
    	int getVersionCode();
    	String getVersionName();
    }
  2. 启动服务组件：
      new ClientVersionMonitor(IVersionInfoProvider).startComponent();

  3. 客户端需要修改服务器的通信地址：
      1.  在打包为jar时，首次运行为产生一个配置文件在jar同级目录version_cfg.properties，打包更改其地址即可（建议使用这种方式)
      2.  可在VersionMonitorMsg工程中的Utils的postMsgAndGet的方法中修改(从开闭原则上来说不建议此种方法）
      
      
#python客户端调用
python客户端调用方法(python客户端需要使用cx_freeze进行打包导出，并且需要使用wxPython库（其为python的图形化界面库）
调用时只需要继承一类即可：PyVerFrame
然后在子类中调用：PyVerFrame.__init__(self,app, None, -1, strTitle, style=style)即可启动服务组件



#联系方式：
  1. 有使用问题都可以联系我：worldandyou@163.com
  2. 或者可以在csdn的博客中给我留言: tqtihihc


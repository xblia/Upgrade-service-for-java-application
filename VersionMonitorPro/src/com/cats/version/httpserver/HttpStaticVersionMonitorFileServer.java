/*
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
 */
 package com.cats.version.httpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;

/**
 * @author xblia2
 * Jun 12, 2015
 */
public final class HttpStaticVersionMonitorFileServer extends Thread
{

	public static final String VERSION = "v2.1";
	public static final String VERSION_DESC = "VersionMonitorService_" + VERSION;
	private static final int PORT = Integer.parseInt(System.getProperty("port", "8023"));
	protected static boolean OPEN_ACCESS = false;
    
    public HttpStaticVersionMonitorFileServer()
    {
    }
    
    public void startServer()
    {
    	this.start();
    }
    
    @Override
    public void run()
    {
    	try
        {
	        this.init();
        } catch (Exception e)
        {
	        e.printStackTrace();
        }
    }

	private void init() throws Exception
    {
		// Configure SSL.
        final SslContext sslCtx = null;

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new HttpStaticFileServerInitializer(sslCtx));

            Channel ch = b.bind(PORT).sync().channel();

            System.err.println("Open your web browser and navigate to http://127.0.0.1:" + PORT + '/');
            System.err.println("Welcome use " + VERSION_DESC);

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
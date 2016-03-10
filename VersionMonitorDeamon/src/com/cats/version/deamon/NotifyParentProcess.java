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
 package com.cats.version.deamon;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;

import com.cats.version.utils.IVersionConstant;
import com.cats.version.utils.Utils;

/**
 * @author xblia
 * 2015-08-20
 */
public class NotifyParentProcess extends Thread
{
	private int iPort;
	private boolean parentIsDied = false;
	public NotifyParentProcess(int iParentPort)
    {
		this.iPort = iParentPort;
    }
	
	@Override
	public void run()
	{
		if(iPort == -1 || iPort == 0)
		{
			return;
		}
		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;
		try
        {
			socket = new Socket();
	        socket.setSoTimeout(3000);
	        socket.connect(new InetSocketAddress("127.0.0.1", this.iPort));
	        out = socket.getOutputStream();
	        in = socket.getInputStream();
	        String notifyMsg = IVersionConstant.SOCKET_MSG_STARTUPOK + IVersionConstant.SOCKET_MSG_LINECHAR;
	        out.write(notifyMsg.getBytes(Charset.defaultCharset()));
	        out.flush();
	        Thread.sleep(2000);
	        parentIsDied = true;
	        int iEnd = in.read();
	        if(iEnd == -1)
	        {
	        	//Nothing
	        }
        } catch (Exception e)
        {
	        e.printStackTrace();
        }finally
        {
        	Utils.closeRes(in);
        	Utils.closeRes(out);
        	if(socket != null)
        	{
        		try
                {
	                socket.close();
                } catch (Exception e)
                {
	                e.printStackTrace();
                }
        	}
        }
	}
	
	public boolean parentProcessDied()
	{
		return this.parentIsDied;
	}

	public NotifyParentProcess notifyProc()
    {
		this.start();
		return this;
    }

}

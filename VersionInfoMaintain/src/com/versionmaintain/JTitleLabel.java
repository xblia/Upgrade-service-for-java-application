package com.versionmaintain;

import java.awt.Font;

import javax.swing.JLabel;

/**
 * @author xiaobolx
 * 2016年1月26日
 */
public class JTitleLabel extends JLabel
{

	public static Font FONT = new Font("Segoe UI", Font.BOLD, 12);
	/**
	 * 
	 */
    private static final long serialVersionUID = 6454588869144787280L;
    
    public JTitleLabel(String info)
    {
    	super(info);
    	
    	this.initView();
    }
    
    public void initView()
    {
    	this.setFont(FONT);
    }
}

package com.versionmaintain;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.cats.utils.SwingUtil;
import com.versionmaintain.files.EnFileType;
import com.versionmaintain.res.Resource;

/**
 * @author xiaobolx
 * 2016年1月25日
 */
public class FileTypeSelectionDlg extends JDialog implements ActionListener
{
	/**
	 * serialVersionUID
	 */
    private static final long serialVersionUID = 1L;
    private String title = "Version Maintain New File Type Selector";
    
    private JCheckBox binFileCheck;
    private JCheckBox xmlFileCheck;
    private JButton okBtn;
    private JButton cancelBtn;
    private boolean isOk = false;
    
    public FileTypeSelectionDlg()
    {
    	this.setTitle(title);
    	this.setIconImage(new ImageIcon(Resource.class.getResource("icon_maintenance.png")).getImage());
    	this.setModal(true);
    	
    	this.initView();
    	this.initEvent();
    	
    	this.pack();
    	this.setSize(this.getWidth() + 20, this.getHeight() + 20);
    	SwingUtil.centerWindow(this.getWidth(), this.getHeight(), this);
    	this.setVisible(true);
    }
    
	private void initView()
    {
    	JLabel tooltipLabel = new JLabel("Please Select File Type");
    	binFileCheck = new JCheckBox("BIN File", true);
    	xmlFileCheck = new JCheckBox("XML File");
    	okBtn = new JButton("OK");
    	cancelBtn = new JButton("CANCEL");
    	
    	this.getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
    	this.setLayout(new BorderLayout());
    	JPanel centerPanel = new JPanel(new GridLayout(2, 1));
    	centerPanel.add(binFileCheck);
    	centerPanel.add(xmlFileCheck);
    	
    	JPanel bottomPanel = new JPanel();
    	bottomPanel.setLayout(new GridLayout(1, 2));
    	bottomPanel.add(okBtn);
    	bottomPanel.add(cancelBtn);
    	
    	this.add(tooltipLabel, BorderLayout.NORTH);
    	this.add(centerPanel, BorderLayout.CENTER);
    	this.add(bottomPanel, BorderLayout.SOUTH);
    }
	
	private void initEvent()
	{
		binFileCheck.addActionListener(this);
		xmlFileCheck.addActionListener(this);
		okBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
			    super.windowClosing(e);
			    FileTypeSelectionDlg.this.dispose();
			}
		});
	}
    
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == binFileCheck)
		{
			xmlFileCheck.setSelected(!binFileCheck.isSelected());
		}
		else if(e.getSource() == xmlFileCheck)
		{
			binFileCheck.setSelected(!xmlFileCheck.isSelected());
		}else if(e.getSource() == okBtn)
		{
			this.isOk = true;
			this.dispose();
		}else if (e.getSource() == cancelBtn)
		{
			this.isOk = false;
			this.dispose();
		}
	}
	
	private EnFileType getSelectFileType()
    {
		if(!isOk)
		{
			return null;
		}
		
		if(binFileCheck.isSelected())
		{
			return EnFileType.enBinFile;
		}
		else if(xmlFileCheck.isSelected())
		{
			return EnFileType.enXmlFile;
		}
	    return null;
    }
	
	public static EnFileType waitingUseSelectFileType()
	{
		FileTypeSelectionDlg fileTypeSelection = new FileTypeSelectionDlg();
		return fileTypeSelection.getSelectFileType();
	}
}

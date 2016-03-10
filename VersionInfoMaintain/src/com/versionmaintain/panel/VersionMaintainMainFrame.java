package com.versionmaintain.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cats.utils.SwingUtil;
import com.cats.version.VersionInfo;
import com.cats.version.VersionInfoDetail;
import com.versionmaintain.FileTypeSelectionDlg;
import com.versionmaintain.JTitleLabel;
import com.versionmaintain.files.EnFileType;
import com.versionmaintain.files.FileDataMgr;
import com.versionmaintain.model.SoftwareNameComboBoxModel;
import com.versionmaintain.model.VersionInfoDetailTreeModel;
import com.versionmaintain.model.VersionInfoTableModel;
import com.versionmaintain.res.Resource;
import com.versionmaintain.utils.JTreeUtils;
import com.versionmaintain.utils.VersionServiceUtils;
/**
 * @author xiaobolx
 * 2016年1月19日
 */
public class VersionMaintainMainFrame extends JFrame implements ItemListener, ListSelectionListener
{
	/**
	 * serialVersionUID
	 */
    private static final long serialVersionUID = -1482123180433755683L;
    
	private static String TITLE = "Version Maintain v1.0 ";
	private int width;
	private int height;
	
	private JComboBox<String> softwareNameComboBox;
	private VersionInfoTablePanel versionInfoTablePanel;
	private VersionDetailPanel verDetailPanel;
	private IgnoreFileListPanel ignoreFileListPanel;
	private BaseInfoPanel versionBaseInfoPanel;
	private StatusPanel statusPanel;
	
	private VersionMaintainMainFrame()
    {
		width = (int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.7);
		height = (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.7);

		this.setIconImage(new ImageIcon(Resource.class.getResource("icon_maintenance.png")).getImage());
		this.setTitle(TITLE);
		this.setSize(width, height);
		SwingUtil.centerWindow(width, height, this);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.initView();
		this.setVisible(true);
    }

	private void initView()
    {
		statusPanel = new StatusPanel();
		JComponent topPanel = genTopPanel();
		JComponent centerPanel = genCenterPanel();
		JComponent bottomPanel = statusPanel.getComponent();
		
		JPanel versionMaintainPanel = new JPanel(new BorderLayout());
		versionMaintainPanel.add(topPanel, BorderLayout.NORTH);
		versionMaintainPanel.add(centerPanel, BorderLayout.CENTER);
		versionMaintainPanel.add(bottomPanel, BorderLayout.SOUTH);
		this.add(versionMaintainPanel, BorderLayout.CENTER);
		
    }

	//Combobox and toolbar.
	private JPanel genTopPanel()
    {
	    JLabel softNameLabel = new JTitleLabel(" Software ");
		softwareNameComboBox = new JComboBox<String>();
		softwareNameComboBox.addItemListener(this);
		softwareNameComboBox.setPreferredSize(new Dimension(-1, 30));
		softwareNameComboBox.setModel(new SoftwareNameComboBoxModel(null));
		
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		new MenuBarPanel(this, topPanel).initMenuBar();
		topPanel.add(softNameLabel, BorderLayout.WEST);
		topPanel.add(softwareNameComboBox, BorderLayout.CENTER);
		
	    return topPanel;
    }
	
	private JComponent genCenterPanel()
    {
		final JSplitPane splitPane = new JSplitPane(); 
		versionInfoTablePanel = new VersionInfoTablePanel(this);
        splitPane.add(versionInfoTablePanel.getComponent(), JSplitPane.LEFT);  
        splitPane.add(genVersionInfoPanel(), JSplitPane.RIGHT);  
        splitPane.addComponentListener(new ComponentAdapter() {  
            @Override  
            public void componentResized(ComponentEvent e) {  
                splitPane.setDividerLocation(1.0 / 3.0);  
            }  
        });  
		
	    return splitPane;
    }
	
	private Component genVersionInfoPanel()
    {
		JPanel mainPanel = new JPanel(new BorderLayout());
		versionBaseInfoPanel = new BaseInfoPanel();
		mainPanel.add(versionBaseInfoPanel.getBaseInfoComponent(), BorderLayout.NORTH);
		
		final JSplitPane centerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		verDetailPanel = new VersionDetailPanel();
		ignoreFileListPanel = new IgnoreFileListPanel();
		centerPane.add(verDetailPanel.getVersionDetailInfoPanel(), JSplitPane.TOP);  
		centerPane.add(ignoreFileListPanel.getComponent(), JSplitPane.BOTTOM);  
		centerPane.addComponentListener(new ComponentAdapter() {  
            @Override  
            public void componentResized(ComponentEvent e) {  
            	centerPane.setDividerLocation(2.0 / 3.0);  
            }  
        });
		mainPanel.add(centerPane, BorderLayout.CENTER);
	    return mainPanel;
    }
	
	
	@Override
    public void itemStateChanged(ItemEvent e)
    {
	    if(e.getSource() == softwareNameComboBox)
	    {
	    	Object selectObj = softwareNameComboBox.getSelectedItem();
	    	if(selectObj == null)
	    	{
	    		return;
	    	}
	    	List<VersionInfo> softwareVerInfos = FileDataMgr.getInstance().getSoftwareData(selectObj.toString());
	    	versionInfoTablePanel.getTable().setModel(new VersionInfoTableModel(softwareVerInfos));
	    	versionInfoTablePanel.initVersionInfoTableColumnWidth();
	    	if(versionInfoTablePanel.getTable().getModel().getRowCount() > 0)
	    	{
	    		versionInfoTablePanel.getTable().setRowSelectionInterval(0, 0);
	    	}else
	    	{
	    		verDetailPanel.getTree().setModel(null);
	    	}
	    }
    }

	@Override
    public void valueChanged(ListSelectionEvent e)
    {
	    if(e.getSource() == versionInfoTablePanel.getTable().getSelectionModel())
	    {
	    	int iSelectedRow = versionInfoTablePanel.getTable().getSelectedRow();
	    	if(iSelectedRow != -1)
	    	{
	    		VersionInfoTableModel verInfoModel = (VersionInfoTableModel)versionInfoTablePanel.getTable().getModel();
	    		List<VersionInfoDetail> softwareDetailInfos = verInfoModel.getSoftwareDetailInfos(iSelectedRow);
	    		verDetailPanel.getTree().setModel(new VersionInfoDetailTreeModel(verDetailPanel.getTree(), softwareDetailInfos));
	    		verDetailPanel.resetTreeMenu();
	    		
	    		JTreeUtils.expandAllNodes(verDetailPanel.getTree(), 0, verDetailPanel.getTree().getRowCount());
	    		versionBaseInfoPanel.initVersionBaseInfo(verInfoModel.getSoftwarInfo(iSelectedRow));
	    		ignoreFileListPanel.initIgnoreFileTable(verInfoModel.getSoftwareIgnoreFiles(iSelectedRow));
	    	}
	    }
    }
	
	public void onAddVersionAction()
	{
		Object softwareName = softwareNameComboBox.getSelectedItem();
		if(null != softwareName && !softwareName.toString().isEmpty())
		{
			VersionInfoTableModel verInfoTabModel = ((VersionInfoTableModel)versionInfoTablePanel.getTable().getModel());
			if(null != verInfoTabModel)
			{
				verInfoTabModel.addItem(softwareName.toString());
			}
		}
		versionInfoTablePanel.getTable().updateUI();
	}
	
	public void onRemoveVersionAction()
	{
		int iIndex = versionInfoTablePanel.getTable().getSelectedRow();
		if(-1 != iIndex)
		{
			VersionInfoTableModel verInfoTabModel = ((VersionInfoTableModel)versionInfoTablePanel.getTable().getModel());
			if(null != verInfoTabModel)
			{
				verInfoTabModel.removeItem(iIndex);
			}
		}
		verDetailPanel.getTree().setModel(null);
		versionInfoTablePanel.getTable().updateUI();
	}

	public void onNewSoftwareInfo(String softwareNameInfo)
    {
		SoftwareNameComboBoxModel softwareNameComboModel = (SoftwareNameComboBoxModel)softwareNameComboBox.getModel();
		if(softwareNameComboModel.addSoftInfoItem(softwareNameInfo))
		{
			softwareNameComboModel.setSelectedItem(softwareNameInfo);
			softwareNameComboBox.updateUI();
			itemStateChanged(new ItemEvent(softwareNameComboBox, 0, softwareNameInfo, 0));
		}else
		{
			JOptionPane.showMessageDialog(null, "Already Exists, Please Input Again!.", TITLE, JOptionPane.WARNING_MESSAGE);
		}
    }
	
	public void onDelSoftwareInfo()
	{
		Object selectItem = softwareNameComboBox.getSelectedItem();
		SoftwareNameComboBoxModel comboBoxModel = (SoftwareNameComboBoxModel)softwareNameComboBox.getModel();
		comboBoxModel.removeSoftwareItem(selectItem);
		if(comboBoxModel.getSize() > 0)
		{
			softwareNameComboBox.setSelectedIndex(0);
		}else
		{
			softwareNameComboBox.setSelectedIndex(-1);
		}
		softwareNameComboBox.updateUI();
	}
	
	public void onLoadFile(File file, EnFileType fileType, Map<String, List<VersionInfo>> loadData)
    {
		Set<String> softwareNameSet = loadData.keySet();
		if(softwareNameSet.size() > 0)
		{
			softwareNameComboBox.setModel(new SoftwareNameComboBoxModel(softwareNameSet));
			softwareNameComboBox.setSelectedIndex(0);
		}
		this.setTitle(TITLE + file.getAbsolutePath());
		openPerspective(fileType);
    }

	public void onCloseFileAction()
    {
		FileDataMgr.getInstance().closeFile();
		softwareNameComboBox.setModel(new SoftwareNameComboBoxModel(null));
		versionInfoTablePanel.getTable().setModel(new VersionInfoTableModel(null));
		versionBaseInfoPanel.emptyContent();
		verDetailPanel.getTree().setModel(new VersionInfoDetailTreeModel(verDetailPanel.getTree(), null));
		ignoreFileListPanel.emptyTable();
		this.setTitle(TITLE);
    }

	public void onSaveFile(String info)
    {
		statusPanel.onSaveFile(info);
    }

	public void onNewFile()
    {
		EnFileType fileType = FileTypeSelectionDlg.waitingUseSelectFileType();
		if(null != fileType)
		{
			JFileChooser chooser = new JFileChooser();
			chooser.showSaveDialog(this);
			File file = chooser.getSelectedFile();
			if(null != file)
			{
				file = fixFileWithExtendName(file, fileType);
				FileDataMgr.getInstance().newVersionInfoFile(file, fileType);
				this.setTitle(TITLE + file.getAbsolutePath());
			}
		}
		openPerspective(fileType);
    }
	
	private File fixFileWithExtendName(File file, EnFileType fileType)
    {
		if(fileType == null)
		{
			return file;
		}
		
		String extendName = fileType.getExtendName();
		if(!file.getName().endsWith(extendName))
		{
			return new File(file.getAbsolutePath()+extendName);
		}
	    return file;
    }

	private void openPerspective(EnFileType fileType)
	{
		if(fileType == EnFileType.enBinFile)
		{
			versionBaseInfoPanel.getBaseInfoComponent().setVisible(false);
			ignoreFileListPanel.getComponent().setVisible(false);
		}else if(fileType == EnFileType.enXmlFile)
		{
			versionBaseInfoPanel.getBaseInfoComponent().setVisible(true);
			ignoreFileListPanel.getComponent().setVisible(true);
		}
	}
	
	public static void main(String[] args)
    {
		VersionServiceUtils.initGracefulStyle(new Font("Microsoft YaHei", Font.PLAIN, 13));
		//SwingUtil.initNimbusStyle();
	    new VersionMaintainMainFrame();
    }
}
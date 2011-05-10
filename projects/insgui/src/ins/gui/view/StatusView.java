package ins.gui.view;

import ins.gui.lib.GridBagHelper;
import ins.gui.lib.IConstrained;
import ins.gui.lib.IModelSetter;
import ins.gui.model.Model;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class StatusView extends JPanel implements IModelSetter, IConstrained, Observer{

	public Model model;
	
	private JPanel infoMessagePanel;
	private JPanel progressBarPanel;
	
	private JLabel statusMsg;
	private JProgressBar progressBar;
	
	public StatusView()
	{
		this.setLayout(new BorderLayout());
		infoMessagePanel = new JPanel();
		progressBarPanel = new JPanel();
		
		statusMsg = new JLabel("Placeholder");
		progressBar = new JProgressBar();
		
		add(infoMessagePanel, BorderLayout.WEST);
		add(progressBarPanel, BorderLayout.EAST);
		infoMessagePanel.add(statusMsg);
		progressBarPanel.add(progressBar);
	}
	
	public void setModel(Model model) {
		this.model = model;
	}

	public GridBagConstraints getConstraints() {
		GridBagHelper gridBagHelper = new GridBagHelper(model);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = model.getIntValue("STATUS_VIEW_GRIDX");
		constraints.gridy = model.getIntValue("STATUS_VIEW_GRIDY");
		constraints.gridheight = model.getIntValue("STATUS_VIEW_GRIDHEIGHT");
		constraints.gridwidth = model.getIntValue("STATUS_VIEW_GRIDWIDTH");
		constraints.weightx = model.getDoubleValue("STATUS_VIEW_WEIGHTX");
		constraints.weighty = model.getDoubleValue("STATUS_VIEW_WEIGHTY");
		constraints.anchor = gridBagHelper.getGridBagConst("STATUS_VIEW_ANCHOR");
		constraints.fill = gridBagHelper.getGridBagConst("STATUS_VIEW_FILL");
		return constraints;
	}
	
	public void initProgressBar(int min, int max, int start, boolean indeterminate)
	{
		progressBar.setMinimum(min);
		progressBar.setMaximum(max);
		progressBar.setValue(start);
		progressBar.setIndeterminate(indeterminate);
	}
	
	public void setStatusMessage(String message, boolean append)
	{
		if(append)
		{
			statusMsg.setText(statusMsg.getText()+", "+message);
		}
		else
		{
			statusMsg.setText(message);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}

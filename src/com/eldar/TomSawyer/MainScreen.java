package com.eldar.TomSawyer;

import java.awt.Color;
//import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MainScreen extends JFrame {

	/**
	 *  
	 */
	private static final long serialVersionUID = 7678789780868366268L;
	private JPanel paintPanel;
	private JPanel controlPanel;
	private JPanel globalPanel;
	private JTextField depthText;
	private JTextField noiseText;
	private JComboBox modelCombo;
	private MainScreen me = this;

	public MainScreen() {
		this.addComponentListener(new ComponentAdapter() 
		{  
		        public void componentResized(ComponentEvent evt) {
		            me.resizeIt(me.getWidth(), me.getHeight());
		        }
		});		
		
		setTitle("Tom Sawyer Fence Paint Ink.");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);  

	   globalPanel = new JPanel(/*new FlowLayout()*/);
       globalPanel.setLayout(null);
       getContentPane().add(globalPanel);

       controlPanel = new JPanel(new FlowLayout());
       controlPanel.setBackground(Color.BLUE);
       globalPanel.add(controlPanel);
       
       // Depth:
       JLabel depthLabel = new JLabel("Depth:");
       depthLabel.setForeground(Color.YELLOW);
       controlPanel.add(depthLabel);
       depthText = new JTextField(" 5");
       controlPanel.add(depthText);

       // Noise:
       JLabel noiseLabel = new JLabel("Noise:");
       noiseLabel.setForeground(Color.YELLOW);
       controlPanel.add(noiseLabel);
       noiseText = new JTextField("20.0");
       controlPanel.add(noiseText);
       
       // Select model:
       modelCombo = new JComboBox();
       modelCombo.addItem("TopDown");  // #0
       modelCombo.addItem("BottomUp");  // #1
       controlPanel.add(modelCombo);

       // Run button.
       JButton runButton = new JButton("Run");
       runButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent event) {
        	   RunExperiment(Integer.parseInt(depthText.getText().trim()), 
        			   Double.parseDouble(noiseText.getText().trim()),
        			   modelCombo.getSelectedIndex());
          }
       });
       controlPanel.add(runButton);		
       
       // Quit button.
       JButton quitButton = new JButton("Quit");
       quitButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent event) {
               System.exit(0);
          }
       });
       controlPanel.add(quitButton);		
		
       paintPanel = new JPanel();
       paintPanel.setLayout(null);
       paintPanel.setBackground(Color.lightGray);
       globalPanel.add(paintPanel);					

       resizeIt(800, 500);
	}
	
	private void resizeIt(int width, int height) {
		final int controlIndent = 10;
		final int controlWidth = 120;
		setSize(width, height);
		
        globalPanel.setSize(new Dimension(width, height));
	    controlPanel.setSize(new Dimension(controlWidth, height));
        final int paintIndent = controlPanel.getWidth() + controlIndent;
        paintPanel.setBounds(paintIndent, 10, width - paintIndent - controlIndent, height - 40);
	}
	
	private void RunExperiment(int depth, double noise, int modelId) {
		Corp.SetDepthNoise(depth, noise);
		Corp c = modelId == 0 ? new CorpTopDown() : new CorpBottomUp();
		Graphics g;
	    g = paintPanel.getGraphics();
	    Dimension d = paintPanel.getSize();
	    g.clearRect(0, 0, d.width, d.height);
        c.Draw(g, d.height, d.width);		
        g.drawString("Noise: " + noiseText.getText(), 5, 15);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainScreen ms = new MainScreen();
				ms.setVisible(true);
			}
		});
	}
}

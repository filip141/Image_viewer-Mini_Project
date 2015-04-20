import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;


public class App_window{

	private JFrame frame;
	private String fpatch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App_window window = new App_window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App_window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(){
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		final JPanel panel_1 = new ImageObj();
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open...");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)  {
				   JFileChooser chooser = new JFileChooser();
				   FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "JPG & GIF Images", "jpg", "gif");
				    chooser.setFileFilter(filter);
				    int returnVal = chooser.showOpenDialog(null);
			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			        	fpatch = chooser.getSelectedFile().getAbsolutePath();
			    		((ImageObj) panel_1).readImage(fpatch);
			    		frame.setSize(((ImageObj) panel_1).img.getWidth(),((ImageObj) panel_1).img.getHeight());
			    		panel_1.revalidate();
			    		panel_1.repaint();
			        }
			       
			        
				}
		});

		mnFile.add(mntmOpen);

		
		JMenuItem mntmSaveAs = new JMenuItem("Save as");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(((ImageObj) panel_1).imReaded == true){
					JFileChooser chooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter(
					        "JPG & GIF Images", "jpg", "gif");
					int saveValue = chooser.showSaveDialog(null);
					 if (saveValue == JFileChooser.APPROVE_OPTION) {
				            try {
				                ImageIO.write(((ImageObj) panel_1).img, "png", new File(chooser
				                        .getSelectedFile().getAbsolutePath()));
				            } catch (IOException e) {
				                e.printStackTrace();
				            }
				        }
				}
				else{
					System.out.println("Error no image selected");
				}
			}
		});
		mnFile.add(mntmSaveAs);
		
		JMenuItem mntmShowContainingFolder = new JMenuItem("Show containing folder");
		mntmShowContainingFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SystemID sys = new SystemID();
				if(sys.isWindows()){
					try {
						Process p = new ProcessBuilder("explorer.exe", "/select," + fpatch).start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(sys.isLinux()){
					try {
						Process p = new ProcessBuilder("nautilus", fpatch).start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				}
			
		});
		mnFile.add(mntmShowContainingFolder);
		
		JMenuItem mntmProperties = new JMenuItem("Properties");
		mnFile.add(mntmProperties);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		mnFile.add(mntmClose);
		
		JMenu mnNewMenu = new JMenu("Edit");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Rotate left");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((ImageObj) panel_1).rotateCCw();
				frame.setSize(((ImageObj) panel_1).img.getWidth(),((ImageObj) panel_1).img.getHeight());
	    		panel_1.revalidate();
	    		panel_1.repaint();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmRotateRight = new JMenuItem("Rotate right");
		mntmRotateRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((ImageObj) panel_1).rotateCw();
				frame.setSize(((ImageObj) panel_1).img.getWidth(),((ImageObj) panel_1).img.getHeight());
	    		panel_1.revalidate();
	    		panel_1.repaint();
			}
		});
		mnNewMenu.add(mntmRotateRight);
		
		
		JMenuItem mntnZoomout = new JMenuItem("Zoom out");
		mntnZoomout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((ImageObj) panel_1).zoomOutImg();
				panel_1.revalidate();
				panel_1.repaint();
			}
		});
		mnNewMenu.add(mntnZoomout);
		
		JMenuItem mntnZoomin = new JMenuItem("Zoom in");
		mntnZoomin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((ImageObj) panel_1).zoomInImg();
				panel_1.revalidate();
				panel_1.repaint();
			}
		});
		mnNewMenu.add(mntnZoomin);
		
		JMenuItem mntmDelete = new JMenuItem("Delete");
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((ImageObj) panel_1).clearImg();
				frame.setSize(500,500);
	    		panel_1.revalidate();
	    		panel_1.repaint();
				File imgFile = new File(fpatch);
				imgFile.delete();
			}
		});
		mnNewMenu.add(mntmDelete);
		
		
		JMenu mnNewMenu_1 = new JMenu("View");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmFullscreen = new JMenuItem("Fullscreen");
		mnNewMenu_1.add(mntmFullscreen);
		
		JMenuItem mntmSidePanel = new JMenuItem("Side Panel");
		mnNewMenu_1.add(mntmSidePanel);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		

	}
		
	}



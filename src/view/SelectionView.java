package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.android.ddmlib.IDevice;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;

import src.ADB;

import java.awt.Window.Type;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SelectionView extends JFrame implements MouseListener {

	private JPanel contentPane;
	private JLabel lblCihazSeiniz;
	private JComboBox<String> comboBox;
	private JButton btnConnect;
	private JButton btnRefresh;
	private IDevice[] devices;
	private ADB adb;
	/**
	 * Create the frame.
	 */
	public SelectionView(ADB adb) {
		this.adb = adb;
		this.devices = adb.getDevices();
		setTitle("AndroController");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(249dlu;default):grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		lblCihazSeiniz = new JLabel("Cihaz Se\u00E7iniz:");
		contentPane.add(lblCihazSeiniz, "2, 2");
		
		comboBox = new JComboBox<String>();
		contentPane.add(comboBox, "2, 4, fill, default");
		for(IDevice device : devices){
			comboBox.addItem(device.getSerialNumber());
		}
		
		
		
		btnRefresh = new JButton("Yenile");
		contentPane.add(btnRefresh, "2, 6, left, default");
		
		btnConnect = new JButton("Ba\u011Flan");
		contentPane.add(btnConnect, "2, 8, right, default");
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == btnConnect){
			
			
		}else if(e.getSource()==btnRefresh){
			devices = adb.getDevices();
			for(IDevice device : devices){
				comboBox.addItem(device.getSerialNumber());
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}

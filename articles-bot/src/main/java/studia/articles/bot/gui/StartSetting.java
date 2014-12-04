package studia.articles.bot.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import studia.articles.bot.controller.Controller;
import studia.articles.bot.controller.ControllerListener;
import studia.articles.bot.controller.ControllerListenerImp;

public class StartSetting extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2694808371963837991L;
	private final JPanel contentPanel = new JPanel();
	private JTextField hostTextField;
	private JTextField portTextField;
	private JPanel proxyPanel;
	private JTextField socksPortTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			StartSetting dialog = new StartSetting();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public StartSetting() {
		setTitle("Proxy settings");
		setBounds(450, 280, 368, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JRadioButton doNotUseProxyRadioButton = new JRadioButton(
				"do not use proxy");
		doNotUseProxyRadioButton.setBounds(6, 7, 132, 23);
		doNotUseProxyRadioButton.setSelected(true);
		contentPanel.add(doNotUseProxyRadioButton);

		JRadioButton useProxyRadioButton = new JRadioButton("use proxy");
		useProxyRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					proxyPanel.setVisible(true);
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					proxyPanel.setVisible(false);
				}
			}
		});

		useProxyRadioButton.setBounds(6, 33, 109, 23);
		ButtonGroup bg = new ButtonGroup();
		bg.add(useProxyRadioButton);
		bg.add(doNotUseProxyRadioButton);
		contentPanel.add(useProxyRadioButton);
		{
			JButton okButton = new JButton("OK");
			okButton.setBounds(97, 127, 62, 23);
			contentPanel.add(okButton);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ControllerListener listener = new ControllerListenerImp();

					if (proxyPanel.isVisible()) {

						String host = hostTextField.getText();
						int socksPort = Integer.parseInt(socksPortTextField
								.getText());
						int throughPort = Integer.parseInt(portTextField
								.getText());
						new GuiController(new Controller(listener, host,
								throughPort,socksPort));
					} else {
						new GuiController(new Controller(listener));
					}

					StartSetting.this.dispose();
				}
			});
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Cancel");
			cancelButton.setBounds(169, 127, 72, 23);
			contentPanel.add(cancelButton);
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			cancelButton.setActionCommand("Cancel");
		}
		proxyPanel = new JPanel();
		proxyPanel.setVisible(false);
		proxyPanel.setBounds(6, 63, 346, 53);
		contentPanel.add(proxyPanel);
		proxyPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("host:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(70, 4, 35, 14);
		proxyPanel.add(lblNewLabel);

		hostTextField = new JTextField();
		hostTextField.setText("127.0.0.1");
		hostTextField.setBounds(107, 1, 217, 20);
		proxyPanel.add(hostTextField);
		hostTextField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("connection port:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(5, 29, 100, 14);
		proxyPanel.add(lblNewLabel_1);

		portTextField = new JTextField();
		portTextField.setText("80");
		portTextField.setBounds(107, 26, 60, 20);
		proxyPanel.add(portTextField);
		portTextField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("socks port:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(182, 29, 80, 14);
		proxyPanel.add(lblNewLabel_2);

		socksPortTextField = new JTextField();
		socksPortTextField.setText("8080");
		socksPortTextField.setBounds(264, 26, 60, 20);
		proxyPanel.add(socksPortTextField);
		socksPortTextField.setColumns(10);
	}
}

package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Data;

public class Gui {
	public static Data data = new Data(0);
	public static JFrame jFrame;
	public static Font formFont= new Font("Comic Sans MS",Font.BOLD,18);
	public static Color formColor = new Color(0,150,200);
	public static void hateNonOOMs() {
		jFrame = new JFrame("100 Days of Code");
		jFrame.setSize(500, 300);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JProgressBar jProgressBar = new JProgressBar(SwingConstants.HORIZONTAL,0,100);
		int value = data.getDaysPast();
		jProgressBar.setString("Day :"+value);
		jProgressBar.setStringPainted(true);
		jProgressBar.setValue(value);
		jProgressBar.setForeground(new Color(150,150,0));
		
		JPanel jp  = new JPanel();
		jp.setLayout(new BoxLayout(jp,BoxLayout.PAGE_AXIS));
		jp.add(jProgressBar);
		
		
		String quote="Bharat mata ki jay";
		try {
			quote = data.getRandomQuote();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		quote = "<html>"+quote+"</html>";		//For multiline labels : resize the frame to see the effect
		JLabel jLabel = new JLabel(quote);
		jLabel.setFont(formFont);
		jLabel.setForeground(new Color(150,200,230));
		jp.add(jLabel);
		
		JLabel projectTitelLabel = new JLabel("Project of the Day");
		projectTitelLabel.setFont(formFont);
		projectTitelLabel.setForeground(formColor);
		jp.add(projectTitelLabel);
		
		JTextField projectTitleTextField = new JTextField();
		projectTitleTextField.setToolTipText("Enter Project Title here ...");
		projectTitleTextField.setFont(formFont);
		projectTitleTextField.setForeground(formColor);
		jp.add(projectTitleTextField);
		
		JLabel githubLinkLabel = new JLabel("Github URL");
		githubLinkLabel.setFont(formFont);
		githubLinkLabel.setForeground(formColor);
		jp.add(githubLinkLabel);
		
		
		JTextField githubLinkTextField = new JTextField();
		githubLinkTextField.setToolTipText("Github Link here ...");
		githubLinkTextField.setFont(formFont);
		githubLinkTextField.setForeground(formColor);
		jp.add(githubLinkTextField);
		
		JButton jb = new JButton("Finish and Tweet");
		jb.setBackground(Color.WHITE);
		jb.setIcon(new ImageIcon("../Twitter-icon.png"));
		jb.setSize(100, 30);
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String projectTitle = "The app to tweet didn't work today";
				String githubLink = "No github link";
				Component[] comp = jFrame.getContentPane().getComponents();
				if(comp[0] instanceof JPanel) {
					System.out.println("wow");
					comp = ((JPanel)comp[0]).getComponents();
					for(Component c:comp) {
						if(c instanceof JTextField) {
							JTextField jTextField = (JTextField) c;
							if(jTextField.getToolTipText().contains("Project")) {
								projectTitle = jTextField.getText();
							}
							if(jTextField.getToolTipText().contains("Project")) {
								githubLink = jTextField.getText();
							}
						}
					}
				}
				data.tweet(projectTitle,githubLink);
			}
			
		});
		jp.add(jb);
		
		jFrame.getContentPane().add(jp);
		jFrame.setVisible(true);
	}
}

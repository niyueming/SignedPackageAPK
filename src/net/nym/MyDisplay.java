package net.nym;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Administrator on 2015/9/11 0011.
 * @author nym
 */
public class MyDisplay implements ActionListener {
    private final int WIDTH = 500;
    private final int HEIGHT = 500;
    String fileLocation = "";
    JTextField sourceEdit;
    JButton sourceButton;
    JTextField targetEdit;
    JButton targetButton;

    public MyDisplay(){

    }

    public MyDisplay(String file){
        fileLocation = file;
    }

    public void open(){
        JFrame frame = new JFrame();
        frame.setTitle("apk批量签名工具 1.0.0");
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage("logo.png"));
        frame.setResizable(false);  //禁止缩放
        frame.setSize(WIDTH, HEIGHT);
        //获取屏幕大小
        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width / 2 - WIDTH / 2, screenSize.height / 2 - HEIGHT / 2);

        Container content = frame.getContentPane();
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        content.setLayout(gridBagLayout);

        JPanel panelSourceDir = new JPanel(new BorderLayout(10,10));
        panelSourceDir.setBorder(BorderFactory.createTitledBorder("源"));
        JLabel sourceText = new JLabel("APK文件夹：");
        sourceText.setSize(100, 30);
        panelSourceDir.add(sourceText, BorderLayout.WEST);
        sourceEdit = new JTextField("");
        sourceEdit.setSize(200, 20);
        panelSourceDir.add(sourceEdit, BorderLayout.CENTER);
        sourceButton = new JButton("选择");
        sourceButton.setSize(200, 30);
        sourceButton.addActionListener(this);
        panelSourceDir.add(sourceButton, BorderLayout.EAST);
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 1.0;
       addComponent(content,panelSourceDir,gridBagLayout,gridBagConstraints);


        JPanel panelTargetDir = new JPanel(new BorderLayout(10,10));
        panelTargetDir.setBorder(BorderFactory.createTitledBorder("目标"));
        JLabel targetText = new JLabel("输出APK文件夹：");
        targetText.setSize(100, 30);
        panelTargetDir.add(targetText, BorderLayout.WEST);
        targetEdit = new JTextField("");
        targetEdit.setSize(200, 20);
        panelTargetDir.add(targetEdit, BorderLayout.CENTER);
        targetButton = new JButton("选择");
        targetButton.setSize(200, 30);
        targetButton.addActionListener(this);
        panelTargetDir.add(targetButton, BorderLayout.EAST);
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 1.0;
        addComponent(content, panelTargetDir, gridBagLayout, gridBagConstraints);

        JPanel signDir = new JPanel(new GridLayout(4,1));
        signDir.setBorder(BorderFactory.createTitledBorder("签名文件"));

        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 1.0;
        addComponent(content, signDir, gridBagLayout, gridBagConstraints);


        frame.setVisible(true);
    }

    private void addComponent(Container container,Component component,GridBagLayout gridbag,
                              GridBagConstraints c){
        gridbag.setConstraints(component, c);
        container.add(component);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(sourceButton)){
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jFileChooser.setCurrentDirectory(new File(fileLocation));
            jFileChooser.showOpenDialog(sourceButton);
            sourceEdit.setText(jFileChooser.getSelectedFile().getAbsolutePath());
            fileLocation = jFileChooser.getSelectedFile().getAbsolutePath();
        }
        if (e.getSource().equals(targetButton)){
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jFileChooser.setCurrentDirectory(new File(fileLocation));
            jFileChooser.showOpenDialog(targetButton);
            targetEdit.setText(jFileChooser.getSelectedFile().getAbsolutePath());
            fileLocation = jFileChooser.getSelectedFile().getAbsolutePath();
        }
    }
}

package net.nym;


import org.apache.commons.lang.StringUtils;

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
    JTextField keyStoreEdit;
    JButton keyStoreButton;
    JTextField keyStorePasswordEdit;
    JTextField keyStoreAliasEdit;
    JTextField keyStoreAliasPasswordEdit;
    JButton confirm;

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
        JPanel keyStorePath = new JPanel(new BorderLayout(10,10));
        JLabel keyStoreText = new JLabel("KeyStore路径：");
        keyStoreText.setSize(100, 30);
        keyStorePath.add(keyStoreText, BorderLayout.WEST);
        keyStoreEdit = new JTextField("");
        keyStoreEdit.setSize(200, 20);
        keyStorePath.add(keyStoreEdit, BorderLayout.CENTER);
        keyStoreButton = new JButton("打开");
        keyStoreButton.setSize(200, 30);
        keyStoreButton.addActionListener(this);
        keyStorePath.add(keyStoreButton, BorderLayout.EAST);
        signDir.add(keyStorePath);

        JPanel keyStorePassword = new JPanel(new BorderLayout(10,10));
        JLabel keyStorePasswordText = new JLabel("KeyStore密码：");
        keyStorePasswordText.setSize(100, 30);
        keyStorePassword.add(keyStorePasswordText, BorderLayout.WEST);
        keyStorePasswordEdit = new JTextField("");
        keyStorePasswordEdit.setSize(200, 20);
        keyStorePassword.add(keyStorePasswordEdit, BorderLayout.CENTER);
        signDir.add(keyStorePassword);

        JPanel keyStoreAlias = new JPanel(new BorderLayout(10,10));
        JLabel keyStoreAliasText = new JLabel("KeyStore别名：");
        keyStoreAliasText.setSize(100, 30);
        keyStoreAlias.add(keyStoreAliasText, BorderLayout.WEST);
        keyStoreAliasEdit = new JTextField("");
        keyStoreAliasEdit.setSize(200, 20);
        keyStoreAlias.add(keyStoreAliasEdit, BorderLayout.CENTER);
        signDir.add(keyStoreAlias);

        JPanel keyStoreAliasPassword = new JPanel(new BorderLayout(10,10));
        JLabel keyStoreAliasPasswordText = new JLabel("别名密码：");
        keyStoreAliasPasswordText.setSize(100, 30);
        keyStoreAliasPassword.add(keyStoreAliasPasswordText, BorderLayout.WEST);
        keyStoreAliasPasswordEdit = new JTextField("");
        keyStoreAliasPasswordEdit.setSize(200, 20);
        keyStoreAliasPassword.add(keyStoreAliasPasswordEdit, BorderLayout.CENTER);
        signDir.add(keyStoreAliasPassword);

        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 1.0;
        addComponent(content, signDir, gridBagLayout, gridBagConstraints);

        confirm = new JButton();
        confirm.setText("运行");
        confirm.addActionListener(this);
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 1.0;
        addComponent(content,confirm,gridBagLayout,gridBagConstraints);

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
        if (e.getSource().equals(keyStoreButton)){
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jFileChooser.setCurrentDirectory(new File(fileLocation));
            jFileChooser.showOpenDialog(keyStoreButton);
            keyStoreEdit.setText(jFileChooser.getSelectedFile().getAbsolutePath());
            fileLocation = jFileChooser.getSelectedFile().getParent();
        }
        if (e.getSource().equals(confirm)){
            final File sourceDir = new File(sourceEdit.getText().trim());
            final File targetDir = new File(targetEdit.getText().trim());
            final File keyStoreFile = new File(keyStoreEdit.getText().trim());
            final String keyStorePassword = keyStorePasswordEdit.getText().trim();
            final String keyStoreAlias = keyStoreAliasEdit.getText().trim();
            final String keyStoreAliasPassword = keyStoreAliasPasswordEdit.getText().trim();
            if (sourceDir == null | !sourceDir.isDirectory()){
                JOptionPane.showMessageDialog(confirm,"源文件夹错误");
                return;
            }
            if (targetDir == null | !targetDir.isDirectory()){
                JOptionPane.showMessageDialog(confirm,"目标文件夹错误");
                return;
            }
            if (keyStoreFile == null | !keyStoreFile.isFile()){
                JOptionPane.showMessageDialog(confirm,"keyStore文件错误");
                return;
            }
            if (StringUtils.isEmpty(keyStorePassword)){
                JOptionPane.showMessageDialog(confirm,"keyStore密码不能为空");
                return;
            }
            if (StringUtils.isEmpty(keyStoreAlias)){
                JOptionPane.showMessageDialog(confirm,"keyStore别名不能为空");
                return;
            }
            if (StringUtils.isEmpty(keyStoreAliasPassword)){
                JOptionPane.showMessageDialog(confirm,"别名密码不能为空");
                return;
            }

            final File[] files = sourceDir.listFiles();
            if (files != null){
                LoadingDialog.show(new JDialog(),new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0 ;i < files.length;i ++){
                            String signStr = Signer.signedAndAligned(keyStoreFile,keyStorePassword,keyStoreAlias,keyStoreAliasPassword,files[i],new File(targetDir,files[i].getName().substring(0,files[i].getName().lastIndexOf(".")) + "_signed.apk"),false);
                            System.out.println(signStr + "");
                        }
                    }
                }),"签名中……");


            }
        }
    }
}

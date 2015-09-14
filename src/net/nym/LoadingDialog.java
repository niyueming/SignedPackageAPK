package net.nym;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;



import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;


public class LoadingDialog implements ActionListener
{
    /**
     * 缺省状态提示
     */
    private static final String DEFAULT_STATUS = "Please Waiting";


    /**
     * “线程正在运行”提示框
     */
    private JDialog dialog;


    /**
     * 进度条
     */
    private JProgressBar progressBar;


    /**
     * 显示提示信息的Label
     */
    private JLabel lbStatus;


    /**
     * 取消按钮
     */
    private JButton btnCancel;


    /**
     * 父窗体
     */
    private Window parent;


    /**
     * 需要关联进度条的线程
     */
    private Thread thread;


    /**
     * 提示框的提示信息
     */
    private String statusInfo;


    /**
     * 结果提示
     */
    private String resultInfo;


    /**
     * 取消时的提示
     */
    private String cancelInfo;


    /**
     * 显示一个缺省状态提示且没有结果提示的等待框
     * @param parent 父窗体
     * @param thread 需要关联进度条的线程
     */
    public static void show(Window parent, Thread thread)
    {
        new LoadingDialog(parent, thread, DEFAULT_STATUS, null, null);
    }


    /**
     * 显示一个没有结果提示的等待框
     * @param parent 父窗体
     * @param thread 需要关联进度条的线程
     * @param statusInfo 当前线程动作提示信息
     */
    public static void show(Window parent, Thread thread, String statusInfo)
    {
        new LoadingDialog(parent, thread, statusInfo, null, null);
    }


    /**
     * 显示一个有结果提示的等待框
     * @param parent 父窗体
     * @param thread 需要关联进度条的线程
     * @param statusInfo 当前线程动作提示信息
     * @param resultInfo 线程结束后的提示信息（若为null则不提示）
     * @param cancelInfo 点击取消按钮后的提示信息（若为null则不提示）
     */
    public static void show(Window parent, Thread thread, String statusInfo,

                            String resultInfo, String cancelInfo)
    {
        new LoadingDialog(parent, thread, statusInfo, resultInfo, cancelInfo);
    }


    /**
     * 构造方法
     * @param parent 父窗体
     * @param thread 需要关联进度条的线程
     * @param statusInfo 当前线程动作提示信息
     * @param resultInfo 线程结束后的提示信息（若为null则不提示）
     * @param cancelInfo 点击取消按钮后的提示信息（若为null则不提示）
     */
    private LoadingDialog(Window parent, Thread thread, String statusInfo,

                          String resultInfo, String cancelInfo)
    {
        this.parent = parent;
        this.thread = thread;
        this.statusInfo = statusInfo;
        this.resultInfo = resultInfo;
        this.cancelInfo = cancelInfo;
        initUI();
        startThread();
        dialog.setVisible(true);
    }


    private void initUI()
    {
        if(parent instanceof Dialog)
        {
            dialog = new JDialog((Dialog)parent, statusInfo, true);
        }
        else if(parent instanceof Frame)
        {
            dialog = new JDialog((Frame)parent, statusInfo, true);
        }
        else
        {
            dialog = new JDialog((Frame)null, statusInfo, true);
        }


        final JPanel mainPane = new JPanel(null);
        progressBar = new JProgressBar();
        lbStatus = new JLabel("<html>" + statusInfo);
        btnCancel = new JButton("Cancel");
        progressBar.setIndeterminate(true);
        btnCancel.addActionListener(this);

        mainPane.add(progressBar);
        mainPane.add(lbStatus);
        mainPane.add(btnCancel);

        dialog.getContentPane().add(mainPane);
        dialog.setUndecorated(true);
        dialog.setResizable(false);
        dialog.setSize(390, 100);
        dialog.setLocationRelativeTo(parent);


        mainPane.addComponentListener(new ComponentAdapter()
        {
            public void componentResized(ComponentEvent e)
            {
                layout(mainPane.getWidth(), mainPane.getHeight());
            }
        });
    }


    private void startThread()
    {
        new Thread()
        {
            public void run()
            {
                try
                {
                    thread.start();
// 等待事务处理线程结束 
                    thread.join();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
// 关闭进度提示框 
                    dialog.dispose();

                    if(resultInfo != null && !resultInfo.trim().equals(""))
                    {
                        String title = "Info";
                        JOptionPane.showMessageDialog(parent, resultInfo, title,

                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }.start();
    }


    private void layout(int width, int height)
    {
        progressBar.setBounds(20, 20, 350, 15);
        lbStatus.setBounds(20, 50, 350, 25);
        btnCancel.setBounds(width - 85, height - 31, 75, 21);
    }


    @SuppressWarnings("deprecation")
    public void actionPerformed(ActionEvent e)
    {
        resultInfo = cancelInfo;
        thread.stop();
    }
}
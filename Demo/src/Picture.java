import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class Picture {
    private JLabel label;
    private JTextField JF;
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 400;
    public String FileName;
    public Picture(String FileName) {
        this.FileName = FileName;
        // TODO Auto-generated method stub
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                JFrame frame = new ImageViewerFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }



        class ImageViewerFrame extends JFrame {
            public ImageViewerFrame() {
                JPanel jp = new JPanel();
                jp.setLayout(new BorderLayout());
                setPreferredSize(new Dimension(600, 400));
                setTitle("ImageViewer");
                setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
                String name = "F:\\Work_Space\\代码更新\\QRcodeSecurity\\Gen\\"+FileName;
                JF = new JTextField(FileName);
                JF.setEditable(false);
                jp.add(JF, BorderLayout.NORTH);
                label = new JLabel(FileName);
                jp.add(label, BorderLayout.CENTER);
                label.setIcon(new ImageIcon(name));
                add(jp);
                JMenuBar menubar = new JMenuBar();
                setJMenuBar(menubar);
                JMenu menu = new JMenu("File");
                menubar.add(menu);
                JMenuItem exitItem = new JMenuItem("Close");
                menu.add(exitItem);

                exitItem.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        // TODO Auto-generated method stub
                        System.exit(0);
                    }
                });
            }

        }
}
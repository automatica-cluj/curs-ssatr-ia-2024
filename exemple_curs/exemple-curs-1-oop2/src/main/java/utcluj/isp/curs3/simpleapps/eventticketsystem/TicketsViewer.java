package utcluj.isp.curs3.simpleapps.eventticketsystem;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TicketsViewer extends JFrame {
    private JPanel mainPanel;
    private JList<String> fileList;
    private DefaultListModel<String> listModel;
    private JLabel imageLabel;

    public TicketsViewer(String folderPath) {
        super("File List Image Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());

        listModel = new DefaultListModel<String>();
        fileList = new JList<String>(listModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    System.out.println("SELECTED");
                    String selectedFileName = fileList.getSelectedValue();
                    if (selectedFileName != null) {
                        File selectedFile = new File(folderPath + File.separator + selectedFileName);
                        Image image = new ImageIcon(selectedFile.getAbsolutePath()).getImage();
                        imageLabel.setIcon(new ImageIcon(image));
                    }
                }
            }
        });
        JScrollPane listScrollPane = new JScrollPane(fileList);
        mainPanel.add(listScrollPane, BorderLayout.WEST);

        imageLabel = new JLabel();
        mainPanel.add(imageLabel, BorderLayout.CENTER);

        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                listModel.addElement(file.getName());
            }
        }

        getContentPane().add(mainPanel);
        setSize(320, 200);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        String folderPath = "C:\\Tickets\\";
        new TicketsViewer(folderPath);
    }
}

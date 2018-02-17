package craftedcart.smbrelquickpatcher;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SmbRelQuickPatcher {

    private JPanel contentPane;
    private JButton openRelButton;
    private JButton writeRelButton;
    private JLabel currentlyOpenLabel;
    private JList<EnumStageTheme> themesList;
    private JList<Stage> themesStageList;

    private static JFrame frame;
    private Path openFile;
    private byte[] fileData;
    private Stage[] stages = new Stage[421];

    private static final int THEME_LIST_OFFSET = 0x204E48;

    public SmbRelQuickPatcher() {
        EnumStageTheme[] themes = EnumStageTheme.values();
        themesList.setListData(themes);

        openRelButton.addActionListener(actionEvent -> {
            FileDialog fd = new FileDialog(frame, "Choose a REL", FileDialog.LOAD);
            fd.setFile("*.rel");
            fd.setVisible(true);
            String filename = fd.getDirectory() + fd.getFile();

            if (fd.getFile() == null) {
                System.out.println("File not opened");
            } else {
                System.out.println("Opening " + filename);
                openFile = Paths.get(filename);

                onFileOpened();
            }
        });

        themesList.addListSelectionListener(listSelectionEvent -> {
            for (Stage stage : themesStageList.getSelectedValuesList()) {
                stage.theme = themes[themesList.getSelectedIndex()];
            }

            themesStageList.updateUI();
        });

        writeRelButton.addActionListener(actionEvent -> {
            //Update fileData
            for (int i = 0; i < 421; i++) {
                fileData[THEME_LIST_OFFSET + i] = (byte) stages[i].theme.ordinal();
            }

            //Write the file
            try (FileOutputStream fos = new FileOutputStream(openFile.toString())) {
                fos.write(fileData);
                fos.close();
                JOptionPane.showMessageDialog(frame, "Wrote file", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error writing file", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void onFileOpened() {
        try {
            currentlyOpenLabel.setText(String.format("Currently open: %s", openFile.toString()));
            fileData = Files.readAllBytes(openFile);

            EnumStageTheme[] themes = EnumStageTheme.values();

            //Update theme stuff
            for (int i = THEME_LIST_OFFSET; i < THEME_LIST_OFFSET + 421; i++) {
                Stage stage = new Stage();
                stage.id = i - THEME_LIST_OFFSET;
                stage.theme = themes[fileData[i]];

                stages[i - THEME_LIST_OFFSET] = stage;
            }

            themesStageList.setListData(stages);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error reading file", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        frame = new JFrame("SMB REL Quick Patcher");
        SmbRelQuickPatcher win = new SmbRelQuickPatcher();

        frame.setContentPane(win.contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        openRelButton = new JButton();
        openRelButton.setText("Open REL");
        contentPane.add(openRelButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        currentlyOpenLabel = new JLabel();
        currentlyOpenLabel.setText("Currently open: None");
        contentPane.add(currentlyOpenLabel, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        writeRelButton = new JButton();
        writeRelButton.setText("Write changes to REL");
        contentPane.add(writeRelButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Stages");
        contentPane.add(label1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Assigned theme");
        contentPane.add(label2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        contentPane.add(scrollPane1, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        themesList = new JList();
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        themesList.setModel(defaultListModel1);
        themesList.setSelectionMode(0);
        scrollPane1.setViewportView(themesList);
        final JScrollPane scrollPane2 = new JScrollPane();
        contentPane.add(scrollPane2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        themesStageList = new JList();
        scrollPane2.setViewportView(themesStageList);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
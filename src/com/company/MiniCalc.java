package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.PrintWriter;
import java.util.Scanner;

public class MiniCalc extends JFrame implements ActionListener {
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;
    JButton button;

    JTextArea textArea;
    JPanel panel;
    JLabel label1;
    JMenu fileMenu;
    JMenuBar menuBar;
    JLabel result;
    JLabel label2;
    SimpleLinkedList list = null;

    public MiniCalc() {
        setTitle("Pipisya");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300, 250);
        setLocationRelativeTo(null);

        panel = new JPanel(new FlowLayout());
        textArea = new JTextArea(10, 10);
        label1 = new JLabel("Ввидите числа через пробел: ");
        label2 = new JLabel(" ");
        result = new JLabel("Ответ: ");

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);


        button = new JButton("Calc");

        button.addActionListener(this);


        panel.add(menuBar);
        panel.add(label1);
        panel.add(button);
        panel.add(label2);
        panel.add(textArea);
        panel.add(result);
        add(panel);

        setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==openItem) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
            fileChooser.setFileFilter(filter);

            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;
                try {

                    fileIn = new Scanner(file);

                    if (file.isFile()) {

                        while (fileIn.hasNext()) {
                            String line = fileIn.next() + " ";
                            textArea.append(line);
                        }

                    }

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } finally {
                    fileIn.close();
                }
            }
        }


        if (e.getSource() == saveItem) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int response = fileChooser.showSaveDialog(null);
            if(response == JFileChooser.APPROVE_OPTION){
                File file;
                PrintWriter fileOut = null;

                file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                try {
                    fileOut = new PrintWriter(file);
                    fileOut.println(list.findCountMax(list));

                } catch (FileNotFoundException | SimpleLinkedList.SimpleLinkedListException ex) {
                    ex.printStackTrace();
                }finally {
                    fileOut.close();
                }
            }
        }

        if (e.getSource() == exitItem) {
            System.exit(0);
        }

        if (e.getSource() == button) {
            try {
                list = new SimpleLinkedList();
//                String[] string = textField.getText().split(" ");
                String[] string = textArea.getText().split(" ");
                for (int i = 0; i < string.length; i++) {
                    list.addFirst(Integer.parseInt(string[i]));
                }


                try {
                    result.setText(String.valueOf(list.findCountMax(list)));
                } catch (SimpleLinkedList.SimpleLinkedListException ex) {
                    ex.printStackTrace();
                }
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(panel, "Некорректный ввод!");
            }
        }
    }
}
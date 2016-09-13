package ru.home.commandline;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

/**
 * Created by Alexey Altukhov on 13.09.2016.
 */
public class CommandDefine implements KeyListener {

    // переменная для хранения последней введенной команды
    String command = "";
    // текущая директория
    String currentPath = System.getProperty("user.dir");

    CommandLine parent;  // ссылка на консоль

    int key;    // код нажатой клавиши
    int pos = 0;    // позиция символа перевода строки \n в консоли

    // конструктор сохраняет ссылку на окно командной строки в переменной класса parent
    CommandDefine(CommandLine parent){
        this.parent = parent;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        key = e.getKeyCode();
        //JOptionPane.showConfirmDialog(null, key, "Test", JOptionPane.PLAIN_MESSAGE);

        // если нажали клавишу enter
        if (key == 10){

            if (pos == 0) {
                command = parent.console.getText();
                pos = 1;
                //JOptionPane.showConfirmDialog(null, list.get(list.size() - 1), "Test", JOptionPane.PLAIN_MESSAGE);
            } else {
                pos = parent.console.getText().lastIndexOf("\n");
                command = parent.console.getText().substring(pos+1);
            }

            // pwd — выводит текущую директорию
            if (command.equals("pwd")){
                // добавляем в консоль строку с директорией
                parent.console.setText(parent.console.getText()+"\n"+currentPath);

            // dir — выводит список файлов в текущей директории
            } else if (command.equals("dir")){
                // создаем ссылку на текущую папку
                File folder = new File(currentPath);
                // создаем список файлов и папок в текущей папке
                File[] listOfFiles = folder.listFiles();

                // по порядку добавляем имена файлов и папок в консоль
                for (int i = 0; i < listOfFiles.length; i++) {
                    parent.console.setText(parent.console.getText() + "\n" + listOfFiles[i].getName());
                }

            // cd <путь> — перейти в директорию, путь к которой задан первым аргументом
            } else if (command.substring(0,2).equals("cd")){
                if (command.length()>2) {
                    // путь для перехода
                    String perfectPath = command.substring(3);
                    // создаем ссылку на этот путь
                    File folder = new File(perfectPath);
                    // и проверяем его существование
                    if (folder.exists()) {
                        // если путь найден, то сохраняем его как текущий
                        currentPath = perfectPath;
                    } else {
                        parent.console.setText(parent.console.getText() + "\n" + perfectPath + " путь не найден");
                    }
                } else {
                    // если команда cd указана без аргумента
                    parent.console.setText(parent.console.getText() + "\n" + "Укажите путь к папке");
                }
            } else {
                parent.console.setText(parent.console.getText() + "\n" + command + " не является командой");
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}

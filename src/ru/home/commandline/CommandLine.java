package ru.home.commandline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Created by Alexey Altukhov on 12.09.2016.
 */
public class CommandLine {
    // создаем панель для командной строки
    JPanel windowCommandLine = new JPanel();

    // создаем область ввода команд(консоль), указываем кол-во строк и символов по умолчанию
    JTextArea console = new JTextArea(20,50);

    // добавляем полосу прокрутки для консоли
    JScrollPane sp = new JScrollPane(console);

    public CommandLine(){
        // задаем схему для панели
        windowCommandLine.setLayout(new FlowLayout());

        // делаем автоматический перевод слов на новую строку в консоли
        console.setLineWrap(true);

        // добавляем полосу прокрутки с консолью на панель
        windowCommandLine.add(sp);

        // создаем фрейм и добавляем на него панель
        JFrame frame = new JFrame("Командная строка");
        frame.getContentPane().add(windowCommandLine);

        // делаем размер окна достаточным для того,
        // чтобы вместить все компоненты
        frame.pack();
        // отображаем окно
        frame.setVisible(true);
        // размещаем его по центру экрана
        frame.setLocationRelativeTo(null);
        // завершаем программу при закрытии окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CommandDefine commDef = new CommandDefine();
        console.addKeyListener(commDef);

    }

    public static void main(String[] args) {
        // создаем экземпляр класса CommandLine
        CommandLine cmd = new CommandLine();
    }

    /**
     * Created by Alexey Altukhov on 13.09.2016.
     */
    private class CommandDefine extends KeyAdapter {

        // переменная для хранения последней введенной команды
        private String command = "";
        // текущая директория
        private String currentPath = System.getProperty("user.dir");

        private int key;    // код нажатой клавиши
        private int pos;    // позиция символа перевода строки \n в консоли

        @Override
        public void keyPressed(KeyEvent e) {

            key = e.getKeyCode();
            //JOptionPane.showConfirmDialog(null, key, "Test", JOptionPane.PLAIN_MESSAGE);

            // если нажали клавишу enter
            if (key == 10){

                // опредеояем позицию перевода строки
                pos = console.getText().lastIndexOf("\n");

                if (pos == -1) {
                    // если это первая строка в консоли, то берем текст консоли
                    command = console.getText();
                } else {
                    // иначе берем строку после последнего перевода строки
                    command = console.getText().substring(pos+1);
                }

                // если перед командой есть пробелы, удаляем их
                while (command.length() > 0 && command.substring(0,1).equals(" ")){
                    command = command.substring(1);
                }

                if (!command.equals("")){

                    // pwd — выводит текущую директорию
                    if (command.equals("pwd")){
                        // добавляем в консоль строку с директорией
                        console.setText(console.getText()+"\n"+currentPath);

                        // dir — выводит список файлов в текущей директории
                    } else if (command.equals("dir")){
                        // создаем ссылку на текущую папку
                        File folder = new File(currentPath);
                        // создаем список файлов и папок в текущей папке
                        File[] listOfFiles = folder.listFiles();

                        // по порядку добавляем имена файлов и папок в консоль
                        for (int i = 0; i < listOfFiles.length; i++) {
                            console.setText(console.getText() + "\n" + listOfFiles[i].getName());
                        }

                        // cd <путь> — перейти в директорию, путь к которой задан первым аргументом
                    } else if (command.length()>=2 && command.substring(0,2).equals("cd")){
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
                                console.setText(console.getText() + "\n" + perfectPath + " путь не найден");
                            }
                        } else {
                            // если команда cd указана без аргумента
                            console.setText(console.getText() + "\n" + "Укажите путь к папке");
                        }
                    } else {
                        console.setText(console.getText() + "\n" + command + " не является командой");
                    }

                }

            }

        }

    }
}

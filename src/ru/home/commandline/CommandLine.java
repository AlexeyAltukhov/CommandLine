package ru.home.commandline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Created by Alexey Altukhov on 12.09.2016.
 * Программа представляет собой вариант командной строки.
 * Поддерживает следующие команды:
 *  - dir — выводит список файлов в текущей директории
 *  - cd «путь» — перейти в директорию, путь к которой задан первым аргументом
 *  - pwd — выводит полный путь до текущей директории
 */
public class CommandLine {
    // создаем панель для командной строки
    private JPanel windowCommandLine = new JPanel();

    // создаем область ввода команд(консоль), указываем кол-во строк и символов по умолчанию
    private JTextArea console = new JTextArea(20,50);

    // добавляем полосу прокрутки для консоли
    private JScrollPane sp = new JScrollPane(console);

    // инициализация консоли
    private void initConsole(){
        // задаем схему для панели
        windowCommandLine.setLayout(new FlowLayout());

        // делаем автоматический перевод слов на новую строку в консоли
        console.setLineWrap(true);

        // добавляем слушатель на консоль
        console.addKeyListener(new InputCommandListener());

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

    }

    /**
     * Created by Alexey Altukhov on 13.09.2016.
     * inner class для прослушивания нажатий клавиш и выполнения команд
     */
    private class InputCommandListener extends KeyAdapter {

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
            if (key == KeyEvent.VK_ENTER){

                // определяем позицию перевода строки
                pos = console.getText().lastIndexOf("\n");

                if (pos == -1) {
                    // если это первая строка в консоли, то берем текст консоли
                    command = console.getText();
                } else {
                    // иначе берем строку после последнего перевода строки
                    command = console.getText().substring(pos+1);
                }

                // если перед командой есть пробелы, удаляем их
                if (command.length() > 0) {
                    command = command.trim();
                }

                if (!command.isEmpty()){

                    // pwd — выводит текущую директорию
                    if ("pwd".equalsIgnoreCase(command)){
                        // добавляем в консоль строку с директорией
                        console.setText(console.getText()+"\n"+currentPath);

                        // dir — выводит список файлов в текущей директории
                    } else if ("dir".equalsIgnoreCase(command)){
                        // создаем ссылку на текущую папку
                        File folder = new File(currentPath);
                        // создаем список файлов и папок в текущей папке
                        File[] listOfFiles = folder.listFiles();

                        // по порядку добавляем имена файлов и папок в консоль
                        if (listOfFiles != null && listOfFiles.length > 0){
                            for (File file: listOfFiles) {
                                console.setText(console.getText() + "\n" + file.getName());
                            }
                        }

                        // cd <путь> — перейти в директорию, путь к которой задан первым аргументом
                    } else if (command.length()>=2 && "cd".equalsIgnoreCase(command.substring(0,2))){
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
    public static void main(String[] args) {
        // создаем экземпляр класса CommandLine
        CommandLine cmd = new CommandLine();
        cmd.initConsole();
    }
}

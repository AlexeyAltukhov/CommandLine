package ru.home.commandline;

import javax.swing.*;
import java.awt.*;

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

    CommandLine(){
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

        CommandDefine commDef = new CommandDefine(this);
        console.addKeyListener(commDef);

    }

    public static void main(String[] args) {
        // создаем экземпляр класса CommandLine
        CommandLine cmd = new CommandLine();
    }

}

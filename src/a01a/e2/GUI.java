package a01a.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logic logic;
    private int counter =  1;

    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 70 * size);
        logic = new LogicImpl();

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            var jb = (JButton) e.getSource();
            Position position = cells.get(jb);
            logic.hit(position);
            for (var entry : cells.entrySet()) {
                JButton btn = entry.getKey();
                Position pos = entry.getValue();
                if(logic.isVertex(pos)){
                    btn.setText(String.valueOf(counter%4+1));
                    counter++;
                }
                else if(logic.isOver() && logic.isSelected(pos)){
                    btn.setText("o");
                }
                else if(!logic.isOver() && logic.isSelected(pos)){
                    btn.setText("*");
                }else{
                    btn.setText("");
                }
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton();
                this.cells.put(jb, new Position(j, i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
}

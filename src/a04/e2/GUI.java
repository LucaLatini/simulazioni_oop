package a04.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private final Map<JButton, Position> cells = new HashMap<>();
    private Logic logic;
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 70 * size);
        List<Position> centers = List.of(new Position(1, 1),
                new Position(size / 2, size / 2),
                new Position(size - 2, size - 2));
        logic = new LogicImpl(size);
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            var jb = (JButton) e.getSource();
            Position pos = cells.get(jb);
            this.logic.hit(pos);
            this.updateView();
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton();
                this.cells.put(jb, new Position(j, i));
                jb.addActionListener(al);
                for (Position position : centers) {
                    if (Math.max(Math.abs(position.x() - j), Math.abs(position.y() - i)) == 1) {
                        jb.setEnabled(true);
                        break;
                    } else {
                        jb.setEnabled(false);
                    }
                }
                panel.add(jb);
            }
        }
        this.setVisible(true);
        this.updateView();
    }

    private void updateView(){
        for (var entry : cells.entrySet()) {
            if(entry.getValue().equals(this.logic.markedPosition())){
                entry.getKey().setText("*");
            }else{
                entry.getKey().setText("");
            }
        }
    }
}

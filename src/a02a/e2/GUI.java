package a02a.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private final Map<JButton, Position> cells = new HashMap<>();
    private Logic logic;

    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 70 * size);
        logic = new LogicImpl(size);
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            var jb = (JButton) e.getSource();
            Position pos = cells.get(jb);
            Position newPosition = this.logic.hit();
            for (var entry : this.cells.entrySet()) {
                if (entry.getValue().equals(newPosition)) {
                    entry.getKey().setText("*");
                } else if (!this.logic.isObstacle(entry.getValue())) {
                    entry.getKey().setText("");
                }

            }
            if (logic.isOver()) {
                logic.reset();
                logic = new LogicImpl(size);
                resetGui();
                return;
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (j == 0 || j == size - 1 || i == 0 || i == size - 1) {
                    final JButton jb = new JButton();
                    this.cells.put(jb, new Position(j, i));
                    jb.addActionListener(al);
                    if (logic.isObstacle(new Position(j, i))) {
                        panel.add(jb);
                        jb.setText("o");
                    } else if (logic.isStart(new Position(j, i))) {
                        panel.add(jb);
                        jb.setText("*");
                    } else {
                        panel.add(jb);
                    }
                } else {
                    final JButton jb = new JButton();
                    jb.setEnabled(false);
                    this.cells.put(jb, new Position(j, i));
                    jb.addActionListener(al);
                    panel.add(jb);
                }
            }
        }
        this.setVisible(true);
    }

    private void resetGui() {
        cells.forEach((k, v) -> {
            k.setText("");

            if (logic.isObstacle(v)) {
                k.setText("o");
            } else if (logic.isStart(v)) {
                k.setText("*");
            }
        });
    }
}

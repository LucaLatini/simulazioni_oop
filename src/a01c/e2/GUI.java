package a01c.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private final Map<JButton, Position> cells = new HashMap<>();
    private Logic logic;
    private int count = 1;
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 70 * size);
        logic = new LogicImpl();
    
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            var jb = (JButton) e.getSource();
            Position pos = cells.get(jb);
            if(logic.isOver()){
                logic.reset();
                resetGui();
                return;
            }

            if(logic.hit(pos)){
                jb.setText(String.valueOf(this.logic.Size()));
                if(this.logic.isOver()){
                    for (var entry  : cells.entrySet()) {
                        if(this.logic.isSelected(entry.getValue())){
                            entry.getKey().setText("*");
                        }
                        
                    }
                }
            }else{
                logic.reset();
                resetGui();
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
    
    private void resetGui() {
        cells.forEach((k, v) -> k.setText(""));
    }
}
package a05.e2;

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
        logic = new LogicImpl();
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            var jb = (JButton) e.getSource();
            Position pos = cells.get(jb);
            if(this.logic.hit(pos)){
                for (var entry : cells.entrySet()) {
                    if(this.logic.isMarked(entry.getValue())){
                        entry.getKey().setText("*");
                    }else{
                        entry.getKey().setText("");
                    }

                }

            }else{
                if(this.logic.isOver()){
                    this.updateView();
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

    private void updateView(){
        this.cells.forEach((k,v)->{
            k.setText("");
        });
        this.logic.reset();
    }
}

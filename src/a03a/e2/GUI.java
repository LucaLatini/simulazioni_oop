package a03a.e2;

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
        this.logic = new LogicImpl(size);

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
                if( this.logic.isObstacle(new Position(j,i))){
                    jb.setText("o");
                }else{
                    jb.setText("");
                }

                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    private void updateView(){
        if (this.logic.isOver()) {
        this.logic.reset();
        this.clearView();
        return; // Usciamo perché la vista è già stata pulita da clearView()
    }
        for (var entry : cells.entrySet()) {
            if(this.logic.isContained(entry.getValue())){
                entry.getKey().setText("*");
            }else if(this.logic.isObstacle(entry.getValue())){
                entry.getKey().setText("o");
            }else{
                entry.getKey().setText("");
            }

        }
    }
    private void clearView() {
    for (var entry : cells.entrySet()) {
        JButton jb = entry.getKey();
        Position p = entry.getValue();

        // Ripristiniamo lo stato iniziale: ostacoli o vuoto
        if (this.logic.isObstacle(p)) {
            jb.setText("o");
        } else {
            jb.setText("");
        }
    }
}
}

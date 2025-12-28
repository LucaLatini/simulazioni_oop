package a06.e1;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private final Map<JButton, Position> cells = new HashMap<>();
    private Random random = new Random();
    private Position randomPosition;
    private Logic logic;
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 70 * size);
        int x = random.nextInt(size);
        int y = random.nextInt(size);
        randomPosition = new Position(x,y);
        logic = new LogicImpl(size, randomPosition);
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
                panel.add(jb);
                if((j==randomPosition.x()) || (i == randomPosition.y() && j> randomPosition.x())){
                    jb.setEnabled(true);
                }
                else{
                    jb.setEnabled(false);
                }

            }
        }
        this.setVisible(true);
    }

    private void updateView(){
        for (var entry : cells.entrySet()) {
            if(this.logic.isSelected(entry.getValue())){
                entry.getKey().setText("*");
            }else{
                entry.getKey().setText("");
            }
        }
    }
}

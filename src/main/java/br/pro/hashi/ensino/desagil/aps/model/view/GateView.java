package br.pro.hashi.ensino.desagil.aps.model.view;

import br.pro.hashi.ensino.desagil.aps.model.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.model.Light;
import br.pro.hashi.ensino.desagil.aps.model.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GateView extends FixedPanel implements ActionListener, MouseListener {
    private final Gate gate;
    private final JCheckBox[] input;
    private final Light output;
    private final Image image;
    private Color color;

    public GateView(Gate gate) {
        super(350, 200);
        this.gate = gate;

        input = new JCheckBox[gate.getInputSize()];
        // Se o output for 1, o círculo acenderá verde.
        output = new Light(0, 255, 0);

        //Adicionando a quantidade de checkbox necessária
        //dependendo do Gate escolhido, visto que por exemplo,
        //o gate NOT apresenta apenas 1 entrada (gate.inputSize = 1)
        //ao passo que AND apresenta 2 entradas (gate.inputSize = 2)
        for (int i = 0; i < input.length; i++) {
            input[i] = new JCheckBox();
            add(input[i]);
            input[i].addActionListener(this);
        }

        //Adicionando checkbox nas posições a partir de pixels.
        //Logo, se o gate apresenta mais de 1 entrada (Caso de todos
        //menos o NOT, há uma regra para posicionamento dos checkboxes.
        if (input.length > 1) {
            input[0].setLocation(63, 60);
            input[0].setSize(20, 25);
            input[1].setLocation(63, 103);
            input[1].setSize(20, 25);
        } else {
            input[0].setLocation(45, 88);
            input[0].setSize(25, 25);
        }
        output.connect(0, gate);
        color = Color.BLACK;

        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);
        addMouseListener(this);

        update();
    }

    private void update() {
        //Para cada entrada, deve-se checar se a mesma está selecionada.
        //Se sim, deve-se passar ao Switch como se fosse o valor "1", afirmando
        //que a porta i da entrada foi selecionada.
        //Se não, admite-se que não houve nenhuma seleção, ou seja, valor "0".
        //Após a determinação dos valores das entradas, deve-se conectar o gate
        //com tais resultados, e para isso foi utilizado de uma iteração com for.
        for (int i = 0; i < input.length; i++) {
            Switch interruptor = new Switch();
            if (input[i].isSelected()) {
                interruptor.turnOn();
            } else {
                interruptor.turnOff();
            }
            gate.connect(i, interruptor);
        }

        //Leitura do resultado da conexão estabelecida, agora utilizando cores.
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Descobre em qual posição o clique ocorreu.
        int x = e.getX();
        int y = e.getY();

        if (Math.sqrt(Math.pow((x - 288), 2) + Math.pow((y - 97), 2)) <= 10) {

            color = JColorChooser.showDialog(this, null, color);
            output.setColor(color);

            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 20, 20, 320, 160, this);

        g.setColor(output.getColor());
        g.fillOval(278, 87, 20, 20);

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}

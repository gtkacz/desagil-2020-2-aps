package br.pro.hashi.ensino.desagil.aps.model.view;

import br.pro.hashi.ensino.desagil.aps.model.model.Gate;
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
    private final JCheckBox output;
    private Color color;
    private final Image image;

    public GateView(Gate gate) {
        this.gate = gate;

        input = new JCheckBox[gate.getInputSize()];
        output = new JCheckBox();

        JLabel inputLabel = new JLabel("Entrada:");
        JLabel outputLabel = new JLabel("Saída:");

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(inputLabel);
        //Adicionando a quantidade de checkbox necessária
        //dependendo do Gate escolhido, visto que por exemplo,
        //o gate NOT apresenta apenas 1 entrada (gate.inputSize = 1)
        //ao passo que AND apresenta 2 entradas (gate.inputSize = 2)
        for (int i = 0; i < input.length; i++) {
            input[i] = new JCheckBox();
            add(input[i]);
            input[i].addActionListener(this);
        }

        add(outputLabel);
        add(output);

        // Usamos esse carregamento nos Desafios, vocês lembram?
        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);
        output.setEnabled(false);

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

        //Leitura do resultado da conexão estabelecida.
        boolean saida = gate.read();
        output.setSelected(saida);
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
}

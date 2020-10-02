package br.pro.hashi.ensino.desagil.aps.model;

public class OrGate extends Gate{
    private final NandGate or;

    public OrGate() {
        super("OR", 1);

        or = new NandGate();
    }

    @Override
    public boolean read() {
        return false;
    }

    @Override
    public void connect(int inputIndex, Emitter emitter) {

    }
}

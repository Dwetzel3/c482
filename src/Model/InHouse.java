package Model;

public class InHouse extends Part {
    private int MachineId;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        setMachineId(machineId);
    }

    public void setMachineId(int machineId) {
        this.MachineId = machineId;
    }

    public Integer getMachineId() {
        return MachineId;
    }
}
package com.wgu.c482;
/** The InHouse class extends Part and handles the machineID*/
public class InHouse extends Part {
    //
    private int machineId;

    //
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /**
     * @param machineId the machine id to set
     * */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }
    /**
     * @return the machine id
     * */
    public int getMachineId(){
        return this.machineId;
    }
}

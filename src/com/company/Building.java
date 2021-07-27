package com.company;
/**
 * represents a building in the game. has a height 0 to size.
 */
public class Building{
    private int height;
    private int maxheight;

    public Building(int maxheight){
        this.height = 0;
        this.maxheight = maxheight;
    }

    public Building(int maxheight, int height){
        this.maxheight = maxheight;
        if(0 <= height && height <= maxheight){
            this.height = height;
        }
        else{
            this.height = 0;
        }
    }

    public int get_height(){
        return this.height;
    }

    public boolean set_height(int height){
        if(0 <= height && height <= this.maxheight){
            this.height = height;
            return true;
        }
        else{
            return false;
        }
    }
}

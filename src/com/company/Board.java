package com.company;

import java.util.ArrayList;
import java.util.List;

public class Board{
    /**
     * represents a board in the game.
     * @param size the board will be sizeXsize;
     * @param left an array of values that are needed to see in the left column. length size;
     * @param top an array of values that are needed to see in the top column. length size;
     */
    private int size;
    private int[] left;
    private int[] top;
    private Building[][] buildings;
    private int[][] solution;

    public Board(int size){
        this.size = size;
        this.buildings = new Building[size][size];
        for(int column = 0 ; column < size ; column++) { //fill the board
            for (int row = 0; row < size; row++) {
                this.buildings[row][column] = new Building(size);
            }
        }
        this.solution = new int[size][size];
    }
    public Board(int size, int[] left, int[] top){ //make sure to check size == len
        this.size = size;
        this.left = left;
        this.top = top;
        this.buildings = new Building[size][size];
        for (int i = 0 ; i < size ; i++){
            for (int j = 0 ; j < size ; j++){
                buildings[i][j] = new Building(size);
            }
        }
    }
    public int[][] get_solution(){
        return this.solution;
    }

    public void set_solution(Building[][] solution){
        for (int i = 0 ; i < size ; i++){
            for (int j = 0 ; j < size ; j++){
                this.solution[i][j] = solution[i][j].get_height();
            }
        }
    }

    public Building[][] get_buildings(){
        return this.buildings;
    }

    public int get_solution_height(int row, int column){
        return this.solution[row][column];
    }

    public void set_sols(int[] arr, boolean is_top){
        if(is_top){
            this.top = arr;
        }
        else{
            this.left = arr;
        }
    }

    public void clear(){
        for (int i = 0 ; i < size ; i++){
            for (int j = 0 ; j < size ; j++){
                this.buildings[i][j].set_height(0);
            }
        }
    }

    public void print_board(){
        //print the top layer
        System.out.print("    ");
        for (int sight : top) {
            System.out.print(Integer.toString(sight) + "   ");
        }
        System.out.println("\n    " + new String(new char[size*4-3]).replace("\0", "-"));
        for (int row = 0 ; row < this.size ; row++) {
            System.out.print(Integer.toString(this.left[row]) + " | ");
            for (Building tower : this.buildings[row]){
                if(tower.get_height() != 0){
                    System.out.print(Integer.toString(tower.get_height()) + "   ");
                }
                else{
                    System.out.print("_   ");
                }
            }
            System.out.print("\n");
        }
    }

    public boolean update_building(int height, int row, int column){
        Building b = this.buildings[row][column];
        if(height == 0){
            b.set_height(0);
            return true;
        }
        for(Building building: this.buildings[row]){
            if(building.get_height() == height){
                return false;
            }
        }
        for(int r = 0 ; r < this.size ; r++){
            if(this.buildings[r][column].get_height() == height){
                return false;
            }
        }
        boolean result = b.set_height(height);
        return result;
    }

    private boolean check_sightline(int offset, boolean is_top){
        if(!is_top){
            int sight_amount = this.left[offset];
            return this.calc_sightline(offset,is_top) == sight_amount;
        }
        else{
            int sight_amount = this.top[offset];
            return this.calc_sightline(offset,is_top) == sight_amount;
        }
    }

    public int calc_sightline(int offset, boolean is_top){
        if(!is_top){
            int sight_counted = 1;
            int maxprev = this.buildings[offset][0].get_height();
            for (int column = 1 ; column < this.size ; column++){
                Building curr = this.buildings[offset][column];
                if(curr.get_height() > maxprev){
                    ++sight_counted;
                    maxprev = curr.get_height();
                }
            }
            return sight_counted;
        }
        else{
            int sight_counted = 1;
            int maxprev = this.buildings[0][offset].get_height();
            for (int row = 1 ; row < this.size ; row++){
                Building curr = this.buildings[row][offset];
                if(curr.get_height() > maxprev){
                    ++sight_counted;
                    maxprev = curr.get_height();
                }
            }
            return sight_counted;
        }
    }

    public List<Integer> check(boolean is_top){
        List<Integer> errors = new ArrayList<Integer>();
        if(is_top){
            for(int row = 0 ; row < this.size ; row++){
                if(!check_sightline(row, true)){
                    errors.add(row);
                }
            }
        }
        else{
            for(int column = 0 ; column < this.size ; column++){
                if(!check_sightline(column, false)){
                    errors.add(column);
                }
            }
        }
        return errors;
    }
}

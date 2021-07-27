package com.company;
import java.util.*;

public class Session{
    private Board board;
    private int size;

    public Session(int size){
        this.size = size;
        Random rnd = new Random();
        int tries = 0;
        boolean not_yet = true;
        while(not_yet){
            tries = 0;
            this.board = new Board(size); //sry for bad coding
            all: {for(int column = 0 ; column < size ; column++){ //fill the board
                for(int row = 0 ; row < size ; row++){
                    boolean worked;
                    do{
                        int val = rnd.nextInt(size) + 1;
                        worked = this.board.update_building(val, row, column);
                        ++tries;
                    }while(!worked && tries < size+1);
                    if(tries >= size+1){break all;}
                    tries = 0;
                }
            }
            break;}
        }

        //calc the left array
        int[] left = new int[size];
        for (int row = 0 ; row < size ; row++){
            assert this.board != null;
            left[row] = this.board.calc_sightline(row, false);
        }
        //calc the top array
        int[] top = new int[size];
        for (int column = 0 ; column < size ; column++){
            top[column] = this.board.calc_sightline(column, true);
        }
        assert this.board != null;
        this.board.set_sols(top, true);
        this.board.set_sols(left, false);
        //empty the board!
        this.board.set_solution(this.board.get_buildings());

        for(int column = 0 ; column < size ; column++) { //fill the board
            for (int row = 0; row < size; row++) {
                this.board.update_building(0, column, row);
            }
        }
    }

    public Session(Board board){
        this.board = board;
    }

    public void print_board(){
        this.board.print_board();
    }

    public void play(){
        int actions = size*size;
        while(!check()){
            System.out.println("This is the board: ");
            this.print_board();
            System.out.println("\ntype 'finished' and Enter to check the board");
            System.out.println("or type 'row, column, value' to change a building's value.");
            System.out.println("additional options: 'hint' to get a hint, 'clear' to clear the board.");
            Scanner in = new Scanner(System.in);
            String input;
            label:
            do{
                input = in.nextLine();
                switch (input) {
                    case "finished":
                        break label;
                    case "hint":
                        actions -= size;
                        this.give_hint();
                        continue;
                    case "clear":
                        this.board.clear();
                        break label;
                    case "haxx":
                        for (int i = 0; i < size; i++) {
                            for (int j = 0; j < size; j++) {
                                System.out.print(this.board.get_solution_height(i, j) + " ");
                            }
                            System.out.println();
                        }
                        continue;
                }
                try{
                    String[] values = input.split(", ", 0);
                    int row = Integer.parseInt(values[0]);
                    int column = Integer.parseInt(values[1]);
                    int value = Integer.parseInt(values[2]);
                    boolean worked = this.board.update_building(value, row, column);
                    if(worked){
                        actions--;
                        System.out.println(String.format("Updated building (%d, %d) to height %d", row, column, value));
                        this.print_board();
                        continue;
                    }
                }catch(ArrayIndexOutOfBoundsException | NumberFormatException e){ }
                System.out.println("invalid request");
            } while(!input.equals("finished"));
        }
        System.out.println("You Won!");
        int score = (int)(100*(1+((float)actions/(size*size))));
        System.out.println(String.format("Score: %d%%", score));
        if(score == 100){
            System.out.println("      :::        ::::::::::   ::::::::   ::::::::::  ::::    :::  ::::::::: \n" +
                    "     :+:        :+:         :+:    :+:  :+:         :+:+:   :+:  :+:    :+: \n" +
                    "    +:+        +:+        +:+         +:+         :+:+:+  +:+  +:+    +:+  \n" +
                    "   +#+        +#++:++#   :#:         +#++:++#    +#+ +:+ +#+  +#+    +:+   \n" +
                    "  +#+        +#+        +#+   +#+#  +#+         +#+  +#+#+#  +#+    +#+    \n" +
                    " #+#        #+#        #+#    #+#  #+#         #+#   #+#+#  #+#    #+#     \n" +
                    "########## ##########  ########   ##########  ###    ####  #########       ");
        }
    }

    public boolean check(){
        String columns = this.board.check(true).toString();
        String rows = this.board.check(false).toString();
        boolean ok = true;
        if(!columns.equals("[]") || !rows.equals("[]")){
            System.out.println("You have errors in: ");
            ok = false;
        }
        if(!rows.equals("[]")){
            System.out.println("rows " + rows);
        }
        if(!columns.equals("[]")){
            System.out.println("columns " + columns);
        }
        return ok;
    }

    public void give_hint(){
        Random rnd = new Random();
        int row = rnd.nextInt(size);
        int column = rnd.nextInt(size);
        System.out.println(String.format("HINT: The height of (%d, %d) is %d", column, row, this.board.get_solution_height(row, column)));
        this.board.update_building(this.board.get_solution_height(row, column), column, row);
    }
}

/*
 * Oyun tahtasinda 64 Cell bulunacak ve bu 64 Cell X ve Y ekseninde Cell[][] dizisinide saklanacak.
 * */
public class Board {
    private Cell[][] cells;
    private Cell cell;

    public Board(){

    }
    public void addCellToBoard(Cell cell, int positionX, int positionY){
        this.cells[positionX][positionY] = cell;
    }
    public Cell getCell(int x, int y){
        Cell cellToReturn = this.cells[x][y];
        return cellToReturn;

    }

    public void resetBoard(){
        cells[1][0] = new Cell(1, 0, new Pawn(true));
        addCellToBoard(new Cell(1, 1, new Pawn(true)), 1, 1);
    }
}
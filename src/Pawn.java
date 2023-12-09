public class Pawn extends Piece{
    private boolean moved = false;  //Eger hareket etmemis ise ilk hareketinde iki kare ilerleyebilme opsiyonu olmali
    public Pawn(boolean white){
        super(white);
    }
    public boolean hasMoved(){
        return this.moved;
    }
    public void setMoved(boolean moved){
        this.moved = moved;
    }


    //  Piyonun secili kareye hareket edip edemeyecegini kontrol eden fonksiyon.
    public boolean canMove(Cell start, Cell destination, Board board){

        //  Hareket etmemis ise 2 kare ilerleyip ilerlememe durumunu kontrol eder.
        if (!hasMoved() && (Math.abs(destination.getY() - start.getY()) == 2 && (destination.getX() == start.getX()))){


            //  ilk hareketinde iki kare ilerletilmek isteniyorsa ama onunde tas varsa hareket edememeli.

            //  Taslar ters yone hareket ettiginden birinin Y parametresi artarken digerininki azalacak.
            //  Bu yuzden mutlak degerle tek seferde hesaplamak yerine ayri ayri hesaplanmali.

            //  Eger tas siyah ise:
            if (!start.getPiece().isWhite() && board.getCell(start.getX(), start.getY() + 1).getPiece() == null){

                if (start.getPiece().isWhite() ^ destination.getPiece().isWhite()){
                    return true;
                }else {
                    return false;
                }

                //  Eger tas beyaz ise:
            }else if (start.getPiece().isWhite() && board.getCell(start.getX(), start.getY() - 1).getPiece() == null){

                if (start.getPiece().isWhite() ^ destination.getPiece().isWhite()){
                    return true;
                }else {
                    return false;
                }

            }else {
                return false;
            }


            //  Hareket etmis ise sadece 1 kare ilerleme durumunu kontrol eder.
        }else if (!hasMoved() && (Math.abs(destination.getY() - start.getY()) == 1 && (destination.getX() == start.getX()))){
            if (start.getPiece().isWhite() && destination.getPiece().isWhite()){
                return false;
            }else {
                return true;
            }

            //  Eger piyon sadece birer kare carprazindaki noktalara gidecekse ve gidecegi karedeki tas
            //  ayni renkte degilse true dondurur.
            //  If icinde ikinci karsilastirmada mutlak deger alinmamasinin sebebi piyonun baslangic
            //  konumuna dogru gitmesini engellemek.
            //  Mutlak deger alinmadigidan dolayi her renk icin ayri ayri kontrol edilmeli.

        }else if(Math.abs(destination.getX() - start.getX()) == 1 && (destination.getY() - start.getY()) == 1) {
            if (start.getPiece().isWhite() && destination.getPiece().isWhite()){
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }
    }

}
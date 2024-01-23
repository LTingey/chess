package chess;

import java.util.Collection;

public class KingMovesCalculator extends PieceMovesCalculator{
    public KingMovesCalculator(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        for (int i = position.getRow()-1; i < position.getRow()+1; i++) {
            if (i>=1 && i<=8) {
                for (int j = position.getColumn()-1; j < position.getColumn()+1; j++) {
                    if (j>=1 && j<=8 && (i!=position.getRow() && j!=position.getColumn())) {
                        // check that the moves are still on the board
                        // and exclude the current position from being added to the array
                        ChessPosition newPosition = new ChessPosition(i,j);
                        ChessMove optionalMove = new ChessMove(position,newPosition,null);
                        moves.add(optionalMove);
                    }
                }
            }
        }
        return null;
    }
}

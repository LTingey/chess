package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator extends PieceMovesCalculator{
    public RookMovesCalculator(ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        super(board, position, pieceColor);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        ArrayList<ChessPosition> pieceRange = new ArrayList<ChessPosition>();

        // up
        for (int i = position.getRow()+1; i<9; i++) {
            ChessPosition newPosition = new ChessPosition(i, position.getColumn());
            if (IsAvaliableSquare(newPosition)) {

            }
        }

        return validMoves;
    }
}

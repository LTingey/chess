package chess;

import java.util.Collection;

public class RookMovesCalculator extends PieceMovesCalculator{
    public RookMovesCalculator(ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        super(board, position, pieceColor);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        // up
        for (int i = currentPosition.getRow()+1; i<9; i++) {
            ChessPosition newPosition = new ChessPosition(i, currentPosition.getColumn());
            if (isAvailableSquare(newPosition)) {
                addValidMove(newPosition, null);
            }
            if (isOccupied(newPosition)) {
                break;
            }
        }
        // down
        for (int i = currentPosition.getRow()-1; i>0; i--) {
            ChessPosition newPosition = new ChessPosition(i, currentPosition.getColumn());
            if (isAvailableSquare(newPosition)) {
                addValidMove(newPosition, null);
            }
            if (isOccupied(newPosition)) {
                break;
            }
        }
        // left
        for (int i = currentPosition.getColumn()-1; i>0; i--) {
            ChessPosition newPosition = new ChessPosition(currentPosition.getRow(), i);
            if (isAvailableSquare(newPosition)) {
                addValidMove(newPosition, null);
            }
            if (isOccupied(newPosition)) {
                break;
            }
        }
        // right
        for (int i = currentPosition.getColumn()+1; i<9; i++) {
            ChessPosition newPosition = new ChessPosition(currentPosition.getRow(), i);
            if (isAvailableSquare(newPosition)) {
                addValidMove(newPosition, null);
            }
            if (isOccupied(newPosition)) {
                break;
            }
        }
        return validMoves;
    }
}

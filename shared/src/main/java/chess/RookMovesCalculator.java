package chess;

import java.util.Collection;

public class RookMovesCalculator extends PieceMovesCalculator{
    public RookMovesCalculator(ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        super(board, position, pieceColor);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        // up
        for (int i = position.getRow()+1; i<9; i++) {
            ChessPosition newPosition = new ChessPosition(i, position.getColumn());
            if (IsAvailableSquare(newPosition)) {
                AddValidMove(newPosition);
            }
            if (IsOccupied(newPosition)) {
                break;
            }
        }
        // down
        for (int i = position.getRow()-1; i>0; i--) {
            ChessPosition newPosition = new ChessPosition(i, position.getColumn());
            if (IsAvailableSquare(newPosition)) {
                AddValidMove(newPosition);
            }
            if (IsOccupied(newPosition)) {
                break;
            }
        }
        // left
        for (int i = position.getColumn()-1; i>0; i--) {
            ChessPosition newPosition = new ChessPosition(position.getRow(), i);
            if (IsAvailableSquare(newPosition)) {
                AddValidMove(newPosition);
            }
            if (IsOccupied(newPosition)) {
                break;
            }
        }
        // right
        for (int i = position.getColumn()+1; i<9; i++) {
            ChessPosition newPosition = new ChessPosition(position.getRow(), i);
            if (IsAvailableSquare(newPosition)) {
                AddValidMove(newPosition);
            }
            if (IsOccupied(newPosition)) {
                break;
            }
        }
        return validMoves;
    }
}

package chess;

import java.util.Collection;

public class BishopMovesCalculator extends PieceMovesCalculator{
    public BishopMovesCalculator(ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        super(board, position, pieceColor);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        // up left
        for (int i = position.getRow()+1, j = position.getColumn()-1; i<9 && j>0; i++, j--) {
            ChessPosition newPosition = new ChessPosition(i,j);
            if (IsAvailableSquare(newPosition)) {
                AddValidMove(newPosition, null);
            }
            if (IsOccupied(newPosition)) {
                break;
            }
        }
        // down left
        for (int i = position.getRow()-1, j = position.getColumn()-1; i>0 && j>0; i--, j--) {
            ChessPosition newPosition = new ChessPosition(i,j);
            if (IsAvailableSquare(newPosition)) {
                AddValidMove(newPosition, null);
            }
            if (IsOccupied(newPosition)) {
                break;
            }
        }
        // up right
        for (int i = position.getRow()+1, j = position.getColumn()+1; i<9 && j<9; i++, j++) {
            ChessPosition newPosition = new ChessPosition(i,j);
            if (IsAvailableSquare(newPosition)) {
                AddValidMove(newPosition, null);
            }
            if (IsOccupied(newPosition)) {
                break;
            }
        }
        // down right
        for (int i = position.getRow()-1, j = position.getColumn()+1; i>0 && j<9; i--, j++) {
            ChessPosition newPosition = new ChessPosition(i,j);
            if (IsAvailableSquare(newPosition)) {
                AddValidMove(newPosition, null);
            }
            if (IsOccupied(newPosition)) {
                break;
            }
        }
        return validMoves;
    }
}

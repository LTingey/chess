package chess;

import java.util.Collection;

public class QueenMovesCalculator extends PieceMovesCalculator{
    public QueenMovesCalculator(ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
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
        // up left
        for (int i = position.getRow()+1, j = position.getColumn()-1; i<9 && j>0; i++, j--) {
            ChessPosition newPosition = new ChessPosition(i,j);
            if (IsAvailableSquare(newPosition)) {
                AddValidMove(newPosition);
            }
            if (IsOccupied(newPosition)) {
                break;
            }
        }
        // down left
        for (int i = position.getRow()-1, j = position.getColumn()-1; i>0 && j>0; i--, j--) {
            ChessPosition newPosition = new ChessPosition(i,j);
            if (IsAvailableSquare(newPosition)) {
                AddValidMove(newPosition);
            }
            if (IsOccupied(newPosition)) {
                break;
            }
        }
        // up right
        for (int i = position.getRow()+1, j = position.getColumn()+1; i<9 && j<9; i++, j++) {
            ChessPosition newPosition = new ChessPosition(i,j);
            if (IsAvailableSquare(newPosition)) {
                AddValidMove(newPosition);
            }
            if (IsOccupied(newPosition)) {
                break;
            }
        }
        // down right
        for (int i = position.getRow()-1, j = position.getColumn()+1; i>0 && j<9; i--, j++) {
            ChessPosition newPosition = new ChessPosition(i,j);
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

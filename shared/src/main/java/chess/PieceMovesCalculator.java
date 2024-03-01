package chess;

import java.util.ArrayList;
import java.util.Collection;

public abstract class PieceMovesCalculator {
    protected ChessBoard board;
    protected ChessPosition currentPosition;
    protected ChessGame.TeamColor pieceColor;
    public Collection<ChessMove> validMoves = new ArrayList<ChessMove>();
    public abstract Collection<ChessMove> pieceMoves();

    public PieceMovesCalculator(ChessBoard board, ChessPosition currentPosition, ChessGame.TeamColor pieceColor) {
        this.board = board;
        this.currentPosition = currentPosition;
        this.pieceColor = pieceColor;
    }

    protected boolean isAvailableSquare(ChessPosition newPosition) {
        // check out of bounds
        if (!isInBounds(newPosition)) {
            return false;
        }
        // check same color piece already there
        if (isOccupied(newPosition) && (board.getPiece(newPosition).getTeamColor() == pieceColor)) {
            return false;
        }
        return true;
    }

    protected boolean isInBounds(ChessPosition position) {
        return position.getRow()>0 && position.getRow()<9 && position.getColumn()>0 && position.getColumn()<9;
    }

    protected boolean isOccupied(ChessPosition position) {
        return (board.getPiece((position)) != null);
    }

    protected void addValidMove(ChessPosition newPosition, ChessPiece.PieceType promotionPiece) {
        ChessMove move = new ChessMove(currentPosition, newPosition, promotionPiece);
        validMoves.add(move);
    }

    protected void rookMoves() {
        // up
        for (int i = currentPosition.getRow()+1; i<9; i++) {
            if (verticalMovesHelper(i)) {
                break;
            }
        }
        // down
        for (int i = currentPosition.getRow()-1; i>0; i--) {
            if (verticalMovesHelper(i)) {
                break;
            }
        }
        // left
        for (int i = currentPosition.getColumn()-1; i>0; i--) {
            if (horizontalMovesHelper(i)) {
                break;
            }
        }
        // right
        for (int i = currentPosition.getColumn()+1; i<9; i++) {
            if (horizontalMovesHelper(i)) {
                break;
            }
        }
    }

    protected void bishopMoves() {
        // up left
        for (int i = currentPosition.getRow()+1, j = currentPosition.getColumn()-1; i<9 && j>0; i++, j--) {
            if (diagonalMovesHelper(i,j)) {
                break;
            }
        }
        // down left
        for (int i = currentPosition.getRow()-1, j = currentPosition.getColumn()-1; i>0 && j>0; i--, j--) {
            if (diagonalMovesHelper(i,j)) {
                break;
            }
        }
        // up right
        for (int i = currentPosition.getRow()+1, j = currentPosition.getColumn()+1; i<9 && j<9; i++, j++) {
            if (diagonalMovesHelper(i,j)) {
                break;
            }
        }
        // down right
        for (int i = currentPosition.getRow()-1, j = currentPosition.getColumn()+1; i>0 && j<9; i--, j++) {
            if (diagonalMovesHelper(i,j)) {
                break;
            }
        }
    }

    protected boolean verticalMovesHelper(int i) {
        boolean endLoop = false;
        ChessPosition newPosition = new ChessPosition(i, currentPosition.getColumn());
        if (isAvailableSquare(newPosition)) {
            addValidMove(newPosition, null);
        }
        if (isOccupied(newPosition)) {
            endLoop = true;
        }
        return endLoop;
    }

    protected boolean horizontalMovesHelper(int i) {
        boolean endloop = false;
        ChessPosition newPosition = new ChessPosition(currentPosition.getRow(), i);
        if (isAvailableSquare(newPosition)) {
            addValidMove(newPosition, null);
        }
        if (isOccupied(newPosition)) {
            endloop = true;
        }
        return endloop;
    }

    protected boolean diagonalMovesHelper(int i, int j) {
        boolean endloop = false;
        ChessPosition newPosition = new ChessPosition(i,j);
        if (isAvailableSquare(newPosition)) {
            addValidMove(newPosition, null);
        }
        if (isOccupied(newPosition)) {
            endloop = true;
        }
        return endloop;
    }
}

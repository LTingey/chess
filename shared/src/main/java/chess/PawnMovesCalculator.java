package chess;

import java.util.Collection;

public class PawnMovesCalculator extends PieceMovesCalculator{
    public PawnMovesCalculator (ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        super(board, position, pieceColor);
    }

    private void promoteAndAdd(ChessPosition newPosition) {
        addValidMove(newPosition, ChessPiece.PieceType.ROOK);
        addValidMove(newPosition, ChessPiece.PieceType.KNIGHT);
        addValidMove(newPosition, ChessPiece.PieceType.BISHOP);
        addValidMove(newPosition, ChessPiece.PieceType.QUEEN);
    }

    private boolean endOfBoard(ChessPosition newPosition) {
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            return newPosition.getRow() == 8;
        }
        else {
            return newPosition.getRow() == 1;
        }
    }

    private void checkPromoteAndAdd(ChessPosition newPosition) {
        if (endOfBoard(newPosition)) {
            promoteAndAdd(newPosition);
        }
        else {
            addValidMove(newPosition, null);
        }
    }

    private void checkLeftDiagonal() {
        ChessPosition newPosition;

        // WHITE
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            newPosition = new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()-1);
        }
        else {
            newPosition = new ChessPosition(currentPosition.getRow()-1, currentPosition.getColumn()-1);
        }

        // check if occupied by enemy
        if (isOccupied(newPosition)) {
            if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                checkPromoteAndAdd(newPosition);
            }
        }
    }

    private void checkRightDiagonal() {
        ChessPosition newPosition;

        // WHITE
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            newPosition = new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()+1);
        }
        else {
            newPosition = new ChessPosition(currentPosition.getRow()-1, currentPosition.getColumn()+1);
        }

        // check if occupied by enemy
        if (isOccupied(newPosition)) {
            if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                checkPromoteAndAdd(newPosition);
            }
        }
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        ChessPosition newPosition;

        // WHITE
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            // check square in front
            newPosition = new ChessPosition(currentPosition.getRow() + 1, currentPosition.getColumn());
            if (!isOccupied(newPosition)) {
                checkPromoteAndAdd(newPosition);

                if (!endOfBoard(newPosition)) {
                    // check if first move
                    if (currentPosition.getRow() == 2) {
                        // check 2 spaces ahead
                        newPosition = new ChessPosition(currentPosition.getRow()+2, currentPosition.getColumn());
                        if (!isOccupied(newPosition)) {
                            checkPromoteAndAdd(newPosition);
                        }
                    }
                }
            }
        }

        // BLACK
        else {
            // check square in front
            newPosition = new ChessPosition(currentPosition.getRow() - 1, currentPosition.getColumn());
            if (!isOccupied(newPosition)) {
                checkPromoteAndAdd(newPosition);

                if (!endOfBoard(newPosition)) {
                    // check if first move
                    if (currentPosition.getRow() == 7) {
                        // check 2 spaces ahead
                        newPosition = new ChessPosition(currentPosition.getRow()-2, currentPosition.getColumn());
                        if (!isOccupied(newPosition)) {
                            checkPromoteAndAdd(newPosition);
                        }
                    }
                }
            }
        }

        checkLeftDiagonal();
        checkRightDiagonal();

        return validMoves;
    }
}

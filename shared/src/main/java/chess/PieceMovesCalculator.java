package chess;

import java.util.ArrayList;
import java.util.Collection;

public abstract class PieceMovesCalculator {
    protected ChessBoard board;
    protected ChessPosition currentPosition;
    protected ChessGame.TeamColor pieceColor;
    public Collection<ChessMove> validMoves = new ArrayList<ChessMove>();
    public PieceMovesCalculator(ChessBoard board, ChessPosition currentPosition, ChessGame.TeamColor pieceColor) {
        this.board = board;
        this.currentPosition = currentPosition;
        this.pieceColor = pieceColor;
    }

    protected boolean isAvailableSquare(ChessPosition newPosition) {
        // check out of bounds
        if (newPosition.getRow()<1 || newPosition.getRow()>8 || newPosition.getColumn()<1 || newPosition.getColumn()>8) {
            return false;
        }
        // check same color piece already there
        if (isOccupied(newPosition) && (board.getPiece(newPosition).getTeamColor() == pieceColor)) {
            return false;
        }
        return true;
    }

    protected boolean isOccupied(ChessPosition position) {
        return (board.getPiece((position)) != null);
    }

    protected void addValidMove(ChessPosition newPosition, ChessPiece.PieceType promotionPiece) {
        ChessMove move = new ChessMove(currentPosition, newPosition, promotionPiece);
        validMoves.add(move);
    }

    public abstract Collection<ChessMove> pieceMoves();
}

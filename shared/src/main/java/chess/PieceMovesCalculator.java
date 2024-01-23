package chess;

import java.util.ArrayList;
import java.util.Collection;

public abstract class PieceMovesCalculator {
    protected ChessBoard board;
    protected ChessPosition position;
    protected ChessGame.TeamColor pieceColor;
    public Collection<ChessMove> validMoves = new ArrayList<ChessMove>();
    public PieceMovesCalculator(ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        this.board = board;
        this.position = position;
        this.pieceColor = pieceColor;
    }

    protected void AddMove(ChessMove move) {
        validMoves.add(move);
    }

    protected boolean IsAvaliableSquare(ChessPosition newPosition) {
        // check out of bounds
        if (newPosition.getRow()<1 || newPosition.getRow()>8 || newPosition.getColumn()<1 || newPosition.getColumn()>8) {
            return false;
        }
        // check same color piece already there
        if ((board.getPiece((newPosition)) != null) && (board.getPiece(newPosition).getTeamColor() == pieceColor)) {
            return false;
        }
        return true;
    }

    protected void AddValidMove(ChessPosition newPosition) {
        ChessMove move = new ChessMove(position, newPosition, null);
        validMoves.add(move);
    }

    public abstract Collection<ChessMove> pieceMoves();
}

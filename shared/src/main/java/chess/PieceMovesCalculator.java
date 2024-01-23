package chess;

import java.util.ArrayList;
import java.util.Collection;

public abstract class PieceMovesCalculator {
    protected ChessBoard board;
    protected ChessPosition position;
    public Collection<ChessMove> moves = new ArrayList<ChessMove>();
    public PieceMovesCalculator(ChessBoard board, ChessPosition position) {
        this.board = board;
        this.position = position;
    }

    public abstract Collection<ChessMove> pieceMoves();
}

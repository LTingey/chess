package chess;

import java.util.Collection;

public class QueenMovesCalculator extends PieceMovesCalculator{
    public QueenMovesCalculator(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        return null;
    }
}

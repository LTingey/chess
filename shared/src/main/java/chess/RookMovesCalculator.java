package chess;

import java.util.Collection;

public class RookMovesCalculator extends PieceMovesCalculator{
    public RookMovesCalculator(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        return null;
    }
}

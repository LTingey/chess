package chess;

import java.util.Collection;

public class BishopMovesCalculator extends PieceMovesCalculator{
    public BishopMovesCalculator(ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        return null;
    }
}

package chess;

import java.util.Collection;

public class PawnMovesCalculator extends PieceMovesCalculator{
    public PawnMovesCalculator (ChessBoard board, ChessPosition position) {
        super(board, position);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        return null;
    }
}

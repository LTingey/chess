package chess;

import java.util.Collection;

public class KnightMovesCalculator extends PieceMovesCalculator{
    public KnightMovesCalculator(ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        super(board, position, pieceColor);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        return null;
    }
}

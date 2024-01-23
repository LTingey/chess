package chess;

import java.util.Collection;

public class RookMovesCalculator extends PieceMovesCalculator{
    public RookMovesCalculator(ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        super(board, position, pieceColor);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {


        return validMoves;
    }
}

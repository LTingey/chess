package chess.calculators;

import chess.*;

import java.util.Collection;

public class BishopMovesCalculator extends PieceMovesCalculator {
    public BishopMovesCalculator(ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        super(board, position, pieceColor);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        bishopMoves();
        return validMoves;
    }
}

package chess.calculators;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.calculators.PieceMovesCalculator;

import java.util.Collection;

public class QueenMovesCalculator extends PieceMovesCalculator {
    public QueenMovesCalculator(ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        super(board, position, pieceColor);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        rookMoves();
        bishopMoves();
        return validMoves;
    }
}

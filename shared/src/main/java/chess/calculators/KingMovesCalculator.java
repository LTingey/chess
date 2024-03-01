package chess.calculators;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalculator extends PieceMovesCalculator {
    public KingMovesCalculator(ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        super(board, position, pieceColor);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        ArrayList<ChessPosition> pieceRange = new ArrayList<ChessPosition>();

        // upper left
        pieceRange.add(new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()-1));
        // above
        pieceRange.add(new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()));
        // upper right
        pieceRange.add(new ChessPosition(currentPosition.getRow()+1, currentPosition.getColumn()+1));
        // left
        pieceRange.add(new ChessPosition(currentPosition.getRow(), currentPosition.getColumn()-1));
        // right
        pieceRange.add(new ChessPosition(currentPosition.getRow(), currentPosition.getColumn()+1));
        // lower left
        pieceRange.add(new ChessPosition(currentPosition.getRow()-1, currentPosition.getColumn()-1));
        // below
        pieceRange.add(new ChessPosition(currentPosition.getRow()-1, currentPosition.getColumn()));
        // lower right
        pieceRange.add(new ChessPosition(currentPosition.getRow()-1, currentPosition.getColumn()+1));

        for (ChessPosition square : pieceRange) {
            if (isAvailableSquare(square)) {
                addValidMove(square, null);
            }
        }

        return validMoves;
    }
}

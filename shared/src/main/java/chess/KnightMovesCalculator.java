package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator extends PieceMovesCalculator{
    public KnightMovesCalculator(ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        super(board, position, pieceColor);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        ArrayList<ChessPosition> pieceRange = new ArrayList<ChessPosition>();

        // up 2, left 1
        pieceRange.add(new ChessPosition(position.getRow()+2, position.getColumn()-1));
        // up 2, right 1
        pieceRange.add(new ChessPosition(position.getRow()+2, position.getColumn()+1));
        // up 1, left 2
        pieceRange.add(new ChessPosition(position.getRow()+1, position.getColumn()-2));
        // up 1, right 2
        pieceRange.add(new ChessPosition(position.getRow()+1, position.getColumn()+2));
        // down 1, left 2
        pieceRange.add(new ChessPosition(position.getRow()-1, position.getColumn()-2));
        // down 1, right 2
        pieceRange.add(new ChessPosition(position.getRow()-1, position.getColumn()+2));
        // down 2, left 1
        pieceRange.add(new ChessPosition(position.getRow()-2, position.getColumn()-1));
        // down 2, right 1
        pieceRange.add(new ChessPosition(position.getRow()-2, position.getColumn()+1));

        for (ChessPosition square : pieceRange) {
            if (IsAvailableSquare(square)) {
                AddValidMove(square, null);
            }
        }

        return validMoves;
    }
}

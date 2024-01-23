package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalculator extends PieceMovesCalculator{
    public KingMovesCalculator(ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        super(board, position, pieceColor);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        ArrayList<ChessPosition> pieceRange = new ArrayList<ChessPosition>();

        // upper left
        pieceRange.add(new ChessPosition(position.getRow()+1, position.getColumn()-1));
        // above
        pieceRange.add(new ChessPosition(position.getRow()+1, position.getColumn()));
        // upper right
        pieceRange.add(new ChessPosition(position.getRow()+1, position.getColumn()+1));
        // left
        pieceRange.add(new ChessPosition(position.getRow(), position.getColumn()-1));
        // right
        pieceRange.add(new ChessPosition(position.getRow(), position.getColumn()+1));
        // lower left
        pieceRange.add(new ChessPosition(position.getRow()-1, position.getColumn()-1));
        // below
        pieceRange.add(new ChessPosition(position.getRow()-1, position.getColumn()));
        // lower right
        pieceRange.add(new ChessPosition(position.getRow()-1, position.getColumn()+1));

        for (ChessPosition square : pieceRange) {
            if (IsAvailableSquare(square)) {
                AddValidMove(square);
            }
        }

        return validMoves;
    }
}

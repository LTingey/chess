package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        switch (type) {
            case KING -> {
                KingMovesCalculator kingMoves = new KingMovesCalculator(board, myPosition);
                return kingMoves.pieceMoves();
            }
            case QUEEN -> {
                QueenMovesCalculator queenMoves = new QueenMovesCalculator(board, myPosition);
                return queenMoves.pieceMoves();
            }
            case BISHOP -> {
                BishopMovesCalculator bishopMoves = new BishopMovesCalculator(board, myPosition);
                return bishopMoves.pieceMoves();
            }
            case KNIGHT -> {
                KnightMovesCalculator knightMoves = new KnightMovesCalculator(board, myPosition);
                return knightMoves.pieceMoves();
            }
            case ROOK -> {
                RookMovesCalculator rookMoves = new RookMovesCalculator(board, myPosition);
                return rookMoves.pieceMoves();
            }
            case PAWN -> {
                PawnMovesCalculator pawnMoves = new PawnMovesCalculator(board, myPosition);
                return pawnMoves.pieceMoves();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }
}

package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor currentTeamTurn;
    private ChessBoard board;

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currentTeamTurn;
    }

    /**
     * Sets which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        currentTeamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null) {
            return null;
        }
        return piece.pieceMoves(board, startPosition);
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece piece = board.getPiece(startPosition);
        TeamColor pieceColor = piece.getTeamColor();

        // check if it's their turn
        if (pieceColor != getTeamTurn()) {
            throw new InvalidMoveException();
        }

        // check if it is a valid move
        Collection<ChessMove> validMoves = validMoves(startPosition);
        boolean isValidMove = false;
        for (ChessMove validMove: validMoves) {
            if (validMove.equals(move)) {
                isValidMove = true;
                break;
            }
        }
        if (!isValidMove) {
            throw new InvalidMoveException();
        }

        // make the move
        ChessPiece capturedPiece = board.getPiece(endPosition);
        tryMove(piece, move);

        // check if it leaves the king in danger
        if (isInCheck(pieceColor)) {
            undoMove(move, capturedPiece);
            throw new InvalidMoveException();
        }

        // change team turn
        if (getTeamTurn() == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        }
        else {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    private void tryMove(ChessPiece piece, ChessMove move) {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        TeamColor pieceColor = piece.getTeamColor();

        board.addPiece(startPosition, null);
        // check if it is a pawn that needs promoting
        if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            ChessPiece.PieceType promotion = move.getPromotionPiece();
            if (promotion != null) {
                piece = new ChessPiece(pieceColor, promotion);
            }
        }
        board.addPiece(endPosition, piece);
    }

    private void undoMove(ChessMove move, ChessPiece capturedPiece) {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece piece = board.getPiece(endPosition);

        board.addPiece(startPosition, piece);
        board.addPiece(endPosition, capturedPiece);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // find the king's position
        ChessPosition kingPosition = null;
        for (int i=1; i<9; i++) {
            for (int j=1; j<9; j++) {
                ChessPosition position = new ChessPosition(i,j);
                ChessPiece piece = board.getPiece(position);
                if (piece != null) {
                    if (piece.getTeamColor() == teamColor && piece.getPieceType() == ChessPiece.PieceType.KING) {
                        kingPosition = position;
                        break;
                    }
                }
            }
            if (kingPosition != null) {
                break;
            }
        }

        // look at all of the opposing team's moves and see if any of them can capture the king
        ChessPosition position;
        ChessPiece piece;
        Collection<ChessMove> pieceMoves;
        for (int i=1; i<9; i++) {
            for (int j=1; j<9; j++) {
                position = new ChessPosition(i,j);
                piece = board.getPiece(position);
                if (piece != null) {
                    if (piece.getTeamColor() != teamColor) {
                        pieceMoves = piece.pieceMoves(board, position);
                        for (ChessMove move: pieceMoves) {
                            if (move.getEndPosition().equals(kingPosition)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            return false;
        }
        // test all the moves to see if any get you out of check
        // one way: clone the board and make a move (simplest, but expensive)
        // another way: implement an undo move (harder)
        // find each piece on the board of that color
        ChessPosition position;
        ChessPiece piece;
        Collection<ChessMove> pieceMoves;
        for (int i=1; i<9; i++) {
            for (int j=1; j<9; j++) {
                position = new ChessPosition(i,j);
                piece = board.getPiece(position);
                if (piece != null) {
                    if (piece.getTeamColor() == teamColor) {
                        pieceMoves = piece.pieceMoves(board, position);
                        // make each move and see if they are still in Check
                        for (ChessMove move: pieceMoves) {

                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}

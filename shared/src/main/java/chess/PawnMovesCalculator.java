package chess;

import java.util.Collection;

public class PawnMovesCalculator extends PieceMovesCalculator{
    public PawnMovesCalculator (ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        super(board, position, pieceColor);
    }

    public void PromoteAndAdd(ChessPosition newPosition) {
        AddValidMove(newPosition, ChessPiece.PieceType.ROOK);
        AddValidMove(newPosition, ChessPiece.PieceType.KNIGHT);
        AddValidMove(newPosition, ChessPiece.PieceType.BISHOP);
        AddValidMove(newPosition, ChessPiece.PieceType.QUEEN);
    }

    @Override
    public Collection<ChessMove> pieceMoves() {
        // WHITE
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            // check square in front
            ChessPosition newPosition = new ChessPosition(position.getRow()+1, position.getColumn());
            // check if occupied
            if (!IsOccupied(newPosition)) {
                // check if end of board
                if (newPosition.getRow() == 8) {
                    PromoteAndAdd(newPosition);
                }
                else {
                    AddValidMove(newPosition, null);
                    // check if first move
                    if (position.getRow() == 2) {
                        // check 2 spaces ahead
                        newPosition = new ChessPosition(position.getRow()+2, position.getColumn());
                        if (!IsOccupied(newPosition)) {
                            // check if end of board
                            if (newPosition.getRow() == 8) {
                                PromoteAndAdd(newPosition);
                            }
                            else {
                                AddValidMove(newPosition, null);
                            }
                        }
                    }
                }
            }
            // check left diagonal
            newPosition = new ChessPosition(position.getRow()+1, position.getColumn()-1);
            // check if occupied by enemy
            if (IsOccupied(newPosition)) {
                if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    // check if end of board
                    if (newPosition.getRow() == 8) {
                        PromoteAndAdd(newPosition);
                    }
                    else {
                        AddValidMove(newPosition, null);
                    }
                }
            }
            // check right diagonal
            newPosition = new ChessPosition(position.getRow()+1, position.getColumn()+1);
            // check if occupied by enemy
            if (IsOccupied(newPosition)) {
                if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    // check if end of board
                    if (newPosition.getRow() == 8) {
                        PromoteAndAdd(newPosition);
                    }
                    else {
                        AddValidMove(newPosition, null);
                    }
                }
            }
        }
        // BLACK
        else {
            // check square in front
            ChessPosition newPosition = new ChessPosition(position.getRow()-1, position.getColumn());
            // check if occupied
            if (!IsOccupied(newPosition)) {
                // check if end of board
                if (newPosition.getRow() == 1) {
                    PromoteAndAdd(newPosition);
                }
                else {
                    AddValidMove(newPosition, null);
                    // check if first move
                    if (position.getRow() == 7) {
                        // check 2 spaces ahead
                        newPosition = new ChessPosition(position.getRow()-2, position.getColumn());
                        if (!IsOccupied(newPosition)) {
                            // check if end of board
                            if (newPosition.getRow() == 1) {
                                PromoteAndAdd(newPosition);
                            }
                            else {
                                AddValidMove(newPosition, null);
                            }
                        }
                    }
                }
            }
            // check left diagonal
            newPosition = new ChessPosition(position.getRow()-1, position.getColumn()-1);
            // check if occupied by enemy
            if (IsOccupied(newPosition)) {
                if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    // check if end of board
                    if (newPosition.getRow() == 1) {
                        PromoteAndAdd(newPosition);
                    }
                    else {
                        AddValidMove(newPosition, null);
                    }
                }
            }
            // check right diagonal
            newPosition = new ChessPosition(position.getRow()-1, position.getColumn()+1);
            // check if occupied by enemy
            if (IsOccupied(newPosition)) {
                if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    // check if end of board
                    if (newPosition.getRow() == 1) {
                        PromoteAndAdd(newPosition);
                    }
                    else {
                        AddValidMove(newPosition, null);
                    }
                }
            }
        }

        return validMoves;
    }
}

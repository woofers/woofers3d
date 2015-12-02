package com.jaxson.chess.board;

import com.jaxson.chess.ai.HardPlayer;
import com.jaxson.chess.ai.Player;
import com.jaxson.chess.board.move.Move;
import com.jaxson.chess.board.move.MoveList;
import com.jaxson.chess.board.move.PieceMove;
import com.jaxson.chess.board.move.Promotion;
import com.jaxson.chess.board.move.Remove;
import com.jaxson.chess.ui.Board;
import com.jaxson.chess.ui.Piece;
import com.jaxson.lib.geom.Point;
import com.jaxson.lib.util.MyArrayList;
import com.jaxson.lib.util.MyMath;

public class IntBoard
{
	public static final int infinity = Integer.MAX_VALUE;

	private int width, height, turn, color;
	private Player player;
	private IntPiece[][] board;

	public IntBoard(Board board)
	{
		this(board.getBoardWidth(), board.getBoardHeight(), board.getTurn(), board.getColor());
		for (int y = 0; y < height; y ++)
		{
			for (int x = 0; x < width; x ++)
			{
				this.board[x][y] = board.getSpot(x, y).toIntPiece();
			}
		}

		//print();
		//new Move(this.board[3][5], this.board[3][6]).move(this);
		//new Move(this.board[6][2], this.board[6][0]).move(this);
		//new Move(this.board[3][4], this.board[3][5]).move(this);
		//new Move(this.board[7][3], this.board[6][2]).move(this);
		//print();
	}

	public IntBoard(int width, int height, int turn)
	{
		this(width, height, turn, Board.DEFAULT_COLOR);
	}

	public IntBoard(int width, int height, int turn, int color)
	{
		this.width = width;
		this.height = height;
		this.turn = turn;
		this.color = color;
		this.board = new IntPiece[width][height];
		this.player = new HardPlayer(color);

	}

	public Move aiMove()
	{
		return miniMax(player.getDepth());
	}

	public int getColor()
	{
		return color;
	}

	public int getHeight()
	{
		return height;
	}

	public int getTurn()
	{
		return turn;
	}

	public int getWidth()
	{
		return width;
	}

	private MoveList getAllAbove(IntPiece piece)
	{
		return getAllByIncrement(piece, 0, -1);
	}

	private MoveList getAllBelow(IntPiece piece)
	{
		return getAllByIncrement(piece, 0, +1);
	}

	private MoveList getAllByIncrement(IntPiece piece, int xIncrement, int yIncrement)
	{
		MoveList moves = new MoveList();
		IntPiece spot = getSpot(piece.location.x + xIncrement, piece.location.y + yIncrement);
		while (spot != null)
		{
			if (!spot.isFriendly(piece.color))
			{
				moves.add(new Move(spot, piece));
				if (spot.isEmpty())
				{
					spot = getSpot(spot.location.x + xIncrement, spot.location.y + yIncrement);
					continue;
				}
			}
			break;
		}
		return moves;
	}

	private MoveList getAllDiagonal(IntPiece piece)
	{
		MoveList moves = new MoveList();
		moves.addAll(getAllDiagonalTopLeft(piece));
		moves.addAll(getAllDiagonalTopRight(piece));
		moves.addAll(getAllDiagonalBottomLeft(piece));
		moves.addAll(getAllDiagonalBottomRight(piece));
		return moves;
	}

	private MoveList getAllDiagonalBottomLeft(IntPiece piece)
	{
		return getAllByIncrement(piece, -1, +1);
	}

	private MoveList getAllDiagonalBottomRight(IntPiece piece)
	{
		return getAllByIncrement(piece, +1, +1);
	}

	private MoveList getAllDiagonalTopLeft(IntPiece piece)
	{
		return getAllByIncrement(piece, -1, -1);
	}

	private MoveList getAllDiagonalTopRight(IntPiece piece)
	{
		return getAllByIncrement(piece, +1, -1);
	}

	private MoveList getAllLeft(IntPiece piece)
	{
		return getAllByIncrement(piece, -1, 0);
	}

	private MoveList getAllRight(IntPiece piece)
	{
		return getAllByIncrement(piece, +1, 0);
	}

	private MoveList getCastling(IntPiece piece)
	{
		MoveList moves = new MoveList();
		if (piece.hasMoved()) return moves;
		moves.add(getCastlingMove(piece, +1));
		moves.add(getCastlingMove(piece, -1));
		return moves;
	}

	private Move getCastlingMove(IntPiece piece, int direction)
	{
		IntPiece spot = piece;
		while (true)
		{
			spot = getSpot(spot.location.x + direction, spot.location.y);
			if (spot == null) return null;
			if (!spot.isEmpty()) break;
		}
		if (spot.type == Piece.ROOK)
		{
			if (!spot.hasMoved())
			{
				Move move;
				PieceMove pieceMove;
				IntPiece newKingSpot, newRookSpot;
				newKingSpot = getSpot(piece.location.x + direction * 2, piece.location.y);
				newRookSpot = getSpot(newKingSpot.location.x - direction, newKingSpot.location.y);
				pieceMove = new PieceMove(newKingSpot, piece);
				move = new Move(newRookSpot, spot);
				move.setOrigin(spot);
				move.add(pieceMove);
				return move;
			}
		}
		return null;
	}

	private MoveList getKnight(IntPiece piece)
	{
		MoveList moves = new MoveList();
		moves.add(new Move(getSpot(piece.location.x - 1, piece.location.y - 2), piece));
		moves.add(new Move(getSpot(piece.location.x + 1, piece.location.y - 2), piece));
		moves.add(new Move(getSpot(piece.location.x - 1, piece.location.y + 2), piece));
		moves.add(new Move(getSpot(piece.location.x + 1, piece.location.y + 2), piece));
		moves.add(new Move(getSpot(piece.location.x - 2, piece.location.y - 1), piece));
		moves.add(new Move(getSpot(piece.location.x - 2, piece.location.y + 1), piece));
		moves.add(new Move(getSpot(piece.location.x + 2, piece.location.y - 1), piece));
		moves.add(new Move(getSpot(piece.location.x + 2, piece.location.y + 1), piece));
		return moves;
	}

	public MoveList getLegalMoves(int color)
	{
		MoveList moves = new MoveList();
		MyArrayList<IntPiece> pieces = getPieces(color);
		for (IntPiece piece: pieces)
		{
			moves.addAll(getLegalMoves(piece));
		}
		return moves;
	}

	public MoveList getLegalMoves(IntPiece piece)
	{
		MoveList moves = new MoveList();
		switch (piece.type)
		{
			case Piece.KING:
				moves.addAll(getSurrondingSpots(piece));
				moves.addAll(getCastling(piece));
				break;
			case Piece.QUEEN:
				moves.addAll(getAllAbove(piece));
				moves.addAll(getAllBelow(piece));
				moves.addAll(getAllLeft(piece));
				moves.addAll(getAllRight(piece));
				moves.addAll(getAllDiagonal(piece));
				break;
			case Piece.ROOK:
				moves.addAll(getAllAbove(piece));
				moves.addAll(getAllBelow(piece));
				moves.addAll(getAllLeft(piece));
				moves.addAll(getAllRight(piece));
				break;
			case Piece.BISHOP:
				moves = getAllDiagonal(piece);
				break;
			case Piece.KNIGHT:
				moves = getKnight(piece);
				break;
			case Piece.PAWN:
				moves.addAll(getPawn(piece));
				moves.addAll(getPromotion(piece));
				break;
		}
		return moves;
	}

	private MoveList getPawn(IntPiece piece)
	{
		MoveList moves = new MoveList();
		IntPiece spot = piece;
		for (int i = 0; i < 2; i ++)
		{
			spot = getSpot(spot.location.x, spot.location.y + piece.direction);
			if (spot != null)
			{
				if (spot.isEmpty())
				{
					moves.add(new Move(spot, piece));
					if (!piece.hasMoved()) continue;
				}
			}
			break;
		}
		moves.addAll(getPawnCapture(piece));
		moves.addAll(getPawnPass(piece));
		return moves;
	}

	private MoveList getPawnCapture(IntPiece piece)
	{
		MoveList moves = new MoveList();
		IntPiece spot;
		spot = getSpot(piece.location.x + 1, piece.location.y + piece.direction);
		if (spot != null)
		{
			if (spot.isEnemey(piece.color)) moves.add(new Move(spot, piece));
		}
		spot = getSpot(piece.location.x - 1, piece.location.y + piece.direction);
		if (spot != null)
		{
			if (spot.isEnemey(piece.color)) moves.add(new Move(spot, piece));
		}
		return moves;
	}

	private MoveList getPawnPass(IntPiece piece)
	{
		MoveList moves = new MoveList();
		IntPiece leftPawn, rightPawn;
		leftPawn = getSpot(piece.location.x - 1, piece.location.y);
		rightPawn = getSpot(piece.location.x + 1, piece.location.y);
		moves.add(getPawnPassMove(piece, leftPawn));
		moves.add(getPawnPassMove(piece, rightPawn));
		return moves;
	}

	private Move getPawnPassMove(IntPiece piece, IntPiece capture)
	{
		if (capture == null) return null;
		if (capture.type == Piece.PAWN)
		{
			if (capture.isEnemey(piece.color))
			{
				if (capture.passingIndex == turn)
				{
					IntPiece pass;
					pass = getSpot(capture.location.x, capture.location.y + piece.direction);
					if (pass != null)
					{
						Move move = new Move(pass, piece);
						move.add(new Remove(capture));
						return move;
					}
				}
			}
		}
		return null;
	}

	public MyArrayList<IntPiece> getPieces()
	{
		MyArrayList<IntPiece> pieces;
		pieces = getPieces(Piece.WHITE);
		pieces.addAll(getPieces(Piece.BLACK));
		return pieces;
	}

	private MyArrayList<IntPiece> getPieces(int color)
	{
		MyArrayList<IntPiece> pieces = new MyArrayList<>();
		for (int y = 0; y < height; y ++)
		{
			for (int x = 0; x < width; x ++)
			{
				if (board[x][y].color == color) pieces.add(board[x][y]);
			}
		}
		return pieces;
	}


	private MoveList getPromotion(IntPiece piece)
	{
		MoveList moves = new MoveList();
		if (piece.isPromotable())
		{
			moves.add(new Move(piece, new Promotion(piece, Piece.QUEEN)));
			moves.add(new Move(piece, new Promotion(piece, Piece.ROOK)));
			moves.add(new Move(piece, new Promotion(piece, Piece.BISHOP)));
			moves.add(new Move(piece, new Promotion(piece, Piece.KNIGHT)));
		}
		return moves;
	}

	public IntPiece getSpot(Point location)
	{
		return getSpot(location.x, location.y);
	}

	public IntPiece getSpot(int x, int y)
	{
		if (spotExist(x, y)) return board[x][y];
		return null;
	}

	private MoveList getSurrondingSpots(IntPiece piece)
	{
		MoveList moves = new MoveList();
		moves.add(new Move(getSpot(piece.location.x - 1, piece.location.y - 1), piece));
		moves.add(new Move(getSpot(piece.location.x, piece.location.y - 1), piece));
		moves.add(new Move(getSpot(piece.location.x + 1, piece.location.y - 1), piece));
		moves.add(new Move(getSpot(piece.location.x - 1, piece.location.y), piece));
		moves.add(new Move(getSpot(piece.location.x + 1, piece.location.y), piece));
		moves.add(new Move(getSpot(piece.location.x - 1, piece.location.y + 1), piece));
		moves.add(new Move(getSpot(piece.location.x, piece.location.y + 1), piece));
		moves.add(new Move(getSpot(piece.location.x + 1, piece.location.y + 1), piece));;
		return moves;
	}

	private Boolean hasWon(int color)
	{
		for (int y = 0; y < height; y ++)
		{
			for (int x = 0; x < width; x ++)
			{
				if (board[x][y].isEnemey(color))
				{
					if (board[x][y].type == Piece.KING) return false;
				}
			}
		}
		return true;
	}

	private Move miniMax(int depth)
	{
		int maxDepth, alpha, beta;
		maxDepth = depth;
		alpha = -infinity;
		beta = infinity;

		MoveList moves = getLegalMoves(color);
		Move bestMove = moves.get(0);
		int bestValue, value, currentX;
		bestValue = alpha;
		for (Move move: moves)
		{
			move.move(this);
			if (hasWon(color))
			{
				value = infinity - maxDepth + depth;
			}
			else if (depth == 0 || moves.isEmpty())
			{
				value = player.evaluateBoard(this);
			}
			else
			{
				value = min(depth - 1, maxDepth, alpha, beta);
			}
			move.undo(this);
			if (value > bestValue)
			{
				bestValue = value;
				bestMove = move;
			}
			alpha = Math.max(alpha, bestValue);
			if (alpha >= beta) break;
		}
		return bestMove;
	}

	private int min(int depth, int maxDepth, int alpha, int beta)
	{
		MoveList moves = getLegalMoves(color);
		int bestValue, value, currentX;
		bestValue = beta;
		for (Move move: moves)
		{
			move.move(this);
			if (hasWon(color))
			{
				value = infinity - maxDepth + depth;
			}
			else if (depth == 0 || moves.isEmpty())
			{
				value = player.evaluateBoard(this);
			}
			else
			{
				value = max(depth - 1, maxDepth, alpha, beta);
			}
			move.undo(this);

			if (value < bestValue)
			{
				bestValue = value;
			}
			beta = Math.min(beta, bestValue);
			if (alpha >= beta) break;
		}
		return bestValue;
	}

	private int max(int depth, int maxDepth, int alpha, int beta)
	{
		MoveList moves = getLegalMoves(color);
		int bestValue, value, currentX;
		bestValue = alpha;
		for (Move move: moves)
		{
			move.move(this);
			if (hasWon(color))
			{
				value = infinity - maxDepth + depth;
			}
			else if (depth == 0 || moves.isEmpty())
			{
				value = player.evaluateBoard(this);
			}
			else
			{
				value = min(depth - 1, maxDepth, alpha, beta);
			}
			move.undo(this);

			if (value > bestValue)
			{
				bestValue = value;
			}
			alpha = Math.max(alpha, bestValue);
			if (alpha >= beta) break;
		}
		return bestValue;
	}

	public void print()
	{
		System.out.println(toString());
	}

	public void setColor(int value)
	{
		color = value;
	}

	public void setPlayer(Player value)
	{
		player = value;
	}

	public void setSpot(IntPiece value)
	{
		setSpot(value, value.location);
	}

	public void setSpot(IntPiece value, Point location)
	{
		setSpot(value, location.x, location.y);
	}

	public void setSpot(IntPiece value, int x, int y)
	{
		value = value.clone();
		value.location = new Point(x, y);
		board[x][y] = value;
	}

	private Boolean spotExist(int x, int y)
	{
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	public void swapColors()
	{
		color = Piece.getOppositeColor(color);
	}

	@Override
	public String toString()
	{
		String string = "";
		for (int y = 0; y < height; y ++)
		{
			for (int x = 0; x < width; x ++)
			{
				string += board[x][y].toString();
				string += ", ";
			}
			string += "\n";
		}
		return string;
	}
}

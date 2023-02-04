package chess;

import static util.StringUtil.NEWLINE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pieces.Piece;
import pieces.Piece.Color;
import pieces.Piece.Type;

public class Board {
														// a b c d e f g hs
	private List<Piece> rank1 = new ArrayList<Piece>(); // r n b q k b n r (white)
	private List<Piece> rank2 = new ArrayList<Piece>();
	private List<Piece> rank3 = new ArrayList<Piece>();
	private List<Piece> rank4 = new ArrayList<Piece>();
	private List<Piece> rank5 = new ArrayList<Piece>();
	private List<Piece> rank6 = new ArrayList<Piece>();
	private List<Piece> rank7 = new ArrayList<Piece>();
	private List<Piece> rank8 = new ArrayList<Piece>(); // R N B Q K B N R (black)
	
	private List<List<Piece>> board = new ArrayList<List<Piece>>();	
	/**
	 * creates an empty 8x8 board
	 */
	Board() {
		board.add(rank1);
		board.add(rank2);
		board.add(rank3);
		board.add(rank4);
		board.add(rank5);
		board.add(rank6);
		board.add(rank7);
		board.add(rank8);
		fillWithNoPieces();
	}

	/**
	 * 
	 */
	private void fillWithNoPieces() {
		for (List<Piece> rank : board) {
			for (int i = 0; i < 8; i++) {
				rank.add(i, Piece.noPiece());
			}
		}
	}

	void initialize() {
		for (int i = 0; i < 8; i++) {
			switch (i) {
			case 0:
			case 7:
				initRooks(i);
				break;
			case 1:
			case 6:
				initKnights(i);
				break;
			case 2:
			case 5:
				initBishops(i);
				break;
			case 3:
				initQueens(i);
				break;
			case 4:
				initKings(i);
				break;
			} // end switch

			initPawns(i);
			initNoPieces(i);
		} // end for
	}

	/**
	 * @return number of non-no-piece pieces on the board
	 */
	public int count() {
		int count = 0;
		for (List<Piece> rank : board) {
			for (Piece piece : rank) {
				if (piece.getType() != Piece.Type.NO_PIECE ) {
					count++;
				}
			}
		}
		return count;
	}

	String print(List<Piece> rank) {
		String str = "";
		for (Piece piece : rank) {
			str += piece.toString();
		}
		return str;
	}

	List<Piece> getRank1() {
		return rank1;
	}

	List<Piece> getRank2() {
		return rank2;
	}

	List<Piece> getRank3() {
		return rank3;
	}

	List<Piece> getRank4() {
		return rank4;
	}

	List<Piece> getRank5() {
		return rank5;
	}

	List<Piece> getRank6() {
		return rank6;
	}

	List<Piece> getRank7() {
		return rank7;
	}

	List<Piece> getRank8() {
		return rank8;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(print(rank8)).append(NEWLINE);
		sb.append(print(rank7)).append(NEWLINE);
		sb.append(print(rank6)).append(NEWLINE);
		sb.append(print(rank5)).append(NEWLINE);
		sb.append(print(rank4)).append(NEWLINE);
		sb.append(print(rank3)).append(NEWLINE);
		sb.append(print(rank2)).append(NEWLINE);
		sb.append(print(rank1)).append(NEWLINE);
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(new Board().toString());
	}

	public String print() {
		return toString();
	}

	/**
	 * 
	 */
	private void initPawns(int i) {
		rank2.set(i, Piece.createPawn(Color.WHITE));
		rank7.set(i, Piece.createPawn(Color.BLACK));
	}

	/**
	 * 
	 */
	private void initKings(int i) {
		rank1.set(i, Piece.createKing(Color.WHITE));
		rank8.set(i, Piece.createKing(Color.BLACK));
	}

	/**
	 * 
	 */
	private void initQueens(int i) {
		rank1.set(i, Piece.createQueen(Color.WHITE));
		rank8.set(i, Piece.createQueen(Color.BLACK));
	}

	/**
	 * 
	 */
	private void initBishops(int i) {
		rank1.set(i, Piece.createBishop(Color.WHITE));
		rank8.set(i, Piece.createBishop(Color.BLACK));
	}

	/**
	 * 
	 */
	private void initKnights(int i) {
		rank1.set(i, Piece.createKnight(Color.WHITE));
		rank8.set(i, Piece.createKnight(Color.BLACK));
	}

	/**
	 * 
	 */
	private void initRooks(int i) {
		rank1.set(i, Piece.createRook(Color.WHITE));
		rank8.set(i, Piece.createRook(Color.BLACK));
	}
	
	/**
	 * rank3 - 6
	 * @param i
	 */
	private void initNoPieces(int i) {
		rank3.set(i, Piece.noPiece());
		rank4.set(i, Piece.noPiece());
		rank5.set(i, Piece.noPiece());
		rank6.set(i, Piece.noPiece());		
	}


	public int count(char representation) {
		int count = 0;
		for (List<Piece> rank : board) {
			for (Piece piece : rank) {
				if(representation == piece.getRepresentation()) count++;
			}
		}
		return count;
	}
	
	public void place(char piece, String position) {
		int file = file(position.charAt(0));
		int rank = rank(position.charAt(1));
		board.get(rank).set(file, Piece.create(piece));;
	}

	/**
	 * Given an initial board setup, 
	 * asking for the piece at "a8" returns the black rook.
	 * The white king is at "e1".
	 * 
	 * @param position
	 * @return
	 */
	public Piece getPieceAt(String position) {
		if (position.length() != 2) {
			throw new IllegalArgumentException("Position length must be 2");
		}
		int file = file(position.charAt(0));
		int rank = rank(position.charAt(1));
		return board.get(rank).get(file);
	}

	/**
	 * @param rank [1 ... 8]
	 * @return rank - 49 (ASCII '1') [0 ... 7]
	 */
	int rank(char rank) {
		if (rank < 48 || rank > 48 + 8) {
			throw new IllegalArgumentException("Illegal rank " + rank + " should be [1 ... 8]");
		}
		return  rank - 49;
	}

	/**
	 * @param file [a ... h]
	 * @return file - 97 (ASCII 'a') [0 ... 7]
	 */
	int file(char file) {
		if (file < 97 || file > 97 + 7) {
			throw new IllegalArgumentException("Illegal file '" + file + "' should be ['a' ... 'h']");
		}
		return file - 97;
	}

	/*
	 *  Calculate this sum by
	 *  
	 *  - adding 9 points for a queen, 
	 * - 5 points for a rook, 
	 * - 3 points for a bishop, and 
	 * - 2.5 points for a knight. 
	 * - Add 0.5 points for a pawn that is on the same file as a pawn of the same color
	 * - and 1 point for a pawn otherwise.(default, substract 0.5 otherwise)
	 */
	public double evalStrength(Color color) {
		double sum = 0.0;
		int[] pawnFileRecord = new int[8];
		
		for (List<Piece> rank : board) {
//			System.out.println(rank);
			for (int i = 0; i < rank.size(); i++) {
				Piece piece = rank.get(i);
				if (sameColor(color, piece)) {
					sum += piece.getType().getStrength();
					if (piece.getType() == Type.PAWN) {
						pawnFileRecord[i]++;
					}
				}
			}
		}
//		System.out.println(Arrays.toString(pawnFileRecord));
		return sum - (countPawnsInSameFile(pawnFileRecord) * 0.5);  // ??? why does (./2) NOT work but (.*0.5) ???
	}

	// TODO default/package for testing! Should this be private???
	int countPawnsInSameFile(int[] pawnFileRecord) {
		int count = 0;
		for (int i = 0; i < pawnFileRecord.length; i++) {
			int p = pawnFileRecord[i];
			if (p > 1) {
				count += p - 1;
			}
		}
		return count;
	}

	/**
	 * @param color
	 * @param piece
	 * @return
	 */
	private boolean sameColor(Color color, Piece piece) {
		return piece.isWhite() && color == Color.WHITE ||
			piece.isBlack() && color == Color.BLACK;
	}

	public List<Piece> getPieces(Color color) {
		List<Piece> pieces = new ArrayList<Piece>(); 
		for (List<Piece> rank : board) {
			for (Piece piece : rank) {
				if (sameColor(color, piece) && piece.getType() != Type.NO_PIECE) {
					pieces.add(piece);
				}
			}
		}
		System.out.println("getPieces");
		System.out.println(Arrays.toString(pieces.toArray()));
		return pieces;
	}
}
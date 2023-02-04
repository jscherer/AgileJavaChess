package chess;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static util.StringUtil.BLANK_RANK;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import pieces.Piece;
import pieces.Piece.Color;
import util.StringUtil;

	
public class BoardTest {

	double delta = 0.0;
	
	private static final char ASCII_96 = '`';
	private static final char BLACK_QUEEN_REPRESENTATION = 'Q';
	private static final char WHITE_ROOK_REPRESENTATION = 'r';
	private static final char BLACK_PAWN_REPRESENTATION = 'P';
	private static final char WHITE_PAWN_REPRESENTATION = 'p';

	Board board;
	
// 	initialized board
//							// ranks
//			"RNBQKBNR"  	// rank 8
//			"PPPPPPPP"  	// rank 7
//			"........" 
//			"........" 
//			"........" 
//			"pppppppp"  	// rank 2
//			"rnbqkbnr" 		// rank 1
// 	files	 abcdefgh
	@Before
	public void setUp() {
		board = new Board();
		board.initialize();
//		System.out.println(board);
	}

	@Test
	public final void testCreate() {
		Board board = new Board();
		assertNotNull(board);
		assertEquals(0, board.count());  // without NO_PIECE
	}
	
	@Test
	public void testPrintRank() {
		assertEquals("RNBQKBNR", board.print(board.getRank8()));
		assertEquals("PPPPPPPP", board.print(board.getRank7()));
		assertEquals(BLANK_RANK, board.print(board.getRank6()));
		assertEquals(BLANK_RANK, board.print(board.getRank5()));
		assertEquals(BLANK_RANK, board.print(board.getRank4()));
		assertEquals(BLANK_RANK, board.print(board.getRank3()));
		assertEquals("pppppppp", board.print(board.getRank2()));
		assertEquals("rnbqkbnr", board.print(board.getRank1()));		
	}
		
	public void testPrintInitialBoard() {
		assertEquals(32, board.count());
		String blankRank = StringUtil.appendNewLine(BLANK_RANK);
		assertEquals(
				StringUtil.appendNewLine("RNBQKBNR") + 
				StringUtil.appendNewLine("PPPPPPPP") + 
				blankRank + blankRank + blankRank + blankRank + 
				StringUtil.appendNewLine("pppppppp") + 
				StringUtil.appendNewLine("rnbqkbnr"),
				board.print());
		
		assertEquals(8, board.count(WHITE_PAWN_REPRESENTATION)); // 'p'
		assertEquals(8, board.count(BLACK_PAWN_REPRESENTATION));
		assertEquals(2, board.count(WHITE_ROOK_REPRESENTATION)); // 'r'
		assertEquals(1, board.count(BLACK_QUEEN_REPRESENTATION));
	}
		
	/**
	 * Given an initial board setup, 
	 * asking for the piece at "a8" returns the black rook.
	 * The white king is at "e1".
	 */
	@Test
	public void testGetPieceAt() {
		Piece blackRook = board.getPieceAt("a8");
		assertEquals('R', blackRook.getRepresentation());
		Piece whiteKing = board.getPieceAt("e1");
		assertEquals('k', whiteKing.getRepresentation());
	}

	@Test
	public void testFile() {
		assertEquals(0, board.file('a'));
		assertEquals(1, board.file('b'));
		assertEquals(7, board.file('h'));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFileIllegalArgumentException() {
		board.file(ASCII_96);
		board.file('i');
	}

	@Test
	public void testRank() {
		assertEquals(0, board.rank('1'));
		assertEquals(6, board.rank('7'));
		assertEquals(7, board.rank('8'));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRankIllegalArgumentException() {
		board.rank('0');
		board.rank('9');
	}
	
	/**
	 * place pieces at arbitrary positions on the board. 
		. . . . . . . . 8 (rank 8)
		. . . . . . . . 7
		. K . . . . . . 6
		. R . . . . . . 5
		. . k . . . . . 4
		. . . . . . . . 3
		. . . . . . . . 2
		. . . . . . . . 1 (rank 1)
files	a b c d e f g h									*/
	@Test
	public void testPlacePiece() {
		String position = "b6";
		board.place('K', position);
		assertEquals('K', board.getPieceAt(position).getRepresentation());
		
		position = "b5";
		board.place('R', position);
		assertEquals('R', board.getPieceAt(position).getRepresentation());
		
		position = "c4";
		board.place('k', position);
		assertEquals('k', board.getPieceAt(position).getRepresentation());
	}
	
	/**
	 * Using a very primitive board evaluation function, 
	 * you will add up the values of the pieces on the board. 
	 * Calculate this sum by 
	 * 
	 * - adding 9 points for a queen, 
	 * - 5 points for a rook, 
	 * - 3 points for a bishop, and 
	 * - 2.5 points for a knight. 
	 * - Add 0.5 points for a pawn that is on the same file as a pawn of the same color
	 * - and 1 point for a pawn otherwise.
	 * 
	 * Remember to count the points for only one side at a time.
	 *
		. K R . . . . . 8
		P . P B . . . . 7
		. P . . Q . . . 6
		. . . . . . . . 5
		. . . . . n q . 4
		. . . . . p . p 3
		. . . . . p p . 2
		. . . . r k . . 1
		a b c d e f g h
		
	* For the example shown, 
	* black would have a strength of 20
	*/
	@Test
	public void testEvalBlackStrength() {
		Board board = new Board();
		
		assertEquals(0, board.evalStrength(Color.BLACK), delta);
		
		// add nothing for a king, strength = 0
		board.place('K', "b8");
		assertEquals(0, board.evalStrength(Color.BLACK), delta);
		
		// add 5 points for a rook, strength = 5		
		board.place('R', "c8");
		assertEquals(5, board.evalStrength(Color.BLACK), delta);
		
		// add 1 point for pawn, strength = 6
		board.place('P', "a7");
		assertEquals(6, board.evalStrength(Color.BLACK), delta);
		
		// add 1 point for pawn, strength = 7
		board.place('P', "c7");
		assertEquals(7, board.evalStrength(Color.BLACK), delta);
		
		// add 3 points for a bishop, strength = 10
		board.place('B', "d7");
		assertEquals(10, board.evalStrength(Color.BLACK), delta);
		
		// add 1 point for pawn, strength = 11
		board.place('P', "b6");
		assertEquals(11, board.evalStrength(Color.BLACK), delta);
		
		// add 9 points for a queen, black would have a strength of 20
		board.place('Q', "e6");
		assertEquals(20, board.evalStrength(Color.BLACK), delta);
		
		board.place('n', "f4");
		board.place('q', "g4");
		board.place('p', "f3"); // add 1.0
		board.place('p', "h3");
		board.place('p', "g2"); // add 1.0
		board.place('r', "e1"); // add 5
		board.place('k', "f1"); // white would have a strength of 20.

		assertEquals(20, board.evalStrength(Color.BLACK), delta);		
	}
	
	/*
	 * For the example shown above, 
	 * white would have a strength of 19.5.	 
	 */
	@Test
	public void testEvalWhiteStrength() {
		Board board = new Board();
		
		assertEquals(0, board.evalStrength(Color.WHITE), delta);

		// Add pieces to the board one by one, altering your assertions each time.
		board.place('K', "b8");
		board.place('R', "c8");
		board.place('P', "a7");
		board.place('P', "c7");
		board.place('B', "d7");
		board.place('P', "b6");
		board.place('Q', "e6");
		assertEquals(0, board.evalStrength(Color.WHITE), delta);
		
		board.place('n', "f4"); // add 2.5
		assertEquals(2.5, board.evalStrength(Color.WHITE), delta);
		
		board.place('q', "g4"); // add 9
		assertEquals(11.5, board.evalStrength(Color.WHITE), delta);
		
		board.place('p', "f3"); // add 1.0
		board.place('p', "b2"); // add 1.0		
		board.place('p', "g2"); // add 1.0
		assertEquals(14.5, board.evalStrength(Color.WHITE), delta);
		
		board.place('r', "e1"); // add 5
		board.place('k', "f1"); 
		
		// white would have a strength of 19.5.
		assertEquals(19.5, board.evalStrength(Color.WHITE), delta);
	}
	
	@Test
	public void testEvalStrengthPawnsInSameFile() {
		Board board = new Board();
		
		assertEquals(0, board.evalStrength(Color.WHITE), delta);
		
		board.place('p', "f3"); // add 1.0
		board.place('p', "h3");	// add 1.0
		assertEquals(2, board.evalStrength(Color.WHITE), delta);

		// Finally, add the complex scenario of determining if another pawn is in the same file.
		board.place('p', "f2"); // "f3", "f2" -> 3 - 0.5
		assertEquals(2.5, board.evalStrength(Color.WHITE), delta);
		
		board.place('p', "h2"); // "h3", "h2" -> 4 - 2 x 0.5
		assertEquals(3, board.evalStrength(Color.WHITE), delta);
		
		board.place('p', "f4"); // -> add 0.5
		assertEquals(3.5, board.evalStrength(Color.WHITE), delta);
	}
	
//			[0, 0, 0, 0, 0, 0, 0, 0]
//			[0, 0, 0, 0, 0, 1, 0, 1]
//			[0, 0, 0, 0, 0, 2, 0, 1]
//			[0, 0, 0, 0, 0, 2, 0, 2]
//			[0, 0, 0, 0, 0, 3, 0, 2]
	@Test
	public void testCountPawnsInSameFile() {
		assertEquals(0, board.countPawnsInSameFile(new int[] {0, 0, 0, 0, 0, 0, 0, 0}));
		assertEquals(0, board.countPawnsInSameFile(new int[] {0, 0, 0, 0, 0, 1, 0, 0}));
		assertEquals(0, board.countPawnsInSameFile(new int[] {0, 0, 0, 0, 0, 1, 0, 1}));
		assertEquals(1, board.countPawnsInSameFile(new int[] {0, 0, 0, 0, 0, 2, 0, 1}));
		assertEquals(2, board.countPawnsInSameFile(new int[] {0, 0, 0, 0, 0, 2, 0, 2}));
		assertEquals(3, board.countPawnsInSameFile(new int[] {0, 0, 0, 0, 0, 3, 0, 2}));
	}
	
	// For each side (black and white),
	// TODO gather the list of pieces in a collection.
	@Test 	
	@Ignore
	public void testGetWhitePieces() {
		List<Piece> pieces = initWhitePieces();
		assertEquals(16, pieces.size());
		assertArrayEquals(pieces.toArray(), board.getPieces(Color.WHITE).toArray());
	}

	@Test
	@Ignore
	public void testGetBlackPieces() {
		List<Piece> pieces = initBlackPieces();
		assertEquals(16, pieces.size());
		assertArrayEquals(pieces.toArray(), board.getPieces(Color.BLACK).toArray());
	}

	/**
	 * @return
	 */
	private List<Piece> initWhitePieces() {
		List<Piece> pieces = board.getRank1();
		pieces.addAll(board.getRank2());
		return pieces;
	}

	private List<Piece> initBlackPieces() {
		List<Piece> pieces = board.getRank7();
		pieces.addAll(board.getRank8());
		return pieces;
	}
}

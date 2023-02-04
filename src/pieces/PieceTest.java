package pieces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static pieces.Piece.createBishop;
import static pieces.Piece.createKing;
import static pieces.Piece.createKnight;
import static pieces.Piece.createPawn;
import static pieces.Piece.createQueen;
import static pieces.Piece.createRook;
import static pieces.Piece.noPiece;

import org.junit.Test;

import pieces.Piece.Color;
import pieces.Piece.Type;

public class PieceTest {

	Piece whitePawn = createPawn(Color.WHITE);
	Piece blackPawn = createPawn(Color.BLACK);

	@Test
	public void testCreate() {
		assertEquals('K', Piece.create('K').getRepresentation());
		assertEquals('R', Piece.create('R').getRepresentation());
		assertEquals('k', Piece.create('k').getRepresentation());
	}
	
	@Test
	public void testCreateXyz() {
		verifyCreation(whitePawn, blackPawn, Type.PAWN);
		verifyCreation(createRook(Color.WHITE), createRook(Color.BLACK), Type.ROOK);
		verifyCreation(createKnight(Color.WHITE), createKnight(Color.BLACK), Type.KNIGHT);
		verifyCreation(createBishop(Color.WHITE), createBishop(Color.BLACK), Type.BISHOP);
		verifyCreation(createQueen(Color.WHITE), createQueen(Color.BLACK), Type.QUEEN);
		verifyCreation(createKing(Color.WHITE), createKing(Color.BLACK), Type.KING);
		Piece blank = noPiece();
		assertEquals('.', blank.getRepresentation());
		assertEquals(Type.NO_PIECE, blank.getType());
	}

	private void verifyCreation(Piece whitePiece, Piece blackPiece, Type type) {
		char representation = type.getRepresentation();
		assertTrue(whitePiece.isWhite());
		assertEquals(type, whitePiece.getType());
		assertEquals(representation, whitePiece.getRepresentation());
		assertTrue(blackPiece.isBlack());
		assertEquals(type, blackPiece.getType());
		assertEquals(Character.toUpperCase(representation), blackPiece.getRepresentation());
	}

	@Test
	public final void testCreateWhitePawn() {
		assertNotNull(whitePawn);
	}

	@Test
	public void testIsWhite() {
		assertTrue(whitePawn.isWhite());
	}

	@Test
	public void testIsBlack() {
		assertTrue(blackPawn.isBlack());
	}

	@Test
	public void testPawn() {
		assertEquals(Type.PAWN, whitePawn.getType());
		assertEquals(Type.PAWN, blackPawn.getType());
	}

	@Test
	public void testToStrimg() {
		assertEquals("p", whitePawn.toString());
		assertEquals("P", blackPawn.toString());
	}

}

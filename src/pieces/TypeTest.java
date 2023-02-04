package pieces;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pieces.Piece.Type;

public class TypeTest {

	@Before
	public void setUp() throws Exception {
	}
	
// 	PAWN('p'), ROOK('r'), KNIGHT('n'), BISHOP('b'), QUEEN('q'), KING('k');
	@Test
	public final void testRepresentation() {
		assertEquals('p', Type.PAWN.getRepresentation());
		assertEquals('r', Type.ROOK.getRepresentation());
		assertEquals('n', Type.KNIGHT.getRepresentation());
		assertEquals('b', Type.BISHOP.getRepresentation());
		assertEquals('q', Type.QUEEN.getRepresentation());
		assertEquals('k', Type.KING.getRepresentation());
	}

}

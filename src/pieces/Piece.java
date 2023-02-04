package pieces;

/*
 * A Piece is a color plus a name (pawn, knight, rook, bishop, queen, or king).
 * A Piece should be a value object: It should have a private constructor and no
 * way to change anything on the object after construction. Create factory
 * methods to return Piece objects based on the color and name. Eliminate the
 * ability to create a default piece.
 */
public class Piece {

	private Color color = Color.WHITE;
	private Type type;

	public enum Color {
		BLACK, WHITE
	};

	public enum Type {
		PAWN('p', 1), ROOK('r', 5), KNIGHT('n', 2.5), BISHOP('b', 3), QUEEN('q', 9), KING('k', 0), NO_PIECE('.', 0);

		char repr = '.';
		double strength = 0.0;

		Type(char repr, double strength) {
			this.repr = repr;
			this.strength = strength;
		}

		public char getRepresentation() {
			return repr;
		}

		public double getStrength() {
			return strength;
		}
	};

	private Piece(Color color, Type name) {
		this.color = color;
		this.type = name;
	}

	public static Piece createPawn(Color color) {
		return new Piece(color, Type.PAWN);
	}

	public static Piece createRook(Color color) {
		return new Piece(color, Type.ROOK);
	}

	public static Piece createKnight(Color color) {
		return new Piece(color, Type.KNIGHT);
	}

	public static Piece createBishop(Color color) {
		return new Piece(color, Type.BISHOP);
	}

	public static Piece createQueen(Color color) {
		return new Piece(color, Type.QUEEN);
	}

	public static Piece createKing(Color color) {
		return new Piece(color, Type.KING);
	}

	public static Piece noPiece() {
		return new Piece(Color.WHITE, Type.NO_PIECE);
	}
	
	public static Piece create(char piece) {
		switch (piece) {
		case 'b':
			return createBishop(Color.WHITE);
		case 'B':
			return createBishop(Color.BLACK);
		case 'k':
			return createKing(Color.WHITE);
		case 'K':
			return createKing(Color.BLACK);
		case 'n':
			return createKnight(Color.WHITE);
		case 'N':
			return createKnight(Color.BLACK);
		case 'p':
			return createPawn(Color.WHITE);
		case 'P':
			return createPawn(Color.BLACK);
		case 'q':
			return createQueen(Color.WHITE);
		case 'Q':
			return createQueen(Color.BLACK);			
		case 'r':
			return createRook(Color.WHITE);
		case 'R':
			return createRook(Color.BLACK);
		default:
			return noPiece();
		}		
	}


	public Type getType() {
		return type;
	}

	public boolean isWhite() {
		return color == Color.WHITE ? true : false;
	}

	public boolean isBlack() {
		return !isWhite();
	}

	@Override
	public String toString() {
		return Character.toString(getRepresentation());
	}

	public char getRepresentation() {
		char repr = type.getRepresentation();
		return color.equals(Color.WHITE) ? repr : Character.toUpperCase(repr);
	}
}

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.misc.Interval;

public class CaseInsensitiveStream implements CharStream {
    private final CharStream stream;

    public CaseInsensitiveStream(CharStream stream) {
        this.stream = stream;
    }

    @Override
    public int LA(int i) {
        int c = stream.LA(i);
        return c == IntStream.EOF ? c : Character.toLowerCase(c);
    }

    // Delegación de los demás métodos
    @Override public void consume() { stream.consume(); }
    @Override public int mark() { return stream.mark(); }
    @Override public void release(int marker) { stream.release(marker); }
    @Override public int index() { return stream.index(); }
    @Override public void seek(int index) { stream.seek(index); }
    @Override public int size() { return stream.size(); }
    @Override public String getSourceName() { return stream.getSourceName(); }
    @Override public String getText(Interval interval) { return stream.getText(interval); }
}

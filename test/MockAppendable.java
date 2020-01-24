import java.io.IOException;

/**
 * For testing bad appendable outputs.
 */
public class MockAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("Intentionally triggering IOException!");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("Intentionally triggering IOException!");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("Intentionally triggering IOException!");
  }
}

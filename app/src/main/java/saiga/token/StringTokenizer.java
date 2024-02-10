package saiga.token;

public class StringTokenizer extends Tokenizer {
    private String str;

    public StringTokenizer(String str) {
        super();
        this.str = str;
    }

    public StringTokenizer(String str, String delimeters, String quotationMarks) {
        super(delimeters,quotationMarks);
        this.str = str;
    }

    @Override
    public boolean hasMoreElements() {
        
    }

    @Override
    public Token nextElement() {
        
    }

    @Override
    public boolean hasNextToken() {
        
    }

    @Override
    public Token nextToken() {
        
    }
}
